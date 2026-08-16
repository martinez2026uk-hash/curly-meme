package com.rmagent.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rmagent.app.databinding.FragmentMindmapBinding

class MindMapFragment : Fragment() {

    private var _binding: FragmentMindmapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mindMapView: MindMapView
    private var nodes: MutableList<MindMapNode> = mutableListOf()
    private var selectedNode: MindMapNode? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMindmapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mindMapView = binding.mindMapView
        nodes = mutableListOf()

        val root = MindMapNode(
            id = "root",
            text = "Główny temat",
            x = resources.displayMetrics.widthPixels / 2f,
            y = 200f,
            parentId = null
        )
        nodes.add(root)

        mindMapView.setRoot(root)
        mindMapView.onNodeSelected = { selectedNode = it }
        mindMapView.onNodeDoubleTap = { editNode(it) }

        binding.btnAdd.setOnClickListener {
            val text = binding.inputNode.text.toString().trim()
            if (text.isNotEmpty()) {
                val parent = selectedNode ?: root
                val newNode = MindMapNode(
                    id = "n_${System.currentTimeMillis()}",
                    text = text,
                    x = parent.x + 200,
                    y = parent.y + 200,
                    parentId = parent.id
                )
                parent.children.add(newNode)
                mindMapView.setRoot(root)
                binding.inputNode.text.clear()
            }
        }

        binding.btnExport.setOnClickListener { exportMindMap() }
        binding.btnImport.setOnClickListener { importMindMap() }
        binding.btnClear.setOnClickListener {
            nodes.clear()
            val newRoot = MindMapNode(
                id = "root",
                text = "Główny temat",
                x = resources.displayMetrics.widthPixels / 2f,
                y = 200f,
                parentId = null
            )
            nodes.add(newRoot)
            mindMapView.setRoot(newRoot)
            selectedNode = null
        }
    }

    private fun editNode(node: MindMapNode) {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_edit_node, null)
        val editText = dialogView.findViewById<android.widget.EditText>(R.id.nodeEdit)
        editText.setText(node.text)

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Edytuj węzeł")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                node.text = editText.text.toString()
                mindMapView.setRoot(nodes.first { it.id == "root" })
            }
            .setNegativeButton("Usuń") { _, _ ->
                node.parentId?.let { pid ->
                    val parent = nodes.find { it.id == pid }
                    parent?.children?.remove(node)
                }
                nodes.remove(node)
                mindMapView.setRoot(nodes.first { it.id == "root" })
            }
            .show()
    }

    private fun exportMindMap() {
        val root = nodes.firstOrNull { it.id == "root" } ?: return
        val json = kotlinx.serialization.json.Json.encodeToString(
            kotlinx.serialization.json.JsonArray(
                nodes.map { node ->
                    kotlinx.serialization.json.JsonObject(
                        mapOf(
                            "id" to kotlinx.serialization.json.JsonPrimitive(node.id),
                            "text" to kotlinx.serialization.json.JsonPrimitive(node.text),
                            "x" to kotlinx.serialization.json.JsonPrimitive(node.x),
                            "y" to kotlinx.serialization.json.JsonPrimitive(node.y),
                            "parentId" to kotlinx.serialization.json.JsonPrimitive(node.parentId ?: "")
                        )
                    )
                }
            )
        )
        saveToFile("mindmap_${System.currentTimeMillis()}.json", json)
    }

    private fun importMindMap() {
        val content = loadFromFile() ?: return
        try {
            val jsonArray = kotlinx.serialization.json.Json.parseToJsonElement(content)
                as kotlinx.serialization.json.JsonArray
            nodes.clear()
            jsonArray.forEach { element ->
                val obj = element as kotlinx.serialization.json.JsonObject
                nodes.add(
                    MindMapNode(
                        id = obj["id"]!!.toString().trim('"'),
                        text = obj["text"]!!.toString().trim('"'),
                        x = obj["x"]!!.toString().toFloat(),
                        y = obj["y"]!!.toString().toFloat(),
                        parentId = obj["parentId"]!!.toString().trim('"').ifEmpty { null }
                    )
                )
            }
            val root = nodes.first { it.id == "root" }
            root.children.clear()
            nodes.filter { it.parentId == "root" }.forEach { root.children.add(it) }
            mindMapView.setRoot(root)
            selectedNode = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveToFile(fileName: String, content: String) {
        try {
            requireContext().openFileOutput(fileName, android.content.Context.MODE_PRIVATE)
                .use { it.write(content.toByteArray()) }
            android.widget.Toast.makeText(requireContext(), "Zapisano: $fileName", android.widget.Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadFromFile(): String? {
        return try {
            requireContext().fileList().firstOrNull { it.endsWith(".json") }?.let { fileName ->
                requireContext().openFileInput(fileName).bufferedReader().use { it.readText() }
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
