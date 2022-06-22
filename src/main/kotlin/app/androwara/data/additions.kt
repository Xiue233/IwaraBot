package app.androwara.data

import app.androwara.data.model.index.MediaPreview
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
inline fun <T> List<T>.fastForEach(action: (T) -> Unit) {
    contract { callsInPlace(action) }
    for (index in indices) {
        val item = get(index)
        action(item)
    }
}

enum class SortType(val value: String) {
    DATE("date"),
    VIEWS("views"),
    LIKES("likes")
}

enum class PlaylistAction {
    PUT,
    DELETE
}

class PlaylistOverview(
    val name: String,
    val id: String
)

class PlaylistDetail(
    val title: String,
    val nid: Int,
    val videolist: List<MediaPreview>
)


