package eu.bubu1.logintest;

import eu.bubu1.logintest.api.ClientApi;
import eu.bubu1.logintest.apimodels.Client;
import eu.bubu1.logintest.apimodels.ClientRegistration;
import io.reactivex.Observable;

public class RegisterNewDeviceService {
    private ApiClient defaultClient;

    public RegisterNewDeviceService(String uri, String username, String password){
        defaultClient = new ApiClient(uri, "basic_auth");
        defaultClient.setCredentials(username, password);
    }

    public Observable<Client> registerNewDevice(String name){
        ClientApi api = defaultClient.createService(ClientApi.class);
        ClientRegistration client = new ClientRegistration();
        client.name(name);
        return api.clientPost(client);
    }

}
