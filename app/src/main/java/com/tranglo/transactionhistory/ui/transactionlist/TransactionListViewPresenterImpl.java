package com.tranglo.transactionhistory.ui.transactionlist;

import android.content.SharedPreferences;

import com.tranglo.transactionhistory.BuildConfig;
import com.tranglo.transactionhistory.Const;
import com.tranglo.transactionhistory.network.TransactionWebService;
import com.tranglo.transactionhistory.network.model.Auth;
import com.tranglo.transactionhistory.network.model.TransactionDetail;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bot on 27/01/2018.
 */

public class TransactionListViewPresenterImpl implements  TransactionListViewPresenter {

    private TransactionWebService transactionWebService;
    private SharedPreferences sharedPreferences;
    private TransactionListView transactionListView;

    public TransactionListViewPresenterImpl(TransactionWebService transactionWebService, SharedPreferences sharedPreferences){
        this.transactionWebService = transactionWebService;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void setView(TransactionListView transactionListView) {
        this.transactionListView = transactionListView;
    }

    @Override
    public void getAccessToken() {
        this.transactionWebService.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, BuildConfig.GRANT_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Auth>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Auth auth) {
                        storeAccessToken(auth);
                        getTransactionHistory();
                    }

                    @Override
                    public void onError(Throwable e) {
                        transactionListView.displayErrorMessage(e.getMessage());
                        transactionListView.showLoading(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getTransactionHistory() {
        this.transactionWebService.getTransactionDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TransactionDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<TransactionDetail> transactionDetails) {
                        transactionListView.showTransactionList(transactionDetails);
                        transactionListView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        transactionListView.displayErrorMessage(e.getMessage());
                        transactionListView.showLoading(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void storeAccessToken(Auth auth){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Const.ACCESS_TOKEN, auth.access_token);
        editor.apply();
    }


}
