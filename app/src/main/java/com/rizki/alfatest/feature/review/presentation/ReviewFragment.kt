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
import com.rizki.alfatest.BR
import com.rizki.alfatest.common.Args
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.common.autoCleaned
import com.rizki.alfatest.databinding.FragmentHomeBinding
import com.rizki.alfatest.domain.mapper.Reviews
import com.rizki.alfatest.feature.review.adapter.ReviewAdapter
import com.rizki.alfatest.databinding.FragmentReviewBinding
import com.rizki.alfatest.feature.base.BaseFragment
import com.rizki.alfatest.feature.home.presentation.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment() : BaseFragment<FragmentReviewBinding, ReviewViewModel>() {

    override val bindingVariable: Int = BR.vmReview
    override val binding: FragmentReviewBinding by autoCleaned { (FragmentReviewBinding.inflate(layoutInflater)) }
    override val viewModel: ReviewViewModel by viewModels()

    private lateinit var adapterReview: ReviewAdapter
    private lateinit var layoutMovie: LinearLayoutManager

    override fun setupObserver() {
        super.setupObserver()
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
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun initAPI() {
        super.initAPI()
        arguments?.let { viewModel.getReview(it.getInt(Args.PARAM_ONE)) }
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