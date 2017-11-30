package kr.co.wooltari.custom;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
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

public class CameraGalleryPopup extends FrameLayout implements View.OnClickListener, PermissionUtil.IPermissionGrant {

    private TextView textCGPopupTitle;
    private Button btnCamera;
    private Button btnGallery;
    private Button btnDelete;
    private Activity activity;
    private IDelete iDelete;

    PopupType type = null;
    private PermissionUtil pUtil = null;
    // 저장된 파일의 경로를 가지는 컨텐츠 uri
    private Uri fileUri = null;

    public CameraGalleryPopup(@NonNull Context context, boolean isProfile, PopupType type, IDelete iDelete) {
        super(context);
        if(context instanceof Activity)
            activity = (Activity)context;
        else throw new RuntimeException(context.toString()  + " Context must instance of Activity");
        this.iDelete = iDelete;
        this.type = type;

        initView(isProfile);
        setTextTitle();
        setBtnListener();
    }

    public void initView(boolean isProfile) {
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_camera_gallery, null);
        textCGPopupTitle = popupView.findViewById(R.id.textCGPopupTitle);
        btnCamera = popupView.findViewById(R.id.btnCamera);
        btnGallery = popupView.findViewById(R.id.btnGallery);
        btnDelete = popupView.findViewById(R.id.btnDelete);

        if(!isProfile) btnDelete.setVisibility(GONE);
        addView(popupView);
    }

    /**
     * 타입에 따라 타이틀을 세팅
     */
    private void setTextTitle(){
        switch (type){
            case PET_MEDICAL:
                textCGPopupTitle.setText(getResources().getString(R.string.popup_camera_gallery_title_medical));
                break;
            default:
                textCGPopupTitle.setText(getResources().getString(R.string.popup_camera_gallery_title_profile));
                break;
        }
    }

    /**
     * 버튼에 대한 리스너 세팅
     */
    private void setBtnListener(){
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
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
            case R.id.btnDelete: loadBasicImageResource(); break;
        }
    }
    /**
     * 기본 이미지의 resourceId를 리턴
     */
    private void loadBasicImageResource(){
//        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
//                "://" + getContext().getResources().getResourcePackageName(R.drawable.pet_profile)
//                + '/' + getContext().getResources().getResourceTypeName(R.drawable.pet_profile)
//                + '/' + getContext().getResources().getResourceEntryName(R.drawable.pet_profile)
//        );
        switch (type){
            case PET_MEDICAL: iDelete.setBasicImage(R.drawable.ic_menu_slideshow); break;
            case PET_PROFILE: iDelete.setBasicImage(R.drawable.pet_profile); break;
            case USER_PROFILE: iDelete.setBasicImage(R.drawable.user_profile); break;
        }
        iDelete.deletePopup();
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
            fileUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",photoFile);
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
        MediaScannerConnection.scanFile(getContext(), new String[]{file.getAbsolutePath()}, null, (path, uri) -> {});
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
        iDelete.deletePopup();
    }
    @Override
    public void fail() {
        DialogUtil.showDialog(activity,
                getResources().getString(R.string.alert_permission_title),
                getResources().getString(R.string.alert_permission_msg),
                false);
        iDelete.deletePopup();
    }

    /**
     * 팝업의 타입 종류
     */
    public enum PopupType{
        USER_PROFILE, PET_PROFILE, PET_MEDICAL
    }

    public interface IDelete {
        void setBasicImage(int ResourceId);
        void deletePopup();
    }
}
