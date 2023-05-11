package com.example.medicinedonator.User.Core;

import retrofit2.Call;
import retrofit2.http.GET;
public interface Interface {

    // as we are making get request
    // so we are displaying GET as annotation.
    // and inside we are passing
    // last parameter for our url.
    @GET("users")

    // as we are calling data from array
    // so we are calling it with json object
    // and naming that method as getCourse();
    Call<RecyclerData> getFirstName();

}
