package ch.cmi.proximitychat.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Chat
import ch.cmi.proximitychat.model.Device
import ch.cmi.proximitychat.model.Message
import ch.cmi.proximitychat.model.User
import java.time.LocalDate

class DevicesFragment : Fragment() {
    // placeholder data
    val devices = ArrayList<Device>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_devices, container, false)

        // placeholder data
        val device1 = Device("Galaxy S8", "123")
        val device2 = Device("Pixel 2", "123")

        devices.add(device1)
        devices.add(device2)
        view.findViewById<RecyclerView>(R.id.devicesRecycler).adapter = DeviceAdapter(devices)

        return view
    }
}