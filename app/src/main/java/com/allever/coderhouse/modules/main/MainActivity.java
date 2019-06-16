package com.allever.coderhouse.modules.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.allever.coderhouse.R;
import com.allever.coderhouse.modules.image.ui.ImageActivity;
import com.allever.coderhouse.modules.main.adapter.ViewPageAdapter;
import com.allever.coderhouse.modules.main.ui.MainFragment;
import com.allever.coderhouse.utils.Constants;
import com.allever.coderhouse.view.RxDrawer;
import com.allever.coderhouse.view.RxUtils;
import com.allever.coderhouse.view.SimpleSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity {

    private ViewPageAdapter viewPageAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.id_main_activity_toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.id_main_activity_drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



        NavigationView navigationView = (NavigationView)findViewById(R.id.id_main_activity_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                RxDrawer.close(drawerLayout).compose(RxUtils.rxSchedulerHelper(AndroidSchedulers.mainThread())).subscribe(
                        new SimpleSubscriber<Void>() {
                            @Override
                            public void onNext(Void aVoid) {
                                Intent intent;
                                switch (item.getItemId()){
                                    case R.id.id_nav_menu_pic:
                                        intent = new Intent(MainActivity.this, ImageActivity.class);
                                        startActivity(intent);
                                        break;
                                    case R.id.id_nav_menu_city_music:
                                        drawerLayout.closeDrawers();
                                        break;
                                    case R.id.id_nav_menu_video:
                                        break;
                                    case R.id.id_nav_menu_about:
                                        break;
                                }
                            }
                        });
                return true;
            }
        });

        //
        fragmentList.add(new MainFragment());//
        fragmentList.add(new MainFragment(Constants.DATA_TYPE_ANDROID));
        fragmentList.add(new MainFragment(Constants.DATA_TYPE_IOS));
        fragmentList.add(new MainFragment(Constants.DATA_TYPE_WEB));
        fragmentList.add(new MainFragment(Constants.DATA_TYPE_APP));
        fragmentList.add(new MainFragment(Constants.DATA_TYPE_OTHER));


        titleList.add("猜你喜欢");
        titleList.add(Constants.DATA_TYPE_ANDROID);
        titleList.add(Constants.DATA_TYPE_IOS);
        titleList.add(Constants.DATA_TYPE_WEB);
        titleList.add(Constants.DATA_TYPE_APP);
        titleList.add(Constants.DATA_TYPE_OTHER);

        viewPager = (ViewPager)findViewById(R.id.id_main_activity_view_pager);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(viewPageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_main_activity_table_layout);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.id_main_menu_like:
                break;

        }
        return true;
    }
}
