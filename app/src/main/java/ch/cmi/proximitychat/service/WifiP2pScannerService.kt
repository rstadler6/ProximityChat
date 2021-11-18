package ch.cmi.proximitychat.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Binder
import android.os.IBinder
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
    }

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
        // TODO check if WifiP2p enabled

        devices.addOnListChangedCallback(callback)
        registerReceiver(receiver, intentFilter)
    }
}