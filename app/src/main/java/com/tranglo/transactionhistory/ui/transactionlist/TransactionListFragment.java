package com.tranglo.transactionhistory.ui.transactionlist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tranglo.transactionhistory.BaseApplication;
import com.tranglo.transactionhistory.R;
import com.tranglo.transactionhistory.di.Qualifiers;
import com.tranglo.transactionhistory.di.component.DaggerAppComponent;
import com.tranglo.transactionhistory.di.component.DaggerTransactionListComponent;
import com.tranglo.transactionhistory.di.component.TransactionListComponent;
import com.tranglo.transactionhistory.di.module.TransactionListModule;
import com.tranglo.transactionhistory.network.model.TransactionDetail;
import com.tranglo.transactionhistory.ui.transactionlist.adapter.TransactionListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bot on 27/01/2018.
 */

public class TransactionListFragment extends Fragment implements TransactionListView {


    @Inject
    TransactionListViewPresenter transactionListViewPresenter;

    @BindView(R.id.transaction_list)
    RecyclerView transactionList;

    TransactionListComponent activityComponent;


    public TransactionListComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerTransactionListComponent.builder()
                    .transactionListModule(new TransactionListModule())
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


        return view;
    }

    @Override
    public void showLoading(boolean isLoading) {

    }

    @Override
    public void displayErrorMessage(String text) {

    }

    @Override
    public void showTransactionList(List<TransactionDetail> transactionDetails) {
        transactionList.setAdapter(new TransactionListAdapter(getActivity(), transactionDetails));
    }
}
