package cat.lafosca.smartcitizen.rest.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Created by ferran on 22/06/15.
 */
public interface AuthRestClient {

    @GET("/v0/me")
    void getCurrentUser(Callback<Response> cb);
}
