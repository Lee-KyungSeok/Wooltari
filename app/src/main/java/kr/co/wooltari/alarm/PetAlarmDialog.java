package kr.co.wooltari.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.util.DialogUtil;

/**
 * Created by Kyung on 2017-12-11.
 */

public class PetAlarmDialog {

    private AlertDialog petAlarmDialog;

    private TextView textAlarmPetValue;
    private TextView textAlarmDateValue;
    private Spinner spinnerAlarmHour;
    private Spinner spinnerAlarmMin;
    private TextView textAlarmContentValue;
    private Spinner spinnerAlarmSound;
    private Switch switchAlarm;
    private Button btnAlarmDelete;
    private Button btnAlarmSave;

    private Activity activity;
    private IChange callback;
    private String type;

    public PetAlarmDialog(Activity activity, String petName, String date, String content, IChange callback){
        this.activity = activity;
        this.callback = callback;
        type = Const.ALARM_OFF;

        initView();
        setTextView(petName, date, content);
        initButtonAndSwitch();
        initSpinner();
    }

    private void initView(){
        View alarmView = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.dialog_pet_alarm,null);
        textAlarmPetValue = alarmView.findViewById(R.id.textAlarmPetValue);
        textAlarmDateValue = alarmView.findViewById(R.id.textAlarmDateValue);
        spinnerAlarmHour = alarmView.findViewById(R.id.spinnerAlarmHour);
        spinnerAlarmMin = alarmView.findViewById(R.id.spinnerAlarmMin);
        textAlarmContentValue = alarmView.findViewById(R.id.textAlarmContentValue);
        spinnerAlarmSound = alarmView.findViewById(R.id.spinnerAlarmSound);
        switchAlarm = alarmView.findViewById(R.id.switchAlarm);
        btnAlarmDelete = alarmView.findViewById(R.id.btnAlarmDelete);
        btnAlarmSave = alarmView.findViewById(R.id.btnAlarmSave);

        petAlarmDialog = DialogUtil.getCustomDialog(activity,alarmView);
    }

    private void setTextView(String petName, String date, String content){
        textAlarmPetValue.setText(petName);
        textAlarmDateValue.setText(date);
        textAlarmContentValue.setText(content);
    }

    /**
     * 버튼 및 스위치를 세팅
     */
    private void initButtonAndSwitch(){
        btnAlarmSave.setOnClickListener(v -> save());
        btnAlarmDelete.setOnClickListener(v -> delete());

        switchAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) type = Const.ALARM_ON;
            else type = Const.ALARM_OFF;
        });
    }

    /**
     * 스피너 세팅
     */
    private void initSpinner(){
        List<String> hourSpinList = new ArrayList<>();
        for(int i=0 ; i<24 ; i++){
            if(i<10) hourSpinList.add(0+""+i);
            else hourSpinList.add(i+"");
        }
        spinnerAlarmHour.setAdapter(createAdapter(hourSpinList, activity.getResources().getString(R.string.alarm_dialog_hour_hint)));

        List<String> minSpinList = new ArrayList<>();
        for(int i=0 ; i<59 ; i++){
            if(i<10) minSpinList.add(0+""+i);
            else minSpinList.add(i+"");
        }
        spinnerAlarmMin.setAdapter(createAdapter(minSpinList, activity.getResources().getString(R.string.alarm_dialog_min_hint)));

        List<String> soundList = new ArrayList<>(Arrays.asList(activity.getResources().getStringArray(R.array.alarm_pet_sound)));
        spinnerAlarmSound.setAdapter(createAdapter(soundList,activity.getResources().getString(R.string.alarm_dialog_sound_hint)));
    }

    /**
     * 아답터 생성
     */
    private ArrayAdapter createAdapter(List<String> data, String hint){
        data.add(0,hint);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getBaseContext(),android.R.layout.simple_expandable_list_item_1,data){
            @Override
            public boolean isEnabled(int position) {
                return position!=0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position==0){
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }

    public void show(String petName, String date, String content){
        setTextView(petName, date, content);
        petAlarmDialog.show();
    }

    public void cancel(){
        petAlarmDialog.cancel();
    }
    public void dismiss(){
        petAlarmDialog.dismiss();
    }

    private void save(){
        // Todo 저장하는 로직이 작성되어야 함

        // 저장 완료되는 경우만 아래를 실행
        petAlarmDialog.cancel();
        callback.changeIcon(type);
    }

    private void delete(){
        // Todo 삭제하는 로직이 작성되어야 함

        // 삭제 완료되는 경우만 아래를 실행
        petAlarmDialog.cancel();
        switchAlarm.setChecked(false);
        type = Const.ALARM_OFF;
        callback.changeIcon(type);
    }

    private void setValue(){
        // Todo 서버에서 데이터를 가져온 후 값을 세팅 혹은 세팅하지 않음
    }

    private void setDefaultAlarm(){

    }

    private void checkValid(){

    }

    /**
     * 타입에 따라 알람 아이콘을 변경하도록 하는 콜백함수 지정
     */
    public interface IChange{
        void changeIcon(String type);
    }

}
