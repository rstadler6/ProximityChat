package ch.cmi.proximitychat.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ch.cmi.proximitychat.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE macAddress LIKE :macAddress LIMIT 1")
    fun findByMacAddress(macAddress: String): User

    @Insert
    fun insertAll(vararg chars: User)

    @Delete
    fun delete(user: User)
}