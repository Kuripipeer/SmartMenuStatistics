package org.smartmenu.project.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.ui.screens.home.components.GraficaCard
import org.smartmenu.project.ui.screens.home.components.charts.BarChart
import org.smartmenu.project.ui.screens.home.components.charts.LineChart
import org.smartmenu.project.ui.screens.home.components.charts.PieChart
import org.smartmenu.project.ui.viewmodels.MetricsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, innerPadding: PaddingValues) {
    val mVm: MetricsViewModel = viewModel()
    val colors = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var tableTitle by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(top = 50.dp)
            .padding(horizontal = 15.dp)
    ){
        item {
            Button(
                onClick = {
                    scope.launch {
                        tableTitle = "Ventas diarias"
                        mVm.separateMetricsByTitle(tableTitle)
                        sheetState.expand()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Ventas diarias", color = colors.onBackground)
            }
        }

        item {
            Button(
                onClick = {
                    scope.launch {
                        tableTitle = "Ventas por platillo"
                        mVm.separateMetricsByTitle(tableTitle)
                        sheetState.expand()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Ventas por platillo", color = colors.onBackground)
            }
        }

        item {
            Button(
                onClick = {
                    scope.launch {
                        tableTitle = "Meseros con más pedidos"
                        mVm.separateMetricsByTitle(tableTitle)
                        sheetState.expand()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Meseros con más pedidos", color = colors.onBackground)
            }
        }
    }

    if (mVm.showMetrics){
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    mVm.showMetrics = false
                    sheetState.hide()
                }
            },
            sheetState = sheetState,
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                GraficaCard(grafica = mVm.separatedMetrics)
            }
        }
    }

}