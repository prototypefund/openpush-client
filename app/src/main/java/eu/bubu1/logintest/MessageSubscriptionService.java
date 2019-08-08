package eu.bubu1.logintest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;

import com.here.oksse.OkSse;
import com.here.oksse.ServerSentEvent;

import eu.bubu1.logintest.ui.login.StartActivity;
import eu.bubu1.logintest.utils.SaveSharedPreference;
import okhttp3.Request;
import okhttp3.Response;

public class MessageSubscriptionService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, StartActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_arrow_downward_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        new Thread(() -> {
            String path = "http://localhost:8000/subscribe";
            String key = SaveSharedPreference.getClientToken(getApplicationContext());
            Request request = new Request.Builder().url(path).addHeader("X-Openpush-Key", key).build();
            OkSse okSse = new OkSse();
            ServerSentEvent sse = okSse.newServerSentEvent(request, new ServerSentEvent.Listener() {
                @Override
                public void onOpen(ServerSentEvent sse, Response response) {
                    // When the channel is opened
                }

                @Override
                public void onMessage(ServerSentEvent sse, String id, String event, String message) {
                    // When a message is received
                }

                @WorkerThread
                @Override
                public void onComment(ServerSentEvent sse, String comment) {
                    // When a comment is received
                }

                @WorkerThread
                @Override
                public boolean onRetryTime(ServerSentEvent sse, long milliseconds) {
                    return true; // True to use the new retry time received by SSE
                }

                @WorkerThread
                @Override
                public boolean onRetryError(ServerSentEvent sse, Throwable throwable, Response response) {
                    return true; // True to retry, false otherwise
                }

                @WorkerThread
                @Override
                public void onClosed(ServerSentEvent sse) {
                    // Channel closed
                }

                @Override
                public Request onPreRetry(ServerSentEvent sse, Request originalRequest) {
                    return null;
                }
            });
        }).start();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
