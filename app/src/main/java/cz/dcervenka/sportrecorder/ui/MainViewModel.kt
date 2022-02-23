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
    private val sportRepository: SportRepository
) : ViewModel() {

    private val sportsAll = sportRepository.getAllSportsSortedByDate()
    private val sportsLocal = sportRepository.getSportsByStorage(SortType.LOCAL)
    private val sportsRemote = sportRepository.getSportsByStorage(SortType.REMOTE)

    val sports = MediatorLiveData<List<Sport>>()

    var sortType = SortType.ALL

    init {
        sports.addSource(sportsAll) { result ->
            if (sortType == SortType.ALL) {
                result?.let { sports.value = it }
            }
        }
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
        SortType.ALL -> sportsAll.value?.let { sports.value = it }
        SortType.LOCAL -> sportsLocal.value?.let { sports.value = it }
        SortType.REMOTE -> sportsRemote.value?.let { sports.value = it }
    }.also {
        this.sortType = sortType
    }

    fun insertSport(sport: Sport) = viewModelScope.launch {
        sportRepository.insertSport(sport)
    }

    fun deleteSport(sport: Sport) = viewModelScope.launch {
        sportRepository.deleteSport(sport)
    }

}