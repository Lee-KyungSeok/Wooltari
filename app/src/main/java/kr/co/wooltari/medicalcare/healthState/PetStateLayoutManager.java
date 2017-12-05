package kr.co.wooltari.medicalcare.healthState;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Kyung on 2017-12-05.
 */

public class PetStateLayoutManager extends LinearLayoutManager{

    public PetStateLayoutManager(Context context) {
        super(context);
        this.setAutoMeasureEnabled(false);
    }

    public void setView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(this);
    }
}
