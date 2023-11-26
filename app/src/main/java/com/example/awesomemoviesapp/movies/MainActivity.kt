package com.example.awesomemoviesapp.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.awesomemoviesapp.movies.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import com.example.awesomemoviesapp.movies.presentation.moviedetails.view.MoviesDetailsScreen
import com.example.awesomemoviesapp.movies.presentation.movieslist.view.MoviesListScreen
import com.example.awesomemoviesapp.movies.presentation.movieslist.viewmodel.MoviesViewModel
import com.example.awesomemoviesapp.ui.theme.AwesomeMoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AwesomeMoviesAppTheme {
                MoviesApp()
            }
        }
    }
}


@Composable
private fun MoviesApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "movies" ) {
        composable(route = "movies") {
            val vm: MoviesViewModel = hiltViewModel()
            val moviesScreenState by vm.state

            MoviesListScreen(
                state = moviesScreenState,
                onItemClick = { movieId ->
                    navController.navigate("movies/$movieId")
                },
                onRetryClicked = {
                    vm.loadData()
                }
            )
        }
        composable(route = "movies/{movie_id}",
            arguments = listOf(
                navArgument(name = "movie_id") {
                    type = NavType.IntType
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://www.awesomemoviesapp.com/details/{movie_id}"
                }
            )
        ) {
            val vm: MovieDetailsViewModel = hiltViewModel()
            val moviesDetailsScreenState by vm.state

            MoviesDetailsScreen(
                state = moviesDetailsScreenState,
                onBackClicked = {
                    navController.navigate("movies")
                },
                onRetryClicked = {
                    vm.loadMovieDetails()
                }
            )
        }
    }
}
