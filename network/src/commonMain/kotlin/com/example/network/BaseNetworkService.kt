package com.example.network

import com.example.core.KMPConfig
import com.example.core.dataService.BaseDataService
import com.example.core.dataService.models.APIRequest
import com.example.core.dataService.models.APIResponse
import com.example.core.dataService.models.AppError
import com.example.core.dataService.models.DataTypeInfo
import com.example.network.interceptor.RequestInterceptorExecutor
import com.example.network.interceptor.ResponseInterceptorExecutor
import com.example.network.interceptor.requestInterceptor.RequestInterceptor
import com.example.network.interceptor.responseInterceptor.ResponseInterceptor

/**
 * BaseNetworkService — base class for all KMP network services.
 *
 * CHANGES FROM ORIGINAL
 * ──────────────────────
 * `performHttpDefault` now wraps the HTTP call in try/catch and notifies
 * KMPConfig.debugObserver on both API-level failures and exceptions.
 *
 * HOW DEBUG OBSERVER IS USED HERE
 * ─────────────────────────────────
 * 1. API error response (non-200 status):
 *    → observer.onError() is called with an Exception whose message contains
 *      the HTTP status code, the API error message, and the request path.
 *      The host app's CrashReporter will log this to the debug Room DB and
 *      show a notification if the host app has registered KMPDebugBridge.
 *
 * 2. Unexpected exception (network failure, JSON parse error, etc.):
 *    → observer.onError() is called with the raw exception.
 *      Same path to the host app's crash log.
 *
 * NULL SAFETY
 * ───────────
 * KMPConfig.debugObserver is null on release builds.  All call sites use
 * `?.` — nothing runs if the observer is not set.
 *
 * NOTE ON API LOGGING (requests + responses)
 * ──────────────────────────────────────────
 * Full request/response logging (URL, headers, body, cURL, timing) is handled
 * transparently by the host app's ApiLogsInterceptor, which is attached to the
 * OkHttpClient injected via initKMPForAndroid().  No duplicate logging needed here.
 */
open class BaseNetworkService : BaseDataService {

    open val responseInterceptors: List<ResponseInterceptor>
        get() = emptyList()
    open val requestInterceptors: List<RequestInterceptor>
        get() = emptyList()
    
    override suspend fun <T> perform(request: APIRequest, responseType: DataTypeInfo): APIResponse<T> {

        var request = request

        RequestInterceptorExecutor(requestInterceptors).execute<T>(request, responseType) {
            request = it
        }?.let { return it }

        var response = performHttpDefault<T>(request, responseType)

        ResponseInterceptorExecutor(responseInterceptors).execute<T>(request, responseType, response) {
            response = it
        }?.let { return it }

        return response
    }

    suspend fun <T> performHttpDefault(
        request: APIRequest,
        responseType: DataTypeInfo
    ): APIResponse<T> {
        return try {
            val httpResponse = HttpServiceFactory.execute(APIClientProvider.client, request)
            val response = APIResponseHandler.handle<T>(httpResponse, responseType)

            // Notify debug observer when the server returns an error status.
            // On release builds KMPConfig.debugObserver is null — this is a no-op.
            if (response is APIResponse.Failure) {
                KMPConfig.debugObserver?.onError(
                    throwable = Exception(
                        "API Error [${httpResponse.status.value}]: " +
                        "${response.apiError.message} — path: ${request.path}"
                    ),
                    context = "BaseNetworkService.performHttpDefault"
                )
            }

            response
        } catch (e: Exception) {
            // Network failure, timeout, JSON parse error, etc.
            // Notify debug observer so the error appears in the crash log.
            KMPConfig.debugObserver?.onError(
                throwable = e,
                context = "BaseNetworkService.performHttpDefault — path: ${request.path}"
            )
            APIResponse.Failure(AppError(message = e.message, cause = e.cause))
        }
    }
}