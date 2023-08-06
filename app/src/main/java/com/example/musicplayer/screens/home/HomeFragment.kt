package com.example.musicplayer.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapters.SongRecyclerViewAdapter
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.other.onItemClick
import com.example.musicplayer.screens.main.MainViewModel


class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private val binding get() = homeBinding

    //attaching the shared viewModel
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.songsListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        var adapter = SongRecyclerViewAdapter(requireContext(), viewModel.listOfSongs)
        binding.songsListRecyclerView.adapter = adapter

        binding.songLabelTextview.text = "${viewModel.listOfSongs.size} Song(s)"
        //onClick Listener

        binding.songsListRecyclerView.onItemClick{recyclerView, position, v ->
            //Toast.makeText(requireContext(),"Song clicked: ${viewModel.listOfSongs[position].songName}", Toast.LENGTH_SHORT).show()

            viewModel.myPlayer.reset()
            viewModel.setPlayer()
            viewModel.currentSongIndex = position
            viewModel.currentMusic.value = viewModel.listOfSongs[position]
            viewModel.selectSong()
            viewModel.playSong()

            viewModel.playButtonState.value = false

        }

        //adapter.notifyDataSetChanged()

    }







}