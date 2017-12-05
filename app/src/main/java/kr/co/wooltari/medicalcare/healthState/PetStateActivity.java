package kr.co.wooltari.medicalcare.healthState;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.ToolbarUtil;


public class PetStateActivity extends AppCompatActivity {

    RecyclerView recyclerState;
    PetStateAdapter adapter;
    PetDummy.Dummy petInfo;
    HealthStateDummy.StateDummy stateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_state);

        int pPk = getIntent().getIntExtra(Const.PET_ID,-1);
        petInfo = PetDummy.data.get(pPk);
        stateInfo = HealthStateDummy.stateData.get(pPk);

        initView();
        setRecyclerState();
    }

    private void initView(){
        recyclerState = findViewById(R.id.recyclerState);
        ToolbarUtil.setCommonToolbar(this,findViewById(R.id.toolbarPetState),getResources().getString(R.string.pet_state_title));
    }

    private void setRecyclerState(){
        PetStateLayoutManager manager = new PetStateLayoutManager(this);
        manager.setView(recyclerState);
        PetStateDivider divider = new PetStateDivider(this);
        divider.setView(recyclerState);
        adapter = new PetStateAdapter(this);
        adapter.setView(recyclerState);
        adapter.setDataAndRefresh(stateInfo, petInfo);
    }
}
