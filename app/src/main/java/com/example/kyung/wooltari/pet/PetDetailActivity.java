package com.example.kyung.wooltari.pet;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.kyung.wooltari.R;
import com.example.kyung.wooltari.custom.PetNavigationView;
import com.example.kyung.wooltari.custom.CameraGalleryPopup;
import com.example.kyung.wooltari.util.LoadUtil;
import com.example.kyung.wooltari.util.ToolbarUtil;

public class PetDetailActivity extends AppCompatActivity {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        initView();
    }

    private void initView(){
        // 네비게이션 바 및 툴바 세팅
        drawer =findViewById(R.id.drawerPetLayout);
        PetNavigationView navigation = new PetNavigationView(this,drawer, findViewById(R.id.navPetView));
        Toolbar toolbar = findViewById(R.id.petDetailToolbar);
        ToolbarUtil.setCommonToolbar(this, toolbar, "Pet0");
        // 네비게이션 토글 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu_etc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ToolbarUtil.setMenuItemSelectedAction(this, item) || super.onOptionsItemSelected(item);
    }
}
