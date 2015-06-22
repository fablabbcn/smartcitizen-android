package cat.lafosca.smartcitizen.model.rest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 22/06/15.
 */
public class CurrentUser extends User {

    private List<Device> devices = new ArrayList<Device>();

    //GETTER
    public List<Device> getDevices() {
        return devices;
    }
}
