package eu.bubu1.pushclient.utils;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class PackageUtils {

    public static String packageFromPendingIntent(PendingIntent pi) {
        if (pi == null) return null;
        return pi.getCreatorPackage();
    }

    public static void checkPackageUid(Context context, String packageName, int callingUid) {
        getAndCheckPackage(context, packageName, callingUid, 0);
    }

    @Nullable
    public static String getAndCheckPackage(Context context, String suggestedPackageName, int callingUid, int callingPid) {
        String packageName = packageFromProcessId(context, callingPid);
        if (packageName == null) {
            String[] packagesForUid = context.getPackageManager().getPackagesForUid(callingUid);
            if (packagesForUid != null && packagesForUid.length != 0) {
                if (packagesForUid.length == 1) {
                    packageName = packagesForUid[0];
                } else if (Arrays.asList(packagesForUid).contains(suggestedPackageName)) {
                    packageName = suggestedPackageName;
                } else {
                    packageName = packagesForUid[0];
                }
            }
        }
        if (packageName != null && suggestedPackageName != null && !packageName.equals(suggestedPackageName)) {
            throw new SecurityException("UID [" + callingUid + "] is not related to packageName [" + suggestedPackageName + "] (seems to be " + packageName + ")");
        }
        return packageName;
    }

    @Nullable
    public static String packageFromProcessId(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) return null;
        if (pid <= 0) return null;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses) {
                if (processInfo.pid == pid) return processInfo.processName;
            }
        }
        return null;
    }
}
