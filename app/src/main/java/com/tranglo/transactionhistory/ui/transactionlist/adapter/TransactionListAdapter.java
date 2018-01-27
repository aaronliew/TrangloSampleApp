package com.tranglo.transactionhistory.ui.transactionlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bot on 27/01/2018.
 */

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder>{
    TransactionListAdapter(){

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public
        ViewHolder(View convertView) {
            super(convertView);
            thumbnail  = convertView.findViewById(R.id.thumbnail);
        }
    }
}
