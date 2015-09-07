package com.smartcitizen.controllers;

import java.util.List;

import com.smartcitizen.model.rest.BaseDevice;
import com.smartcitizen.model.rest.Device;
import com.smartcitizen.rest.RestController;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ferran on 04/06/15.
 */
public class DeviceController {

    //interface classes
    public interface DeviceControllerListener {
        void onError(RetrofitError error);
    }

    public interface GetDevicesListener extends DeviceControllerListener {
        void onGetDevices(List<Device> devices);
    }

    public interface GetWorldMapDevicesListener extends DeviceControllerListener {
        void onGetDevices(List<BaseDevice> devices);
    }

    public interface GetDeviceListener extends DeviceControllerListener {
        void onGetDevice(Device device);
    }

    //methods
    public static void getWorldMapDevices(final GetWorldMapDevicesListener listener) {
        RestController.getInstance().getRestClient().getWorldDevices(new Callback<List<BaseDevice>>() {
            @Override
            public void success(List<BaseDevice> devices, Response response) {
                if (listener != null) {
                    listener.onGetDevices(devices);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onError(error);
            }
        });
    }

    public static void getAllDevices(final GetDevicesListener listener) {
        RestController.getInstance().getRestClient().getAllDevices(new Callback<List<Device>>() {
            @Override
            public void success(List<Device> devices, Response response) {
                if (listener != null) {
                    listener.onGetDevices(devices);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onError(error);
            }
        });
    }

    public static void getDevice(int deviceId, final GetDeviceListener listener) {
        RestController.getInstance().getRestClient().getDevice(deviceId, new Callback<Device>() {
            @Override
            public void success(Device device, Response response) {
                if (listener != null) {
                    listener.onGetDevice(device);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onError(error);
            }
        });
    }
}
