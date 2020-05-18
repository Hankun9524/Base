package com.cictec.ibd.base.http


import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.*
import java.util.concurrent.TimeUnit

/**
 * 执行请求接口
 *
 *
 * CopyRight (c)2018: <北京中航讯科技股份有限公司>
 * @author    HanKun
 * @date      2018-11-18
 * @version   1.0
 */


const val UNKNOWN_ERROR = "C100-未知错误"


/**
 *  通用的网络封装库
 *
 * @param url 访问的url
 * @return 返回构建好的网络库
 *
 */
fun getDefaultRetrofit(url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}


/**
 * 获取请求client
 *
 */
fun getClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain: Interceptor.Chain ->
            val builder = chain.request().newBuilder()
            for (entry in HeaderManager.getHeader()) {
                builder.addHeader(entry.key, entry.value)
            }
            chain.proceed(builder.build())
        }
        .build()
}

/**
 * 获取异常
 *
 * @param e 异常
 * @return 返回异常信息文字提示
 */
fun getExceptionMsg(e: Throwable?): String {
    return if (e == null) {
        "C00-未知错误"
    } else {
        e.printStackTrace()
        when (e) {
            is JsonSyntaxException -> "C01-查询数据错误"
            is JsonIOException -> "C02-查询数据错误"
            is JsonParseException -> "C03-查询数据错误"
            is BindException -> "C04-网络访问端口被占用"
            is HttpRetryException -> "C05-网络访问失败"
            is ConnectException -> "C06-无法连接服务器，请检查您的网络"
            is UnknownHostException -> "C07-无法解析的域名，请检查网络配置"
            is MalformedURLException -> "C08-网络访问地址错误，请检查网络配置"
            is NoRouteToHostException -> "C09-网络访问被拦截，请检查路由器配置"
            is PortUnreachableException -> "C10-端口异常错误"
            is ProtocolException -> "C11-网络访问失败"
            is UnknownServiceException -> "C12-网络访问失败"
            is URISyntaxException -> "C13-网络访问失败"
            is SocketTimeoutException -> "C14网络链接超时，请检查您的网络"
            is SocketException -> "C15-网络访问失败"
            is IllegalArgumentException -> "C16-配置文件参数错误，请检查网络配置"
            else -> "C200-未知错误"
        }
    }
}