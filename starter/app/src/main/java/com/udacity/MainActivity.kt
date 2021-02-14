package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.udacity.util.cancelNotifications
import com.udacity.util.sendNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        val repository:RadioGroup = findViewById(R.id.radioGroup)

        custom_button.setOnClickListener {
            val checkId = repository.checkedRadioButtonId
            when (checkId) {
                -1 -> {
                    Toast.makeText(applicationContext, "Please select a repository before clicking download", Toast.LENGTH_LONG).show()
                    custom_button.buttonState = ButtonState.Clicked
                }
                R.id.glide_repositoryradioButton -> {
                    custom_button.buttonState = ButtonState.Loading
                    download("https://github.com/bumptech/glide/archive/master.zip")
                }
                R.id.currentprojectradioButton -> {
                    custom_button.buttonState = ButtonState.Loading
                    download("https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip")
                }
                R.id.retrofit_radioButton -> {
                    custom_button.buttonState = ButtonState.Loading
                    download("https://github.com/square/retrofit/archive/master.zip")

                }
            }

        }

        createChannel(
            getString(R.string.download_repository_channel_id),
            getString(R.string.download_notification_channel_name)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (downloadID == id) {
                Toast.makeText(context, "Download Completed here", Toast.LENGTH_LONG).show()
//                val query = DownloadManager.Query()
//                query.setFilterById(downloadID)
////                val cursor: Cursor = d
                custom_button.buttonState = ButtonState.Completed
                val notificationManager = ContextCompat.getSystemService(
                    context, NotificationManager::class.java
                ) as NotificationManager
                notificationManager.sendNotification("The download is now completed", context)
            }
        }
    }

    private fun download(url: String) {

        val notificationManager =
            ContextCompat.getSystemService(
                applicationContext,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancelNotifications()

        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }


    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )
                // TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(true)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = applicationContext.getString(R.string.download_notification_channel_description)

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create channel
    }

}
