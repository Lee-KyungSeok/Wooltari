package kr.co.wooltari.pet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.CameraGalleryPopup;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.DialogUtil;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;

public class PetProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout stagePetProfilePopup;
    private ImageView imagePetProfile;
    private EditText editPetName, editTextPetNumber;
    private Spinner spinnerPetSpecies, spinnerPetBreeds, spinnerPetYear, spinnerPetMonth, spinnerPetDay;
    private TextView textAge, textHumanAge;
    private RadioGroup radioGroupSex, radioGroupNeutral, radioGroupColor;
    private RadioButton radioButtonMale, radioButtonFemale, radioButtonNeuYes, radioButtonNeuNo;
    private RadioButton radioButtonRed, radioButtonBurgundy, radioButtonPink, radioButtonBeige, radioButtonDarkBlue;
    private RadioButton radioButtonGray, radioButtonDarkGreen, radioButtonGoldGreen, radioButtonBlueOfSea, radioButtonOrangeMuffler;
    private Button btnNumberSearch, btnPetAddEdit, btnPetCancel, btnPetInfoEdit, btnPetDelete, btnPetState;

    private RadioButton activeRadioColor;

    private int pPk = -1;
    CameraGalleryPopup cameraGalleryPopup = null;
    private boolean isImage = false;
    AlertDialog backDialog, cancelDialog, deleteDialog, saveDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        pPk = getIntent().getIntExtra(Const.PET_ID,-1);
        initView();
        init();

        btnPetInfoEdit.setVisibility(View.GONE); // 버튼을 일단은 없앰(확장성 고려)
    }

    private void initView() {
        imagePetProfile = findViewById(R.id.imagePetProfile);
        spinnerPetSpecies = findViewById(R.id.spinnerPetSpecies); spinnerPetBreeds = findViewById(R.id.spinnerPetBreeds);
        spinnerPetYear = findViewById(R.id.spinnerPetYear); spinnerPetMonth = findViewById(R.id.spinnerPetMonth); spinnerPetDay = findViewById(R.id.spinnerPetDay);
        textAge = findViewById(R.id.textAge); textHumanAge = findViewById(R.id.textHumanAge);
        radioGroupSex = findViewById(R.id.radioGroupSex); radioGroupNeutral = findViewById(R.id.radioGroupNeutral);
        radioButtonMale = findViewById(R.id.radioButtonMale); radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonNeuYes = findViewById(R.id.radioButtonNeuYes); radioButtonNeuNo = findViewById(R.id.radioButtonNeuNo);
        editTextPetNumber = findViewById(R.id.editTextPetNumber); editPetName = findViewById(R.id.editPetName);
        btnNumberSearch = findViewById(R.id.btnNumberSearch); btnPetInfoEdit = findViewById(R.id.btnPetInfoEdit);
        btnPetAddEdit = findViewById(R.id.btnPetAddEdit); btnPetCancel = findViewById(R.id.btnPetCancel);
        btnPetDelete = findViewById(R.id.btnPetDelete); btnPetState = findViewById(R.id.btnPetState);
        radioGroupColor = findViewById(R.id.radioGroupColor);
        radioButtonRed = findViewById(R.id.radioButtonRed); radioButtonBurgundy = findViewById(R.id.radioButtonBurgundy);
        radioButtonPink = findViewById(R.id.radioButtonPink); radioButtonBeige = findViewById(R.id.radioButtonBeige);
        radioButtonDarkBlue = findViewById(R.id.radioButtonDarkBlue); radioButtonGray = findViewById(R.id.radioButtonGray);
        radioButtonDarkGreen = findViewById(R.id.radioButtonDarkGreen); radioButtonGoldGreen = findViewById(R.id.radioButtonGoldGreen);
        radioButtonBlueOfSea = findViewById(R.id.radioButtonBlueOfSea); radioButtonOrangeMuffler = findViewById(R.id.radioButtonOrangeMuffler);

        stagePetProfilePopup = findViewById(R.id.stagePetProfilePopup);
    }

    private void init(){
        setBtnListener(); // 버튼, 라디오 버튼 리스너 세팅
        setImagePopup(); // 카메라, 갤러리 팝업 정의
        setPetSpecies(); // 펫 종류, 종 선택 스피너 정의
        setPetBirth(); // 펫 생년월일 스피너 정의
        activeRadioColor = radioButtonRed;

        if(pPk==-1){
            setRegisterPetProfile();
        } else {
            setDefaultPetProfile();
        }
        createDialog();
    }

    /**
     * 팝업 세팅 (팝업 효과 및 이미지 클릭 세팅)
     */
    private void setImagePopup(){
        stagePetProfilePopup.setVisibility(View.GONE);
        cameraGalleryPopup = new CameraGalleryPopup(this, CameraGalleryPopup.PopupType.PET_PROFILE, new CameraGalleryPopup.IDelete() {
            @Override
            public void setBasicImage(Uri basicProfileUri) {
                LoadUtil.circleImageLoad(PetProfileActivity.this, basicProfileUri, imagePetProfile);
                isImage = false;
            }

            @Override
            public void deletePopup() {
                stagePetProfilePopup.setVisibility(View.GONE);
            }
        });
        stagePetProfilePopup.setOnClickListener(v -> stagePetProfilePopup.setVisibility(View.GONE));
        stagePetProfilePopup.addView(cameraGalleryPopup);
        imagePetProfile.setOnClickListener(v -> {
            stagePetProfilePopup.setVisibility(View.VISIBLE);
            cameraGalleryPopup.setbtnList(isImage);
        });
    }

    private void setBtnListener(){
        btnPetCancel.setOnClickListener(this);
        btnPetAddEdit.setOnClickListener(this);
        btnPetInfoEdit.setOnClickListener(this);
        btnNumberSearch.setOnClickListener(this);
        btnPetDelete.setOnClickListener(this);
        btnPetState.setOnClickListener(this);

        for(int i=0 ; i<radioGroupColor.getChildCount() ; i++){
            for(int j=0 ; j<5 ; j++){
                View vRow = ((LinearLayout)radioGroupColor.getChildAt(i)).getChildAt(j);
                vRow.setOnClickListener(this);
            }
        }
    }

    /**
     * 등록을 위한 페이지를 보여줌
     */
    private void setRegisterPetProfile(){
        // 툴바를 register로 세팅
        ToolbarUtil.setCommonToolbar(this,findViewById(R.id.toolbarPetProfile),getResources().getString(R.string.pet_profile_register));
        // 버튼 상태 정의
        btnPetAddEdit.setText(getResources().getString(R.string.pet_profile_btn_add));
        btnPetDelete.setVisibility(View.GONE);
        btnPetState.setVisibility(View.GONE);
        changePetBackgroundColor(activeRadioColor.getCurrentTextColor());
    }

    /**
     * 기본 프로필 세팅 및 버튼 제거, 이름 변경
     */
    private void setDefaultPetProfile(){
        // 툴바를 Porfile로 세팅
        ToolbarUtil.setCommonToolbar(this,findViewById(R.id.toolbarPetProfile),getResources().getString(R.string.pet_profile));
        // 이미지 세팅
        LoadUtil.circleImageLoad(this,PetDummy.data.get(pPk).pProfile, imagePetProfile);
        if(PetDummy.data.get(pPk).pProfile!=null) isImage = true;
        // 버튼 상태 정의
        btnPetAddEdit.setText(getResources().getString(R.string.pet_profile_btn_edit));
        // 이름 디폴트 값 정의
        editPetName.setText(PetDummy.data.get(pPk).pName);
        // 스피너 디폴트 값 정의 (데이터의 정의값 있으면 그것으로 설정)
        checkSpinnerDefaultValue(spinnerPetSpecies, "Dog");
        checkSpinnerDefaultValue(spinnerPetYear,"2000");
        checkSpinnerDefaultValue(spinnerPetMonth,"09");
        checkSpinnerDefaultValue(spinnerPetDay,"23");
        // 라디오 버튼(sex, neuter/spay) 디폴트 값 정의
        checkRadioSexNeuterValue(radioGroupNeutral);
        checkRadioSexNeuterValue(radioGroupSex);
        // pet Number 디폴트 값 정의
        editTextPetNumber.setText(PetDummy.data.get(pPk).petNumber);
        // pet 디폴트 색상 정의
        checkRadioColorValue();
        changePetBackgroundColor(activeRadioColor.getCurrentTextColor());
        // pet State 체크
        if(PetDummy.data.get(pPk).state) changeState(false);
        else changeState(true);
    }

    /**
     * 종 선택에 대한 스피너 정의
     */
    private void setPetSpecies(){
        List<String> data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.pet_species_item)));
        spinnerPetSpecies.setAdapter(createArrayAdapter(data, getResources().getString(R.string.pet_profile_species_hint)));
        spinnerPetSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // position 0 은 hint값
                setPetBreeds(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setPetBreeds(int position){
        List<String> data;
        switch (position){
            case 1: data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.pet_breeds_dog_item))); break;
            case 2: data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.pet_breeds_cat_item))); break;
            default: data = new ArrayList<>(); break;
        }
        spinnerPetBreeds.setAdapter(createArrayAdapter(data, getResources().getString(R.string.pet_profile_breeds_hint)));
        if(pPk!=1) { checkSpinnerDefaultValue(spinnerPetBreeds,"Poodle"); }
    }

    /**
     * 펫 생년월일 정의
     */
    private void setPetBirth(){
        List<String> dataYear = new ArrayList<>();
        for(int i= 2000 ; i<2021 ; i++){ dataYear.add(i+""); }
        spinnerPetYear.setAdapter(createArrayAdapter(dataYear,getResources().getString(R.string.pet_profile_birth_year)));

        List<String> dataMonth = new ArrayList<>();
        for(int i=1 ; i<=12 ; i++){
            if(i<10) dataMonth.add("0"+i+"");
            else  dataMonth.add(i+"");
        }
        spinnerPetMonth.setAdapter(createArrayAdapter(dataMonth,getResources().getString(R.string.pet_profile_birth_month)));

        List<String> dataDay = new ArrayList<>();
        for(int i=1 ; i<=31 ; i++){
            if(i<10) dataDay.add("0"+i+"");
            else  dataDay.add(i+"");
        }
        spinnerPetDay.setAdapter(createArrayAdapter(dataDay,getResources().getString(R.string.pet_profile_birth_day)));
    }

    /**
     * 스피너의 아답터 생성
     */
    private ArrayAdapter createArrayAdapter(List<String> data, String hint){
        data.add(0,hint);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data){
            @Override
            public boolean isEnabled(int position) {
                return position!=0;
            }
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position==0){
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Pet의 색상을 변경시킴
     *  - 현재는 이미지 배경만
     */
    private void changePetBackgroundColor(int colorId){
        imagePetProfile.setBackgroundColor(colorId);
    }
    /**
     * Dialog를 정의
     */
    private void createDialog(){
        backDialog = DialogUtil.showDialog(this, getResources().getString(R.string.alert_pet_cancel_back_title)
                ,getResources().getString(R.string.alert_pet_cancel_back_msg), true);
        backDialog.cancel();
        backDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });

        cancelDialog = DialogUtil.showDialog(this, getResources().getString(R.string.alert_pet_cancel_title)
                ,getResources().getString(R.string.alert_pet_cancel_msg), true);
        cancelDialog.cancel();


        deleteDialog = DialogUtil.showDialog(this, getResources().getString(R.string.alert_pet_delete_title),
                getResources().getString(R.string.alert_pet_delete_msg) , this::delete);
        deleteDialog.cancel();

        saveDialog = DialogUtil.showDialog(this, getResources().getString(R.string.alert_pet_save_title),
                getResources().getString(R.string.alert_pet_save_msg), this::save);
        saveDialog.cancel();

        Log.e("show"," deleteDialog : "+deleteDialog.isShowing()+" cancelDialog : "+cancelDialog.isShowing()+" saveDialog : "+saveDialog.isShowing());
    }

    /**
     * 입력된 정보를 가져올 수 있도록 하는 메소드
     *  - 성별, 중성화여부, 색깔
     */
    private String getPetSex(){
        if(radioButtonMale.isChecked()) return "M";
        else return "F";
    }
    private String getPetNeuterSpay(){
        if(radioButtonNeuYes.isChecked()) return "Y";
        else return "N";
    }
    private String getPetColor(int radioId){
        switch (radioId){
            case R.id.radioButtonBeige:
                // 리턴값을 정의하면 됨
                break;
            case R.id.radioButtonBlueOfSea:
                break;
            case R.id.radioButtonBurgundy:
                break;
            case R.id.radioButtonDarkBlue:
                break;
            case R.id.radioButtonDarkGreen:
                break;
            case R.id.radioButtonGoldGreen:
                break;
            case R.id.radioButtonGray:
                break;
            case R.id.radioButtonOrangeMuffler:
                break;
            case R.id.radioButtonPink:
                break;
            default:
                break;
        }
        return radioId+"";
    }

    /**
     * 스피너의 디폴트 값 찾기
     */
    private void checkSpinnerDefaultValue(Spinner spinner, String value){
        Adapter adapter = spinner.getAdapter();
        for(int i=0 ; i<adapter.getCount() ; i++){
            if(adapter.getItem(i).equals(value)){
                spinner.setSelection(i);
            }
        }
    }
    /**
     * radio 버튼 디폴트 값 찾기
     */
    private void checkRadioSexNeuterValue(RadioGroup radioGroup){
        switch (radioGroup.getId()){
            case R.id.radioGroupSex:
                if ("M".equals(PetDummy.data.get(pPk).sex)) {
                    radioButtonMale.setChecked(true);
                    radioButtonFemale.setChecked(false);
                } else {
                    radioButtonMale.setChecked(false);
                    radioButtonFemale.setChecked(true);
                }
                break;
            case R.id.radioGroupNeutral:
                if ("Y".equals(PetDummy.data.get(pPk).sex)) {
                    radioButtonNeuYes.setChecked(true);
                    radioButtonNeuNo.setChecked(false);
                } else {
                    radioButtonNeuYes.setChecked(false);
                    radioButtonNeuNo.setChecked(true);
                }
                break;
        }
    }

    private void checkRadioColorValue(){
        // 로직 작성// 데이터가 나온 후 만들 수 있을듯....
    }

    /**
     * 인터넷과 연결하여 등록번호 확인
     */
    private void searchNumber(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("http://www.animal.go.kr/mobile2/html/03_inquiry.jsp");
        intent.setData(uri);
        startActivity(intent);
    }

    /**
     * 팻 정보를 저장
     */
    private void save(){
        String name = editPetName.getText().toString();
        String species = spinnerPetSpecies.getSelectedItem().toString();
        String breeds = spinnerPetBreeds.getSelectedItem().toString();
        String year = spinnerPetYear.getSelectedItem().toString();
        String month = spinnerPetMonth.getSelectedItem().toString();
        String day = spinnerPetDay.getSelectedItem().toString();
        String sex = getPetSex();
        String neuterSpay = getPetNeuterSpay();
        String petNum = editTextPetNumber.getText().toString();
        String color = getPetColor(activeRadioColor.getId());

        Log.e("저장 확인"," name = "+name + " species = "+species + " breeds = "+breeds + " birth0 = "+year+month+day
        + " sex = "+sex + " neuterSpay = "+neuterSpay + " petNum = "+petNum + " colorId = "+color );
    }

    /**
     * 펫 정보를 삭제 수행
     */
    private void delete(){
        // ========================
        // 삭제 로직 실행
        //=========================
        PetProfileActivity.this.finish();
    }

    /**
     * 펫을 비활성화
     */
    private void changeState(boolean isActive){
        // 비활성화로 바꿈
        if(isActive){
            btnPetState.setText(getResources().getString(R.string.pet_profile_btn_state_active));
            // 펫 상태 변경
            PetDummy.data.get(pPk).state = false;
            // 버튼 삭제
            btnPetAddEdit.setVisibility(View.GONE);
            btnPetCancel.setVisibility(View.GONE);
            btnNumberSearch.setVisibility(View.GONE);
            // 이미지 클릭 막기
            imagePetProfile.setOnClickListener(null);
            // 배경색 제거
            changePetBackgroundColor(ContextCompat.getColor(this, R.color.colorPetDefault));
            // View 들에 대한 선택 효과 제거
            changeViewEnabled(false);
        } else {
            // 활성화로 바꿈
            btnPetState.setText(getResources().getString(R.string.pet_profile_btn_state_inactive));
            PetDummy.data.get(pPk).state = true;
            btnPetAddEdit.setVisibility(View.VISIBLE);
            btnPetCancel.setVisibility(View.VISIBLE);
            btnNumberSearch.setVisibility(View.VISIBLE);
            imagePetProfile.setOnClickListener(v -> {
                stagePetProfilePopup.setVisibility(View.VISIBLE);
                cameraGalleryPopup.setbtnList(isImage);
            });
            changePetBackgroundColor(activeRadioColor.getCurrentTextColor());
            changeViewEnabled(true);
        }
    }
    private void changeViewEnabled(boolean isEnabled){
        editPetName.setEnabled(isEnabled); editTextPetNumber.setEnabled(isEnabled);
        spinnerPetBreeds.setEnabled(isEnabled); spinnerPetSpecies.setEnabled(isEnabled);
        spinnerPetYear.setEnabled(isEnabled); spinnerPetMonth.setEnabled(isEnabled); spinnerPetDay.setEnabled(isEnabled);
        radioButtonMale.setEnabled(isEnabled); radioButtonFemale.setEnabled(isEnabled);
        radioButtonNeuYes.setEnabled(isEnabled); radioButtonNeuNo.setEnabled(isEnabled);
        for(int i=0 ; i<radioGroupColor.getChildCount() ; i++){
            for(int j=0 ; j<5 ; j++){
                View vRow = ((LinearLayout)radioGroupColor.getChildAt(i)).getChildAt(j);
                vRow.setEnabled(isEnabled);
            }
        }
    }

    @Override
    public void onClick(View v) {
        //color에 관한 radioButton
        if(v instanceof RadioButton) {
            activeRadioColor.setChecked(false);
            ((RadioButton) v).setChecked(true);
            activeRadioColor = (RadioButton) v;
            changePetBackgroundColor(activeRadioColor.getCurrentTextColor());
        } else {
            switch (v.getId()){
                case R.id.btnPetAddEdit: saveDialog.show(); break;
                case R.id.btnPetCancel: cancelDialog.show(); break;
                case R.id.btnPetDelete: deleteDialog.show(); break;
                case R.id.btnPetState: changeState(PetDummy.data.get(pPk).state); break;
                case R.id.btnNumberSearch: searchNumber(); break;
                case R.id.btnPetInfoEdit: break; // 임시용
            }
        }
    }

    /**
     * 툴바 세팅
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ToolbarUtil.setMenuItemSelectedAction(this, item) || super.onOptionsItemSelected(item);
    }

    /**
     * 카메라, 갤러리 팝업에 대한 퍼미션 확인
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Const.PERMISSION_REQ_CAMERA==requestCode || Const.PERMISSION_REQ_GALLERY == requestCode)
            cameraGalleryPopup.checkPermResult(requestCode,grantResults);
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 카메라, 갤러리 결과를 이미지뷰에 세팅
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = cameraGalleryPopup.getImage(requestCode,resultCode,data);
        if(imageUri!=null) {
            LoadUtil.circleImageLoad(this, imageUri, imagePetProfile);
            isImage = true;
        }
    }

    /**
     * 백버튼 세팅
     */
    @Override
    public void onBackPressed() {
        Log.e("show"," backDialog : "+backDialog.isShowing()+" deleteDialog : "+deleteDialog.isShowing()+" cancelDialog : "+cancelDialog.isShowing()+" saveDialog : "+saveDialog.isShowing());
        if(stagePetProfilePopup.getVisibility() == View.VISIBLE){
            stagePetProfilePopup.setVisibility(View.GONE);
        } else if(btnPetState.getVisibility() == View.VISIBLE){
            if(!PetDummy.data.get(pPk).state) finish();
            else backDialog.show();
        } else {
            backDialog.show();
        }
        // super.onBackPressed();
    }
}
