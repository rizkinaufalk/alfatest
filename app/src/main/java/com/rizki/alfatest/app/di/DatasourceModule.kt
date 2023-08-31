package com.rizki.alfatest.app.di

import com.rizki.alfatest.app.data.datasource.remote.MovieDataSourceImpl
import com.rizki.alfatest.app.data.remote.MovieApi
import com.rizki.alfatest.app.domain.datasource.MovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceModule {

    @Singleton
    @Provides
    fun provideMovieDatasource(
        movieApi: MovieApi
    ): MovieDataSource = MovieDataSourceImpl(movieApi)
}