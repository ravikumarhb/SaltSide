package com.ravikhb.saside.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * Created by ravikhb on 09/01/16.
 */
public class HeadlinesFragment extends Fragment implements SaSideAdapter.OnItemSelectedAdapterListener{

    private List<SaltSideItem> mSaltSideList;
    private SaSideAdapter mAdapter;

    private OnItemSelectedActivityListener mCallbackActivity;

    @Override
    public void onItemSelected(SaltSideItem object) {
        mCallbackActivity.onItemSelectedActivity(object);
    }


    public interface OnItemSelectedActivityListener {
        void onItemSelectedActivity(SaltSideItem object);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_headlines, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        createJsonRequest();
        mSaltSideList = new ArrayList<>();
        mAdapter = new SaSideAdapter(getActivity().getApplicationContext(), mSaltSideList, this);
        recyclerView.setAdapter(mAdapter);
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

                        object.image = jsonObject.getString(Utils.IMAGE);
                        object.description = jsonObject.getString(Utils.DESCRIPTION);
                        object.title = jsonObject.getString(Utils.TITLE);

                        mSaltSideList.add(i, object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter.notifyDataSetChanged();
                ((MainActivity)mCallbackActivity).updateProgressBar(View.INVISIBLE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ((MainActivity)mCallbackActivity).updateProgressBar(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Download Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

        CustomVolley.addToRequestQueue(getActivity().getApplicationContext(), jsonObjectRequest);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbackActivity = (OnItemSelectedActivityListener) activity;
    }

}
