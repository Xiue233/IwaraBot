package app.androwara.data.model.user

import app.androwara.data.model.comment.CommentPostParam

data class UserData(
    val userId: String,
    val username: String,
    val userIdMedia: String,

    val follow: Boolean,
    val followLink: String,

    val friend: UserFriendState,
    val id: Int,

    val pic: String,
    val joinDate: String,
    val lastSeen: String,
    val about: String,

    val commentId: Int,
    val commentPostParam: CommentPostParam
) {
    companion object {
        val LOADING = UserData(
            "",
            "",
            "",
            false,
            "",
            UserFriendState.NOT,
            0,
            "",
            "",
            "",
            "",
            0,
            CommentPostParam.Default
        )
    }
}

enum class UserFriendState {
    NOT,
    PENDING,
    ALREADY
}