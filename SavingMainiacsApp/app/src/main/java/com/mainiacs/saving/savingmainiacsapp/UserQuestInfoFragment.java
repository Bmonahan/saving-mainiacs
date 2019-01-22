package com.mainiacs.saving.savingmainiacsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class UserQuestInfoFragment extends Fragment {

    private static final String ARG_QUESTS = "userQuests";
    private static final String ARG_QUEST_STATUS = "questStatus";

    private ArrayList<UserQuestInfo> questList;
    private int questStatus;
    private OnActiveQuestFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private UserQuestInfoViewAdapter adapter;

    public UserQuestInfoFragment() {
    }

    public static UserQuestInfoFragment newInstance(ArrayList<UserQuestInfo> quests, int questStatus) {
        UserQuestInfoFragment fragment = new UserQuestInfoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_QUESTS, quests);
        args.putInt(ARG_QUEST_STATUS, questStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            questList = getArguments().getParcelableArrayList(ARG_QUESTS);
            questStatus = getArguments().getInt(ARG_QUEST_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userquestinfo_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;

            // Only show the buttons to leave quest or mark as complete in active quest page
            boolean showButtons = questStatus == QuestFragment.QUEST_STATUS_ACTIVE;

            adapter = new UserQuestInfoViewAdapter(questList, showButtons, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void removeActiveQuest(int position) {
        if (questList != null) questList.remove(position);
        recyclerView.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, questList.size());

        adapter.notifyDataSetChanged();
    }

    public void updateList(ArrayList<UserQuestInfo> newQuestList) {
        if (adapter != null) {
            questList = newQuestList;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActiveQuestFragmentInteractionListener) {
            mListener = (OnActiveQuestFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActiveQuestFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnActiveQuestFragmentInteractionListener {
        void onCompleteQuest(int activeQuestId, int position);
        void onLeaveActiveQuest(int activeQuestId, int position);
    }
}
