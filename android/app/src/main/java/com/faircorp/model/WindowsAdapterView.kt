package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R

// an adapter must implement RecyclerView.Adapter which manage a RecyclerView.ViewHolder
class WindowAdapter(val listener: OnWindowSelectedListener) : RecyclerView.Adapter<WindowAdapter.WindowViewHolder>() {

    // WindowViewHolder is able to hold fields defined in layout
    inner class WindowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txt_item_window_name)
        val room: TextView = view.findViewById(R.id.txt_item_window_room)
        val status: TextView = view.findViewById(R.id.txt_item_status)
    }

    // Adapter has a mutable list to store elements to display
    private val items = mutableListOf<WindowDto>()

    // Update the list content (called when data will be ready)
    fun update(windows: List<WindowDto>) {
        items.clear()
        items.addAll(windows)
        notifyDataSetChanged()
    }

    // Method that returns the number of records
    override fun getItemCount(): Int = items.size

    // Method that initializes a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindowViewHolder {
        // inflate activity_windows_item.xml layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_windows_item, parent, false)
        // send it to ViewHolder constructor
        return WindowViewHolder(view)
    }

    // Method that define what to do when position in the list changes
    override fun onBindViewHolder(holder: WindowViewHolder, position: Int) {  // (7)
        val window = items[position]
        holder.apply {
            name.text = window.name
            status.text = window.status.toString()
            room.text = window.room.name

            // listener is called when someone item is clicked
            itemView.setOnClickListener {listener.onWindowSelected(window.id) }
        }
    }

    // Clear OnClickListener when a view holder is recycled to prevent memory leaks
    override fun onViewRecycled(holder: WindowViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }
    }



}