package kr.co.wooltari.medicalcare.healthState;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.util.LoadUtil;

/**
 * Created by Kyung on 2017-12-05.
 */

public class PetStateDetailPagerAdapter extends PagerAdapter {

    Context context;
    List<HealthStateDummy.petWeight> petStateData;
    FrameLayout progressStagePSD;
    public int pageNum=0;
    private int count =1;

    public PetStateDetailPagerAdapter(Context context, List<HealthStateDummy.petWeight> petStateData){
        this.context = context;
        this.petStateData = petStateData;
        Collections.reverse(this.petStateData);
        checkPageNum();
    }

    private boolean addPage(){
        int compareCount =20*count;
        if(petStateData.size()<=compareCount){
            return false;
        } else {
            count++;
            checkPageNum();
            notifyDataSetChanged();
            return true;
        }
    }

    public void setView(ViewPager viewPager, FrameLayout progressStagePSD){
        viewPager.setAdapter(this);
        this.progressStagePSD = progressStagePSD;
    }

    private void checkPageNum(){
        int compareCount =20*count;
        if(petStateData.size()>compareCount){
            pageNum = 4*count ;
        } else {
            if(petStateData.size()%5==0) pageNum = petStateData.size()/5;
            else pageNum = petStateData.size()/5+1;
        }
    }

    private View getTableData(HealthStateDummy.petWeight data){
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet_state_detail_value,null);
        ((TextView)view.findViewById(R.id.textPSDValueDate)).setText(data.inputDate);
        ((TextView)view.findViewById(R.id.textPSDValueWeight)).setText(data.petWeight+"");
        TextView textPSDValueDelete = view.findViewById(R.id.textPSDValueDelete);
        textPSDValueDelete.setOnClickListener(v ->{ /*삭제 로직*/ });
        return view;
    }

    private void setBtnListener(Button button){
        button.setOnClickListener(v -> {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected void onPreExecute() {
                    progressStagePSD.setVisibility(View.VISIBLE);
                    super.onPreExecute();
                }
                @Override
                protected Boolean doInBackground(Void... voids) {
                    boolean check = addPage();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return check;
                }
                @Override
                protected void onPostExecute(Boolean check) {
                    progressStagePSD.setVisibility(View.GONE);
                    if(!check) Toast.makeText(context, context.getResources().getString(R.string.pet_state_detail_no_more), Toast.LENGTH_SHORT).show();
                    else button.setVisibility(View.GONE);
                }
            }.execute();
        });
    }

    @Override
    public int getCount() {
        return pageNum;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet_state_detail_view,null);
        LinearLayout petStateDetailTable = view.findViewById(R.id.petStateDetailTable);
        Button btnWeightMore = view.findViewById(R.id.btnWeightMore);
        if (pageNum==0) {}
        else {
            if(position+1 == pageNum){
                int compareCount =20*count;
                if(compareCount>=petStateData.size()){
                    for(int i=position*5 ; i<petStateData.size() ; i++){
                        View valueView = getTableData(petStateData.get(i));
                        petStateDetailTable.addView(valueView);
                    }
                } else {
                    for(int i=0 ; i<5 ; i++) {
                        View valueView = getTableData(petStateData.get(position*5+i));
                        petStateDetailTable.addView(valueView);
                    }
                }
                setBtnListener(btnWeightMore);
            } else {
                btnWeightMore.setVisibility(View.GONE);
                for(int i=0 ; i<5 ; i++) {
                    View valueView = getTableData(petStateData.get(position*5+i));
                    petStateDetailTable.addView(valueView);
                }
            }
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
