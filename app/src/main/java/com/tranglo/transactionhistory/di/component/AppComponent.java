package com.tranglo.transactionhistory.di.component;

import android.content.SharedPreferences;

import com.tranglo.transactionhistory.BaseApplication;
import com.tranglo.transactionhistory.di.Qualifiers;
import com.tranglo.transactionhistory.di.module.AppModule;
import com.tranglo.transactionhistory.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by bot on 17/01/2018.
 */
@Singleton
@Component(modules = {
        AppModule.class, NetworkModule.class})
public interface AppComponent {
    @Qualifiers.Retrofit
    Retrofit retrofit();

    @Qualifiers.SharedPreferences
    SharedPreferences sharedPreferences();

    void inject(BaseApplication application);
}
