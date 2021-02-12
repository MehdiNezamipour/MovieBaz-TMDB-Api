package com.nezamipour.mehdi.moviebaz.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nezamipour.mehdi.moviebaz.data.model.Movie
import com.nezamipour.mehdi.moviebaz.databinding.FragmentMovieDetailsBinding
import com.nezamipour.mehdi.moviebaz.utils.ImageUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {

    private lateinit var movie: Movie
    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie.run {
            binding.textViewLongDescription.text = this.overview
            binding.tvMovieTitle.text = this.title
            binding.textViewReleaseData.text = this.releaseDate
            binding.textViewGenres.text = this.genreIds?.let {
                viewModel.extractMovieGenres(it)
            }
            binding.ratingBar.rating = this.voteAverage!!.div(2)
            binding.textViewRating.text = this.voteAverage.toString()

            context?.let {
                Glide
                    .with(it)
                    .load(this.backdropPath?.let { it1 ->
                        ImageUtils.getLargeImageUrl(it1)
                    })
                    .centerCrop()
                    .into(binding.imageViewBackGround)

                Glide
                    .with(it)
                    .load(movie.posterPath?.let { it1 ->
                        ImageUtils.getImageUrl(it1)
                    })
                    .into(binding.imageViewSmallImage)
            }

        }
    }

}