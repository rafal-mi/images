package org.pinczow.images.data

sealed class Resource<out R> {

    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Resource] is of type [Success] & holds non-null [Success.data].
 */
val Resource<*>.succeeded
    get() = this is Resource.Success && data != null

