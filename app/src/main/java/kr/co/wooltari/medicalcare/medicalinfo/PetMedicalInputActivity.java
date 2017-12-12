package kr.co.wooltari.medicalcare.medicalinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import kr.co.wooltari.R;
import kr.co.wooltari.alarm.PetAlarmDialog;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.CameraGalleryPopup;
import kr.co.wooltari.domain.MedicalInfoDummy;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;

public class PetMedicalInputActivity extends AppCompatActivity {

    private Toolbar toolbarPetMedicalInput;
    private ImageView imagePMI;
    private TextView textPMIDate;
    private EditText editPMIDateValue;
    private TextView textPMIAlarm;
    private EditText editPMIAlarmValue;
    private ImageView imagePMIAlarm;
    private TextView textPMIDescription;
    private EditText editPMIDescriptionValue;
    private TextView textPMIComment;
    private EditText editPMICommentValue;
    private Button btnPMIDelete, btnPMISave;
    int petPK;
    int medicalPK;
    String petName;

    PetAlarmDialog petAlarmDialog;
    CameraGalleryPopup cameraGalleryPopup;
    boolean isImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_medical_input);

        initView();

        petPK = getIntent().getIntExtra(Const.PET_ID,-1);
        petName = getIntent().getStringExtra(Const.PET_NAME);
        medicalPK = getIntent().getIntExtra(Const.PET_MEDICAL_ID,-1);
        if(petPK == -1) finish();
        if(medicalPK != -1) setDefaultValue();
        else ToolbarUtil.setCommonToolbar(this,toolbarPetMedicalInput,getResources().getString(R.string.pet_medical_title_input));
    }

    private void initView() {
        toolbarPetMedicalInput = findViewById(R.id.toolbarPetMedicalInput);
        imagePMI = findViewById(R.id.imagePMI);
        textPMIDate = findViewById(R.id.textPMIDate);
        editPMIDateValue = findViewById(R.id.editPMIDateValue);
        textPMIAlarm = findViewById(R.id.textPMIAlarm);
        editPMIAlarmValue = findViewById(R.id.editPMIAlarmValue);
        imagePMIAlarm = findViewById(R.id.imagePMIAlarm);
        textPMIDescription = findViewById(R.id.textPMIDescription);
        editPMIDescriptionValue = findViewById(R.id.editPMIDescriptionValue);
        textPMIComment = findViewById(R.id.textPMIComment);
        editPMICommentValue = findViewById(R.id.editPMICommentValue);
        btnPMIDelete = findViewById(R.id.btnPMIDelete);
        btnPMISave = findViewById(R.id.btnPMISave);

        cameraGalleryPopup = new CameraGalleryPopup(this, CameraGalleryPopup.PopupType.PET_MEDICAL, basicProfileUri -> {
            LoadUtil.recCropImageLoad(this,basicProfileUri,imagePMI);
            isImage = false;
        });

        imagePMI.setOnClickListener(v -> {
            cameraGalleryPopup.show();
            cameraGalleryPopup.setBtnList(isImage);
        });

        petAlarmDialog = new PetAlarmDialog(this, petName, null, null, type -> {
            if(Const.ALARM_ON.equals(type)) imagePMIAlarm.setImageResource(R.drawable.ic_alarm_on);
            else imagePMIAlarm.setImageResource(R.drawable.ic_alarm_off);
        });

        imagePMIAlarm.setOnClickListener(v -> {
            petAlarmDialog.show(petName, editPMIDateValue.getText().toString(), editPMIDescriptionValue.getText().toString());
        });
    }

    private void setDefaultValue(){
        ToolbarUtil.setCommonToolbar(this,toolbarPetMedicalInput,getResources().getString(R.string.pet_medical_title_edit));
        // 나중에는 서버에서 가져와야 함
        MedicalInfoDummy.petMediInfo petMediInfo = null;
        for(MedicalInfoDummy.MedicalDummy data : MedicalInfoDummy.data){
            if (data.pPK == petPK) {
                petMediInfo = data.petMediInfoList.get(medicalPK);
                break;
            }
        }
        if (petMediInfo == null) finish();
        if(petMediInfo.imageUrl!=null) {
            LoadUtil.recCropImageLoad(this, petMediInfo.imageUrl,imagePMI);
            isImage = true;
        } else {
            LoadUtil.recCropImageLoad(this, LoadUtil.getResourceImageUri(R.drawable.pet_profile, this), imagePMI);
        }
        editPMIDateValue.setText(petMediInfo.medicalDate.replace("-",""));
        editPMIDescriptionValue.setText(petMediInfo.description);
        editPMICommentValue.setText(petMediInfo.comment);
        btnPMISave.setText(getResources().getString(R.string.pet_btn_edit));
    }

    public void save(){
        // 저장로직
        finish();
    }

    public void delete(){
        // 삭제로직
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ToolbarUtil.setMenuItemSelectedAction(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Const.PERMISSION_REQ_CAMERA==requestCode || Const.PERMISSION_REQ_GALLERY == requestCode)
            cameraGalleryPopup.checkPermResult(requestCode,grantResults);
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = cameraGalleryPopup.getImage(requestCode,resultCode,data);
        if(imageUri!=null) {
            Glide.with(this).load(imageUri).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(imagePMI);
            isImage = true;
        }
    }
}
