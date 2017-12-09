package kr.co.wooltari.domain.pet;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kyung on 2017-12-08.
 */

public interface IPet {
    @POST("profile/{user_pk}/pets/")
    Call<ResponseBody> savePetData( @Path("user_pk") String userPK );

    @GET("profile/{user_pk}/pets/{pet_pk}/")
    Call<Pet> getPetData(
            @Path("user_pk") String userPK,
            @Path("pet_pk") String petPK
    );

//    @GET("profile/{user_pk}/pets/")
//    Call

}
