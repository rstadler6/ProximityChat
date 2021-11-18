package ch.cmi.proximitychat.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BluetoothChatService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}