package com.example.musicplayer.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapters.SongRecyclerViewAdapter
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.screens.main.MainViewModel


class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private val binding get() = homeBinding
    //private lateinit var viewModel : HomeViewModel
    val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)

       // viewModel =  ViewModelProvider(this).get(HomeViewModel::class.java)
        //viewModel.context = requireContext()
        //viewModel.setListOfSongs()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.songsListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        var adapter = SongRecyclerViewAdapter(requireContext(), viewModel.listOfSongs)
        binding.songsListRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }







}