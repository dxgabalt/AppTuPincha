package com.dxgabalt.tupincha

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform