package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class Owner {

    private Integer id;

    private String username;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private String avatar;

    private String url;

    @SerializedName("joined_at")
    private String joinedAt;

    private OwnerLocation location;

    @SerializedName("device_ids")
    private List<Integer> deviceIds = new ArrayList<Integer>();
}
