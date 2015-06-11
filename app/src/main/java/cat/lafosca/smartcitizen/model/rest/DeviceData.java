package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class DeviceData {

    @SerializedName("recorded_at")
    private String recordedAt;

    @SerializedName("added_at")
    private String addedAt;

    @SerializedName("calibrated_at")
    private String calibratedAt;

    private String firmware;

    private DeviceLocation location;

    private List<Sensor> sensors = new ArrayList<Sensor>();

    //SETTERS & GETTERS

    public DeviceLocation getLocation() {
        return location;
    }
}
