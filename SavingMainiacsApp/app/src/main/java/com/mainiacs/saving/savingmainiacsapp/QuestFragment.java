package com.mainiacs.saving.savingmainiacsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class QuestFragment extends Fragment {

    private static final String URL_GET_ACTIVE_QUESTS = "https://abnet.ddns.net/mucoftware/remote/get_user_active_quests.php?";
    private static final String URL_GET_PENDING_QUESTS = "  https://abnet.ddns.net/mucoftware/remote/get_user_pending_quests.php?";
    private static final String URL_GET_REJECTED_QUESTS = "https://abnet.ddns.net/mucoftware/remote/get_user_rejected_quests.php?";
    private static final String URL_GET_COMPLETED_QUESTS = "https://abnet.ddns.net/mucoftware/remote/get_user_rewarded_quests.php?";

    public static final int QUEST_STATUS_ACTIVE = 0;
    public static final int QUEST_STATUS_PENDING = 1;
    public static final int QUEST_STATUS_REJECTED = 2;
    public static final int QUEST_STATUS_COMPLETED = 3;
    private boolean[] query_done = new boolean[4];

    private static final int NUM_QUEST_TABS = 4;

    private static final String ARG_USERNAME = "username";
    private static final String ARG_PASSWORD = "password";

    private String username;
    private String password;

    private ArrayList<UserQuestInfo> activeQuests;
    private ArrayList<UserQuestInfo> pendingQuests;
    private ArrayList<UserQuestInfo> completedQuests;
    private ArrayList<UserQuestInfo> rejectedQuests;

    private ViewPagerAdapter adapter;

    private UserQuestInfoFragment activeFragmentRef;
    private UserQuestInfoFragment pendingFragmentRef;

    public QuestFragment() {
        // Required empty public constructor
    }

    public static QuestFragment newInstance(String param1, String param2) {
        QuestFragment fragment = new QuestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, param1);
        args.putString(ARG_PASSWORD, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            password = getArguments().getString(ARG_PASSWORD);
        }
        activeQuests = new ArrayList<>();
        pendingQuests = new ArrayList<>();
        completedQuests = new ArrayList<>();
        rejectedQuests = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest, container, false);
        ViewPager questPager = (ViewPager) view.findViewById(R.id.quests_pager);

        TabLayout tabs = (TabLayout) view.findViewById(R.id.quests_tabs);
        tabs.setupWithViewPager(questPager);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(activeFragmentRef = UserQuestInfoFragment.newInstance(activeQuests, QUEST_STATUS_ACTIVE), "Active");
        adapter.addFragment(pendingFragmentRef = UserQuestInfoFragment.newInstance(pendingQuests, QUEST_STATUS_PENDING), "Pending");
        adapter.addFragment(UserQuestInfoFragment.newInstance(rejectedQuests, QUEST_STATUS_REJECTED), "Rejected");
        adapter.addFragment(UserQuestInfoFragment.newInstance(completedQuests, QUEST_STATUS_COMPLETED), "Completed");

        questPager.setAdapter(adapter);

        // Set icons for each tab
        int[] tabIcons = {R.drawable.quest_active
                , R.drawable.quest_pending
                , R.drawable.quest_rejected
                , R.drawable.quest_completed};

        for (int i = 0; i < NUM_QUEST_TABS; i++) {
            tabs.getTabAt(i).setIcon(tabIcons[i]);
        }

        getAllQuests();

        return view;
    }

    public void refreshData(int position, int type) {
        if (activeFragmentRef != null && pendingFragmentRef != null) {
            activeFragmentRef.removeActiveQuest(position);

            // Reload pending quest list if the quest was marked as completed
            if (type == MainActivity.TYPE_COMPLETE_QUEST) {
                final RequestQueue queue = Volley.newRequestQueue(getContext());
                getPendingQuests(queue, false);
                pendingFragmentRef.updateList(pendingQuests);
            }
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    private void getAllQuests() {
        // Start chain of queries to get all quests of different statuses
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        getActiveQuests(queue, true);
    }

    private void getActiveQuests(final RequestQueue queue, boolean nextQuery) {
        String url = URL_GET_ACTIVE_QUESTS + "user=" + username + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("success") == 1) {
                        activeQuests.clear();

                        JSONArray questList = jsonObject.getJSONArray("results");
                        for (int i = 0; i < questList.length(); i++) {
                            JSONObject quest = questList.getJSONObject(i);

                            int userQuestId = quest.getInt("ActiveID");
                            int rewardAmount = quest.getInt("RewardAmount");
                            String date = quest.getString("AcceptDate");

                            String questName = quest.getString("QuestName");
                            String questDescription = quest.getString("QuestDescription");
                            String charityName = quest.getString("CharityName");

                            activeQuests.add(new UserQuestInfo(userQuestId, questName, questDescription, charityName, rewardAmount, date));
                        }

                        ((UserQuestInfoFragment) adapter.mFragmentList.get(QUEST_STATUS_ACTIVE)).updateList(activeQuests);

                    } else {
                        Toast.makeText(getContext(), "Failed to get active quests.", Toast.LENGTH_LONG).show();
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
        if (nextQuery) getPendingQuests(queue, true);
    }

    private void getPendingQuests(RequestQueue queue, boolean nextQuery) {
        String url = URL_GET_PENDING_QUESTS + "user=" + username + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("success") == 1) {
                        pendingQuests.clear();

                        JSONArray questList = jsonObject.getJSONArray("results");
                        for (int i = 0; i < questList.length(); i++) {
                            JSONObject quest = questList.getJSONObject(i);

                            int userQuestId = quest.getInt("ActiveID");
                            int rewardAmount = quest.getInt("RewardAmount");
                            String date = quest.getString("AcceptDate");

                            String questName = quest.getString("QuestName");
                            String questDescription = quest.getString("QuestDescription");
                            String charityName = quest.getString("CharityName");

                            pendingQuests.add(new UserQuestInfo(userQuestId, questName, questDescription, charityName, rewardAmount, date));
                        }

                        ((UserQuestInfoFragment) adapter.mFragmentList.get(QUEST_STATUS_PENDING)).updateList(pendingQuests);

                    } else {
                        Toast.makeText(getContext(), "Failed to get pending quests.", Toast.LENGTH_LONG).show();
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
        if (nextQuery) getRejectedQuests(queue, true);
    }

    private void getRejectedQuests(RequestQueue queue, boolean nextQuery) {
        String url = URL_GET_REJECTED_QUESTS + "user=" + username + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("success") == 1) {
                        rejectedQuests.clear();

                        JSONArray questList = jsonObject.getJSONArray("results");
                        for (int i = 0; i < questList.length(); i++) {
                            JSONObject quest = questList.getJSONObject(i);

                            int userQuestId = quest.getInt("ActiveID");
                            int rewardAmount = quest.getInt("RewardAmount");
                            String date = quest.getString("AcceptDate");

                            String questName = quest.getString("QuestName");
                            String questDescription = quest.getString("QuestDescription");
                            String charityName = quest.getString("CharityName");

                            rejectedQuests.add(new UserQuestInfo(userQuestId, questName, questDescription, charityName, rewardAmount, date));
                        }

                        ((UserQuestInfoFragment) adapter.mFragmentList.get(QUEST_STATUS_REJECTED)).updateList(rejectedQuests);

                    } else {
                        Toast.makeText(getContext(), "Failed to get rejected quests.", Toast.LENGTH_LONG).show();
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
        if (nextQuery) getCompletedQuests(queue);
    }

    private void getCompletedQuests(RequestQueue queue) {
        String url = URL_GET_COMPLETED_QUESTS + "user=" + username + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("success") == 1) {
                        completedQuests.clear();

                        JSONArray questList = jsonObject.getJSONArray("results");
                        for (int i = 0; i < questList.length(); i++) {
                            JSONObject quest = questList.getJSONObject(i);

                            int userQuestId = quest.getInt("ActiveID");
                            int rewardAmount = quest.getInt("RewardAmount");
                            String date = quest.getString("AcceptDate");

                            String questName = quest.getString("QuestName");
                            String questDescription = quest.getString("QuestDescription");
                            String charityName = quest.getString("CharityName");

                            completedQuests.add(new UserQuestInfo(userQuestId, questName, questDescription, charityName, rewardAmount, date));
                        }

                        ((UserQuestInfoFragment) adapter.mFragmentList.get(QUEST_STATUS_COMPLETED)).updateList(completedQuests);

                    } else {
                        Toast.makeText(getContext(), "Failed to get completed quests.", Toast.LENGTH_LONG).show();
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
