package cz.dcervenka.sportrecorder.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.network.Resource
import cz.dcervenka.sportrecorder.network.model.Document
import cz.dcervenka.sportrecorder.network.model.RemoteData
import cz.dcervenka.sportrecorder.other.Mapping.mapRemoteToLocal
import cz.dcervenka.sportrecorder.other.SortType
import cz.dcervenka.sportrecorder.repository.SportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sportRepository: SportRepository
) : ViewModel() {

    val remoteData: MutableLiveData<Resource<RemoteData>> = MutableLiveData()

    private val sportsLocal = sportRepository.getSportsByStorage(SortType.LOCAL)
    //private val sportsRemote = sportRepository.getSportsByStorage(SortType.REMOTE)

    val sports = MediatorLiveData<List<Sport>>()

    var sortType = SortType.LOCAL

    init {
        getRemoteData()

        sports.addSource(sportsLocal) { result ->
            if (sortType == SortType.LOCAL) {
                result?.let { sports.value = it }
            }
        }
        sports.addSource(remoteData) { result ->
            if (sortType == SortType.REMOTE) {
                result?.let {
                    sports.value = mapRemoteToLocal(it.data?.documents)
                }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.LOCAL -> sportsLocal.value?.let { sports.value = it }
        SortType.REMOTE -> remoteData.value?.let { sports.value = mapRemoteToLocal(it.data?.documents) }
    }.also {
        this.sortType = sortType
    }

    fun insertSport(sport: Sport) = viewModelScope.launch {
        sportRepository.insertSport(sport)
    }

    fun deleteSport(sport: Sport) = viewModelScope.launch {
        sportRepository.deleteSport(sport)
    }

    private fun getRemoteData() = viewModelScope.launch {
        remoteData.postValue(Resource.Loading())
        val response = sportRepository.getRemoteData()
        remoteData.postValue(handleSnowResponse(response))
    }

    fun postSport(document: Document, collectionId: String) = viewModelScope.launch {
        sportRepository.postSport(document, collectionId)
    }

    private fun handleSnowResponse(response: Response<RemoteData>): Resource<RemoteData> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}