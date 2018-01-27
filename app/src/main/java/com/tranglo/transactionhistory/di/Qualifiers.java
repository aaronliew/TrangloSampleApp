package com.tranglo.transactionhistory.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by bot on 19/01/2018.
 */

public @interface Qualifiers {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface SharedPreferences {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface Retrofit {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface TransactionWebService {}
}
