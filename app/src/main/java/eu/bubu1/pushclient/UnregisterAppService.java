package eu.bubu1.pushclient;

import java.io.IOException;

import eu.bubu1.pushclient.api.ApplicationApi;
import eu.bubu1.pushclient.apimodels.AppRegistration;
import eu.bubu1.pushclient.apimodels.Application;
import retrofit2.Call;
import retrofit2.Response;

public class UnregisterAppService {
    private ApiClient defaultClient;

    public UnregisterAppService(String uri, String clientKey){
        defaultClient = new ApiClient(uri, "client_key");
        defaultClient.setApiKey(clientKey);
    }

    public Call<Void> unregisterApp(String registrationId){
        ApplicationApi api = defaultClient.createService(ApplicationApi.class);
        return api.applicationIdDelete(registrationId);
    }
}
