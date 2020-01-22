package eu.bubu1.pushclient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import eu.bubu1.pushclient.db.entity.Registration;

public class RegistrationViewModel extends AndroidViewModel {

    private RegisteredAppsRepository repository;
    private LiveData<List<Registration>> allRegistrations;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        repository = new RegisteredAppsRepository(application);
        allRegistrations = repository.getAllRegistrations();
    }

    public LiveData<List<Registration>> getAllRegistrations() {
        return allRegistrations;
    }

    public void insert(Registration registration){
        repository.insert(registration);
    }
}
