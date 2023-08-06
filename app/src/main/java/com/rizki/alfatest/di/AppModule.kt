package com.rizki.alfatest.di

import android.app.Application
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.rizki.alfatest.MovieApp
import com.rizki.alfatest.data.local.MovieDb
import com.rizki.alfatest.data.remote.MovieApi
import com.rizki.alfatest.data.repository.MovieDbRepositoryImpl
import com.rizki.alfatest.data.repository.MovieRepositoryImpl
import com.rizki.alfatest.domain.repository.MovieDbRepository
import com.rizki.alfatest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        val okHttp = OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(MovieApp.applicationContext())
                    .collector(ChuckerCollector(MovieApp.applicationContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
            .create(MovieApi::class.java)

    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieApi
    ): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieDbRepository(
        db: MovieDb
    ): MovieDbRepository {
        return MovieDbRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideMovieDb(app: Application): MovieDb{
        return Room.databaseBuilder(
            app, MovieDb::class.java, "movie_db"
        ).build()
    }
}