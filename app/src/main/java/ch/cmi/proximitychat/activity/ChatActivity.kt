package ch.cmi.proximitychat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Chat
import ch.cmi.proximitychat.model.Message
import ch.cmi.proximitychat.model.User
import java.time.LocalDate

class ChatActivity : AppCompatActivity() {
    // placeholder data
    private val messages = ArrayList<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // placeholder data
        val message1 = Message(User("123", "User1"), "hallo", LocalDate.now())
        val message2 = Message(User("local", "localUser"), "hi", LocalDate.now())
        val message3 = Message(User("123", "User1"), "testwerqojwfoqwfjqalwlfjwelakjflsjfklsjafklajflajfalkfjkkls", LocalDate.now())
        messages.add(message1)
        messages.add(message2)
        messages.add(message3)

        findViewById<RecyclerView>(R.id.messagesRecycler).adapter = MessageAdapter(messages)
    }

    fun back (view: View?) {
        finish()
    }
}