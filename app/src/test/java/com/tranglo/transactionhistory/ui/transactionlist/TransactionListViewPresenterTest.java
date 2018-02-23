package com.tranglo.transactionhistory.ui.transactionlist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;

import com.tranglo.transactionhistory.BuildConfig;
import com.tranglo.transactionhistory.network.TransactionWebService;
import com.tranglo.transactionhistory.network.model.Auth;
import com.tranglo.transactionhistory.network.model.TransactionDetail;
import com.tranglo.transactionhistory.ui.transactionlist.TransactionListView;
import com.tranglo.transactionhistory.ui.transactionlist.TransactionListViewPresenter;
import com.tranglo.transactionhistory.ui.transactionlist.TransactionListViewPresenterImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Created by aaronliew on 15/02/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class TransactionListViewPresenterTest {

    private TransactionListViewPresenterImpl mTransactionListViewPresenter;

    @Mock
    TransactionWebService transactionWebService;

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    TransactionListView transactionListView;

    @Mock
    Activity mActivity;

    @Mock
    List<TransactionDetail> transactionDetails;

    @Mock
    Auth auth;

    @BeforeClass
    public static void setUpRxSchedulers() {
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxJavaPlugins.setComputationSchedulerHandler (new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxJavaPlugins.setNewThreadSchedulerHandler (new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @Before
    public void setUp() throws Exception{
        mTransactionListViewPresenter = spy(new TransactionListViewPresenterImpl(transactionWebService, sharedPreferences, mActivity));
        mTransactionListViewPresenter.setView(transactionListView);
    }

    @Test
    public void testSuccessfulLogin() throws  Exception{

        auth.access_token = "access";
        doReturn(Observable.just(auth)).when(transactionWebService).getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, BuildConfig.GRANT_TYPE);
        doNothing().when(mTransactionListViewPresenter).storeAccessToken(auth);
        doNothing().when(mTransactionListViewPresenter).getTransactionHistory();
        mTransactionListViewPresenter.getAccessToken();

        verify(mTransactionListViewPresenter, times(1)).storeAccessToken(auth);
        verify(mTransactionListViewPresenter, times(1)).getTransactionHistory();


    }

    @Test
    public void testFailureLogin() throws Exception {
        Auth auth = new Auth();
        auth.access_token = "";
        doReturn(Observable.error(new Exception("Invalid Token"))).when(transactionWebService).getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, BuildConfig.GRANT_TYPE);
        mTransactionListViewPresenter.getAccessToken();

        verify(transactionListView, times(1)).displayErrorMessage("Invalid Token");
        verify(transactionListView, times(1)).showTransactionList(new ArrayList<TransactionDetail>());
        verify(transactionListView, times(1)).showLoading(false);
    }

//    @After
//    public void tearDown() throws Exception{
//
//    }
}
