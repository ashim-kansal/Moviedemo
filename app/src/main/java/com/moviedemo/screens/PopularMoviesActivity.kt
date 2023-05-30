package com.moviedemo.screens

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.moviedemo.adapters.PagedMoviesAdapter
import com.moviedemo.data.MediaViewModel
import com.moviedemo.databinding.ActivityPopularMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMoviesActivity : BaseActivity() {
    private lateinit var binding: ActivityPopularMoviesBinding
    private val viewModel by viewModels<MediaViewModel>()
    private lateinit var popularMoviesItemsAdapter: PagedMoviesAdapter
    private lateinit var menuProvider: SearchMenuProvider
    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        fetchData()
        setSupportActionBar(binding.toolbar)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.e("orientattion", ""+newConfig.toString())

        super.onConfigurationChanged(newConfig)
    }

    private fun setupUI() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        popularMoviesItemsAdapter = PagedMoviesAdapter()
        binding.popularMoviesList.adapter = popularMoviesItemsAdapter
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                viewModel.result.observe(this@PopularMoviesActivity, Observer {
                    popularMoviesItemsAdapter.submitData(lifecycle, it)
                    popularMoviesItemsAdapter.updateSearchtext(viewModel.currentQuery.value?:"")
                })

            } catch (e: Exception) {
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuProvider = SearchMenuProvider(viewModel)
        menuProvider.onCreateMenu(menu, menuInflater)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuProvider.onPrepareMenu(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return menuProvider.onMenuItemSelected(item) || super.onOptionsItemSelected(item)
    }
}