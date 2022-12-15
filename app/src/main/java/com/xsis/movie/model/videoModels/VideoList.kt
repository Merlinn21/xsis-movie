package com.xsis.movie.model.videoModels

import com.google.gson.annotations.SerializedName
import com.xsis.movie.model.videoModels.Video

data class VideoList(
    @SerializedName("results")
    val results: List<Video>
)
