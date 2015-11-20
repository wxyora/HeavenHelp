package com.heaven.heavenhelp.utils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Acer-002 on 2015/7/31.
 */
public class StringRequestUtil extends StringRequest {


    public StringRequestUtil(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(5000,0,1.0f));
    }

    public StringRequestUtil(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(5000, 0, 1.0f));
    }
}
