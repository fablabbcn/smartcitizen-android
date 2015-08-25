package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

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
        dest.writeString(this.slug);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public Kit() {
    }

    protected Kit(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.slug = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
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
