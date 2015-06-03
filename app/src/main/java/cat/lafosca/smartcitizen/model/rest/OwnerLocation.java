package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 03/06/15.
 */
public class OwnerLocation {

    private Object city;

    private Object country;

    @SerializedName("country_code")
    private Object countryCode;
}
