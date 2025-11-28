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
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.double
import kotlinx.serialization.json.int
import kotlinx.serialization.json.long
import kotlinx.serialization.json.float
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.ChartColors

@Composable
fun BarChart(grafica: Grafica) {

    val colors = MaterialTheme.colorScheme

    // Convertir JsonElement → Double de forma segura
    fun jsonToDouble(element: JsonElement): Double {
        val primitive = element.jsonPrimitive

        // Si es número normal
        primitive.doubleOrNull?.let { return it }

        // Si viene como string numérico
        primitive.content.toDoubleOrNull()?.let { return it }

        // Si viene mal, regresamos 0
        return 0.0
    }

    // Colores dinámicos
    val barColors = remember(grafica) {
        grafica.labels.mapIndexed { i, _ ->
            ChartColors[i % ChartColors.size]
        }
    }

    // Construcción segura de la data
    val data = remember(grafica, barColors) {
        grafica.labels.zip(grafica.values).mapIndexed { index, (label, valueJson) ->

            val numericValue = jsonToDouble(valueJson)

            Bars(
                label = label,
                values = listOf(
                    Bars.Data(
                        label = label,
                        value = numericValue,
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
            enabled = false
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
