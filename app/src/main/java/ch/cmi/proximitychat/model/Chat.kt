package ch.cmi.proximitychat.model

import android.graphics.Bitmap

class Chat(user: User) {
    lateinit var user: User
    val messages: ArrayList<Message> = ArrayList()
}