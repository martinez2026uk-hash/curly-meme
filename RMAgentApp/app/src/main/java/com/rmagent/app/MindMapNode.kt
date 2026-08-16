package com.rmagent.app

data class MindMapNode(
    val id: String,
    var text: String,
    var x: Float,
    var y: Float,
    var parentId: String?,
    val children: MutableList<MindMapNode> = mutableListOf()
)
