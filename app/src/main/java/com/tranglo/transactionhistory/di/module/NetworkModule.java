package com.tranglo.transactionhistory.di.module;

import android.content.SharedPreferences;

import com.tranglo.transactionhistory.BuildConfig;
import com.tranglo.transactionhistory.di.Qualifiers;
import com.tranglo.transactionhistory.network.RetrofitInterceptor;
import com.tranglo.transactionhistory.network.TransactionWebService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bot on 19/01/2018.
 */
@Module
public class NetworkModule {
    public static final int CONNECT_TIMEOUT_IN_MS = 30000;
    public NetworkModule(){}

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@Qualifiers.SharedPreferences SharedPreferences sharedPreferences) {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(new RetrofitInterceptor(sharedPreferences))
                .build();
    }

    @Singleton
    @Provides
    @Qualifiers.Retrofit
    Retrofit retrofit(OkHttpClient okHttpClient){
        return new Retrofit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    @Qualifiers.TransactionWebService
    TransactionWebService provideRedditWebService(@Qualifiers.Retrofit Retrofit retrofit) {
        return retrofit.create(TransactionWebService.class);
    }

}
