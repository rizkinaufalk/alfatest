package com.rizki.alfatest.app.feature.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.rizki.alfatest.app.common.GlideApp
import com.rizki.alfatest.app.data.remote.MovieApi
import com.rizki.alfatest.app.domain.mapper.Reviews
import com.rizki.alfatest.databinding.ItemReviewBinding

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val item = ArrayList<Reviews>()

    fun setList(reviews: ArrayList<Reviews>) {
        item.addAll(reviews)
        notifyDataSetChanged()
    }

    fun clearList() {
        item.clear()
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(val bind: ItemReviewBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Reviews) {
            bind.apply {
                GlideApp.with(itemView)
                    .load("${MovieApi.IMAGE_URL}${data.avatar_path}")
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivAvatar)

                tvUsername.text = data.username
                tvContent.text = data.content

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val datav: Reviews = item[position]
        holder.bind(item[position])
        holder.bind.apply {

            if (datav.updated_at != null){
                tvUpdate.visibility = 1
            }
        }
    }

    override fun getItemCount(): Int = item.size

}