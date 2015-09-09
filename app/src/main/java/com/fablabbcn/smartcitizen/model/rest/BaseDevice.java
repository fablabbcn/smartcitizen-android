package com.fablabbcn.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ferran on 27/08/15.
 */
public class BaseDevice implements Parcelable {

    //common info with Device (https://new-api-smartcitizen.me/v0/devices/)
    protected Integer id;

    protected String uuid;

    protected String name;

    protected String description;

    protected String state;

    @SerializedName("system_tags")
    protected List<String> systemTags = new ArrayList<String>();

    @SerializedName("added_at")
    protected Date addedAt;

    //omit data object (get full info from device from https://new-api-smartcitizen.me/v0/devices/:id -> DetailDeviceActivity)

    //only useful in this class (null at Device.java)
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("owner_username")
    @Expose
    private String ownerUsername;
    @Expose
    private Double latitude;
    @Expose
    private Double longitude;
    @Expose
    private String city;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("kit_id")
    @Expose
    private Integer kitId;

    //GETTERS
    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public Integer getId() {
        return id;
    }

    public List<String> getSystemTags() {
        return systemTags;
    }

    public String getDescription() {
        return description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Date getAddedAt() {
        return addedAt;
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
        dest.writeLong(addedAt != null ? addedAt.getTime() : -1);
        dest.writeValue(this.ownerId);
        dest.writeString(this.ownerUsername);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.city);
        dest.writeString(this.countryCode);
        dest.writeValue(this.kitId);
    }

    public BaseDevice() {
    }

    protected BaseDevice(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.uuid = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.state = in.readString();
        this.systemTags = in.createStringArrayList();
        long tmpAddedAt = in.readLong();
        this.addedAt = tmpAddedAt == -1 ? null : new Date(tmpAddedAt);
        this.ownerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ownerUsername = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.city = in.readString();
        this.countryCode = in.readString();
        this.kitId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<BaseDevice> CREATOR = new Parcelable.Creator<BaseDevice>() {
        public BaseDevice createFromParcel(Parcel source) {
            return new BaseDevice(source);
        }

        public BaseDevice[] newArray(int size) {
            return new BaseDevice[size];
        }
    };
}
