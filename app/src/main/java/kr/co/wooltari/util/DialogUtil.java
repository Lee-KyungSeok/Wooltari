package kr.co.wooltari.util;

import android.app.Activity;
import android.app.AlertDialog;

import kr.co.wooltari.R;


/**
 * Created by Kyung on 2017-11-29.
 */

public class DialogUtil {
    // 단순히 다이얼로그를 이용하여 activity를 종료시키려는 경우
    public static AlertDialog showDialog(final Activity activity, String title , String msg, final boolean activityFinish){
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
        return dialog;
    }

    // 특정 효과를 주고싶은 경우
    public static AlertDialog showDialog(final Activity activity, String title , String msg, IDialogEvent iDialogEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(activity.getString(R.string.alert_ok), (dialog, which) -> {
                    dialog.dismiss();
                    iDialogEvent.activateAction();
                });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        return dialog;
    }

    public interface IDialogEvent{
        void activateAction();
    }
}
