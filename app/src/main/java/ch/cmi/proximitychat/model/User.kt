package ch.cmi.proximitychat.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val macAddress: String,
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "icon") var icon: ByteArray?
)
