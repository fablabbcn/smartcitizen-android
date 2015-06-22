package cat.lafosca.smartcitizen.rest.api;

import java.util.List;

import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.model.rest.UserAuth;
import cat.lafosca.smartcitizen.rest.response.LoginResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by ferran on 03/06/15.
 */
public interface RestClient {

    @GET("/v0/devices")
    void getAllDevices(Callback<List<Device>> cb);

    @POST("/sessions")
    void login(@Body UserAuth auth, Callback<LoginResponse> callback);

}
