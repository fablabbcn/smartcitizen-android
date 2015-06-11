package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 03/06/15.
 */
public class Kit {

    private Integer id;

    private String slug;

    private String name;

    private String description;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    //GETTERS

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
