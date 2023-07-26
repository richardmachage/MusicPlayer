package com.example.musicplayer.screens.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.adapters.PlaylistsRecyclerAdapter
import com.example.musicplayer.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {
    private lateinit var libraryBinding : FragmentLibraryBinding
    private val binding get() = libraryBinding
    private lateinit var viewModel:LibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        libraryBinding = FragmentLibraryBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(LibraryViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playlistsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.playlistsRecyclerView.adapter = PlaylistsRecyclerAdapter(requireContext(), viewModel.listOfPlaylits)
    }



}