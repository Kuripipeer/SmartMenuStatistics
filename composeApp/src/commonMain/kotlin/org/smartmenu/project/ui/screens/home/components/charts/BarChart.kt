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
import ir.ehsannarmani.compose_charts.models.*
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.ChartColors
import org.smartmenu.project.ui.asDoubleList

@Composable
fun BarChart(grafica: Grafica) {
    val colors = MaterialTheme.colorScheme

    val barColors = remember(grafica) {
        grafica.labels.mapIndexed { index, _ ->
            ChartColors[index % ChartColors.size]
        }
    }

    val numericValues = remember(grafica) {
        grafica.values.asDoubleList()  // <- aquí convertimos JsonElement -> Double
    }

    val data = remember(grafica, barColors, numericValues) {
        grafica.labels.zip(numericValues).mapIndexed { index, (label, value) ->
            Bars(
                label = label,
                values = listOf(
                    Bars.Data(
                        label = label,
                        value = value,                 // ya es Double
                        color = SolidColor(barColors[index])
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
        labelProperties = LabelProperties(
            enabled = false // ya decidiste quitar labels de abajo
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
