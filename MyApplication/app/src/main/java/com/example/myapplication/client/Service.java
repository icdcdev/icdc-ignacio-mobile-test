package com.example.myapplication.client;

import com.example.myapplication.model.Orders;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("orders")
    Call<Orders> listOrders();
}
