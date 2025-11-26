package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String? = null,
    val error: String? = null
)
