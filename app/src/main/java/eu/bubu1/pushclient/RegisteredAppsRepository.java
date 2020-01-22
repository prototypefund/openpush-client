package eu.bubu1.pushclient;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import eu.bubu1.pushclient.db.Database;
import eu.bubu1.pushclient.db.PushClientDatabase;
import eu.bubu1.pushclient.db.RegistrationDao;
import eu.bubu1.pushclient.db.entity.Registration;

public class RegisteredAppsRepository {

    private RegistrationDao registrationDao;
    private LiveData<List<Registration>> allRegistrations;

    public RegisteredAppsRepository(Application application) {
        PushClientDatabase db = PushClientDatabase.getDatabase(application);
        registrationDao = db.registrationDao();
        allRegistrations = registrationDao.getAll();
    }

    LiveData<List<Registration>> getAllRegistrations() {
        return allRegistrations;
    }

    public void insert (Registration registration) {
        new insertAsyncTask(registrationDao).execute(registration);
    }

    private static class insertAsyncTask extends AsyncTask<Registration, Void, Void> {

        private RegistrationDao mAsyncTaskDao;

        insertAsyncTask(RegistrationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Registration... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
