package com.heaven.heavenhelp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.heaven.heavenhelp.R;

public class PublicAccompanyActivity extends ActionBarActivity {

    EditText et_descreb_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_accompany);
        ActionBar supportActionBar = getSupportActionBar();

        supportActionBar.setDisplayHomeAsUpEnabled(true);
        et_descreb_info = (EditText)findViewById(R.id.et_descreb_info);
        //et_descreb_info.clearFocus();
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
