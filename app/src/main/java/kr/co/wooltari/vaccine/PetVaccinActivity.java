package kr.co.wooltari.vaccine;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import kr.co.wooltari.R;

public class PetVaccinActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ImageView petInfo_imageview;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button addpetInfo_button;
    private Context mContext;
    private RecyclerView vaccineinfo_recyclerview;
    private LinearLayout vaccineinfo_all;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<VaccineInfo> tempInfo;

    private static String TAG="PetVaccinActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_vaccine);

        init();
    }

    private void init() {
        petInfo_imageview=findViewById(R.id.petvaccineactivity_petInfo_imageview);

        mContext = getApplicationContext();

        vaccineinfo_recyclerview=findViewById(R.id.petvaccineactivity_vaccineinfo_recyclerview);
        vaccineinfo_recyclerview.setHasFixedSize(true);

        addpetInfo_button=findViewById(R.id.petvaccineactivity_addpetInfo_button);

        Glide.with(mContext)
                .load("https://avatars0.githubusercontent.com/u/" +0+"?v=4")
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(petInfo_imageview);

        // 임시 VaccineInfo
        tempInfo = new ArrayList<>();
        tempInfo.add(new VaccineInfo("광견병", "1개월", "1/3", "하나병원", "2018/01/01",false));
        tempInfo.add(new VaccineInfo("광견병", "1개월", "2/3", "하나병원", "2018/02/01",false));
        tempInfo.add(new VaccineInfo("광견병", "1개월", "3/3", "하나병원", "2018/03/01",false));
        tempInfo.add(new VaccineInfo("광견병", "1개월", "1/3", "하나병원", "2018/04/01",false));
        tempInfo.add(new VaccineInfo("광견병", "1개월", "2/3", "하나병원", "2018/05/01",false));
        tempInfo.add(new VaccineInfo("광견병", "1개월", "3/3", "하나병원", "2018/06/01",false));

        layoutManager = new LinearLayoutManager(this);
        vaccineinfo_recyclerview.setLayoutManager(layoutManager);

        VaccineInfoAdapter adapter = new VaccineInfoAdapter(tempInfo,mContext);

        mSwipeRefreshLayout = findViewById(R.id.petvaccineactivity_petInfo_swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        addpetInfo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG+1,"추가 버튼 클릭");
            }
        });

        vaccineinfo_recyclerview.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        vaccineinfo_recyclerview.setLayoutManager(llm);

        vaccineinfo_recyclerview.setAdapter(adapter);
        vaccineinfo_recyclerview.getAdapter().notifyDataSetChanged();

        vaccineinfo_all=findViewById(R.id.petvaccineactivity_vaccineinfo_all);

    }

    @Override
    public void onRefresh() {
        // TODO 새로고침 코드 추가
        Log.e(TAG+1, "onRefresh");
        // 새로고침 완료시
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

class VaccineInfoAdapter extends RecyclerView.Adapter<VaccineInfoAdapter.TempViewHolder> {
    private Context context;
    private ArrayList<VaccineInfo> mItems;
    private String TAG="VaccineInfoAdapter";

    public VaccineInfoAdapter(ArrayList<VaccineInfo> mItems, Context context) {
        this.context = context;
        this.mItems = mItems;
    }

    @Override
    public TempViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_vacccine_info,parent,false);
        TempViewHolder holder = new TempViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TempViewHolder holder, int position) {
        holder.vaccine_TextView.setText(mItems.get(position).getVaccine());
        holder.peroid_TextView.setText(mItems.get(position).getPeroid());
        holder.number_TextView.setText(mItems.get(position).getNumber());
        holder.hospital_TextView.setText(mItems.get(position).getHospital());
        holder.date_TextView.setText(mItems.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class TempViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/{
        public CardView petinfo_CardView;
        public TextView vaccine_TextView;
        public TextView peroid_TextView;
        public TextView number_TextView;
        public TextView hospital_TextView;
        public TextView date_TextView;
        public LinearLayout vaccineinfo_all;
        public ToggleButton alarm_ToggleButton;
//        public ITempHolderClicks mListener;

        public TempViewHolder(View view) {
            super(view);
            petinfo_CardView= view.findViewById(R.id.petvaccineactivity_petInfo_cardview);
            vaccine_TextView = (TextView) view.findViewById(R.id.petvaccineactivity_vaccineinfo_vaccine);
            peroid_TextView = (TextView) view.findViewById(R.id.petvaccineactivity_vaccineinfo_period);
            number_TextView = (TextView) view.findViewById(R.id.petvaccineactivity_vaccineinfo_number);
            hospital_TextView = (TextView) view.findViewById(R.id.petvaccineactivity_vaccineinfo_hospital);
            date_TextView = (TextView) view.findViewById(R.id.petvaccineactivity_vaccineinfo_date);
            alarm_ToggleButton=view.findViewById(R.id.petvaccineactivity_petInfo_alarm_ToggleButton);
//            alarm_ToggleButton.setOnClickListener(this);
            vaccineinfo_all=view.findViewById(R.id.petvaccineactivity_vaccineinfo_all);
//            vaccineinfo_all.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View view) {
//            if(view instanceof ToggleButton){
//                mListener.onCkeckUnCheckAlarm((ToggleButton)view);
//            }else {
//                mListener.onPetInfo(view);
//            }
//        }
    }
//    public static interface ITempHolderClicks{
//        public void onPetInfo(View view);
//        public void onCkeckUnCheckAlarm(ToggleButton view_toggleButton);
//    }
}

class VaccineInfo{
    private String vaccine;
    private String peroid;
    private String number;
    private String hospital;
    private String date;
    private boolean alermChecked;

    public VaccineInfo(String vaccine, String peroid, String number, String hospital, String date, boolean alermChecked) {
        this.vaccine = vaccine;
        this.peroid = peroid;
        this.number = number;
        this.hospital = hospital;
        this.date = date;
        this.alermChecked = alermChecked;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getPeroid() {
        return peroid;
    }

    public void setPeroid(String peroid) {
        this.peroid = peroid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAlermChecked() {
        return alermChecked;
    }

    public void setAlermChecked(boolean alermChecked) {
        this.alermChecked = alermChecked;
    }

    @Override
    public String toString() {
        return "VaccineInfo{" +
                "vaccine='" + vaccine + '\'' +
                ", peroid='" + peroid + '\'' +
                ", number='" + number + '\'' +
                ", hospital='" + hospital + '\'' +
                ", date='" + date + '\'' +
                ", alermChecked=" + alermChecked +
                '}';
    }
}
