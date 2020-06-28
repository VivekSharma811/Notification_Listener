package com.josh.notificationlistener.viewmodel

import android.app.Notification
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.josh.notificationlistener.model.dataclass.Message
import com.josh.notificationlistener.model.repository.MessageRepository

class MessageViewModel(
    private val messageRepository: MessageRepository
) : ViewModel() {

    lateinit var allMessages : LiveData<List<Message>>

    suspend fun addMessage(message : Message) {
        messageRepository.addMessage(message)
    }

    suspend fun getMessages() {
        allMessages = messageRepository.getMessages()
    }

    suspend fun delete(message: Message) : Int {
        return messageRepository.deleteMessage(message)
    }

}