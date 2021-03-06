package eu.bubu1.pushclient;

import java.io.IOException;

import eu.bubu1.pushclient.api.ClientApi;
import eu.bubu1.pushclient.apimodels.Client;
import eu.bubu1.pushclient.apimodels.ClientRegistration;
import retrofit2.Response;

public class RegisterNewDeviceService {
    private ApiClient defaultClient;

    public RegisterNewDeviceService(String uri, String username, String password){
        defaultClient = new ApiClient(uri, "basic_auth");
        defaultClient.setCredentials(username, password);
    }

    public Response<Client> registerNewDevice(String name) throws IOException {
        ClientApi api = defaultClient.createService(ClientApi.class);
        ClientRegistration client = new ClientRegistration();
        client.name(name);
        return api.clientPost(client).execute();
    }

}
