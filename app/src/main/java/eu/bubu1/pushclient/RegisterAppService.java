package eu.bubu1.pushclient;

import java.io.IOException;

import eu.bubu1.pushclient.api.ApplicationApi;
import eu.bubu1.pushclient.apimodels.AppRegistration;
import eu.bubu1.pushclient.apimodels.Application;
import retrofit2.Response;

public class RegisterAppService {
    private ApiClient defaultClient;

    public RegisterAppService(String uri, String clientKey){
        defaultClient = new ApiClient(uri, "client_key");
        defaultClient.setApiKey(clientKey);
    }

    public Response<Application> registerNewDevice(String registrationId) throws IOException {
        ApplicationApi api = defaultClient.createService(ApplicationApi.class);
        AppRegistration app = new AppRegistration();
        app.registrationId(registrationId);
        return api.applicationPost(app).execute();
    }

}
