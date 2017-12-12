package kr.co.wooltari.medicalcare.medicalinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.MedicalInfoDummy;

/**
 * Created by Kyung on 2017-12-07.
 */

public class PetMedicalInfoHolder extends RecyclerView.ViewHolder {

    private MedicalInfoDummy.petMediInfo petMediInfo;

    private LinearLayout stagePMRValue;
    private TextView textPMRDate;
    private TextView textPMRDateValue;
    private TextView textPMRDescription;
    private TextView textPMRDescriptionValue;
    private TextView textPMRComment;
    private TextView textPMRCommentValue;
    private ImageView imagePMRAlarm;
    private TextView textPMRAlarmTime;

    public PetMedicalInfoHolder(View itemView, Context context) {
        super(itemView);

        initView(itemView);
        setListener(context);
    }

    private void initView(View itemView){
        stagePMRValue = itemView.findViewById(R.id.stagePMRValue);
        textPMRDate = itemView.findViewById(R.id.textPMRDate);
        textPMRDateValue = itemView.findViewById(R.id.textPMRDateValue);
        textPMRDescription = itemView.findViewById(R.id.textPMRDescription);
        textPMRDescriptionValue = itemView.findViewById(R.id.textPMRDescriptionValue);
        textPMRComment = itemView.findViewById(R.id.textPMRComment);
        textPMRCommentValue = itemView.findViewById(R.id.textPMRCommentValue);
        imagePMRAlarm = itemView.findViewById(R.id.imagePMRAlarm);
        textPMRAlarmTime = itemView.findViewById(R.id.textPMRAlarmTime);
    }

    private void setListener(Context context){
        stagePMRValue.setOnClickListener(v -> {
            if(context instanceof PetMedicalInfoActivity){
                ((PetMedicalInfoActivity)context).setData(petMediInfo);
                ((PetMedicalInfoActivity)context).downBottomSheet();
            }
        });
    }
    public void setPetMediInfo(MedicalInfoDummy.petMediInfo petMediInfo){
        this.petMediInfo = petMediInfo;
    }

    public void setTextColor(int color){
        textPMRDate.setTextColor(color);
        textPMRDescription.setTextColor(color);
        textPMRComment.setTextColor(color);
    }
    public void setTextPMRDateValue(String date){
        textPMRDateValue.setText(date);
    }
    public void setTextPMRDescriptionValue(String description){
        textPMRDescriptionValue.setText(description);
    }
    public void setTextPMRCommentValue(String comment){
        textPMRCommentValue.setText(comment);
    }
    public void setTextPMRAlarm(){
        // 알람은 나중에....
    }
    public void setImagePMRAlarm(){

    }
}
