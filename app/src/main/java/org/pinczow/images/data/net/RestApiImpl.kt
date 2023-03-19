package org.pinczow.images.data.net

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
// import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.pinczow.images.BuildConfig
import org.pinczow.images.data.Resource
import org.pinczow.images.data.Resource.Success
import org.pinczow.images.data.Resource.Error
import org.pinczow.images.feature.image.domain.model.ImageModel

const val BASE_URL = "https://api.unsplash.com"

class RestApiImpl: RestApi {
    override suspend fun getImages(page: Int, perPage: Int): Resource<List<ImageModel>> {
        val client = HttpClient(Android) {
            // install(Resources)
            install(DefaultRequest) {
                headers.append("Accept", "application/json")
                headers.append("Authorization", "Client-ID ${BuildConfig.API_KEY}")
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        val url = "$BASE_URL/photos"

        return try {
            val r: List<ImageModel> = client.get(url) {
                url {
                    parameters.append("page", page.toString())
                    parameters.append("per_page", perPage.toString())
                }
            }.body()

            return Success(r)
        } catch(e: Exception) {
            return Error(e)
        }
    }

    override suspend fun search(): SearchResponse {
        TODO("Not yet implemented")
    }
}