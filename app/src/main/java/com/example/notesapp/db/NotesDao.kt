package com.example.notesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.db.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note : Note)
    @Delete
    fun delete(note: Note)
    @Query("SELECT * FROM `note table`")
    fun allNotes(): LiveData<List<Note>>
    @Query("SELECT * FROM `note table` WHERE id =:id")
    fun getNoteById(id:Int?) : Note
    @Query("DELETE FROM `note table` ")
    fun deleteAl()

}