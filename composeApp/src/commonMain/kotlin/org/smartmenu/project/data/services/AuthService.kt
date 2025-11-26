package org.smartmenu.project.data.services

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.http.cio.Request
import org.smartmenu.project.models.AuthResponse
import org.smartmenu.project.models.LoginBody
import org.smartmenu.project.models.MetricsResponse

interface AuthService {

    @POST("login")
    suspend fun login(@Body request: LoginBody): AuthResponse

}