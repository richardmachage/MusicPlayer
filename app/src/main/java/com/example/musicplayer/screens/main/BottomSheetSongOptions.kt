package com.example.musicplayer.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.musicplayer.R
import com.example.musicplayer.databinding.SongOptionsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetSongOptions : BottomSheetDialogFragment(){
    private lateinit var bottomSheetBinding: SongOptionsBottomSheetBinding
    private val binding get() = bottomSheetBinding

    //attaching the shared view model
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate the view here
        return inflater.inflate(R.layout.song_options_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addToPlaylistLinearLayout.setOnClickListener{
           Toast.makeText(requireContext(), "add to playlist clicked", Toast.LENGTH_SHORT).show()
        }

        binding.viewDetailsLinearLayout.setOnClickListener {
            Toast.makeText(requireContext(), "viewDetails clicked", Toast.LENGTH_SHORT).show()
        }

        binding.shareLinearLayout.setOnClickListener {
            Toast.makeText(requireContext(), "Share song clicked", Toast.LENGTH_SHORT).show()
        }

        binding.deleteSongLinearLayout.setOnClickListener {
            Toast.makeText(requireContext(), "Delete Song clicked", Toast.LENGTH_SHORT).show()
        }
    }
}