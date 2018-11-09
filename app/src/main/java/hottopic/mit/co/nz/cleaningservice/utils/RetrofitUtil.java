package hottopic.mit.co.nz.cleaningservice.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wendychen on 26/10/17.
 */

public class RetrofitUtil {

    /**
     * Get the Retrofit entity to request
     * @param header request header
     * @param baseUrl request base url
     * @return
     */
    private static Retrofit getDefaultRetrofit(Map<String, String> header, @NonNull String baseUrl) {
        OkHttpClient.Builder httpClientBuilder = getOkHttpClientBuilder(header);
        OkHttpClient client = httpClientBuilder.build();
        if (!baseUrl.contains("?") && baseUrl.lastIndexOf("/") != baseUrl.length() - 1) {
            baseUrl += "/";
        }
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = "http://localhost/";
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GeneralUtil.getGson())).build();
    }

    /**
     * Get the Retrofit entity to request
     * @param header request header
     * @param baseUrl request base url
     * @return
     */
    private static Retrofit getStringRetrofit(Map<String, String> header, @NonNull String baseUrl) {
        OkHttpClient.Builder httpClientBuilder = getOkHttpClientBuilder(header);
        OkHttpClient client = httpClientBuilder.build();
        if (!baseUrl.contains("?") && baseUrl.lastIndexOf("/") != baseUrl.length() - 1) {
            baseUrl += "/";
        }
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = "http://localhost/";
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    /**
     * Get the OkHttpClient entity
     * @param header request header
     * @return
     */
    private static OkHttpClient.Builder getOkHttpClientBuilder(final Map<String, String> header) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addNetworkInterceptor(loggingInterceptor);
        if (header != null && header.size() > 0) {
            httpClientBuilder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Log.d("Retrofit", "intercept called");
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder();
                    Iterator<String> iterator = header.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        String value = header.get(key);
                        requestBuilder.addHeader(key, value);
                    }

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        return httpClientBuilder;
    }

    /**
     * Get the service entity
     * @param header request header
     * @param baseUrl request base url
     * @param serviceClass service type
     * @param <S>
     * @return
     */
    public static <S> S getService(Map<String, String> header, String baseUrl, Class<S> serviceClass) {
        return getDefaultRetrofit(header, baseUrl).create(serviceClass);
    }

    public static <S> S getRawService(Map<String, String> header, String baseUrl, Class<S> serviceClass) {
        return getStringRetrofit(header, baseUrl).create(serviceClass);
    }

    public static <S> S getService(String baseUrl, Class<S> serviceClass) {
        return getDefaultRetrofit(null, baseUrl).create(serviceClass);
    }
}
