package com.example.musicplayer.models

import android.content.ContentResolver
import android.provider.MediaStore

class Playlist(
    var id: Long,
    private val contentResolver: ContentResolver,
    var name: String, var path: String
) {
    var numOfSongs = numOfSongs()
    private fun numOfSongs(): Int {
        val uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id)
        val projection = arrayOf(MediaStore.Audio.Playlists.Members.AUDIO_ID)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        val count = cursor?.count ?: 0
        cursor?.close()

        return count
    }
}
