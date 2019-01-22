package com.mainiacs.saving.savingmainiacsapp;

/**
 * Created by zax on 4/21/17.
 */

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile implements Parcelable {

    private int id;
    private String user_name;
    private String password;
    private String login_name;
    private String email_address;
    private int temp_steps;
    private int day_steps;
    private int month_steps;
    private int total_steps;
    private double last_lat;
    private double last_long;
    private int coins;
    private int total_coins;
    Bitmap Picture;
    List<Quest> activeQuests;

    public int ID() { return id; }
    public String UserName() { return user_name; }
    public void UserName(String value) { user_name = value; }
    public String Password() { return password; }
    public void Password(String value) { password = value; }
    public String LoginName() { return login_name; }
    public String Email() { return email_address; }
    public int TempSteps() { return temp_steps; }
    public void TempSteps(int value) { temp_steps =+ value; }
    public int DaySteps() { return day_steps; }
    public int MonthSteps() { return month_steps; }
    public int TotalSteps() { return total_steps; }
    public double Latitude() { return last_lat; }
    public double Longitude() { return last_long; }
    public int Coins() { return coins; }
    public int TotalCoins() { return total_coins; }
    public void ResetTempSteps() { temp_steps = 0;}

    public UserProfile() {

    }

    public UserProfile(JSONObject obj) {
        try {
            JSONArray jarr = obj.optJSONArray("results");
            JSONObject data = jarr.getJSONObject(0);
            id = data.getInt("UserID");
            user_name = data.getString("UserName");
            login_name = data.getString("LoginName");
            email_address = data.getString("EmailAddress");
            day_steps = data.getInt("DaySteps");
            month_steps = data.getInt("MonthSteps");
            total_steps = data.getInt("TotalSteps");
            last_lat = data.getDouble("LastLatitude");
            last_long = data.getDouble("LastLongitude");
            coins = data.getInt("Coins");
            total_coins = data.getInt("TotalCoins");

            temp_steps = 0;
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        activeQuests = new ArrayList<>();
    }

    protected UserProfile(Parcel in) {
        id = in.readInt();
        user_name = in.readString();
        password = in.readString();
        login_name = in.readString();
        email_address = in.readString();
        day_steps = in.readInt();
        month_steps = in.readInt();
        total_steps = in.readInt();
        last_lat = in.readDouble();
        last_long = in.readDouble();
        coins = in.readInt();
        total_coins = in.readInt();
        activeQuests = in.createTypedArrayList(Quest.CREATOR);
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(user_name);
        dest.writeString(password);
        dest.writeString(login_name);
        dest.writeString(email_address);
        dest.writeInt(day_steps);
        dest.writeInt(month_steps);
        dest.writeInt(total_steps);
        dest.writeDouble(last_lat);
        dest.writeDouble(last_long);
        dest.writeInt(coins);
        dest.writeInt(total_coins);
        dest.writeTypedList(activeQuests);
    }
}
