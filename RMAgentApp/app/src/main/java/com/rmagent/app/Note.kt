package com.rmagent.app

data class Note(
    val id: String = System.currentTimeMillis().toString(),
    var title: String,
    var body: String,
    var timestamp: Long = System.currentTimeMillis()
)
