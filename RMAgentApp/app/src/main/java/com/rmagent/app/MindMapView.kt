package com.rmagent.app

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

class MindMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    var onNodeSelected: ((MindMapNode) -> Unit)? = null
    var onNodeDoubleTap: ((MindMapNode) -> Unit)? = null

    var nodes: MutableList<MindMapNode> = mutableListOf()
        private set
    var root: MindMapNode? = null
        private set

    private val nodePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#58A6FF")
        style = Paint.Style.FILL
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 36f
        typeface = Typeface.DEFAULT_BOLD
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#30363D")
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    private var selectedNode: MindMapNode? = null
    private var draggingNode: MindMapNode? = null
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var lastTapTime = 0L

    fun setRoot(rootNode: MindMapNode) {
        root = rootNode
        nodes.clear()
        flattenNodes(rootNode)
        invalidate()
    }

    private fun flattenNodes(node: MindMapNode) {
        nodes.add(node)
        node.children.forEach { flattenNodes(it) }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        root?.let { drawNode(it, canvas) }
    }

    private fun drawNode(node: MindMapNode, canvas: Canvas) {
        node.children.forEach { child ->
            canvas.drawLine(node.x, node.y, child.x, child.y, linePaint)
            drawNode(child, canvas)
        }

        val radius = 60f
        canvas.drawCircle(node.x, node.y, radius, nodePaint)
        canvas.drawText(node.text, node.x - textPaint.measureText(node.text) / 2, node.y + 12f, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val tapped = findNodeAt(x, y)
                if (tapped != null) {
                    if (System.currentTimeMillis() - lastTapTime < 300) {
                        onNodeDoubleTap?.invoke(tapped)
                    } else {
                        selectedNode = tapped
                        draggingNode = tapped
                        onNodeSelected?.invoke(tapped)
                    }
                }
                lastTapTime = System.currentTimeMillis()
                lastTouchX = x
                lastTouchY = y
            }
            MotionEvent.ACTION_MOVE -> {
                draggingNode?.let { node ->
                    node.x += (x - lastTouchX)
                    node.y += (y - lastTouchY)
                    invalidate()
                }
                lastTouchX = x
                lastTouchY = y
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                draggingNode = null
            }
        }
        return true
    }

    private fun findNodeAt(x: Float, y: Float): MindMapNode? {
        for (node in nodes) {
            val dx = node.x - x
            val dy = node.y - y
            if (sqrt(dx * dx + dy * dy) < 60f) {
                return node
            }
        }
        return null
    }
}
