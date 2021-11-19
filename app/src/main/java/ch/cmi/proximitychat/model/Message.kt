package ch.cmi.proximitychat.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Message(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "chatId") val chatId: Int,
    @ColumnInfo(name = "userAddress") val userAddress: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDate
)