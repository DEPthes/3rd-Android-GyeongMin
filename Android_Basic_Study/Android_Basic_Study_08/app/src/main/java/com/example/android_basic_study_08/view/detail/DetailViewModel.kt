package com.example.android_basic_study_08.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_basic_study_08.entity.DetailImage
import com.example.android_basic_study_08.entity.NewImage
import com.example.android_basic_study_08.repository.PhotoRepositoryImpl
import com.example.android_basic_study_08.utils.UiState
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    private val photoRepositoryImpl = PhotoRepositoryImpl()
    private var _newState = MutableLiveData<UiState<DetailImage>>(UiState.Loading)
    val newState get() = _newState

    fun getPhotos(id: String) {
        _newState.value = UiState.Loading

        viewModelScope.launch {
            photoRepositoryImpl.getPhotoDetail(id)
                .onSuccess { _newState.value = UiState.Success(it) }
                .onFailure {
                    it.printStackTrace()
                    _newState.value = UiState.Failure(400, it.message.toString())
                }
        }
    }
}