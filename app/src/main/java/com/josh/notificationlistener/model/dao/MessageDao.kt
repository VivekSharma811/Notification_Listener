package com.josh.notificationlistener.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.josh.notificationlistener.model.dataclass.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg message: Message)

    @Query("SELECT * FROM messages")
    fun getAllMessages() : LiveData<List<Message>>

    @Query("SELECT * FROM messages WHERE uuid= :messageId")
    fun getMessage(messageId : Int) : LiveData<Message>

    @Query("DELETE FROM messages WHERE uuid= :messageId")
    fun deleteMessage(messageId: Int)

    @Query("DELETE FROM messages")
    fun deleteAll()

}