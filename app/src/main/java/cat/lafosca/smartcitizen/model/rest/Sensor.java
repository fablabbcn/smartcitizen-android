package cat.lafosca.smartcitizen.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

import cat.lafosca.smartcitizen.R;

/**
 * Created by ferran on 03/06/15.
 */
public class Sensor implements Parcelable {

    //Sensor names
    private final String BATTERY =      "Battery";
    private final String SOLAR_PANE =   "Solar Panel";
    private final String HUMIDITY =     "Humidity";
    private final String TEMPERATURE =  "Temperature";
    private final String SOUND =        "Sound";
    private final String NETWORK =      "Network";
    private final String LIGHT =        "Light";
    private final String NO2 =          "No2";
    private final String CO =           "Co";

    private Integer id;

    @SerializedName("measurement_id")
    private String measurementId;

    private String uuid;

    private String ancestry;

    private String name;

    private String description;

    private String unit;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    //when asking for devices (/v0/devices), the data from sensors come in this fields (value, raw_value, prevValue, prevRawValue)
    //when asking for kits generic info (/v0/kits), this info doesn't exits, but you get a field called "measurement"
    //there's an endpoint that given a device id & a timestamp, it returns the values of the kit sensors int the timestamp

    private float value;

    @SerializedName("raw_value")
    private float rawValue;

    @SerializedName("prev_value")
    private float prevValue;

    @SerializedName("prev_raw_value")
    private float prevRawValue;

    private Measurement measurement;

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

    public int getIcon(String prettyName) {
        int sensorIcon = 0;
        if (prettyName.equalsIgnoreCase(SOUND)) {
            sensorIcon = R.mipmap.ic_sensor_sound;

        } else if (prettyName.equalsIgnoreCase(TEMPERATURE)) {
            sensorIcon = R.mipmap.ic_sensor_temperature;

        } else if (prettyName.equalsIgnoreCase(HUMIDITY)) {
            sensorIcon = R.mipmap.ic_sensor_humidity;

        } else if (prettyName.equalsIgnoreCase(LIGHT)) {
            sensorIcon = R.mipmap.ic_sensor_light;

        } else if (prettyName.equalsIgnoreCase(NO2)) {
            sensorIcon = R.mipmap.ic_sensor_no2;

        } else if (prettyName.equalsIgnoreCase(CO)) {
            sensorIcon = R.mipmap.ic_sensor_co;

        } else if (prettyName.equalsIgnoreCase(BATTERY)) {
            sensorIcon = R.mipmap.ic_sensor_battery;

        } else if (prettyName.equalsIgnoreCase(SOLAR_PANE)) {
            sensorIcon = R.mipmap.ic_sensor_solar_panel;

        } else if (prettyName.equalsIgnoreCase(NETWORK)) {
            sensorIcon = R.mipmap.ic_sensor_net;

        }

        return sensorIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.measurementId);
        dest.writeString(this.uuid);
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
        dest.writeParcelable(this.measurement, 0);
    }

    public Sensor() {
    }

    protected Sensor(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.measurementId = in.readString();
        this.uuid = in.readString();
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
        this.measurement = in.readParcelable(Measurement.class.getClassLoader());
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
