package ch.cmi.proximitychat.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getAll(): List<Chat>

    @Query("SELECT * FROM chat WHERE chatAddress LIKE :macAddress LIMIT 1")
    fun findByMacAddress(macAddress: String): Chat

    @Insert
    fun insertAll(vararg chars: Chat)

    @Delete
    fun delete(chat: Chat)
}
