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

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import eu.bubu1.pushclient.RegisterAppService;
import eu.bubu1.pushclient.UnregisterAppService;
import eu.bubu1.pushclient.apimodels.Application;
import eu.bubu1.pushclient.db.PushClientDatabase;
import eu.bubu1.pushclient.db.RegistrationDao;
import eu.bubu1.pushclient.db.entity.Registration;
import eu.bubu1.pushclient.utils.PackageUtils;
import eu.bubu1.pushclient.utils.SaveSharedPreference;
import eu.bubu1.pushclient.utils.TokenGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eu.bubu1.pushclient.registration.Constants.ACTION_PUSH_REGISTER;
import static eu.bubu1.pushclient.registration.Constants.ACTION_PUSH_REGISTRATION;
import static eu.bubu1.pushclient.registration.Constants.ACTION_PUSH_UNREGISTER;
import static eu.bubu1.pushclient.registration.Constants.ERROR_SERVICE_NOT_AVAILABLE;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_APP;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_ERROR;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_REGISTRATION_ID;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_UNREGISTERED;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PushRegisterService extends JobIntentService {
    private static final String TAG = "PushRegisterSvc";

    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 1;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, PushRegisterService.class, JOB_ID, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onHandleWork(@NotNull Intent intent) {
        Log.d(TAG, "onHandleWork: " + intent);
        try {
            if (ACTION_PUSH_UNREGISTER.equals(intent.getAction())) {
                unregister(intent);
            } else if (ACTION_PUSH_REGISTER.equals(intent.getAction())) {
                register(intent);
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
    }

    private void register(final Intent intent) {
        PendingIntent pendingIntent = intent.getParcelableExtra(EXTRA_APP);
        final String packageName = PackageUtils.packageFromPendingIntent(pendingIntent);

        final RegistrationDao dao = PushClientDatabase.getDatabase(this).registrationDao();
        registerAndReply(this, dao, intent, packageName);
    }

    private void unregister(Intent intent) {
        PendingIntent pendingIntent = intent.getParcelableExtra(EXTRA_APP);
        String packageName = PackageUtils.packageFromPendingIntent(pendingIntent);
        RegistrationDao dao = PushClientDatabase.getDatabase(this).registrationDao();
        Context context = this;
        Log.d(TAG, "unregister[req]: " + intent.toString() + " extras=" + intent.getExtras());
        new UnregisterAppService(SaveSharedPreference.getLoggedInServerUri(this),
                SaveSharedPreference.getClientToken(this))
                .unregisterApp(dao.findByPackageName(packageName).getRegistrationId())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            dao.delete(packageName);
                            Intent outIntent = new Intent(ACTION_PUSH_REGISTRATION);
                            Bundle resultBundle = new Bundle();
                            resultBundle.putString(EXTRA_UNREGISTERED, packageName);
                            outIntent.putExtras(resultBundle);
                            Log.d(TAG, "unregister[res]: " + outIntent.toString() + " extras=" + outIntent.getExtras());
                            sendReply(context, packageName, outIntent);
                        } else {
                            Intent outIntent = new Intent(ACTION_PUSH_REGISTRATION);
                            Bundle resultBundle = new Bundle();
                            resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                            outIntent.putExtras(resultBundle);
                            sendReply(context, packageName, outIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                        Intent outIntent = new Intent(ACTION_PUSH_REGISTRATION);
                        Bundle resultBundle = new Bundle();
                        resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                        outIntent.putExtras(resultBundle);
                        sendReply(context, packageName, outIntent);
                    }
                });
    }


    public static void registerAndReply(Context context, RegistrationDao dao, Intent intent, String packageName) {
        Log.d(TAG, "register[req]: " + intent.toString() + " extras=" + intent.getExtras());
        Registration registration = new Registration();
        registration.setRegistrationId(TokenGenerator.getToken(20));
        registration.setPackageName(packageName);
        new RegisterAppService(SaveSharedPreference.getLoggedInServerUri(context),
                SaveSharedPreference.getClientToken(context)).registerApp(registration.getRegistrationId())
                .enqueue(new Callback<Application>() {
                    @Override
                    public void onResponse(Call<Application> call, Response<Application> response) {
                        if (response.isSuccessful()) {
                            dao.insert(registration);
                            Intent outIntent = new Intent(ACTION_PUSH_REGISTRATION);
                            Bundle resultBundle = new Bundle();
                            resultBundle.putString(EXTRA_REGISTRATION_ID, response.body().getRoutingToken());
                            outIntent.putExtras(resultBundle);
                            Log.d(TAG, "register[res]: " + outIntent.toString() + " extras=" + outIntent.getExtras());
                            sendReply(context, packageName, outIntent);
                        } else {
                            Intent outIntent = new Intent(ACTION_PUSH_REGISTRATION);
                            Bundle resultBundle = new Bundle();
                            resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                            outIntent.putExtras(resultBundle);
                            sendReply(context, packageName, outIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Application> call, Throwable t) {
                        t.printStackTrace();
                        Intent outIntent = new Intent(ACTION_PUSH_REGISTRATION);
                        Bundle resultBundle = new Bundle();
                        resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                        outIntent.putExtras(resultBundle);
                        sendReply(context, packageName, outIntent);
                    }
                });
    }


    private static void sendReply(Context context, String packageName, Intent outIntent) {
        outIntent.setPackage(packageName);
        context.sendOrderedBroadcast(outIntent, null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + intent.toString());
        if (ACTION_PUSH_REGISTER.equals(intent.getAction())) {
            Messenger messenger = new Messenger(new PushRegisterHandler(this));
            return messenger.getBinder();
        }
        return super.onBind(intent);
    }
}
