package com.rizki.alfatest.app.feature.review.presentation

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizki.alfatest.BR
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.common.autoCleaned
import com.rizki.alfatest.app.domain.mapper.Reviews
import com.rizki.alfatest.app.feature.review.adapter.ReviewAdapter
import com.rizki.alfatest.app.feature.base.BaseFragment
import com.rizki.alfatest.databinding.FragmentReviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment() : BaseFragment<FragmentReviewBinding, ReviewViewModel>() {

    override val bindingVariable: Int = BR.vmReview
    override val binding: FragmentReviewBinding by autoCleaned {
        (FragmentReviewBinding.inflate(
            layoutInflater
        ))
    }
    override val viewModel: ReviewViewModel by viewModels()

    private lateinit var adapterReview: ReviewAdapter
    private lateinit var layoutMovie: LinearLayoutManager

    override fun setupObserver() {
        super.setupObserver()
        viewModel.getReviewResult.observe(viewLifecycleOwner) {
            when (it) {

                is Resource.Loading -> {
                    // TODO: Display progressBar
                }

                is Resource.Success -> {
                    val reviewList = ArrayList<Reviews>()

                    it.data?.map { item -> reviewList.add(item) }
                    adapterReview.setList(reviewList)

                    if (reviewList.size == 0) {
                        binding.tvNoData.visibility = 1
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.failureData.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }

    override fun initAPI() {
        super.initAPI()
        arguments?.let { viewModel.getReview(it.getInt(com.rizki.alfatest.app.common.Args.PARAM_ONE)) }
    }

    override fun setupComponent() {
        super.setupComponent()
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