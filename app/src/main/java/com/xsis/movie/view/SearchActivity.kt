package com.xsis.movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.*
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.xsis.movie.R
import com.xsis.movie.adapter.CustomMovieAdapter
import com.xsis.movie.databinding.ActivitySearchBinding
import com.xsis.movie.helper.GridItemDecoration
import com.xsis.movie.interfaces.OnMovieClick
import com.xsis.movie.model.movieModels.Movie
import com.xsis.movie.viewmodel.MovieViewModel

class SearchActivity : AppCompatActivity(), OnEditorActionListener {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var viewModel : MovieViewModel
    private lateinit var searchAdapter: CustomMovieAdapter
    private lateinit var fragmentManager : FragmentManager

    private var isLoading = true
    private var page = 1
    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager;

        initSearch()
        initAdapter()
        initRecyclerView()
        initViewModel()
    }

    private fun initAdapter(){
        searchAdapter = CustomMovieAdapter(object : OnMovieClick {
            override fun onMovieClick(position: Int) {
                val data : Movie = searchAdapter.getMovie(position)
                viewModel.getMovieDetail(data.id)
            }
        }, CustomMovieAdapter.GRID_VIEW_TYPE)
    }

    private fun initSearch(){
        binding.tvInfo.text = getString(R.string.search_movies)
        binding.etSearch.imeOptions = EditorInfo.IME_ACTION_SEARCH

        binding.etSearch.setOnEditorActionListener(this)
    }

    private fun initRecyclerView(){
        val gridLayoutManager = GridLayoutManager(this, 2)
        val gridItemDecoration = GridItemDecoration(2, resources.getDimension(R.dimen.normal_margin_padding).toInt(), true)

        binding.rcSearch.addItemDecoration(gridItemDecoration)
        binding.rcSearch.adapter = searchAdapter
        binding.rcSearch.layoutManager = gridLayoutManager

        binding.rcSearch.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastItem = gridLayoutManager.findLastVisibleItemPosition()

                if(lastItem > searchAdapter.itemCount / 2 && !isLoading){
                    isLoading = true
                    page++
                    viewModel.getSearchMovie(query, page)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        viewModel.observeSearchMovieLiveData().observe(this) { movieList ->
            if(movieList.results.isEmpty()){
                binding.tvInfo.text = getString(R.string.search_not_found)
                binding.tvInfo.visibility = VISIBLE
                binding.rcSearch.visibility = GONE
            } else{
                binding.tvInfo.visibility = GONE
                binding.rcSearch.visibility = VISIBLE

                isLoading = false
                searchAdapter.setMoviesData(movieList.results)
            }
        }

        viewModel.observeMovieDetailLiveData().observe(this) { detail ->
            val dialog = MovieDetailDialog(detail)
            dialog.show(fragmentManager, MovieDetailDialog.TAG)
        }

        viewModel.getPopularMovies()
        viewModel.getLatestMovies()
    }

    private fun searchMovie(){
        query = binding.etSearch.text.toString()

        if(query == "" || query.equals(null)){
            Toast.makeText(this, getString(R.string.empty_search), Toast.LENGTH_SHORT).show()
            return
        }

        page = 1
        searchAdapter.clearAdapter()
        viewModel.getSearchMovie(query ,page)
    }

    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            searchMovie()
        }
        return true;
    }
}