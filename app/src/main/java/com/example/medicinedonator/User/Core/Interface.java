package com.example.medicinedonator.User.Core;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Interface {

    // as we are making get request
    // so we are displaying GET as annotation.
    // and inside we are passing
    // last parameter for our url.



    // as we are calling data from array
    // so we are calling it with json object
    // and naming that method as getCourse();


    @POST("user/")
    Call<List<RecyclerData> >getFirstName(@Body JsonObject Object);

    @POST("user/add/")
    Call<List<RecyclerData> >addUser(@Body JsonObject Object);


    @GET("medicine/")
    Call<List<Medicine>> getAllMedicine();


    @POST("medicine/search")
    Call<List<Medicine>> getSearcedItem(@Body JsonObject Object);

    @POST("medicine/add")
    Call<DonateResponseModel>addMedicine(@Body JsonObject Object);


    @GET("medicine/get/cat")
    Call<CategoryResponseModel> getItemsonCategory();


    @POST("medicine/update/")
    Call<Medicine> updateMedicine(@Body JsonObject Object);
}
