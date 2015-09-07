package com.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ferran on 03/06/15.
 */
public class Kit implements Parcelable {

    private Integer id;

    //new
    private String uuid;

    private String slug;

    private String name;

    private String description;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    //GETTERS

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public String getSlug() {
        return slug;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.uuid);
        dest.writeString(this.slug);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
    }

    public Kit() {
    }

    protected Kit(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.uuid = in.readString();
        this.slug = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    public static final Parcelable.Creator<Kit> CREATOR = new Parcelable.Creator<Kit>() {
        public Kit createFromParcel(Parcel source) {
            return new Kit(source);
        }

        public Kit[] newArray(int size) {
            return new Kit[size];
        }
    };
}
