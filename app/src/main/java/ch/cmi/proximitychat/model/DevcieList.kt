package ch.cmi.proximitychat.model

import androidx.databinding.ObservableList

class DeviceList(c: MutableCollection<out Device>) : ArrayList<Device>(c) {
    private val callbacks = ArrayList<() -> Unit>()

    override fun add(element: Device): Boolean {
        val result = super.add(element)

        if (!result)
            return result

        callbacks.forEach {
            it()
        }

        return result
    }

    override fun remove(element: Device): Boolean {
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