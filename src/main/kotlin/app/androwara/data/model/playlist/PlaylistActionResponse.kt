package app.androwara.data.model.playlist


import com.google.gson.annotations.SerializedName

data class PlaylistActionResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)