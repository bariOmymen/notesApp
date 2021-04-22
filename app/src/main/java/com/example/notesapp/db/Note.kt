package com.example.notesapp.db

import android.telecom.Call
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note table")
 class Note (title : String,details: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "note title")
    var title: String = title

    @ColumnInfo(name = "note details")
    var details: String = details
 }

/*(@PrimaryKey var id: Int = 0,

             @ColumnInfo(name = "note title")
             var title: String = "title",
             @ColumnInfo(name = "note details")
             var details: String = "details")
*/
