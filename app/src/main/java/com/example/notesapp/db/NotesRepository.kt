package com.example.notesapp.db

import androidx.lifecycle.LiveData
import com.example.notesapp.db.Note
import com.example.notesapp.db.NoteDataBase
import com.example.notesapp.db.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesRepository(private val dataBase: NoteDataBase) {
    private lateinit var allNotes: LiveData<List<Note>>
    lateinit var note : Note
    init {
        var dao : NotesDao = dataBase.noteDao
        allNotes = dataBase.noteDao.allNotes()
    }
    fun getAllNotes() : LiveData<List<Note>>{
        return allNotes
    }
    suspend fun insertNote(note: Note){
        withContext(Dispatchers.IO){
            dataBase.noteDao.insert(note)
        }

    }
    suspend fun getNoteById(id : Int?) : Note {
        withContext(Dispatchers.IO){
             note = dataBase.noteDao.getNoteById(id)
        }
        return note
    }
    suspend fun deleteAll(){
        withContext(Dispatchers.IO){
            dataBase.noteDao.deleteAl()
        }
    }



}