package kr.co.wooltari.user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
//                        Log.e(TAG+"0", response.isSuccessful()+"");
//                        Log.e(TAG+"1",call.toString());
//                        Log.e(TAG+"2",response.toString());
//                        Log.e(TAG+"3", response.raw().toString());
//                        Log.e(TAG+"4", response.message().toString());
//                        try {
//                            Log.e(TAG+"5", response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Log.e(TAG+"5", response.body().getEmail()[0]);
//                        Log.e(TAG+"6", response.body().getNon_field_errors()[0]);
//                        Log.e(TAG+"7", response.body().getMessage());
                        try {
                        String errorString=response.errorBody().string();
                        Log.e("TAG1", errorString);
                            Gson gson = new Gson();
//                            Type type = new TypeToken<List<EmailNoFoundError>>() {}.getType();
//                            Type type = new TypeToken<List<EmailNoFoundError>>() {}.getType();
                            EmailNoFoundError error = gson.fromJson(errorString, EmailNoFoundError.class);
                            Log.e("TAG2", error.toString());
//                            List<EmailNoFoundError> fromJson = gson.fromJson(json, type);

//                            for (Task task : fromJson) {
//                                System.out.println(task);
//                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        if(response.code()==200 & response.body().getMessage().equals("Your temporary password has been emailed.")) {
//                            String responseMessage = response.body().getTo_email();
//                            Intent intent = getIntent();
//                            intent.putExtra("email", responseMessage);
//                            setResult(RESULT_OK, intent);
//                            finish();
//                        }else if(response.code()==400){
//                            String responseMessage = response.body().toString();
//                            Log.e(TAG, responseMessage);
//                        }else{
//                            String responseMessage = response.body().toString();
//                            Log.e(TAG, responseMessage);
//                        }
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
class EmailNoFoundError {

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
        return "EmailNoFoundError{" +
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

// TODO: 스펠링 바꿀것
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


