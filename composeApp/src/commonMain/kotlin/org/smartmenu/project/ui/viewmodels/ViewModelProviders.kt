// kotlin
// `composeApp/src/commonMain/kotlin/org/smartmenu/project/ui/viewmodels/ViewModelProviders.kt`
package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.Composable

@Composable
expect fun rememberAdmonViewModel(): AdmonViewModel

@Composable
expect fun rememberAuthViewModel(): AuthViewModel

@Composable
expect fun rememberMetricsViewModel(): MetricsViewModel
