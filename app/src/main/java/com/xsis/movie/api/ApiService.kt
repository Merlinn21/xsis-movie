package com.xsis.movie.api

import com.xsis.movie.model.genreModels.GenreList
import com.xsis.movie.model.movieModels.DiscoveredMovie
import com.xsis.movie.model.movieModels.MovieDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?")
    fun discoverMovie(
        @Query("api_key") api_key : String,
        @Query("with_genres") with_genres : String?,
        @Query("page") page : Int,
        @Query("sort_by") sort_by : String?,
        @Query("year") year : String?
    ) : Call<DiscoveredMovie>

    @GET("movie/{movie_id}?")
    fun getMovieDetail(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response : String
    ) :Call<MovieDetail>

    @GET("search/movie?")
    fun searchMovie(
        @Query("api_key") api_key : String,
        @Query("page") page : Int,
        @Query("query") query : String
    ) : Call<DiscoveredMovie>

    @GET("genre/movie/list")
    fun getGenre(
        @Query("api_key") api_key : String
    ) : Call<GenreList>
}