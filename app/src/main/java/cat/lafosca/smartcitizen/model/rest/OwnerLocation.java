package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 03/06/15.
 */
public class OwnerLocation implements Parcelable {

    private String city;

    private String country;

    @SerializedName("country_code")
    private String countryCode;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.countryCode);
    }

    public OwnerLocation() {
    }

    protected OwnerLocation(Parcel in) {
        this.city = in.readString();
        this.country = in.readString();
        this.countryCode = in.readString();
    }

    public static final Parcelable.Creator<OwnerLocation> CREATOR = new Parcelable.Creator<OwnerLocation>() {
        public OwnerLocation createFromParcel(Parcel source) {
            return new OwnerLocation(source);
        }

        public OwnerLocation[] newArray(int size) {
            return new OwnerLocation[size];
        }
    };
}
