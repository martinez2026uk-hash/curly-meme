package com.rmagent.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rmagent.app.databinding.ActivityMainBinding

class MainActivity : androidx.appcompat.app.AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: PagerAdapter
    private val fragments = mutableListOf<Fragment>()
    private val tabTitles = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        fragments.add(BrowserFragment())
        fragments.add(MindMapFragment())
        fragments.add(AgentFragment())
        fragments.add(NotesFragment())

        tabTitles.addAll(listOf("Przeglądarka", "Mind Map", "Agent RM", "Notatki"))

        adapter = PagerAdapter(this, fragments)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                showLoginDialog()
                true
            }
            R.id.action_export -> {
                exportAllData()
                true
            }
            R.id.action_import -> {
                importAllData()
                true
            }
            R.id.action_add_tab -> {
                addNewTab()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoginDialog() {
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_login, null)
        val userEdit = dialogView.findViewById<EditText>(R.id.loginUser)
        val passEdit = dialogView.findViewById<EditText>(R.id.loginPass)

        AlertDialog.Builder(this)
            .setTitle("Zaloguj się")
            .setView(dialogView)
            .setPositiveButton("Zaloguj") { _, _ ->
                val user = userEdit.text.toString()
                val pass = passEdit.text.toString()
                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    Toast.makeText(this, "Zalogowano: $user", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Wprowadź dane", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Anuluj", null)
            .show()
    }

    private fun exportAllData() {
        val prefs = getSharedPreferences("rmagent_data", MODE_PRIVATE)
        val allData = prefs.all.toString()
        try {
            openFileOutput("rmagent_export_${System.currentTimeMillis()}.json", MODE_PRIVATE)
                .use { it.write(allData.toByteArray()) }
            Toast.makeText(this, "Wyeksportowano dane", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun importAllData() {
        try {
            val files = fileList().filter { it.startsWith("rmagent_export") }
            if (files.isNotEmpty()) {
                val content = openFileInput(files.last()).bufferedReader().use { it.readText() }
                Toast.makeText(this, "Zaimportowano dane", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addNewTab() {
        if (fragments.size < 5) {
            fragments.add(BrowserFragment())
            tabTitles.add("Strona ${fragments.size}")
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(fragments.size - 1, true)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        } else {
            Toast.makeText(this, "Maksymalnie 5 kart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class PagerAdapter(
    activity: androidx.fragment.app.FragmentActivity,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}
