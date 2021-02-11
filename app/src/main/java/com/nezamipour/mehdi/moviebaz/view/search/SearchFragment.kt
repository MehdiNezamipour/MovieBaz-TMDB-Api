package com.nezamipour.mehdi.moviebaz.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nezamipour.mehdi.moviebaz.databinding.FragmentSearchBinding
import org.koin.android.ext.android.inject


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by inject()
    private lateinit var adapter: SearchAdapter
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.movies.observe(this, Observer {
            adapter.movies = it
            adapter.notifyDataSetChanged()
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSearchResult.layoutManager = LinearLayoutManager(context)
        adapter = SearchAdapter(emptyList())
        binding.recyclerViewSearchResult.adapter = adapter

        binding.toolbarSearch.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty())
                    viewModel.searchBetweenMovies(s.toString())
                if (s != null) {
                    if (s.isEmpty())
                        adapter.movies = emptyList()
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
            }
    }
}