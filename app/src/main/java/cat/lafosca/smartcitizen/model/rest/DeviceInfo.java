package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 17/06/15.
 */
public class DeviceInfo implements Parcelable {

    private Integer id;

    private String name;

    private String description;

    private String status;

    @SerializedName("last_reading_at")
    private String lastReadingAt;

    @SerializedName("added_at")
    private String addedAt;

    @SerializedName("updated_at")
    private String updatedAt;

    //GETTERS

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.status);
        dest.writeString(this.lastReadingAt);
        dest.writeString(this.addedAt);
        dest.writeString(this.updatedAt);
    }

    public DeviceInfo() {
    }

    protected DeviceInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.status = in.readString();
        this.lastReadingAt = in.readString();
        this.addedAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<DeviceInfo> CREATOR = new Parcelable.Creator<DeviceInfo>() {
        public DeviceInfo createFromParcel(Parcel source) {
            return new DeviceInfo(source);
        }

        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };
}
