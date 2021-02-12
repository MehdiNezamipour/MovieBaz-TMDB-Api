package com.nezamipour.mehdi.moviebaz.view.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezamipour.mehdi.moviebaz.R
import com.nezamipour.mehdi.moviebaz.databinding.FragmentMovieListBinding
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: HomeViewModel by sharedViewModel()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllGenre()
        movieAdapter = MovieAdapter(MovieAdapter.MovieComparator)
        // apply genres filter with create new data source for movie adapter
        viewModel.selectedGenres.observe(this, {
            lifecycleScope.launch {
                viewModel.getGenresMovieFlow().collectLatest {
                    movieAdapter.submitData(it)
                }
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewMovies.adapter = movieAdapter


        binding.recyclerViewMovies.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.getPopularMovieFlow().collectLatest {
                movieAdapter.submitData(it)
            }
        }


        // show the loading state for te first load
        movieAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                // Show ProgressBar
                binding.progressBar.visibility = View.VISIBLE
            } else {
                // Hide ProgressBar
                binding.progressBar.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_search -> view?.let {
                Navigation.findNavController(it)
                    .navigate(MovieListFragmentDirections.actionMovieListFragmentToSearchFragment())
                return true
            }
            R.id.app_bar_filter -> view?.let {
                Navigation.findNavController(it)
                    .navigate(MovieListFragmentDirections.actionMovieListFragmentToGenresFragment())
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}