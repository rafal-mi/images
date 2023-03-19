package org.pinczow.images.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import org.pinczow.images.data.db.entity.ImageEntity

@Dao
interface ImageDao {
    @Query("SELECT * FROM image")
    fun selectAll(): PagingSource<Int, ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Query("DELETE FROM image")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOne(image: ImageEntity)
}