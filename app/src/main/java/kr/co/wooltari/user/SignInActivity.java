package kr.co.wooltari.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;

import kr.co.wooltari.main.MainActivity;
import kr.co.wooltari.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Log.e("refreshedToken", "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());

    }

    public void onClick_Login(View view){
        startActivity(new Intent(this,MainActivity.class));
    }

    public void onClick_Signup(View view){
        startActivity(new Intent(this,SignUpActivity.class));
    }

    public void onClick_MiggingPassword(View view){ startActivity(new Intent(this,MissingPasswordActivity.class));}
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Pet")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
