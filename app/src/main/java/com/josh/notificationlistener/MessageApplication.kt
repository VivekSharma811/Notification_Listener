package com.josh.notificationlistener

import android.app.Application
import com.josh.notificationlistener.model.db.MessageDatabase
import com.josh.notificationlistener.model.repository.MessageRepository
import com.josh.notificationlistener.model.repository.MessageRepositoryImpl
import com.josh.notificationlistener.viewmodel.MessageViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MessageApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        import(androidXModule(this@MessageApplication))

        bind() from singleton { MessageDatabase(instance()) }
        bind() from singleton { instance<MessageDatabase>().messageDao() }

        bind<MessageRepository>() with singleton { MessageRepositoryImpl(instance()) }

        bind() from provider { MessageViewModelFactory(instance()) }

    }

}