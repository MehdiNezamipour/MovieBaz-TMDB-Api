package com.nezamipour.mehdi.moviebaz.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nezamipour.mehdi.moviebaz.data.model.Movie
import com.nezamipour.mehdi.moviebaz.databinding.SearchItemLayoutBinding
import com.nezamipour.mehdi.moviebaz.view.home.MovieListFragmentDirections

class SearchAdapter(var movies: List<Movie>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        val searchItemLayoutBinding =
            SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return SearchViewHolder(searchItemLayoutBinding)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = movies.get(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.

        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    // viewHolder class
    class SearchViewHolder(private val binding: SearchItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textViewTitle.text = movie.title

            binding.root.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(
                        SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(
                            movie
                        )
                    )
            }

        }
    }


}