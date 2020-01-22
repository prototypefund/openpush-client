package eu.bubu1.pushclient.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import eu.bubu1.pushclient.db.entity.Registration;

public class RegistrationItem {
    public Drawable icon;
    public String packageName;
    public CharSequence title;

    public RegistrationItem(String packageName, PackageManager pm) {
        this.packageName = packageName;
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(packageName, 0);
            this.title = pm.getApplicationLabel(appinfo);
            this.icon = pm.getApplicationIcon(appinfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public RegistrationItem(Registration registration, PackageManager pm) {
        this(registration.getPackageName(), pm);
    }
}
