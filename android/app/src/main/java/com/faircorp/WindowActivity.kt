package com.faircorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

//const val WINDOW_NAME_PARAM = "com.faircorp.windowname.attribute"

class WindowActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)

        // activate option in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val param = intent.getStringExtra(WINDOW_NAME_PARAM)
        val windowName = findViewById<TextView>(R.id.tv_window_name)
        windowName.text = param
    }
}