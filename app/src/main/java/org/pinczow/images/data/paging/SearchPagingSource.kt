package org.pinczow.images.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.pinczow.images.Constants.PAGE_SIZE
import org.pinczow.images.data.Resource
import org.pinczow.images.data.net.RestApi
import org.pinczow.images.feature.image.domain.model.ImageModel

class SearchPagingSource(
    private val restApi: RestApi,
    private val query: String
) : PagingSource<Int, ImageModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageModel> {
        val currentPage = params.key ?: 1
        return try {
            val response = restApi.search(query = query, perPage = PAGE_SIZE)

            return when(response) {
                is Resource.Success -> {
                    val endOfPaginationReached = response.data.images.isEmpty()
                    if (response.data.images.isNotEmpty()) {
                        LoadResult.Page(
                            data = response.data.images,
                            prevKey = if (currentPage == 1) null else currentPage - 1,
                            nextKey = if (endOfPaginationReached) null else currentPage + 1
                        )
                    } else {
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
                        )
                    }
                }
                is Resource.Error -> {
                    LoadResult.Error(response.exception)
                }
                else -> {
                    LoadResult.Error(Exception("Unknown error"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageModel>): Int? {
        return state.anchorPosition
    }

}