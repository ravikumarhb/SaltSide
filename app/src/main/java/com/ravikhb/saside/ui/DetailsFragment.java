package com.ravikhb.saside.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ravikhb.saside.R;
import com.ravikhb.saside.utils.Utils;

/**
 * Created by ravikhb on 09/01/16.
 */
public class DetailsFragment extends Fragment {

    private String mImageStr;
    private String mTitleStr;
    private String mDescriptionStr;

    private TextView mTitle;
    private TextView mDescription;
    private ImageView mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mImageStr = bundle.getString(Utils.IMAGE);
            mDescriptionStr = bundle.getString(Utils.DESCRIPTION);
            mTitleStr = bundle.getString(Utils.TITLE);
        } else {
            SharedPreferences preferences = getActivity().getSharedPreferences(Utils.PREFERENCE_NAME, Context.MODE_PRIVATE);
            mTitleStr = preferences.getString(Utils.TITLE,"");
            mDescriptionStr = preferences.getString(Utils.DESCRIPTION,"");
            mImageStr = preferences.getString(Utils.IMAGE, "");
        }

    }

    private void updateUI() {

        mTitle.setText(mTitleStr);
        mDescription.setText(mDescriptionStr);
        ((MainActivity)getActivity()).updateProgressBar(View.VISIBLE);
        Glide.with(getActivity().getApplicationContext()).load(mImageStr).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                ((MainActivity)getActivity()).updateProgressBar(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((MainActivity)getActivity()).updateProgressBar(View.INVISIBLE);
                return false;
            }
        }).into(mImage);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTitle = (TextView)getActivity().findViewById(R.id.title);
        mDescription = (TextView)getActivity().findViewById(R.id.description);
        mImage = (ImageView)getActivity().findViewById(R.id.image_view);

        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences preferences = getActivity().getSharedPreferences(Utils.PREFERENCE_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(Utils.TITLE, mTitleStr).apply();
        preferences.edit().putString(Utils.DESCRIPTION, mDescriptionStr).apply();
        preferences.edit().putString(Utils.IMAGE, mImageStr).apply();

    }

}
