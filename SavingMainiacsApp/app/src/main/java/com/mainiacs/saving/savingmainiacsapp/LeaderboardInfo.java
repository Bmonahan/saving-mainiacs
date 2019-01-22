package com.mainiacs.saving.savingmainiacsapp;

/**
 * Created by kent on 5/3/17.
 */

public class LeaderboardInfo {

    int userId;
    int rank;
    String userName;
    int totalCoins;

    public LeaderboardInfo(int userId, int rank, String userName, int totalCoins) {
        this.userId = userId;
        this.rank = rank;
        this.userName = userName;
        this.totalCoins = totalCoins;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
    }
}
