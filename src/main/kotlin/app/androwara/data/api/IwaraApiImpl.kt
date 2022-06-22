package app.androwara.data.api

import app.androwara.data.PlaylistAction
import app.androwara.data.PlaylistDetail
import app.androwara.data.PlaylistOverview
import app.androwara.data.SortType
import app.androwara.data.model.comment.CommentList
import app.androwara.data.model.comment.CommentPostParam
import app.androwara.data.model.detail.image.ImageDetail
import app.androwara.data.model.detail.video.VideoDetail
import app.androwara.data.model.flag.FollowResponse
import app.androwara.data.model.flag.LikeResponse
import app.androwara.data.model.friends.FriendList
import app.androwara.data.model.index.MediaList
import app.androwara.data.model.index.MediaType
import app.androwara.data.model.index.SubscriptionList
import app.androwara.data.model.playlist.PlaylistPreview
import app.androwara.data.model.session.Session
import app.androwara.data.model.user.Self
import app.androwara.data.model.user.UserData
import app.androwara.data.service.IwaraParser
import app.androwara.data.service.IwaraService
import app.androwara.util.net.autoRetry

/**
 * IwaraAPI接口的具体实现
 *
 * 内部持有 iwaraParser 和 iwaraService 两个模块, 根据资源是否可以
 * 通过restful api直接访问来选择使用哪个模块获取数据
 */
class IwaraApiImpl(
    private val iwaraParser: IwaraParser,
    private val iwaraService: IwaraService
) : IwaraApi {
    override suspend fun login(username: String, password: String): Response<Session> =
        iwaraParser.login(username, password)

    override suspend fun getSelf(session: Session): Response<Self> = iwaraParser.getSelf(session)

    override suspend fun getSubscriptionList(
        session: Session,
        page: Int
    ): Response<SubscriptionList> = autoRetry { iwaraParser.getSubscriptionList(session, page) }

    override suspend fun getImagePageDetail(
        session: Session,
        imageId: String
    ): Response<ImageDetail> = autoRetry { iwaraParser.getImagePageDetail(session, imageId) }

    override suspend fun getVideoPageDetail(
        session: Session,
        videoId: String
    ): Response<VideoDetail> = autoRetry {
        iwaraParser.getVideoPageDetail(session, videoId)
    }

    override suspend fun like(
        session: Session,
        like: Boolean,
        likeLink: String
    ): Response<LikeResponse> = iwaraParser.like(session, like, likeLink)

    override suspend fun follow(
        session: Session,
        follow: Boolean,
        followLink: String
    ): Response<FollowResponse> = iwaraParser.follow(session, follow, followLink)

    override suspend fun getCommentList(
        session: Session,
        mediaType: MediaType,
        mediaId: String,
        page: Int
    ): Response<CommentList> = autoRetry {
        iwaraParser.getCommentList(
            session,
            mediaType,
            mediaId,
            page
        )
    }

    override suspend fun getMediaList(
        session: Session,
        mediaType: MediaType,
        page: Int,
        sort: SortType,
        filter: Set<String>
    ): Response<MediaList> = autoRetry(maxRetry = 3) {
        iwaraParser.getMediaList(
            session,
            mediaType,
            page,
            sort,
            filter
        )
    }

    override suspend fun getUser(session: Session, userId: String): Response<UserData> = autoRetry {
        iwaraParser.getUser(
            session,
            userId
        )
    }

    override suspend fun getUserMediaList(
        session: Session,
        userIdOnVideo: String,
        mediaType: MediaType,
        page: Int
    ): Response<MediaList> = autoRetry {
        iwaraParser.getUserMediaList(session, userIdOnVideo, mediaType, page)
    }

    override suspend fun getUserPageComment(
        session: Session,
        userId: String,
        page: Int
    ): Response<CommentList> = autoRetry {
        iwaraParser.getUserPageComment(
            session,
            userId,
            page
        )
    }

    override suspend fun search(
        session: Session,
        query: String,
        page: Int,
        sort: SortType,
        filter: Set<String>
    ): Response<MediaList> = autoRetry {
        iwaraParser.search(
            session,
            query,
            page,
            sort,
            filter
        )
    }

    override suspend fun getLikePage(session: Session, page: Int): Response<MediaList> = autoRetry {
        iwaraParser.getLikePage(
            session,
            page
        )
    }

    override suspend fun getPlaylistPreview(session: Session, nid: Int): Response<PlaylistPreview> {
        return try {
            Response.success(iwaraService.getPlaylistPreview(session.toString(), nid))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.failed(e.javaClass.simpleName)
        }
    }

    override suspend fun modifyPlaylist(
        session: Session,
        nid: Int,
        playlist: Int,
        action: PlaylistAction
    ): Response<Int> {
        return try {
            val result = when (action) {
                PlaylistAction.PUT -> {
                    iwaraService.putToPlaylist(
                        cookie = session.toString(),
                        nid = nid,
                        playlist = playlist
                    )
                }
                PlaylistAction.DELETE -> {
                    iwaraService.deleteFromPlaylist(
                        cookie = session.toString(),
                        nid = nid,
                        playlist = playlist
                    )
                }
            }
            Response.success(result.status)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.failed(e.javaClass.simpleName)
        }
    }

    override suspend fun postComment(
        session: Session,
        nid: Int,
        commentId: Int?,
        content: String,
        commentPostParam: CommentPostParam
    ) {
        iwaraParser.postComment(session, nid, commentId, content, commentPostParam)
    }

    override suspend fun getPlaylistOverview(session: Session): Response<List<PlaylistOverview>> {
        return iwaraParser.getPlaylistOverview(session)
    }

    override suspend fun getPlaylistDetail(
        session: Session,
        playlistId: String
    ): Response<PlaylistDetail> {
        return iwaraParser.getPlaylistDetail(
            session,
            playlistId
        )
    }

    override suspend fun createPlaylist(session: Session, name: String): Response<Boolean> {
        return try {
            Response.success(iwaraService.createPlaylist(name).status == 1)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.failed(e.javaClass.name)
        }
    }

    override suspend fun deletePlaylist(session: Session, id: Int): Response<Boolean> {
        return iwaraParser.deletePlaylist(session, id)
    }

    override suspend fun changePlaylistName(
        session: Session,
        id: Int,
        name: String
    ): Response<Boolean> {
        return iwaraParser.changePlaylistName(session, id, name)
    }

    override suspend fun getFriendList(session: Session): Response<FriendList> {
        return iwaraParser.getFriendList(session)
    }
}