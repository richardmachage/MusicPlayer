package com.example.musicplayer.screens.main

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicplayer.databinding.ViewDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetViewDetails : BottomSheetDialogFragment() {
    private  lateinit  var viewDetailsBinding : ViewDetailsBottomSheetBinding
    private val binding get() = viewDetailsBinding

    private val viewModel : MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDetailsBinding = ViewDetailsBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputSongNameEditText.text = Editable.Factory.getInstance().newEditable(viewModel.currentMusic.value?.songName?: "")
        binding.inputArtistNameEditText.text = Editable.Factory.getInstance().newEditable(viewModel.currentMusic.value?.songArtist?: "")
        binding.inputAlbumNameEditText.text = Editable.Factory.getInstance().newEditable(viewModel.currentMusic.value?.songAlbum?: "")
        binding.songPathEditText.text = viewModel.currentMusic.value?.songFilePath?: ""

        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }

        binding.doneButton.setOnClickListener {
            this.dismiss()
        }
    }
}