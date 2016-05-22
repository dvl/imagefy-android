package com.vanhackathon.imagefy.service;

import com.vanhackathon.imagefy.service.data.auth.LoginResponse;
import com.vanhackathon.imagefy.service.data.auth.Wish;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
                        ongoing.addHeader("Authorization", token);

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
        @POST("/api/v1/wishes/")
        Call<Wish> add(@Body Wish data);
    }
}
