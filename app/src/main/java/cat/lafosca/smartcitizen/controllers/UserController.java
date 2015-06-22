package cat.lafosca.smartcitizen.controllers;

import cat.lafosca.smartcitizen.model.rest.CurrentUser;
import cat.lafosca.smartcitizen.rest.RestController;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ferran on 22/06/15.
 */
public class UserController {

    public interface UserControllerListener {
        void onGetUserData(CurrentUser currentUser);
        void onErrorGetUserData(RetrofitError error);
    }

    public static void getCurrentUserData(final UserControllerListener listener) {

        //todo retrieve data from db before than the api
        RestController.getInstance().getAuthRestClient().getCurrentUser(new Callback<CurrentUser>() {
            @Override
            public void success(CurrentUser currentUser, Response response) {
                if (listener != null)
                    listener.onGetUserData(currentUser);
            }

            @Override
            public void failure(RetrofitError error) {
                if (listener != null)
                    listener.onErrorGetUserData(error);
            }
        });
    }
}
