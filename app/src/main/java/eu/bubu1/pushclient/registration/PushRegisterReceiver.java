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
