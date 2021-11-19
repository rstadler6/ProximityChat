package ch.cmi.proximitychat.model

import androidx.room.*
import ch.cmi.proximitychat.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE macAddress LIKE :macAddress LIMIT 1")
    fun findByMacAddress(macAddress: String): User

    @Insert
    fun insertAll(vararg chars: User)

    @Update
    fun updateAll(vararg chars: User)

    @Delete
    fun delete(user: User)
}