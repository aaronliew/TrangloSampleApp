package com.tranglo.transactionhistory.ui.transactionlist;

import com.tranglo.transactionhistory.network.model.TransactionDetail;

import java.util.List;

/**
 * Created by bot on 27/01/2018.
 */

public interface TransactionListViewPresenter {
    void setView(TransactionListView transactionListView);
    void getAccessToken();
    void sortByName();
    void sortByTime();
}
