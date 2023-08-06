package com.example.musicplayer.models

import android.net.Uri

data class Song(
    var songId: Long,
    var songName: String,
    var songArtist: String,
    var songDuration: Long,
    var songAlbum: String,
    var songFilePath: String
) {
    var isCurrentlyPlaying = false

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$songName",
            "$songArtist",
            "$songAlbum",
        )

        return matchingCombinations.any { combination ->
            combination.contains(query, ignoreCase = true)
        }
    }
}