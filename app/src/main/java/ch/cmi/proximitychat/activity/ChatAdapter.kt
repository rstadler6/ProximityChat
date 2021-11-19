package ch.cmi.proximitychat.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.adapters.ViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Chat
import java.time.format.DateTimeFormatter


class ChatAdapter(private val dataSet: List<Chat>, private val onClick: (Chat) -> Unit) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val onClick: (Chat) -> Unit) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username)
        val lastMessage: TextView = view.findViewById(R.id.lastMessage)
        val lastMessageTime: TextView = view.findViewById(R.id.lastMessageTime)
        var currentChat: Chat? = null

        init {
            view.setOnClickListener {
                currentChat?.let {
                    onClick(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_row_item, viewGroup, false)

        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val chat = dataSet[position]
        val user = db!!.userDao().findByMacAddress(chat.chatAddress)
        val messages = db!!.messageDao().findByChatId(chat.chatAddress)

        viewHolder.currentChat = chat
        viewHolder.username.text = user.username
        viewHolder.lastMessage.text = if (messages.count() > 0) messages.last().content else "No messages"
        viewHolder.lastMessageTime.text = if (messages.count() > 0)messages.last().timestamp.format(
            DateTimeFormatter.BASIC_ISO_DATE
        ) else ""
    }

    override fun getItemCount() = dataSet.size
}
