package com.xsis.movie.model.movieModels

import com.google.gson.annotations.SerializedName
import com.xsis.movie.model.movieModels.Movie

data class DiscoveredMovie (
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<Movie>,

    @SerializedName("total_results")
    val total_result: Int,

    @SerializedName("total_pages")
    val total_pages: Int
)