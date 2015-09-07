package com.smartcitizen.rest.api;

import java.util.List;

import com.smartcitizen.model.rest.BaseDevice;
import com.smartcitizen.model.rest.Device;
import com.smartcitizen.model.rest.UserAuth;
import com.smartcitizen.rest.response.LoginResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ferran on 03/06/15.
 */
public interface RestClient {

    @GET("/v0/devices")
    void getAllDevices(Callback<List<Device>> cb);

    @GET("/v0/devices/world_map")
    void getWorldDevices(Callback<List<BaseDevice>> cb);

    @GET("/v0/devices/{device_id}")
    void getDevice(@Path("device_id") int deviceId, Callback<Device> callback);

    @POST("/sessions")
    void login(@Body UserAuth auth, Callback<LoginResponse> callback);

}
