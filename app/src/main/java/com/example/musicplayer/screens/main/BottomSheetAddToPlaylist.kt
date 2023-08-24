package com.example.musicplayer.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapters.PlaylistsRecyclerAdapter
import com.example.musicplayer.databinding.AddToPlaylistBottomSheetBinding
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.screens.library.LibraryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAddToPlaylist : BottomSheetDialogFragment() {
    private lateinit var bottomSheetBinding: AddToPlaylistBottomSheetBinding
    private val binding get() = bottomSheetBinding

    //attaching the shared view model
    private val viewModelMain : MainViewModel by activityViewModels()
    private val viewModel : LibraryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomSheetBinding = AddToPlaylistBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playlistsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.playlistsRecyclerView.adapter = PlaylistsRecyclerAdapter(
            requireContext(),
            viewModel.getPlaylists(requireContext().contentResolver) as ArrayList<Playlist>
        )

        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }
    }
}