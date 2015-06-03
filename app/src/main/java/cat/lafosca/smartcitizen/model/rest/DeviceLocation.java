package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 03/06/15.
 */
public class DeviceLocation {

    private Object ip;

    private String exposure;

    private Integer elevation;

    private Double latitude;

    private Double longitude;

    private String geohash;

    private String city;

    @SerializedName("country_code")
    private String countryCode;

    private String country;
}
