package com.example.notesadv.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int=0,
    val Title:String,
    val content:String,
    val timeStamp:Long=System.currentTimeMillis()
) {
}