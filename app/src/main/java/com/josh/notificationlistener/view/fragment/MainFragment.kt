package com.josh.notificationlistener.view.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.josh.notificationlistener.R
import com.josh.notificationlistener.model.dataclass.Message
import com.josh.notificationlistener.model.helper.NotificationHelper
import com.josh.notificationlistener.service.NotificationService
import com.josh.notificationlistener.view.adapter.MessagesAdapter
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

    private val messagesAdapter = MessagesAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        NotificationService().setListener(this)

        messagesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messagesAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        val swipeToDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                messagesList.removeViewAt(position)
                messagesAdapter.notifyItemRemoved(position)
                //delete(position)
                deleteMessage(messagesAdapter.getMessageAtPosition(position))
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(messagesList)

        refreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            listen()
            refreshLayout.isRefreshing = false
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessageViewModel::class.java)

        listen()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerBubble()
        }
    }

    fun deleteMessage(message: Message) = launch {
        viewModel.delete(message)
    }

    fun delete(messageId : Int) = launch {
        //val count = viewModel.delete(messageId)
//        if(count != null) {
//            Toast.makeText(context, count.toString(), Toast.LENGTH_SHORT).show()
//        }
    }

    override fun setValue(message : Message) {
        persist(message)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showBubble(message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerBubble() {
        val notification = context?.let { NotificationHelper(it) }
        notification?.setUpNotificationChannels()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showBubble(message: Message) {
        val notification = context?.let { NotificationHelper(it) }
        notification?.showNotification(message, true)
    }

    private fun listen() = launch {
        viewModel.getMessages()
        viewModel.allMessages.observe(viewLifecycleOwner, Observer {
            if(it.size == 0) {
                defaultText.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
            messagesAdapter.updateMessages(it)
            progressBar.visibility = View.GONE
            defaultText.visibility = View.GONE
            messagesList.visibility = View.VISIBLE
        })
    }

    private fun persist(message: Message) = launch {
        viewModel.addMessage(message)
    }
}
