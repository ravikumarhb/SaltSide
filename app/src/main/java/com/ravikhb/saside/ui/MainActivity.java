package com.ravikhb.saside.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ravikhb.saside.R;
import com.ravikhb.saside.entity.SaltSideItem;
import com.ravikhb.saside.utils.Utils;

public class MainActivity extends AppCompatActivity implements HeadlinesFragment.OnItemSelectedActivityListener {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null ) {
            String fragmentType = savedInstanceState.getString(Utils.FRAGMENT);
            if (findViewById(R.id.fragment_container) != null) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                if(fragmentType.equalsIgnoreCase(Utils.HEADLINES_FRAGMENT)) {
                    HeadlinesFragment fragment = new HeadlinesFragment();
                    transaction.replace(R.id.fragment_container, fragment).commit();
                } else {
                    DetailsFragment fragment = new DetailsFragment();
                    transaction.replace(R.id.fragment_container, fragment).commit();
                }
            }
        } else {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            HeadlinesFragment fragment = new HeadlinesFragment();
            transaction.replace(R.id.fragment_container, fragment).commit();
        }

        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        boolean available = Utils.isNetworkAvailable(getApplicationContext());
        if(available) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayout), R.string.no_internet_connection, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    @Override
    public void onItemSelectedActivity(SaltSideItem item) {

        Bundle bundle = new Bundle();
        bundle.putString(Utils.TITLE, item.title);
        bundle.putString(Utils.DESCRIPTION, item.description);
        bundle.putString(Utils.IMAGE, item.image);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailsFragment).addToBackStack("").commit();

    }

    public void updateProgressBar(int visibilty) {
        mProgressBar.setVisibility(visibilty);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment instanceof HeadlinesFragment)
            outState.putString(Utils.FRAGMENT, Utils.HEADLINES_FRAGMENT);
        else
            outState.putString(Utils.FRAGMENT, Utils.DETAILS_FRAGMENT);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
