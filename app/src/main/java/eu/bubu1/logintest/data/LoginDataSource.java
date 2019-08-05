package eu.bubu1.logintest.data;

import android.os.Build;
import android.webkit.URLUtil;

import eu.bubu1.logintest.RegisterNewDeviceService;
import eu.bubu1.logintest.data.model.RegisteredClient;
import eu.bubu1.logintest.apimodels.Client;
import retrofit2.Response;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<RegisteredClient> login(String serverUri, String username, String password) {
        try {
                Response<Client> clientResponse = new RegisterNewDeviceService(serverUri, username, password)
                        .registerNewDevice(Build.MODEL);
                if (clientResponse.isSuccessful()){
                    RegisteredClient client = new RegisteredClient(username, serverUri, clientResponse.body().getToken());
                    return new Result.Success<>(client);
                }
                else {
                    switch (clientResponse.code()) {
                        case 401:
                            return new Result.Error(new IOException("Authorization failed."));
                        case 500:
                            return new Result.Error(new IOException("Internal Server Error."));
                        default:
                            return new Result.Error(new IOException("Something unexpected went wrong, Error code: " + clientResponse.code()));
                    }
                }


            } catch (IOException e) {
                return new Result.Error(new IOException("Network Error", e));
            }
    }
}
