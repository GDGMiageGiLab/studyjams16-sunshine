package io.github.gdgmiage.sunshine.android.sj.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by angebagui on 14/05/2016.
 */
public class TempJson {
    @SerializedName("day")
    public Double day;
    public Double min;
    public Double max;
    public Double night;
    public Double eve;
    public Double morn;
}
