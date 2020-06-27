package com.josh.notificationlistener.model.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    val user : String,
    val message : String,
    val timestamp : String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}