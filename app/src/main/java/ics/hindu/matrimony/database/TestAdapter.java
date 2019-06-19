package ics.hindu.matrimony.database; /**
 * Created by Varun on 7/27/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ics.hindu.matrimony.models.CommanDTO;

import java.io.IOException;
import java.util.ArrayList;

public class TestAdapter {

    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    public ArrayList<CommanDTO> getAllState(String language) {
        ArrayList<CommanDTO> stateList = new ArrayList<CommanDTO>();

        Cursor res = mDb.rawQuery("select * from states", null);


        if (res.moveToFirst()) {
            do {
                if (language.equalsIgnoreCase("hi")) {
                    stateList.add(new CommanDTO(res.getString(res.getColumnIndex("id")), res.getString(res.getColumnIndex("name_hi"))));
                } else {
                    stateList.add(new CommanDTO(res.getString(res.getColumnIndex("id")), res.getString(res.getColumnIndex("name"))));
                }


            } while (res.moveToNext());
        }
        return stateList;
    }
    public ArrayList<CommanDTO> getAllCaste(String language) {
        ArrayList<CommanDTO> casteList = new ArrayList<CommanDTO>();

        Cursor res = mDb.rawQuery("select * from caste", null);


        if (res.moveToFirst()) {
            do {
                if (language.equalsIgnoreCase("hi")) {
                    casteList.add(new CommanDTO(res.getString(res.getColumnIndex("id")), res.getString(res.getColumnIndex("name_hi"))));
                } else {
                    casteList.add(new CommanDTO(res.getString(res.getColumnIndex("id")), res.getString(res.getColumnIndex("name"))));
                }


            } while (res.moveToNext());
        }
        return casteList;
    }

    public ArrayList<CommanDTO> getAllDistrict(String stateID, String language) {
        ArrayList<CommanDTO> districtList = new ArrayList<CommanDTO>();
        String sql = "SELECT * FROM  districts  WHERE state_id= '" + stateID + "'";
        Log.e(TAG, sql);

        Cursor res = mDb.rawQuery(sql, null);


        if (res.moveToFirst()) {
            do {
                if (language.equalsIgnoreCase("hi")) {
                    districtList.add(new CommanDTO(res.getString(res.getColumnIndex("id")), res.getString(res.getColumnIndex("name_hi")), res.getString(res.getColumnIndex("state_id"))));
                } else {
                    districtList.add(new CommanDTO(res.getString(res.getColumnIndex("id")), res.getString(res.getColumnIndex("name")), res.getString(res.getColumnIndex("state_id"))));
                }



            } while (res.moveToNext());
        }
        return districtList;
    }


}