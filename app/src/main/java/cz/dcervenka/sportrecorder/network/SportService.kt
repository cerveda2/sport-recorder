package cz.dcervenka.sportrecorder.network

import cz.dcervenka.sportrecorder.network.model.Document
import cz.dcervenka.sportrecorder.network.model.RemoteData
import retrofit2.Response
import retrofit2.http.*

interface SportService {

    @GET("projects/sportrecorder-dabcc/databases/(default)/documents/data")
    suspend fun getData(@Query("key") apiKey: String): Response<RemoteData>

    @POST("projects/sportrecorder-dabcc/databases/(default)/documents/data")
    suspend fun postData(@Query("key") apiKey: String, @Body document: Document)

    @DELETE("projects/sportrecorder-dabcc/databases/(default)/documents/data/{collectionId}")
    suspend fun deleteData(@Path("collectionId") collectionId: String, @Query("key") apiKey: String)

}