package com.josh.notificationlistener.model.repository

import androidx.lifecycle.LiveData
import com.josh.notificationlistener.model.dao.MessageDao
import com.josh.notificationlistener.model.dataclass.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepositoryImpl(
    private val messageDao: MessageDao
) : MessageRepository {
    override suspend fun addMessage(message: Message) {
        withContext(Dispatchers.IO) {
            messageDao.insert(message)
        }
    }

    override suspend fun getMessages(): LiveData<List<Message>> {
        return withContext(Dispatchers.IO) {
            return@withContext messageDao.getAllMessages()
        }
    }
}