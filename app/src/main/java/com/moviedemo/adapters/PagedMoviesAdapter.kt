package com.moviedemo.adapters

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaltest.data.model.MoveListModel
import com.moviedemo.databinding.ItemMediaBinding
import com.moviedemo.extensions.setPosterUrl


class PagedMoviesAdapter :
        PagingDataAdapter<MoveListModel, PagedMoviesAdapter.MovieViewHolder>(MovieDiffCallback()) {
    private var searchText = "";

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val media = getItem(position)
        holder.bind(media,searchText)
    }

    fun updateSearchtext(text: String) {
        searchText = text;
    }


    inner class MovieViewHolder(
        var binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MoveListModel?, text: String
        ) {
            if (movie == null) {
                return
            }

            binding.posterImage.setPosterUrl(movie.poster_image)
            binding.name.text = getSpannableText(movie.name, text)
        }

        private fun getSpannableText(name: String, highlight : String): SpannableString {

            val spannable = SpannableString(name)
            val startIndex = name.indexOf(highlight, ignoreCase = true)
            val endIndex = startIndex + highlight.length

            if (startIndex != -1) {
                val colorSpan = ForegroundColorSpan(Color.YELLOW)
                spannable.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            return spannable
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<MoveListModel>() {
        override fun areItemsTheSame(oldItem: MoveListModel, newItem: MoveListModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoveListModel, newItem: MoveListModel): Boolean {
            return oldItem == newItem
        }
    }
}