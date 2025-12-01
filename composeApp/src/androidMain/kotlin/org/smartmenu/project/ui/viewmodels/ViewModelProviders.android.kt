// kotlin
// `composeApp/src/androidMain/kotlin/org/smartmenu/project/ui/viewmodels/ViewModelProviders.android.kt`
package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.smartmenu.project.utils.GenericViewModelFactory

@Composable
actual fun rememberAdmonViewModel(): AdmonViewModel =
    viewModel(factory = GenericViewModelFactory())

@Composable
actual fun rememberAuthViewModel(): AuthViewModel =
    viewModel(factory = GenericViewModelFactory())

@Composable
actual fun rememberMetricsViewModel(): MetricsViewModel =
    viewModel(factory = GenericViewModelFactory())