package com.josh.notificationlistener.service

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import com.josh.notificationlistener.model.dataclass.Message
import com.josh.notificationlistener.model.helper.NotificationHelper
import com.josh.notificationlistener.view.listener.MyListener
import java.text.SimpleDateFormat
import java.util.*

class NotificationService : NotificationListenerService() {
    private val TAG = this.javaClass.simpleName
    var context: Context? = null

    companion object {
        var myListener: MyListener? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if(sbn.packageName.equals("com.whatsapp")) {
            if(sbn.notification.flags and Notification.FLAG_GROUP_SUMMARY !== 0) {
                return
            }
            if(sbn.notification.extras.get("android.title").toString().equals("WhatsApp")) {
                return
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enableBubble()
            }
            myListener!!.setValue(Message(sbn.notification.extras.get("android.title").toString(), sbn.notification.extras.get("android.text").toString(), SimpleDateFormat("HH:mm, dd-MM-yy").format(Date()).toString()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun enableBubble() {
        val notification = NotificationHelper(application)
        notification.setUpNotificationChannels()
        notification.showNotification(null, true)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        //myListener!!.setValue("Remove: " + sbn.packageName)
    }

    fun setListener(myListener: MyListener?) {
        Companion.myListener = myListener ;
    }
}