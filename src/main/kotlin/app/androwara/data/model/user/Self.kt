package app.androwara.data.model.user

data class Self(
    val id: String,
    val numId: Int,
    val nickname: String,
    val profilePic: String,
    val about: String? = null,
    val friendRequest: Int = 0,
    val messages: Int = 0
) {
    companion object {
        val GUEST = Self("", 0, "访客", "https://ecchi.iwara.tv/sites/all/themes/main/img/logo.png")
    }
}