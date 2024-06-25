package com.example.android_basic_study_08.view.detail

import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.android_basic_study_08.data.local.BookmarkImage
import com.example.android_basic_study_08.databinding.FragmentDetailBinding
import com.example.android_basic_study_08.utils.UiState

class DetailFragment(private val id: String): DialogFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel : DetailViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(MATCH_PARENT, MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        observer()
        initListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.isBookmark(id, requireContext())
        detailViewModel.getPhotos(id)
    }

    private fun observer(){
        detailViewModel.newState.observe(viewLifecycleOwner) {
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
                    binding.tvDescription.text = it.data.description
                    binding.tvUserName.text = it.data.user
                    val tag = StringBuilder()
                    it.data.tag.forEach{
                        tag.append("#${it} ")
                    }
                    binding.tvTag.text = tag
                    binding.tvTitle.text = it.data.title
                    binding.btnDownload.setOnClickListener {_ ->
                        useDownloadManager(it.data.downloadLink)
                    }
                    Glide.with(binding.ivDetailImage)
                        .load(it.data.urls)
                        .transform(RoundedCorners(40))
                        .into(binding.ivDetailImage)
                }
            }
        }
        detailViewModel.bookmarkState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    Log.d("TAG", "fail")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {
                    Log.d("TAG", "loading")
                }

                is UiState.Success -> {
                    Log.d("TAG", "success")
                    detailViewModel.isBookmark(id, requireContext())
                }
            }
        }
        detailViewModel.isBookmarkState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Failure -> {
                    Log.d("TAG", "fail")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is UiState.Loading -> {
                    Log.d("TAG", "loading")
                }

                is UiState.Success -> {
                    Log.d("TAG", "success")
                    if (it.data) {
                        binding.btnBookmark.alpha = 1f // 투명도 x
                    } else {
                        binding.btnBookmark.alpha = 0.3f
                    }
                    bookmarkClickListener.onBookmarkClick()
                }
            }
        }
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            dismiss()
        }
        binding.btnBookmark.setOnClickListener {
            Log.d("click","ddd")
            if (binding.btnBookmark.alpha == 1f) {
                detailViewModel.deletePhotos(id, requireContext())
            } else {
                detailViewModel.insertPhotos(BookmarkImage(id, urls = detailViewModel.url), requireContext())
            }
        }
    }

    private fun useDownloadManager(link: String){
        val currentTimeMillis = System.currentTimeMillis()
        val fileName = currentTimeMillis.toString()

        val request = DownloadManager.Request(Uri.parse(link))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName)

        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(requireContext(), "다운로드 완료", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    interface OnBookmarkClickListener {
        fun onBookmarkClick()
    }

    private lateinit var bookmarkClickListener: OnBookmarkClickListener

    fun setBookmarkClickListener(bookmarkClickListener: OnBookmarkClickListener) {
        this.bookmarkClickListener = bookmarkClickListener
    }
}