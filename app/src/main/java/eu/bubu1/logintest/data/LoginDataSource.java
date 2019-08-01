package eu.bubu1.logintest.data;

import android.webkit.URLUtil;

import eu.bubu1.logintest.RegisterNewDeviceService;
import eu.bubu1.logintest.data.model.RegisteredClient;
import eu.bubu1.logintest.apimodels.Client;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<RegisteredClient> login(String serverUri, String username, String password) {
        //try {
            //if (!URLUtil.isValidUrl(serverUri)) {
            //    throw new IOException("Invalid URL");
            //}
                Client newClient = new RegisterNewDeviceService(serverUri, username, password)
                        .registerNewDevice(android.os.Build.MODEL).blockingFirst();
                RegisteredClient loggedinUser =
                        new RegisteredClient(username, newClient.getToken());
                return new Result.Success<>(loggedinUser);
            //} catch (Exception e) {
            //    return new Result.Error(new IOException("Error logging in", e));
            //}
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
