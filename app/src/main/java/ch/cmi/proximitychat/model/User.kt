package ch.cmi.proximitychat.model

import android.graphics.Bitmap

class User(macAddress: String, username: String) {
    lateinit var macAddress: String
    lateinit var username: String
    var icon: Bitmap? = null
}