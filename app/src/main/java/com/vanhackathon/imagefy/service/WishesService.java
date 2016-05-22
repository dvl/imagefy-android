package com.vanhackathon.imagefy.service;

import com.vanhackathon.imagefy.service.data.auth.LoginResponse;
import com.vanhackathon.imagefy.service.data.auth.Wish;
import com.vanhackathon.imagefy.service.data.auth.WishesList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by rodrigo.alencar on 4/22/16.
 */
public class WishesService {
    private static final String API_URL = "http://imagefy.herokuapp.com";

    private static WishesService instance;
    private Retrofit retrofit;
    public ImagefyWishesApi imagefyWishesApi;

    private WishesService() {
    }

    public static synchronized WishesService getInstance(String token) {
        if(instance == null) {
            instance = new WishesService();
            instance.init(token);
        }
        return instance;
    }

    private void init(final String token) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Authorization", "Token " + token);

                        return chain.proceed(ongoing.build());
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        imagefyWishesApi = retrofit.create(ImagefyWishesApi.class);
    }

    public interface ImagefyWishesApi {
        @Headers("content-type: \"application/json\"")
        @GET("/api/v1/wishes/")
        Call<List<Wish>> get();

        @Multipart
        @POST("/api/v1/wishes/")
        Call<Wish> uploadFile(@Part MultipartBody.Part image, @Part("buget") RequestBody budget, @Part("brief") RequestBody brief);
    }
}
