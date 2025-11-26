package org.smartmenu.project.models
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val usuario: String,
    val contraseña: String
)
