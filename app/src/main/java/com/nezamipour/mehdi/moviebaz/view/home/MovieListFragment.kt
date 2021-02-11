package com.nezamipour.mehdi.moviebaz.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezamipour.mehdi.moviebaz.databinding.FragmentMovieListBinding
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MovieListFragment : Fragment() {

    private lateinit var mBinding: FragmentMovieListBinding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: HomeViewModel by inject()


    companion object {
        @JvmStatic
        fun newInstance() =
            MovieListFragment().apply {

            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MovieAdapter(MovieAdapter.MovieComparator)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMovieListBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewMovies.adapter = movieAdapter

        mBinding.recyclerViewMovies.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                movieAdapter.submitData(it)
            }
        }


        // show the loading state for te first load
        movieAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                // Show ProgressBar
                mBinding.progressBar.visibility = View.VISIBLE
            } else {
                // Hide ProgressBar
                mBinding.progressBar.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
            }
        }

    }


}