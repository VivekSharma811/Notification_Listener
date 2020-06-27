package com.josh.notificationlistener.service

import android.app.Notification
import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.josh.notificationlistener.model.dataclass.Message
import com.josh.notificationlistener.view.listener.MyListener
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class NotificationService : NotificationListenerService() {
    private val TAG = this.javaClass.simpleName
    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        var myListener: MyListener? = null
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.i(TAG, "********** onNotificationPosted")
        Log.i(
            TAG,
            "ID :" + sbn.id + " \t " + sbn.notification.tickerText + " \t " + sbn.packageName
        )
        try {
            if(sbn.packageName.equals("com.whatsapp")) {
                myListener!!.setValue(Message(sbn.notification.extras.get("android.title").toString(), sbn.notification.extras.get("android.text").toString(), SimpleDateFormat("HH:mm, dd-mm-yy").format(Date()).toString()))
            }
        } catch (e : Exception) {
            Log.e(TAG, e.message)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.i(TAG, "********** onNotificationRemoved")
        Log.i(
            TAG,
            "ID :" + sbn.id + " \t " + sbn.notification.tickerText + " \t " + sbn.packageName
        )
        //myListener!!.setValue("Remove: " + sbn.packageName)
    }

    fun setListener(myListener: MyListener?) {
        Companion.myListener = myListener ;
    }

}