package kr.co.wooltari.medicalcare.medicalinfo;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kr.co.wooltari.R;

/**
 * Created by Kyung on 2017-12-07.
 */

public class PetMedicalInfoDivider extends RecyclerView.ItemDecoration {

    private final int verticalHeightSpace;

    public PetMedicalInfoDivider(Context context){
        verticalHeightSpace = (int)context.getResources().getDimension(R.dimen.pet_medical_bottom_item_interval);
    }

    public void setView(RecyclerView recyclerView){
        recyclerView.addItemDecoration(this);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = verticalHeightSpace;
    }
}
