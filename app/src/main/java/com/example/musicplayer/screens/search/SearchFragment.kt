package com.example.musicplayer.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapters.SearchViewRecyclerAdapter
import com.example.musicplayer.databinding.FragmentSearchBinding
import com.example.musicplayer.models.Song
import com.example.musicplayer.other.onItemClick
import com.example.musicplayer.screens.main.MainViewModel


class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val binding get() = searchBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit  var filteredList : ArrayList<Song>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        searchBinding = FragmentSearchBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

       // var adapter = SearchViewRecyclerAdapter(requireContext())
        //binding.searchListRecyclerView.adapter = adapter
        //adapter.notifyDataSetChanged()

        binding.searchListRecyclerView.onItemClick { recyclerView, position, v ->

            viewModel.myPlayer.reset()
            viewModel.setPlayer()
            viewModel.currentSongIndex = position
            viewModel.currentMusic.value = filteredList[position]
            viewModel.selectSong()
            viewModel.playSong()

            viewModel.playButtonState.value = false

        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList = viewModel.searchSong(newText.toString()) as ArrayList<Song>

                if (!newText.isNullOrBlank()){
                    if(filteredList.isNotEmpty()){
                        //binding.searchListRecyclerView.visibility = View.VISIBLE
                        var adapter = SearchViewRecyclerAdapter(requireContext())
                        adapter.setList(filteredList as ArrayList<Song>)
                        binding.searchListRecyclerView.adapter = adapter

                    }
                }else{
                    filteredList as ArrayList
                    filteredList.clear()
                    var adapter = SearchViewRecyclerAdapter(requireContext())
                    adapter.setList(filteredList)
                    binding.searchListRecyclerView.adapter = adapter
                }

                return true
            }
        })

    }
}