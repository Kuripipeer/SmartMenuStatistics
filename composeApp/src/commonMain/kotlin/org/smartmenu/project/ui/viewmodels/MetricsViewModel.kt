package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.smartmenu.project.data.services.KtorfitFactory
import org.smartmenu.project.models.Data
import org.smartmenu.project.models.MetricsResponse

class MetricsViewModel : ViewModel() {

    private val metricsService = KtorfitFactory.getMetricsService()

    var response by mutableStateOf(
        MetricsResponse(
            data = Data(graficas = emptyList()),
            ok = false
        )
    )
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        getMetrics()
    }

    fun getMetrics() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val result = metricsService.getMetrics()
                response = result

                println("Metrics fetched successfully: $result")
            } catch (e: Exception) {
                println("Error fetching metrics: ${e.message}")
                errorMessage = e.message ?: "Error desconocido al cargar métricas"

                // Dejas la respuesta en estado "sin datos"
                response = MetricsResponse(
                    data = Data(emptyList()),
                    ok = false
                )
            } finally {
                isLoading = false
            }
        }
    }
}
