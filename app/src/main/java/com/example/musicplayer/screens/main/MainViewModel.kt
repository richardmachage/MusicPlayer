package com.example.musicplayer.screens.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.models.Song
import java.util.concurrent.TimeUnit
import kotlin.random.Random

const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 111
const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 112
//const val MY_PERMISSIONS_REQUEST_MANAGE_EXTERNAL_STORAGE = 113

class MainViewModel : ViewModel() {
    lateinit var listOfSongs: ArrayList<Song>
    lateinit var context: Context
    var currentMusic = MutableLiveData<Song>()
    var playButtonState = MutableLiveData<Boolean>()
    var currentSongPosition = MutableLiveData<Long>()

    val myPlayer = MediaPlayer()
    var isResourceSet: Boolean
    var currentSongIndex = 0
    var shuffleSongs = false


    init {
        setPlayer()
        isResourceSet = false
        myPlayer.setOnCompletionListener {
            playNext(shuffleSongs)
            playSong()
        }

    }


    fun setListOfSongs() {
        listOfSongs = getMusicFilesFromStorage(context)
    }

    fun setCurrentSongObject() {
        currentMusic.value = listOfSongs[currentSongIndex]
    }

    fun checkIfPermissionGranted(context: Context, permissionName: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permissionName
        ) != PackageManager.PERMISSION_GRANTED
    }


    fun requestPermission(context: Context, permissionName: String, permissionRequest: Int) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(permissionName),
            permissionRequest
        )
    }

    fun handlePermissionRequestResult(context: Context, permission: String, result: Int) {
        if (result != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(context, "$permission : denied", Toast.LENGTH_SHORT).show()
    }

    private fun getMusicFilesFromStorage(context: Context): ArrayList<Song> {
        val listOfMusic = ArrayList<Song>()

        //initialize content resolver and the music URI
        var contentResolver = context.contentResolver
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        //columns to retrieve from the mediaStore query
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA
        )
        //filters
        val selection =
            "${MediaStore.Audio.Media.DURATION} >= ? AND ${MediaStore.Audio.Media.DATA} NOT LIKE ?"
        val selectionArgs = arrayOf(
            TimeUnit.SECONDS.toMillis(60).toString(), //filtered to Audio files longer than 60 secs
            "%Recordings%" // filters out the audio files in the recordings directory

        )

        //assign query results to a cursor
        val cursor = contentResolver.query(musicUri, projection, selection, selectionArgs, null)

        //get contents of cursor into arraylist
        cursor?.use { cursor ->
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val dataColumn =
                cursor.getColumnIndex(MediaStore.Audio.Media.DATA) // dataColumn holds the filepath

            while (cursor.moveToNext()) {
                val songId = cursor.getLong(idColumn)
                val songName = cursor.getString(titleColumn)
                val songArtist = cursor.getString(artistColumn)
                val songDuration = cursor.getLong(durationColumn)
                val songAlbum = cursor.getString(albumColumn)
                val songFilePath = cursor.getString(dataColumn)

                val musicFile =
                    Song(songId, songName, songArtist, songDuration, songAlbum, songFilePath)
                listOfMusic.add(musicFile)
            }
        }
        return listOfMusic
    }

    fun selectSong() {
        myPlayer.setDataSource(/* path = */ currentMusic.value?.songFilePath)
        myPlayer.prepare()
        isResourceSet = true

    }

    fun playSong() {
        myPlayer.start()
        listOfSongs[currentSongIndex].isCurrentlyPlaying = true

    }

    fun pauseSong() {
        myPlayer.pause()
        listOfSongs[currentSongIndex].isCurrentlyPlaying = false
    }

    fun playNext(shuffle: Boolean) {
        currentMusic.value?.isCurrentlyPlaying = false

        myPlayer.reset()
        setPlayer()

        if (shuffle) {
            currentSongIndex = Random.nextInt(listOfSongs.size + 1)
        } else {
            if (currentSongIndex != listOfSongs.lastIndex) {
                currentSongIndex += 1
            } else {
                currentSongIndex = 0
            }
        }

        currentMusic.value = listOfSongs[currentSongIndex]
        selectSong()
    }

    fun playPrevious() {
        myPlayer.reset()
        setPlayer()
        if (currentSongIndex != 0) {
            currentSongIndex -= 1
        } else {
            currentSongIndex = listOfSongs.lastIndex
        }
        //currentSongObject = listOfSongs[currentSongIndex]
        currentMusic.value = listOfSongs[currentSongIndex]
        selectSong()
        // setSeekBar()
    }

    fun setPlayer() {
        myPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    fun setSongRepeat(isRepeating: Boolean) {
        myPlayer.isLooping = isRepeating
    }

    fun searchSong(searchQuery: String): List<Song> = listOfSongs.filter { song ->
        song.doesMatchSearchQuery(query = searchQuery)
    }

    @SuppressLint("Range")
    fun addSongToPlaylist(context: Context, playlist: Playlist) {
        val contentResolver = context.contentResolver

        val playlistMemberUri =
            MediaStore.Audio.Playlists.Members.getContentUri("external", playlist.id)

        val projection = arrayOf(MediaStore.Audio.Playlists.Members.PLAY_ORDER)
        val cursor = contentResolver.query(playlistMemberUri, projection, null, null, "${MediaStore.Audio.Playlists.Members.PLAY_ORDER} DESC")
        val playOrder = if (cursor?.moveToFirst() == true) {
            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.PLAY_ORDER)) + 1
        } else {
            0
        }
        cursor?.close()


        val contentValues = ContentValues()
        contentValues.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, currentMusic.value?.songId)
        contentValues.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, playOrder) // set your desired play order here


        val insertUri = contentResolver.insert(playlistMemberUri, contentValues)


        if (insertUri != null) {
            Toast.makeText(
                context,
                "${currentMusic.value?.songName} added to ${playlist.name}",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                "failed: songId= ${currentMusic.value?.songId} playlistId= ${playlist.id} ",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    fun deleteSong(context:Context,song: Song) {
        val deleteURI = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.songId)

        if (myPlayer.isPlaying){
            pauseSong()
            playButtonState.value = false
        }

        try{
           context.contentResolver.delete(deleteURI,null,null)
            Toast.makeText(context,"Deleted successfully: ${song.songName}", Toast.LENGTH_SHORT).show()

            //set updated list of songs
            setListOfSongs()

            //set player
            myPlayer.reset()
            setPlayer()
            isResourceSet = false

            //check if the deleted song was on the lastindex of array
            currentMusic.value = if (currentSongIndex > listOfSongs.lastIndex){
                currentSongIndex = listOfSongs.lastIndex
                listOfSongs[currentSongIndex]
            }else{
                listOfSongs[currentSongIndex]
            }

            selectSong()

        }catch (e:Exception){
            Toast.makeText(context,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }



    }

}
