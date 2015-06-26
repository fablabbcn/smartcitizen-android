package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ferran on 03/06/15.
 */
public class DeviceLocation implements Parcelable {

    private String ip;

    private String exposure;

    private Integer elevation;

    private Double latitude;

    private Double longitude;

    private String geohash;

    private String city;

    @SerializedName("country_code")
    private String countryCode;

    private String country;

    //GETTERS

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getExposure() {
        return exposure;
    }

    public String getPrettyLocation() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCity());
        sb.append(", "+getCountry());
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ip);
        dest.writeString(this.exposure);
        dest.writeValue(this.elevation);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.geohash);
        dest.writeString(this.city);
        dest.writeString(this.countryCode);
        dest.writeString(this.country);
    }

    public DeviceLocation() {
    }

    protected DeviceLocation(Parcel in) {
        this.ip = in.readString();
        this.exposure = in.readString();
        this.elevation = (Integer) in.readValue(Integer.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.geohash = in.readString();
        this.city = in.readString();
        this.countryCode = in.readString();
        this.country = in.readString();
    }

    public static final Parcelable.Creator<DeviceLocation> CREATOR = new Parcelable.Creator<DeviceLocation>() {
        public DeviceLocation createFromParcel(Parcel source) {
            return new DeviceLocation(source);
        }

        public DeviceLocation[] newArray(int size) {
            return new DeviceLocation[size];
        }
    };
}
