package kr.co.wooltari.medicalcare.healthState;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.HealthStateDummy;

/**
 * Created by Kyung on 2017-12-04.
 */

public class PetStateDetailHolder extends RecyclerView.ViewHolder {

    TextView textInputPSDGoal;
    ViewPager viewPagerPSD;
    PetStateDetailPagerAdapter adapter;

    public PetStateDetailHolder(View itemView) {
        super(itemView);

        initView(itemView);
    }

    private void initView(View itemView){
        textInputPSDGoal = itemView.findViewById(R.id.textInputPSDGoal);
        viewPagerPSD = itemView.findViewById(R.id.viewPagerPSD);
    }

    public void setTextInputPSDGoal(String goal){
        textInputPSDGoal.setText(goal);
    }

    public void setViewPagerPSD(Context context, List<HealthStateDummy.petWeight> petWeightList){
        adapter = new PetStateDetailPagerAdapter(context, petWeightList);
        adapter.setView(viewPagerPSD);

//        viewPagerPSD.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }
}
