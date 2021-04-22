package com.example.notesapp.notesUI.noteslist

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.notesUI.NavigationHost
import com.example.notesapp.notesUI.notesFragment.NotesFragment
import com.example.notesapp.notesUI.NotesViewModel

import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotesListFragment : Fragment() {
    private val channelID = "primary channel Id"

    val activityViewModel : NotesViewModel by activityViewModels<NotesViewModel>()


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
      //  val toolBar : Toolbar = view.findViewById(R.id.toolbar)
        //toolBar.setTitle("first fragment")





        return view
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.notesRecycler)
        val adapter = activity?.let { NotesAdapter(requireActivity(),activityViewModel) }
        val gridLayoutManager = GridLayoutManager(activity?.applicationContext,2)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = gridLayoutManager
        activity?.let { activityViewModel.allNotes().observe(
                it,
                Observer {
                    adapter?.setNoteList(it)
                    if (it != null) {

                        Log.i("not null", it.size.toString())
                       /// Log.i("not null", it.get(0).id.toString())
                    }
                },
        ) }

        val fab = view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener(View.OnClickListener {
            Toast.makeText(activity?.applicationContext,"tic",Toast.LENGTH_LONG).show()
            (activity as NavigationHost).navigateTO(NotesFragment(),false)
        })


        



       /* view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)*/
        }
    }
