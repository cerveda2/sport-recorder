package cz.dcervenka.sportrecorder.repository

import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.db.SportDao
import cz.dcervenka.sportrecorder.network.SportRemote
import cz.dcervenka.sportrecorder.network.SportService
import cz.dcervenka.sportrecorder.network.model.Document
import cz.dcervenka.sportrecorder.other.Constants.API_KEY
import cz.dcervenka.sportrecorder.other.SortType
import javax.inject.Inject

class SportRepository @Inject constructor(
    private val sportDao: SportDao,
    private val sportService: SportService
) {
    suspend fun insertSport(sport: Sport) = sportDao.insertSport(sport)

    suspend fun deleteSport(sport: Sport) = sportDao.deleteSport(sport)

    fun getAllSportsSortedByDate() = sportDao.getAllSportsSortedByDate()

    fun getSportsByStorage(type: SortType) = sportDao.getSportsByStorage(type.name)

    suspend fun getRemoteData() = sportService.getData(API_KEY)

    suspend fun postSport(document: Document, collectionId: String) = sportService.postData(document, collectionId)

    suspend fun deleteRemoteSport(sport: SportRemote) = sportService.deleteData(sport)

}