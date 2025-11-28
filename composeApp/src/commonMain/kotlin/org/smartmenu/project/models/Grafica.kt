package org.smartmenu.project.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Grafica(
    val labels: List<String>,
    @SerialName("type") val tipo: String,
    @SerialName("title") val titulo: String,
    val values: List<JsonElement>
)