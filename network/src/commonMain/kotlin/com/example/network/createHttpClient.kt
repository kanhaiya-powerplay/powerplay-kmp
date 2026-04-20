package com.example.network

import com.example.network.apiClientConfigurers.APIClientConfigurer
import io.ktor.client.HttpClient

/**
 * createHttpClient — expect/actual factory that builds the Ktor HttpClient
 * with the correct platform engine.
 *
 * WHY EXPECT/ACTUAL
 * ─────────────────
 * The Ktor engine class is platform-specific:
 *   • Android → HttpClient(OkHttp)  — lets us inject a pre-configured OkHttpClient
 *   • iOS     → HttpClient(Darwin)  — uses Apple's native HTTP stack
 *
 * commonMain cannot reference OkHttp or Darwin directly, so we use the
 * expect/actual pattern:
 *   • This file declares the contract (expect)
 *   • createHttpClient.android.kt and createHttpClient.ios.kt provide the
 *     platform implementations (actual)
 *
 * APIClientProvider calls this function instead of creating HttpClient directly,
 * so the correct engine is always used and the host app's OkHttpClient
 * (with ApiLogsInterceptor) is injected on Android debug builds.
 *
 * @param configurers  List of APIClientConfigurer instances (ContentNegotiation,
 *                     DefaultHeaders, etc.) — same list used before this refactor.
 * @return             A fully configured HttpClient ready for network calls.
 */
expect fun createHttpClient(configurers: List<APIClientConfigurer>): HttpClient
