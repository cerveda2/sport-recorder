package cz.dcervenka.sportrecorder.ui

import androidx.lifecycle.ViewModel
import cz.dcervenka.sportrecorder.repository.SportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val sportRepository: SportRepository
) : ViewModel() {

}