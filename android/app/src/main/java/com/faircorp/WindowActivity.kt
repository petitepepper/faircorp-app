package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.faircorp.model.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WindowActivity : BasicActivity() {


    // Use "var" to make is available for switchStatus function
    private var windowId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        // add a button to go back on MainActivity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the id of window clicked in WindowsActivity
        windowId = intent.getLongExtra(WINDOW_NAME_PARAM, 0)

        // Consult information of this window by using its id
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().windowsApiService.findById(windowId).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        val window = it.body()
                        if (window != null) {
                            // show the window name and window status
                            findViewById<TextView>(R.id.txt_window_name).text = window.name
                            findViewById<TextView>(R.id.txt_window_status).text = window.windowStatus.toString()

                            // get Room information by using roomId
                            val roomId = window.roomId
                            lifecycleScope.launch(context = Dispatchers.IO) {
                                runCatching { ApiServices().roomsApiService.findById(roomId).execute() }
                                    .onSuccess {
                                        withContext(context = Dispatchers.Main) {
                                            val room = it.body()
                                            if(room != null){
                                                // show room name, and temperature information
                                                findViewById<TextView>(R.id.txt_window_room_name).text =
                                                    room.name
                                                findViewById<TextView>(R.id.txt_window_current_temperature).text =
                                                    room.currentTemperature?.toString()
                                                findViewById<TextView>(R.id.txt_window_target_temperature).text =
                                                    room.targetTemperature?.toString()
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


    //Button function to change window status
    fun switchWindowStatus(view: View){
        // Switch WindowStatus
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices().windowsApiService.switchStatus(windowId).execute() }
                .onSuccess {
                    Toast.makeText(applicationContext, "WindowId: $windowId", Toast.LENGTH_SHORT).show()
                    withContext(context = Dispatchers.Main) {
                        View.inflate(applicationContext,R.layout.activity_window,null)
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "WindowStatus change failed:  $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }



}