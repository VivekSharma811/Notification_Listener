package com.josh.notificationlistener

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationService().setListener(this)

//        btnCreateNotification.setOnClickListener {
//            var notificationManager : NotificationManager? = null
//            getSystemService(Context.NOTIFICATION_SERVICE)
//            var builder = NotificationCompat.Builder(this, "default")
//            builder.setContentTitle("My Notification")
//            builder.setContentText("Notification Example")
//            builder.setTicker("Example")
//            builder.setSmallIcon(R.drawable.ic_launcher_foreground)
//            builder.setAutoCancel(true)
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                var notificationChannel = NotificationChannel("10001", "Channel", NotificationManager.IMPORTANCE_HIGH)
//                builder.setChannelId("10001")
//                notificationManager?.createNotificationChannel(notificationChannel)
//            }
//            notificationManager?.notify((System.currentTimeMillis()).toInt(), builder.build())
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings) {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun setValue(packageName: String) {
        textView.append("\n ${packageName}")
    }
}
