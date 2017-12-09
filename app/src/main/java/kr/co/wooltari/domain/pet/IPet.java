package kr.co.wooltari.domain.pet;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kyung on 2017-12-08.
 */

public interface IPet {
    @POST("profile/{user_pk}/pets/")
    Call<Response<Pet>> savePetData(
            @Path("user_pk") String userPK,
            @Body Pet petData
    );

    @GET("profile/{user_pk}/pets/")
    Call<Response<PetList>> getPetList(@Path("user_pk") String userPK );

    @GET("profile/{user_pk}/pets/{pet_pk}/")
    Call<Response<Pet>> getPetData(
            @Path("user_pk") String userPK,
            @Path("pet_pk") String petPK
    );

    // 반응형으로 가져오기 위해 observable 로 생성
    @GET("profile/{user_pk}/pets/{pet_pk}/age/")
    Observable<Response<Age>> getAge(
            @Path("user_pk") String userPK,
            @Path("pet_pk") String petPK
    );

}
