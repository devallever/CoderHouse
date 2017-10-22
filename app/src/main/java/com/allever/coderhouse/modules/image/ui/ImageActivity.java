package com.allever.coderhouse.modules.image.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.main.adapter.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allever on 17-5-28.
 */

public class ImageActivity extends AppCompatActivity {

    private ViewPageAdapter viewPageAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity_layout);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.id_image_activity_toolbar);
        toolbar.setTitle("图库");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_android_48);
        }

        fragmentList.add(new BingImageFragment());//

        fragmentList.add(new BaiDuImageFragment("宠物","全部"));
        fragmentList.add(new BaiDuImageFragment("明星","全部"));
        fragmentList.add(new BaiDuImageFragment("美食","全部"));
        fragmentList.add(new BaiDuImageFragment("动漫","全部"));
        fragmentList.add(new BaiDuImageFragment("摄影","全部"));
        fragmentList.add(new BaiDuImageFragment("设计","全部"));
        fragmentList.add(new GanHuoImageFragment());
        fragmentList.add(new BaiDuImageFragment("美女","小清新"));
        //fragmentList.add(new BaiDuImageFragment("美女","性感"));
        //fragmentList.add(new BaiDuImageFragment("美女","大胸"));
        //fragmentList.add(new BaiDuImageFragment("美女","写真"));


        titleList.add("必应壁纸");

        titleList.add("萌货");
        titleList.add("明星");
        titleList.add("美食");
        titleList.add("动漫");
        titleList.add("摄影");
        titleList.add("设计");
        titleList.add("妹子");
        titleList.add("小清新");
        //titleList.add("性感");
        //titleList.add("大胸");
        //titleList.add("写真");

        viewPager = (ViewPager)findViewById(R.id.id_image_activity_view_pager);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(viewPageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_image_activity_table_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                //setResult(RESULT_OK);
                finish();
                break;
            case R.id.id_image_menu_like:
                Toast.makeText(this,"Like",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}
