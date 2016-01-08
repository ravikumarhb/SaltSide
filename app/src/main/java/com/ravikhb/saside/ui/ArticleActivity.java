package com.ravikhb.saside.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ravikhb.saside.R;

import org.w3c.dom.Text;

/**
 * Created by ravikhb on 08/01/16.
 */
public class ArticleActivity extends AppCompatActivity {

    private String mImageStr;
    private String mTitleStr;
    private String mDescriptionStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null ){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle != null ){
             mImageStr = bundle.getString("image");
             mDescriptionStr = bundle.getString("description");
             mTitleStr = bundle.getString("title");
        }

        TextView title = (TextView)findViewById(R.id.title);
        TextView description = (TextView)findViewById(R.id.description);
        ImageView image = (ImageView)findViewById(R.id.image_view);


        title.setText(mTitleStr);
        description.setText(mDescriptionStr);
        Glide.with(getApplicationContext()).load(mImageStr)
                .into(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}
