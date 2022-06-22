package app.androwara.data.model.playlist

import app.androwara.data.model.index.MediaPreview

class PlaylistDetail(
    val title: String,
    val nid: Int,
    val videolist: List<MediaPreview>
)