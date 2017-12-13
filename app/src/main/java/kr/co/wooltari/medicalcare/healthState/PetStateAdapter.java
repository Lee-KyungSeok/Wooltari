package kr.co.wooltari.medicalcare.healthState;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.PetDummy;


/**
 * Created by Kyung on 2017-12-04.
 */

public class PetStateAdapter extends RecyclerView.Adapter {

    private HealthStateDummy.StateDummy petState = null;
    private Context context;
    private int petPk;
    private String petName;
    private String petColor;
    private boolean petActive;
    private String petProfileUrl;
    private final int STATE_PROFILE = 20;
    private final int  STATE_CHART = 21;
    private final int  STATE_DETAIL = 22;

    public PetStateAdapter(Context context, int petPk, String petName, String petColor, boolean petActive, String petProfileUrl){
        this.context = context;
        this.petPk = petPk;
        this.petName = petName;
        this.petColor = petColor;
        this.petActive = petActive;
        this.petProfileUrl = petProfileUrl;
    }

    public void setView(RecyclerView recyclerView){
        recyclerView.setAdapter(this);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 1:return STATE_CHART ;
            case 2:return STATE_DETAIL;
            default: return STATE_PROFILE;
        }
    }

    public void setDataAndRefresh(HealthStateDummy.StateDummy petState){
        this.petState = petState;
        if(petState!=null) notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case STATE_CHART:
                view = LayoutInflater.from(context).inflate(R.layout.item_pet_state_chart,parent,false);
                return new PetStateChartHolder(view);
            case STATE_DETAIL:
                view = LayoutInflater.from(context).inflate(R.layout.item_pet_state_detail,parent,false);
                return new PetStateDetailHolder(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_pet_state_profile,parent,false);
                return new PetStateProfileHolder(context, view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position==1){
            PetStateChartHolder holderChart = (PetStateChartHolder)holder;
            holderChart.setChart(petState.petWeightList, petState.petTargetWeight);
            if(petState.petWeightList!=null && petState.petWeightList.size()>0) holderChart.setTextStateLastDate(petState.petWeightList.get(petState.petWeightList.size()-1).inputDate);
        } else if(position==2) {
            PetStateDetailHolder holderDetail = (PetStateDetailHolder)holder;
            holderDetail.setTextInputPSDGoal(petState.petTargetWeight+" kg");
            holderDetail.setViewPagerPSD(context,petState.petWeightList);
            holderDetail.setTextColor(context, petColor);
        } else {
            PetStateProfileHolder holderProfile = (PetStateProfileHolder)holder;
            holderProfile.setImagePetStateProfile(context, petProfileUrl);
            holderProfile.setTextInputPSPName(petName);
            holderProfile.setTextColor(context, petColor);
            if(petActive) {
                holderProfile.setBackground(context, petColor);
            } else {
                holderProfile.setBackground(context, "colorPetDefault");
                holderProfile.setGoneEditButton();
            }
            holderProfile.setTextInputPSPWeight(petState.petNowWeight+" kg");
            holderProfile.setTextInputPSPHeight(petState.petHeight+" cm");
            holderProfile.setTextInputPSPNeckSize(petState.petNeckSize+" cm");
            holderProfile.setTextInputPSPChestSize(petState.petChestSize+" cm");
            holderProfile.setTextInputPSPGoalWeight(petState.petTargetWeight+" kg");
            holderProfile.setTextInputPSPGap(petState.petNowWeight, petState.petTargetWeight);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
