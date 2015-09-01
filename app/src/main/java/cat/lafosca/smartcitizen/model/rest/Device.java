package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class Device extends BaseDevice implements Parcelable {

    @SerializedName("last_reading_at")
    private Date lastReadingAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("mac_address")
    private String macAddress;

    private User owner;

    private DeviceData data;

    private Kit kit;

    //GETTERS

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getLastReadingAt() {
        return lastReadingAt;
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

    //UTILS
    public static Comparator<Device> COMPARE_BY_UPDATED = new Comparator<Device>() {
        @Override
        public int compare(Device device, Device other) {
            if (device.getUpdatedAt() == null || other.getUpdatedAt() == null)
                return 0;
            return device.getUpdatedAt().compareTo(other.getUpdatedAt());
        }
    };

    //PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(lastReadingAt != null ? lastReadingAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
        dest.writeString(this.macAddress);
        dest.writeParcelable(this.owner, 0);
        dest.writeParcelable(this.data, 0);
        dest.writeParcelable(this.kit, 0);
    }

    public Device() {
    }

    protected Device(Parcel in) {
        super(in);
        long tmpLastReadingAt = in.readLong();
        this.lastReadingAt = tmpLastReadingAt == -1 ? null : new Date(tmpLastReadingAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
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
