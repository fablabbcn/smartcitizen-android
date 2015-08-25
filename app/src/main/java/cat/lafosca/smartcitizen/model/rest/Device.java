package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class Device implements Parcelable {

    private Integer id;

    private String uuid;

    private String name;

    private String description;

    private String state;

    @SerializedName("system_tags")
    private List<String> systemTags = new ArrayList<String>();

    @SerializedName("last_reading_at")
    private String lastReadingAt;

    @SerializedName("added_at")
    private String addedAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("mac_address")
    private String macAddress;

    private User owner;

    private DeviceData data;

    private Kit kit;

    //GETTERS

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public Integer getId() {
        return id;
    }

    public Kit getKit() {
        return kit;
    }

    public DeviceData getDeviceData() {
        return data;
    }

    public User getOwner() {
        return owner;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public List<String> getSystemTags() {
        return systemTags;
    }

    public Device() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.state);
        dest.writeStringList(this.systemTags);
        dest.writeString(this.lastReadingAt);
        dest.writeString(this.addedAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.macAddress);
        dest.writeParcelable(this.owner, 0);
        dest.writeParcelable(this.data, 0);
        dest.writeParcelable(this.kit, 0);
    }

    protected Device(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.uuid = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.state = in.readString();
        this.systemTags = in.createStringArrayList();
        this.lastReadingAt = in.readString();
        this.addedAt = in.readString();
        this.updatedAt = in.readString();
        this.macAddress = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.data = in.readParcelable(DeviceData.class.getClassLoader());
        this.kit = in.readParcelable(Kit.class.getClassLoader());
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
