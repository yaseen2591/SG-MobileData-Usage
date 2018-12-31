package projects.yaseen.sg_mobiledata_usage.rest;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import projects.yaseen.sg_mobiledata_usage.BuildConfig;
import projects.yaseen.sg_mobiledata_usage.util.Constants;
import projects.yaseen.sg_mobiledata_usage.util.Util;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "https://data.gov.sg/api/action/";
    private static Retrofit retrofit = null;
    private static Retrofit.Builder builder;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static <S> S createService(
            Class<S> serviceClass, Context mContext) {
        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY);

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(mContext.getCacheDir(), cacheSize);

        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();


        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(logging);
        }
        httpClient.cache(cache);
        httpClient.addNetworkInterceptor(onlineInterceptor);
        if (!Util.isNetworkAvailable(mContext)) {
            httpClient.addInterceptor(offlineInterceptor);
        }
        builder.client(httpClient.build());
        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    static Interceptor onlineInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    static Interceptor offlineInterceptor= new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
                int maxStale = 60 * 60 * 24 * 30; // Offline cache available for 30 days
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            return chain.proceed(request);
        }
    };
}
