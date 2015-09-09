package com.fablabbcn.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class DeviceData implements Parcelable {

    @SerializedName("recorded_at")
    private Date recordedAt;

    @SerializedName("added_at")
    private Date addedAt;

    //todo IGNORE?
    private String firmware;

    private DeviceLocation location;

    private List<Sensor> sensors = new ArrayList<Sensor>();

    //SETTERS & GETTERS

    public DeviceLocation getLocation() {
        return location;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(recordedAt != null ? recordedAt.getTime() : -1);
        dest.writeLong(addedAt != null ? addedAt.getTime() : -1);
        dest.writeString(this.firmware);
        dest.writeParcelable(this.location, 0);
        dest.writeTypedList(sensors);
    }

    public DeviceData() {
    }

    protected DeviceData(Parcel in) {
        long tmpRecordedAt = in.readLong();
        this.recordedAt = tmpRecordedAt == -1 ? null : new Date(tmpRecordedAt);
        long tmpAddedAt = in.readLong();
        this.addedAt = tmpAddedAt == -1 ? null : new Date(tmpAddedAt);
        this.firmware = in.readString();
        this.location = in.readParcelable(DeviceLocation.class.getClassLoader());
        this.sensors = in.createTypedArrayList(Sensor.CREATOR);
    }

    public static final Parcelable.Creator<DeviceData> CREATOR = new Parcelable.Creator<DeviceData>() {
        public DeviceData createFromParcel(Parcel source) {
            return new DeviceData(source);
        }

        public DeviceData[] newArray(int size) {
            return new DeviceData[size];
        }
    };
}
