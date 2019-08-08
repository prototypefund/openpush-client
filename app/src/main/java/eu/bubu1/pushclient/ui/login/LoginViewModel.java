package eu.bubu1.pushclient.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;
import android.webkit.URLUtil;

import eu.bubu1.pushclient.data.LoginRepository;
import eu.bubu1.pushclient.data.Result;
import eu.bubu1.pushclient.data.model.RegisteredClient;
import eu.bubu1.pushclient.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String uri, String username, String password) {
        // can be launched in a separate asynchronous job
        Result<RegisteredClient> result = loginRepository.login(uri, username, password);

        if (result instanceof Result.Success) {
            RegisteredClient data = ((Result.Success<RegisteredClient>) result).getData();
            loginResult.postValue(new LoginResult(new LoggedInUserView(data.getUserId(), data.getServerUri(), data.getClientToken())));
        } else {
            loginResult.postValue(new LoginResult(result.toString()));
        }
    }

    void loginDataChanged(String uri, String username, String password) {
        if (!isUriValid(uri)) {
            loginFormState.setValue(new LoginFormState(R.string.need_http_https, null, null));
        } else if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean isUriValid(String uri) {
        return URLUtil.isHttpsUrl(uri)||URLUtil.isHttpUrl(uri);
    }


    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return !username.isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 0;
    }
}
