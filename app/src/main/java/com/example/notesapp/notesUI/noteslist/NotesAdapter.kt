package com.example.notesapp.notesUI.noteslist

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.db.Note
import com.example.notesapp.notesUI.NavigationHost
import com.example.notesapp.notesUI.notesFragment.NotesFragment
import com.example.notesapp.notesUI.NotesViewModel

class NotesAdapter(activity: Activity,viewModel: NotesViewModel) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(), OnClickListner {
    var notesList =  listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val viewModel : NotesViewModel = viewModel


    val activity : Activity
    init {
        this.activity=activity
    }



    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.title.text = notesList.get(position).title
        holder.detail.text = notesList.get(position).details
        holder.noteView.setOnClickListener { view -> this.OnClick(activity,notesList.get(position)) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesViewHolder {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.item_sample,parent,false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title : TextView = itemView.findViewById(R.id.titleView)
        var detail : TextView = itemView.findViewById(R.id.detailView)
        val noteView : CardView = itemView.findViewById(R.id.noteCard)
    }

    fun setNoteList(list : List<Note>){
        notesList=list
        Log.i("worked","here")
        notifyDataSetChanged()
    }

    override fun OnClick(activity: Activity, note : Note) {
        (activity as NavigationHost).navigateTO(NotesFragment(),false)
        if(note!=null){
            Log.i("note is note null",note.title)
        }
        viewModel.currentNote = note

    }


    /* override fun OnClick(v: View, position: Int, context: Context) {
         val fragment = SecondFragment()


     }*/
}