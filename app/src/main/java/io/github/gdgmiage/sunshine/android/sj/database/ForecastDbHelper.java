package io.github.gdgmiage.sunshine.android.sj.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import io.github.gdgmiage.sunshine.android.sj.database.async.ForecastSaveDbTask;

import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.*;

/**
 * Created by angebagui on 11/06/2016.
 */
public class ForecastDbHelper extends SQLiteOpenHelper {


    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY " +COMMA_SEP+
                    COLUMN_NAME_TEMP_MAX + " DOUBLE NOT NULL " +COMMA_SEP+
                    COLUMN_NAME_TEMP_MIN + " DOUBLE NOT NULL " +COMMA_SEP+
                   COLUMN_NAME_DATE_TIME + " BIGINT NOT NULL " + COMMA_SEP +
                   COLUMN_NAME_WEATHER_ID + " INTEGER NOT NULL " + COMMA_SEP +
                   COLUMN_NAME_WEATHER_MAIN + " VARCHAR(255) NOT NULL " +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Database name
     */
    private static final String DB_NAME = "Forecast.db";

    /**
     * Database version
     */
    private static final int DB_VERSION = 1;
    private static final String TAG = ForecastSaveDbTask.class.getSimpleName();

    public ForecastDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "I CREATE DATABASE FOR YOU");
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "I UPDGRADE DATABASE FOR YOU");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}
