package app.androwara.util

fun isNotEmpty(obj: Any?): Boolean {
    if (obj == null)
        return false
    if (obj is String && obj == "") {
        return false
    }
    return true
}
