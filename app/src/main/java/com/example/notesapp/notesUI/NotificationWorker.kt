package com.example.notesapp.notesUI

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.notesapp.R

class NotificationWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    private val ACTION_UPDATE_NOTIFICATION =
    "com.example.notesapp.notesUI.ACTION_UPDATE_NOTIFICATION";
    private val channelID = "primary channel Id"
    override fun doWork(): Result {
       val notificationManager : NotificationManager = getSystemService(applicationContext,NotificationManager::class.java) as NotificationManager
        notificationManager?.notify(0, getNotificationBuilder(applicationContext).build())
        return Result.success()

    }


    private fun getNotificationBuilder(context: Context): NotificationCompat.Builder{
        val intent = Intent(ACTION_UPDATE_NOTIFICATION)
        var notificationBuilder = NotificationCompat.Builder(context, channelID)
        notificationBuilder.setAutoCancel(true)
                .setContentTitle("you got notified")
                .setSmallIcon(R.drawable.ic_action_name)
        val contentIntent = Intent(context, MainActivity::class.java)
                val pendeingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendeingIntent)
        Log.i("notification", "builder was created")
        return notificationBuilder

    }
}