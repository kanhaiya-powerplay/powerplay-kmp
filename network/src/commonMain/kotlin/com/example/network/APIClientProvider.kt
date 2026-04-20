package com.example.network

import com.example.network.apiClientConfigurers.configurations.ContentNegotiationConfig
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.DefaultHeadersConfig
import com.example.network.apiClientConfigurers.APIClientConfigurer
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.AcceptLanguageHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.AppVersionHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.AuthorizationHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.ContentTypeHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.LanguageHeader
import com.example.network.apiClientConfigurers.configurations.defaultHeaderConfig.headers.PlatformHeader
import io.ktor.client.HttpClient

/**
 * APIClientProvider — singleton that holds the single shared Ktor HttpClient.
 *
 * CHANGES FROM ORIGINAL
 * ──────────────────────
 * • `client` is now built via `createHttpClient(configs)` instead of
 *   `HttpClient { }`.  This goes through the expect/actual factory so the
 *   correct engine (OkHttp on Android, Darwin on iOS) is used, and the
 *   host app's pre-configured OkHttpClient (with ApiLogsInterceptor) is
 *   injected on Android debug builds.
 *
 * • `reinitialize()` is added so that `initKMPForAndroid()` can rebuild the
 *   client AFTER setting KMPAndroidConfig.okHttpClient, guaranteeing the
 *   injected OkHttpClient is always used regardless of initialization order.
 *
 * THREAD SAFETY
 * ─────────────
 * `reinitialize()` is called once during app startup before any coroutine
 * makes a network call, so no additional locking is required.
 */
object APIClientProvider {

    val configs: List<APIClientConfigurer>
        get() = listOf(ContentNegotiationConfig(), DefaultHeadersConfig(listOf(AcceptLanguageHeader(),
            AppVersionHeader(), AuthorizationHeader(), ContentTypeHeader(), LanguageHeader(),
            PlatformHeader())))

    /**
     * The live HttpClient used by all KMP network services.
     *
     * Built with the platform-specific engine (OkHttp/Darwin) via
     * createHttpClient().  Call reinitialize() to rebuild it after
     * updating KMPAndroidConfig.okHttpClient or KMPConfig.baseUrl.
     */
    var client: HttpClient = createHttpClient(configs)
        private set

    /**
     * Rebuilds the HttpClient.
     *
     * Called by initKMPForAndroid() after the host app's OkHttpClient has
     * been stored in KMPAndroidConfig, so the new client picks it up.
     * The old client is closed to release its thread-pool resources.
     */
    fun reinitialize() {
        client.close()
        client = createHttpClient(configs)
    }
}


