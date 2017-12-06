package kr.co.wooltari.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.PetDummy;

public class contentFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content,container,false);

        RecyclerView petlist=view.findViewById(R.id.Main_PetList_RecycleView);

        List<PetDummy.Dummy> data=new ArrayList<>();
        data.clear();
        data.addAll(PetDummy.data);

        PetListRecyclerAdapter petListAdapter=new PetListRecyclerAdapter(data, this.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);

        petlist.setLayoutManager(layoutManager);
        petlist.setAdapter(petListAdapter);

        return view;
    }
}
