package com.tranglo.transactionhistory.di;

/**
 * Created by bot on 18/01/2018.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {}