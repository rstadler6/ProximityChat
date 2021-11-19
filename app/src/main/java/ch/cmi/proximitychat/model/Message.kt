package ch.cmi.proximitychat.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Message(
    @ColumnInfo(name = "chatAddress") val chatAddress: String,
    @ColumnInfo(name = "userAddress") val userAddress: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "timestamp") val timestamp: String
) {
    @PrimaryKey var id: Int? = null
}