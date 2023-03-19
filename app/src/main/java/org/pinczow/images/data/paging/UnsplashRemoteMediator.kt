package org.pinczow.images.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import org.pinczow.images.Constants.ITEMS_PER_PAGE
import org.pinczow.images.data.Resource
import org.pinczow.images.data.db.AppDatabase
import org.pinczow.images.data.db.entity.ImageEntity
import org.pinczow.images.data.db.entity.RemoteKeys
import org.pinczow.images.data.net.RestApi
import org.pinczow.images.feature.image.domain.model.ImageModel

@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator(
    private val restApi: RestApi,
    private val database: AppDatabase
) : RemoteMediator<Int, ImageModel>() {

    private val imageDao = database.imageDao()
    private val remoteKeysDao = database.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageModel>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = restApi.getImages(page = currentPage, perPage = ITEMS_PER_PAGE)
            when(response) {
                is Resource.Success -> {
                    val endOfPaginationReached = response.data.isNotEmpty()

                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            imageDao.deleteAll()
                            remoteKeysDao.deleteAll()
                        }
                        val keys = response.data.map { unsplashImage ->
                            RemoteKeys(
                                id = unsplashImage.id,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }
                        remoteKeysDao.insertAll(remoteKeys = keys)
                        imageDao.insertAll(
                            images = response.data.map {
                                ImageEntity(it)
                            }
                        )
                    }
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                is Resource.Error -> {
                    MediatorResult.Error(response.exception)
                }
                else -> {
                    throw Exception("Unknown Result")
                }
            }

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ImageModel>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ImageModel>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ImageModel>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                remoteKeysDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

}