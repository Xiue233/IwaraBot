# 网络层使用说明

### 一、基本使用

网络API相关操作均集成于 [IwaraHelper](src/main/kotlin/app/androwara/data/api/IwaraHelper.kt) 内，其中


| 变量名       | 职位                      |
| -------------- | --------------------------- |
| iwaraParser  | 提供封装后的Iwara网络操作 |
| iwaraApi     | 提供Iwara网络操作接口     |
| backendApi   | 提供Iwara后端网络操作接口 |
| iwaraService | Retrofit创建的service     |

Tips:建议在协程内操作。

### 二、登录

每次使用建议登录,

`IwaraHelper.iwaraParser.login("username","pwd")`

并且应该判断是否成功，接受login的返回值作为Session来保存登录状态。

**注意：Session应该及时保存并传入相关方法，否则将丢失登录状态。**

```
withContext(Dispatchers.IO) {
    val parser = IwaraHelper.iwaraParser
    val session = parser.login("username", "pwd").apply {
            if (isFailed()) {
                println("登录失败")
                println("错误信息:" + errorMessage())
                return@withContext
            }
    }.read()
    ...
}
```

### 三、基本操作
请查看 [IwaraParser](src/main/kotlin/app/androwara/data/service/IwaraParser.kt)
