package org.smartmenu.project.models

import kotlinx.serialization.Serializable

typealias UserResponse = List<UsersResponse>
@Serializable
data class UsersResponse(
    val id: Int,
    val nombre: String,
    val usuario: String,
    val contraseña: String,
    val rol_id: Int
)
