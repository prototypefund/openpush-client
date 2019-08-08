package eu.bubu1.pushclient.data;

import eu.bubu1.pushclient.data.model.RegisteredClient;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private RegisteredClient user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    private void setLoggedInUser(RegisteredClient user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<RegisteredClient> login(String uri, String username, String password) {
        // handle login
        Result<RegisteredClient> result = dataSource.login(uri, username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<RegisteredClient>) result).getData());
        }
        return result;
    }
}
