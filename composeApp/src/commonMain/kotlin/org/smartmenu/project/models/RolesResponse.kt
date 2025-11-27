package org.smartmenu.project.models

import kotlinx.serialization.Serializable

typealias RolResponse = List<RolesResponse>
@Serializable
data class RolesResponse(
    val id: Int,
    val nombre: String
)
