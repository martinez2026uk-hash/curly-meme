package com.rmagent.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.rmagent.app.databinding.FragmentAgentBinding

class AgentFragment : Fragment() {

    private var _binding: FragmentAgentBinding? = null
    private val binding get() = _binding!!

    private val agent = AgentRM()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("rmagent_prefs", android.content.Context.MODE_PRIVATE)
        binding.dailyNote.setText(prefs.getString("daily_note", ""))

        binding.btnAsk.setOnClickListener {
            val question = binding.agentInput.text.toString().trim()
            if (question.isNotEmpty()) {
                binding.agentResponse.text = "Agent myśli…"
                binding.btnAsk.isEnabled = false
                binding.agentInput.setText("")
                Thread {
                    val answer = agent.ask(question)
                    activity?.runOnUiThread {
                        binding.agentResponse.text = answer
                        binding.btnAsk.isEnabled = true
                    }
                }.start()
            }
        }

        binding.btnSaveNote.setOnClickListener {
            val note = binding.dailyNote.text.toString()
            prefs.edit().putString("daily_note", note).apply()
            Toast.makeText(requireContext(), "Zapisano dzienną notatkę", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
