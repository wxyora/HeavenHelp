package com.heaven.heavenhelp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.MyFragmentPagerAdapter;
import com.heaven.heavenhelp.utils.CustomViewPager;

import java.util.ArrayList;


public class IndexActivity extends AppCompatActivity implements ProductInfoFragment.OnFragmentInteractionListener,MyCenterFragment.OnFragmentInteractionListener{

    CustomViewPager pag=null;
    private ArrayList<Fragment> fragmentList=null;
    private TextView tv_my_host,tv_buy_vegetable;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        pag=(CustomViewPager)this.findViewById(R.id.id_viewPager);
        tv_my_host =(TextView)findViewById(R.id.tv_my_host);
        tv_buy_vegetable = (TextView)findViewById(R.id.tv_buy_vegetable);
        actionBar = getSupportActionBar();
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("首页");

        initViewpager();

    }
    ProductInfoFragment fragment1;
     MyCenterFragment fragment2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final int drawingCacheBackgroundColor = pag.getDrawingCacheBackgroundColor();
        tv_buy_vegetable.setBackgroundColor(drawingCacheBackgroundColor);
        tv_my_host.setBackgroundColor(Color.GRAY);

    }
    private void initViewpager(){
        final int drawingCacheBackgroundColor = pag.getDrawingCacheBackgroundColor();
        fragment1 = ProductInfoFragment.newInstance("123","qwe");
         fragment2 = MyCenterFragment.newInstance("345", "qwe");
        tv_buy_vegetable.setBackgroundColor(Color.GRAY);
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        pag.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        Intent intent = getIntent();
        pag.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.print(position);
            }

            @Override
            public void onPageSelected(int position) {
                if (1 == position) {
                    fragment2.initCenterInfo();
                    tv_my_host.setBackgroundColor(Color.GRAY);
                    tv_buy_vegetable.setBackgroundColor(drawingCacheBackgroundColor);
                    actionBar.setTitle("个人中心");
                }
                if (0 == position) {
                    //fragment2.loginRequst();
                    tv_my_host.setBackgroundColor(drawingCacheBackgroundColor);
                    tv_buy_vegetable.setBackgroundColor(Color.GRAY);
                    actionBar.setTitle("首页");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.print(state);
            }
        });

        tv_my_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pag.setCurrentItem(1);
                tv_buy_vegetable.setBackgroundColor(drawingCacheBackgroundColor);
                tv_my_host.setBackgroundColor(Color.GRAY);

            }

        });

        tv_buy_vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pag.setCurrentItem(0);
                tv_my_host.setBackgroundColor(drawingCacheBackgroundColor);
                tv_buy_vegetable.setBackgroundColor(Color.GRAY);

            }

        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_index, menu);
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

    }
}
