package app.androwara.util

import kotlin.math.roundToInt

fun Float.format() = (this * 1000).roundToInt() / 1000.0
fun Float.formatToString() = format().toString()