package kr.co.wooltari.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import kr.co.wooltari.R;


public class MissingPasswordActivity extends AppCompatActivity {
    EditText input_email_edittext;
    Button cancel_button;
    Button sendemail_button;
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
}
