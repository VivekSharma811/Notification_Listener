package com.josh.notificationlistener

import android.content.Context
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import java.lang.Exception

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
            myListener!!.setValue("Post: " + sbn.packageName)
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
        myListener!!.setValue("Remove: " + sbn.packageName)
    }

    fun setListener(myListener: MyListener?) {
        NotificationService.myListener = myListener ;
    }

}