package ch.cmi.proximitychat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ch.cmi.proximitychat.R

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }

    fun back (view: View?) {
        finish()
    }
}