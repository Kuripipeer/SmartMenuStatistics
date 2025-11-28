package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class NewSupplierBody(
    val nombre: String,
    val contacto: String,
    val telefono: String,
    val correo: String
)
