package com.smartcitizen.rest.api;

import com.smartcitizen.model.rest.CurrentUser;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by ferran on 22/06/15.
 */
public interface AuthRestClient {

    @GET("/v0/me")
    void getCurrentUser(Callback<CurrentUser> cb);
}
