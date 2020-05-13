package com.example.moviesdb.ui.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdb.R
import com.example.moviesdb.data.model.Search

class MoviesListAdapter(
    private val clickCallback: ClickCallback
) :
    PagedListAdapter<Search, RecyclerView.ViewHolder>(SEARCH_COMPARATOR) {

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.txtMovieTitle)
        val poster = itemView.findViewById<AppCompatImageView>(R.id.imgAlbumCover)

        fun bindItems(
            search: Search
        ) {
            title.text = search.title
            Glide.with(itemView.context)
                .load(search.poster)
                .into(poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        val holder = MoviesViewHolder(view)
        holder.itemView.setOnClickListener {
            val item = getItem(holder.adapterPosition)
            item.let {
                clickCallback.onClick(item!!)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviesViewHolder).bindItems(getItem(position)!!)
    }

    interface ClickCallback {
        fun onClick(search: Search)
    }

    companion object {
        private val SEARCH_COMPARATOR = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean =
                oldItem == newItem


            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean =
                oldItem.imdbID == newItem.imdbID

        }
    }


}