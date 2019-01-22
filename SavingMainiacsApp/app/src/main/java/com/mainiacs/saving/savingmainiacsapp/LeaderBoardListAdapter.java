package com.mainiacs.saving.savingmainiacsapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kent on 5/3/17.
 */

public class LeaderBoardListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LeaderboardInfo> userList;
    private int userId;

    public LeaderBoardListAdapter(Context context, ArrayList<LeaderboardInfo> userList, int userId) {
        this.context = context;
        this.userList = userList;
        this.userId = userId;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public LeaderboardInfo getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_leader_board_item, parent, false);

            holder.container = (LinearLayout) convertView.findViewById(R.id.leaderboard_container);
            holder.rank = (TextView) convertView.findViewById(R.id.leaderboard_rank);
            holder.userName = (TextView) convertView.findViewById(R.id.leaderboard_user);
            holder.totalCoins = (TextView) convertView.findViewById(R.id.leaderboard_coins);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LeaderboardInfo info = getItem(position);

        // TODO: Figure out why this doesn't work properly
//        if (info.getUserId() == userId) {
//            holder.container.setBackgroundResource(R.drawable.sm_text_view);
//        }
        holder.rank.setText(String.valueOf(info.getRank()));
        holder.userName.setText(info.getUserName());
        holder.totalCoins.setText(String.valueOf(info.getTotalCoins()));

        return convertView;
    }

    private class ViewHolder {
        LinearLayout container;
        TextView rank;
        TextView userName;
        TextView totalCoins;
    }
}
