package com.example.musicplayer.screens.main

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.R.drawable
import com.example.musicplayer.databinding.ActivityMainBinding
import android.Manifest
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.musicplayer.models.Song
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val binding get() = mainBinding
    private lateinit var viewModel: MainViewModel
    private var PERMISSION_REQUEST_CODE = 111
    val bottomSheetSongOptions = BottomSheetSongOptions()

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
        //Manifest.permission.MANAGE_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater) // inflate the binding feature
        setContentView(binding.root)

        //create and instantiate the view model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.context = applicationContext
        viewModel.setListOfSongs()
        viewModel.setCurrentSongObject()

        //setSongDetails(viewModel.currentSongObject)

        //set softkeyboard not to affect screen
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        //check if permission is granted and ask for it if not
        val permissionsToRequest = mutableListOf<String>()
        for (permission in permissions) {
            if (viewModel.checkIfPermissionGranted(this, permission)) {
                permissionsToRequest.add(permission)
                Toast.makeText(applicationContext, "$permission : Granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }

        //observer for currentMusic
        viewModel.currentMusic.observe(this, Observer {song ->
            binding.seekbar.max = viewModel.currentMusic.value?.songDuration?.toInt() ?:0
            setSeekBar()
            binding.songNameTextView.text = song.songName
            binding.artistNameTextView.text = song.songArtist
            //binding.songNameTextView.isSelected = true
        })

        //observer for play?pause button
        viewModel.playButtonState.observe(this, Observer { isplaying ->
            if (isplaying){
                binding.playImagebutton.setImageResource(drawable.play_icon)
                binding.songNameTextView.isSelected = false
            }else{
                binding.playImagebutton.setImageResource(drawable.pause_icon)
                binding.songNameTextView.isSelected = true
            }
        })

        // songs number display


        // seekBar logic
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (p0 != null) {
                    viewModel.myPlayer.seekTo(p0.progress)
                }else{
                    viewModel.selectSong()
                    viewModel.playSong()
                }

            }

        })

        // bottom navigation logic
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_bottom_nav -> navigateToHomeFragment()
                R.id.search_bottom_nav -> navigateToSearchFragment()
                else -> navigateToLibraryFragment()
            }
            true
        }

        //Play button
        binding.playImagebutton.setOnClickListener {
            if (  viewModel.myPlayer.isPlaying//binding.playImagebutton.drawable.constantState == resources.getDrawable(R.drawable.pause_icon,null).constantState
            ) {
                //first change the icon to show the play icon
               // binding.playImagebutton.setImageResource(drawable.play_icon)
                //then pause the music player
                viewModel.pauseSong()
                viewModel.playButtonState.value = true

            }
            else {
                //first change the icon
                //binding.playImagebutton.setImageResource(drawable.pause_icon)

                //then start the mediaPlayer and play a song
                if(!viewModel.isResourceSet){
                    viewModel.selectSong()
                }

                viewModel.playSong()
                viewModel.playButtonState.value = false

            }
        }

        //Next button
        binding.nextImagebutton.setOnClickListener {

            if(viewModel.myPlayer.isPlaying){
                viewModel.playNext(viewModel.shuffleSongs)
                viewModel.playSong()
            }else{
                viewModel.playNext(viewModel.shuffleSongs)
            }

        }

        //Previous button
        binding.prevImageButton.setOnClickListener {
            if(viewModel.myPlayer.isPlaying){
                viewModel.playPrevious()
                viewModel.playSong()
            }else{
                viewModel.playPrevious()
            }
            //setSongDetails(viewModel.listOfSongs[viewModel.currentSongIndex])
        }


        binding.repeatImagebutton.setOnClickListener {
            if (binding.repeatImagebutton.drawable.constantState == resources.getDrawable(
                    drawable.repeat_one_icon,
                    null
                ).constantState
            ){
                binding.repeatImagebutton.setImageResource(drawable.repeat_icon)
                viewModel.setSongRepeat(isRepeating = false)
            }
            else {
                binding.repeatImagebutton.setImageResource(drawable.repeat_one_icon)
                viewModel.setSongRepeat(isRepeating = true)
            }
        }

        binding.shuffleImagebutton.setOnClickListener {
            if (binding.shuffleImagebutton.drawable.constantState == resources.getDrawable(
                    drawable.no_shuffle_icon,
                    null
                ).constantState
            ){
                binding.shuffleImagebutton.setImageResource(drawable.shuffle_icon)
                viewModel.shuffleSongs = false
                Toast.makeText(applicationContext,"Shuffle off", Toast.LENGTH_SHORT).show()
            }
            else {
                binding.shuffleImagebutton.setImageResource(drawable.no_shuffle_icon)
                viewModel.shuffleSongs = true
                Toast.makeText(applicationContext,"Shuffle on", Toast.LENGTH_SHORT).show()

            }
        }

        binding.songOptionsImagebutton.setOnClickListener {
            bottomSheetSongOptions.show(supportFragmentManager,"Bottom sheet dialog")
        }

    }

    private fun navigateToSearchFragment() {
        if (binding.bottomNavigationView.selectedItemId == R.id.home_bottom_nav) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment2_to_searchFragment)
        } else if (binding.bottomNavigationView.selectedItemId == R.id.library_bottom_nav) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_libraryFragment_to_searchFragment)
        }

    }

    private fun navigateToLibraryFragment() {
        if (binding.bottomNavigationView.selectedItemId == R.id.home_bottom_nav) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_homeFragment2_to_libraryFragment)
        } else if (binding.bottomNavigationView.selectedItemId == R.id.search_bottom_nav) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_searchFragment_to_libraryFragment)
        }
    }

    private fun navigateToHomeFragment() {
        if (binding.bottomNavigationView.selectedItemId == R.id.search_bottom_nav) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_searchFragment_to_homeFragment2)
        } else if (binding.bottomNavigationView.selectedItemId == R.id.library_bottom_nav) {
            findNavController(R.id.fragmentContainerView).navigate(R.id.action_libraryFragment_to_homeFragment2)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == PERMISSION_REQUEST_CODE) {
            val deniedPermissions = mutableListOf<String>()

            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i])
                }
            }

            if (deniedPermissions.isNotEmpty()) {
                for (permission in deniedPermissions) {
                    Toast.makeText(this, "$permission: Permission denied", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()

            }
        }

    }

    /*private fun setSongDetails(song: Song){
        binding.artistNameTextView.text = song.songArtist
        binding.songNameTextView.text = song.songName
    }*/

    private fun setSeekBar() {
        var timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                binding.seekbar.progress = viewModel.myPlayer.currentPosition
            }

        }, 0, 900)
    }
}

