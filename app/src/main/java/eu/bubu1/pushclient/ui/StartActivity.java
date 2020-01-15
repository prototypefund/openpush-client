package eu.bubu1.pushclient.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import eu.bubu1.pushclient.MessageSubscriptionService;
import eu.bubu1.pushclient.R;
import eu.bubu1.pushclient.UnregisterService;
import eu.bubu1.pushclient.ui.login.LoginActivity;
import eu.bubu1.pushclient.utils.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StartActivity extends AppCompatActivity {

    Button btnStartService, btnStopService, btnAddFakeApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final TextView serverUriTextView = findViewById(R.id.serverUri);
        final TextView usernameTextView = findViewById(R.id.username);
        final TextView clientTokenTextView = findViewById(R.id.clientToken);
        final Button logoutButton = findViewById(R.id.logout);

        btnAddFakeApp = findViewById(R.id.buttonRegisterFakeApp);
        btnStartService = findViewById(R.id.buttonStartService);
        btnStopService = findViewById(R.id.buttonStopService);

        btnAddFakeApp.setOnClickListener(v -> addFakeApp());
        btnStartService.setOnClickListener(v -> startService());
        btnStopService.setOnClickListener(v -> stopService());

        if (!SaveSharedPreference.getLoggedInStatus(getApplicationContext())) {
            logoutButton.setEnabled(false);
            Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
            StartActivity.this.startActivity(myIntent);
            finish();
        } else {
            serverUriTextView.setText(SaveSharedPreference.getLoggedInServerUri(this));
            usernameTextView.setText(SaveSharedPreference.getLoggedInUsername(this));
            clientTokenTextView.setText(SaveSharedPreference.getClientToken(this));
            logoutButton.setEnabled(true);
        }


        logoutButton.setOnClickListener(v -> {
                String token = SaveSharedPreference.getClientToken(this);
                new UnregisterService(SaveSharedPreference.getLoggedInServerUri(this), token).unregister()
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(!response.isSuccessful()){
                                    Toast.makeText(StartActivity.this, R.string.logout_failed, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(StartActivity.this, R.string.logout_failed, Toast.LENGTH_SHORT).show();
                            }
                        });
            SaveSharedPreference.setLoggedOut(getApplicationContext());
            recreate();
        });
    }

    private void addFakeApp() {

    }

    public void startService() {
        Intent serviceIntent = new Intent(this, MessageSubscriptionService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");

        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, MessageSubscriptionService.class);
        stopService(serviceIntent);
    }
}
