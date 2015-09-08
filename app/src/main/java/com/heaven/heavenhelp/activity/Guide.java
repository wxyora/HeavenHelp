package com.heaven.heavenhelp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.heaven.heavenhelp.R;
import com.heaven.heavenhelp.activity.ViewPaperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Acer-002 on 2015/9/8.
 */
public class Guide extends Activity {

    private ViewPager vp;
    private ViewPaperAdapter vpAdaper;

    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_page);

        initViews();
    }

    public void initViews(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View guide_view_one = inflater.inflate(R.layout.guide_page_one, null);
        View guide_view_two = inflater.inflate(R.layout.guide_page_two, null);
        View guide_view_three = inflater.inflate(R.layout.guide_page_three, null);
        views = new ArrayList<>();
        views.add(guide_view_one);
        views.add(guide_view_two);
        views.add(guide_view_three);

        vpAdaper = new ViewPaperAdapter(views,getApplicationContext());

        ViewPager viewPager = (ViewPager)findViewById(R.id.id_guide_page_one);
        viewPager.setAdapter(vpAdaper);

    }
}
