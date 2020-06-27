package com.josh.notificationlistener.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.josh.notificationlistener.model.dao.MessageDao
import com.josh.notificationlistener.model.dataclass.Message

@Database(
    entities = arrayOf(Message::class),
    version = 1
)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao() : MessageDao

    companion object {
        @Volatile private var instance : MessageDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                )
                    .also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MessageDatabase::class.java,
            "message.db")
            .build()

    }

}