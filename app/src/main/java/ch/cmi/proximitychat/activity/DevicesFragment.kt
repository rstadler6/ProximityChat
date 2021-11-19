package ch.cmi.proximitychat.activity

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Chat
import ch.cmi.proximitychat.model.Device
import ch.cmi.proximitychat.service.WifiP2pScannerService

class DevicesFragment : Fragment() {
    // placeholder data
    val devices = ArrayList<Device>()
    var count = 1

    private lateinit var scannerService: WifiP2pScannerService
    private var bound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as WifiP2pScannerService.ScannerBinder
            scannerService = binder.getService()
            scannerService.startDiscovery(::onDevicesChanged)
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(activity, WifiP2pScannerService::class.java).also { intent ->
            activity!!.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        activity!!.unbindService(connection)
        bound = false
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
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

    fun onDevicesChanged() {
        view!!.findViewById<TextView>(R.id.debug).text = "device found: " + count++
        view!!.findViewById<RecyclerView>(R.id.devicesRecycler).adapter = DeviceAdapter(devices, ::onDeviceClick)
    }

    private fun onDeviceClick(device: Device) {
        // TODO
    }
}