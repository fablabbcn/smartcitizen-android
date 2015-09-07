package com.smartcitizen.controllers;

import com.smartcitizen.managers.SharedPreferencesManager;
import com.smartcitizen.model.rest.UserAuth;
import com.smartcitizen.rest.RestController;
import com.smartcitizen.rest.response.LoginResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ferran on 19/06/15.
 */
public class SessionController {

    public interface SessionControllerListener {
        void onLoginSucces();
        void onLoginError(RetrofitError error);
    }

    public static void userWantsToLogin(final SessionControllerListener listener, String username, String passw) {

        RestController.getInstance().getRestClient().login(new UserAuth(username, passw), new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                SharedPreferencesManager.getInstance().setUserLoggedIn(loginResponse.getAccesToken());

                listener.onLoginSucces();

            }

            @Override
            public void failure(RetrofitError error) {

                //TODO error.getBodyAs()
                listener.onLoginError(error);
            }
        });
    }
}
