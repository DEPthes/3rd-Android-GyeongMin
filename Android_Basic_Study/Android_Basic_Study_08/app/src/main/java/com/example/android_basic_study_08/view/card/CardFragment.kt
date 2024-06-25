package com.example.android_basic_study_08.view.card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.android_basic_study_08.data.local.BookmarkImage
import com.example.android_basic_study_08.databinding.FragmentCardBinding
import com.example.android_basic_study_08.utils.UiState
import kotlin.math.abs

class CardFragment: Fragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    private lateinit var randomImageAdapter: RandomImageAdapter
    private val cardViewModel : CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        randomImageAdapter = RandomImageAdapter().apply {
            setItemClickListener(object : RandomImageAdapter.OnItemClickListener {
                override fun onItemClick(bookmarkImage: BookmarkImage, position: Int) {
                    cardViewModel.insertPhotos(bookmarkImage, requireContext())
                    binding.viewpagerCard.currentItem = position + 1
                }

            })
        }
        binding.viewpagerCard.adapter = randomImageAdapter
        binding.viewpagerCard.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerCard.offscreenPageLimit = 3

        // item_view 간의 양 옆 여백을 상쇄할 값
        val offsetBetweenPages = 55f
        binding.viewpagerCard.setPageTransformer { page, position ->
            val myOffset = position * -(2 * offsetBetweenPages)
            if (position < -1) {
                page.translationX = -myOffset
            } else if (position <= 1) {
                // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                val scaleFactor = 0.85f.coerceAtLeast(1 - abs(position))
                page.translationX = myOffset
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
            } else {
                page.alpha = 0f
                page.translationX = myOffset
            }
        }

        observer()
        cardViewModel.getPhotoRandom(4)

        binding.viewpagerCard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            // Paging 완료되면 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                cardViewModel.getPhotoRandom(1)
            }
        })

        return binding.root
    }

    private fun observer() {
        cardViewModel.newState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    Log.d("TAG","RandomImage fail")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> {
                    Log.d("TAG","RandomImage loading")
                }
                is UiState.Success -> {
                    Log.d("TAG","RandomImage success")
                    randomImageAdapter.addItem(it.data)
                }
            }
        }
        cardViewModel.bookmarkImage.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    Log.d("TAG","RandomImage Bookmark fail")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> {
                    Log.d("TAG","RandomImage Bookmark loading")
                }
                is UiState.Success -> {
                    Log.d("TAG","RandomImage Bookmark success")

                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}