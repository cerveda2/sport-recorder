package cz.dcervenka.sportrecorder.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fields(
    val distance: Distance,
    val duration: Duration,
    val name: Name,
    val place: Place,
    val timestamp: Timestamp
)