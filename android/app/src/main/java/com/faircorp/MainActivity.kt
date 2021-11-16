package com.faircorp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.faircorp.model.OnWindowSelectedListener


const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"

class MainActivity : BasicActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Version1.0: Set the name given and jump to WindowActivity
//    fun openWindow(view: View) {
//        // Extract value filled in editext identified with txt_window_name id
//        val windowName = findViewById<EditText>(R.id.tv_main_window_name).text.toString()
//
//        // Do something in response to button
//        val intent = Intent(this, WindowActivity::class.java).apply {
//            putExtra(WINDOW_NAME_PARAM, windowName)
//        }
//        startActivity(intent)
//    }

}