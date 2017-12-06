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
        data.add(new PetDummy.Dummy(1, "댕댕이" + 0, 0, "128989"));
        data.add(new PetDummy.Dummy(2, "댕댕이" + 1, 1, "123390"));
        data.add(new PetDummy.Dummy(3, "댕댕이" + 2, 2, "123390"));
        data.add(new PetDummy.Dummy(4, "댕댕이" + 3, 3, "123390"));
        data.add(new PetDummy.Dummy(5, "댕댕이" + 4, 4, "123390"));
        data.add(new PetDummy.Dummy(6, "댕댕이" + 5, 5, "123390"));
        data.add(new PetDummy.Dummy(7, "댕댕이" + 6, 6, "123390"));
        data.add(new PetDummy.Dummy(8, "댕댕이" + 7, 7, "123390"));

        PetListRecyclerAdapter petListAdapter=new PetListRecyclerAdapter(data, this.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);

        petlist.setLayoutManager(layoutManager);
        petlist.setAdapter(petListAdapter);

        return view;
    }
}
