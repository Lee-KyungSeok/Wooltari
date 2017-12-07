package kr.co.wooltari.medicalcare.medicalinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.MedicalInfoDummy;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;

public class PetMedicalInfoActivity extends AppCompatActivity {

    private Toolbar toolbarPetMedical;
    private ImageView imagePMM;
    private TextView textPMMName, textPMMNameValue;
    private TextView textPMMDate, textPMMDateValue;
    private TextView textPMMAlarm, textPMMAlarmValue;
    private TextView textPMMDescription, textPMMDescriptionValue;
    private TextView textPMMComment, textPMMCommentValue;
    private ConstraintLayout LayoutPMBottom;
    private RecyclerView recyclerMedicalBottom;
    PetMedicalInfoAdapter adapter;

    private BottomSheetBehavior bottomMedicalBehavior;

    private PetDummy.Dummy petInfo = null;
    private MedicalInfoDummy.MedicalDummy medicalInfo = null;
    private int petPK;
    private int mediPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_medical_info);
        initView();

        petPK = getIntent().getIntExtra(Const.PET_ID,-1);
        petInfo = PetDummy.data.get(petPK);
        medicalInfo = MedicalInfoDummy.data.get(petPK);
        int petColor = LoadUtil.loadColor(this,petInfo.color);
        initToolbar();
        setColor(petColor);

        if(medicalInfo.petMediInfoList.size()!=0) setData(medicalInfo.petMediInfoList.get(medicalInfo.petMediInfoList.size()-1));

        setRecyclerMedicalBottom(petColor);
    }

    private void initView() {
        toolbarPetMedical = findViewById(R.id.toolbarPetMedical);
        imagePMM = findViewById(R.id.imagePMM);
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

    private void setColor(int color){
        textPMMName.setTextColor(color);
        textPMMDate.setTextColor(color);
        textPMMAlarm.setTextColor(color);
        textPMMDescription.setTextColor(color);
        textPMMComment.setTextColor(color);
    }

    public void setData(MedicalInfoDummy.petMediInfo petMediInfo){
        mediPK = petMediInfo.medicalPk;
        textPMMNameValue.setText(petInfo.pName);
        textPMMDateValue.setText(petMediInfo.medicalDate);
        textPMMAlarmValue.setText("임시임시");
        textPMMDescriptionValue.setText(petMediInfo.description);
        textPMMCommentValue.setText(petMediInfo.comment);
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
        return ToolbarUtil.setMenuItemSelectedAction(this, item) || super.onOptionsItemSelected(item);
    }
}
