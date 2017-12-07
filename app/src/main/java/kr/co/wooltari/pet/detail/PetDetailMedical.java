package kr.co.wooltari.pet.detail;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.MedicalInfoDummy;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.medicalcare.healthState.PetStateActivity;
import kr.co.wooltari.medicalcare.medicalinfo.PetMedicalInfoActivity;
import kr.co.wooltari.util.LoadUtil;

/**
 * Created by Kyung on 2017-12-07.
 */

public class PetDetailMedical {

    private TextView PDMMore;
    private ImageView imagePDM;
    private TextView textPDMExist;
    private TextView textPDMDateValue;
    private TextView textPDMDescriptionValue;
    private TextView textPDMCommentValue;

    Activity activity;

    public PetDetailMedical(Activity activity, MedicalInfoDummy.petMediInfo lastMedicalInfo, int pFK){
        this.activity = activity;

        initView();
        setValue(lastMedicalInfo);
        setMore(pFK);
    }

    private void initView(){
        PDMMore = activity.findViewById(R.id.PDMMore);
        imagePDM = activity.findViewById(R.id.imagePDM);
        textPDMExist = activity.findViewById(R.id.textPDMExist);
        textPDMDateValue = activity.findViewById(R.id.textPDMDateValue);
        textPDMDescriptionValue = activity.findViewById(R.id.textPDMDescriptionValue);
        textPDMCommentValue = activity.findViewById(R.id.textPDMCommentValue);
    }

    public void setValue(MedicalInfoDummy.petMediInfo lastMedicalInfo){
        if(lastMedicalInfo == null){
            Glide.with(activity).load(LoadUtil.getResourceImageUri(R.drawable.pet_profile,activity)).into(imagePDM);
            textPDMExist.setVisibility(View.VISIBLE);
            textPDMDateValue.setText("");
            textPDMDescriptionValue.setText("");
            textPDMCommentValue.setText("");
        } else {
            if (lastMedicalInfo.imageUrl != null) {
                Glide.with(activity).load(lastMedicalInfo.imageUrl).into(imagePDM);
                textPDMExist.setVisibility(View.GONE);
            } else {
                Glide.with(activity).load(LoadUtil.getResourceImageUri(R.drawable.pet_profile,activity)).into(imagePDM);
                textPDMExist.setVisibility(View.VISIBLE);
            }
            textPDMDateValue.setText(lastMedicalInfo.medicalDate);
            textPDMDescriptionValue.setText(lastMedicalInfo.description);
            textPDMCommentValue.setText(lastMedicalInfo.comment);
        }
    }

    private void setMore(int pFK){
        PDMMore.setOnClickListener( v -> {
            Intent intent = new Intent(activity, PetMedicalInfoActivity.class);
            intent.putExtra(Const.PET_ID,pFK);
            activity.startActivityForResult(intent,Const.PET_MEDICAL);
        });
    }
}
