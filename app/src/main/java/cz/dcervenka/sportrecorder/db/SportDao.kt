package cz.dcervenka.sportrecorder.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSport(sport: Sport)

    @Delete
    suspend fun deleteSport(sport: Sport)

    @Query("SELECT * FROM sport_table WHERE storageType = :type ORDER BY timestamp DESC")
    fun getAllSportsSortedByDate(type: String): LiveData<List<Sport>>

}