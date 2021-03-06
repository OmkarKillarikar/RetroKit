package com.example.okmac.retrokit.retrofit;

import android.content.Context;

import com.example.okmac.retrokit.listeners.RetrofitListener;
import com.example.okmac.retrokit.utils.Constants;
import com.example.okmac.retrokit.utils.HttpUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiServiceProvider extends RetrofitBase {
    private static ApiServiceProvider apiServiceProvider;
    private ApiServices apiServices;

    private ApiServiceProvider(Context context) {
        super(context, true);
        apiServices = retrofit.create(ApiServices.class);
    }

    public static ApiServiceProvider getInstance(Context context) {
        if (apiServiceProvider == null) {
            apiServiceProvider = new ApiServiceProvider(context);
        }
        return apiServiceProvider;
    }

    public void getSomething(String parameter, final RetrofitListener retrofitListener) {
        Call<ResponseBody> call = apiServices.getSomething(parameter);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.GET_SOMETHING);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getSomethingElse(final RetrofitListener retrofitListener) {
        Call<ResponseBody> call = apiServices.getSomethingElse();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.GET_SOMETHING_ELSE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //pass correct flag to differentiate between multiple api calls in same activity/frag
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING_ELSE);
            }
        });
    }

}
