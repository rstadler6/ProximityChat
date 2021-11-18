package ch.cmi.proximitychat.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Message
import java.time.format.DateTimeFormatter

private const val TYPE_MESSAGE_SENT = 0
private const val TYPE_MESSAGE_RECEIVED = 1

class MessageAdapter(private val dataSet: ArrayList<Message>) :
        RecyclerView.Adapter<MessageAdapter.MessageHolder>() {
    // placeholder data
    val localMac = "local"

    class MessageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.content)
        val timestamp: TextView = view.findViewById(R.id.timestamp)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessageHolder {
        val viewId = if (viewType == TYPE_MESSAGE_SENT) R.layout.sent_message_item
            else R.layout.received_message_item


        val view = LayoutInflater.from(viewGroup.context)
                .inflate(viewId, viewGroup, false)

        return MessageHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MessageHolder, position: Int) {
        viewHolder.content.text = dataSet[position].content
        viewHolder.timestamp.text = dataSet[position].timestamp.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position].user.macAddress == localMac) TYPE_MESSAGE_SENT else TYPE_MESSAGE_RECEIVED
    }

    override fun getItemCount() = dataSet.size
}
