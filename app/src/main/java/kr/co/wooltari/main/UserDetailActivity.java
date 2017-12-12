package kr.co.wooltari.main;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.CameraGalleryPopup;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.LoadUtil;

public class UserDetailActivity extends AppCompatActivity{
    ImageView UserimageImageview;
    Button ModifyUserInfoButton;
    TextView EmailTextView;
    EditText NickNameEdittext;
    GradientDrawable gradientDrawable;

    // 카메라&갤러리 팝업, isImage는 프로필이 지정되어 있는지 체크
    CameraGalleryPopup cameraGalleryPopup = null;
    boolean isImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        init();
    }
    private void init(){
        UserimageImageview=findViewById(R.id.userdetail_userimage_imageview);
        ModifyUserInfoButton= findViewById(R.id.userdetail_modify_UserInfo_button);
        EmailTextView=findViewById(R.id.userdetail_email_textview);
        NickNameEdittext =findViewById(R.id.userdetail_nickname_edittext);

        LoadUtil.circleImageLoad(this, PetDummy.data.get(1).pProfile, UserimageImageview);
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        UserimageImageview.setBackground(gradientDrawable);

        cameraGalleryPopup = new CameraGalleryPopup(this, CameraGalleryPopup.PopupType.USER_PROFILE, basicProfileUri -> {
            LoadUtil.circleImageLoad(this,basicProfileUri,UserimageImageview);
            isImage = false;
        });
    }



    public void modifyUserInfo(View view) {
        boolean flag=NickNameEdittext.isEnabled();
        if(flag){
            NickNameEdittext.setEnabled(false);
        }else{
            NickNameEdittext.setEnabled(true);
        }
    }

    public void DeleteUser(View view){
        Log.e("DeleteUser","DeleteUser");
    }

    public void changeUserImage(View view) {
        cameraGalleryPopup.show();
        cameraGalleryPopup.setBtnList(isImage);
    }

    // 퍼미션 체크
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Const.PERMISSION_REQ_CAMERA==requestCode || Const.PERMISSION_REQ_GALLERY == requestCode)
            cameraGalleryPopup.checkPermResult(requestCode,grantResults);
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 이미지를 얻어온 결과를 다시 자리에 세팅
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri = cameraGalleryPopup.getImage(requestCode,resultCode,data);
        if(imageUri!=null) {
            LoadUtil.circleImageLoad(this, imageUri, UserimageImageview);
            isImage = true;
        }
    }
}
