package com.rizki.alfatest.app.feature.favourite.presentation

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rizki.alfatest.BR
import com.rizki.alfatest.app.common.Resource
import com.rizki.alfatest.app.common.autoCleaned
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.feature.base.BaseFragment
import com.rizki.alfatest.app.feature.favourite.adapter.FavouriteAdapter
import com.rizki.alfatest.databinding.FragmentFavouriteBinding
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
            if (it is Resource.Success) {
                if (it.data?.isNotEmpty() == true) {
                    val favouriteList = ArrayList<FavouriteEntity>()
//                    it.data?.map { item -> favouriteList.add(item) }
                    it.data.mapTo(favouriteList) { item -> item }
                    adapterFavourite.setList(favouriteList)
                } else {
                    binding.tvNoData.isVisible = true
                }
            } else if (it is Resource.Failure) {
                Toast.makeText(requireContext(), it.failureData.message, Toast.LENGTH_SHORT).show()
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