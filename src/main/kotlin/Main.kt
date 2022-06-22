import app.androwara.data.api.IwaraHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun main(args: Array<String>) {
    withContext(Dispatchers.IO) {
        val parser = IwaraHelper.iwaraParser
        val session = parser.login("xiue233", "xyishacker51").apply {
            if (isFailed()) {
                println("登录失败")
                println("错误信息:" + errorMessage())
                return@withContext
            }
        }.read()
        val videoDetail = parser.getVideoPageDetail(session, "q8rmphdqy2coakodr").apply {
            if (isFailed()) {
                println("错误信息:" + errorMessage())
                return@withContext
            }
        }.read()
        println(videoDetail.title)
    }
}