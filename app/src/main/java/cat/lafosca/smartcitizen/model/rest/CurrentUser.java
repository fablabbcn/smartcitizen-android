package cat.lafosca.smartcitizen.model.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 22/06/15.
 */
public class CurrentUser extends User {

    //currently, "/v0/me" returns another format for DeviceInfo data (missing : latitude (float), longitude (float), kit_id (int)
    private List<DeviceInfo> devices = new ArrayList<DeviceInfo>();

    //GETTER
    public List<DeviceInfo> getDevices() {
        return devices;
    }
}
