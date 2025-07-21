package com.example.notesadv.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDB: RoomDatabase() {
    abstract fun getNoteDAO(): NoteDAO
     companion object{
         @Volatile
         var instance: NoteDB?=null
         fun getInstance(context: Context): NoteDB {
             return instance ?: synchronized(this){
                 val Instance=Room.databaseBuilder(
                     context.applicationContext,
                     NoteDB::class.java,
                     "note_db"
                 ).build()
                 instance =Instance
                 Instance
             }
         }
     }
}