package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class ClientsResponseItem(
    val correo: String,
    val id: Int,
    val nombre: String,
    val telefono: String
)