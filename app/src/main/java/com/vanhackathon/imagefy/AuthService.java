package com.vanhackathon.imagefy;

import com.vanhackathon.imagefy.service.data.auth.FacebookLoginData;
import com.vanhackathon.imagefy.service.data.auth.LoginResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by rodrigo.alencar on 4/22/16.
 */
public class AuthService {
    private static final String API_URL = "http://imagefy.herokuapp.com";

    private static AuthService instance;
    private Retrofit retrofit;
    public ImagefyAuthApi imagefyAuthApi;

    private AuthService() {
    }

    public static synchronized AuthService getInstance() {
        if(instance == null) {
            instance = new AuthService();
            instance.init();
        }
        return instance;
    }

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        imagefyAuthApi = retrofit.create(ImagefyAuthApi.class);
    }

    public interface ImagefyAuthApi {

        @Headers("content-type: \"application/json\"")
        @POST("/api/v1/auth/facebook/")
        Call<LoginResponse> facebook(@Body FacebookLoginData data);

//        @GET("movie/{id}/reviews")
//        Call<Reviews> reviews(@Path("id") String id, @Query(API_KEY) String apiKey);
    }
}
