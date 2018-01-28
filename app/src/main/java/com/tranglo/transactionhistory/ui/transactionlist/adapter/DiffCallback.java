package com.tranglo.transactionhistory.ui.transactionlist.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.tranglo.transactionhistory.network.model.TransactionDetail;

import java.util.List;

/**
 * Created by bot on 28/01/2018.
 */

public class DiffCallback extends DiffUtil.Callback{

    List<TransactionDetail> oldTransactionDetails;
    List<TransactionDetail> newTransactionDetails;

    public DiffCallback(List<TransactionDetail> newTransactionDetails, List<TransactionDetail> oldTransactionDetails) {
        this.newTransactionDetails = newTransactionDetails;
        this.oldTransactionDetails = oldTransactionDetails;
    }

    @Override
    public int getOldListSize() {
        return oldTransactionDetails.size();
    }

    @Override
    public int getNewListSize() {
        return newTransactionDetails.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTransactionDetails.get(oldItemPosition).getTransactionId() == newTransactionDetails.get(newItemPosition).getTransactionId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTransactionDetails.get(oldItemPosition).equals(newTransactionDetails.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
