package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 22/06/15.
 */
public class CurrentUser extends User implements Parcelable {

    //currently, "/v0/me" returns another format for DeviceInfo data -> missing : latitude (float), longitude (float), kit_id (int)
    private List<Device> devices = new ArrayList<Device>();

    private String email;

    private String role;

    //GETTER
    public List<Device> getDevices() {
        return devices;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(devices);
        dest.writeString(this.email);
        dest.writeString(this.role);
    }

    public CurrentUser() {
    }

    protected CurrentUser(Parcel in) {
        super(in);
        this.devices = in.createTypedArrayList(Device.CREATOR);
        this.email = in.readString();
        this.role = in.readString();
    }

    public static final Creator<CurrentUser> CREATOR = new Creator<CurrentUser>() {
        public CurrentUser createFromParcel(Parcel source) {
            return new CurrentUser(source);
        }

        public CurrentUser[] newArray(int size) {
            return new CurrentUser[size];
        }
    };
}
