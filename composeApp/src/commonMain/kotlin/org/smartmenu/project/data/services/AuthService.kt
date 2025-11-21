package org.smartmenu.project.data.services

import de.jensklingenberg.ktorfit.http.GET
import org.smartmenu.project.models.MetricsResponse

interface AuthService {

    @GET("metrics/")
    suspend fun getMetrics(): MetricsResponse
}