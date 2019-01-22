package com.mainiacs.saving.savingmainiacsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeaderBoardFragment extends Fragment {

    private static final String URL_GET_LEADERBOARD = "https://abnet.ddns.net/mucoftware/remote/get_user_rank.php";
    private static final String ARG_USER_ID = "userId";

    private int userId;
    private ListView leaderboardList;
    private ArrayList<LeaderboardInfo> userList;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    public static LeaderBoardFragment newInstance(int userId) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }
        userList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);

        leaderboardList = (ListView) view.findViewById(R.id.leaderboard_list);
        getLeaderboard();

        return view;
    }

    private void getLeaderboard() {
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = URL_GET_LEADERBOARD;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("success") == 1) {
                        userList.clear();

                        JSONArray resultList = jsonObject.getJSONArray("results");
                        for (int i = 0; i < resultList.length(); i++) {
                            JSONObject userInfo = resultList.getJSONObject(i);

                            int userId = userInfo.getInt("UserID");
                            int rank = userInfo.getInt("Rank");
                            String userName = userInfo.getString("UserName");
                            int totalCoins = userInfo.getInt("SumQuantity");

                            userList.add(new LeaderboardInfo(userId, rank, userName, totalCoins));
                        }

                        // Set up leader board after getting data
                        LeaderBoardListAdapter adapter = new LeaderBoardListAdapter(getContext(), userList, userId);
                        leaderboardList.setAdapter(adapter);

                    } else {
                        Toast.makeText(getContext(), "Failed to get user rankings.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
        queue.add(jsonObjectRequest);
    }
}
