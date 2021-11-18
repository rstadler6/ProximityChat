package ch.cmi.proximitychat.model

import android.net.wifi.p2p.WifiP2pDevice
import androidx.databinding.ObservableList

class DeviceList(c: MutableCollection<out WifiP2pDevice> = ArrayList()) : ArrayList<WifiP2pDevice>(c) {
    private val callbacks = ArrayList<() -> Unit>()

    override fun add(element: WifiP2pDevice): Boolean {
        val result = super.add(element)

        if (!result)
            return result

        callbacks.forEach {
            it()
        }

        return result
    }

    override fun remove(element: WifiP2pDevice): Boolean {
        val result = super.remove(element)

        if (!result)
            return result

        callbacks.forEach {
            it()
        }

        return result
    }

    fun addOnListChangedCallback(callback: () -> Unit) {
        callbacks.add(callback)
    }

    fun removeOnListChangedCallback(callback: () -> Unit) {
        callbacks.remove(callback)
    }
}