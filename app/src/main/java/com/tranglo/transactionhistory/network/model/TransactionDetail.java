package com.tranglo.transactionhistory.network.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by bot on 27/01/2018.
 */

public class TransactionDetail {

    public int TransactionId;
    public String CreatedDate;
    public double SenderAmount;
    public String RecipientCountry;
    public String RecipientCurrency;
    public int Status;
    public Recipientinfo RecipientInfo;

    public class Recipientinfo{
        public String Name;
        public String Relationship;
        public String ContactNumber;
    }

    public String getCreatedDate() {

        try {
            SimpleDateFormat parsedFormat = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            parsedFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = parsedFormat.parse(CreatedDate);

            SimpleDateFormat outFormat = new SimpleDateFormat("h:mma dd/MM/yy", Locale.ENGLISH);
            return outFormat.format(date);
        } catch (ParseException e){
            return CreatedDate;
        }

    }

    public String getRecipientName(){
        return RecipientInfo.Name;
    }

    public String getRecipientRelationShip(){
        return RecipientInfo.Relationship;
    }

    public String getRecipientContactNumber(){
        return RecipientInfo.ContactNumber;
    }

    public int getStatus() {
        return Status;
    }

    public String getSenderAmount() {
        return String.valueOf(SenderAmount);
    }

    public String getRecipientCurrency() {
        return RecipientCurrency;
    }

    public String getRecipientCountry() {
        return RecipientCountry;
    }
}
