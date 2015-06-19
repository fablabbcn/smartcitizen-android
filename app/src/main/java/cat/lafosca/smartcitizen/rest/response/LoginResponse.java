package cat.lafosca.smartcitizen.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 19/06/15.
 */
public class LoginResponse {

    @SerializedName("access_token")
    String accesToken;

    public String getAccesToken() {
        return accesToken;
    }
}
