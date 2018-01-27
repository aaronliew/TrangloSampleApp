package com.tranglo.transactionhistory;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tranglo.transactionhistory.di.Qualifiers;
import com.tranglo.transactionhistory.di.component.AppComponent;
import com.tranglo.transactionhistory.di.component.DaggerAppComponent;
import com.tranglo.transactionhistory.di.module.AppModule;
import com.tranglo.transactionhistory.di.module.NetworkModule;

import javax.inject.Inject;

/**
 * Created by bot on 27/01/2018.
 */

public class BaseApplication extends Application {

    @Inject
    @Qualifiers.SharedPreferences
    SharedPreferences mSharedPreferences;

    protected AppComponent appComponent;

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .appModule(new AppModule(this))
                .build();

        appComponent.inject(this);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("name", "Elena");
        editor.apply();
    }

    public AppComponent getComponent(){
        return appComponent;
    }
}

