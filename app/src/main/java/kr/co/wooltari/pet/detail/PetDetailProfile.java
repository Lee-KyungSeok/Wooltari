package kr.co.wooltari.pet.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.domain.pet.Age;
import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetDataFormatUtil;
import kr.co.wooltari.pet.PetProfileActivity;
import kr.co.wooltari.util.LoadUtil;

/**
 * Created by Kyung on 2017-12-07.
 */

public class PetDetailProfile {

    Activity activity;
    Pet petInfo;
    GradientDrawable gd;

    private FrameLayout imagePetDetailProfileStage;
    private ImageView imagePetDetailProfile;
    private TextView textPDPSpecies, textPDPSpeciesValue;
    private TextView textPDPBreeds, textPDPBreedsValue;
    private TextView textPDPBirth, textPDPBirthValue;
    private TextView textPDPAge, textPDPAgeValue;
    private TextView textPDPHumanAge,  textPDPHumanAgeValue;
    private TextView textPDPSex, textPDPSexValue;
    private TextView textPDPNeuter, textPDPNeuterValue;
    private TextView textPDPNum, textPDPNumValue;
    private Button btnPetStateEdit;

    public PetDetailProfile(Activity activity, ICallbackPetProfile callback){
        this.activity = activity;

        initView();
        setProfileBackground();

        btnPetStateEdit.setOnClickListener(v -> {
            callback.goPetProfile();
        });
    }

    private void initView(){
        imagePetDetailProfileStage = activity.findViewById(R.id.imagePetDetailProfileStage);
        imagePetDetailProfile = activity.findViewById(R.id.imagePetDetailProfile);
        textPDPSpecies = activity.findViewById(R.id.textPDPSpecies);
        textPDPSpeciesValue = activity.findViewById(R.id.textPDPSpeciesValue);
        textPDPBreeds = activity.findViewById(R.id.textPDPBreeds);
        textPDPBreedsValue = activity.findViewById(R.id.textPDPBreedsValue);
        textPDPBirth = activity.findViewById(R.id.textPDPBirth);
        textPDPBirthValue = activity.findViewById(R.id.textPDPBirthValue);
        textPDPAge = activity.findViewById(R.id.textPDPAge);
        textPDPAgeValue = activity.findViewById(R.id.textPDPAgeValue);
        textPDPHumanAge = activity.findViewById(R.id.textPDPHumanAge);
        textPDPHumanAgeValue = activity.findViewById(R.id.textPDPHumanAgeValue);
        textPDPSex = activity.findViewById(R.id.textPDPSex);
        textPDPSexValue = activity.findViewById(R.id.textPDPSexValue);
        textPDPNeuter = activity.findViewById(R.id.textPDPNeuter);
        textPDPNeuterValue = activity.findViewById(R.id.textPDPNeuterValue);
        textPDPNum = activity.findViewById(R.id.textPDPNum);
        textPDPNumValue = activity.findViewById(R.id.textPDPNumValue);
        btnPetStateEdit = activity.findViewById(R.id.btnPetStateEdit);
    }

    private void setProfileBackground(){
        gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        imagePetDetailProfileStage.setBackground(gd);
    }

    public void setValue(Pet petInfo){
        this.petInfo = petInfo;
        setColor();
        if(petInfo.getProfileUrl()!=null) LoadUtil.circleImageLoad(activity,petInfo.getProfileUrl(),imagePetDetailProfile);
        else LoadUtil.circleImageLoad(activity,LoadUtil.getResourceImageUri(R.drawable.pet_profile_temp,activity),imagePetDetailProfile);
        textPDPSpeciesValue.setText(PetDataFormatUtil.SpeciesNameById(activity,petInfo.getSpecies()));
        textPDPBreedsValue.setText(PetDataFormatUtil.BreedsNameById(activity,petInfo.getBreeds()));
        textPDPBirthValue.setText(petInfo.getBirth_date());
        textPDPSexValue.setText(petInfo.getGender());
        if(petInfo.getIs_neutering()) textPDPNeuterValue.setText("Y");
        else textPDPNeuterValue.setText("N");
        textPDPNumValue.setText(petInfo.getIdentified_number());
    }

    public void setAge(Age age){
        if(age!=null) {
            textPDPAgeValue.setText(age.getPet_age());
            textPDPHumanAgeValue.setText(age.getConversed_age());
        } else {
            textPDPAgeValue.setText("나이!!!!");
            textPDPHumanAgeValue.setText("사람나이!!!!");
        }
    }

    private void setColor(){
        int petColor = LoadUtil.loadColor(activity,petInfo.getBody_color());
        textPDPSpecies.setTextColor(petColor);
        textPDPBreeds.setTextColor(petColor);
        textPDPBirth.setTextColor(petColor);
        textPDPAge.setTextColor(petColor);
        textPDPHumanAge.setTextColor(petColor);
        textPDPSex.setTextColor(petColor);
        textPDPNeuter.setTextColor(petColor);
        textPDPNum.setTextColor(petColor);
        gd.setColor(petColor);
    }

    public interface ICallbackPetProfile{
        void goPetProfile();
    }
}
