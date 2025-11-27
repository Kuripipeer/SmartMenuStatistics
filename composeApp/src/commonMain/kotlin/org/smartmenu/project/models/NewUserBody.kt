package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class NewUserBody(
    val nombre: String,
    val usuario: String,
    val contraseña: String,
    val rol_id: Int
)
