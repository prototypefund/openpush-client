package eu.bubu1.pushclient;


import eu.bubu1.pushclient.api.ClientApi;
import retrofit2.Call;

public class UnregisterService {
    private ApiClient defaultClient;

    public UnregisterService(String uri, String clientKey) {
        defaultClient = new ApiClient(uri, "client_key");
        defaultClient.setApiKey(clientKey);
    }

    public Call<Void> unregister() {
        ClientApi api = defaultClient.createService(ClientApi.class);
        return api.apiClientDeleteSelf();
    }

}
