package com.example.kyung.wooltari.pet;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
        drawer =findViewById(R.id.drawer_pet_layout);
        PetNavigationView navigation = new PetNavigationView(this,drawer, findViewById(R.id.navPetView));
        ToolbarUtil.setMainToolBar(this, drawer, findViewById(R.id.petDetailToolbar), "Pet0");
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
