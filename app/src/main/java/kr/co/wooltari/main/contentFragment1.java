package kr.co.wooltari.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetDataManager;

public class contentFragment1 extends Fragment {


    Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            activity = (Activity)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content,container,false);

        RecyclerView petlist=view.findViewById(R.id.Main_PetList_RecycleView);

        List<Pet> data=new ArrayList<>();
        data.clear();
        data.addAll(PetDummy.data);

        PetListRecyclerAdapter petListAdapter=new PetListRecyclerAdapter(data, this.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);

        petlist.setLayoutManager(layoutManager);
        petlist.setAdapter(petListAdapter);

        PetDataManager.getPetList(activity, petDataList -> {
            data.addAll(petDataList);
            petListAdapter.setDataAndRefresh(data);
        });
        return view;
    }
}
