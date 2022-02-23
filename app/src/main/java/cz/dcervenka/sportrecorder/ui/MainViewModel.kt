package cz.dcervenka.sportrecorder.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.other.SortType
import cz.dcervenka.sportrecorder.repository.SportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val sportRepository: SportRepository
) : ViewModel() {

    private val sportsLocal = sportRepository.getAllSportsSortedByDate(SortType.LOCAL)
    private val sportsRemote = sportRepository.getAllSportsSortedByDate(SortType.REMOTE)

    val sports = MediatorLiveData<List<Sport>>()

    var sortType = SortType.LOCAL

    init {
        sports.addSource(sportsLocal) { result ->
            if (sortType == SortType.LOCAL) {
                result?.let { sports.value = it }
            }
        }
        sports.addSource(sportsRemote) { result ->
            if (sortType == SortType.REMOTE) {
                result?.let { sports.value = it }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.LOCAL -> sportsLocal.value?.let { sports.value = it }
        SortType.REMOTE -> sportsRemote.value?.let { sports.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(sport: Sport) = viewModelScope.launch {
        sportRepository.insertSport(sport)
    }

}