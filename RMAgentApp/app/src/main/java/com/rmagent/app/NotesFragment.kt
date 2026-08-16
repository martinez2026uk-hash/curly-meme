package com.rmagent.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rmagent.app.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapter
    private val notes = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter(notes) { note ->
            val text = "${note.title}\n\n${note.body}"
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(sendIntent, "Udostępnij notatkę"))
        }

        binding.notesRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecycler.adapter = adapter

        binding.btnAddNote.setOnClickListener {
            val text = binding.noteInput.text.toString().trim()
            if (text.isNotEmpty()) {
                notes.add(0, Note(title = text.take(30), body = text))
                adapter.notifyItemInserted(0)
                binding.noteInput.text.clear()
                binding.notesRecycler.scrollToPosition(0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
