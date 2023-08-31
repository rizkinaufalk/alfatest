package com.rizki.alfatest.app.domain.usecase.movie.get_video

import com.rizki.alfatest.app.common.DataSource
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.remote.dto.toYoutubeVideo
import com.rizki.alfatest.app.domain.FlowUseCase
import com.rizki.alfatest.app.domain.mapper.YoutubeVideo
import com.rizki.alfatest.app.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVideoByMovieIdUseCase @Inject constructor(
    private val repository: MovieRepository
) : FlowUseCase<Int, YoutubeVideo>() {

    override suspend fun execute(parameters: Int?): Flow<Resource<YoutubeVideo>> {
        return flow {
            repository.getVideos(parameters).collect() { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val filteredData = resource.data?.filter { it.site == "YouTube" && it.type == "Trailer" }?.map { it }?.first()?.toYoutubeVideo()
                        if (filteredData != null ){
                            emit(Resource.Success(filteredData, DataSource.REMOTE))
                        }
                    }
                }
            }
        }
    }


}