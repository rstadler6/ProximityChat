package ch.cmi.proximitychat.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Chat
import ch.cmi.proximitychat.model.Message
import ch.cmi.proximitychat.model.User
import java.time.LocalDate
import kotlin.collections.ArrayList

class ChatsFragment : Fragment() {
    // placeholder data
    val chats = ArrayList<Chat>()
    val user = User("123", "User1")

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chats, container, false)

        // placeholder data
        val chat = Chat(user)
        val message = Message(user,"hello", LocalDate.now())
        chat.messages.add(message)
        chats.add(chat)

        view.findViewById<RecyclerView>(R.id.chatsRecycler).adapter = ChatAdapter(chats, ::onChatClick)

        return view
    }

    private fun onChatClick(chat: Chat) {
        val bundle = Bundle()
        bundle.putString("userMac", chat.user.macAddress)

        val intent = Intent(activity, ChatActivity::class.java).putExtras(bundle)
        startActivity(intent)
    }
}