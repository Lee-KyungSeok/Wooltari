package kr.co.wooltari.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anya on 2017. 12. 12..
 */

public class VerificationUtil {

    public static boolean isValidPassword(String password) {
        String regex = "^[A-Za-z0-9]{8,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-z0-9A-Z_-]+(.[a-z0-9A-Z_-])*@([a-z0-9A-Z.])+.([a-zA-Z]){2,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidNickName(String str){
        String regex = "^[가-힣A-Za-z0-9]{2,12}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();

    }
}
