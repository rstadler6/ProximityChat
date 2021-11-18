package ch.cmi.proximitychat.service

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import ch.cmi.proximitychat.model.Device
import ch.cmi.proximitychat.model.DeviceList


class BluetoothScannerService : Service() {
    private val binder = ScannerBinder()
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val devices = DeviceList(ArrayList())

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                    devices.add(Device(device.name, device.address))
                }
            }
        }
    }

    fun startScan(callback: () -> Unit) {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableBtIntent)
        }

        devices.addOnListChangedCallback(callback)

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        bluetoothAdapter.startDiscovery()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy()
    }

    inner class ScannerBinder : Binder() {
        fun getService() = this@BluetoothScannerService
    }
}