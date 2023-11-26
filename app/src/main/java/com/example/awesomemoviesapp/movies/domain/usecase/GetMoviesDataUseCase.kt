package com.example.awesomemoviesapp.movies.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.awesomemoviesapp.common.data.di.NetworkRepository
import com.example.awesomemoviesapp.movies.data.MoviesPagingSource
import com.example.awesomemoviesapp.movies.data.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMoviesDataUseCase @Inject constructor(
    private val moviesDataSource: MoviesPagingSource, // or MoviesPagingSource
    private val networkRepository: NetworkRepository,
    private val moviesPagingSource: MoviesPagingSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        pageSize: Int = 10
    ) = withContext(dispatcher){
        Pager(
            pagingSourceFactory = { moviesPagingSource },
            config = PagingConfig(pageSize = pageSize)
        ).flow
    }
}
