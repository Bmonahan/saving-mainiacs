package com.mainiacs.saving.savingmainiacsapp;


import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by zax on 4/12/17.
 */

public class Charity implements Parcelable{

    private int id;
    private String name;
    private String address1;
    private String address2;
    private double latitude;
    private double longitude;
    private String phone;
    private List<Quest> quests;

    public Charity(int id, String name, String address, double latitude, double longitude) {
        this.id = id;
        this.name = name;

        String[] spltAddr = address.split(",");
        this.address1 = spltAddr[0];

        if(spltAddr.length > 0)
            this.address2 = spltAddr[1];
        if(spltAddr.length > 1)
            this.address2 += ", " + spltAddr[2];

        this.longitude = longitude;
        this.latitude = latitude;

        quests = new LinkedList<Quest>();
    }

    public Charity(JSONObject obj) {
        try {
            this.id = obj.getInt("CharityID");
            this.name = obj.getString("CharityName");
            String addr = obj.getString("Address");
            String[] spltAddr = addr.split(",");
            this.address1 = spltAddr[0];
            if(spltAddr.length > 0) {
                this.address2 = spltAddr[1];
            }
            this.longitude = obj.getDouble("Longitude");
            this.latitude = obj.getDouble("Latitude");
            this.phone = obj.getString("PhoneNumber");
            this.quests = new LinkedList<Quest>();
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private Charity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        phone = in.readString();
        quests = in.createTypedArrayList(Quest.CREATOR);
    }

    public static final Creator<Charity> CREATOR = new Creator<Charity>() {
        @Override
        public Charity createFromParcel(Parcel in) {
            return new Charity(in);
        }

        @Override
        public Charity[] newArray(int size) {
            return new Charity[size];
        }
    };

    public int ID() {
        return id;
    }
    public String Name() {
        return name;
    }


    public String Address1() {
        return address1.trim();
    }

    public String Address2() { return address2.trim(); }



    public String Phone() {
        String s = phone;
        String numAsString = "";
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == 48 | c == 49 | c == 50 | c == 51 | c == 52 | c == 53 | c == 54 | c == 55 | c == 56 | c == 57){
                numAsString += c;
            }
        }

        String finalNum = "";
        s = numAsString;
        for (int i = 0; i < s.length(); i++){
            if(i == 0){
                finalNum += "(";
            } else if (i == 3){
                finalNum += ")";
            }else if(i == 6){
                finalNum += "-";
            }

            char c = s.charAt(i);
            finalNum += c;
        }
        return finalNum; }


    public double Latitude() {
        return latitude;
    }
    public double Longitude() {
        return longitude;
    }
    public void Name(String name) {
        this.name = name;
    }
    public void Address1(String address) {
        this.address1 = address;
    }
    public void Latitude(double latitude) {
        this.latitude = latitude;
    }
    public void Longitude(double longitude) {
        this.longitude = longitude;
    }
    public void Phone(String phone) {this.phone = phone;}

    public void AddQuest(Quest q) { quests.add(q); }
    public void RemoveQuest(Quest q) { quests.remove(q); }
    public List<Quest> GetAllQuests() { return quests; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(phone);
        dest.writeTypedList(quests);
    }

    public int GetNumberOfCurrentQuests() {
        return quests.size();
    }

    @Override
    public String toString() {
        String str = "Name: " + Name() + "\n";
        str += "Address: " + Address1() + ", " + Address2() + "\n";
        str += "LatLong: " + Double.toString(Latitude()) + ", " + Double.toString(Longitude()) + "\n";
        return str;
    }
}
