package com.example.musicplayer.screens.library

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.Playlist

class LibraryViewModel : ViewModel() {
    var listOfPlaylits = mutableListOf<Playlist>()

    init {
        //listOfPlaylits = getPlaylists()
    }

    private fun generatePlaylists(): ArrayList<Playlist> {
        var listOfPlaylists = ArrayList<Playlist>()

        for(i in 1..5) {
          //  listOfPlaylists.add(Playlist("Heart Strings", 20))
        }
        return listOfPlaylists
    }

    fun getPlaylists(contentResolver: ContentResolver): List<Playlist>{
        val listOfPlayLists = mutableListOf<Playlist>()
        val uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.Playlists._ID,MediaStore.Audio.Playlists.NAME)

        contentResolver.query(uri, projection,null,null,null)?.use { cursor->
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Playlists._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME)

            while (cursor.moveToNext()){
                listOfPlayLists.add(Playlist(cursor.getLong(idColumn),cursor.getString(nameColumn)))
            }
        }

        return  listOfPlayLists
    }

    fun addNewPlaylists(contentResolver: ContentResolver){

    }
}