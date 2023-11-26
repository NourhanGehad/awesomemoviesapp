package com.example.awesomemoviesapp.movies.data

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.awesomemoviesapp.movies.data.model.RemoteMovie
import com.example.awesomemoviesapp.movies.domain.model.Movie
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Exception

class MoviesPagingSource @Inject constructor(
    private val apiService: MoviesApiService,
): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNo = params.key ?: 1
            val movies = apiService.getMovies(pageNumber = pageNo).results.toMovies()

            Log.d("loading page no", pageNo.toString())
            LoadResult.Page(
                data = movies,
                prevKey = if (pageNo == 1) null else pageNo,
                nextKey = pageNo + 1,
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }


    private fun List<RemoteMovie>.toMovies() = this.map {
        Movie(
            id = it.id,
            adult = it.adult,
            name = it.originalTitle,
            releaseYear = it.releaseDate?.subSequence(0, 4).toString(),
            posterImagePath = it.posterPath
        )
    }
}
