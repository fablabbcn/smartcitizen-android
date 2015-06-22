package cat.lafosca.smartcitizen.controllers;

import java.util.List;

import cat.lafosca.smartcitizen.model.rest.Device;
import cat.lafosca.smartcitizen.rest.RestController;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ferran on 04/06/15.
 */
public class KitsController {

    //TODO: Decide: user reactive android or jobs (job queues)

    //TODO: use event bus? listeners?
    public interface KitsControllerListener {
        void onGetKits(List<Device> devices);
    }

    public static void getKits(final KitsControllerListener listener) {
        RestController.getInstance().getRestClient().getAllDevices(new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                if (listener != null) {
                    listener.onGetKits(devices);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
