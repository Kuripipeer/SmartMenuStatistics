package org.smartmenu.project.models

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val graficas: List<Grafica>
)