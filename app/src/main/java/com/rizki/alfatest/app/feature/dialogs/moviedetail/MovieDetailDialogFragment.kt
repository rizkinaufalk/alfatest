package com.rizki.alfatest.app.feature.dialogs.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rizki.alfatest.app.MovieApp
import com.rizki.alfatest.R
import com.rizki.alfatest.app.common.GlideApp
import com.rizki.alfatest.app.common.GlobalFunc
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.data.remote.MovieApi
import com.rizki.alfatest.app.domain.mapper.Movies
import com.rizki.alfatest.databinding.DialogMovieDetailBinding
import com.rizki.alfatest.ext.delegate.DisplayMessage
import com.rizki.alfatest.ext.delegate.DisplayMessageImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailDialogFragment(
    private val value: Movies
) : BottomSheetDialogFragment(),
    DisplayMessage by DisplayMessageImpl() {

    private lateinit var binding: DialogMovieDetailBinding

    private val viewModel: MovieDetailDialogViewmodel by viewModels()

    private var isFav = false

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
            if (it is Resource.Success) {
                if (it.data != null) {
                    isFav = true
                    binding.ivFavourite.setImageResource(R.drawable.ic_favorite_solid_24)
                } else {
                    isFav = false
                    binding.ivFavourite.setImageResource(R.drawable.ic_favorite_border_24)
                }
            } else if (it is Resource.Failure) {
                Toast.makeText(requireContext(), it.failureData.message, Toast.LENGTH_SHORT).show()
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

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.failureData.message, Toast.LENGTH_SHORT).show()
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
                showToastCenter(requireContext(), "Removed From Favourite", R.drawable.ic_favorite_border_24)
                viewModel.deleteFavourite(FavouriteEntity(value.id, value.original_title, value.overview, value.poster_path))
            } else {
                isFav = true
                binding.ivFavourite.setImageResource(R.drawable.ic_favorite_solid_24)
                showToastCenter(requireContext(), "Add To Favourite", R.drawable.ic_favorite_solid_24)
                viewModel.addFavourite(FavouriteEntity(value.id, value.original_title, value.overview, value.poster_path))
            }
        }

//        binding.anmFavourite.setOnClickListener {
//            binding.anmFavourite.playAnimation()
//            binding.anmFavourite.progress = 0f
//        }

        binding.btnReview.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(com.rizki.alfatest.app.common.Args.PARAM_ONE, value.id)
            }

            findNavController().navigate(R.id.review_fragment, bundle)

            dialog?.dismiss()
        }
    }
}