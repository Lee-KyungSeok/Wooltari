package kr.co.wooltari.util;

import android.app.Activity;
import android.app.AlertDialog;

import kr.co.wooltari.R;


/**
 * Created by Kyung on 2017-11-29.
 */

public class DialogUtil {
    public static void showDialog(final Activity activity, String title , String msg, final boolean activityFinish){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(activity.getString(R.string.alert_ok), (dialog, which) -> {
                    dialog.dismiss();
                    if(activityFinish){
                        activity.finish();
                    }
                });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
