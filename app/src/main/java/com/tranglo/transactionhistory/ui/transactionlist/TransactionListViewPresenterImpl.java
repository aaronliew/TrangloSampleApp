package com.tranglo.transactionhistory.ui.transactionlist;

import android.content.SharedPreferences;
import android.util.Log;

import com.tranglo.transactionhistory.BuildConfig;
import com.tranglo.transactionhistory.Const;
import com.tranglo.transactionhistory.network.TransactionWebService;
import com.tranglo.transactionhistory.network.model.Auth;
import com.tranglo.transactionhistory.network.model.TransactionDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
    private List<TransactionDetail> transactionDetails;


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
                        transactionListView.showTransactionList(new ArrayList<TransactionDetail>());
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
                        TransactionListViewPresenterImpl.this.transactionDetails = transactionDetails;
                        transactionListView.showTransactionList(transactionDetails);
                        sortByTime();
                        transactionListView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        transactionListView.displayErrorMessage(e.getMessage());
                        transactionListView.showTransactionList(new ArrayList<TransactionDetail>());
                        transactionListView.showLoading(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void sortByName(){
        Collections.sort(transactionDetails, new Comparator<TransactionDetail>() {
            @Override
            public int compare(TransactionDetail t1, TransactionDetail t2) {
                return t1.getRecipientName().compareToIgnoreCase(t2.getRecipientName());
            }
        });

        transactionListView.updateTransactionHistory(transactionDetails);
    }

    @Override
    public void sortByTime(){
        final SimpleDateFormat parsedFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        parsedFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Collections.sort(transactionDetails, new Comparator<TransactionDetail>() {
            @Override
            public int compare(TransactionDetail t1, TransactionDetail t2) {
                try {
                    Date datet1 = parsedFormat.parse(t1.CreatedDate);
                    Date datet2 = parsedFormat.parse(t2.CreatedDate);
                    Log.d("time", "functioning");
                    return datet2.after(datet1)? 1 : -1;
                } catch(ParseException e){
                    Log.d("time", "error");
                    return 0;
                }


            }
        });
        transactionListView.updateTransactionHistory(transactionDetails);

    }

    private void storeAccessToken(Auth auth){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Const.ACCESS_TOKEN, auth.access_token);
        editor.apply();
    }


}
