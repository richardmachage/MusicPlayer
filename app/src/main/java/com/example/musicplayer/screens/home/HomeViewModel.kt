package com.example.musicplayer.screens.home

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.Song

class HomeViewModel : ViewModel() {


    lateinit var listOfSongs : ArrayList<Song>
    lateinit var context: Context


    fun setListOfSongs(){
       // listOfSongs = getMusicFilesFromStorage(context)
    }


    /*private fun getMusicFilesFromStorage(context: Context): ArrayList<Song>{
        val listOfMusic = ArrayList<Song>()

        //initialize content resolver and the music URI
        var contentResolver = context.contentResolver
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        //columns to retrieve from the mediaStore query
        val projection = arrayOf(MediaStore.Audio.Media._ID ,MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ALBUM)

        //assign query results to a cursor
        val cursor = contentResolver.query(musicUri, projection, null,null,null)

        //get contents of cursor into arraylist
        cursor?.use {cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)

            while (cursor.moveToNext()){
                val songId = cursor.getLong(idColumn)
                val songName = cursor.getString(titleColumn)
                val songArtist = cursor.getString(artistColumn)
                val songDuration = cursor.getLong(durationColumn)
                val songAlbum = cursor.getString(albumColumn)

                val musicFile = Song(songId,songName,songArtist,songDuration,songAlbum, )
                listOfMusic.add(musicFile)
            }
        }
        return listOfMusic
    }*/


}