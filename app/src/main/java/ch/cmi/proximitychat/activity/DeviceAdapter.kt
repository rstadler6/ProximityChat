package ch.cmi.proximitychat.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.cmi.proximitychat.R
import ch.cmi.proximitychat.model.Chat
import ch.cmi.proximitychat.model.Device
import java.time.format.DateTimeFormatter


class DeviceAdapter(private val dataSet: ArrayList<Device>, private val onClick: (Device) -> Unit) :
        RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val onClick: (Device) -> Unit) : RecyclerView.ViewHolder(view) {
        val deviceName: TextView = view.findViewById(R.id.deviceName)
        val macAddress: TextView = view.findViewById(R.id.macAddress)
        var currentDevice: Device? = null

        init {
            view.setOnClickListener {
                currentDevice?.let {
                    onClick(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.device_row_item, viewGroup, false)

        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.currentDevice = dataSet[position]
        viewHolder.deviceName.text = dataSet[position].deviceName
        viewHolder.macAddress.text = dataSet[position].macAddress
    }

    override fun getItemCount() = dataSet.size
}
