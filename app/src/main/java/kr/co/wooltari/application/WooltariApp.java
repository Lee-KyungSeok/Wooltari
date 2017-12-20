package kr.co.wooltari.application;

import android.app.Application;

/**
 * Created by Kyung on 2017-12-18.
 */

public class WooltariApp extends Application {
    public static long userPK = -1;
    public static String userToken = null;
    public static String userName = null;
    public static String userEmail = null;
    public static String userImage = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
