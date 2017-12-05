package kr.co.wooltari.medicalcare.healthState;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.wooltari.R;
import kr.co.wooltari.util.LoadUtil;

/**
 * Created by Kyung on 2017-12-04.
 */

public class PetStateProfileHolder extends RecyclerView.ViewHolder {

    private FrameLayout imagePSPStage;
    private ImageView imagePetStateProfile;
    private TextView textInputPSPName, textPSPWeight, textInputPSPWeight, textPSPHeight, textInputPSPHeight;
    private TextView textPSPNeckSize, textInputPSPNeckSize, textPSPChestSize, textInputPSPChestSize;


    public PetStateProfileHolder(View itemView) {
        super(itemView);

        initView(itemView);
    }

    private void initView(View itemView){
        imagePSPStage = itemView.findViewById(R.id.imagePSPStage);
        imagePetStateProfile = itemView.findViewById(R.id.imagePetStateProfile);
        textInputPSPName = itemView.findViewById(R.id.textInputPSPName);
        textPSPWeight = itemView.findViewById(R.id.textPSPWeight);
        textInputPSPWeight = itemView.findViewById(R.id.textInputPSPWeight);
        textPSPHeight = itemView.findViewById(R.id.textPSPHeight);
        textInputPSPHeight = itemView.findViewById(R.id.textInputPSPHeight);
        textPSPNeckSize = itemView.findViewById(R.id.textPSPNeckSize);
        textInputPSPNeckSize = itemView.findViewById(R.id.textInputPSPNeckSize);
        textPSPChestSize = itemView.findViewById(R.id.textPSPChestSize);
        textInputPSPChestSize = itemView.findViewById(R.id.textInputPSPChestSize);
    }

    public void setImagePetStateProfile(Context context, String imageUrl){
        LoadUtil.circleImageLoad(context,imageUrl,imagePetStateProfile);
    }

    public void setTextColor(Context context, String color){
        int textColor = LoadUtil.loadColor(context, color);
        textInputPSPName.setTextColor(textColor);
        textPSPWeight.setTextColor(textColor);
        textPSPHeight.setTextColor(textColor);
        textPSPNeckSize.setTextColor(textColor);
        textPSPChestSize.setTextColor(textColor);
        imagePSPStage.setBackgroundColor(textColor);
    }

    public void setTextInputPSPName(String name){
        textInputPSPName.setText(name);
    }
    public void setTextInputPSPWeight(String weight){
        textInputPSPWeight.setText(weight);
    }
    public void setTextInputPSPHeight(String height){
        textInputPSPHeight.setText(height);
    }
    public void setTextInputPSPNeckSize(String neckSize){
        textInputPSPNeckSize.setText(neckSize);
    }
    public void setTextInputPSPChestSize(String chestSize){
        textInputPSPChestSize.setText(chestSize);
    }
}
