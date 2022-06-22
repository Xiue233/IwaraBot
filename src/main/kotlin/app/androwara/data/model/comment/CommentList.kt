package app.androwara.data.model.comment

import app.androwara.data.fastForEach
import kotlin.contracts.ExperimentalContracts

data class CommentList(
    val total: Int,
    val page: Int,
    val hasNext: Boolean,
    val comments: List<Comment>,
)

data class Comment(
    val authorId: String,
    val authorName: String,
    val authorPic: String,
    val posterType: CommentPosterType,

    val nid: Int,
    val commentId: Int,

    val content: String,
    val date: String,
    val fromIwara4a: Boolean,

    var reply: List<Comment>
)

enum class CommentPosterType {
    NORMAL,
    SELF,
    OWNER
}

@ExperimentalContracts
fun Comment.getAllReplies(): List<Comment> {
    val list = mutableListOf<Comment>()
    list.addAll(reply)
    reply.fastForEach {
        list.addAll(it.getAllReplies())
    }
    return list
}