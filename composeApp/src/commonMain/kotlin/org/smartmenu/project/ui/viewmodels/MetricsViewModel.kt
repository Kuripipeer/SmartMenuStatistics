package org.smartmenu.project.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.smartmenu.project.data.services.KtorfitFactory

class MetricsViewModel : ViewModel() {
    val metricsService = KtorfitFactory.getMetricsService()

    init {
        getMetrics()
    }

    fun getMetrics() {
        viewModelScope.launch {
            try {
                val response = metricsService.getMetrics()
                println("Metrics fetched successfully: $response")
            }catch (e: Exception) {
                println("Error fetching metrics: ${e.message}")
            }
        }
    }
}