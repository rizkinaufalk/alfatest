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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.rizki.alfatest.MovieApp
import com.rizki.alfatest.R
import com.rizki.alfatest.common.Constants
import com.rizki.alfatest.common.GlideApp
import com.rizki.alfatest.common.GlobalFunc
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.data.remote.MovieApi
import com.rizki.alfatest.data.remote.dto.toResult
import com.rizki.alfatest.databinding.FragmentHomeBinding
import com.rizki.alfatest.domain.model.Genres
import com.rizki.alfatest.domain.model.Movies
import com.rizki.alfatest.feature.favourite.presentation.FavouriteFragment
import com.rizki.alfatest.feature.home.adapter.MovieAdapter
import com.rizki.alfatest.feature.review.presentation.ReviewFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var token: String
    var page = 1
    var genre = ""
    var isGenre = false
    var youtubeKey : String? = null

    private lateinit var binding: FragmentHomeBinding
    private lateinit var movies: Movies

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
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
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
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                }
            }

        }

        viewModel.getVideoByMovieIdResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    // TODO: Display progressBar
                }

                is Resource.Success -> {
                    youtubeKey = it.data?.get(0)?.key
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                }
            }

        }

        viewModel.getGenreResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    // TODO: Display progressBar
                }

                is Resource.Success -> {
                    val genreList = ArrayList<Genres>()
                    genreList.add(Genres("Genre", 0))
                    it.data?.map { genreList.add(it) }
                    showGenreList(genreList)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                }
            }
        }

        viewModel.getFavByIdResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Success -> {
                    showMovieDetail(
                        movies.id,
                        movies.poster_path,
                        movies.original_title,
                        movies.release_date,
                        movies.overview,
                        true
                    )
                }

                is Resource.Error -> {
                    showMovieDetail(
                        movies.id,
                        movies.poster_path,
                        movies.original_title,
                        movies.release_date,
                        movies.overview,
                        false
                    )
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
        initAdapter()

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
        adapterMovie = MovieAdapter()
        layoutMovie = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        binding.rcvMovie.apply {
            adapter = adapterMovie
            layoutManager = layoutMovie

        }

        adapterMovie.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movies) {
//                setFragmentResult("requestKey", bundleOf("movieId" to item.id))

                viewModel.getFavById(data.id)
                viewModel.getVideoByMovieId(data.id)

                movies = Movies(
                    false,
                    data.id,
                    data.original_title,
                    data.overview,
                    data.poster_path,
                    data.release_date
                )
            }
        })
    }

    private fun showMovieDetail(
        movieId: Int,
        posterPath: String,
        title: String,
        releaseDate: String,
        overview: String,
        isFav: Boolean
    ) {
        val dialog = BottomSheetDialog(this.requireContext())
        dialog.setContentView(R.layout.dialog_bottom_sheet_detail)
        var mutableIsfav = isFav

        val ivPoster = dialog.findViewById<ImageView>(R.id.iv_poster)
        val tvTitle = dialog.findViewById<TextView>(R.id.tv_title)
        val tvReleaseDate = dialog.findViewById<TextView>(R.id.tv_release_date)
        val tvOverview = dialog.findViewById<TextView>(R.id.tv_overview)
        val btnReview = dialog.findViewById<Button>(R.id.btn_review)
        val ivFavourite = dialog.findViewById<ImageView>(R.id.iv_favourite)
        val youtubeView: YouTubePlayerView? = dialog.findViewById<YouTubePlayerView>(R.id.youtube_player)

        GlideApp.with(MovieApp.applicationContext()).load("${MovieApi.IMAGE_URL}${posterPath}")
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(ivPoster!!)

        tvTitle?.text = title
        tvReleaseDate?.text = GlobalFunc.dateConverter(releaseDate)
        tvOverview?.text = overview

        if (isFav) {
            ivFavourite?.setImageResource(R.drawable.ic_favorite_solid_24)
        } else {
            ivFavourite?.setImageResource(R.drawable.ic_favorite_border_24)
        }

        ivFavourite?.setOnClickListener {
            if (mutableIsfav) {
                mutableIsfav = false
                ivFavourite.setImageResource(R.drawable.ic_favorite_border_24)
                viewModel.deleteFavourite(FavouriteEntity(movieId, title, overview, posterPath))
            } else {
                mutableIsfav = true
                ivFavourite.setImageResource(R.drawable.ic_favorite_solid_24)
                viewModel.addFavourite(FavouriteEntity(movieId, title, overview, posterPath))
            }
        }

        btnReview?.setOnClickListener {
            val myfragment = ReviewFragment.newInstance(movieId)
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.frm_container_main, myfragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showGenreList(genres: ArrayList<Genres>) {
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

}