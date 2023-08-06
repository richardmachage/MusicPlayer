package com.example.musicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.SingleSongRecyclerViewBinding
import com.example.musicplayer.models.Song

class SearchViewRecyclerAdapter(val context: Context,
                                var filteredListOfSongs: ArrayList<Song>) :
    RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = SingleSongRecyclerViewBinding.inflate(inflater,parent, false)
        return SearchViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.apply {
            songNameTextView.text = filteredListOfSongs[position].songName
            artistNameTextView.text = filteredListOfSongs[position].songArtist
        }
    }

    override fun getItemCount(): Int = filteredListOfSongs.size

    fun setList(filteredList : ArrayList<Song>){
        this.filteredListOfSongs = filteredList
        notifyDataSetChanged()
    }

}

class SearchViewHolder(val binding: SingleSongRecyclerViewBinding) :
    RecyclerView.ViewHolder(binding.root)
