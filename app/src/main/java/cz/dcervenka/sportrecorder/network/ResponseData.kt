package cz.dcervenka.sportrecorder.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseData(
    val sports: List<SportRemote>
)
