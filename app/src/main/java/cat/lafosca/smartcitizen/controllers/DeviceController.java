package cat.lafosca.smartcitizen.controllers;

import android.util.Log;

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

    //TODO implement another solution? multiple interfaces? For every new method in the controller, we should add two methods (success and error) to this interface
    public interface DeviceControllerListener {
        void onGetDevices(List<Device> devices);
        void onGetDevicesError(RetrofitError error);
        void onGetDevice(Device device);
        void onGetDeviceError(RetrofitError error);
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
                listener.onGetDevicesError(error);
            }
        });
    }

    public static void getDevice(int deviceId, final DeviceControllerListener listener) {
        RestController.getInstance().getRestClient().getDevice(deviceId, new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
                if (listener != null) {
                    listener.onGetDevice(device);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onGetDeviceError(error);
            }
        });
    }
}
