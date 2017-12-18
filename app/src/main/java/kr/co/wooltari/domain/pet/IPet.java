package kr.co.wooltari.domain.pet;


import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kyung on 2017-12-08.
 */

public interface IPet {
    @POST("profile/{user_pk}/pets/")
    Call<PetOne> savePetData(
//            @Header("Authorization") String token,
            @Path("user_pk") int userPK,
            @Body Pet petData
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
