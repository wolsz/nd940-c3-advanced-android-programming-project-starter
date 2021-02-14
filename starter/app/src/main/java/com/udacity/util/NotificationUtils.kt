package com.udacity.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.MainActivity
import com.udacity.R

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


    // TODO: Step 1.11 create intent
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    // TODO: Step 1.12 create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

// TODO: Step 2.0 add style
//    val eggImage = BitmapFactory.decodeResource(
//        applicationContext.resources,
//        R.drawable.cooked_egg
//    )
//    val bigPicStyle = NotificationCompat.BigPictureStyle()
//        .bigPicture(eggImage)
//        .bigLargeIcon(null)


    // TODO: Step 2.2 add snooze action
//    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
//    val snoozePendingIntent: PendingIntent =
//        PendingIntent.getBroadcast(applicationContext, REQUEST_CODE, snoozeIntent, FLAGS)

//    // TODO: Step 1.2 get an instance of NotificationCompat.Builder
//    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.download_repository_channel_id)
    )
//        // TODO: Step 1.3 set title, text and icon to builder
        .setSmallIcon(R.drawable.ic_download)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
//        // TODO: Step 1.13 set content intent
        .setContentIntent(contentPendingIntent)
//        // TODO: Step 2.1 add style to builder
//        .setStyle(bigPicStyle)
//        .setLargeIcon(eggImage)
//        // TODO: Step 2.3 add snooze action
//        .addAction(
//            R.drawable.egg_icon,
//            applicationContext.getString(R.string.snooze),
//            snoozePendingIntent
//        )
//        // TODO: Step 2.5 set priority
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
//
//    // TODO Step 1.4 call notify
//    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
//}
}
// TODO: Step 1.14 Cancel all notifications
/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}


