package com.example.android_basic_study_08.view.detail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_basic_study_08.data.local.BookmarkDB
import com.example.android_basic_study_08.data.local.BookmarkImage
import com.example.android_basic_study_08.entity.DetailImage
import com.example.android_basic_study_08.repository.PhotoRepositoryImpl
import com.example.android_basic_study_08.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    private val photoRepositoryImpl = PhotoRepositoryImpl()
    private var _newState = MutableLiveData<UiState<DetailImage>>(UiState.Loading)
    val newState get() = _newState
    private var _bookmarkState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val bookmarkState get() = _bookmarkState

    private var _isBookmarkState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    val isBookmarkState get() = _isBookmarkState
    lateinit var url : String

    fun getPhotos(id: String) {
        _newState.value = UiState.Loading

        viewModelScope.launch {
            photoRepositoryImpl.getPhotoDetail(id)
                .onSuccess {
                    _newState.value = UiState.Success(it)
                    url = it.urls
                }
                .onFailure {
                    it.printStackTrace()
                    _newState.value = UiState.Failure(400, it.message.toString())
                }
        }
    }

    fun insertPhotos(bookmarkImage: BookmarkImage, context: Context) {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dao = BookmarkDB.getInstance(context).getBookmarkDAO()
                dao.insertBookmark(bookmarkImage)
                launch(Dispatchers.Main) {_bookmarkState.value = UiState.Success(Unit) }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {_bookmarkState.value = UiState.Failure(400, e.message.toString()) }
            }
        }
    }

    fun deletePhotos(id: String, context: Context) {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dao = BookmarkDB.getInstance(context).getBookmarkDAO()
                dao.deleteBookmark(id)
                launch(Dispatchers.Main) {_bookmarkState.value = UiState.Success(Unit) }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {_bookmarkState.value = UiState.Failure(400, e.message.toString()) }
            }
        }
    }

    fun isBookmark(id: String, context: Context) {
        _isBookmarkState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dao = BookmarkDB.getInstance(context).getBookmarkDAO()
                if (dao.isBookmark(id)==0) {
                    launch(Dispatchers.Main) {_isBookmarkState.value = UiState.Success(false) }
                } else {
                    launch(Dispatchers.Main) {_isBookmarkState.value = UiState.Success(true) }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {_isBookmarkState.value = UiState.Failure(400, e.message.toString()) }
            }
        }
    }

}