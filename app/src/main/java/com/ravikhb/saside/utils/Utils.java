package com.ravikhb.saside.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.ravikhb.saside.R;

/**
 * Created by ravikhb on 08/01/16.
 */
public class Utils {

    public static final String URL = "https://gist.githubusercontent.com/maclir/f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json";

    public static final String IMAGE = "image";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";

    public static final String PREFERENCE_NAME = "preference_name";
    public static final String FRAGMENT = "fragment";
    public static final String DETAILS_FRAGMENT = "details_fragment";
    public static final String HEADLINES_FRAGMENT = "headlines_fragment";


    public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) return false;
            NetworkInfo.State network = info.getState();
            return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

}
