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

@Composable
fun LineChart(grafica: Grafica) {

    val colors = MaterialTheme.colorScheme

    // Convertidor seguro JsonElement -> Double
    fun jsonToDouble(el: JsonElement): Double {
        val prim = el.jsonPrimitive

        prim.doubleOrNull?.let { return it }
        prim.content.toDoubleOrNull()?.let { return it }

        return 0.0
    }

    // Convertimos los valores sin crashear
    val numericValues = remember(grafica) {
        grafica.values.map { jsonToDouble(it) }
    }

    // Serie de línea
    val line = remember(numericValues) {
        Line(
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

        labelProperties = LabelProperties(
            enabled = true,
            labels = grafica.labels,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        ),

        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        ),

        labelHelperProperties = LabelHelperProperties(
            enabled = true,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        )
    )
}
