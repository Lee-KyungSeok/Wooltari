package kr.co.wooltari;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SignUpActivity extends AppCompatActivity {
    EditText _id_editText;
    Button _id_button;
    TextView _id_errormassge_editText;

    EditText nickname_editText;
    Button nickname_button;
    TextView nickname_errormassge_editText;

    EditText password1_editText;
    TextView password1_errormassage;

    EditText password2_editText;
    TextView password2_errormassage;

    Button cancel_button;
    Button join_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

                            myConnection = (HttpsURLConnection) httpbinEndpoint.openConnection();
                            myConnection.setRequestMethod("POST");
                            String queryStringData = "email="+id+"&"+
                                    "nickname="+nickname+"&"+
                                    "password1="+password1+"&"+
                                    "password2="+password2;

                            myConnection.setDoOutput(true);
                            myConnection.getOutputStream().write(queryStringData.getBytes());

                            Log.e("myConnection",queryStringData);
                            if (myConnection.getResponseCode() == 201) {
                                // Success
                                Log.e("myConnection","connection sucess!!");
                                _id_editText.setText(myConnection.getResponseMessage().substring(500));
                            }else if (myConnection.getResponseCode()==400){
                                // Error
                                Log.e("myConnection",myConnection.getResponseCode()+" "+myConnection.getResponseMessage());
                            }else {
                                Log.e("myConnection",myConnection.getResponseCode()+" "+myConnection.getResponseMessage());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}

