package com.example.notesapp.notesUI.noteslist

import android.app.Activity
import com.example.notesapp.db.Note

interface OnClickListner {



    fun OnClick(activity: Activity,note : Note)
}