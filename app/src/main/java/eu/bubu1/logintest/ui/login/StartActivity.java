package eu.bubu1.logintest.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import eu.bubu1.logintest.R;
import eu.bubu1.logintest.utils.SaveSharedPreference;

public class StartActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final TextView serverUriTextView = findViewById(R.id.serverUri);
        final TextView clientTokenTextView = findViewById(R.id.clientToken);
        final Button logoutButton = findViewById(R.id.logout);

        if(!SaveSharedPreference.getLoggedInStatus(getApplicationContext())) {
            Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
            StartActivity.this.startActivity(myIntent);
        }
        else{
            serverUriTextView.setText(SaveSharedPreference.getLoggedInServerUri(getApplicationContext()));
            clientTokenTextView.setText(SaveSharedPreference.getClientToken(getApplicationContext()));
        }



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> {
                //sloginViewModel.logout();
                }).start();
            }
        });
    }
}
