package eu.bubu1.pushclient.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class RegisteredClient {

    private String userId;
    private String clientToken;
    private String serverUri;

    public RegisteredClient(String userId, String serverUri, String clientToken) {
        this.userId = userId;
        this.serverUri = serverUri;
        this.clientToken = clientToken;
    }

    public String getUserId() {
        return userId;
    }
    public String getClientToken() {
        return clientToken;
    }

    public String getServerUri() {
        return serverUri;
    }

}
