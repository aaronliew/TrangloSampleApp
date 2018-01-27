package com.tranglo.transactionhistory.network;

import android.content.SharedPreferences;
import android.util.Log;

import com.tranglo.transactionhistory.Const;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bot on 19/01/2018.
 */
public class RetrofitInterceptor implements Interceptor{

    private SharedPreferences sharedPreferences;
    public RetrofitInterceptor(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = sharedPreferences.getString(Const.ACCESS_TOKEN, "");
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", "Bearer "+ token).build();
        return chain.proceed(authenticatedRequest);
    }
}
