package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 03/06/15.
 */
public class Device {

    private Integer id;

    private String name;

    private String description;

    private String status;

    @SerializedName("last_reading_at")
    private String lastReadingAt;

    @SerializedName("added_at")
    private String addedAt;

    @SerializedName("updated_at")
    private String updatedAt;

    private Owner owner;

    private DeviceData data;

    //GETTERS & SETTERS

    private Kit kit;

    public String getName() {
        return name;
    }

    public DeviceData getData() {
        return data;
    }
}
