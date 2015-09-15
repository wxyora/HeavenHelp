package com.heaven.heavenhelp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer-002 on 2015/9/8.
 */
public class GuideActivity extends Activity implements View.OnClickListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdaper;

    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences =
                getSharedPreferences("loginInfo", MODE_PRIVATE);
        String firstEnterFlag = sharedPreferences.getString("firstEnterFlag", null);
        if("yes".equals(firstEnterFlag)){
            Intent intent = new Intent(GuideActivity.this, IndexActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.guide_page);
        initViews();
    }

    public void initViews() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View guide_view_one = inflater.inflate(R.layout.guide_page_one, null);
        View guide_view_two = inflater.inflate(R.layout.guide_page_two, null);
        View guide_view_three = inflater.inflate(R.layout.guide_page_three, null);
        View enter_my_app = guide_view_three.findViewById(R.id.enter_my_app);
        enter_my_app.setOnClickListener(this);
        views = new ArrayList<>();
        views.add(guide_view_one);
        views.add(guide_view_two);
        views.add(guide_view_three);
        vpAdaper = new ViewPagerAdapter(views, getApplicationContext());
        ViewPager viewPager = (ViewPager) findViewById(R.id.id_guide_page_one);
        viewPager.setAdapter(vpAdaper);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_my_app:
                SharedPreferences sharedPreferences =
                        getSharedPreferences("loginInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("firstEnterFlag","yes");
                editor.commit();
                Intent intent = new Intent(GuideActivity.this, IndexActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
