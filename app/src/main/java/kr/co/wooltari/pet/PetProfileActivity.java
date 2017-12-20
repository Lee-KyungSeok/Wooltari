package kr.co.wooltari.pet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.Toast;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.CameraGalleryPopup;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.domain.pet.ActivePet;
import kr.co.wooltari.domain.pet.Breed;
import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetDataManager;
import kr.co.wooltari.util.DialogUtil;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;

public class PetProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout imagePetProfileStage;
    private ImageView imagePetProfile;
    private EditText editPetName, editTextPetNumber, editPetBirth;
    private Spinner spinnerPetSpecies, spinnerPetBreeds;
    private TextView textAge, textHumanAge;
    private RadioGroup radioGroupSex, radioGroupNeutral, radioGroupColor;
    private RadioButton radioButtonMale, radioButtonFemale, radioButtonNeuYes, radioButtonNeuNo;
    private RadioButton radioButtonRed, radioButtonBrown, radioButtonPink, radioButtonGold, radioButtonDarkBlue;
    private RadioButton radioButtonGray, radioButtonDarkGreen, radioButtonGoldGreen, radioButtonBlueOfSea, radioButtonBlack;
    private Button btnNumberSearch, btnPetAddEdit, btnPetCancel, btnPetInfoEdit, btnPetDelete, btnPetState;

    private RadioButton activeRadioColor;

    private int petPk = -1;
    CameraGalleryPopup cameraGalleryPopup = null;
    GradientDrawable gdImage;
    File profileFile = null;
    private boolean petActive = true;
    private boolean isImage = false;
    AlertDialog backDialog, cancelDialog, deleteDialog, saveDialog, activeDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        petPk = getIntent().getIntExtra(Const.PET_ID,-1);
        initView();
        init();

        btnPetInfoEdit.setVisibility(View.GONE); // 버튼을 일단은 없앰(확장성 고려)
    }

    private void initView() {
        imagePetProfileStage = findViewById(R.id.imagePetProfileStage);
        imagePetProfile = findViewById(R.id.imagePetProfile);
        spinnerPetSpecies = findViewById(R.id.spinnerPetSpecies); spinnerPetBreeds = findViewById(R.id.spinnerPetBreeds);
        textAge = findViewById(R.id.textAge); textHumanAge = findViewById(R.id.textHumanAge);
        radioGroupSex = findViewById(R.id.radioGroupSex); radioGroupNeutral = findViewById(R.id.radioGroupNeutral);
        radioButtonMale = findViewById(R.id.radioButtonMale); radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonNeuYes = findViewById(R.id.radioButtonNeuYes); radioButtonNeuNo = findViewById(R.id.radioButtonNeuNo);
        editTextPetNumber = findViewById(R.id.editTextPetNumber); editPetName = findViewById(R.id.editPetName); editPetBirth = findViewById(R.id.editPetBirth);
        btnNumberSearch = findViewById(R.id.btnNumberSearch); btnPetInfoEdit = findViewById(R.id.btnPetInfoEdit);
        btnPetAddEdit = findViewById(R.id.btnPetAddEdit); btnPetCancel = findViewById(R.id.btnPetCancel);
        btnPetDelete = findViewById(R.id.btnPetDelete); btnPetState = findViewById(R.id.btnPetState);
        radioGroupColor = findViewById(R.id.radioGroupColor);
        radioButtonRed = findViewById(R.id.radioButtonRed); radioButtonBrown = findViewById(R.id.radioButtonBrown);
        radioButtonPink = findViewById(R.id.radioButtonPink); radioButtonGold = findViewById(R.id.radioButtonGold);
        radioButtonDarkBlue = findViewById(R.id.radioButtonDarkBlue); radioButtonGray = findViewById(R.id.radioButtonGray);
        radioButtonDarkGreen = findViewById(R.id.radioButtonDarkGreen); radioButtonGoldGreen = findViewById(R.id.radioButtonGoldGreen);
        radioButtonBlueOfSea = findViewById(R.id.radioButtonBlueOfSea); radioButtonBlack = findViewById(R.id.radioButtonBlack);

        // 배경 이미지 모양 변경
        gdImage = new GradientDrawable();
        gdImage.setShape(GradientDrawable.OVAL);
        imagePetProfileStage.setBackground(gdImage);
    }

    private void init(){
        setBtnListener(); // 버튼, 라디오 버튼 리스너 세팅
        setImagePopup(); // 카메라, 갤러리 팝업 정의
        setPetSpecies(); // 펫 종류, 종 선택 스피너 정의
        setCheckingValidation(); // 유효성 체크
        activeRadioColor = radioButtonBrown;

        if(petPk ==-1){
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
        cameraGalleryPopup = new CameraGalleryPopup(this, CameraGalleryPopup.PopupType.PET_PROFILE, new CameraGalleryPopup.IDelete() {
            @Override
            public void setBasicImage(Uri basicProfileUri) {
                profileFile = cameraGalleryPopup.getProfileFile();
//                Log.e("file",profileFile.toString());
                LoadUtil.circleImageLoad(PetProfileActivity.this, basicProfileUri, imagePetProfile);
            }
        });
        imagePetProfile.setOnClickListener(v -> {
            cameraGalleryPopup.show();
            cameraGalleryPopup.setBtnList(isImage);
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
        btnPetAddEdit.setText(getResources().getString(R.string.pet_btn_add));
        btnPetDelete.setVisibility(View.GONE);
        btnPetState.setVisibility(View.GONE);
        LoadUtil.circleImageLoad(this,LoadUtil.getResourceImageUri(R.drawable.pet_profile_temp,this),imagePetProfile);
        changePetBackground(activeRadioColor.getCurrentTextColor());
    }

    /**
     * 기본 프로필 세팅 및 버튼 제거, 이름 변경
     */
    private void setDefaultPetProfile(){
        // 툴바를 Porfile로 세팅
        ToolbarUtil.setCommonToolbar(this,findViewById(R.id.toolbarPetProfile),getResources().getString(R.string.pet_profile));
        // 버튼 상태 정의
        btnPetAddEdit.setText(getResources().getString(R.string.pet_btn_edit));

        if(petPk>=8) {
            PetDataManager.getPet(this, petPk, petData -> {
                if(petData==null) {
                    Toast.makeText(PetProfileActivity.this, getResources().getString(R.string.pet_null), Toast.LENGTH_SHORT).show();
                } else {
                    if(petData.getProfileUrl()!=null) isImage = true;
                    // 이름 디폴트 값 정의
                    editPetName.setText(petData.getName());
                    // 스피너 디폴트 값 정의 (데이터의 정의값 있으면 그것으로 설정)
                    setPetSpecies(petData);
                    checkSpinnerDefaultValue(spinnerPetSpecies, petData.getSpecies());
                    // 생일 정의
                    editPetBirth.setText(petData.getBirth_date().replace("-",""));
                    // 라디오 버튼(gender, is_neutering/spay) 디폴트 값 정의
                    checkRadioSexNeuterValue(radioGroupSex, "male".equals(petData.getGender()));
                    checkRadioSexNeuterValue(radioGroupNeutral, petData.getIs_neutering());
                    // pet Number 디폴트 값 정의
                    editTextPetNumber.setText(petData.getIdentified_number());
                    // pet 디폴트 색상 정의
                    checkRadioColorValue(petData.getBody_color());
                    changePetBackground(activeRadioColor.getCurrentTextColor());
                    // pet State 체크
                    changeState(!petData.getIs_active());
                    petActive = petData.getIs_active();
                }
            });
        } else { // PetDummy 임시
            // 이미지 세팅
            LoadUtil.circleImageLoad(this, PetDummy.data.get(petPk).getProfileUrl(), imagePetProfile);
            if (PetDummy.data.get(petPk).getProfileUrl() != null) isImage = true;
            // 이름 디폴트 값 정의
            editPetName.setText(PetDummy.data.get(petPk).getName());
            // 스피너 디폴트 값 정의 (데이터의 정의값 있으면 그것으로 설정)
            checkSpinnerDefaultValue(spinnerPetSpecies, PetDummy.data.get(petPk).getSpecies());
            // 생일 정의
            editPetBirth.setText(PetDummy.data.get(petPk).getBirth_date().replace("-", ""));
            // 라디오 버튼(gender, is_neutering/spay) 디폴트 값 정의
            checkRadioSexNeuterValue(radioGroupNeutral, "male".equals(PetDummy.data.get(petPk).getGender()));
            checkRadioSexNeuterValue(radioGroupSex,PetDummy.data.get(petPk).getIs_neutering());
            // pet Number 디폴트 값 정의
            editTextPetNumber.setText(PetDummy.data.get(petPk).getIdentified_number());
            // pet 디폴트 색상 정의
            checkRadioColorValue(PetDummy.data.get(petPk).getBody_color());
            changePetBackground(activeRadioColor.getCurrentTextColor());
            // pet State 체크
            changeState(!PetDummy.data.get(petPk).getIs_active());
            petActive = PetDummy.data.get(petPk).getIs_active();
        }
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
    private void setPetSpecies(Pet pet){
        List<String> data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.pet_species_item)));
        spinnerPetSpecies.setAdapter(createArrayAdapter(data, getResources().getString(R.string.pet_profile_species_hint)));
        spinnerPetSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // position 0 은 hint값
                setPetBreeds(position, pet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setPetBreeds(int position){
        if(position!=0) {
            Breed species = new Breed();
            species.setSpecies(spinnerPetSpecies.getSelectedItem().toString());
            PetDataManager.getBreedsList(this, species, new PetDataManager.CallbackGetBreeds() {
                @Override
                public void getBreeds(List<Breed> breedList) {
                    List<String> data = new ArrayList<>();
                    for (Breed breed : breedList) {
                        data.add(breed.getBreeds_name());
                    }
                    spinnerPetBreeds.setAdapter(createArrayAdapter(data, getResources().getString(R.string.pet_profile_breeds_hint)));
                    if(petPk !=-1 && petPk<=8) { checkSpinnerDefaultValue(spinnerPetBreeds, PetDummy.data.get(petPk).getBreeds()); }
                }
            });
        } else {
            List<String> data = new ArrayList<>();
            spinnerPetBreeds.setAdapter(createArrayAdapter(data, getResources().getString(R.string.pet_profile_breeds_hint)));
            if(petPk !=-1 && petPk<=8) { checkSpinnerDefaultValue(spinnerPetBreeds, PetDummy.data.get(petPk).getBreeds()); }
        }
    }
    private void setPetBreeds(int position, Pet pet){
//        List<String> data;
//        switch (position){
//            case 1: data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.pet_breeds_dog_item))); break;
//            case 2: data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.pet_breeds_cat_item))); break;
//            default: data = new ArrayList<>(); break;
//        }
        if(position!=0) {
            Breed species = new Breed();
            species.setSpecies(spinnerPetSpecies.getSelectedItem().toString());
            PetDataManager.getBreedsList(this, species, new PetDataManager.CallbackGetBreeds() {
                @Override
                public void getBreeds(List<Breed> breedList) {
                    List<String> data = new ArrayList<>();
                    for (Breed breed : breedList) {
                        data.add(breed.getBreeds_name());
                    }
                    spinnerPetBreeds.setAdapter(createArrayAdapter(data, getResources().getString(R.string.pet_profile_breeds_hint)));
                    if(petPk !=-1) {checkSpinnerDefaultValue(spinnerPetBreeds, pet.getBreeds());}
                }
            });
        } else {
            List<String> data = new ArrayList<>();
            spinnerPetBreeds.setAdapter(createArrayAdapter(data, getResources().getString(R.string.pet_profile_breeds_hint)));
            if(petPk !=-1) {checkSpinnerDefaultValue(spinnerPetBreeds, pet.getBreeds());}
        }
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
    private void changePetBackground(int colorId){
        gdImage.setColor(colorId);
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

        activeDialog = DialogUtil.showDialog(this, getResources().getString(R.string.alert_pet_active_title),
                getResources().getString(R.string.alert_pet_active_msg), () -> {
                    if(petPk<=8) {
                        changeState(PetDummy.data.get(petPk).getIs_active());
                    }
                    else {
                        ActivePet activePet = new ActivePet();
                        if(petActive) activePet.setIs_active("False");
                        else activePet.setIs_active("True");
                        PetDataManager.updatePetActive(this, petPk, activePet, petData -> {
                            changeState(petActive);
                        });
                    }
                });
        activeDialog.cancel();
    }

    /**
     * 입력된 정보를 가져올 수 있도록 하는 메소드
     *  - 성별, 중성화여부, 색깔
     */
    private String getPetSex(){
        if(radioButtonMale.isChecked()) return "male";
        else return "female";
    }
    private boolean getPetNeuterSpay(){
        if(radioButtonNeuYes.isChecked()) return true;
        else return false;
    }
    private String getPetColor(int radioId){
        switch (radioId){
            case R.id.radioButtonBlack:
                return "black";
            case R.id.radioButtonBrown:
                return "brown";
            case R.id.radioButtonGold:
                return "gold";
            case R.id.radioButtonBlueOfSea:
                return "colorBlueOfSea";
            case R.id.radioButtonDarkBlue:
                return "colorDarkBlue";
            case R.id.radioButtonDarkGreen:
                return "colorDarkGreen";
            case R.id.radioButtonGoldGreen:
                return "colorGoldGreen";
            case R.id.radioButtonGray:
                return "colorGray";
            case R.id.radioButtonPink:
                return "colorPink";
            default:
                return "colorRed";
        }
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
    private void checkRadioSexNeuterValue(RadioGroup radioGroup, boolean checking){
        switch (radioGroup.getId()){
            case R.id.radioGroupSex:
                if (checking) {
                    radioButtonMale.setChecked(true);
                    radioButtonFemale.setChecked(false);
                } else {
                    radioButtonMale.setChecked(false);
                    radioButtonFemale.setChecked(true);
                }
                break;
            case R.id.radioGroupNeutral:
                if (checking) {
                    radioButtonNeuYes.setChecked(true);
                    radioButtonNeuNo.setChecked(false);
                } else {
                    radioButtonNeuYes.setChecked(false);
                    radioButtonNeuNo.setChecked(true);
                }
                break;
        }
    }

    private void checkRadioColorValue(String colorName){
        activeRadioColor.setChecked(false);
        switch (colorName){
            case "brown": activeRadioColor = radioButtonBrown; break;
            case "black": activeRadioColor = radioButtonBlack; break;
            case "gold": activeRadioColor = radioButtonGold; break;
            case "colorRed": activeRadioColor = radioButtonRed; break;
            case "colorPink": activeRadioColor = radioButtonPink; break;
            case "colorDarkBlue": activeRadioColor = radioButtonDarkBlue; break;
            case "colorGray": activeRadioColor = radioButtonGray; break;
            case "colorDarkGreen": activeRadioColor = radioButtonDarkGreen; break;
            case "colorGoldGreen": activeRadioColor = radioButtonGoldGreen; break;
            case "colorBlueOfSea": activeRadioColor = radioButtonBlueOfSea; break;
        }
        activeRadioColor.setChecked(true);
        changePetBackground(activeRadioColor.getCurrentTextColor());
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

    private Pet getInputData(){
        Pet pet = new Pet();
        pet.setName(editPetName.getText().toString());
        pet.setSpecies(spinnerPetSpecies.getSelectedItem().toString());
        pet.setBreeds(spinnerPetBreeds.getSelectedItem().toString());
        String birth = editPetBirth.getText().toString();
        pet.setBirth_date(birth.substring(0,4)+"-"+birth.substring(4,6)+"-"+birth.substring(6,8));
        pet.setGender(getPetSex());
        pet.setIs_neutering(getPetNeuterSpay());
        pet.setIdentified_number(editTextPetNumber.getText().toString());
        pet.setBody_color(getPetColor(activeRadioColor.getId()));
        pet.setIs_active(petActive);
        return pet;
    }

    /**
     * 팻 정보를 저장
     */
    private void save(){
        if(petPk==-1) {
            PetDataManager.savePet(this, getInputData(), profileFile, petData -> {
                Intent intent = new Intent();
                intent.putExtra(Const.PET_INFO,Const.PET_PROFILE_UPDATE);
                setResult(RESULT_OK,intent);
                finish();
            });
        } else {
            PetDataManager.updatePet(this, petPk, getInputData(), petData -> {
                Intent intent = new Intent();
                intent.putExtra(Const.PET_INFO,Const.PET_PROFILE_UPDATE);
                setResult(RESULT_OK,intent);
                finish();
            });
        }
    }

    /**
     * 펫 정보를 삭제 수행
     */
    private void delete(){
        PetDataManager.deletePet(this, petPk, () -> {
            Intent intent = new Intent();
            intent.putExtra(Const.PET_INFO,Const.PET_PROFILE_DELETE);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    /**
     * 펫을 비활성화
     */
    private void changeState(boolean isActive){
        // 비활성화로 바꿈
        if(isActive){
            btnPetState.setText(getResources().getString(R.string.pet_profile_btn_state_active));
            // 펫 상태 변경
            if(petPk<=8) PetDummy.data.get(petPk).setIs_active(false);
            else petActive = false;
            // 버튼 삭제
            btnPetAddEdit.setVisibility(View.GONE);
            btnPetCancel.setVisibility(View.GONE);
            btnNumberSearch.setVisibility(View.GONE);
            // 이미지 클릭 막기
            imagePetProfile.setOnClickListener(null);
            // 배경색 제거
            changePetBackground(ContextCompat.getColor(this, R.color.colorBlackE));
            // View 들에 대한 선택 효과 제거
            changeViewEnabled(false);
        } else {
            // 활성화로 바꿈
            btnPetState.setText(getResources().getString(R.string.pet_profile_btn_state_inactive));
            if(petPk<=8) PetDummy.data.get(petPk).setIs_active(true);
            else petActive = true;
            btnPetAddEdit.setVisibility(View.VISIBLE);
            btnPetCancel.setVisibility(View.VISIBLE);
            btnNumberSearch.setVisibility(View.VISIBLE);
            imagePetProfile.setOnClickListener(v -> {
                cameraGalleryPopup.show();
                cameraGalleryPopup.setBtnList(isImage);
            });
            changePetBackground(activeRadioColor.getCurrentTextColor());
            changeViewEnabled(true);
        }
    }
    private void changeViewEnabled(boolean isEnabled){
        editPetName.setEnabled(isEnabled); editTextPetNumber.setEnabled(isEnabled);
        spinnerPetBreeds.setEnabled(isEnabled); spinnerPetSpecies.setEnabled(isEnabled);
        editPetBirth.setEnabled(isEnabled);
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
            changePetBackground(activeRadioColor.getCurrentTextColor());
        } else {
            switch (v.getId()){
                case R.id.btnPetAddEdit: saveDialog.show(); break;
                case R.id.btnPetCancel: cancelDialog.show(); break;
                case R.id.btnPetDelete: deleteDialog.show(); break;
                case R.id.btnPetState: activeDialog.show(); break;
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
            profileFile = cameraGalleryPopup.getProfileFile();
            LoadUtil.circleImageLoad(this, imageUri, imagePetProfile);
            isImage = true;
        }
    }

    /**
     * 백버튼 세팅
     */
    @Override
    public void onBackPressed() {
        if(btnPetState.getVisibility() == View.VISIBLE){
            if(petPk>8 && !petActive) finish();
            else if(petPk<=8 && !PetDummy.data.get(petPk).getIs_active()) finish();
            else backDialog.show();
        } else {
            backDialog.show();
        }
        // super.onBackPressed();
    }

    private void setCheckingValidation(){
        Observable<TextViewTextChangeEvent> nameEmitter = RxTextView.textChangeEvents(editPetName);
        Observable<TextViewTextChangeEvent> birthEmitter = RxTextView.textChangeEvents(editPetBirth);
        InitialValueObservable<Integer> speciesEmitter = RxAdapterView.itemSelections(spinnerPetSpecies);
        InitialValueObservable<Integer> breedsEmitter = RxAdapterView.itemSelections(spinnerPetBreeds);
        Observable
                .combineLatest(nameEmitter, birthEmitter, breedsEmitter, (name,birth, breeds) ->
                    isNameValidate(name.text()) && isBirthValidate(birth.text().toString()) && isSpinnerValidate(breeds))
                .subscribe(
                flag ->{
                    btnPetAddEdit.setEnabled(flag);
                }
        );
    }

    private boolean isNameValidate(CharSequence name){
        if(name==null || "".equals(name)) return false;
        else return true;
    }

    private boolean isSpinnerValidate(int position){
        if(position==0) return false;
        else return true;
    }

    private boolean isBirthValidate(String birth){
        if(birth.length()!=8) return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setLenient(false); // 유효성 체크
        try {
            sdf.parse(birth); // birth를 parsing 해본다.
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
