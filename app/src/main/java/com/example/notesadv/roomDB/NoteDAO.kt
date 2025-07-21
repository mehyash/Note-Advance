package com.example.notesadv.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDAO {
    @Insert
    fun insert(note: NoteEntity)
    @Update
    fun update(note: NoteEntity)
    @Delete
    fun delete(note: NoteEntity)
    @Query("SELECT * FROM Notes")
    fun getQuery(): List<NoteEntity>

}