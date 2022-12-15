package com.xsis.movie.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar.OnMenuItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.xsis.movie.R
import com.xsis.movie.adapter.CustomCarouselAdapter
import com.xsis.movie.adapter.CustomMovieAdapter
import com.xsis.movie.databinding.ActivityLandingPageBinding
import com.xsis.movie.interfaces.OnMovieClick
import com.xsis.movie.model.genreModels.Genre
import com.xsis.movie.model.genreModels.GenreList
import com.xsis.movie.model.movieModels.Movie
import com.xsis.movie.viewmodel.MovieViewModel


class LandingPageActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private lateinit var binding: ActivityLandingPageBinding
    private lateinit var viewModel : MovieViewModel
    private lateinit var carouselAdapter : CustomCarouselAdapter
    private lateinit var latestMovieAdapter : CustomMovieAdapter
    private lateinit var genreMovieAdapter : CustomMovieAdapter
    private lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initAdapter()
        initDialog()
        initRecyclerView()
        initViewModel()
    }

    private fun initToolbar(){
        binding.myToolbar.title = getString(R.string.netplix)

        binding.myToolbar.setOnMenuItemClickListener(this)
    }

    private fun initAdapter(){
        carouselAdapter = CustomCarouselAdapter(this, object : OnMovieClick{
            override fun onMovieClick(position: Int) {
                val data : Movie = carouselAdapter.getMovie(position)
                viewModel.getMovieDetail(data.id)
            }
        })
        latestMovieAdapter = CustomMovieAdapter(object : OnMovieClick{
            override fun onMovieClick(position: Int) {
                val data : Movie = latestMovieAdapter.getMovie(position)
                viewModel.getMovieDetail(data.id)
            }
        }, CustomMovieAdapter.LIST_VIEW_TYPE)

        genreMovieAdapter = CustomMovieAdapter(object : OnMovieClick{
            override fun onMovieClick(position: Int) {
                val data : Movie = genreMovieAdapter.getMovie(position)
                viewModel.getMovieDetail(data.id)
            }
        }, CustomMovieAdapter.LIST_VIEW_TYPE)
    }

    private fun initDialog(){
        fragmentManager = supportFragmentManager;
    }

    private fun initRecyclerView(){
        binding.carouselPopular.adapter = carouselAdapter
        binding.dotsPager.setupWithViewPager(binding.carouselPopular)

        binding.rcLatestMovie.adapter = latestMovieAdapter
        binding.rcLatestMovie.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        binding.rcGenreMovie.adapter = genreMovieAdapter
        binding.rcGenreMovie.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun initChips(genres : List<Genre>){
        for ((i,genre) in genres.withIndex()){
            val chip = Chip(this)
            val drawable = ChipDrawable.createFromAttributes(this, null, 0, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice)
            chip.setChipDrawable(drawable)
            chip.text = genre.name
            chip.chipBackgroundColor = ContextCompat.getColorStateList(this, R.color.chip_selector)
            chip.setTextColor(ContextCompat.getColor(this, R.color.white))
            chip.setOnClickListener(View.OnClickListener {
                viewModel.getMovieWithGenre(genre.id.toString())
            })

            binding.chipGroup.addView(chip)

            if(i == 0){
                binding.chipGroup.check(chip.id)
            }
        }

        binding.chipGroup.isSelectionRequired = true
        binding.chipGroup.isSingleSelection = true
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        viewModel.observePopularMovieLiveData().observe(this) { movieList ->
            carouselAdapter.updateMovies(movieList.results.take(5))
        }

        viewModel.observeLatestMovieLiveData().observe(this) { movieList ->
            latestMovieAdapter.setMoviesData(movieList.results)
        }

        viewModel.observeMovieDetailLiveData().observe(this) { detail ->
            val dialog = MovieDetailDialog(detail)
            dialog.show(fragmentManager, MovieDetailDialog.TAG)
        }

        viewModel.observeGenreLiveData().observe(this) { genreList ->
            initChips(genreList.genres)
        }

        viewModel.observerGenreMovieLiveData().observe(this) { movieList ->
            genreMovieAdapter.clearAdapter()
            genreMovieAdapter.setMoviesData(movieList.results)
            binding.rcGenreMovie.scrollToPosition(0)
        }

        viewModel.getPopularMovies()
        viewModel.getLatestMovies()
        viewModel.getGenre()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        if(menuItem?.itemId == R.id.action_search){
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            println("123")
        }

        return false
    }
}