package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class NewUserResponse(
    val message: String? = null,
    val user_id: Int? = null,
    val error: String? = null
)