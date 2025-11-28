package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class NewClientResponse(
    val message: String? = null,
    val client_id: Int? = null,
    val error: String? = null
)
