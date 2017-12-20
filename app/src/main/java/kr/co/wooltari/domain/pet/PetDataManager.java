package kr.co.wooltari.domain.pet;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.application.WooltariApp;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.MedicalInfoDummy;
import kr.co.wooltari.domain.retrofit.RetrofitManager;
import kr.co.wooltari.domain.UserDummy;
import kr.co.wooltari.domain.retrofit.IPet;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Kyung on 2017-12-08.
 */

public class PetDataManager {

    /**
     * 펫 정보를 저장
     */
    public static void savePet(Activity activity, Pet pet, File file , CallbackGetPet callback){
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<PetOne> remote = null;
        if(file == null){
            remote = service.savePetData(WooltariApp.userPK,pet);
        } else {
            // form을 multipart/formdata로 하지 말것...!!
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), pet.getName());
            RequestBody birth_date = RequestBody.create(MediaType.parse("text/plain"), pet.getBirth_date());
            RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), pet.getGender());
            RequestBody body_color = RequestBody.create(MediaType.parse("text/plain"), pet.getBody_color());
            RequestBody species = RequestBody.create(MediaType.parse("text/plain"), pet.getSpecies());
            RequestBody breeds = RequestBody.create(MediaType.parse("text/plain"), pet.getBreeds());
            RequestBody identified_number = RequestBody.create(MediaType.parse("text/plain"), pet.getIdentified_number());
            RequestBody is_neutering = null;
            if(pet.getIs_neutering()) is_neutering = RequestBody.create(MediaType.parse("text/plain"),"True");
            else is_neutering = RequestBody.create(MediaType.parse("text/plain"),"False");
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
            remote = service.savePetData(WooltariApp.userPK ,name,birth_date,gender,body_color,species,breeds,identified_number,is_neutering,image);
        }
        remote.enqueue(new Callback<PetOne>() {
            @Override
            public void onResponse(Call<PetOne> call, retrofit2.Response<PetOne> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
                if(201 == response.code()){
                    Log.e("save data","===="+response.body().getPet().toString());
                    callback.getPetData(response.body().getPet());
                    Toast.makeText(activity, activity.getResources().getString(R.string.pet_profile_save_success), Toast.LENGTH_SHORT).show();
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
                            case 405:
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
     * 펫 정보(활성화 여부)를 업데이트
     *  - boolean 이 사용 안되어 String 형태로 넘겨줌.
     */
    public static void updatePetActive(Activity activity, int petPK, ActivePet active, CallbackGetPet callback){
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<PetOne> remote = service.updatePetActive(WooltariApp.userPK, petPK, active);
        remote.enqueue(new Callback<PetOne>() {
            @Override
            public void onResponse(Call<PetOne> call, retrofit2.Response<PetOne> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
                if(200 == response.code()){
                    Log.e("update active data","===="+response.body().getPet().toString());
                    Toast.makeText(activity, activity.getResources().getString(R.string.pet_profile_change_active_success), Toast.LENGTH_SHORT).show();
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
                            case 405:
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
                Log.e("updatePet Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 펫 정보를 업데이트
     */
    public static void updatePet(Activity activity, int petPK, Pet pet, CallbackGetPet callback){
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<PetOne> remote = service.updatePetData(WooltariApp.userPK, petPK, pet);
        remote.enqueue(new Callback<PetOne>() {
            @Override
            public void onResponse(Call<PetOne> call, retrofit2.Response<PetOne> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
                if(200 == response.code()){
                    Toast.makeText(activity, activity.getResources().getString(R.string.pet_profile_update_success), Toast.LENGTH_SHORT).show();
                    Log.e("update data","===="+response.body().getPet().toString());
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
                            case 405:
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
                Log.e("updatePet Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 펫 정보를 삭제
     */
    public static void deletePet(Activity activity, int petPK, CallbackDeletePetData callback){
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<Pet> remote = service.deletePetData(WooltariApp.userPK, petPK);
        remote.enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, retrofit2.Response<Pet> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
                if(204 == response.code()){
                    Toast.makeText(activity, activity.getResources().getString(R.string.pet_profile_delete_success), Toast.LENGTH_SHORT).show();
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
                            case 405:
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
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<PetList> remote = service.getPetList(WooltariApp.userPK);
        List<Pet> dataList = new ArrayList<>();
        remote.enqueue(new Callback<PetList>() {
            @Override
            public void onResponse(Call<PetList> call, retrofit2.Response<PetList> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
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
                            case 405:
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
            public void onFailure(Call<PetList> call, Throwable t) {
                Log.e("getPetList Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 다음 리스트를 가져오는 메소드
     *  - (API 에서 삭제된 기능)
     */
    public static void getPetListNext(Activity activity, int pageNumber, CallbackGetPetList callback) {
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<PetList> remote = service.getPetListNext(WooltariApp.userPK,pageNumber);
        List<Pet> dataList = new ArrayList<>();
        remote.enqueue(new Callback<PetList>() {
            @Override
            public void onResponse(Call<PetList> call, retrofit2.Response<PetList> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
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
                            case 405:
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
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<PetOne> remote = service.getPetData(WooltariApp.userPK, petPk);
        remote.enqueue(new Callback<PetOne>() {
            @Override
            public void onResponse(Call<PetOne> call, retrofit2.Response<PetOne> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
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
                            case 405:
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
                Log.e("getPet Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 펫의 나이를 가져옴
     */
    public static void getPetAge(Activity activity, int petPK, CallbackGetPetAge callback){
        IPet service = RetrofitManager.create(IPet.class,true, true);
        Call<Age> remote = service.getAge(WooltariApp.userPK,petPK);
        remote.enqueue(new Callback<Age>() {
            @Override
            public void onResponse(Call<Age> call, retrofit2.Response<Age> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
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
                            case 405:
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
            public void onFailure(Call<Age> call, Throwable t) {
                Log.e("getPetAge Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getBreedsList(Activity activity, Breed speciesFormatBreed, CallbackGetBreeds callback){
        IPet service = RetrofitManager.create(IPet.class, true, true);
        Call<List<Breed>> remote = service.getBreedsList(speciesFormatBreed);
        remote.enqueue(new Callback<List<Breed>>() {
            @Override
            public void onResponse(Call<List<Breed>> call, retrofit2.Response<List<Breed>> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
                if(200 == response.code()){
                    callback.getBreeds(response.body());
                } else {
                    PetError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,PetError.class);
                        switch (response.code()){
                            case 400:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.pet_breed_null), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 405:
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
            public void onFailure(Call<List<Breed>> call, Throwable t) {
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

    public interface CallbackGetBreeds{
        void getBreeds(List<Breed> breedList);
    }
}
