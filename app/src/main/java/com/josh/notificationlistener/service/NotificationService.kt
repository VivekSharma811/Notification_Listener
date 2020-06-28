package com.josh.notificationlistener.service

import android.app.Notification
import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.josh.notificationlistener.model.dataclass.Message
import com.josh.notificationlistener.view.listener.MyListener
import java.text.SimpleDateFormat
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
        if(sbn.packageName.equals("com.whatsapp")) {
            if(sbn.notification.flags and Notification.FLAG_GROUP_SUMMARY !== 0) {
                return
            }
            if(sbn.notification.extras.get("android.title").toString().equals("WhatsApp")) {
                return
            }
            myListener!!.setValue(Message(sbn.notification.extras.get("android.title").toString(), sbn.notification.extras.get("android.text").toString(), SimpleDateFormat("HH:mm, dd-MM-yy").format(Date()).toString()))
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        //myListener!!.setValue("Remove: " + sbn.packageName)
    }

    fun setListener(myListener: MyListener?) {
        Companion.myListener = myListener ;
    }

}