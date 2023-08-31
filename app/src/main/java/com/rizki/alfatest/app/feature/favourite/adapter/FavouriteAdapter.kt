package com.rizki.alfatest.app.feature.favourite.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.Target
import com.rizki.alfatest.app.common.GlideApp
import com.rizki.alfatest.app.data.local.entity.FavouriteEntity
import com.rizki.alfatest.app.data.remote.MovieApi
import com.rizki.alfatest.databinding.ItemFavouriteBinding

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewholder>() {

    private var onItemClickCallback: OnDeleteCallBack? = null
    private val item = ArrayList<FavouriteEntity>()

    fun setList(movies: ArrayList<FavouriteEntity>) {
        item.addAll(movies)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        item.removeAt(position)
        notifyDataSetChanged()
    }

    fun clearList() {
        item.clear()
        notifyDataSetChanged()
    }


    inner class FavouriteViewholder(val bind: ItemFavouriteBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: FavouriteEntity, position: Int) {
            bind.apply {
                GlideApp.with(itemView)
                    .load("${MovieApi.IMAGE_URL}${data.poster_path}")
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(ivPoster)

                tvTitle.text = data.original_title
                tvOverview.text = data.overview

                ivDelete.setOnClickListener {

                }

                Log.d(ContentValues.TAG, "${MovieApi.IMAGE_URL}${data.poster_path}")

                ivDelete.setOnClickListener {
                    onItemClickCallback?.onDeleteClick(position, data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewholder {
        val view = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewholder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewholder, position: Int) {
        holder.bind(item[position], position)
    }

    override fun getItemCount(): Int = item.size

    interface OnDeleteCallBack {
        fun onDeleteClick(position: Int, data: FavouriteEntity)
    }

    fun setOnDeleteClickCallback(onDeleteClickCallBack: OnDeleteCallBack) {
        this.onItemClickCallback = onDeleteClickCallBack
    }
}