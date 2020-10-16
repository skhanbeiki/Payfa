package com.payfa.network;

import android.content.Context;

import com.payfa.models.ErrorModel;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayfaService {

    private RetrofitService mService;
    private Retrofit retrofit;

    public Retrofit getRetrofitClient() {
        return retrofit;
    }

    public RetrofitService getApiInterface() {
        return mService;
    }

    private OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .proxy(Proxy.NO_PROXY)
                .build();
    }

    public PayfaService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ClientConfigs.baseUrl())
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(RetrofitService.class);
    }


    public ErrorModel parseErrorNoCaching(retrofit2.Response<?> response) {
        Converter<ResponseBody, ErrorModel> converter = getRetrofitClient().responseBodyConverter(ErrorModel.class, new Annotation[0]);
        ErrorModel error = new ErrorModel();
        try {
            if (response.errorBody() != null) {
                error = converter.convert(response.errorBody());
            }
        } catch (IOException e) {
            return new ErrorModel();
        }
        return error;
    }
}
