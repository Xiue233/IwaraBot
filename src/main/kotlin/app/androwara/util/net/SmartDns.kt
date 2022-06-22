package app.androwara.util.net

import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress
import java.util.concurrent.TimeUnit

object SmartDns : Dns {
    private const val CLOUDFLARE_DOH = "https://1.0.0.1/dns-query"

    private var dnsClient: DnsOverHttps = DnsOverHttps.Builder()
        .client(
            OkHttpClient.Builder()
                .connectTimeout(10_000, TimeUnit.MILLISECONDS)
                .build()
        )
        .url(
            //TODO 检查该网址是否可用
            CLOUDFLARE_DOH.toHttpUrl()
            //"https://dns.alidns.com/dns-query".toHttpUrl()
        )
        .build()

    override fun lookup(hostname: String): List<InetAddress> {
        return Dns.SYSTEM.lookup(hostname)
    }
}