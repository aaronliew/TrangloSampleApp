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
    public String Status;
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

            SimpleDateFormat outFormat = new SimpleDateFormat("h:mm a dd MMM yyyy", Locale.ENGLISH);
            return outFormat.format(date);
        } catch (ParseException e){
            return CreatedDate;
        }

    }
}
