package kr.co.wooltari.medicalcare.healthState;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.DialogUtil;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;


public class PetStateActivity extends AppCompatActivity implements PetStateProfileHolder.IPetStateShowDialog {

    private RecyclerView recyclerState;


    private PetStateAdapter adapter;
    private PetDummy.Dummy petInfo;
    private HealthStateDummy.StateDummy stateInfo;
    private AlertDialog petStateEditDialog;
    private TextView textPSEWeight, textPSEGoalWeight, textPSEHeight, textPSENeckSize, textPSEChestSize, textPetStateValid;
    private EditText editPSEWeight, editPSEGoalWeight, editPSEHeight, editPSENeckSize, editPSEChestSize;
    private Button btnPetStateCancel, btnPetStateSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_state);

        int pPk = getIntent().getIntExtra(Const.PET_ID, -1);
        petInfo = PetDummy.data.get(pPk);
        stateInfo = HealthStateDummy.stateData.get(pPk);

        initView();
        setRecyclerState();
        setCustomDialog();
    }

    private void initView() {
        recyclerState = findViewById(R.id.recyclerState);
        ToolbarUtil.setCommonToolbar(this, findViewById(R.id.toolbarPetState), getResources().getString(R.string.pet_state_title));
    }

    private void setRecyclerState() {
        PetStateLayoutManager manager = new PetStateLayoutManager(this);
        manager.setView(recyclerState);
        PetStateDivider divider = new PetStateDivider(this);
        divider.setView(recyclerState);
        adapter = new PetStateAdapter(this, petInfo);
        adapter.setView(recyclerState);
        adapter.setDataAndRefresh(stateInfo);
    }

    private void setCustomDialog() {
        View editDialog = LayoutInflater.from(this).inflate(R.layout.dialog_pet_state_edit, null);
        petStateEditDialog = DialogUtil.getCustomDialog(this, editDialog);
        initDialogView(editDialog);
        setTextViewColor(petInfo.body_color);
        resetPetState();
        dialogBtnListener();
        setEditTextChecking();
    }

    private void initDialogView(View editDialog){
        textPSEWeight = editDialog.findViewById(R.id.textPSEWeight);
        editPSEWeight = editDialog.findViewById(R.id.editPSEWeight);
        textPSEGoalWeight = editDialog.findViewById(R.id.textPSEGoalWeight);
        editPSEGoalWeight = editDialog.findViewById(R.id.editPSEGoalWeight);
        textPSEHeight = editDialog.findViewById(R.id.textPSEHeight);
        editPSEHeight = editDialog.findViewById(R.id.editPSEHeight);
        textPSENeckSize = editDialog.findViewById(R.id.textPSENeckSize);
        editPSENeckSize = editDialog.findViewById(R.id.editPSENeckSize);
        textPSEChestSize = editDialog.findViewById(R.id.textPSEChestSize);
        editPSEChestSize = editDialog.findViewById(R.id.editPSEChestSize);
        textPetStateValid = editDialog.findViewById(R.id.textPetStateValid);
        btnPetStateCancel = editDialog.findViewById(R.id.btnPetStateCancel);
        btnPetStateSubmit = editDialog.findViewById(R.id.btnPetStateSubmit);
    }

    private void setTextViewColor(String color){
        int petColor = LoadUtil.loadColor(this,color);
        textPSEWeight.setTextColor(petColor);
        textPSEGoalWeight.setTextColor(petColor);
        textPSEHeight.setTextColor(petColor);
        textPSENeckSize.setTextColor(petColor);
        textPSEChestSize.setTextColor(petColor);
    }

    private void dialogBtnListener(){
        btnPetStateCancel.setOnClickListener(v -> {
            petStateEditDialog.cancel();
            resetPetState();
        });
        btnPetStateSubmit.setOnClickListener(v-> savePetState() );
        btnPetStateSubmit.setEnabled(false);
    }

    private void setEditTextChecking(){
        Observable<TextViewTextChangeEvent> weightEmitter = RxTextView.textChangeEvents(editPSEWeight);
        Observable<TextViewTextChangeEvent> goalWeightEmitter = RxTextView.textChangeEvents(editPSEGoalWeight);
        Observable<TextViewTextChangeEvent> heightEmitter = RxTextView.textChangeEvents(editPSEHeight);
        Observable<TextViewTextChangeEvent> neckEmitter = RxTextView.textChangeEvents(editPSENeckSize);
        Observable<TextViewTextChangeEvent> chestEmitter = RxTextView.textChangeEvents(editPSEChestSize);
        Observable.combineLatest(
                weightEmitter, goalWeightEmitter, heightEmitter, neckEmitter, chestEmitter,
                (w, g, h, n, c) -> checkValid(w.text()) && checkValid(g.text()) && checkValid(h.text()) && checkValid(n.text()) && checkValid(c.text())
        ).subscribe(
                flag -> {
                    btnPetStateSubmit.setEnabled(flag);
                    if(flag) {
                        textPetStateValid.setText(getResources().getString(R.string.pet_state_save_valid));
                        textPetStateValid.setTextColor(LoadUtil.loadColor(this,"colorLittleBlack"));
                    } else {
                        textPetStateValid.setText(getResources().getString(R.string.pet_state_save_invalid));
                        textPetStateValid.setTextColor(LoadUtil.loadColor(this,"colorRed"));
                    }
                }
        );
    }
    private boolean checkValid(CharSequence text){
        if(text.length()<=0) return false;
        String regex = "^[0-9]+(\\.[0-9]+)*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    private void resetPetState(){
        editPSEWeight.setText(stateInfo.petNowWeight+"");
        editPSEGoalWeight.setText(stateInfo.petTargetWeight+"");
        editPSEHeight.setText(stateInfo.petHeight+"");
        editPSENeckSize.setText(stateInfo.petNeckSize+"");
        editPSEChestSize.setText(stateInfo.petChestSize+"");
    }

    private void savePetState(){
        // 저장하는 로직 서버에 저장되면 값을 불러오고
        // adapter.setDataAndRefresh(신체정보); 를 선언
    }

    @Override
    public void showEditDialog() {
        petStateEditDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ToolbarUtil.setMenuItemSelectedAction(this, item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Collections.reverse(stateInfo.petWeightList);
    }
}
