package br.com.digitalhouse.desafiofirebase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MyViewModel(application: Application) : AndroidViewModel(application) {
    var _scrollCoordinates = MutableLiveData<IntArray>()
    private val scrollCoordinates: MutableLiveData<IntArray>
        get() = _scrollCoordinates

    fun updateScrollCoordinates(coordinates: IntArray) {
        scrollCoordinates.value = coordinates
    }
}