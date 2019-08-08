package eu.bubu1.pushclient.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String username;
    private String serverUri;
    private String clientToken;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String username, String serverUri, String clientToken) {
        this.username = username;
        this.serverUri = serverUri;
        this.clientToken = clientToken;
    }

    String getUsername() {
        return username;
    }
    String getServerUri() {
        return serverUri;
    }
    String getClientToken() {
        return clientToken;
    }
}
