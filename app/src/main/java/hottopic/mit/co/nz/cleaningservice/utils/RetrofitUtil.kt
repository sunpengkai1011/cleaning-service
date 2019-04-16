package hottopic.mit.co.nz.cleaningservice.utils

import android.text.TextUtils
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by wendychen on 26/10/17.
 */

object RetrofitUtil {

    /**
     * Get the Retrofit entity to request
     * @param header request header
     * @param baseUrl request base url
     * @return
     */
    private fun getDefaultRetrofit(header: Map<String, String>?, baseUrl: String): Retrofit {
        var mBaseUrl = baseUrl
        val httpClientBuilder = getOkHttpClientBuilder(header)
        val client = httpClientBuilder.build()
        if (!mBaseUrl.contains("?") && mBaseUrl.lastIndexOf("/") != mBaseUrl.length - 1) {
            mBaseUrl += "/"
        }
        if (TextUtils.isEmpty(mBaseUrl)) {
            mBaseUrl = "http://localhost/"
        }
        return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GeneralUtil.gson)).build()
    }

//    /**
//     * Get the Retrofit entity to request
//     * @param header request header
//     * @param baseUrl request base url
//     * @return
//     */
    /*private fun getStringRetrofit(header: Map<String, String>, baseUrl: String): Retrofit {
        var mBaseUrl = baseUrl
        val httpClientBuilder = getOkHttpClientBuilder(header)
        val client = httpClientBuilder.build()
        if (!mBaseUrl.contains("?") && mBaseUrl.lastIndexOf("/") != mBaseUrl.length - 1) {
            mBaseUrl += "/"
        }
        if (TextUtils.isEmpty(mBaseUrl)) {
            mBaseUrl = "http://localhost/"
        }
        return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build()
    }*/

    /**
     * Get the OkHttpClient entity
     * @param header request header
     * @return
     */
    private fun getOkHttpClientBuilder(header: Map<String, String>?): OkHttpClient.Builder {
        val httpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addNetworkInterceptor(loggingInterceptor)
        if (header != null && header.isNotEmpty()) {
            httpClientBuilder.interceptors().add(Interceptor { chain ->
                Log.d("Retrofit", "intercept called")
                val original = chain.request()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                val iterator = header.keys.iterator()
                while (iterator.hasNext()) {
                    val key = iterator.next()
                    val value = header[key]
                    requestBuilder.addHeader(key, value)
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            })
        }
        return httpClientBuilder
    }

    /**
     * Get the service entity
     * @param header request header
     * @param baseUrl request base url
     * @param serviceClass service type
     * @param <S>
     * @return
    </S> */
    fun <S> getService(header: Map<String, String>?, baseUrl: String, serviceClass: Class<S>): S {
        return getDefaultRetrofit(header, baseUrl).create(serviceClass)
    }

    /*fun <S> getRawService(header: Map<String, String>, baseUrl: String, serviceClass: Class<S>): S {
        return getStringRetrofit(header, baseUrl).create(serviceClass)
    }*/

    /*fun <S> getService(baseUrl: String, serviceClass: Class<S>): S {
        return getDefaultRetrofit(null, baseUrl).create(serviceClass)
    }*/
}
