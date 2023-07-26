package com.example.musicplayer.screens.library

import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.Playlist

class LibraryViewModel : ViewModel() {
    lateinit var listOfPlaylits : ArrayList<Playlist>

    init {
        listOfPlaylits = generatePlaylists()
    }

    private fun generatePlaylists(): ArrayList<Playlist> {
        var listOfPlaylists = ArrayList<Playlist>()

        for(i in 1..5) {
            listOfPlaylists.add(Playlist("Heart Strings", 20))
        }
        return listOfPlaylists
    }
}