package com.rizki.alfatest.feature.favourite.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rizki.alfatest.BR
import com.rizki.alfatest.R
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.common.autoCleaned
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.databinding.FragmentFavouriteBinding
import com.rizki.alfatest.databinding.FragmentReviewBinding
import com.rizki.alfatest.feature.base.BaseFragment
import com.rizki.alfatest.feature.favourite.adapter.FavouriteAdapter
import com.rizki.alfatest.feature.review.presentation.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>() {

    override val bindingVariable: Int = BR.vmFavourite
    override val binding: FragmentFavouriteBinding by autoCleaned { (FragmentFavouriteBinding.inflate(layoutInflater)) }
    override val viewModel: FavouriteViewModel by viewModels()

    private lateinit var adapterFavourite: FavouriteAdapter
    private lateinit var layoutFavourite: GridLayoutManager

    override fun setupObserver() {
        super.setupObserver()
        viewModel.getFavouriteResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.size != 0){
                        val favouriteList = ArrayList<FavouriteEntity>()
                        it.data?.map { item -> favouriteList.add(item) }
                        adapterFavourite.setList(favouriteList)
                    } else {
                        binding.tvNoData.isVisible = true
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
        viewModel.getFavourite()
    }

    override fun setupComponent() {
        super.setupComponent()
        setupAdapter()
    }

    override fun setupAdapter() {
        super.setupAdapter()
        adapterFavourite = FavouriteAdapter()
        layoutFavourite = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)

        binding.rcvMovie.apply {
            adapter = adapterFavourite
            layoutManager = layoutFavourite

        }

        adapterFavourite.setOnDeleteClickCallback(object : FavouriteAdapter.OnDeleteCallBack {
            override fun onDeleteClick(position: Int, data: FavouriteEntity) {
                viewModel.deleteFavourite(
                    FavouriteEntity(
                        data.id,
                        data.original_title,
                        data.overview,
                        data.poster_path
                    )
                )
                adapterFavourite.deleteItem(position)
            }
        })
    }

}