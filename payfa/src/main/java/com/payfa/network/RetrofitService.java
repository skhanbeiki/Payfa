package com.payfa.network;

import com.payfa.models.Status;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("request/")
    Call<Status> getData(@Field("api") String api, @Field("amount") int amount, @Field("invoice_id") int invoice_id, @Field("callback") String callback);
}
