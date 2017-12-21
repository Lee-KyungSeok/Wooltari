package kr.co.wooltari.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.application.WooltariApp;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetDataManager;
import kr.co.wooltari.medicalcare.healthState.PetStateActivity;
import kr.co.wooltari.medicalcare.medicalinfo.PetMedicalInfoActivity;
import kr.co.wooltari.main.UserDetailActivity;
import kr.co.wooltari.pet.detail.PetDetailActivity;
import kr.co.wooltari.util.LoadUtil;

/**
 * Created by Kyung on 2017-11-28.
 */

public class PetNavigationView implements NavigationView.OnNavigationItemSelectedListener  {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Context context;
    Spinner spinnerPetName;
    int petPk = -1;
    String petName = null;
    String petColor = null;
    String petProfile = null;
    boolean petActive = true;

    public PetNavigationView(Context context, DrawerLayout drawerLayout, NavigationView navigationView){
        this.context = context;
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;

        // 네비게이션 기본 세팅
        setHeaderView();
        setPetHeaderView();
        setItemMenu();
    }

    /**
     * 네비게이션의 유저 정보(이미지, 닉네임) 세팅
     */
    private void setHeaderView(){
        View navHeaderView = navigationView.inflateHeaderView(R.layout.common_nav_header);
        LoadUtil.circleImageLoad(context, WooltariApp.userImage, navHeaderView.findViewById(R.id.imageNavUserProfile));
        TextView UserName=navHeaderView.findViewById(R.id.textNavUserName);
        UserName.setText(WooltariApp.userName);
        UserName.setOnClickListener(view -> {
            Intent intent = new Intent(context, UserDetailActivity.class);
            context.startActivity(intent);
        });

    }

    /**
     * 네비게이션의 메뉴 아이템 세팅 및 리스너 등록
     */
    private void setItemMenu(){
        navigationView.inflateMenu(R.menu.common_menu_drawer);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 네비게이션의 펫 정보 세팅
     */
    private void setPetHeaderView(){
        View petHeaderView = navigationView.inflateHeaderView(R.layout.common_nav_pet_header);
        ImageView imageNavPetProfile = petHeaderView.findViewById(R.id.imageNavPetProfile);
        spinnerPetName = petHeaderView.findViewById(R.id.spinnerPetName);

        List<Pet> petData = new ArrayList<>();
        PetDataManager.getPetList((Activity)context, petDataList -> {
            petData.addAll(petDataList);
            // 펫정보에 디폴트값으로 첫번째 반려동물 이미지 세팅
            if(petData.size()>0) LoadUtil.circleImageLoad(context, petData.get(0).getProfileUrl(), imageNavPetProfile);
            else LoadUtil.circleImageLoad(context,LoadUtil.getResourceImageUri(R.drawable.pet_basic_profile,context),imageNavPetProfile);
            initPetHeaderData(petData,imageNavPetProfile);
            if(context instanceof PetDetailActivity) ((ISetSpinner)context).setSpinner();
        });

    }

    /**
     * 펫 정보 세팅
     */
    private void initPetHeaderData(List<Pet> petData, ImageView imageNavPetProfile){
        // 스피너세팅
        // 이름 배열 생성
        List<String> petNameList = new ArrayList<>();
        for(int i=0 ; i<petData.size() ; i++){
            petNameList.add(petData.get(i).getName());
        }
        // 아답터 세팅
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_expandable_list_item_1, petNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPetName.setAdapter(adapter);
        // 아답터에 리스너 세팅하여 이미지 변경
        spinnerPetName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(petData.get(position).getProfileUrl()!=null)
                    LoadUtil.circleImageLoad(context, petData.get(position).getProfileUrl(), imageNavPetProfile);
                else
                    LoadUtil.circleImageLoad(context, LoadUtil.getResourceImageUri(R.drawable.pet_profile_temp,context),imageNavPetProfile);

                petPk = petData.get(position).getPk();
                petName = petData.get(position).getName();
                petColor = petData.get(position).getBody_color();
                petProfile = petData.get(position).getProfileUrl();
                petActive = petData.get(position).getIs_active();
                if(context instanceof PetDetailActivity){
                    ((PetDetailActivity)context).changeView(petPk);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 동물이 선택되어 있을경우 스피너의 디폴트 값을 변경
     * @param petName
     */
    public void setPetSpinnerLocation(String petName){
        Adapter adapter = spinnerPetName.getAdapter();
        for(int i=0 ; i<adapter.getCount() ; i++){
            if(adapter.getItem(i).equals(petName)){
                spinnerPetName.setSelection(i);
            }
        }
    }

    /**
     * 메뉴 선택시에 해당 엑티비티로 이동하게 하는 메소드
     * return이 true이면 check로 표시지정이 됨
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(petPk!=-1) {
            switch (item.getItemId()) {
                case R.id.nav_pet_state: goActivity(PetStateActivity.class); break;
                case R.id.nav_vaccination: goActivity(PetStateActivity.class); break;
                case R.id.nav_medical_info: goActivity(PetMedicalInfoActivity.class); break;
                case R.id.nav_temp_detail: goActivity(PetDetailActivity.class); break;
            }
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.navigation_pet_null), Toast.LENGTH_SHORT).show();
        }
        return false;

    }

    /**
     * 엑티비티로 이동하는 메소드
     * @param cls
     */
    private void goActivity(Class<?> cls){
        Intent intent= new Intent(context, cls);
        intent.putExtra(Const.PET_ID, petPk);
        intent.putExtra(Const.PET_NAME,petName);
        intent.putExtra(Const.PET_COLOR,petColor);
        intent.putExtra(Const.PET_PROFILE_URL,petProfile);
        intent.putExtra(Const.PET_ACTIVE,petActive);
        context.startActivity(intent);
    }

    public interface ISetSpinner{
        void setSpinner();
    }
}
