package com.tranglo.transactionhistory.ui.transactionlist.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
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
        holder.transactionDate.setText(transactionDetail.getCreatedDate());
        holder.recipientContact.setText(formatString(R.string.recipient_contact, transactionDetail.getRecipientContactNumber()));
        holder.recipientRelationship.setText(formatString(R.string.recipient_relationship, transactionDetail.getRecipientRelationShip()));

        holder.senderAmount.setText(formatString(R.string.amount, transactionDetail.getSenderAmount()));
        holder.recipientCurrency.setText(formatString(R.string.recipient_currency, transactionDetail.getRecipientCurrency()));
        holder.recipientCountry.setText(formatString(R.string.recipient_country, transactionDetail.getRecipientCountry()));

        setStatusIcon(holder, transactionDetail.getStatus());
    }

    private String formatString(int id, String text){
        return context.getString(id, text);
    }


    private void setStatusIcon(ViewHolder holder, int statusCode){
        ImageView statusIcon = holder.statusIcon;
        CardView transactionItemContainer = holder.transactionItemContainer;
        switch (statusCode){
            case Const.PENDING_STATUS_CODE:
                statusIcon.setBackground(context.getDrawable(R.drawable.ic_success));
                transactionItemContainer.setCardBackgroundColor(context.getResources().getColor(R.color.status_success));
                break;
            case Const.FAIL_STATUS_CODE:
                statusIcon.setBackground(context.getDrawable(R.drawable.ic_failed));
                transactionItemContainer.setCardBackgroundColor(context.getResources().getColor(R.color.status_failed));
                break;
            default:
                statusIcon.setBackground(context.getDrawable(R.drawable.ic_pending));
                transactionItemContainer.setCardBackgroundColor(context.getResources().getColor(R.color.status_pending));
        }

    }

    public void updateList(List<TransactionDetail> newTransactionDetails) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(newTransactionDetails, this.transactionDetails));
        diffResult.dispatchUpdatesTo(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.transaction_item_container)
        public CardView transactionItemContainer;

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
