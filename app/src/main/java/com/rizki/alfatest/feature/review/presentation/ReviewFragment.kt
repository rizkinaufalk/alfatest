package com.rizki.alfatest.feature.review.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.domain.model.Reviews
import com.rizki.alfatest.feature.review.adapter.ReviewAdapter
import com.rizki.alfatest.databinding.FragmentReviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment() : Fragment() {

    private var movieId: Int = 0
    companion object {
        fun newInstance(movieid: Int): ReviewFragment {
            val reviewFragment: ReviewFragment = ReviewFragment()
            reviewFragment.movieId = movieid
            return reviewFragment
        }
    }

    private val viewModel: ReviewViewModel by viewModels()
    private lateinit var adapterReview: ReviewAdapter
    private lateinit var layoutMovie: LinearLayoutManager

    private lateinit var binding: FragmentReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initComponent()
        initData()
        observeData()

    }

    private fun observeData() {

        viewModel.getReviewResult.observe(viewLifecycleOwner){
            when (it) {

                is Resource.Loading -> {
                // TODO: Display progressBar
                }

                is Resource.Success -> {
                    val reviewList = ArrayList<Reviews>()

                    it.data?.map { item ->  reviewList.add(item)}
                    adapterReview.setList(reviewList)

                    if (reviewList.size == 0){
                        binding.tvNoData.visibility = 1
                    }


                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                }
            }
        }

    }

    private fun initData() {
        viewModel.getReview(movieId)
    }

    private fun initComponent() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapterReview = ReviewAdapter()
        layoutMovie = LinearLayoutManager(context, GridLayoutManager.VERTICAL, false)

        binding.rcvMovie.apply {
            adapter = adapterReview
            layoutManager = layoutMovie

        }
    }

}