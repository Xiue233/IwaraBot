package app.androwara.data.api

import app.androwara.data.api.backend.Iwara4aBackendAPI
import app.androwara.data.service.IwaraParser
import app.androwara.data.service.IwaraService
import app.androwara.util.net.CookieJarHelper
import app.androwara.util.net.Retry
import app.androwara.util.net.SmartDns
import app.androwara.util.net.UserAgentInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object IwaraHelper {
    private const val TIMEOUT = 15_000L
    private const val HTTP_PROXY_HOST = "127.0.0.1"
    private const val HTTP_PROXY_PORT = 1082

    val okHttpClient: OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        addInterceptor(UserAgentInterceptor())
        //.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
        cookieJar(CookieJarHelper())
        addInterceptor(Retry())
        dns(SmartDns)
        //设置代理
        proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(HTTP_PROXY_HOST, HTTP_PROXY_PORT)))
        //忽略https检验
        val x509TrustManager = object : X509TrustManager {
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
            }

            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        val sslContext: SSLContext? = try {
            SSLContext.getInstance("SSL")
                ?.apply {
                    init(null, arrayOf(x509TrustManager), SecureRandom())
                }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        val hostnameVerifier = HostnameVerifier { _, _ -> true }
        sslSocketFactory(sslContext!!.socketFactory!!, x509TrustManager)
        hostnameVerifier(hostnameVerifier)
    }.build()

    val iwaraService: IwaraService by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://ecchi.iwara.tv/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IwaraService::class.java)
    }

    val iwaraParser: IwaraParser by lazy {
        IwaraParser(okHttpClient)
    }

    val iwaraApi: IwaraApi by lazy {
        IwaraApiImpl(iwaraParser, iwaraService)
    }

    val backendApi: Iwara4aBackendAPI by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://iwara.matrix.rip")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Iwara4aBackendAPI::class.java)
    }

}