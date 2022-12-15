package com.xsis.movie.model.genreModels

import com.google.gson.annotations.SerializedName
import com.xsis.movie.model.genreModels.Genre

data class GenreList(
    @SerializedName("genres")
    val genres: List<Genre>
)
