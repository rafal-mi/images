package org.pinczow.images.data.repository

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.pinczow.images.Constants.PAGE_SIZE
import org.pinczow.images.data.db.AppDatabase
import org.pinczow.images.data.db.entity.ImageEntity
import org.pinczow.images.data.net.RestApi
import org.pinczow.images.data.paging.SearchPagingSource
import org.pinczow.images.data.paging.UnsplashRemoteMediator
import org.pinczow.images.feature.image.domain.model.ImageModel
import org.pinczow.images.feature.image.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val restApi: RestApi,
    private val database: AppDatabase
) : ImageRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAll(): Flow<PagingData<ImageModel>> {
        val pagingSourceFactory = { database.imageDao().selectAll() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = UnsplashRemoteMediator(
                restApi = restApi,
                database = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
                pagingData -> pagingData.map {
                    ImageModel(it)
                }
        }
    }

    override fun search(query: String): Flow<PagingData<ImageModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchPagingSource(restApi = restApi, query = query)
            }
        ).flow
    }

    override suspend fun toggleFavorite(image: ImageModel) {
        val newImage = image.copy()
        newImage.favorite = image.favorite
        database.imageDao().updateOne(ImageEntity(newImage))
    }

}