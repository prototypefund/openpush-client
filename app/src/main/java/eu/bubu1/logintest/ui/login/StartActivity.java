package eu.bubu1.logintest.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import eu.bubu1.logintest.R;
import eu.bubu1.logintest.UnregisterService;
import eu.bubu1.logintest.utils.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static java.lang.String.valueOf;

public class StartActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final TextView serverUriTextView = findViewById(R.id.serverUri);
        final TextView usernameTextView = findViewById(R.id.username);
        final TextView clientTokenTextView = findViewById(R.id.clientToken);
        final Button logoutButton = findViewById(R.id.logout);

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
                                //ignore
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(StartActivity.this,"Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            SaveSharedPreference.setLoggedOut(getApplicationContext());
            recreate();
        });
    }
}
