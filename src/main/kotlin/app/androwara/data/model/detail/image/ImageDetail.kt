package app.androwara.data.model.detail.image

import app.androwara.data.model.comment.CommentPostParam

data class ImageDetail(
    val id: String,
    val nid: Int,
    val title: String,
    val imageLinks: List<String>,
    val description: String,

    val authorId: String,
    val authorName: String,
    val authorProfilePic: String,

    val watchs: String,

    val commentPostParam: CommentPostParam,
)