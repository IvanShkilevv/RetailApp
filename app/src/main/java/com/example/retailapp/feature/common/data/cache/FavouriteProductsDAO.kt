package com.example.retailapp.feature.common.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteProductsDAO {

    @Query(
        """
        SELECT * FROM favourites
        ORDER BY createDate DESC
        """
    )
    fun getAll(): Flow<List<FavouriteProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: FavouriteProductEntity)

    @Query(
        """
        DELETE FROM favourites
        WHERE id = :id
        """
    )
    suspend fun deleteItem(id: String)

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM favourites WHERE id = :id
        )
        """
    )
    suspend fun isFavourite(id: String): Boolean
}