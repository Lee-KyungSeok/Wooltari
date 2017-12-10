package kr.co.wooltari.domain.pet;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Kyung on 2017-12-08.
 */

public interface IPet {
    @POST("profile/{user_pk}/pets/")
    Call<Pet> savePetData(
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
    Call<Pet> getPetData(
            @Path("user_pk") int userPK,
            @Path("pet_pk") int petPK
    );

    // 반응형으로 가져오기 위해 observable 로 생성
    @GET("profile/{user_pk}/pets/{pet_pk}/age/")
    Observable<Age> getAge(
            @Path("user_pk") int userPK,
            @Path("pet_pk") int petPK
    );

}
