package kr.co.wooltari.custom;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import kr.co.wooltari.BuildConfig;
import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.util.DialogUtil;
import kr.co.wooltari.util.PermissionUtil;

/**
 * Created by Kyung on 2017-11-28.
 */

public class CameraGalleryPopup implements View.OnClickListener, PermissionUtil.IPermissionGrant {

    private View popupView;
    private AlertDialog dialog;
    private TextView textCGPopupTitle;
    private Button btnCamera;
    private Button btnGallery;
    private Button btnCGDelete;
    private Activity activity;
    private Context context;
    private IDelete iDelete;

    PopupType type = null;
    private PermissionUtil pUtil = null;
    // 저장된 파일의 경로를 가지는 컨텐츠 uri
    private Uri fileUri = null;

    public CameraGalleryPopup(@NonNull Context context, PopupType type, IDelete iDelete) {
        this.context = context;
        if(context instanceof Activity)
            activity = (Activity)context;
        else throw new RuntimeException(context.toString()  + " Context must instance of Activity");
        this.iDelete = iDelete;
        this.type = type;

        initView();
        setTextTitle();
        setBtnListener();
    }

    private void initView() {
        popupView = LayoutInflater.from(context).inflate(R.layout.popup_camera_gallery, null);
        dialog = DialogUtil.getCustomDialog(activity,popupView);
        textCGPopupTitle = popupView.findViewById(R.id.textCGPopupTitle);
        btnCamera = popupView.findViewById(R.id.btnCamera);
        btnGallery = popupView.findViewById(R.id.btnGallery);
        btnCGDelete = popupView.findViewById(R.id.btnCGDelete);
    }

//    /**
//     * 팝업 나타나는 위치 지정
//     */
//    private void setLocation(){
//        FrameLayout.LayoutParams layoutParams
//                = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
//        setLayoutParams(layoutParams);
//    }

    public void show(){
        dialog.show();
    }
    public void cancel(){
        dialog.cancel();
    }
    public void dismiss(){
        dialog.dismiss();
    }

    /**
     * 프로필 유무에 따라 delete 버튼 생성 및 삭제
     * @param isProfile
     */
    public void setBtnList(boolean isProfile){
        if(isProfile) btnCGDelete.setVisibility(popupView.VISIBLE);
        else btnCGDelete.setVisibility(popupView.GONE);
    }

    /**
     * 타입에 따라 타이틀을 세팅
     */
    private void setTextTitle(){
        switch (type){
            case PET_MEDICAL: textCGPopupTitle.setText(context.getResources().getString(R.string.popup_camera_gallery_title_medical)); break;
            default: textCGPopupTitle.setText(context.getResources().getString(R.string.popup_camera_gallery_title_profile)); break;
        }
    }

