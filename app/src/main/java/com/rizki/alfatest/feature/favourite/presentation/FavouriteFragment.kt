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
import com.rizki.alfatest.R
import com.rizki.alfatest.common.Resource
import com.rizki.alfatest.data.local.entity.FavouriteEntity
import com.rizki.alfatest.databinding.FragmentFavouriteBinding
import com.rizki.alfatest.feature.favourite.adapter.FavouriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private lateinit var binding: FragmentFavouriteBinding

    private val viewModel: FavouriteViewModel by viewModels()

    private lateinit var adapterFavourite: FavouriteAdapter
    private lateinit var layoutFavourite: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initComponent()
        initData()
        observeData()
    }

    private fun observeData() {
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

    private fun initData() {
        viewModel.getFavourite()
    }

    private fun initComponent() {
        initAdapter()
    }

    private fun initAdapter() {
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