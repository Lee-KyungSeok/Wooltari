package kr.co.wooltari.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.custom.PetNavigationView;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.pet.detail.PetDetailActivity;
import kr.co.wooltari.util.ToolbarUtil;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        // 네비게이션 바 및 툴바 세팅
        drawer =findViewById(R.id.drawerMainLayout);
        new PetNavigationView(this,drawer, findViewById(R.id.navMainView));
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        ToolbarUtil.setCommonToolbar(this, toolbar, "");
//        // 툴바에 뒤로가기 메뉴 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        // 툴바의 타이틀 세팅
        getSupportActionBar().setTitle("Pet");
//
        // 네비게이션 토글 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


//        RecyclerView petlist=findViewById(R.id.Main_PetList_RecycleView);
//
//        List<PetDummy.Dummy> data=new ArrayList<>();
//        data.clear();
//        data.addAll(PetDummy.data);
//
//        PetListRecyclerAdapter petListAdapter=new PetListRecyclerAdapter(data, this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        petlist.setLayoutManager(layoutManager);
//        petlist.setAdapter(petListAdapter);
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
