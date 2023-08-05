package com.example.musicplayer.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicplayer.databinding.ViewDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetViewDetails : BottomSheetDialogFragment() {
    private  lateinit  var viewDetailsBinding : ViewDetailsBottomSheetBinding
    private val binding get() = viewDetailsBinding
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

        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }

        binding.doneButton.setOnClickListener {
            this.dismiss()
        }
    }
}