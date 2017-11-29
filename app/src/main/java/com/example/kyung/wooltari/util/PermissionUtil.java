package com.example.kyung.wooltari.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-11-28.
 */

public class PermissionUtil {
    private final int REQ_CODE;
    private final String[] permissions;

    public PermissionUtil(int REQ_CODE, String[] permissions) {
        this.REQ_CODE = REQ_CODE;
        this.permissions = permissions;
    }

    /**
     * 앱의 버번 체크 후 퍼미션 요청
     * @param activity
     * @param permissionGrant
     */
    public void checkVersion(Activity activity, IPermissionGrant permissionGrant){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) request(activity,permissionGrant);
        else permissionGrant.success(REQ_CODE);
    }

    /**
     * 퍼미션 요청
     * @param activity
     * @param permissionGrant
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void request(Activity activity, IPermissionGrant permissionGrant){
        List<String> requirePermissions = new ArrayList<>();
        // 권한 승인 여부 확인
        for(String perm : permissions){
            if(activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED){
                requirePermissions.add(perm);
            }
        }
        if(requirePermissions.size()>0){
            String[] requirePermissionArray = new String[requirePermissions.size()];
            requirePermissionArray = requirePermissions.toArray(requirePermissionArray);
            activity.requestPermissions(requirePermissionArray,REQ_CODE);
        } else {
            permissionGrant.success(REQ_CODE);
        }
    }

    /**
     * 퍼미션 요청 결과 전달후 엑티비티 실행할지 결정
     * @param requestCode
     * @param grantResults
     * @param permissionGrant
     */
    public void onResult(int requestCode, int[] grantResults, IPermissionGrant permissionGrant){
        if(requestCode == REQ_CODE){
            boolean granted = true;
            for(int grant : grantResults){
                if(grant != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if(granted) permissionGrant.success(REQ_CODE);
            else permissionGrant.fail();
        }
    }

    public interface IPermissionGrant{
        void success(int requestCode);
        void fail();
    }
}
