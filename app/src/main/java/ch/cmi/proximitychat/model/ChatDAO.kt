package ch.cmi.proximitychat.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getAll(): List<Chat>

    @Query("SELECT * FROM chat WHERE macAddress LIKE :mac LIMIT 1")
    fun findByUser(macAddress: String): Chat

    @Insert
    fun insertAll(vararg chars: Chat)

    @Delete
    fun delete(chat: Chat)
}
