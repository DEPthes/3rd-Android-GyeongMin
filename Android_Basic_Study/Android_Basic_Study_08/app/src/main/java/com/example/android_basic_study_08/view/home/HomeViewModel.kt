package com.example.android_basic_study_08.view.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_basic_study_08.entity.NewImage
import com.example.android_basic_study_08.repository.PhotoRepositoryImpl
import com.example.android_basic_study_08.utils.UiState
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel(){
    private val photoRepositoryImpl = PhotoRepositoryImpl()
    private var _newState = MutableLiveData<UiState<List<NewImage>>>(UiState.Loading)
    val newState get() = _newState

    fun getPhotos() {
        _newState.value = UiState.Loading

        viewModelScope.launch {
            photoRepositoryImpl.getPhotoList(0)
                .onSuccess { _newState.value = UiState.Success(it) }
                .onFailure {
                    it.printStackTrace()
                    _newState.value = UiState.Failure(400, it.message.toString())
                }
        }
    }

}