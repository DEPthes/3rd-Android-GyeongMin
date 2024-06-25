package com.example.android_basic_study_08.view.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_basic_study_08.data.local.BookmarkDB
import com.example.android_basic_study_08.data.local.BookmarkImage
import com.example.android_basic_study_08.entity.NewImage
import com.example.android_basic_study_08.repository.PhotoRepositoryImpl
import com.example.android_basic_study_08.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel(){
    private val photoRepositoryImpl = PhotoRepositoryImpl()
    private var _newState = MutableLiveData<UiState<List<NewImage>>>(UiState.Loading)
    val newState get() = _newState

    private var _bookmarkState = MutableLiveData<UiState<List<BookmarkImage>>>(UiState.Loading)
    val bookmarkState get() = _bookmarkState
    fun getPhotos(currentPage: Int) {
        _newState.value = UiState.Loading

        viewModelScope.launch {
            photoRepositoryImpl.getPhotoList(currentPage)
                .onSuccess { _newState.value = UiState.Success(it) }
                .onFailure {
                    it.printStackTrace()
                    _newState.value = UiState.Failure(400, it.message.toString())
                }
        }
    }

    fun getBookmarks(context: Context) {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dao = BookmarkDB.getInstance(context).getBookmarkDAO()
                val bookmark = dao.getBookmark()
                launch(Dispatchers.Main) {_bookmarkState.value = UiState.Success(bookmark) }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {_bookmarkState.value = UiState.Failure(400, e.message.toString()) }
            }
        }
    }

}