package com.rizki.alfatest.feature.home.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.Target
import com.rizki.alfatest.common.GlideApp
import com.rizki.alfatest.data.remote.MovieApi
import com.rizki.alfatest.domain.model.Movies
import com.rizki.alfatest.databinding.ItemMovieBinding

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewholder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    private val item = ArrayList<Movies>()

    fun setList(movies: ArrayList<Movies>) {
        item.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearList() {
        item.clear()
        notifyDataSetChanged()
    }


    inner class MovieViewholder(val bind: ItemMovieBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Movies) {
            bind.apply {
                GlideApp.with(itemView)
                    .load("${MovieApi.IMAGE_URL}${data.poster_path}")
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(ivMovie)

                Log.d(TAG, "${MovieApi.IMAGE_URL}${data.poster_path}")

                bind.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewholder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewholder(view)
    }

    override fun onBindViewHolder(holder: MovieViewholder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int = item.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Movies)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}