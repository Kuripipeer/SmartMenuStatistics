package org.smartmenu.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.smartmenu.project.ui.EditSupplierScreenRoute
import org.smartmenu.project.ui.EditUserScreenRoute
import org.smartmenu.project.ui.HomeScreenRoute
import org.smartmenu.project.ui.LoginScreenRoute
import org.smartmenu.project.ui.NewSupplierScreenRoute
import org.smartmenu.project.ui.RegisterScreenRoute
import org.smartmenu.project.ui.SmartMenuTheme
import org.smartmenu.project.ui.SuppliersScreenRoute
import org.smartmenu.project.ui.UsersScreenRoute
import org.smartmenu.project.ui.screens.auth.LoginScreen
import org.smartmenu.project.ui.screens.auth.RegisterScreen
import org.smartmenu.project.ui.screens.home.HomeScreen
import org.smartmenu.project.ui.screens.suppliers.EditAddSupplier
import org.smartmenu.project.ui.screens.suppliers.NewSupplier
import org.smartmenu.project.ui.screens.suppliers.SuppliersHome
import org.smartmenu.project.ui.screens.users.EditUser
import org.smartmenu.project.ui.screens.users.UserHome

import smartmenustatistics.composeapp.generated.resources.Res
import smartmenustatistics.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    SmartMenuTheme {
        val navController = rememberNavController()
        Scaffold { innerPadding: PaddingValues ->
            NavHost(
                navController = navController,
                startDestination = LoginScreenRoute
            ) {
                composable<LoginScreenRoute> {
                    LoginScreen(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
                composable<RegisterScreenRoute> {
                    RegisterScreen(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
                composable<HomeScreenRoute> {
                    HomeScreen(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
                composable<UsersScreenRoute>{
                    UserHome(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
                composable<EditUserScreenRoute> { entry ->
                    val args = entry.toRoute<EditUserScreenRoute>()
                    EditUser(
                        navController = navController,
                        innerPadding = innerPadding,
                        userId = args.userId
                    )
                }
                composable<SuppliersScreenRoute>{
                    SuppliersHome(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
                composable<NewSupplierScreenRoute> {
                    NewSupplier(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
                composable<EditSupplierScreenRoute> {
                    val args = it.toRoute<EditSupplierScreenRoute>()
                    EditAddSupplier(
                        navController = navController,
                        innerPadding = innerPadding,
                        supplierId = args.supplierId
                    )
                }
            }
        }
    }
}