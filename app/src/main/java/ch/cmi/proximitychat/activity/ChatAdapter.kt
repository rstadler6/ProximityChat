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


class ChatAdapter(private val dataSet: ArrayList<Chat>, private val onClick: (Chat) -> Unit) :
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
        viewHolder.currentChat = dataSet[position]
        viewHolder.username.text = dataSet[position].user.username
        viewHolder.lastMessage.text = dataSet[position].messages.last().content
        viewHolder.lastMessageTime.text = dataSet[position].messages.last().timestamp.format(
            DateTimeFormatter.BASIC_ISO_DATE
        )
    }

    override fun getItemCount() = dataSet.size
}
