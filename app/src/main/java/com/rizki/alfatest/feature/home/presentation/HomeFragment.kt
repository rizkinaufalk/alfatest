package com.rizki.alfatest.feature.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.rizki.alfatest.MovieApp
import com.rizki.alfatest.R
import com.rizki.alfatest.common.*
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.data.remote.MovieApi
import com.rizki.alfatest.data.remote.dto.GenresDto
import com.rizki.alfatest.data.remote.dto.toResult
import com.rizki.alfatest.databinding.FragmentHomeBinding
import com.rizki.alfatest.domain.mapper.Movies
import com.rizki.alfatest.feature.dialogs.MovieDetailDialogFragment
import com.rizki.alfatest.feature.favourite.presentation.FavouriteFragment
import com.rizki.alfatest.feature.home.adapter.MovieAdapter
import com.rizki.alfatest.feature.review.presentation.ReviewFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), MovieAdapter.OnClickMovie {

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var token: String
    var page = 1
    var genre = ""
    var isGenre = false
    var youtubeKey: String? = null
    var isfav = false

    private var binding: FragmentHomeBinding by autoCleaned {
        (FragmentHomeBinding.inflate(
            layoutInflater
        ))
    }
    private var movieId: Int = 0

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapterMovie: MovieAdapter
    private lateinit var layoutMovie: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initComponent()
        initData()
        observeData()
        initAdapter()
    }

    private fun observeData() {
        viewModel.getMovieResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    // TODO: Display progressBar
                }

                is Resource.Success -> {
                    val movieList = ArrayList<Movies>()

                    it.data?.results?.map { item -> movieList.add(item.toResult()) }

                    adapterMovie.setList(movieList)


                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        viewModel.getMovieByGenreResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    // TODO: Display progressBar
                }

                is Resource.Success -> {
                    val movieList = ArrayList<Movies>()

                    it.data?.results?.map { item -> movieList.add(item.toResult()) }

                    adapterMovie.setList(movieList)


                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        viewModel.getGenreResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    // TODO: Display progressBar
                }

                is Resource.Success -> {
                    val genreList = ArrayList<GenresDto>()
                    genreList.add(GenresDto(0, "Genre"))
                    it.data?.map { genreList.add(it) }
                    showGenreList(genreList)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initData() {
        token = Constants.TOKEN
        viewModel.getMovie(page.toString())
        viewModel.getGenre()
    }

    private fun initComponent() {
        isGenre = false

        binding.apply {

            rcvMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = layoutMovie.childCount
                    val pastVisibleItem = layoutMovie.findFirstVisibleItemPosition()
                    val total = adapterMovie.itemCount

                    if (visibleItemCount + pastVisibleItem >= total) {

                        if (!isGenre) {
                            if (page > 1) {
                                viewModel.getMovie(page.toString())
                            }
                            page++
                        } else {
                            if (page > 1) {
                                viewModel.getMovieByGenre(genre, page)
                            }
                            page++
                        }
//                    showLoading(3, true)
                    }

                    super.onScrolled(recyclerView, dx, dy)

                }
            })

            ivFavourite.setOnClickListener {

                val myfragment = FavouriteFragment()
                val fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.frm_container_main, myfragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

        }

    }

    private fun initAdapter() {
        layoutMovie = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        adapterMovie = MovieAdapter(this)

        binding.rcvMovie.apply {
            adapter = adapterMovie
            layoutManager = layoutMovie

        }

//        adapterMovie.setOnItemClickCallback(this)
    }

    private fun showGenreList(genres: ArrayList<GenresDto>) {
        val genreSpinner: Spinner = binding.spGenre
        genreSpinner.onItemSelectedListener
        val spMovieAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner, genres.map { it.name })
        spMovieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spGenre.adapter = spMovieAdapter

        genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                val idGenre = genres[position].id

                if (idGenre != 0) {
                    page = 1
                    isGenre = true
                    binding.tvSection.text = "Genre"
                    genre = idGenre.toString()
                    adapterMovie.clearList()

                    viewModel.getMovieByGenre(genre, page)
                } else {
                    binding.tvSection.text = "Popular"
                    page = 1
                    viewModel.getMovie(page.toString())
                    adapterMovie.clearList()
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    override fun onClickMovie(value: Movies) {

        val bottomSheetFragment = MovieDetailDialogFragment(value)
        bottomSheetFragment.show(childFragmentManager, "")
    }

}