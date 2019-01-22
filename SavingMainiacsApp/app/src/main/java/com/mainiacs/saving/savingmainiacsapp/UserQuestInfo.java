package com.mainiacs.saving.savingmainiacsapp;

import android.os.Parcel;
import android.os.Parcelable;

public class UserQuestInfo implements Parcelable {

    private int activeQuestId;
    private int questId;
    private int charityId;
    private int rewardAmount;
    private String date;

    private String questName;
    private String questDescription;
    private String charityName;

    public UserQuestInfo(int activeQuestId, int questId, int charityId, int rewardAmount, String date) {
        this.activeQuestId = activeQuestId;
        this.questId = questId;
        this.charityId = charityId;
        this.rewardAmount = rewardAmount;
        this.date = date;
    }

    public UserQuestInfo(int activeQuestId, String questName, String questDescription, String charityName, int rewardAmount, String date) {
        this.activeQuestId = activeQuestId;
        this.questName = questName;
        this.questDescription = questDescription;
        this.charityName = charityName;
        this.rewardAmount = rewardAmount;
        this.date = date;
    }

    public String toString() {
        return "UserQuestId: " + this.activeQuestId
                + ", QuestName: " + this.questName
                + ", CharityName: " + this.charityName
                + ", Amount: " + this.rewardAmount
                + ", Date: " + this.date;
    }

    public static final Creator<UserQuestInfo> CREATOR = new Creator<UserQuestInfo>() {
        @Override
        public UserQuestInfo createFromParcel(Parcel in) {
            return new UserQuestInfo(in);
        }

        @Override
        public UserQuestInfo[] newArray(int size) {
            return new UserQuestInfo[size];
        }
    };

    protected UserQuestInfo(Parcel in) {
        activeQuestId = in.readInt();
        questId = in.readInt();
        charityId = in.readInt();
        rewardAmount = in.readInt();
        date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(activeQuestId);
        dest.writeInt(questId);
        dest.writeInt(charityId);
        dest.writeInt(rewardAmount);
        dest.writeString(date);
    }

    public int getActiveQuestId() {
        return activeQuestId;
    }

    public void setActiveQuestId(int activeQuestId) {
        this.activeQuestId = activeQuestId;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(int rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public void setQuestDescription(String questDescription) {
        this.questDescription = questDescription;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }
}
