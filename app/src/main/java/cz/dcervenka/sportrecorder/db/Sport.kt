package cz.dcervenka.sportrecorder.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.dcervenka.sportrecorder.other.SortType

@Entity(tableName = "sport_table")
data class Sport(
    var timestamp: Long = 0L,
    var name: String = "",
    var place: String = "",
    var durationInMillis: Long = 0L,
    var distanceInMeters: Int = 0,
    var storageType: SortType = SortType.LOCAL
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}