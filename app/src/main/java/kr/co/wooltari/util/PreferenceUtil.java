package kr.co.wooltari.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kyung on 2017-11-28.
 */

public class PreferenceUtil {
    private static final String filename = "Wooltari";

    // 1. SharedPreference 를 생성
    private static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    public static void setValue(Context context, String key, String value){
        // 2. 쓰기를 위한 editor 생성
        SharedPreferences.Editor editor = getPreference(context).edit();
        // 3. 키, 값 형태로 값을 저장
        editor.putString(key, value);
        // commit : 동기로 작동, apply : 비동기로 작동
        editor.apply();
    }

    public static void setValue(Context context, String key, Boolean value){
        // 2. 쓰기를 위한 editor 생성
        SharedPreferences.Editor editor = getPreference(context).edit();
        // 3. 키, 값 형태로 값을 저장
        editor.putBoolean(key, value);
        editor.commit();
    }

    // 값 가져오기
    public static String getString(Context context, String key){
        return getPreference(context).getString(key,"");
    }

    public static boolean getBoolean(Context context, String key){
        return getPreference(context).getBoolean(key,false);
    }
}
