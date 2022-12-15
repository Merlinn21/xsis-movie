package com.xsis.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xsis.movie.databinding.LayoutMovieGridBinding
import com.xsis.movie.databinding.LayoutMovieListBinding
import com.xsis.movie.global.Api
import com.xsis.movie.helper.GlideHelper
import com.xsis.movie.interfaces.OnMovieClick
import com.xsis.movie.model.movieModels.Movie
import java.util.*

class CustomMovieAdapter (private val onMovieClick: OnMovieClick, private val viewType : Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val LIST_VIEW_TYPE = 0;
        const val GRID_VIEW_TYPE = 1;
    }

    private var lsMovie : MutableList<Movie> = Collections.emptyList<Movie?>().toMutableList()

    override fun getItemViewType(position: Int): Int {
        if(viewType == 0) return LIST_VIEW_TYPE
        return GRID_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == LIST_VIEW_TYPE){
            val binding = LayoutMovieListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            ListHolder(binding, onMovieClick)
        } else{
            val binding = LayoutMovieGridBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            GridHolder(binding, onMovieClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListHolder){
            bindList(holder, position)
        } else if(holder is GridHolder){
            bindList(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return lsMovie.size
    }

    private fun bindList(holder: ListHolder, position: Int){
        val movieData = lsMovie[position]

        holder.binding.tvMovieTitle.text = movieData.title

        if(movieData.poster_path == null){
            holder.binding.ivMoviePoster.visibility = View.INVISIBLE
            holder.binding.ivImgNotFound.visibility = View.VISIBLE
        } else{
            holder.binding.ivMoviePoster.visibility = View.VISIBLE
            holder.binding.ivImgNotFound.visibility = View.GONE

            GlideHelper.load(
                holder.itemView.context,
                Api.BASE_IMG_URL + movieData.poster_path,
                holder.binding.ivMoviePoster)
        }
    }

    private fun bindList(holder: GridHolder, position: Int){
        val movieData = lsMovie[position]

        holder.binding.tvMovieTitle.text = movieData.title

        if(movieData.poster_path == null){
            holder.binding.ivMoviePoster.visibility = View.INVISIBLE
            holder.binding.ivImgNotFound.visibility = View.VISIBLE
        } else{
            holder.binding.ivMoviePoster.visibility = View.VISIBLE
            holder.binding.ivImgNotFound.visibility = View.GONE

            GlideHelper.load(
                holder.itemView.context,
                Api.BASE_IMG_URL + movieData.poster_path,
                holder.binding.ivMoviePoster)
        }
    }

    fun setMoviesData(lsMovie : List<Movie>){
        val startSize = this.lsMovie.size
        this.lsMovie.addAll(lsMovie)
        val newSize = this.lsMovie.size

        notifyItemRangeInserted(startSize, newSize)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAdapter(){
        lsMovie.clear()
        notifyDataSetChanged()
    }

    fun getMovie(position: Int) : Movie {
        return lsMovie[position]
    }

    class GridHolder(val binding: LayoutMovieGridBinding, onMovieClick: OnMovieClick) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvMovie.setOnClickListener {
                onMovieClick.onMovieClick(absoluteAdapterPosition)
            }
        }
    }

    class ListHolder(val binding: LayoutMovieListBinding, onMovieClick: OnMovieClick) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvMovie.setOnClickListener {
                onMovieClick.onMovieClick(absoluteAdapterPosition)
            }
        }
    }
}