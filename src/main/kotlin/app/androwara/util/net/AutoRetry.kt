package app.androwara.util.net

import app.androwara.data.api.Response

private const val TAG = "AutoRetry"

/**
 * 自动重试函数, 用于iwara的服务器大概率会无响应，因此需要尝试多次才能获取到响应内容
 *
 * @param maxRetry 重试次数
 * @param action 重试体
 * @return 最终响应
 */
suspend fun <T> autoRetry(
    maxRetry: Int = 3, // 重连次数
    action: suspend () -> Response<T>
): Response<T> {
    repeat(maxRetry - 1) {
        val start = System.currentTimeMillis()
        val response = action()
        if (response.isSuccess()) {
            return response
        }
    }
    return action()
}