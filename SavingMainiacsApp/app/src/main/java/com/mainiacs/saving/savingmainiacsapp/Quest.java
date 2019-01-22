package com.mainiacs.saving.savingmainiacsapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zax on 4/12/17.
 */

public class Quest implements Parcelable {

    private int ID;
    private int charity_ID;
    private String name;
    private int payment;
    private int quantity;
    private String desc;
    private String dropoff_location;
    private double latitude;
    private double longitude;

    public Quest(JSONObject data) {
        try {
            ID = data.getInt("QuestID");
            charity_ID = data.getInt("CharityID");
            name = data.getString("QuestName");
            payment = data.getInt("Payment");
            quantity = data.getInt("Quantity");
            desc = data.getString("QuestDescription");
            dropoff_location = data.getString("DropOffLocation");
            latitude = data.getDouble("DropOffLat");
            longitude = data.getDouble("DropOffLong");
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    protected Quest(Parcel in) {
        ID = in.readInt();
        charity_ID = in.readInt();
        name = in.readString();
        payment = in.readInt();
        quantity = in.readInt();
        desc = in.readString();
        dropoff_location = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public int ID() { return ID; }
    public int CharityID() { return charity_ID; }
    public String Name() { return name; }
    public int Payment() { return payment; }
    public int Quantity() { return quantity; }
    public String Description() { return desc; }
    public String DropOffLocation() { return dropoff_location; }
    public double Latitude() { return latitude; }
    public double Longitude() { return longitude; }

    public static final Creator<Quest> CREATOR = new Creator<Quest>() {
        @Override
        public Quest createFromParcel(Parcel in) {
            return new Quest(in);
        }

        @Override
        public Quest[] newArray(int size) {
            return new Quest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(charity_ID);
        dest.writeString(name);
        dest.writeInt(payment);
        dest.writeInt(quantity);
        dest.writeString(desc);
        dest.writeString(dropoff_location);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
