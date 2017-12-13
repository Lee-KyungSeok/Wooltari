package kr.co.wooltari.medicalcare.healthState;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import kr.co.wooltari.R;
import kr.co.wooltari.util.DialogUtil;
import kr.co.wooltari.util.LoadUtil;

/**
 * Created by Kyung on 2017-12-04.
 */

public class PetStateProfileHolder extends RecyclerView.ViewHolder {

    private FrameLayout imagePSPStage;
    private ImageView imagePetStateProfile;
    private TextView textInputPSPName, textPSPWeight, textInputPSPWeight, textPSPHeight, textInputPSPHeight;
    private TextView textPSPNeckSize, textInputPSPNeckSize, textPSPChestSize, textInputPSPChestSize;
    private TextView textPSPGoalWeight, textInputPSPGoalWeight, textPSPGap, textInputPSPGap;
    private Button btnPetStateEdit;


    public PetStateProfileHolder(Context context, View itemView) {
        super(itemView);

        initView(itemView);
        setStateEditButton(context);
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
        textPSPGoalWeight = itemView.findViewById(R.id.textPSPGoalWeight);
        textInputPSPGoalWeight = itemView.findViewById(R.id.textInputPSPGoalWeight);
        textPSPGap = itemView.findViewById(R.id.textPSPGap);
        textInputPSPGap = itemView.findViewById(R.id.textInputPSPGap);
        btnPetStateEdit = itemView.findViewById(R.id.btnPetStateEdit);
    }

    private void setStateEditButton(Context context){
        if(context instanceof IPetStateShowDialog) {
            btnPetStateEdit.setOnClickListener(v -> ((IPetStateShowDialog) context).showEditDialog());
        }
    }

    public void setGoneEditButton(){
        btnPetStateEdit.setVisibility(View.GONE);
    }

    public void setImagePetStateProfile(Context context, String imageUrl){
        if(imageUrl==null) LoadUtil.circleImageLoad(context,LoadUtil.getResourceImageUri(R.drawable.pet_profile_temp,context),imagePetStateProfile);
        else LoadUtil.circleImageLoad(context,imageUrl,imagePetStateProfile);
    }

    public void setTextColor(Context context, String color){
        int textColor = LoadUtil.loadColor(context, color);
        textInputPSPName.setTextColor(textColor);
        textPSPWeight.setTextColor(textColor);
        textPSPHeight.setTextColor(textColor);
        textPSPNeckSize.setTextColor(textColor);
        textPSPChestSize.setTextColor(textColor);
        textPSPGoalWeight.setTextColor(textColor);
        textPSPGap.setTextColor(textColor);
    }

    public void setBackground(Context context, String color){
        int backgroundColor = LoadUtil.loadColor(context, color);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(backgroundColor);
        imagePSPStage.setBackground(gd);
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
    public void setTextInputPSPGoalWeight(String goalWeight){
        textInputPSPGoalWeight.setText(goalWeight);
    }

    public void setTextInputPSPGap(double weight, double goalWeight){
        double gap = Math.floor(100*(goalWeight-weight))/100;
        String gapString = gap+" kg";
        textInputPSPGap.setText(gapString);
    }

    public interface IPetStateShowDialog{
        void showEditDialog();
    }
}
