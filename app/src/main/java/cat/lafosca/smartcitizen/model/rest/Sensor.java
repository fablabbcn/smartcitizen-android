package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

/**
 * Created by ferran on 03/06/15.
 */
public class Sensor implements Parcelable {

    private Integer id;

    private String ancestry;

    private String name;

    private String description;

    private String unit;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    private float value;

    @SerializedName("raw_value")
    private float rawValue;

    @SerializedName("prev_value")
    private float prevValue;

    @SerializedName("prev_raw_value")
    private float prevRawValue;

    //GETTERS
    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.ancestry);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.unit);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeFloat(this.value);
        dest.writeFloat(this.rawValue);
        dest.writeFloat(this.prevValue);
        dest.writeFloat(this.prevRawValue);
    }


    public String getPrettyName() {
        String sensorName;

        //Pattern pattern = Pattern.compile(Pattern.quote(getDescription()), Pattern.CASE_INSENSITIVE);
        String description = getDescription().toLowerCase();

        if (description.contains("custom circuit")) {
            sensorName = getName();

        } else {
            if (description.contains("noise")) {
                sensorName = "Sound";

            } else if (description.contains("light")) {
                sensorName = "Light";

            } else if (description.contains("wifi")) {
                sensorName = "Network";

            } else if (description.contains("co")) {
                sensorName = "Co";

            } else if (description.contains("no2")) {
                sensorName = "No2";

            } else {
                sensorName = getDescription();
            }
        }

        return sensorName;
    }

    public Sensor() {
    }

    protected Sensor(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ancestry = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.unit = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.value = in.readFloat();
        this.rawValue = in.readFloat();
        this.prevValue = in.readFloat();
        this.prevRawValue = in.readFloat();
    }

    public static final Parcelable.Creator<Sensor> CREATOR = new Parcelable.Creator<Sensor>() {
        public Sensor createFromParcel(Parcel source) {
            return new Sensor(source);
        }

        public Sensor[] newArray(int size) {
            return new Sensor[size];
        }
    };
}
