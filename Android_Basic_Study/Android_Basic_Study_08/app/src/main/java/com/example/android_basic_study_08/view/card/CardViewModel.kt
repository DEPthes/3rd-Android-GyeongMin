package com.example.android_basic_study_08.view.card

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_basic_study_08.data.local.BookmarkDB
import com.example.android_basic_study_08.data.local.BookmarkImage
import com.example.android_basic_study_08.entity.DetailImage
import com.example.android_basic_study_08.entity.RandomImage
import com.example.android_basic_study_08.repository.PhotoRepositoryImpl
import com.example.android_basic_study_08.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardViewModel: ViewModel() {
    private val photoRepositoryImpl = PhotoRepositoryImpl()
    private var _newState = MutableLiveData<UiState<List<RandomImage>>>(UiState.Loading)
    val newState get() = _newState
    private var _bookmarkState = MutableLiveData<UiState<Unit>>(UiState.Loading)
    val bookmarkImage get() = _bookmarkState

    fun getPhotoRandom(count: Int) {
        _newState.value = UiState.Loading

        viewModelScope.launch {
            photoRepositoryImpl.getPhotoRandom(count)
                .onSuccess { _newState.value = UiState.Success(it) }
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
}