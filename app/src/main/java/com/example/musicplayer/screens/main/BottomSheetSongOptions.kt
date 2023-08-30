package com.example.musicplayer.screens.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.musicplayer.R
import com.example.musicplayer.databinding.SongOptionsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetSongOptions : BottomSheetDialogFragment(){
    private lateinit var bottomSheetBinding: SongOptionsBottomSheetBinding
    private val binding get() = bottomSheetBinding

    //attaching the shared view model
    private val viewModel : MainViewModel by activityViewModels()

    private val viewDetailsBottomSheet = BottomSheetViewDetails()
    private val addToPlaylistBottomSheet = BottomSheetAddToPlaylist()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate the view here
        bottomSheetBinding = SongOptionsBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addToPlaylistButton.setOnClickListener{

            addToPlaylistBottomSheet.isCancelable = false
            addToPlaylistBottomSheet.show(parentFragmentManager,"add to playlist bottom sheet")
            this.dismiss()
        }

        binding.viewDetailsButton.setOnClickListener {
            //Toast.makeText(requireContext(), "viewDetails clicked", Toast.LENGTH_SHORT).show()

            viewDetailsBottomSheet.isCancelable = false
            viewDetailsBottomSheet.show(parentFragmentManager, "View details bottoms sheet")
            this.dismiss()
        }

        binding.shareSongButton.setOnClickListener {
            Toast.makeText(requireContext(), "Share song clicked", Toast.LENGTH_SHORT).show()
        }

        binding.deleteSongButton.setOnClickListener {

            Toast.makeText(requireContext(), "Delete Song clicked", Toast.LENGTH_SHORT).show()
            viewModel.currentMusic.value?.let { song -> viewModel.deleteSong(requireContext(),song) }
        }
    }

}