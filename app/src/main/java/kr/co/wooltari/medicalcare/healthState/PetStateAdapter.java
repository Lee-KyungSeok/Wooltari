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

    HealthStateDummy.StateDummy petState = null;
    PetDummy.Dummy petInfo = null;
    Context context;
    private final int STATE_PROFILE = 20;
    private final int  STATE_CHART = 21;
    private final int  STATE_DETAIL = 22;

    public PetStateAdapter(Context context){
        this.context = context;
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

    public void setDataAndRefresh(HealthStateDummy.StateDummy petState, PetDummy.Dummy petInfo){
        this.petState = petState;
        this.petInfo = petInfo;
        notifyDataSetChanged();
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
                return new PetStateProfileHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position==1){
            PetStateChartHolder holderChart = (PetStateChartHolder)holder;
            holderChart.setChart(petState.petWeightList, petState.petTargetWeight);
        } else if(position==2) {
            PetStateDetailHolder holderDetail = (PetStateDetailHolder)holder;
            holderDetail.setTextInputPSDGoal(petState.petTargetWeight+"");
            holderDetail.setViewPagerPSD(context,petState.petWeightList);
        } else {
            PetStateProfileHolder holderProfile = (PetStateProfileHolder)holder;
            holderProfile.setImagePetStateProfile(context, petInfo.pProfile);
            holderProfile.setTextInputPSPName(petInfo.pName);
            holderProfile.setTextInputPSPWeight(petState.petNowWeight+" kg");
            holderProfile.setTextInputPSPHeight(petState.petHeight+" cm");
            holderProfile.setTextInputPSPNeckSize(petState.petNeckSize+" cm");
            holderProfile.setTextInputPSPChestSize(petState.petChestSize+" cm");
            holderProfile.setTextColor(context, petInfo.color);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
