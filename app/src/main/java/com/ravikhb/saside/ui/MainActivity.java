package com.ravikhb.saside.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ravikhb.saside.R;
import com.ravikhb.saside.adapter.SaSideAdapter;
import com.ravikhb.saside.entity.SaltSideItem;
import com.ravikhb.saside.utils.Utils;
import com.ravikhb.saside.volley.CustomVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<SaltSideItem> mSaltSideList;
    private SaSideAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean available = Utils.isNetworkAvailable(getApplicationContext());
        if(available) {
            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
            mProgressBar.setVisibility(View.VISIBLE);

            createJsonRequest();
            mSaltSideList = new ArrayList<>();
            mAdapter = new SaSideAdapter(getApplicationContext(), mSaltSideList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.linearLayout), R.string.no_internet_connection, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    private void createJsonRequest() {
        String url = Utils.URL;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                 if(mSaltSideList != null)
                     mSaltSideList.clear();
                for(int i=0; i< jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        SaltSideItem object = new SaltSideItem();

                        object.image = jsonObject.getString("image");
                        object.description = jsonObject.getString("description");
                        object.title = jsonObject.getString("title");

                        mSaltSideList.add(i, object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

        CustomVolley.addToRequestQueue(getApplicationContext(), jsonObjectRequest);
    }

}
