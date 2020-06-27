package com.josh.notificationlistener.view.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.josh.notificationlistener.R
import com.josh.notificationlistener.model.dataclass.Message
import com.josh.notificationlistener.service.NotificationService
import com.josh.notificationlistener.view.listener.MyListener
import com.josh.notificationlistener.viewmodel.MessageViewModel
import com.josh.notificationlistener.viewmodel.MessageViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainFragment : ScopedFragment(), KodeinAware, MyListener {

    override val kodein by closestKodein()

    private val viewModelFactory : MessageViewModelFactory by instance()
    private lateinit var viewModel : MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NotificationService().setListener(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessageViewModel::class.java)

        listen()
    }

    override fun setValue(message : Message) {
        persist(message)
    }

    private fun listen() = launch {
        viewModel.getMessages()
        viewModel.allMessages.observe(viewLifecycleOwner, Observer {
            textView.append(it.toString())
        })
    }

    private fun persist(message: Message) = launch {
        viewModel.addMessage(message)
    }

}
