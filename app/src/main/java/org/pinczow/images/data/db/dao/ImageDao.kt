package org.pinczow.images.data.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import org.pinczow.images.data.db.entity.Image

@Dao
interface ImageDao {
    @Query("SELECT * FROM image")
    fun selectAll(): PagingSource<Int, Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<Image>)

    @Query("DELETE FROM image")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOne(image: Image)
}