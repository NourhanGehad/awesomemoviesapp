package com.example.awesomemoviesapp.movies.presentation.moviedetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.awesomemoviesapp.common.ui.StandardErrorMessage
import com.example.awesomemoviesapp.common.ui.StandardProgressIndicator
import com.example.awesomemoviesapp.movies.domain.model.MovieDetails
import com.example.awesomemoviesapp.movies.presentation.moviedetails.model.MovieDetailsScreenState

@Composable
fun MoviesDetailsScreen(
    state: MovieDetailsScreenState,
    onBackClicked: () -> Unit,
    onRetryClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        state.movie?.let { MovieDetailsItem(state.movie, onBackClicked) }
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
fun MovieDetailsItem(
    movieDetails: MovieDetails,
    onBackClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Movie Title
        Text(
            text = movieDetails.title.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        // Movie Overview
        Text(
            text = movieDetails.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Movie Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movieDetails.backdropPathPath)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        // Movie Details
        Text(
            text = "Release Date: ${movieDetails.releaseDate}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Genres: ${movieDetails.genres.joinToString { it.name }}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Original Language: ${movieDetails.originalLanguage}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )


        // Back Button
        IconButton(
            onClick = { onBackClicked() },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(36.dp)
            )
        }
    }
}
