package cz.dcervenka.sportrecorder.network

import com.squareup.moshi.JsonClass
import cz.dcervenka.sportrecorder.other.SortType

@JsonClass(generateAdapter = true)
data class SportRemote(
    var timestamp: Long = 0L,
    var name: String = "",
    var place: String = "",
    var durationInMillis: Long = 0L,
    var distanceInMeters: Int = 0,
    var storageType: SortType = SortType.REMOTE
)
