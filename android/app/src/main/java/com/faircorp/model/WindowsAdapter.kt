package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R

class WindowAdapter (val listener: OnWindowSelectedListener): RecyclerView.Adapter<WindowAdapter.WindowViewHolder>() { // (1)

    inner class WindowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txt_item_window_name)
        val room: TextView = view.findViewById(R.id.txt_item_window_room)
        val status: TextView = view.findViewById(R.id.txt_item_window_status)
    }

    // Adapter has a mutable list to store elements to display
    private val windowItems = mutableListOf<WindowDto>()


    // Method used to update the list content. This method will be called when data is ready
    fun update(windows: List<WindowDto>) {
        windowItems.clear()
        windowItems.addAll(windows)
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int = windowItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_windows_item, parent, false)
        return WindowViewHolder(view)
    }

    override fun onBindViewHolder(holder: WindowViewHolder, position: Int) {
        val window = windowItems[position]
        holder.apply {
            name.text = window.name
            status.text = window.windowStatus.toString()
            room.text = window.roomName
            itemView.setOnClickListener { listener.onWindowSelected(window.id) }
        }
    }

    override fun onViewRecycled(holder: WindowViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }
    }





}