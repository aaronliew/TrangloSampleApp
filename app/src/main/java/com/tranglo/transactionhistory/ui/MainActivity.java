package com.tranglo.transactionhistory.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.airbnb.lottie.LottieAnimationView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tranglo.transactionhistory.R;
import com.tranglo.transactionhistory.ui.transactionlist.TransactionListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.sort_by_name)
    FloatingActionButton sortByNamefab;

    @BindView(R.id.sort_by_time)
    FloatingActionButton sortByTimefab;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.loading_animation)
    LottieAnimationView loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initFab();
        initDrawer();

        //renderDefaultFragment
        renderFragment(R.id.nav_transaction_history);
    }

    private void initFab(){
        sortByNamefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionListFragment transactionListFragment = (TransactionListFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
                if (transactionListFragment.isAdded()){
                    transactionListFragment.sortByName();
                }
            }
        });

        sortByTimefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionListFragment transactionListFragment = (TransactionListFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
                if (transactionListFragment.isAdded()){
                    Log.d("time", "sort by time");
                    transactionListFragment.sortByTime();
                }
            }
        });
    }

    private void initDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        renderFragment(id);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void renderFragment(int id){
        Fragment fragment = null;

        if (id == R.id.nav_transaction_history) {
            // Handle transaction history
            fragment =  TransactionListFragment.newInstance();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    public void showLoading(boolean playAnimation){
        if (playAnimation && !loadingAnimation.isAnimating()){
            loadingAnimation.setVisibility(View.VISIBLE);
            loadingAnimation.playAnimation();
        } else if (!playAnimation && loadingAnimation.isAnimating()){
            loadingAnimation.setVisibility(View.GONE);
            loadingAnimation.cancelAnimation();
        }
    }
}
