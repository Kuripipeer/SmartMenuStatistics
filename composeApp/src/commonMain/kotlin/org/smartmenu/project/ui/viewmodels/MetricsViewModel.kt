package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.smartmenu.project.data.services.KtorfitFactory
import org.smartmenu.project.models.Data
import org.smartmenu.project.models.Grafica
import org.smartmenu.project.models.MetricsResponse

class MetricsViewModel : ViewModel() {
    val metricsService = KtorfitFactory.getMetricsService()
    var showMetrics by mutableStateOf(false)
    var separatedMetrics: Grafica? = null
    var response : MetricsResponse = MetricsResponse(Data(listOf()), false)

    init {
        getMetrics()
    }

    fun getMetrics() {
        viewModelScope.launch {
            try {
                response = metricsService.getMetrics()
                println("Metrics fetched successfully: $response")
            }catch (e: Exception) {
                println("Error fetching metrics: ${e.message}")
            }
        }
    }

    fun separateMetricsByTitle(title: String){
        separatedMetrics = response.data.graficas.firstOrNull { it.titulo == title }
        showMetrics = true
        println(separatedMetrics)
    }
}