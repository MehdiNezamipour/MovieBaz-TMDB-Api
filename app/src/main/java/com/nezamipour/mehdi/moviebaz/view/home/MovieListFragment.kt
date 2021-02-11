package com.nezamipour.mehdi.moviebaz.view.home

import android.app.SearchManager
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezamipour.mehdi.moviebaz.R
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
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


    /////////////////////////////////////////////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_activity_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        //val to = intArrayOf(R.id.item_label)

        val suggestionAdapter = SimpleCursorAdapter(
            context,
            android.R.layout.simple_list_item_1,
            null,
            arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1),
            IntArray(android.R.id.text1),
            0
        )

        val suggestions: List<String> = ArrayList()
        searchView.suggestionsAdapter = suggestionAdapter


        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                searchView.setQuery(suggestions[position], false)
                searchView.clearFocus()
                return true
            }
        })



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.searchBetweenMovies(newText)

                    fun success(autocomplete: Autocomplete, response: Response?) {
                        suggestions.clear()
                        suggestions.addAll(autocomplete.suggestions)
                        val columns = arrayOf(
                            BaseColumns._ID,
                            SearchManager.SUGGEST_COLUMN_TEXT_1,
                            SearchManager.SUGGEST_COLUMN_INTENT_DATA
                        )
                        val cursor = MatrixCursor(columns)
                        for (i in 0 until autocomplete.suggestions.size()) {
                            val tmp = arrayOf(
                                i.toString(),
                                autocomplete.suggestions.get(i),
                                autocomplete.suggestions.get(i)
                            )
                            cursor.addRow(tmp)
                        }
                        suggestionAdapter.swapCursor(cursor)
                    }

                   /* fun failure(error: RetrofitError) {
                        Toast.makeText(
                            this@SearchFoodActivity,
                            error.getMessage(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }*/
                })
                return false
            }
        })



        return super.onCreateOptionsMenu(menu, inflater)

    }
}