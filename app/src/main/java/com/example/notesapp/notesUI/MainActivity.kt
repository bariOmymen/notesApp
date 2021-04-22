package com.example.notesapp.notesUI

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.R
import com.example.notesapp.notesUI.noteslist.NotesListFragment

class MainActivity : AppCompatActivity(), NavigationHost {
    private val channelID = "primary channel Id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        createChannel()


        val viewModel: NotesViewModel by lazy {
            val activity = requireNotNull(this.application) {
                "You can only access the viewModel after onActivityCreated()"
            }
            ViewModelProvider(this, NotesViewModel.Factory(activity))
                    .get(NotesViewModel::class.java)

        }


        supportFragmentManager.beginTransaction()
                .add(R.id.container, NotesListFragment::class.java,null)
                .addToBackStack("first fragment")
                .commit()
    }


    override fun navigateTO(fragment: Fragment, addToBackStack: Boolean) {
       val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.container,fragment,null)

             transaction.commit()
    }
    private fun createChannel(){
        val notificationManager : NotificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    channelID,
                    "notification",
                    NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.lightColor
            channel.description = "Notification From Notes App"
            notificationManager.createNotificationChannel(channel)
        }

    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1){

         navigateTO(NotesListFragment(),true)
        }
        super.onBackPressed()

    }
}