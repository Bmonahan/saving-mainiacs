package com.mainiacs.saving.savingmainiacsapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by zax on 4/12/17.
 */

public class DataManager
        implements Parcelable {
    public List<Charity> charities;
    public UserProfile userProfile;

    public DataManager() {
        charities = new LinkedList<Charity>();
    }

    protected DataManager(Parcel in) {
        charities = in.createTypedArrayList(Charity.CREATOR);
        userProfile = (UserProfile)in.readParcelable(UserProfile.class.getClassLoader());
    }

    public static final Creator<DataManager> CREATOR = new Creator<DataManager>() {
        @Override
        public DataManager createFromParcel(Parcel in) {
            return new DataManager(in);
        }

        @Override
        public DataManager[] newArray(int size) {
            return new DataManager[size];
        }
    };

    public Charity FindCharityByID(int id){
        for(Charity charity : charities) {
            if(charity.ID() == id) {
                return charity;
            }
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(charities);
        dest.writeParcelable(userProfile, flags);
    }

}
