package org.smartmenu.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform