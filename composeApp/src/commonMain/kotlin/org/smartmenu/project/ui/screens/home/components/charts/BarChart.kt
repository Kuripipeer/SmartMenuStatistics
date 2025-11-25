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
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.SmartMenuTheme
import org.smartmenu.project.ui.ChartColors // IMPORTANTE

@Composable
fun BarChart(grafica: Grafica) {

    val colors = MaterialTheme.colorScheme

    // Asignar colores personalizados a cada barra
    val barColors = remember(grafica) {
        grafica.labels.mapIndexed { index, _ ->
            ChartColors[index % ChartColors.size]  // usa paleta oficial
        }
    }

    // Construcción de la data con color por barra
    val data = remember(grafica, barColors) {
        grafica.labels.zip(grafica.values).mapIndexed { index, (label, value) ->
            Bars(
                label = label,
                values = listOf(
                    Bars.Data(
                        label = label,
                        value = value.toDouble(),
                        color = SolidColor(barColors[index]) // color único
                    )
                )
            )
        }
    }

    ColumnChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        data = data,
        barProperties = BarProperties(
            thickness = 18.dp,
            spacing = 8.dp,
            cornerRadius = Bars.Data.Radius.Circular(6.dp),
            style = DrawStyle.Fill
        ),

        // QUITAR labels inferiores (evita recorte)
        labelProperties = LabelProperties(
            enabled = false
        ),

        // Indicadores numéricos eje Y
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        ),

        // Labels superiores (ya con colores distintos)
        labelHelperProperties = LabelHelperProperties(
            enabled = true,
            textStyle = TextStyle.Default.copy(color = colors.onBackground)
        )
    )

}

@Preview
@Composable
fun BarChartPreview() {
    val grafica = Grafica(
        tipo = "bar",
        titulo = "Ventas Mensuales",
        labels = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun"),
        values = listOf(150, 200, 180, 220, 300, 250)
    )
    SmartMenuTheme {
        BarChart(grafica = grafica)
    }
}
