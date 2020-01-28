package eu.bubu1.pushclient.registration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static eu.bubu1.pushclient.registration.Constants.*;

public class PushRegisterReceiver extends BroadcastReceiver {
    private static final String TAG = "PushRegisterRcv";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Broadcast received. Intent: " + intent.toString() + "Extras: " + intent.getExtras().toString());
        Intent newIntent = new Intent(context, PushRegisterService.class);
        if (intent.getExtras().get("delete") != null) {
            newIntent.setAction(ACTION_PUSH_UNREGISTER);
        } else {
            newIntent.setAction(ACTION_PUSH_REGISTER);
        }
        newIntent.putExtras(intent.getExtras());
        PushRegisterService.enqueueWork(context, newIntent);
    }

}
