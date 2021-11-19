package ch.cmi.proximitychat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Chat(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "userAddress") val userAddress: String,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDate
)