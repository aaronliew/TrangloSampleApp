package com.tranglo.transactionhistory.di.component;

import com.tranglo.transactionhistory.di.PerFragment;
import com.tranglo.transactionhistory.di.module.TransactionListModule;
import com.tranglo.transactionhistory.ui.transactionlist.TransactionListFragment;

import dagger.Component;

/**
 * Created by bot on 17/01/2018.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = {TransactionListModule.class})
public interface TransactionListComponent {
    void inject(TransactionListFragment target);
}
