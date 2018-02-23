package com.tranglo.transactionhistory.di.module;

import android.app.Activity;
import android.content.SharedPreferences;

import com.tranglo.transactionhistory.di.Qualifiers;
import com.tranglo.transactionhistory.network.TransactionWebService;
import com.tranglo.transactionhistory.ui.transactionlist.TransactionListViewPresenter;
import com.tranglo.transactionhistory.ui.transactionlist.TransactionListViewPresenterImpl;

import dagger.Module;
import dagger.Provides;
/**
 * Created by bot on 19/01/2018.
 */
@Module
public class TransactionListModule {


    private Activity mActivity;

    public TransactionListModule(Activity mActivity){
        this.mActivity = mActivity;
    }

    @Provides
    TransactionListViewPresenter provideTransactionListViewPresenter(@Qualifiers.TransactionWebService TransactionWebService transactionWebService,
                                                                     @Qualifiers.SharedPreferences SharedPreferences sharedPreferences) {
        return new TransactionListViewPresenterImpl(transactionWebService, sharedPreferences, mActivity);
    }


}
