package com.tranglo.transactionhistory.ui.transactionlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tranglo.transactionhistory.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bot on 27/01/2018.
 */

public class TransactionListFragment extends Fragment {

    @BindView(R.id.transaction_list)
    RecyclerView transactionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
