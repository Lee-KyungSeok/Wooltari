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

    CameraGalleryPopup cameraGalleryPopup;
    boolean isImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_medical_input);

        initView();

        petPK = getIntent().getIntExtra(Const.PET_ID,-1);
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
            Glide.with(this).load(basicProfileUri).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(imagePMI);
            isImage = false;
        });

        imagePMI.setOnClickListener(v -> {
            cameraGalleryPopup.show();
            cameraGalleryPopup.setBtnList(isImage);
        });
    }

    private void setDefaultValue(){
        ToolbarUtil.setCommonToolbar(this,toolbarPetMedicalInput,getResources().getString(R.string.pet_medical_title_edit));
        MedicalInfoDummy.petMediInfo petMediInfo = MedicalInfoDummy.data.get(petPK).petMediInfoList.get(medicalPK);
        if(petMediInfo.imageUrl!=null) {
            Glide.with(this).load(petMediInfo.imageUrl)
                    .apply(RequestOptions.bitmapTransform(new CenterCrop())).into(imagePMI);
            isImage = true;
        } else {
            Glide.with(this).load(LoadUtil.getResourceImageUri(R.drawable.pet_profile, this))
                    .apply(RequestOptions.bitmapTransform(new CenterCrop())).into(imagePMI);
        }
        editPMIDateValue.setText(petMediInfo.medicalDate.replace("-",""));
        editPMIDescriptionValue.setText(petMediInfo.description);
        editPMICommentValue.setText(petMediInfo.comment);
        btnPMISave.setText(getResources().getString(R.string.pet_btn_edit));
        // 알람은 나중에...
    }

    public void save(){

    }

    public void delete(){

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
