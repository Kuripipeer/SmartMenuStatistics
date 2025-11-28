package org.smartmenu.project.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.smartmenu.project.ui.viewmodels.AdmonViewModel
import org.smartmenu.project.ui.viewmodels.AuthViewModel
import org.smartmenu.project.ui.viewmodels.MetricsViewModel

actual class GenericViewModelFactory actual constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AdmonViewModel::class.java) -> AdmonViewModel() as T
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel() as T
            modelClass.isAssignableFrom(MetricsViewModel::class.java) -> MetricsViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
