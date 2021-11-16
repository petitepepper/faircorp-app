package com.faircorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.ApiServices
import com.faircorp.model.OnWindowSelectedListener
import com.faircorp.model.WindowAdapter
import com.faircorp.model.WindowService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WindowsActivity : BasicActivity(), OnWindowSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windows)

        // Find the recycler view defined in layout by its id list_windows
        val recyclerView = findViewById<RecyclerView>(R.id.list_windows)
        // Create adapter and define recycler view properties
        val adapter = WindowAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        // Version1.1: Update adapter dat ==> works well
//        val windowService = WindowService()
//        adapter.update(windowService.findAll())

        // Version2.1: Use Remote API    ==> [ERROR] NO TOAST ERROR NOTIFICATION, EMPTY WINDOW LIST
        // Manage successes and failures
        lifecycleScope.launch(context = Dispatchers.IO) { // (1)
            runCatching { ApiServices().windowsApiService.findAll().execute() } // (2)
                .onSuccess {
                    withContext(context = Dispatchers.Main) { // (3)
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) { // (3)
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    // Use RecyclerView, click on item
    override fun onWindowSelected(id: Long) {
        val intent = Intent(this, WindowActivity::class.java).putExtra(WINDOW_NAME_PARAM, id)
        startActivity(intent)
    }

}
