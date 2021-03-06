package com.josh.notificationlistener.view.activity

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.josh.notificationlistener.R
import com.josh.notificationlistener.databinding.FragmentMainBinding.bind
import com.josh.notificationlistener.model.dataclass.Message
import com.josh.notificationlistener.model.helper.NotificationHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allow.setOnClickListener {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enableBubble()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun enableBubble() {
        val notification = NotificationHelper(application)
        notification.setUpNotificationChannels()
        notification.showNotification(null, true)
    }
}
