package ch.cmi.proximitychat.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ch.cmi.proximitychat.model.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM message WHERE chatAddress LIKE :chatAddress")
    fun findByChatId(chatAddress: String): List<Message>

    @Insert
    fun insertAll(vararg chars: Message)

    @Delete
    fun delete(message: Message)
}