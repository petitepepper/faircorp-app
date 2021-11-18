package com.faircorp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.model.ApiServices
import com.faircorp.model.OnWindowSelectedListener
import com.faircorp.model.WindowAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : BasicActivity(), OnWindowSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        // add a button to go back on MainActivity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the id of window clicked in WindowsActivity
        val roomId = intent.getLongExtra(ROOM_NAME_PARAM, 0)

        // Consult information of this room by using its id
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().roomsApiService.findById(roomId).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        val room = it.body()
                        if (room != null) {
                            // show room name, and temperature information
                            findViewById<TextView>(R.id.txt_room_name).text =
                                room.name
                            findViewById<TextView>(R.id.txt_room_floor).text =
                                room.floor.toString()
                            findViewById<TextView>(R.id.txt_room_current_temperature).text =
                                room.currentTemperature?.toString()
                            findViewById<TextView>(R.id.txt_room_target_temperature).text =
                                room.targetTemperature?.toString()
                            findViewById<TextView>(R.id.txt_room_building).text =
                                room.buildingName
                            loadWindows(roomId)
                        }
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on room loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }

    }


    private fun loadWindows(roomId:Long){
        val recyclerView = findViewById<RecyclerView>(R.id.list_windows_in_room)
        val adapter = WindowAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        //Find all windows and show information of each
        lifecycleScope.launch(context = Dispatchers.IO) {
            //TODO: Should have a "findWindowsInRoom" api service!
            runCatching { ApiServices().windowsApiService.findAll().execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on windows loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }

    override fun onWindowSelected(id: Long) {
        val intent = Intent(this, WindowActivity::class.java).putExtra(WINDOW_NAME_PARAM, id)
        startActivity(intent)
    }
}


