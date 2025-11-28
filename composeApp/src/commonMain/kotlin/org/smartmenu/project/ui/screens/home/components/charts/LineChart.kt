package org.smartmenu.project.ui.screens.home.components.charts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.*
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.asDoubleList


private fun String.isIsoDateTime(): Boolean =
    Regex("^\\d{4}-\\d{2}-\\d{2}T").containsMatchIn(this)

@Composable
fun LineChart(grafica: Grafica) {
    val colors = MaterialTheme.colorScheme

    // 1) Valores numéricos desde List<JsonElement>
    val numericValues = remember(grafica) {
        grafica.values.asDoubleList()
    }

    // nos quedamos solo con lo de antes de la 'T'.
    val displayLabels = remember(grafica.labels) {
        grafica.labels.map { raw ->
            if (raw.isIsoDateTime()) raw.substringBefore('T') else raw
        }
    }

    // 3) Línea
    val line = remember(grafica, colors.primary, numericValues) {
        Line(
            label = grafica.titulo,
            values = numericValues,
            color = SolidColor(colors.primary),
            drawStyle = DrawStyle.Stroke(width = 3.dp)
        )
    }

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        data = listOf(line),

        // Eje X
        labelProperties = LabelProperties(
            enabled = true,
            labels = displayLabels,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        ),

        // Eje Y
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        ),

        // Texto superior (helpers)
        labelHelperProperties = LabelHelperProperties(
            enabled = true,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        )
    )
}
