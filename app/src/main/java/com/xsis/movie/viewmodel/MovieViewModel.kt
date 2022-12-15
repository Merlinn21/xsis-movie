package com.xsis.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xsis.movie.api.RetrofitInstance
import com.xsis.movie.global.Api
import com.xsis.movie.model.genreModels.Genre
import com.xsis.movie.model.genreModels.GenreList
import com.xsis.movie.model.movieModels.DiscoveredMovie
import com.xsis.movie.model.movieModels.Movie
import com.xsis.movie.model.movieModels.MovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private var popularMovieLiveData = MutableLiveData<DiscoveredMovie>()
    private var latestMovieLiveData = MutableLiveData<DiscoveredMovie>()
    private var movieDetailLiveData = MutableLiveData<MovieDetail>()
    private var searchMovieLiveData = MutableLiveData<DiscoveredMovie>()
    private var genreLiveData = MutableLiveData<GenreList>()
    private var genreMovieLiveData = MutableLiveData<DiscoveredMovie>()

    fun getPopularMovies() {
        RetrofitInstance.apiService.discoverMovie(Api.API_KEY, null, 1, null, null).enqueue(object  :
            Callback<DiscoveredMovie> {
            override fun onResponse(call: Call<DiscoveredMovie>, response: Response<DiscoveredMovie>) {
                if (response.body() != null){
                    popularMovieLiveData.value = response.body()
                }
            }
            override fun onFailure(call: Call<DiscoveredMovie>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        })
    }

    fun getLatestMovies(){
        RetrofitInstance.apiService.discoverMovie(Api.API_KEY, null, 1, "release_date.desc", "2022").enqueue(object  :
            Callback<DiscoveredMovie> {
            override fun onResponse(call: Call<DiscoveredMovie>, response: Response<DiscoveredMovie>) {
                if (response.body() != null){
                    latestMovieLiveData.value = response.body()
                }
            }
            override fun onFailure(call: Call<DiscoveredMovie>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        })
    }

    fun getMovieDetail(id: Int){
        val appendRespond = "videos"
        RetrofitInstance.apiService.getMovieDetail(id, Api.API_KEY, appendRespond).enqueue(object  :
            Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.body() != null){
                    movieDetailLiveData.value = response.body()
                }
            }
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        }
        )
    }

    fun getSearchMovie(query : String, page : Int){
        RetrofitInstance.apiService.searchMovie(Api.API_KEY, page, query).enqueue(object  :
            Callback<DiscoveredMovie> {
            override fun onResponse(call: Call<DiscoveredMovie>, response: Response<DiscoveredMovie>) {
                if (response.body() != null){
                    searchMovieLiveData.value = response.body()
                }
            }
            override fun onFailure(call: Call<DiscoveredMovie>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        })
    }

    fun getGenre(){
        RetrofitInstance.apiService.getGenre(Api.API_KEY).enqueue(object  :
            Callback<GenreList> {
            override fun onResponse(call: Call<GenreList>, response: Response<GenreList>) {
                if (response.body() != null){
                    genreLiveData.value = response.body()
                }
            }
            override fun onFailure(call: Call<GenreList>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        })
    }

    fun getMovieWithGenre(genre : String){
        RetrofitInstance.apiService.discoverMovie(Api.API_KEY, genre, 1, null, null).enqueue(object  :
            Callback<DiscoveredMovie> {
            override fun onResponse(call: Call<DiscoveredMovie>, response: Response<DiscoveredMovie>) {
                if (response.body() != null){
                    genreMovieLiveData.value = response.body()
                }
            }
            override fun onFailure(call: Call<DiscoveredMovie>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        })
    }

    fun observePopularMovieLiveData() : LiveData<DiscoveredMovie> {
        return popularMovieLiveData
    }

    fun observeLatestMovieLiveData() : LiveData<DiscoveredMovie>{
        return latestMovieLiveData
    }

    fun observeMovieDetailLiveData() : LiveData<MovieDetail>{
        return movieDetailLiveData
    }

    fun observeSearchMovieLiveData() : LiveData<DiscoveredMovie>{
        return searchMovieLiveData
    }

    fun observeGenreLiveData() : LiveData<GenreList>{
        return genreLiveData
    }

    fun observerGenreMovieLiveData() : LiveData<DiscoveredMovie>{
        return genreMovieLiveData
    }
}