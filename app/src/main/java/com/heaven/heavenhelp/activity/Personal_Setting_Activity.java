package com.heaven.heavenhelp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.model.UserInfo;
import com.heaven.heavenhelp.utils.SharePrefUtil;

public class Personal_Setting_Activity extends ActionBarActivity {

    private Button btn_login_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        btn_login_out = (Button)findViewById(R.id.btn_login_out);
        btn_login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = SharePrefUtil.getUserInfo(getApplicationContext());
                userInfo.setToken("");
                SharePrefUtil.updateUserInfo(getApplicationContext(), userInfo);
                startActivity(new Intent(Personal_Setting_Activity.this, IndexActivity.class));
                finish();
            }
        });
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login_success, menu);
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
        }else{
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



}
