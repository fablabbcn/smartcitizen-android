package cat.lafosca.smartcitizen.rest.api;

import java.util.List;

import cat.lafosca.smartcitizen.model.rest.Device;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by ferran on 03/06/15.
 */
public interface RestClient {

    @GET("/v0/devices")
    public void getAllDevices(Callback<List<Device>> cb);

}
