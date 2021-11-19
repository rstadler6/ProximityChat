package ch.cmi.proximitychat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Chat
import ch.cmi.proximitychat.model.Message
import ch.cmi.proximitychat.model.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {
    lateinit var chatAddress: String
    var messages = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatAddress = currentChatAddress!!//savedInstanceState!!.getString("chatAddress")!!
        messages = db!!.messageDao().findByChatId(chatAddress) as ArrayList<Message>
        findViewById<RecyclerView>(R.id.messagesRecycler).adapter = MessageAdapter(messages)

        val user = db!!.userDao().findByMacAddress(chatAddress)
        findViewById<TextView>(R.id.username).text = user.username
    }

    fun back(view: View?) {
        finish()
    }

    fun sendMessage(view: View?) {
        val message = Message(chatAddress, "local", findViewById<TextView>(R.id.messageText).text.toString(), LocalDate.now().format(
            DateTimeFormatter.BASIC_ISO_DATE))
        messages.add(message)
        db!!.messageDao().insertAll(message)
        findViewById<RecyclerView>(R.id.messagesRecycler).adapter = MessageAdapter(messages)
    }

    fun takePicture(view: View?) {

    }
}