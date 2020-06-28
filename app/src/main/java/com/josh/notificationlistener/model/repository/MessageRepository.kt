package com.josh.notificationlistener.model.repository

import androidx.lifecycle.LiveData
import com.josh.notificationlistener.model.dataclass.Message

interface MessageRepository {

    suspend fun addMessage(message : Message)

    suspend fun getMessages() : LiveData<List<Message>>

    suspend fun deleteMessage(message: Message) : Int

}