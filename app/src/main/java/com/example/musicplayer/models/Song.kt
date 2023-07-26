package com.example.musicplayer.models

import android.net.Uri

data class Song (var songId: Long ,var songName: String, var songArtist:String, var songDuration: Long, var songAlbum: String, var songFilePath: String){
    var isCurrentlyPlaying = false
}