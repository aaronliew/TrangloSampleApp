package com.tranglo.transactionhistory.ui.transactionlist;

import com.tranglo.transactionhistory.network.model.TransactionDetail;

import java.util.List;

/**
 * Created by bot on 27/01/2018.
 */

public interface TransactionListView {
    void displayErrorMessage(String text);
    void showLoading(boolean isLoading);
    void showTransactionList(List<TransactionDetail> transactionDetails);
    void updateTransactionHistory(List<TransactionDetail> transactionDetails);
}
