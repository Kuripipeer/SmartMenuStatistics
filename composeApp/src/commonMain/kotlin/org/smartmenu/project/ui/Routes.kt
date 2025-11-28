package org.smartmenu.project.ui

import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute

@Serializable
object HomeScreenRoute

@Serializable
object RegisterScreenRoute

@Serializable
object UsersScreenRoute

@Serializable
data class EditUserScreenRoute(val userId: Int)

@Serializable
object SuppliersScreenRoute

@Serializable
data class EditSupplierScreenRoute(val supplierId: Int)

@Serializable
object NewSupplierScreenRoute