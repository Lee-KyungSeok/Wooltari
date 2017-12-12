package kr.co.wooltari.domain.pet;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.co.wooltari.BuildConfig;
import kr.co.wooltari.R;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.MedicalInfoDummy;
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

public class PetDataManager {

    /**
     * Retrofit을 생성
     */
    public static <I> I create(Class<I> IRetrofitClass, boolean isNull){
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
                        requestBuilder.header("Authorization", "Token "+UserDummy.data.token);
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

    /**
     * 펫 정보를 저장
     */
    public static void savePet(Activity activity, Pet pet){
        IPet service = PetDataManager.create(IPet.class, true);
        Call<PetOne> remote = service.savePetData(UserDummy.data.pk,pet);
        remote.enqueue(new Callback<PetOne>() {
            @Override
            public void onResponse(Call<PetOne> call, retrofit2.Response<PetOne> response) {
                Log.e("message","====="+response.message());

                if(201 == response.code()){
                    Log.e("save data","===="+response.body().getPet().toString());
                    Toast.makeText(activity, activity.getResources().getString(R.string.pet_profile_save_success), Toast.LENGTH_SHORT).show();
                    activity.setResult(Activity.RESULT_OK);
                    activity.finish();
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 400:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else if(error.getName()!=null){
                                    Toast.makeText(activity, error.getName()[0], Toast.LENGTH_SHORT).show();
                                } else if(error.getBirth_date()!=null){
                                    Toast.makeText(activity, error.getBirth_date()[0], Toast.LENGTH_SHORT).show();
                                } else if(error.getGender()!=null){
                                    Toast.makeText(activity, error.getGender()[0], Toast.LENGTH_SHORT).show();
                                } else if(error.getBody_color()!=null){
                                    Toast.makeText(activity, error.getBody_color()[0], Toast.LENGTH_SHORT).show();
                                } else if(error.getSpecies()!=null){
                                    Toast.makeText(activity, error.getSpecies()[0], Toast.LENGTH_SHORT).show();
                                } else if(error.getBreeds()!=null){
                                    Toast.makeText(activity, error.getBreeds()[0], Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 401:
                                Toast.makeText(activity, activity.getResources().getString(R.string.invalid_token), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<PetOne> call, Throwable t) {
                Log.e("savePet Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 펫 정보를 업데이트
     */
    public static void updatePet(Activity activity, int petPK, CallbackGetPet callback){
        IPet service = PetDataManager.create(IPet.class, true);
        Call<PetOne> remote = service.updatePetData(UserDummy.data.pk, petPK);
        remote.enqueue(new Callback<PetOne>() {
            @Override
            public void onResponse(Call<PetOne> call, retrofit2.Response<PetOne> response) {
                Log.e("message","====="+response.message());
                if(200 == response.code()){
                    callback.getPetData(response.body().getPet());
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 400:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 401:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.invalid_token), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PetOne> call, Throwable t) {
                Log.e("updatePet Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 펫 정보를 삭제
     */
    public static void deletePet(Activity activity, int petPK, CallbackDeletePetData callback){
        IPet service = PetDataManager.create(IPet.class, true);
        Call<Pet> remote = service.deletePetData(UserDummy.data.pk, petPK);
        remote.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, retrofit2.Response<Pet> response) {
                Log.e("message","====="+response.message());
                if(204 == response.code()){
                    callback.deletePetData();
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 401:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.invalid_token), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 403:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 404:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.pet_null), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Log.e("getPet Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 펫 리스트를 가져옴
     */
    public static void getPetList(Activity activity, CallbackGetPetList callback){
        IPet service = PetDataManager.create(IPet.class, true);
        Call<PetList> remote = service.getPetList(UserDummy.data.pk);
        List<Pet> dataList = new ArrayList<>();
        remote.enqueue(new Callback<PetList>() {
            @Override
            public void onResponse(Call<PetList> call, retrofit2.Response<PetList> response) {
                Log.e("message","====="+response.message());
                if(200 == response.code()){
                    if(response.body().getResults()!=null) dataList.addAll(response.body().getResults());
                    if(response.body().getNext()!=null) {
                        getPetListNext(activity, 2, petList -> {
                            dataList.addAll(petList);
                            callback.getPetList(dataList);
                            // 임시 state, medical data 생성
                            for(Pet pet : dataList) {
                                MedicalInfoDummy.createMedicalDummy(pet.getPk());
                                HealthStateDummy.createStateDummy(pet.getPk());
                            }
                        });
                    } else {
                        callback.getPetList(dataList);
                        for(Pet pet : dataList) {
                            MedicalInfoDummy.createMedicalDummy(pet.getPk());
                            HealthStateDummy.createStateDummy(pet.getPk());
                        }
                    }
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 404:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PetList> call, Throwable t) {
                Log.e("getPetList Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 다음 리스트를 가져오는 메소드
     */
    public static void getPetListNext(Activity activity, int pageNumber, CallbackGetPetList callback) {
        IPet service = PetDataManager.create(IPet.class, true);
        Call<PetList> remote = service.getPetListNext(UserDummy.data.pk,pageNumber);
        List<Pet> dataList = new ArrayList<>();
        remote.enqueue(new Callback<PetList>() {
            @Override
            public void onResponse(Call<PetList> call, retrofit2.Response<PetList> response) {
                Log.e("message","====="+response.message());
                if(200 == response.code()){
                    if(response.body().getResults()!=null) dataList.addAll(response.body().getResults());
                    else callback.getPetList(dataList);

                    if(response.body().getNext()!=null) {
                        getPetListNext(activity, pageNumber+1, dataList::addAll);
                    } else {
                        callback.getPetList(dataList);
                    }
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 404:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PetList> call, Throwable t) {
                Log.e("getPetListNext Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 특정 펫 정보를 가져옴
     */
    public static void getPet(Activity activity, int petPk, CallbackGetPet callback){
        IPet service = PetDataManager.create(IPet.class, true);
        Call<PetOne> remote = service.getPetData(UserDummy.data.pk, petPk);
        remote.enqueue(new Callback<PetOne>() {
            @Override
            public void onResponse(Call<PetOne> call, retrofit2.Response<PetOne> response) {
                Log.e("message","====="+response.message());
                if(200 == response.code()){
                    callback.getPetData(response.body().getPet());
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 404:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PetOne> call, Throwable t) {
                Log.e("getPet Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 펫의 나이를 가져옴
     */
    public static void getPetAge(Activity activity, int petPK, CallbackGetPetAge callback){
        IPet service = PetDataManager.create(IPet.class,true);
        Call<Age> remote = service.getAge(UserDummy.data.pk,petPK);
        remote.enqueue(new Callback<Age>() {
            @Override
            public void onResponse(Call<Age> call, retrofit2.Response<Age> response) {
                Log.e("message","====="+response.message());
                if(200 == response.code()){
                    callback.getPetAge(response.body());
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 404:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Age> call, Throwable t) {
                Log.e("getPetAge Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface CallbackGetPet{
        void getPetData(Pet petData);
    }

    public interface CallbackGetPetList{
        void getPetList(List<Pet> petList);
    }

    public interface CallbackGetPetAge{
        void getPetAge(Age petAge);
    }

    public interface CallbackDeletePetData{
        void deletePetData();
    }
}
