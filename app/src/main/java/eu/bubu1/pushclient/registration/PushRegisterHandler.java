package eu.bubu1.pushclient.registration;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

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

import static eu.bubu1.pushclient.registration.Constants.ACTION_PUSH_REGISTRATION;
import static eu.bubu1.pushclient.registration.Constants.ERROR_SERVICE_NOT_AVAILABLE;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_APP;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_ERROR;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_REGISTRATION_ID;
import static eu.bubu1.pushclient.registration.Constants.EXTRA_UNREGISTERED;

class PushRegisterHandler extends Handler {
    private static final String TAG = "PushRegisterHdl";

    private Context context;
    private int callingUid;
    private RegistrationDao dao;


    public PushRegisterHandler(Context context) {
        this.context = context;
        this.dao = PushClientDatabase.getDatabase(context).registrationDao();
    }

    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        this.callingUid = Binder.getCallingUid();
        return super.sendMessageAtTime(msg, uptimeMillis);
    }

    private void sendReply(int what, int id, Messenger replyTo, Bundle data) {
        if (what == 0) {
            Intent outIntent = new Intent(ACTION_PUSH_REGISTRATION);
            outIntent.putExtras(data);
            Message message = Message.obtain();
            message.obj = outIntent;
            try {
                replyTo.send(message);
            } catch (RemoteException e) {
                Log.w(TAG, e);
            }
        } else {
            Bundle messageData = new Bundle();
            messageData.putBundle("data", data);
            Message response = Message.obtain();
            response.what = what;
            response.arg1 = id;
            response.setData(messageData);
            try {
                replyTo.send(response);
            } catch (RemoteException e) {
                Log.w(TAG, e);
            }
        }
    }

    private void replyError(int what, int id, Messenger replyTo, String errorMessage) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ERROR, errorMessage);
        sendReply(what, id, replyTo, bundle);
    }

    private void replyNotAvailable(int what, int id, Messenger replyTo) {
        replyError(what, id, replyTo, ERROR_SERVICE_NOT_AVAILABLE);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == 0) {
            if (msg.obj instanceof Intent) {
                Message nuMsg = Message.obtain();
                nuMsg.what = msg.what;
                nuMsg.arg1 = 0;
                nuMsg.replyTo = null;
                PendingIntent pendingIntent = ((Intent) msg.obj).getParcelableExtra(EXTRA_APP);
                String packageName = PackageUtils.packageFromPendingIntent(pendingIntent);
                Bundle data = new Bundle();
                data.putBoolean("oneWay", false);
                data.putString("pkg", packageName);
                data.putBundle("data", msg.getData());
                nuMsg.setData(data);
                msg = nuMsg;
            } else {
                return;
            }
        }

        int what = msg.what;
        int id = msg.arg1;
        Messenger replyTo = msg.replyTo;
        if (replyTo == null) {
            Log.w(TAG, "replyTo is null");
            return;
        }
        Bundle data = msg.getData();
        if (data.getBoolean("oneWay", false)) {
            Log.w(TAG, "oneWay requested");
            return;
        }

        String packageName = data.getString("pkg");
        Bundle subdata = data.getBundle("data");
        String sender = subdata.getString("sender");
        boolean delete = subdata.get("delete") != null;

        try {
            PackageUtils.checkPackageUid(context, packageName, callingUid);
        } catch (SecurityException e) {
            Log.w(TAG, e);
            return;
        }

        if (delete) {
            new UnregisterAppService(SaveSharedPreference.getLoggedInServerUri(context),
                    SaveSharedPreference.getClientToken(context))
                    .unregisterApp(dao.findByPackageName(packageName).getRegistrationId())
                    .enqueue(new retrofit2.Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                dao.delete(packageName);
                                Bundle resultBundle = new Bundle();
                                resultBundle.putString(EXTRA_UNREGISTERED, packageName);
                                sendReply(what, id, replyTo, resultBundle);
                            } else {
                                Bundle resultBundle = new Bundle();
                                resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                                sendReply(what, id, replyTo, resultBundle);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                            Bundle resultBundle = new Bundle();
                            resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                            sendReply(what, id, replyTo, resultBundle);
                        }
                    });
        } else {
            Registration registration = new Registration();
            registration.setRegistrationId(TokenGenerator.getToken(20));
            registration.setPackageName(packageName);
            new RegisterAppService(SaveSharedPreference.getLoggedInServerUri(context),
                    SaveSharedPreference.getClientToken(context)).registerApp(registration.getRegistrationId())
                    .enqueue(new retrofit2.Callback<Application>() {
                        @Override
                        public void onResponse(Call<Application> call, Response<Application> response) {
                            if (response.isSuccessful()) {
                                dao.insert(registration);
                                Bundle resultBundle = new Bundle();
                                resultBundle.putString(EXTRA_REGISTRATION_ID, response.body().getRoutingToken());
                                sendReply(what, id, replyTo, resultBundle);
                            } else {
                                Bundle resultBundle = new Bundle();
                                resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                                sendReply(what, id, replyTo, resultBundle);
                            }
                        }

                        @Override
                        public void onFailure(Call<Application> call, Throwable t) {
                            t.printStackTrace();
                            Bundle resultBundle = new Bundle();
                            resultBundle.putString(EXTRA_ERROR, ERROR_SERVICE_NOT_AVAILABLE);
                            sendReply(what, id, replyTo, resultBundle);
                        }
                    });
        }
    }
}
