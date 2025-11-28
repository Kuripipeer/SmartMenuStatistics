package org.smartmenu.project.models

import kotlinx.serialization.Serializable
@Serializable
data class NewSupplierResponse(
    val proveedor_id: Int? = null,
    val error: String? = null
)
