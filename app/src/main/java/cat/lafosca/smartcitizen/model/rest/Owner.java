package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class Owner implements Parcelable {

    private Integer id;

    private String username;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private String avatar;

    private String url;

    @SerializedName("joined_at")
    private String joinedAt;

    private OwnerLocation location;

    @SerializedName("device_ids")
    private List<Integer> deviceIds = new ArrayList<Integer>();

    //GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }
    //GETTERS AND SETTERS

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.username);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.avatar);
        dest.writeString(this.url);
        dest.writeString(this.joinedAt);
        dest.writeParcelable(this.location, flags);
        dest.writeList(this.deviceIds);
    }

    public Owner() {
    }

    protected Owner(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.username = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.avatar = in.readString();
        this.url = in.readString();
        this.joinedAt = in.readString();
        this.location = in.readParcelable(OwnerLocation.class.getClassLoader());
        this.deviceIds = new ArrayList<Integer>();
        in.readList(this.deviceIds, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}
