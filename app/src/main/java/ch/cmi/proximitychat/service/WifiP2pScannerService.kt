package ch.cmi.proximitychat.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import ch.cmi.proximitychat.model.Device
import ch.cmi.proximitychat.model.DeviceList

class WifiP2pScannerService : Service() {
    private val binder = ScannerBinder()

    private val intentFilter = IntentFilter()
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var manager: WifiP2pManager

    val devices = DeviceList()

    private val peerListListener = WifiP2pManager.PeerListListener { peerList ->
        val refreshedPeers = peerList.deviceList

        // remove old peers
        for (device in devices) {
            if (!refreshedPeers.contains(device))
                devices.remove(device)
        }

        // add new peers
        for (device in refreshedPeers) {
            if (!devices.contains(device))
                devices.add(device)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                    val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                    if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
                        Log.i("ProximityChat", "WifiP2p enabled")
                    else
                        Log.e("ProximityChat", "WifiP2p disabled")
                }
                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    try {
                        manager.requestPeers(channel, peerListListener)
                    }
                    catch (e: SecurityException) {
                        // not enough permissions, TODO
                    }
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, mainLooper, null)
    }

    @SuppressLint("MissingPermission")
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy()
    }

    inner class ScannerBinder : Binder() {
        fun getService() = this@WifiP2pScannerService
    }

    fun startDiscovery(callback: () -> Unit) {
        devices.addOnListChangedCallback(callback)
        registerReceiver(receiver, intentFilter)

        try {
            manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {

                override fun onSuccess() {
                    Log.i("ProximityChat", "Discovery initiated")
                }

                override fun onFailure(reasonCode: Int) {
                    Log.e("ProximityChat", "Discovery initiation failed")
                }
            })

        }
        catch (e: SecurityException) {
            // not enough permissions, TODO
        }
    }

    fun connectDevice(device: Device) {
        val config = WifiP2pConfig().apply {
            deviceAddress = device.macAddress
            wps.setup = WpsInfo.PBC
        }

        try {
            manager.connect(channel, config, object : WifiP2pManager.ActionListener {

                override fun onSuccess() {
                    // connection successful
                }

                override fun onFailure(reason: Int) {
                    // connection failed
                }
            })
        }
        catch (e: SecurityException) {
            // not enough permissions, TODO
        }
    }

}