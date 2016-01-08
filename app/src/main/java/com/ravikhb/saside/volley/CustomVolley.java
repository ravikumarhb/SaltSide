package com.ravikhb.saside.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ravikhb on 07/01/16.
 */
public class CustomVolley {

    private static final String TAG = CustomVolley.class.getName();
    private static RequestQueue mRequestQueue;

    private static RequestQueue getInstance(Context context){
        if(mRequestQueue == null ) {
            mRequestQueue =  Volley.newRequestQueue(context);
        }
        return  mRequestQueue;
    }

    public static <T> void addToRequestQueue(Context context, Request<T> request) {
        request.setTag(TAG);
        getInstance(context).add(request);
    }

    public <T> void removeAllFromQueue(Request<T> request) {
        request.setTag(TAG);
        if(mRequestQueue != null ) {
            mRequestQueue.cancelAll(TAG);
        }
    }

}
