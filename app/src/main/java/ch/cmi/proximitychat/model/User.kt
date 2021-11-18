package ch.cmi.proximitychat.model

import android.graphics.Bitmap

class User(val macAddress: String, var username: String) {
    var icon: Bitmap? = null
}