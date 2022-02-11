package cz.dcervenka.sportrecorder.repository

import cz.dcervenka.sportrecorder.db.Sport
import cz.dcervenka.sportrecorder.db.SportDao
import javax.inject.Inject

class SportRepository @Inject constructor(
    val sportDao: SportDao
) {
    suspend fun insertSport(sport: Sport) = sportDao.insertSport(sport)

    suspend fun deleteSport(sport: Sport) = sportDao.deleteSport(sport)

    fun getAllSportsSortedByDate(type: String) = sportDao.getAllSportsSortedByDate(type)

}