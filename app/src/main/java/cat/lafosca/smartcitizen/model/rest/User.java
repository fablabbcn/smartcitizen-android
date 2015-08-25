package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ferran on 03/06/15.
 */
public class User implements Parcelable {

    private Integer id;

    private String uuid;

    private String username;

    private String avatar;

    //NULL
    private String url;

    @SerializedName("joined_at")
    private Date joinedAt;

    private UserLocation location;

    @SerializedName("device_ids")
    private List<Integer> deviceIds = new ArrayList<Integer>();

    //GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public UserLocation getLocation() {
        return location;
    }

    public String getAvatar() {
        return avatar;
    }
    //GETTERS AND SETTERS

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.uuid);
        dest.writeString(this.username);
        dest.writeString(this.avatar);
        dest.writeString(this.url);
        dest.writeLong(joinedAt != null ? joinedAt.getTime() : -1);
        dest.writeParcelable(this.location, 0);
        dest.writeList(this.deviceIds);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.uuid = in.readString();
        this.username = in.readString();
        this.avatar = in.readString();
        this.url = in.readString();
        long tmpJoinedAt = in.readLong();
        this.joinedAt = tmpJoinedAt == -1 ? null : new Date(tmpJoinedAt);
        this.location = in.readParcelable(UserLocation.class.getClassLoader());
        this.deviceIds = new ArrayList<Integer>();
        in.readList(this.deviceIds, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
