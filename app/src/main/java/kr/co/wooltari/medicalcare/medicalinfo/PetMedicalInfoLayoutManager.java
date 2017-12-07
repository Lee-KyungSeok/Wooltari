package kr.co.wooltari.medicalcare.medicalinfo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Kyung on 2017-12-07.
 */

public class PetMedicalInfoLayoutManager extends LinearLayoutManager {

    public PetMedicalInfoLayoutManager(Context context) {
        super(context);
        setAutoMeasureEnabled(false);
    }

    public void setView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(this);
    }
}
