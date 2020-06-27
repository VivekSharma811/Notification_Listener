package com.josh.notificationlistener.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.josh.notificationlistener.R
import com.josh.notificationlistener.databinding.ItemMessageBinding
import com.josh.notificationlistener.model.dataclass.Message

class MessagesAdapter(var messageList : ArrayList<Message>)  : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    fun updateMessages(newMessages : List<Message>) {
        messageList.clear()
        messageList.addAll(newMessages.reversed())
        notifyDataSetChanged()
    }

    class MessageViewHolder(var view : ItemMessageBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemMessageBinding>(inflater, R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount() = messageList.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.view.user.text = messageList[position].user
        holder.view.timestamp.text = messageList[position].timestamp
        holder.view.message.text = messageList[position].message
    }

}