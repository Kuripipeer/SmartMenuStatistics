package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class Grafica(
    val labels: List<String>,
    val tipo: String,
    val titulo: String,
    val values: List<Int>
)