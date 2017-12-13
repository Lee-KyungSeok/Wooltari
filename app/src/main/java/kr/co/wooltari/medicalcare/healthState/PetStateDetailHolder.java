package kr.co.wooltari.medicalcare.healthState;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.util.LoadUtil;

/**
 * Created by Kyung on 2017-12-04.
 */

public class PetStateDetailHolder extends RecyclerView.ViewHolder {

    private TextView textInputPSDGoal, textStateTitleDetail, textGoalTitle;
    private ViewPager viewPagerPSD;
    private PetStateDetailPagerAdapter adapter;
    private FrameLayout progressStagePSD;

    public PetStateDetailHolder(View itemView) {
        super(itemView);

        initView(itemView);
    }

    private void initView(View itemView){
        textStateTitleDetail = itemView.findViewById(R.id.textStateTitleDetail);
        textGoalTitle = itemView.findViewById(R.id.textGoalTitle);
        textInputPSDGoal = itemView.findViewById(R.id.textInputPSDGoal);
        viewPagerPSD = itemView.findViewById(R.id.viewPagerPSD);
        progressStagePSD = itemView.findViewById(R.id.progressStagePSD);
    }

    public void setTextInputPSDGoal(String goal){
        textInputPSDGoal.setText(goal);
    }

    public void setTextColor(Context context, String color){
        int petColor = LoadUtil.loadColor(context,color);
        textStateTitleDetail.setTextColor(petColor);
        textGoalTitle.setTextColor(petColor);
    }

    public void setViewPagerPSD(Context context, List<HealthStateDummy.petWeight> petWeightList, String color, boolean active){
        adapter = new PetStateDetailPagerAdapter(context, petWeightList, color, active);
        adapter.setView(viewPagerPSD, progressStagePSD);
    }
}
