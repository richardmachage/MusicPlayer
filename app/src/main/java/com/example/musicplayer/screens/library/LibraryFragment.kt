package com.example.musicplayer.screens.library

import android.app.AlertDialog
import android.content.ContentResolver
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.marginRight
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.models.Playlist
import com.example.musicplayer.adapters.PlaylistsRecyclerAdapter
import com.example.musicplayer.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {
    private lateinit var libraryBinding: FragmentLibraryBinding
    private val binding get() = libraryBinding
    private val viewModel: LibraryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // viewModel.listOfPlaylits = viewModel.getPlaylists(requireContext().contentResolver) as MutableList<Playlist>
        // Inflate the layout for this fragment
        libraryBinding = FragmentLibraryBinding.inflate(layoutInflater)

        binding.newPlayListCardView.setOnClickListener {
            addPlayListDialog()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playlistsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.playlistsRecyclerView.adapter =
            PlaylistsRecyclerAdapter(
                requireContext(),
                viewModel.getPlaylists(requireContext().contentResolver) as ArrayList<Playlist>
            )
    }

    private fun addPlayListDialog() {
        val editText = EditText(requireContext())
        editText.hint = "Playlist Name"
        editText.setHintTextColor(Color.GRAY)
        editText.setTextColor(Color.WHITE)

        val myDialog = AlertDialog.Builder(requireContext())
            .setView(editText)
            .setPositiveButton("Save") { dialog, _ ->
                if (editText.text.isNullOrBlank()) {
                    Toast.makeText(requireContext(), "Name is Blank", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Playlist Created ${editText.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .setIcon(R.drawable.add_circle_icon)
            .show()

        myDialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
            setTextColor(Color.WHITE)
        }
        myDialog.getButton(AlertDialog.BUTTON_NEGATIVE).apply {
            setTextColor(Color.WHITE)
        }
        myDialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.primary_between)))
            navigationBarColor = resources.getColor(R.color.primary_between)
        }

    }


}