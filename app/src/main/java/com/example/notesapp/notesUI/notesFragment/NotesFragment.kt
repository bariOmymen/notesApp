package com.example.notesapp.notesUI.notesFragment

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.notesapp.*
import com.example.notesapp.db.Note
import com.example.notesapp.notesUI.NavigationHost
import com.example.notesapp.notesUI.NotesViewModel
import com.example.notesapp.notesUI.noteslist.NotesListFragment
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NotesFragment : Fragment() {

    val activityViewModel : NotesViewModel by activityViewModels<NotesViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val toolbar = view.findViewById<Toolbar>(R.id.toolbar2)
        toolbar2.setNavigationIcon(R.drawable.ic_action_back)
        toolbar2.setNavigationOnClickListener(View.OnClickListener {
            (activity as NavigationHost).navigateTO(NotesListFragment(),false)
             Log.i("navigation worked","here")
        })

       // toolbar.setNavigationOnClickListener(View.OnClickListener {
           // (activity as NavigationHost).navigateTO(FirstFragment(),false)
          //  Log.i("navigation worked","here")
        //})



       /* val appCompatActivity : AppCompatActivity = activity as AppCompatActivity
        if (appCompatActivity!=null){
            appCompatActivity.setSupportActionBar(toolbar)
        }*/

        var title = view.findViewById<EditText>(R.id.editText)
        var detailsText = view.findViewById<EditText>(R.id.detailsTexts)

        if(activityViewModel.currentNote != null){
            title.setText(activityViewModel.currentNote!!.title)
            detailsText.setText(activityViewModel.currentNote!!.details)
        }

        var current =0

        val saveIcon = view.findViewById<ImageView>(R.id.saveIcon).setOnClickListener(View.OnClickListener {


            current++
            var note = Note(title.text.toString(), detailsText.text.toString())
            activityViewModel.insertNote(note)

            Log.i("current", current.toString())
            Toast.makeText(activity?.applicationContext, note.title, Toast.LENGTH_LONG).show()

        })
        val notifyIcon = view.findViewById<ImageView>(R.id.notifyIcon).setOnClickListener(View.OnClickListener {

            context?.let { it1 ->
                AlertDialog.Builder(it1).setIcon(R.drawable.ic_action_name)
                        .setTitle("repeat?")
                        .setPositiveButton("repeat", DialogInterface.OnClickListener { dialog, which ->
                            val cal = Calendar.getInstance()
                            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                                cal.set(Calendar.HOUR, hour)
                                cal.set(Calendar.MINUTE, minute)
                                val time = SimpleDateFormat("HH:mm").format(cal.time)

                                calculateTime(time, WorkType.PERIODIC)



                            }
                            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
                        }).setNeutralButton("just once", DialogInterface.OnClickListener { dialog, which ->
                            val cal = Calendar.getInstance()
                            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                                cal.set(Calendar.HOUR_OF_DAY, hour)
                                cal.set(Calendar.MINUTE, minute)
                               var time = SimpleDateFormat("HH:mm").format(cal.time)

                                calculateTime(time, WorkType.ONETIME)

                            }
                            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
                        }).show()
            }

        })




        }

   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
       return super.onCreateOptionsMenu(menu, inflater)

    }*/

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }*/


    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateTime(time:String,workType: WorkType){
        activityViewModel.calculateTime(time,workType,activityViewModel.currentNote!!.title)
        Toast.makeText(context,"duaration is ${activityViewModel.hours} and ${activityViewModel.minutes?.toMinutes()} minutes ", Toast.LENGTH_LONG).show()




        /* time.toMutableList()
         val localTime = LocalTime.parse(time).hour
         Log.i("localTime",localTime.toString())
         val localTimeMin = LocalTime.parse(time).minute

         Log.i("localTimeMin",localTimeMin.toString())

         val now = LocalTime.now()
         Log.i("now",now.toString())
         // var nowAfter = LocalTime.now().plusHours(localTime.toLong()).plusMinutes(localTimeMin.toLong())
         var nowAfter = LocalTime.now().plusMinutes(localTimeMin.toLong()).plusHours(localTime.toLong())
         Log.i("nowAfter",nowAfter.toString())
         var duration = Duration.between(now, nowAfter)
        var minutes = duration.minusHours(localTime.toLong())
         var hours = duration.toHours().toString()
         Toast.makeText(context,"duaration is $hours and ${minutes.toMinutes()} minutes ",Toast.LENGTH_LONG).show()
         val alarm = duration.toMinutes()
         when(workType){
              WorkType.PERIODIC -> activityViewModel.makeRepeatingNotification(alarm)
              WorkType.ONETIME ->  activityViewModel.makeNotification(alarm)*/
        }

    }




