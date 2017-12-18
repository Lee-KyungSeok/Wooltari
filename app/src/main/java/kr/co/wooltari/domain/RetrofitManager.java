package kr.co.wooltari.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import kr.co.wooltari.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kyung on 2017-12-18.
 */

public class RetrofitManager {
    /**
     * Retrofit을 생성
     */
    public static <I> I create(Class<I> IRetrofitClass, boolean isNull, boolean isToken){
        // gson 데이터를 null 값 여부에 따라 세팅
        Gson gson;
        if(isNull){
            gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation() // expose를 적용
                    .serializeNulls() // null 을 보냄
                    .create();
        } else {
            gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
        }

        //로그 추적하는 okHttpClient 생성
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder requestBuilder = chain.request().newBuilder();
                        requestBuilder.header("Content-Type", "application/json");
                        if(isToken) requestBuilder.header("Authorization", "Token "+UserDummy.data.token);
                        return chain.proceed(requestBuilder.build());
                    }
                })
                .build();

        // 레트로핏을 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(IRetrofitClass);
    }
}
