package com.internship.dwd.model

data class Room(
    val rowIndex: Int,
    val columnIndex: Int,
    val content: RoomContent? = null
)