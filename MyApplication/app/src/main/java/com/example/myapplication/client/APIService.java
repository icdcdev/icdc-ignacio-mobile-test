package com.example.myapplication.client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    private static Service service;

    public static Service newServiceInstance() {
        if(service != null) {
            return service;
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://5a5q7x88fb.execute-api.us-west-2.amazonaws.com/")
                .client(client)
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
        return service;
    }
}
