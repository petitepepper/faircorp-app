package com.faircorp.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faircorp.R

class RoomsAdapter (val listener: OnRoomSelectedListener): RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txt_item_room_name)
        val floor: TextView = view.findViewById(R.id.txt_item_room_floor)
        val building: TextView = view.findViewById(R.id.txt_item_room_building)
    }

    // Adapter has a mutable list to store elements to display
    private val roomItems = mutableListOf<RoomDto>()


    // Method used to update the list content. This method will be called when data is ready
    fun update(rooms: List<RoomDto>) {
        roomItems.clear()
        roomItems.addAll(rooms)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = roomItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_rooms_item, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomsAdapter.RoomViewHolder, position: Int) {
        val room = roomItems[position]
        holder.apply {
            name.text = room.name
            floor.text = room.floor.toString()
            building.text = room.buildingName
            itemView.setOnClickListener { listener.onRoomSelected(room.id) }
        }
    }

    override fun onViewRecycled(holder: RoomViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            itemView.setOnClickListener(null)
        }
    }



}