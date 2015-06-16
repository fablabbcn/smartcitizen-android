package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class DeviceData implements Parcelable {

    @SerializedName("recorded_at")
    private String recordedAt;

    @SerializedName("added_at")
    private String addedAt;

    @SerializedName("calibrated_at")
    private String calibratedAt;

    private String firmware;

    private DeviceLocation location;

    private List<Sensor> sensors = new ArrayList<Sensor>();

    //SETTERS & GETTERS

    public DeviceLocation getLocation() {
        return location;
    }

    public DeviceData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recordedAt);
        dest.writeString(this.addedAt);
        dest.writeString(this.calibratedAt);
        dest.writeString(this.firmware);
        dest.writeParcelable(this.location, flags);
        dest.writeTypedList(sensors);
    }

    protected DeviceData(Parcel in) {
        this.recordedAt = in.readString();
        this.addedAt = in.readString();
        this.calibratedAt = in.readString();
        this.firmware = in.readString();
        this.location = in.readParcelable(DeviceLocation.class.getClassLoader());
        this.sensors = in.createTypedArrayList(Sensor.CREATOR);
    }

    public static final Creator<DeviceData> CREATOR = new Creator<DeviceData>() {
        public DeviceData createFromParcel(Parcel source) {
            return new DeviceData(source);
        }

        public DeviceData[] newArray(int size) {
            return new DeviceData[size];
        }
    };
}
