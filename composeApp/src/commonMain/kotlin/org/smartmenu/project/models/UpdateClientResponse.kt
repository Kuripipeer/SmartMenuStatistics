package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateClientResponse(
    val message: String? = null,
    val details: String? = null,
    val error: String? = null

)
