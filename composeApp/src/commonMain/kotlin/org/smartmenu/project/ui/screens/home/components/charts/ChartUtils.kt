package org.smartmenu.project.ui

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.doubleOrNull

fun List<JsonElement>.asDoubleList(): List<Double> =
    this.mapNotNull { it.jsonPrimitive.doubleOrNull }
