package kr.co.wooltari.user;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;

import kr.co.wooltari.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class MissingPasswordActivity extends AppCompatActivity {
    EditText input_email_edittext;
    TextView errormessage_textview;
    Button cancel_button;
    Button sendemail_button;
    private String URL="http://wooltari-test-server-dev.ap-northeast-2.elasticbeanstalk.com:80/";
    private static String TAG="MissingPasswordActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_password);
        init();
    }
    private void init(){
        input_email_edittext=findViewById(R.id.missingpasswordactivity_input_email_edittext);
        cancel_button=findViewById(R.id.missingpasswordactivity_cancel_buttoon);
        sendemail_button=findViewById(R.id.missingpasswordacitivy_sendEmail_button);
        errormessage_textview=findViewById(R.id.missingpasswordactivity_errormessage_textview);
    }

    public void onClick_Request_MissingPassword(View view) {
        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ISendEmailOnlyInfo retrofitService = retrofit.create(ISendEmailOnlyInfo.class);

                RequestEmailOnlyInfo requestEmailOnlyInfo=new RequestEmailOnlyInfo();
                String id=input_email_edittext.getText().toString();
                requestEmailOnlyInfo.setEmail(id);

                Call<SuccessMissingEmailInfo> call = retrofitService.Post(requestEmailOnlyInfo);
                call.enqueue(new Callback<SuccessMissingEmailInfo>() {

                    @Override
                    public void onResponse(Call<SuccessMissingEmailInfo> call, Response<SuccessMissingEmailInfo> response) {
                        Log.e(TAG+"0", response.isSuccessful()+"");
                        Log.e(TAG+"1",response.code()+" ");

                        String responseMessage="";
                        if(response.code()==200) {
                            responseMessage = response.body().getTo_email();
                            Intent intent = getIntent();
                            intent.putExtra("email", responseMessage);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else if(response.code()==400){
                            try {
                                responseMessage = response.errorBody().string();
                                Gson gson = new Gson();
                                MissingEmailRequestError error = gson.fromJson(responseMessage, MissingEmailRequestError.class);

                                if(responseMessage.contains("non_field_errors")){
                                    errormessage_textview.setText(error.getNon_field_errors()[0]);
                                    Log.e(TAG+"5", error.getNon_field_errors()[0]);
                                }else{
                                    errormessage_textview.setText(error.getEmail()[0]);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            errormessage_textview.setText("알수없는 에러가 발성하였습니다 앱을 종료하고 다시 시도해주세요");
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessMissingEmailInfo> call, Throwable t) {
                        Log.e(TAG, "error "+call.toString());
                    }
                });

            }
        });
    }

    public void onClick_missingPassword_back_button(View view) {
        // 뒤로간다
        finish();
    }
}
class MissingEmailRequestError {

    private String[] non_field_errors;
    private String[] email;

    public String[] getNon_field_errors() {
        return non_field_errors;
    }

    public void setNon_field_errors(String[] non_field_errors) {
        this.non_field_errors = non_field_errors;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MissingEmailRequestError{" +
                "non_field_errors=" + Arrays.toString(non_field_errors) +
                ", email=" + Arrays.toString(email) +
                '}';
    }
}

interface ISendEmailOnlyInfo {

    @Headers("Content-Type: application/json")
    @POST("/auth/reset-password/")
    public Call<SuccessMissingEmailInfo> Post(@Body RequestEmailOnlyInfo requestUserInfo);
}

class RequestEmailOnlyInfo
{
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class SuccessMissingEmailInfo
{
    private String message;
    private String to_email;
    private String[] email;
    private String[] non_field_errors;

    public String getMessage ()
    {
        return message;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String[] getNon_field_errors() {
        return non_field_errors;
    }

    public void setNon_field_errors(String[] non_field_errors) {
        this.non_field_errors = non_field_errors;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getTo_email ()
    {
        return to_email;
    }

    public void setTo_email (String to_email)
    {
        this.to_email = to_email;
    }

    @Override
    public String toString() {
        return "SuccessMissingEmailInfo{" +
                "message='" + message + '\'' +
                ", to_email='" + to_email + '\'' +
                ", email='" + email[0] + '\'' +
                ", non_field_errors='" + non_field_errors[0] + '\'' +
                '}';
    }
}