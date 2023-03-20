package org.pinczow.images.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.pinczow.images.data.db.AppDatabase
import org.pinczow.images.data.net.RestApi
import org.pinczow.images.data.net.RestApiImpl
import org.pinczow.images.feature.image.domain.repository.ImageRepository
import org.pinczow.images.feature.image.domain.use_cases.GetAll
import org.pinczow.images.feature.image.domain.use_cases.ImageUseCases
import org.pinczow.images.feature.image.domain.use_cases.Search
import org.pinczow.images.feature.image.domain.use_cases.ToggleFavorite
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app.db"
        )// .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideRestApi(): RestApi =
        RestApiImpl()

    @Provides
    @Singleton
    fun provideImageUseCases(
        repository: ImageRepository
    ): ImageUseCases =
        ImageUseCases(
            getAll = GetAll(repository),
            search = Search(repository),
            toggleFavorite = ToggleFavorite(repository)
        )
}