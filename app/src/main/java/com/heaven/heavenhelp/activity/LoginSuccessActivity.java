package com.heaven.heavenhelp.activity;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.ProductInfoAdapter;
import com.heaven.heavenhelp.model.ProductInfo;
import com.heaven.heavenhelp.model.UserInfo;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshBase;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshListView;
import com.heaven.heavenhelp.utils.StringRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginSuccessActivity extends ActionBarActivity {

    PullToRefreshListView newsListView;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        newsListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_text);
        newsListView.setMode(PullToRefreshBase.Mode.BOTH);
        requestQueue = Volley.newRequestQueue(this);



        final StringRequestUtil request = new StringRequestUtil(Request.Method.POST, "http://waylonsir.imwork.net/celechem/getProductInfo.action", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                List<ProductInfo> productInfos = new ArrayList<ProductInfo>();

                JSONObject jo;
                try {
                    JSONArray jsonArray = new JSONArray(s.toString());
                    for (int i=0;i<jsonArray.length();i++){
                        ProductInfo productInfo = new ProductInfo();
                        final Object o = jsonArray.get(i);
                        final JSONObject jsonObject = new JSONObject(o.toString());
                        final String productName = jsonObject.getString("productName");
                        final String productPrice = jsonObject.getString("productPrice");
                        productInfo.setProductName(productName);
                        productInfo.setProductPrice(productPrice+" RMB");
                        productInfos.add(productInfo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ProductInfoAdapter productInfoAdapter = new ProductInfoAdapter(LoginSuccessActivity.this, productInfos);
                newsListView.setAdapter(productInfoAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        requestQueue.add(request);


        newsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            requestQueue.add(request);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        newsListView.onRefreshComplete();
                        super.onPostExecute(aVoid);
                    }
                }.execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        newsListView.onRefreshComplete();
                        super.onPostExecute(aVoid);
                    }
                }.execute();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_success, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
