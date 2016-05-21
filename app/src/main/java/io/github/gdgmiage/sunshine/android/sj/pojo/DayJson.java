package io.github.gdgmiage.sunshine.android.sj.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by angebagui on 14/05/2016.
 */
public class DayJson implements Serializable{

    @SerializedName("dt")
    public Long date;
    @SerializedName("temp")
    public TempJson temp;
    @SerializedName("pressure")
    public Double pressure;
    @SerializedName("humidity")
    public Double humidity;
    @SerializedName("weather")
    public List<WeatherJson> weather;
    @SerializedName("speed")
    public Double speed;
    @SerializedName("deg")
    public Integer deg;
    @SerializedName("clouds")
    public Integer clouds;
}
