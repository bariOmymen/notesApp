package com.example.notesapp.notesUI

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.work.*
import com.example.notesapp.WorkType
import com.example.notesapp.db.NoteDataBase.Companion.getInstance
import com.example.notesapp.db.Note
import com.example.notesapp.db.NotesRepository
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    var minutes : Duration? = null
    var hours : String = ""

    var currentNote : Note? = null

     val notesRepository = NotesRepository(getInstance(application))
    lateinit var note : Note
    fun allNotes() : LiveData<List<Note>>{

       return notesRepository.getAllNotes()
    }
     fun insertNote(note: Note){
        viewModelScope.launch {  notesRepository.insertNote(note)
            Log.i(note.title,note.details)
    }

     }
    fun getNoteById(id : Int?){
        viewModelScope.launch { note = notesRepository.getNoteById(id)
            Log.i(note.id.toString(),note.details)

       }


    }
    fun deleteAll(){
        viewModelScope.launch { notesRepository.deleteAll() }
    }
    fun makeNotification(minutes : Long,title: String){

        val workRequest : WorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(makeData(title))
                .setInitialDelay(minutes, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(getApplication())
                .enqueue(workRequest)
    }
    fun makeRepeatingNotification(minutes: Long,title: String){
        val  workRequest : WorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(minutes, TimeUnit.MINUTES)
                .setInitialDelay(minutes,TimeUnit.MINUTES)
                .setInputData(makeData(title))
                .build()
        WorkManager.getInstance(getApplication())
                .enqueue(workRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun  calculateTime(time:String,workType: WorkType,title: String): Duration{
        time.toMutableList()
        val localTime = LocalTime.parse(time).hour

        val localTimeMin = LocalTime.parse(time).minute

        val now = LocalTime.now()

        var nowAfter = LocalTime.now().plusMinutes(localTimeMin.toLong()).plusHours(localTime.toLong())

        var duration = Duration.between(now, nowAfter)
        minutes = duration.minusHours(localTime.toLong())
         hours = duration.toHours().toString()

        val alarm = duration.toMinutes()
        when(workType){
            WorkType.PERIODIC -> makeRepeatingNotification(alarm,title)
            WorkType.ONETIME ->  makeNotification(alarm,title)
        }
        return duration

    }
    private fun makeData(title: String): Data {
        val data: Data.Builder = Data.Builder()
        data.putString("title",title)
        return data.build()
    }




    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NotesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }




}