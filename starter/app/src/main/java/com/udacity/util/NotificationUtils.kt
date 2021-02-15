package com.udacity.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.udacity.DetailActivity
import com.udacity.R

// Notification ID.
private val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context,
    description: String,
    status: String
) {


    val contentIntent = Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra(DetailActivity.FILENAME, description)
        putExtra(DetailActivity.STATUS, status)
    }

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    val downloadImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.downloadsicon
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(downloadImage)
        .bigLargeIcon(null)

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.download_repository_channel_id)
    )

        .setSmallIcon(R.drawable.downloadsicon)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setStyle(bigPicStyle)
        .setLargeIcon(downloadImage)

        .addAction(
            R.drawable.downloadsicon,
            applicationContext.getString(R.string.action_button_text),
            contentPendingIntent
        )

        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())

}


fun NotificationManager.cancelNotifications() {
    cancelAll()
}


