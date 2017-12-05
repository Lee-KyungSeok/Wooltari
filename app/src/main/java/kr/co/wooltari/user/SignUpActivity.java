package kr.co.wooltari.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import kr.co.wooltari.BuildConfig;
import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.LoadUtil;

public class SignUpActivity extends AppCompatActivity {
    // 위젯들
    private ImageView UserProfileImageview;

    private EditText _id_editText;
    private Button _id_button;
    private TextView _id_errormassge_editText;

    private EditText nickname_editText;
    private Button nickname_button;
    private TextView nickname_errormassge_editText;

    private EditText password1_editText;
    private TextView password1_errormassage;

    private EditText password2_editText;
    private TextView password2_errormassage;

    private Button cancel_button;
    private Button join_button;

    // 카메라로 저장된 파일 경로
    private Uri fileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        initListener();
    }

    private void init(){
//        profile 필드
        // TODO 지금은 url 이지만 나중에는 기본 이미지따다가 그걸로 바꿀것
        UserProfileImageview=findViewById(R.id.user_profile_imageview);
        LoadUtil.circleImageLoad(this, PetDummy.data.get(1).pProfile, UserProfileImageview);

//        id 필드
        _id_editText=findViewById(R.id.id_editText);
        _id_button=findViewById(R.id.id_button);
        _id_errormassge_editText=findViewById(R.id.id_textView_errorMassage);

//        nickname 필드
        nickname_editText=findViewById(R.id.nickname_editText);
        nickname_button=findViewById(R.id.nickname_button);
        nickname_errormassge_editText=findViewById(R.id.nickname_textView_errormassage);

//        password1 필드
        password1_editText=findViewById(R.id.password1_editText);
        password1_errormassage=findViewById(R.id.password1_textView_errormassage);

//        password2 필드
        password2_editText=findViewById(R.id.password2_editText);
        password2_errormassage=findViewById(R.id.password2_textView_errormassage);

//            취소, 가입버튼
        cancel_button=findViewById(R.id.cancel_button);
        join_button=findViewById(R.id.ok_button);

    }
    public void onClick_set_image(View view){
        //TODO: 흩어져 있는 alertDialog 를 하나로 합하기
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("카메라 선택")
                        .setMessage("카메라? 앨범?")
                        .setPositiveButton("앨범", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                              //TODO: 흩어져 있는 alertDialog 를 하나로 합하기
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent,Const.PERMISSION_REQ_GALLERY);
                            }
                        })
                        .setNeutralButton("카메라", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // 카메라로 찍어서 임시 저장 URL 에 넣고 적용한다
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                    File photoFile = null;
                                    try {
                                        photoFile = createTempFile();
                                        refreshMedia(photoFile);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    fileUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider",photoFile);
                                    Log.e("fileUri============", fileUri.toString());
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                    startActivityForResult(intent,Const.PERMISSION_REQ_CAMERA);
                                } else {
                                    startActivityForResult(intent,Const.PERMISSION_REQ_CAMERA);
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        })
                        .show();

    }

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
    private void refreshMedia(File file){
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.getAbsolutePath()}, null, (path, uri) -> {});
    }

    // 이미지를 얻어온 결과를 다시 자리에 세팅
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK){
            return;
        }
        Uri imagepath = null;

        switch (requestCode){
            case Const.PERMISSION_REQ_GALLERY:
                imagepath=(Uri)data.getData();
                LoadUtil.circleImageLoad(this, imagepath, UserProfileImageview);
                break;
            case Const.PERMISSION_REQ_CAMERA:
                LoadUtil.circleImageLoad(this, fileUri, UserProfileImageview);
                break;
        }
    }

    private void initListener(){
//          이미지 뷰 리스너 달기
//        UserProfileImageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO: 흩어져 있는 alertDialog 를 하나로 합하기
//
//            }
//        });

//          버튼 리스너 달기
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로 간다
                finish();
            }
        });

        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                가입한다
            }
        });

        _id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        nickname_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname=nickname_editText.getText().toString();
            }
        });

        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=_id_editText.getText().toString();
                String nickname=nickname_editText.getText().toString();
                String password1=password1_editText.getText().toString();
                String password2=password2_editText.getText().toString();

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            URL httpbinEndpoint = new URL("https://wooltari.co.kr/auth/signup/");
                            HttpsURLConnection myConnection;

//                            myConnection = (HttpsURLConnection) httpbinEndpoint.openConnection();
//                            myConnection.setRequestMethod("POST");
//                            String queryStringData = "email="+id+"&"+
//                                    "nickname="+nickname+"&"+
//                                    "password1="+password1+"&"+
//                                    "password2="+password2;
//
//                            myConnection.setDoOutput(true);
//                            myConnection.getOutputStream().write(queryStringData.getBytes());
//
//                            Log.e("myConnection",queryStringData);
//                            if (myConnection.getResponseCode() == 201) {
//                                // Success
//                                Log.e("myConnection","connection sucess!!");
//                                _id_editText.setText(myConnection.getResponseMessage().substring(500));
//                            }else if (myConnection.getResponseCode()==400){
//                                // Error
//                                Log.e("myConnection",myConnection.getResponseCode()+" "+myConnection.getResponseMessage());
//                            }else {
//                                Log.e("myConnection",myConnection.getResponseCode()+" "+myConnection.getResponseMessage());
//                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

}
