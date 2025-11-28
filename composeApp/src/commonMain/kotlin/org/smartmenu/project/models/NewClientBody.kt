package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class NewClientBody(
    val nombre: String,
    val telefono: String,
    val email: String
)
