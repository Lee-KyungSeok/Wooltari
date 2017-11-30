package kr.co.wooltari.util;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import kr.co.wooltari.EmergencyActivity;
import kr.co.wooltari.R;
import kr.co.wooltari.medicalcare.PetStateActivity;


/**
 * Created by Kyung on 2017-11-28.
 */

public class ToolbarUtil {

    public static void setCommonToolbar(AppCompatActivity activity, Toolbar toolbar, String title){
        // 툴바를 세팅
        activity.setSupportActionBar(toolbar);
        // 툴바에 뒤로가기 메뉴 추가
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바의 타이틀 세팅
        if(title!=null && !"".equals(title)) {
            activity.getSupportActionBar().setTitle(title);
        }
    }

    public static boolean setMenuItemSelectedAction(Context context, MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_24_hospital:
                Intent intent = new Intent(context, EmergencyActivity.class);
                context.startActivity(intent);
                return true;
        }
        return false;
    }

}
