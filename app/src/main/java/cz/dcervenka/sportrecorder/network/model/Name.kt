package cz.dcervenka.sportrecorder.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Name(
    val stringValue: String
)