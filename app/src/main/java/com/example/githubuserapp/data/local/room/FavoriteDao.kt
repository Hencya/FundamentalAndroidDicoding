package com.example.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuserapp.data.local.entity.UserFavEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite ORDER BY login ASC")
    fun getFavoriteListUser(): LiveData<List<UserFavEntity>>

    @Query("SELECT count(*) FROM favorite WHERE login = :login")
    fun isFavorite(login: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteUser(favoriteEntity: UserFavEntity)

    @Query("DELETE FROM favorite WHERE login = :login")
    fun deleteFavoriteUser(login: String): Int
}