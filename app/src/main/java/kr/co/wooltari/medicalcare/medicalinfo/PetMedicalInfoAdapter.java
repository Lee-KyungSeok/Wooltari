package kr.co.wooltari.medicalcare.medicalinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.MedicalInfoDummy;

/**
 * Created by Kyung on 2017-12-07.
 */

public class PetMedicalInfoAdapter extends RecyclerView.Adapter<PetMedicalInfoHolder> {

    Context context;
    int color;
    List<MedicalInfoDummy.petMediInfo> data = new ArrayList<>();

    public PetMedicalInfoAdapter(Context context, int color){
        this.context = context;
        this.color = color;
    }

    public void setDataAndRefresh(List<MedicalInfoDummy.petMediInfo> data){
        this.data = data;
        Collections.reverse(this.data);
        notifyDataSetChanged();
    }

    public void setView(RecyclerView recyclerView){
        recyclerView.setAdapter(this);
    }

    @Override
    public PetMedicalInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet_medical_info_bottom,parent,false);
        return new PetMedicalInfoHolder(view,context);
    }

    @Override
    public void onBindViewHolder(PetMedicalInfoHolder holder, int position) {
        MedicalInfoDummy.petMediInfo mediInfo = data.get(position);
        holder.setPetMediInfo(mediInfo);
        holder.setTextColor(color);
        holder.setTextPMRDateValue(mediInfo.medicalDate);
        holder.setTextPMRDescriptionValue(mediInfo.description);
        holder.setTextPMRCommentValue(mediInfo.comment);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
