package com.heaven.heavenhelp.activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.UserInfoAdapter;
import com.heaven.heavenhelp.model.UserInfo;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshBase;
import com.heaven.heavenhelp.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


public class LoginSuccessActivity extends ActionBarActivity {


    PullToRefreshListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);







        newsListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_text);
        newsListView.setMode(PullToRefreshBase.Mode.BOTH);

        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for(int i = 0;i<1000;i++){
            userInfos.add(new UserInfo("lisi",String.valueOf(i)));
        }
        UserInfoAdapter userInfoAdapter = new UserInfoAdapter(LoginSuccessActivity.this,userInfos);

        newsListView.setAdapter(userInfoAdapter);

        newsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(2000);
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

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(2000);
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
