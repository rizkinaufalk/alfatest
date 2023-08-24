package com.rizki.alfatest.feature.home.presentation

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizki.alfatest.BR
import com.rizki.alfatest.R
import com.rizki.alfatest.common.Constants
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.common.autoCleaned
import com.rizki.alfatest.data.remote.dto.GenresDto
import com.rizki.alfatest.data.remote.dto.toResult
import com.rizki.alfatest.databinding.FragmentHomeBinding
import com.rizki.alfatest.domain.mapper.Movies
import com.rizki.alfatest.ext.delegate.displaymessage.DisplayMessage
import com.rizki.alfatest.ext.delegate.displaymessage.DisplayMessageImpl
import com.rizki.alfatest.feature.base.BaseFragment
import com.rizki.alfatest.feature.dialogs.LoadingDialog
import com.rizki.alfatest.feature.dialogs.SpinnerHelper
import com.rizki.alfatest.feature.dialogs.moviedetail.MovieDetailDialogFragment
import com.rizki.alfatest.feature.home.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), MovieAdapter.OnClickMovie{

    lateinit var token: String
    var page = 1
    var genre = ""
    var isGenre = false


    override val bindingVariable: Int = BR.vmHome
    override val binding: FragmentHomeBinding by autoCleaned { (FragmentHomeBinding.inflate(layoutInflater)) }
    override val viewModel: HomeViewModel by viewModels()

    private lateinit var adapterMovie: MovieAdapter
    private lateinit var layoutMovie: GridLayoutManager


    override fun setupObserver() {
        super.setupObserver()
        viewModel.getMovieResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    showLoading()
                }

                is Resource.Success -> {
                    
                    val movieList = ArrayList<Movies>()

                    it.data?.results?.map { item -> movieList.add(item.toResult()) }

                    adapterMovie.setList(movieList)

                    hideLoading()
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getMovieByGenreResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    showLoading()
                }

                is Resource.Success -> {
                    
                    val movieList = ArrayList<Movies>()

                    it.data?.results?.map { item -> movieList.add(item.toResult()) }

                    adapterMovie.setList(movieList)

                    hideLoading()


                }

                is Resource.Error -> {
                    
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getGenreResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    showLoading()
                }

                is Resource.Success -> {
                    
                    val genreList = ArrayList<GenresDto>()
                    genreList.add(GenresDto(0, "Genre"))
                    it.data?.map { genreList.add(it) }
                    showGenreList(genreList)

                    hideLoading()
                }

                is Resource.Error -> {
                    
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initAPI() {
        super.initAPI()
        token = Constants.TOKEN
        viewModel.getMovie(page.toString())
        viewModel.getGenre()
    }

    override fun setupComponent() {
        super.setupComponent()
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
                    }

                    super.onScrolled(recyclerView, dx, dy)

                }
            })

            ivFavourite.setOnClickListener {
                findNavController().navigate(R.id.favourite)

            }
        }
    }

    override fun setupAdapter() {
        super.setupAdapter()
        layoutMovie = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        adapterMovie = MovieAdapter(this)

        binding.rcvMovie.apply {
            adapter = adapterMovie
            layoutManager = layoutMovie
        }
    }

    private fun showGenreList(genres: ArrayList<GenresDto>) {
        val spinnerSetup = SpinnerHelper(requireContext(), binding.spGenre)
        spinnerSetup.setupSpinner(R.layout.item_spinner, genres.map { it.name })
        {itemSelected, indexSelected ->

            val genreId = genres[indexSelected].id

            if (genreId != 0) {
                page = 1
                isGenre = true
                binding.tvSection.text = "Genre"
                genre = genreId.toString()
                adapterMovie.clearList()

                viewModel.getMovieByGenre(genre, page)
            } else {
                binding.tvSection.text = "Popular"
                page = 1
                viewModel.getMovie(page.toString())
                adapterMovie.clearList()
            }
        }
    }

    override fun onClickMovie(value: Movies) {

        val bottomSheetFragment = MovieDetailDialogFragment(value)
        bottomSheetFragment.show(childFragmentManager, "")
    }

}