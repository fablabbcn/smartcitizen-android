package com.fablabbcn.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ferran on 30/07/15.
 */

public class Measurement implements Parcelable {

    private Integer id;
    private String uuid;
    private String name;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //Parcelable
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
    }

    public Measurement() {
    }

    protected Measurement(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.uuid = in.readString();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Measurement> CREATOR = new Parcelable.Creator<Measurement>() {
        public Measurement createFromParcel(Parcel source) {
            return new Measurement(source);
        }

        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };
}
