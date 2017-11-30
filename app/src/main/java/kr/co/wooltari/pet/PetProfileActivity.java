package kr.co.wooltari.pet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.CameraGalleryPopup;
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
    private Button btnNumberSearch, btnPetAddEdit, btnPetCancel, btnPetInfoEdit;

    private int pPk = -1;
    CameraGalleryPopup cameraGalleryPopup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        pPk = getIntent().getIntExtra(Const.PET_ID,2);
        initView();
        // 버튼 정의
        setPetProfileButton();
        // 카메라,갤러리 팝업 정의
        setImagePopup();
        if(pPk==-1){
            setRegisterPetProfile();
        } else {
            setDefaultPetProfile();
        }
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
        radioGroupColor = findViewById(R.id.radioGroupColor);
        radioButtonRed = findViewById(R.id.radioButtonRed); radioButtonBurgundy = findViewById(R.id.radioButtonBurgundy);
        radioButtonPink = findViewById(R.id.radioButtonPink); radioButtonBeige = findViewById(R.id.radioButtonBeige);
        radioButtonDarkBlue = findViewById(R.id.radioButtonDarkBlue); radioButtonGray = findViewById(R.id.radioButtonGray);
        radioButtonDarkGreen = findViewById(R.id.radioButtonDarkGreen); radioButtonGoldGreen = findViewById(R.id.radioButtonGoldGreen);
        radioButtonBlueOfSea = findViewById(R.id.radioButtonBlueOfSea); radioButtonOrangeMuffler = findViewById(R.id.radioButtonOrangeMuffler);

        // 툴바세팅
        ToolbarUtil.setCommonToolbar(this,findViewById(R.id.toolbarPetProfile),getResources().getString(R.string.pet_profile_register));
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    /**
     * 팝업 세팅 (팝업 효과 및 이미지 클릭 세팅)
     */
    private void setImagePopup(){
        stagePetProfilePopup = findViewById(R.id.stagePetProfilePopup);
        stagePetProfilePopup.setVisibility(View.GONE);
        cameraGalleryPopup = new CameraGalleryPopup(this, CameraGalleryPopup.PopupType.PET_PROFILE, new CameraGalleryPopup.IDelete() {
            @Override
            public void setBasicImage(Uri basicProfileUri) {
                LoadUtil.circleImageLoad(PetProfileActivity.this, basicProfileUri, imagePetProfile);
            }

            @Override
            public void deletePopup() {
                stagePetProfilePopup.setVisibility(View.GONE);
            }
        });
        stagePetProfilePopup.setOnClickListener(v -> stagePetProfilePopup.setVisibility(View.GONE));
        stagePetProfilePopup.addView(cameraGalleryPopup);
        imagePetProfile.setOnClickListener(v -> stagePetProfilePopup.setVisibility(View.VISIBLE));
    }

    private void setPetProfileButton(){
        btnPetCancel.setOnClickListener(this);
        btnPetAddEdit.setOnClickListener(this);
        btnPetInfoEdit.setOnClickListener(this);
        btnNumberSearch.setOnClickListener(this);
    }

    /**
     * 등록을 위한 페이지를 보여줌
     */
    private void setRegisterPetProfile(){
        // 버튼 상태 정의
        btnPetInfoEdit.setVisibility(View.GONE);
        btnPetAddEdit.setText(getResources().getString(R.string.pet_profile_btn_add));
    }

    /**
     * 기본 프로필 세팅 및 버튼 제거, 이름 변경
     */
    private void setDefaultPetProfile(){
        btnPetAddEdit.setVisibility(View.GONE);
        btnPetCancel.setVisibility(View.GONE);
        btnNumberSearch.setVisibility(View.GONE);
    }

    private void setPetSpecies(){

    }
    private void setPetBreeds(){

    }
    private void setPetBirth(){

    }
    private void setPetSexAndNeutral(){

    }
    private void setPetColor(){

    }

    @Override
    public void onClick(View v) {

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
        if(imageUri!=null) LoadUtil.circleImageLoad(this,imageUri,imagePetProfile);
    }

    /**
     * 백버튼 세팅
     */
    @Override
    public void onBackPressed() {
        if(stagePetProfilePopup.getVisibility() == View.VISIBLE){
            stagePetProfilePopup.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
