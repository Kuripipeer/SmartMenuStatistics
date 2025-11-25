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
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.SmartMenuTheme

@Composable
fun LineChart(grafica: Grafica) {

    val colors = MaterialTheme.colorScheme

    // Serie de la línea
    val line = remember(grafica, colors.primary) {
        Line(
            values = grafica.values.map { it.toDouble() },
            color = SolidColor(colors.primary),
            drawStyle = DrawStyle.Stroke(width = 3.dp)  // grosor correcto
        )
    }

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        data = listOf(line),

        // Texto del eje X (abajo)
        labelProperties = LabelProperties(
            enabled = true,
            labels = grafica.labels,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        ),

        // Texto del eje Y (izquierda)
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

@Preview
@Composable
fun LineChartPreview() {
    val grafica = Grafica(
        tipo = "line",
        titulo = "Ventas Totales por Día",
        labels = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"),
        values = listOf(50, 120, 80, 150, 200, 170)
    )
    SmartMenuTheme {
        LineChart(grafica)
    }
}
