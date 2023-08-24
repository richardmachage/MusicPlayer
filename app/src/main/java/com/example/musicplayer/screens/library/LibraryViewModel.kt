package com.example.musicplayer.screens.library

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.Playlist

class LibraryViewModel : ViewModel() {
    //var listOfPlaylits = MutableLiveData{mutableListOf<Playlist>()}

    init {
        //listOfPlaylits = getPlaylists()
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

    fun addNewPlaylists(context: Context, playlistName:String){
        val contentResolver = context.contentResolver
        val values = ContentValues()
        values.put(MediaStore.Audio.Playlists.NAME, playlistName)

        val uri = contentResolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values)

        //uri holds the content URI of the newly created playlist if successful,
        // or null if the insertion failed.
        if (uri != null){
            Toast.makeText(context, "Playlist $playlistName created successfully!", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(context, "Failed to create playlist $playlistName", Toast.LENGTH_SHORT).show()

        }
    }
}