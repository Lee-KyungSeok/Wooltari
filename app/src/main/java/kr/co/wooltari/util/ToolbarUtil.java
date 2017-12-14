package kr.co.wooltari.util;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import kr.co.wooltari.constant.Const;
import kr.co.wooltari.main.EmergencyActivity;
import kr.co.wooltari.R;
import kr.co.wooltari.medicalcare.medicalinfo.PetMedicalInfoActivity;
import kr.co.wooltari.medicalcare.medicalinfo.PetMedicalInputActivity;
import kr.co.wooltari.pet.PetProfileActivity;
import kr.co.wooltari.pet.detail.PetDetailActivity;


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

    public static boolean setMenuItemSelectedAction(Activity activity, MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_24_hospital:
                intent = new Intent(activity, EmergencyActivity.class);
                activity.startActivity(intent);
                return true;
            case android.R.id.home:
                activity.finish();
                return true;
            case R.id.menu_temp_pet_register:
                intent = new Intent(activity, PetDetailActivity.class);
                intent.putExtra(Const.PET_ID,2);
                activity.startActivity(intent);
                return true;
        }
        return false;
    }

}
