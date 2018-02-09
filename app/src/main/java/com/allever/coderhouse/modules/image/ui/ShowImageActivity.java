package com.allever.coderhouse.modules.image.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.allever.coderhouse.R;

/**
 * Created by allever on 17-6-3.
 */

public class ShowImageActivity extends AppCompatActivity {

    private String url;
    private String description;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_activity_layout);

        url = getIntent().getStringExtra("url");
        description = getIntent().getStringExtra("description");

        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.id_show_image_activity_fragment_container,new ShowImageFragment(url,description));
        fragmentTransaction.commit();

    }
}
