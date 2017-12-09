package kr.co.wooltari.domain.pet;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import kr.co.wooltari.BuildConfig;
import kr.co.wooltari.domain.UserDummy;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kyung on 2017-12-08.
 */

public class PetLoader {

    public static <I> I create(Class<I> IRetrofitClass, boolean isNull){
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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder requestBuilder = chain.request().newBuilder();
                        requestBuilder.header("Content-Type", "application/json");
                        requestBuilder.header("Authorization", "Token "+UserDummy.data.token);
                        return chain.proceed(requestBuilder.build());
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(IRetrofitClass);
    }

    public static void getPet(int PetPK){
        IPet service = PetLoader.create(IPet.class, false);
        Call<Pet> remote = service.getPetData(UserDummy.data.pk, PetPK);
        remote.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, retrofit2.Response<Pet> response) {
                Log.e("response","====="+response.toString());
                Log.e("isSuccessful","====="+response.isSuccessful());
                Log.e("code","====="+response.code());
                Log.e("message","====="+response.message());
                Log.e("headers","====="+response.headers().toString());
                Log.e("raw","====="+response.raw().toString());
                Log.e("raw","====="+response.body().toString()); // 에러가 아닌 경우
                // 에러처리
//                try {
//                    String errorString = response.errorBody().string();
//                    Gson gson = new Gson();
//                    PetError error = gson.fromJson(errorString,PetError.class);
//                    Log.e("error","====="+error.getDetail());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {

            }
        });
    }
}
