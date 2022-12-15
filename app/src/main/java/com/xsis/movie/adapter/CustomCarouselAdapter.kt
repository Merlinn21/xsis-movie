package com.xsis.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.xsis.movie.R
import com.xsis.movie.databinding.LayoutMovieCarouselBinding
import com.xsis.movie.global.Api
import com.xsis.movie.helper.GlideHelper
import com.xsis.movie.interfaces.OnMovieClick
import com.xsis.movie.model.movieModels.Movie
import java.util.*


class CustomCarouselAdapter(private val context: Context, private val onMovieClick: OnMovieClick) : PagerAdapter() {
    private val lsMovie : MutableList<Movie> = Collections.emptyList<Movie?>().toMutableList()

    fun updateMovies(lsMovie : List<Movie>){
        this.lsMovie.addAll(lsMovie)
        notifyDataSetChanged()
    }

    fun getMovie(position: Int) : Movie{
        return lsMovie[position]
    }

    override fun getCount(): Int {
        return lsMovie.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = LayoutMovieCarouselBinding.inflate(LayoutInflater.from(context), container, false)

        val movie = lsMovie[position]

        binding.tvMovieTitle.text = movie.title
        binding.tvMovieDesc.text = movie.overview
        GlideHelper.load(context, Api.BASE_IMG_URL + movie.poster_path, binding.ivMoviePoster)

        binding.cvMovie.setOnClickListener(
            View.OnClickListener {
                onMovieClick.onMovieClick(position)
            }
        )
        container.addView(binding.root, 0)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}