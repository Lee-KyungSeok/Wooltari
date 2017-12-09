package kr.co.wooltari.domain.pet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.wooltari.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kyung on 2017-12-08.
 */

public class RetrofitService {

    public static <I> I create(Class<I> model, boolean isNull){
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(model);
    }
}
