package eu.bubu1.pushclient.registration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import eu.bubu1.pushclient.UnregisterAppService;
import eu.bubu1.pushclient.db.PushClientDatabase;
import eu.bubu1.pushclient.db.RegistrationDao;
import eu.bubu1.pushclient.db.entity.Registration;
import eu.bubu1.pushclient.utils.SaveSharedPreference;

import static android.content.Intent.ACTION_PACKAGE_DATA_CLEARED;
import static android.content.Intent.ACTION_PACKAGE_FULLY_REMOVED;
import static android.content.Intent.ACTION_PACKAGE_REMOVED;
import static android.content.Intent.EXTRA_DATA_REMOVED;
import static android.content.Intent.EXTRA_REPLACING;


public class UnregisterReceiver extends BroadcastReceiver {
    private static final String TAG = "PushUnregisterRcvr";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Package changed: " + intent);
        if ((ACTION_PACKAGE_REMOVED.contains(intent.getAction()) && intent.getBooleanExtra(EXTRA_DATA_REMOVED, false) &&
                !intent.getBooleanExtra(EXTRA_REPLACING, false)) ||
                ACTION_PACKAGE_FULLY_REMOVED.contains(intent.getAction()) ||
                ACTION_PACKAGE_DATA_CLEARED.contains(intent.getAction())) {
            final RegistrationDao dao = PushClientDatabase.getDatabase(context).registrationDao();
            final String packageName = intent.getData().getSchemeSpecificPart();
            Log.d(TAG, "Package removed or data cleared: " + packageName);
            new Thread(() -> {
                Registration registration = dao.findByPackageName(packageName);
                try {
                    new UnregisterAppService(SaveSharedPreference.getLoggedInServerUri(context), SaveSharedPreference.getClientToken(context))
                            .unregisterApp(registration.getRegistrationId()).execute();
                    dao.delete(registration);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}