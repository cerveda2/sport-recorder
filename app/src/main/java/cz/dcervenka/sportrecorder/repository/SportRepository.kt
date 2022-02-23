package cz.dcervenka.sportrecorder.repository

import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.db.SportDao
import cz.dcervenka.sportrecorder.other.SortType
import javax.inject.Inject

class SportRepository @Inject constructor(
    private val sportDao: SportDao
) {
    suspend fun insertSport(sport: Sport) = sportDao.insertSport(sport)

    suspend fun deleteSport(sport: Sport) = sportDao.deleteSport(sport)

    fun getAllSportsSortedByDate(type: SortType) = sportDao.getAllSportsSortedByDate(type.name)

}