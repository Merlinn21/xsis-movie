package com.xsis.movie.view

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.xsis.movie.R
import com.xsis.movie.databinding.LayoutMovieDetailBinding
import com.xsis.movie.model.movieModels.Movie
import com.xsis.movie.model.movieModels.MovieDetail
import com.xsis.movie.model.videoModels.Video

class MovieDetailDialog(private val movieData : MovieDetail) : DialogFragment() {
    private lateinit var binding: LayoutMovieDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LayoutMovieDetailBinding.inflate(LayoutInflater.from(context), container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);

        initMovieData()
        return binding.root
    }

    private fun initMovieData(){
        binding.tvMovieTitle.text = movieData.title

        if(movieData.overview != ""){
            binding.tvMovieDesc.text = movieData.overview
        } else{
            binding.tvMovieDesc.text = getString(R.string.no_summary)
        }

        initVideo()
    }

    private fun initVideo(){
        var trailerVideo : Video? = null
        var videoList = movieData.videos.results

        for (video in videoList){
            if(video.type.lowercase() == "trailer"){
                trailerVideo = video
                break
            }
        }

        if(trailerVideo == null){
            binding.videoPlayer.visibility = View.INVISIBLE
            binding.ivVidNotFound.visibility = View.VISIBLE
            return
        }

        binding.videoPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(trailerVideo.key, 0f)
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.videoPlayer.enterFullScreen();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            binding.videoPlayer.exitFullScreen();
        }
    }

    companion object {
        const val TAG = "MovieDetailDialog"
    }
}