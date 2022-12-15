package com.xsis.movie.model.movieModels

import com.google.gson.annotations.SerializedName
import com.xsis.movie.model.genreModels.Genre
import com.xsis.movie.model.videoModels.VideoList

data class MovieDetail (
    @SerializedName("backdrop_path")
    val backdrop_path: String?,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("original_title")
    val original_title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("poster_path")
    val poster_path: String,

    @SerializedName("release_date")
    val release_date: String,

    @SerializedName("revenue")
    val revenue: String,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("tagline")
    val tagline: String,

    @SerializedName("vote_average")
    val vote_average: Double,

    @SerializedName("vote_count")
    val vote_count: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("videos")
    val videos: VideoList,
)