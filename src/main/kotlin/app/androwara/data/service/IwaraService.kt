package app.androwara.data.service

import app.androwara.data.model.detail.video.VideoLink
import app.androwara.data.model.playlist.PlaylistActionResponse
import app.androwara.data.model.playlist.PlaylistModifyResponse
import app.androwara.data.model.playlist.PlaylistPreview
import retrofit2.http.*

/**
 * 使用Retrofit直接获取 RESTFUL API 资源
 */
interface IwaraService {
    @FormUrlEncoded
    @POST("api/playlists")
    suspend fun createPlaylist(
        @Field("title") title: String
    ): PlaylistActionResponse

    @POST("api/video/{videoId}")
    suspend fun getVideoInfo(@Path("videoId") videoId: String): VideoLink

    @GET("api/playlists")
    suspend fun getPlaylistPreview(
        @Header("cookie") cookie: String,
        @Query("nid") nid: Int
    ): PlaylistPreview

    @FormUrlEncoded
    @PUT("api/playlists")
    suspend fun putToPlaylist(
        @Header("cookie") cookie: String,
        @Field("nid") nid: Int,
        @Field("playlist") playlist: Int
    ): PlaylistModifyResponse

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "api/playlists", hasBody = true)
    suspend fun deleteFromPlaylist(
        @Header("cookie") cookie: String,
        @Field("nid") nid: Int,
        @Field("playlist") playlist: Int
    ): PlaylistModifyResponse
}