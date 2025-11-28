// kotlin
// `composeApp/src/iosMain/kotlin/org/smartmenu/project/ui/viewmodels/ViewModelProviders.ios.kt`
package org.smartmenu.project.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberAdmonViewModel(): AdmonViewModel =
    remember { AdmonViewModel() }
