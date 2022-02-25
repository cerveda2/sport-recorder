package cz.dcervenka.sportrecorder.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Timestamp(
    val integerValue: Long
)
