package com.rizki.alfatest.feature.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rizki.alfatest.MovieApp
import com.rizki.alfatest.R
import com.rizki.alfatest.common.GlideApp
import com.rizki.alfatest.common.GlobalFunc
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.data.remote.MovieApi
import com.rizki.alfatest.databinding.DialogMovieDetailBinding
import com.rizki.alfatest.domain.mapper.Movies
import com.rizki.alfatest.feature.home.presentation.HomeViewModel
import com.rizki.alfatest.feature.review.presentation.ReviewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailDialogFragment(
    private val value: Movies
) : BottomSheetDialogFragment() {

    private lateinit var binding: DialogMovieDetailBinding

    private val viewModel: MovieDetailDialogViewmodel by viewModels()

    private var isFav = false
    private var youtubeKey: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRequest()
        initData()
        initComponent()
    }

    private fun initRequest() {
        viewModel.getFavById(value.id)
        viewModel.getVideoByMovieId(value.id)
    }

    fun initData() {
        viewModel.getFavByIdResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Success -> {
                    isFav = true
                    binding.ivFavourite.setImageResource(R.drawable.ic_favorite_solid_24)
                }

                is Resource.Error -> {
                    isFav = false
                    binding.ivFavourite.setImageResource(R.drawable.ic_favorite_border_24)
                }
            }
        }



        viewModel.getVideoByMovieIdResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    // TODO: Display progressBar
                }

                is Resource.Success -> {

                    binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            it.data?.key.let { key ->
                                youTubePlayer.cueVideo(key.toString(), 0f)
                            }
                        }
                    })
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun initComponent() {
        lifecycle.addObserver(binding.youtubePlayer)

        GlideApp.with(MovieApp.applicationContext()).load("${MovieApi.IMAGE_URL}${value.poster_path}")
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(binding.ivPoster)

        binding.tvTitle.text = value.original_title
        binding.tvReleaseDate.text = GlobalFunc.dateConverter(value.release_date)
        binding.tvOverview.text = value.overview

        if (isFav) {
            binding.ivFavourite.setImageResource(R.drawable.ic_favorite_solid_24)
        } else {
            binding.ivFavourite.setImageResource(R.drawable.ic_favorite_border_24)
        }

        binding.ivFavourite.setOnClickListener {
            if (isFav) {
                isFav = false
                binding.ivFavourite.setImageResource(R.drawable.ic_favorite_border_24)
                viewModel.deleteFavourite(FavouriteEntity(value.id, value.original_title, value.overview, value.poster_path))
            } else {
                isFav = true
                binding.ivFavourite.setImageResource(R.drawable.ic_favorite_solid_24)
                viewModel.addFavourite(FavouriteEntity(value.id, value.original_title, value.overview, value.poster_path))
            }
        }

        binding.btnReview.setOnClickListener {
            val myfragment = ReviewFragment.newInstance(value.id)
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.frm_container_main, myfragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            dialog?.dismiss()
        }
    }
}