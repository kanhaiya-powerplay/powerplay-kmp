package com.example.network.HTTPClientConfigurers

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header


class DefaultHeadersConfig : HTTPClientConfigurer{

    override fun apply(config: HttpClientConfig<*>) {
        config.defaultRequest {
            header("Content-type", "application/json")
            header("app-version", "10000")
            header("x-platform", "ios")
            header("language", "en")
            header("accept-language", "en")
            header("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc19hdXRoIjp0cnVlLCJfaWQiOiJVU1I0bGV4c2tqMGR5MnIiLCJtb2JpbGVfbnVtYmVyIjoiNzc0ODkyMDU0MiIsImNvdW50cnlfY29kZSI6Iis5MSIsInBob3RvX3VybCI6IiIsImlzVmVyaWZpZWQiOnRydWUsImRpc3BsYXlOYW1lIjoiRGVlcGFrIiwib3JnX2lkIjoiT1JHbjN4aTZpNWdsNzhpIiwib3JnX25hbWUiOiJPMSIsIm9yZ19pc19wYWlkIjp0cnVlLCJpc19hZG1pbiI6dHJ1ZSwiZW1haWwiOiJhZ3Jhd2FsZGVlOThAZ21haWwuY29tIiwiaXNfZW1haWxfdmVyaWZpZWQiOnRydWUsImlhdCI6MTc3MzIwNjMzMCwiZXhwIjoxNzczMjEzNTMwfQ.wSGkb_bgejdM6mU3MFxX_KMIWFvbWHwMryHs6l3UYUY")
        }
    }
}