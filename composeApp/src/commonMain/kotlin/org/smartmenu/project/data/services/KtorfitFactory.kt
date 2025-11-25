package org.smartmenu.project.data.services

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorfitFactory {

    val base = "http://159.89.144.67:3000/api/"

    private val httpClient = HttpClient {
        expectSuccess = false

        install(HttpTimeout){
            requestTimeoutMillis = 40000
            connectTimeoutMillis = 40000
            socketTimeoutMillis = 40000
        }

        install(ContentNegotiation){
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    private val ktorfit = Ktorfit
        .Builder()
        .httpClient(httpClient)
        .baseUrl(base)
        .build()

    fun getMetricsService(): MetricsService {
        return ktorfit.createMetricsService()
    }

}