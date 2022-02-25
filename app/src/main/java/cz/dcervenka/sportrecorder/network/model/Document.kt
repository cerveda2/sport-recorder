package cz.dcervenka.sportrecorder.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Document(
    val createTime: String,
    val fields: Fields,
    val name: String,
    val updateTime: String
)