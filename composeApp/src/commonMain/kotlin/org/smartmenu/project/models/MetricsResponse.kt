package org.smartmenu.project.models
import kotlinx.serialization.Serializable

@Serializable
data class MetricsResponse(
    val `data`: Data,
    val ok: Boolean
)