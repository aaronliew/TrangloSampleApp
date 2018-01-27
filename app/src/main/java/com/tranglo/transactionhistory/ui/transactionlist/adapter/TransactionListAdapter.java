package com.tranglo.transactionhistory.ui.transactionlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tranglo.transactionhistory.Const;
import com.tranglo.transactionhistory.R;
import com.tranglo.transactionhistory.network.model.TransactionDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bot on 27/01/2018.
 */

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder>{

    private List<TransactionDetail> transactionDetails;
    private Context context;

    public TransactionListAdapter(Context context, List<TransactionDetail> transactionDetails){
        this.context = context;
        this.transactionDetails = transactionDetails;
    }

    @Override
    public int getItemCount() {
        return transactionDetails.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_transaction_list, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TransactionDetail transactionDetail = transactionDetails.get(position);

        holder.recipientName.setText(transactionDetail.getRecipientName());
        holder.recipientContact.setText(transactionDetail.getRecipientContactNumber());
        holder.transactionDate.setText(transactionDetail.getCreatedDate());
        holder.recipientRelationship.setText(transactionDetail.getRecipientRelationShip());

        holder.senderAmount.setText(transactionDetail.getSenderAmount());
        holder.recipientCurrency.setText(transactionDetail.getRecipientCurrency());
        holder.recipientCountry.setText(transactionDetail.getRecipientCountry());

        setStatusIcon(holder.statusIcon, transactionDetail.getStatus());
    }


    private void setStatusIcon(ImageView statusIcon, int statusCode){
        switch (statusCode){
            case Const.PENDING_STATUS_CODE:
                statusIcon.setBackground(context.getDrawable(R.drawable.ic_success));
            case Const.FAIL_STATUS_CODE:
                statusIcon.setBackground(context.getDrawable(R.drawable.ic_failed));
            default:
                statusIcon.setBackground(context.getDrawable(R.drawable.ic_pending));
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipient_name)
        public TextView recipientName;

        @BindView(R.id.contact_number)
        public TextView recipientContact;

        @BindView(R.id.transaction_date)
        public TextView transactionDate;

        @BindView(R.id.recipient_relationship)
        public TextView recipientRelationship;

        @BindView(R.id.sender_amount)
        public TextView senderAmount;

        @BindView(R.id.recipient_currency)
        public TextView recipientCurrency;

        @BindView(R.id.recipient_country)
        public TextView recipientCountry;

        @BindView(R.id.status_icon)
        public ImageView statusIcon;

        ViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }
}
