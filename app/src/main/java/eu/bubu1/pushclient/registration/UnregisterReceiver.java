/*
 * Copyright (C) 2018 microG Project Team
 * Copyright (C) 2020 Marcus Hoffmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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