package eu.bubu1.pushclient.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eu.bubu1.pushclient.R;
import eu.bubu1.pushclient.RegistrationViewModel;
import eu.bubu1.pushclient.db.entity.Registration;

public class RegisteredAppsActivity extends AppCompatActivity {

    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_apps);

        RecyclerView recyclerView = findViewById(R.id.applist);
        final RegisteredAppsAdapter adapter = new RegisteredAppsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        registrationViewModel.getAllRegistrations().observe(this, new Observer<List<Registration>>() {
            @Override
            public void onChanged(@Nullable final List<Registration> registrations) {
                // Update the cached copy of the words in the adapter.
                adapter.setApps(registrations);
            }
        });
    }

}
