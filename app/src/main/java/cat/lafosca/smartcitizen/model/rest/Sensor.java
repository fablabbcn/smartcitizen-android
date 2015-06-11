package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 03/06/15.
 */
public class Sensor {

    private Integer id;

    private Object ancestry;

    private String name;

    private String description;

    private String unit;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    private Object value;

    @SerializedName("raw_value")
    private Object rawValue;

    @SerializedName("prev_value")
    private Object prevValue;

    @SerializedName("prev_raw_value")
    private Object prevRawValue;
}
