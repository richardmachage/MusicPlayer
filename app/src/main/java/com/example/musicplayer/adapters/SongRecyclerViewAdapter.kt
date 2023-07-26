package com.example.musicplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.SingleSongRecyclerViewBinding
import com.example.musicplayer.models.Song

class SongRecyclerViewAdapter(val context: Context,
                              var listOfSongs: ArrayList<Song>) :
    RecyclerView.Adapter<SongRecyclerViewAdapter.songViewHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): songViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = SingleSongRecyclerViewBinding.inflate(inflater,parent, false)
        //val view = inflater.inflate(R.layout.single_song_recycler_view, parent,false)
        return songViewHolder(binding)
    }

    override fun onBindViewHolder(holder: songViewHolder, position: Int) {
        holder.binding.apply {
            songNameTextView.text = listOfSongs[position].songName
            artistNameTextView.text = listOfSongs[position].songArtist

            if (listOfSongs[position].isCurrentlyPlaying){
                musicPlayingAnimation.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = listOfSongs.size


    class songViewHolder(val binding: SingleSongRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)
}