    /**
     * 버튼에 대한 리스너 세팅
     */
    private void setBtnListener(){
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnCGDelete.setOnClickListener(this);
    }
    /**
     * 버튼 클릭 메소드
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCamera: checkCameraPermission(); break;
            case R.id.btnGallery: checkGalleryPermission(); break;
            case R.id.btnCGDelete: loadBasicImageResource(); break;
        }
    }
    /**
     * 기본 이미지의 resourceId를 리턴
     */
    private void loadBasicImageResource(){
        int resId = 0;
        switch (type){
            case PET_MEDICAL: resId = R.drawable.ic_menu_slideshow; break;
            case PET_PROFILE: resId = R.drawable.pet_profile; break;
            case USER_PROFILE: resId = R.drawable.user_profile; break;
        }
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(resId)
                + '/' + context.getResources().getResourceTypeName(resId)
                + '/' + context.getResources().getResourceEntryName(resId)
        );
        iDelete.setBasicImage(imageUri);
        dialog.cancel();
    }

    /**
     * 카메라에 대한 퍼미션 체크
     */
    private void checkCameraPermission(){
        String[] permission = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        pUtil = new PermissionUtil(Const.PERMISSION_REQ_CAMERA, permission);
        pUtil.checkVersion(activity, this);
    }
    /**
     * 카메라 앱 실행 및 파일 로드
     */
    private void goCamera(){
        // 1. Intent 만들기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 2. 호환성 처리 버전체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 3. 실제 파일이 저장되는 파일 객체 (빈 파일을 생성)
            File photoFile = null;
            // 3.1 실제 파일이 저장되는 곳에 권한이 부여(file provider) > manifest
            // 3.2 임시파일 생성 후 인텐트로 넘김
            try {
                photoFile = createTempFile();
                refreshMedia(photoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            activity.startActivityForResult(intent,Const.PERMISSION_REQ_CAMERA);
        } else {
            activity.startActivityForResult(intent,Const.PERMISSION_REQ_CAMERA);
        }
    }
    /**
     * 이미지를 저장하기 위해 쓰기 권한이 있는 빈 파일을 생성해두는 함수
     * @return
     */
    private File createTempFile() throws IOException{
        // 임시파일명 생성
        String tempFileName = "Temp_" + System.currentTimeMillis();
        // 임시파일 저장용 디렉토리 설정
        File tempDir = new File(Environment.getExternalStorageDirectory() + File.separator + "tempPicture" + File.separator);
        // 생성 체크
        if(!tempDir.exists()) tempDir.mkdirs();
        // 실제 임시파일을 생성
        File tempFile = File.createTempFile(tempFileName, ".jpg", tempDir);
        return tempFile;
    }
    /**
     * 미디어 파일 갱신 (갤러리에 나오지 않을때 호출할 것)
     * @param file
     */
    private void refreshMedia(File file){
        MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, (path, uri) -> {});
    }

    /**
     * 갤러리에 대한 퍼미션 체크
     */
    private void checkGalleryPermission(){
        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};

        pUtil = new PermissionUtil(Const.PERMISSION_REQ_GALLERY, permission);
        pUtil.checkVersion(activity, this);
    }
    /**
     * 갤러리 앱 실행
     */
    private void goGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent,Const.PERMISSION_REQ_GALLERY);
    }

    /**
     * request 결과를 받아오는 메소드
     * onRequestPermissionsResult 에 설정해야 함
     * @param requestCode
     * @param grantResults
     */
    public void checkPermResult(int requestCode, int[] grantResults){
        pUtil.onResult(requestCode, grantResults, this );
    }

    /**
     * intent 후 결과를 세팅하도록 함
     *   - Activity의 onActivityResult에서 설정
     */
    public Uri getImage(int requestCode, int resultCode, Intent data){
        Uri imageUri = null;
        switch (requestCode){
            case Const.PERMISSION_REQ_CAMERA:
                if(-1 == resultCode){
                    // 롤리팝부터 버전 체크
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) imageUri = fileUri;
                    else imageUri = data.getData();
                }
                break;
            case Const.PERMISSION_REQ_GALLERY:
                // 갤러리 액티비티 종료시 호출 - 정상종료 된 경우만 이미지 설정 (-1은 Result_OK 값)
                if( -1 == resultCode) imageUri = data.getData();
                break;
        }
        return imageUri;
    }

    /**
     * 퍼미션 승낙 혹은 거절시 수행되는 메소드
     * @param requestCode
     */
    @Override
    public void success(int requestCode) {
        switch (requestCode){
            case Const.PERMISSION_REQ_CAMERA: goCamera(); break;
            case Const.PERMISSION_REQ_GALLERY: goGallery(); break;
        }
        dialog.cancel();
    }
    @Override
    public void fail() {
        DialogUtil.showDialog(activity,
                context.getResources().getString(R.string.alert_permission_title),
                context.getResources().getString(R.string.alert_permission_msg),
                false);
        dialog.cancel();
    }

    /**
     * 팝업의 타입 종류
     */
    public enum PopupType{
        USER_PROFILE, PET_PROFILE, PET_MEDICAL
    }

    public interface IDelete {
        void setBasicImage(Uri basicProfileUri);
    }
}
