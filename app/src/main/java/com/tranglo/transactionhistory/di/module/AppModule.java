package com.tranglo.transactionhistory.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tranglo.transactionhistory.Const;
import com.tranglo.transactionhistory.di.Qualifiers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bot on 17/01/2018.
 */

@Module
public class AppModule {
    private final Application mApplication;

    public AppModule(Application app) {
        mApplication = app;
    }

    @Singleton
    @Provides
    @Qualifiers.SharedPreferences
    SharedPreferences provideSharePreferences(){
        return mApplication.getSharedPreferences(Const.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

}
