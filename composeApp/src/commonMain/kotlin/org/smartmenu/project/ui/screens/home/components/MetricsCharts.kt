package org.smartmenu.project.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.screens.home.components.charts.BarChart
import org.smartmenu.project.ui.screens.home.components.charts.LineChart
import org.smartmenu.project.ui.screens.home.components.charts.PieChart
import org.smartmenu.project.ui.screens.home.components.charts.TableChart

@Composable
fun GraficaCard(
    grafica: Grafica?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            grafica?.titulo?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            when (grafica?.tipo?.lowercase()) {
                "bar"  -> BarChart(grafica)
                "line" -> LineChart(grafica)
                "pie"  -> PieChart(grafica)
                "table" -> TableChart(grafica)
                else   -> Text("Tipo de gráfica no soportado: ${grafica?.tipo}")
            }
        }
    }
}