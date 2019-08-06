package eu.bubu1.logintest;

import java.io.IOException;

import eu.bubu1.logintest.api.ApplicationApi;
import eu.bubu1.logintest.apimodels.AppRegistration;
import eu.bubu1.logintest.apimodels.Application;
import retrofit2.Response;

public class RegisterAppService {
    private ApiClient defaultClient;

    public RegisterAppService(String uri, String clientKey){
        defaultClient = new ApiClient(uri, "client_key");
        defaultClient.setApiKey(clientKey);
    }

    public Response<Application> registerNewDevice(String name) throws IOException {
        ApplicationApi api = defaultClient.createService(ApplicationApi.class);
        AppRegistration app = new AppRegistration();
        app.name(name);
        return api.applicationPost(app).execute();
    }

}
