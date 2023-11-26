package com.example.awesomemoviesapp.movies.presentation.movieslist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.awesomemoviesapp.movies.domain.model.Movie
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.awesomemoviesapp.common.ui.StandardErrorMessage
import com.example.awesomemoviesapp.common.ui.StandardProgressIndicator
import com.example.awesomemoviesapp.movies.presentation.movieslist.model.MoviesScreenState

@Composable
fun MoviesListScreen(
    state: MoviesScreenState,
    onItemClick: (Int) -> Unit,
    onRetryClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val movies = state.movies.collectAsLazyPagingItems()
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 16.dp
            )
        ) {
            itemsIndexed(movies) { index, movie ->
                movie?.let { m ->
                    MovieItem(
                        movie = m,
                        posterBaseUrl = state.posterBaseUrl.toString(),
                        onItemClick = { movieId -> onItemClick(movieId) }
                    )
                }
            }
        }
        if (state.isLoading) {
            StandardProgressIndicator()
        }
        state.error?.let {
            StandardErrorMessage(
                errorMsg = it,
                onRetryClick = onRetryClicked
            )
        }
    }
}
@Composable
fun MovieItem(
    movie: Movie,
    posterBaseUrl: String,
    onItemClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onItemClick(movie.id) }
            .background(MaterialTheme.colorScheme.surface)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = 12.dp)
        ) {

            // Movie Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$posterBaseUrl${movie.posterImagePath}")
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            // Gradient overlay for a cooler effect
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = 600f
                        )
                    )
            )

            // Movie Details at the bottom
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            ) {
                // Spacer to push details to the bottom
                Spacer(modifier = Modifier.weight(1f))

                // Movie Name
                Text(
                    text = movie.name.toString(),
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                    modifier = Modifier.fillMaxWidth()
                )

                // Release Date
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = movie.releaseYear.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                }
            }
        }
    }
}