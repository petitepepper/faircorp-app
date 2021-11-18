package com.faircorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomActivity : BasicActivity() {
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
                            //TODO: Add window list
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
}


