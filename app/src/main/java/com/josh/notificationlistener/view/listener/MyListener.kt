package com.josh.notificationlistener.view.listener

import com.josh.notificationlistener.model.dataclass.Message

interface MyListener {
    fun setValue(message: Message)
}