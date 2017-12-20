package kr.co.wooltari.domain.retrofit;


import java.util.List;

import io.reactivex.Observable;
import kr.co.wooltari.domain.pet.ActivePet;
import kr.co.wooltari.domain.pet.Age;
import kr.co.wooltari.domain.pet.Breed;
import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetList;
import kr.co.wooltari.domain.pet.PetOne;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kyung on 2017-12-08.
 * - 참고 : The @Body annotation defines a single request body
 */

public interface IPet {
    // 이미지 전송을 위해 multipart 지정
    @POST("profile/{user_pk}/pets/")
    Call<PetOne> savePetData(
            @Path("user_pk") int userPK,
            @Body Pet petData
    );

    @Multipart
    @POST("profile/{user_pk}/pets/")
    Call<PetOne> savePetData(
            @Path("user_pk") int userPK,
            @Part("name") RequestBody name,
            @Part("birth_date") RequestBody birth_date,
            @Part("gender") RequestBody gender,
            @Part("body_color") RequestBody body_color,
            @Part("species") RequestBody species,
            @Part("breeds") RequestBody breeds,
            @Part("identified_number") RequestBody identified_number,
            @Part("is_neutering") RequestBody is_neutering,
            @Part MultipartBody.Part file
    );

    @GET("profile/{user_pk}/pets/")
    Call<PetList> getPetList(
            @Path("user_pk") int userPK
    );

    @GET("profile/{user_pk}/pets/")
    Call<PetList> getPetListNext(
            @Path("user_pk") int userPK,
            @Query("page") int pageNumber
    );

    @GET("profile/{user_pk}/pets/{pet_pk}/")
    Call<PetOne> getPetData(
            @Path("user_pk") int userPK,
            @Path("pet_pk") int petPK
    );

    @GET("profile/{user_pk}/pets/{pet_pk}/age/")
    Call<Age> getAge(
            @Path("user_pk") int userPK,
            @Path("pet_pk") int petPK
    );

    @PATCH("profile/{user_pk}/pets/{pet_pk}/")
    Call<PetOne> updatePetData(
            @Path("user_pk") int userPK,
            @Path("pet_pk") int petPK,
            @Body Pet petData
    );

    @PATCH("profile/{user_pk}/pets/{pet_pk}/")
    Call<PetOne> updatePetActive(
            @Path("user_pk") int userPK,
            @Path("pet_pk") int petPK,
            @Body ActivePet active
    );

    @DELETE("profile/{user_pk}/pets/{pet_pk}/")
    Call<Pet> deletePetData(
            @Path("user_pk") int userPK,
            @Path("pet_pk") int petPK
    );

    @POST("profile/pet-breed-list/")
    Call<List<Breed>> getBreedsList(
            @Body Breed species
    );
}
