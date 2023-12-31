package com.example.musicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapters.PlaylistsRecyclerAdapter.*
import com.example.musicplayer.databinding.SinglePlaylistRecyclerViewBinding
import com.example.musicplayer.models.Playlist

class PlaylistsRecyclerAdapter(
    private val context: Context,
    private val listOfPlaylists: ArrayList<Playlist>
) : RecyclerView.Adapter<PlaylistViewHolder>() {

    var myPlaylists = this.listOfPlaylists

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = SinglePlaylistRecyclerViewBinding.inflate(inflater, parent, false)

        return PlaylistViewHolder(binding)
    }

    override fun getItemCount(): Int = myPlaylists.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.binding.apply {
            playlistNameTextview.text = myPlaylists[position].name
            numberOfSongsTextview.text ="${listOfPlaylists[position].numOfSongs} Songs"
        }
    }

    class PlaylistViewHolder(val binding: SinglePlaylistRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    fun setDataset(listOfPlaylists: ArrayList<Playlist>){
        myPlaylists = listOfPlaylists
    }
}