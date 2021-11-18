package com.faircorp.model

data class RoomDto(val id: Long,
                   val name: String,
                   val floor: Int,
                   val currentTemperature: Double?,
                   val targetTemperature: Double?,
                   val buildingId: Long,
                   val buildingName: String? )