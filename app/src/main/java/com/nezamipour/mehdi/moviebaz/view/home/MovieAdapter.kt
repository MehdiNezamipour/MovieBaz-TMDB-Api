package com.nezamipour.mehdi.moviebaz.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nezamipour.mehdi.moviebaz.data.model.Movie
import com.nezamipour.mehdi.moviebaz.databinding.ListItemLayoutBinding
import com.nezamipour.mehdi.moviebaz.utils.ImageUtils
import kotlinx.android.synthetic.main.list_item_layout.*
import kotlinx.android.synthetic.main.list_item_layout.view.*

class MovieAdapter(diffCallback: DiffUtil.ItemCallback<Movie>) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val listItemLayoutBinding =
            ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return MovieViewHolder(listItemLayoutBinding)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.

        if (item != null) {
            holder.bind(item)
        }
    }

    // comparator object
    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    // viewHolder class
    class MovieViewHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textViewTitle.text = movie.title
            binding.textViewDescription.text = movie.overview
            binding.ratingBar.rating = movie.voteAverage!! / 2
            binding.textViewRating.text = movie.voteAverage.toString()

            movie.posterPath?.run {
                Glide
                    .with(binding.root)
                    .load(ImageUtils.getImageUrl(this))
                    .into(binding.imageViewMovie)
            }

        }
    }

}