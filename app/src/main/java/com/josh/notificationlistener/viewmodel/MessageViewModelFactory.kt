package com.josh.notificationlistener.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josh.notificationlistener.model.repository.MessageRepository

class MessageViewModelFactory(
    private val messageRepository: MessageRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageViewModel(messageRepository) as T
    }

}