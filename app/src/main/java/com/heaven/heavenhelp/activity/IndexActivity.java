package com.heaven.heavenhelp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity implements ProductInfoFragment.OnFragmentInteractionListener,MyCenterFragment.OnFragmentInteractionListener {

    ViewPager pag=null;
    private ArrayList<Fragment> fragmentList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        pag=(ViewPager)this.findViewById(R.id.id_viewPager);
        initViewpager();
    }


    private void initViewpager(){
        ProductInfoFragment fragment1 = ProductInfoFragment.newInstance("123","qwe");
        MyCenterFragment fragment2 = MyCenterFragment.newInstance("345","qwe");
        LayoutInflater inflater = getLayoutInflater();
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        pag.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

        System.out.print(true);

    }
}
