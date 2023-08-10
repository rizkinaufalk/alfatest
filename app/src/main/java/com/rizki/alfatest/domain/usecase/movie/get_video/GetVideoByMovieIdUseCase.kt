package com.rizki.alfatest.domain.usecase.movie.get_video

import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.domain.mapper.YoutubeVideo
import com.rizki.alfatest.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetVideoByMovieIdUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(movieId: Int): Flow<Resource<List<YoutubeVideo>>> = flow {
        try {
            emit(Resource.Loading<List<YoutubeVideo>>())
            val youtube = repository.getVideos(movieId)
            emit(Resource.Success<List<YoutubeVideo>>(youtube))
        } catch(e: HttpException) {
            emit(Resource.Error<List<YoutubeVideo>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<YoutubeVideo>>("Couldn't reach server. Check your internet connection."))
        }
    }
}