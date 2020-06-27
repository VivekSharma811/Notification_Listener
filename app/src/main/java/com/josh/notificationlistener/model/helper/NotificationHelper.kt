package com.josh.notificationlistener.model.helper

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import com.josh.notificationlistener.R
import com.josh.notificationlistener.view.activity.BubbleActivity

class NotificationHelper(private val context: Context) {
    companion object {
        private const val CHANNEL_QUOTES = "quotes"
        private const val REQUEST_CONTENT = 1
        private const val REQUEST_BUBBLE = 2
        private const val NOTIFICATION_ID = 0
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    fun setUpNotificationChannels() {
        if (notificationManager?.getNotificationChannel(
                CHANNEL_QUOTES) == null) {
            val channel = NotificationChannel(
                CHANNEL_QUOTES,
                    "Messages",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Text"
            }
            notificationManager?.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(fromUser: Boolean) {

        val icon = createIcon()

        val person = createPerson(icon)

        val notification = createNotification(icon, person)

        // Create the Bubble's Metadata
        val bubbleMetaData = createBubbleMetadata(icon, fromUser)

        // Set the bubble metadata
        notification.setBubbleMetadata(bubbleMetaData)

        // Build and Display the Notification
        notificationManager?.notify(NOTIFICATION_ID, notification.build())
    }

    fun createPerson(icon: Icon): Person {
        return Person.Builder()
            .setName("Message")
            .setIcon(icon)
            .setBot(true)
            .setImportant(true)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createIcon(): Icon {
        return Icon.createWithResource(
            context,
            R.drawable.ic_launcher_foreground
        )
    }

    fun createBubbleMetadata(icon: Icon, fromUser: Boolean):
            Notification.BubbleMetadata {
        return Notification.BubbleMetadata.Builder()
            .setDesiredHeight(R.dimen.bubble_height)
            .setIcon(icon)
            .apply {
                if (fromUser) {
                    setAutoExpandBubble(true)
                    setSuppressNotification(true)
                }
            }
            .setIntent(
                createIntent(REQUEST_BUBBLE)
            )
            .build()
    }

    fun createIntent(requestCode: Int): PendingIntent {
        return PendingIntent.getActivity(
            context,
            requestCode,
            Intent(context, BubbleActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(icon: Icon, person: Person): Notification.Builder {
        return Notification.Builder(context, CHANNEL_QUOTES)
            .setContentTitle("Message")
            .setLargeIcon(icon)
            .setSmallIcon(icon)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setStyle(
                Notification.MessagingStyle(person)
                    .setGroupConversation(false)
                    .addMessage("Text", System.currentTimeMillis(), person)
            )
            .addPerson(person)
            .setShowWhen(true)
            .setContentIntent(createIntent(REQUEST_CONTENT))
    }
}