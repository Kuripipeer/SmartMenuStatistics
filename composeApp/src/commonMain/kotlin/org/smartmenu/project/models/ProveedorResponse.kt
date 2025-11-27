package org.smartmenu.project.models

import kotlinx.serialization.Serializable

typealias Proveedores = List<ProveedorResponse>
@Serializable
data class ProveedorResponse(
    val id: Int,
    val nombre: String,
    val contacto: String,
    val telefono: String,
    val correo: String
)
