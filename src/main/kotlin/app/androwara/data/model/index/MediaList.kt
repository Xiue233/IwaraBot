package app.androwara.data.model.index

data class MediaList(
    val currentPage: Int,
    val hasNext: Boolean,
    val mediaList: List<MediaPreview>
)