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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.MyFragmentPagerAdapter;
import com.heaven.heavenhelp.fragment.MyCenterFragment;
import com.heaven.heavenhelp.fragment.NearInfoFragment;
import com.heaven.heavenhelp.fragment.OrderInfoFragment;
import com.heaven.heavenhelp.fragment.ProductInfoFragment;
import com.heaven.heavenhelp.utils.CustomViewPager;

import java.util.ArrayList;


public class IndexActivity extends AppCompatActivity implements ProductInfoFragment.OnFragmentInteractionListener,MyCenterFragment.OnFragmentInteractionListener,NearInfoFragment.OnFragmentInteractionListener,OrderInfoFragment.OnFragmentInteractionListener{

    CustomViewPager pag=null;
    private ArrayList<Fragment> fragmentList=null;
    private TextView tv_my_host,tv_buy_vegetable;
    private ActionBar actionBar;
    ProductInfoFragment fragment1;
    NearInfoFragment fragment2;
    OrderInfoFragment fragment3;
    MyCenterFragment fragment4;
    MyFragmentPagerAdapter myFragmentAdapter;
    LinearLayout ll_index,ll_near,ll_order,ll_center;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        pag=(CustomViewPager)this.findViewById(R.id.id_viewPager);
        ll_index =(LinearLayout)findViewById(R.id.ll_index);
        ll_near =(LinearLayout)findViewById(R.id.ll_near);
        ll_order =(LinearLayout)findViewById(R.id.ll_order);
        ll_center = (LinearLayout)findViewById(R.id.ll_center);
        actionBar = getSupportActionBar();
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("首页");
        initViewpager();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(pag.getCurrentItem()==3){
            fragment4.lazyLoad();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initViewpager(){
      
        fragment1 = ProductInfoFragment.newInstance("index","1");
        fragment2 = NearInfoFragment.newInstance("near","2");
        fragment3 = OrderInfoFragment.newInstance("order","3");
        fragment4 = MyCenterFragment.newInstance("center","4");
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        myFragmentAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        pag.setAdapter(myFragmentAdapter);
        pag.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // System.out.print(position);
            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
                    actionBar.setTitle("首页");
                    //fragment1.lazyLoad();
                }
                if (1 == position) {
                    actionBar.setTitle("分类");
                }
                if (2 == position) {
                    actionBar.setTitle("附近");
                }
                if (3 == position) {
                    actionBar.setTitle("个人中心");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //System.out.print(state);
            }
        });

        ll_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pag.setCurrentItem(0,false);

            }

        });

        ll_near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pag.setCurrentItem(1,false);
 
            }

        });

        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pag.setCurrentItem(2,false);
   
            }

        });

        ll_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pag.setCurrentItem(3,false);
     

            }

        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
