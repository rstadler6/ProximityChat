package ch.cmi.proximitychat.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.AppDatabase
import ch.cmi.proximitychat.model.Chat
import ch.cmi.proximitychat.model.Message
import ch.cmi.proximitychat.model.User
import java.time.LocalDate
import kotlin.collections.ArrayList

var currentChatAddress: String? = null

class ChatsFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chats, container, false)

        view.findViewById<RecyclerView>(R.id.chatsRecycler).adapter = ChatAdapter(db!!.chatDao().getAll(), ::onChatClick)

        return view
    }

    private fun onChatClick(chat: Chat) {
        val bundle = Bundle()
        bundle.putString("chatAddress", chat.chatAddress)
        currentChatAddress = chat.chatAddress

        val intent = Intent(activity, ChatActivity::class.java).putExtras(bundle)
        startActivity(intent)
    }
}