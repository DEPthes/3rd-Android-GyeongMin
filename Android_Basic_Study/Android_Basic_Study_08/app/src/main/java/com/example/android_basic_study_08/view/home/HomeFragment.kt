package com.example.android_basic_study_08.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_basic_study_08.BookmarkAdapter
import com.example.android_basic_study_08.NewImageAdapter
import com.example.android_basic_study_08.databinding.FragmentHomeBinding
import com.example.android_basic_study_08.utils.UiState

class HomeFragment: Fragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var bookmarkAdapter : BookmarkAdapter
    private lateinit var newImageAdapter: NewImageAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        bookmarkAdapter = BookmarkAdapter()
        binding.recyclerviewBookmark.adapter = bookmarkAdapter
        binding.recyclerviewBookmark.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        newImageAdapter = NewImageAdapter()
        binding.recyclerviewNewImage.adapter = newImageAdapter
        binding.recyclerviewNewImage.layoutManager = GridLayoutManager(requireContext(), 2)

        homeViewModel.getPhotos()
    }

    private fun observer(){
        homeViewModel.newState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    Log.d("TAG","fail")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> {
                    Log.d("TAG","loading")
                }
                is UiState.Success -> {
                    Log.d("TAG","success")
                    newImageAdapter.addItem(it.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}