package com.tranglo.transactionhistory.network;

import com.tranglo.transactionhistory.network.model.Auth;
import com.tranglo.transactionhistory.network.model.TransactionDetail;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by bot on 27/01/2018.
 */

public interface TransactionWebService {

    @FormUrlEncoded
    @POST("/token_service")
    Observable<Auth> getAccessToken(@Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("grant_type") String grantType);

    @GET("/api/TransactionDetails")
    Observable<List<TransactionDetail>> getTransactionDetails();

}
