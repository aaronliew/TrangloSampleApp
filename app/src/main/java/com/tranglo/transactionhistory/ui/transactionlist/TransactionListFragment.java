package com.tranglo.transactionhistory.ui.transactionlist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tranglo.transactionhistory.BaseApplication;
import com.tranglo.transactionhistory.R;
import com.tranglo.transactionhistory.di.Qualifiers;
import com.tranglo.transactionhistory.di.component.DaggerAppComponent;
import com.tranglo.transactionhistory.di.component.DaggerTransactionListComponent;
import com.tranglo.transactionhistory.di.component.TransactionListComponent;
import com.tranglo.transactionhistory.di.module.TransactionListModule;
import com.tranglo.transactionhistory.network.model.TransactionDetail;
import com.tranglo.transactionhistory.ui.MainActivity;
import com.tranglo.transactionhistory.ui.transactionlist.adapter.TransactionListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

/**
 * Created by bot on 27/01/2018.
 */

public class TransactionListFragment extends Fragment implements TransactionListView {


    @Inject
    TransactionListViewPresenter transactionListViewPresenter;

    @BindView(R.id.empty_view)
    TextView emptyView;

    @BindView(R.id.swipe_to_fresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.transaction_list)
    RecyclerView transactionList;

    TransactionListComponent activityComponent;

    TransactionListAdapter transactionListAdapter;

    public static TransactionListFragment newInstance() {
        return new TransactionListFragment();
    }



    public TransactionListComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerTransactionListComponent.builder()
                    .transactionListModule(new TransactionListModule(getActivity()))
                    .appComponent(BaseApplication.get(getActivity()).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        ButterKnife.bind(this, view);
        showLoading(true);
        transactionListViewPresenter.setView(this);
        transactionListViewPresenter.getAccessToken();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                transactionListViewPresenter.getAccessToken();
            }
        });
        return view;
    }

    @Override
    public void showLoading(boolean isLoading) {
        ((MainActivity) getActivity()).showLoading(isLoading);
    }

    @Override
    public void displayErrorMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTransactionList(List<TransactionDetail> transactionDetails) {
        swipeRefreshLayout.setRefreshing(false);
        if (transactionDetails.size() == 0){
            emptyView.setVisibility(View.VISIBLE);
        } else{
            emptyView.setVisibility(View.GONE);
        }

        transactionList.setItemAnimator(new OvershootInLeftAnimator());
        transactionListAdapter = new TransactionListAdapter(getActivity(), transactionDetails);
        transactionList.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionList.setAdapter(new AlphaInAnimationAdapter(transactionListAdapter));

    }

    @Override
    public void updateTransactionHistory(List<TransactionDetail> transactionDetails) {
        transactionList.swapAdapter(new SlideInLeftAnimationAdapter(new TransactionListAdapter(getActivity(), transactionDetails)), false);
        transactionListAdapter.updateList(transactionDetails);
    }

    public void sortByTime(){
        transactionListViewPresenter.sortByTime();
    }

    public void sortByName(){
        transactionListViewPresenter.sortByName();
    }
}
