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
public class DeviceController {

    //TODO: Decide: user reactive android or jobs (job queues)

    //TODO: use event bus? listeners?
    public interface DeviceControllerListener {
        void onGetDevices(List<Device> devices);
        void onErrorGetDevices(RetrofitError error);
    }

    public static void getAllDevices(final DeviceControllerListener listener) {
        RestController.getInstance().getRestClient().getAllDevices(new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                if (listener != null) {
                    listener.onGetDevices(devices);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onErrorGetDevices(error);
            }
        });
    }
}
