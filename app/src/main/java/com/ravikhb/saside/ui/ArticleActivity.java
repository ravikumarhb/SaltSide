package com.ravikhb.saside.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.ravikhb.saside.R;

/**
 * Created by ravikhb on 08/01/16.
 */
public class ArticleActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}
