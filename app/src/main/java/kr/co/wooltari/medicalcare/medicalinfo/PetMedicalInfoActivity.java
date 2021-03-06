package kr.co.wooltari.medicalcare.medicalinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.MedicalInfoDummy;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;

public class PetMedicalInfoActivity extends AppCompatActivity {

    private Toolbar toolbarPetMedical;
    private ImageView imagePMM;
    private Button btnEditPMM;
    private TextView textPMMName, textPMMNameValue;
    private TextView textPMMDate, textPMMDateValue;
    private TextView textPMMAlarm, textPMMAlarmValue;
    private TextView textPMMDescription, textPMMDescriptionValue;
    private TextView textPMMComment, textPMMCommentValue;
    private ConstraintLayout LayoutPMBottom;
    private RecyclerView recyclerMedicalBottom;
    PetMedicalInfoAdapter adapter;

    private BottomSheetBehavior bottomMedicalBehavior;

    private MedicalInfoDummy.MedicalDummy medicalInfo = null;
    private int petPK;
    private String petName;
    private String petColor;
    private boolean petActive;
    private int mediPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_medical_info);
        initView();

        petPK = getIntent().getIntExtra(Const.PET_ID, -1);
        petName = getIntent().getStringExtra(Const.PET_NAME);
        petColor = getIntent().getStringExtra(Const.PET_COLOR);
        petActive = getIntent().getBooleanExtra(Const.PET_ACTIVE,true);
        for(MedicalInfoDummy.MedicalDummy data : MedicalInfoDummy.data){
            if (data.pPK == petPK) {
                medicalInfo = data;
                break;
            }
        }

        int petIntColor = LoadUtil.loadColor(this, petColor);
        initToolbar();
        initButton();
        setColor(petIntColor);

        if(medicalInfo.petMediInfoList.size()!=0) setData(medicalInfo.petMediInfoList.get(medicalInfo.petMediInfoList.size()-1));
        else btnEditPMM.setVisibility(View.GONE);

        setRecyclerMedicalBottom(petIntColor);
    }

    private void initView() {
        toolbarPetMedical = findViewById(R.id.toolbarPetMedical);
        imagePMM = findViewById(R.id.imagePMM);
        btnEditPMM = findViewById(R.id.btnEditPMM);
        textPMMName = findViewById(R.id.textPMMName);
        textPMMNameValue =findViewById(R.id.textPMMNameValue);
        textPMMDate = findViewById(R.id.textPMMDate);
        textPMMDateValue = findViewById(R.id.textPMMDateValue);
        textPMMAlarm = findViewById(R.id.textPMMAlarm);
        textPMMAlarmValue = findViewById(R.id.textPMMAlarmValue);
        textPMMDescription = findViewById(R.id.textPMMDescription);
        textPMMDescriptionValue = findViewById(R.id.textPMMDescriptionValue);
        textPMMComment = findViewById(R.id.textPMMComment);
        textPMMCommentValue = findViewById(R.id.textPMMCommentValue);
        LayoutPMBottom = findViewById(R.id.LayoutPMBottom);
        recyclerMedicalBottom = findViewById(R.id.recyclerMedicalBottom);

        bottomMedicalBehavior = BottomSheetBehavior.from(LayoutPMBottom);
    }

    private void initToolbar(){
        ToolbarUtil.setCommonToolbar(this,toolbarPetMedical,getResources().getString(R.string.pet_medical_title));
    }

    private void initButton(){
        btnEditPMM.setOnClickListener(v -> {
            Intent intent = new Intent(this,PetMedicalInputActivity.class);
            intent.putExtra(Const.PET_ID,petPK);
            intent.putExtra(Const.PET_NAME, petName);
            intent.putExtra(Const.PET_MEDICAL_ID,mediPK);
            startActivityForResult(intent,Const.PET_PROFILE);
        });
    }

    private void setColor(int color){
        textPMMName.setTextColor(color);
        textPMMDate.setTextColor(color);
        textPMMAlarm.setTextColor(color);
        textPMMDescription.setTextColor(color);
        textPMMComment.setTextColor(color);
    }

    public void setData(MedicalInfoDummy.petMediInfo petMediInfo){
        mediPK = petMediInfo.medicalPk;
        if(petMediInfo.imageUrl!=null) {
            LoadUtil.recCropImageLoad(this,petMediInfo.imageUrl, imagePMM);
        } else {
            LoadUtil.recCropImageLoad(this, LoadUtil.getResourceImageUri(R.drawable.pet_profile, this),imagePMM);
        }
        textPMMNameValue.setText(petName);
        textPMMDateValue.setText(petMediInfo.medicalDate);
        textPMMAlarmValue.setText("임시임시");
        textPMMDescriptionValue.setText(petMediInfo.description);
        textPMMCommentValue.setText(petMediInfo.comment);
        btnEditPMM.setVisibility(View.VISIBLE);
    }

    private void setRecyclerMedicalBottom(int color){
        PetMedicalInfoLayoutManager manager = new PetMedicalInfoLayoutManager(this);
        manager.setView(recyclerMedicalBottom);
        PetMedicalInfoDivider divider = new PetMedicalInfoDivider(this);
        divider.setView(recyclerMedicalBottom);
        adapter = new PetMedicalInfoAdapter(this, color);
        adapter.setView(recyclerMedicalBottom);
        adapter.setDataAndRefresh(medicalInfo.petMediInfoList);
    }

    public void downBottomSheet(){
        bottomMedicalBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medical_info,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_add_medical) {
            Intent intent = new Intent(this,PetMedicalInputActivity.class);
            intent.putExtra(Const.PET_ID, petPK);
            intent.putExtra(Const.PET_NAME, petName);
            startActivityForResult(intent, Const.PET_MEDICAL_ADD);
            return true;
        } else {
            return ToolbarUtil.setMenuItemSelectedAction(this, item) || super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Const.PET_MEDICAL_EDIT:
                if(resultCode == RESULT_OK) {
                    // 서버랑 연동 후 더 해야 할듯...
                    adapter.setDataAndRefresh(medicalInfo.petMediInfoList);
                }
                break;
        }
    }
}
