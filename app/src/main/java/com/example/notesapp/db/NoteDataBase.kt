package com.example.notesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class],version = 2 ,exportSchema = false)
abstract class NoteDataBase : RoomDatabase(){

    abstract val noteDao : NotesDao
    companion object{
        @Volatile
        private var INSTANCE: NoteDataBase? = null
        fun getInstance(context: Context) : NoteDataBase {
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,
                            NoteDataBase::class.java,
                            "Notes Database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}