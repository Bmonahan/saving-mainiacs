package com.mainiacs.saving.savingmainiacsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private static final String URL_GET_DONATION_RATES = "https://abnet.ddns.net/mucoftware/remote/get_user_donationrate.php?";
    private static final String URL_UPDATE_DONATION_RATES = "https://abnet.ddns.net/mucoftware/remote/update_user_donations.php?";
    private static final String URL_GET_CHARITIES = "https://abnet.ddns.net/mucoftware/remote/get_charity_list.php";

    private static final String ARG_USERNAME = "userName";
    private static final String ARG_PASSWORD = "password";

    private String userName;
    private String password;

    // key = charity id, value = charity name
    private SparseArray<String> availableCharityList;

    // key = charity id, value = donation percentage
    private SparseIntArray userDonationRates;

    private SeekBar rateSeekBar;
    private ListView charityListView;
    private Button addCharity;
    private PieChart donationChart;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String userName, String password) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, userName);
        args.putString(ARG_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USERNAME);
            password = getArguments().getString(ARG_PASSWORD);
        }
        availableCharityList = new SparseArray<>();
        userDonationRates = new SparseIntArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        donationChart = (PieChart) view.findViewById(R.id.donation_rate_chart);
        addCharity = (Button) view.findViewById(R.id.donation_add_charity);
        charityListView = (ListView) view.findViewById(R.id.donation_charity_list);
        rateSeekBar = (SeekBar) view.findViewById(R.id.donation_rate_amount);

        rateSeekBar.setVisibility(View.INVISIBLE);
        charityListView.setVisibility(View.INVISIBLE);

        getUserDonationRates();

        return view;
    }

    private void populatePage() {

        if (userDonationRates.size() < 5) {
            addCharity.setVisibility(View.VISIBLE);
        } else {
            addCharity.setVisibility(View.INVISIBLE);
        }


        donationChart.setOnTouchListener(null);
        donationChart.setDescription(null);

        Legend legend = donationChart.getLegend();

        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < userDonationRates.size(); i++) {
            int charityId = userDonationRates.keyAt(i);
            int rate = userDonationRates.get(charityId);
            String name = availableCharityList.get(charityId);

            entries.add(new PieEntry((float) rate, i));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Organizations");
        int[] colors = {ContextCompat.getColor(getContext(), R.color.blue1)
                , ContextCompat.getColor(getContext(), R.color.blue2)
                , ContextCompat.getColor(getContext(), R.color.blue3)
                , ContextCompat.getColor(getContext(), R.color.blue4)
                , ContextCompat.getColor(getContext(), R.color.blue5)
        };

        dataSet.setColors(colors);
        dataSet.setDrawValues(true);

        PieData data = new PieData(dataSet);
        donationChart.setData(data);
        donationChart.invalidate();
    }

    void getCharityList() {
        String url = URL_GET_CHARITIES;
        final RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject.toString());
                try {
                    if (jsonObject.getInt("success") == 1) {
                        availableCharityList.clear();

                        ArrayList<String> charities = new ArrayList<>();

                        JSONArray charityList = jsonObject.getJSONArray("results");
                        for (int i = 0; i < charityList.length(); i++) {
                            JSONObject charity = charityList.getJSONObject(i);

                            int id = charity.getInt("CharityID");
                            String name = charity.getString("CharityName");

                            // Add to available charity list if not yet in user list
                            if (userDonationRates.get(id) == 0) {
                                availableCharityList.put(id, name);
                                charities.add(name);
                            }
                        }

                        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, charities);
                        charityListView.setAdapter(adapter);

                    } else {
                        Toast.makeText(getContext(), "Failed to get charity list.", Toast.LENGTH_LONG).show();
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

    void getUserDonationRates() {
        String url = URL_GET_DONATION_RATES + "user=" + userName + "&password=" + password;
        final RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject.toString());
                try {
                    if (jsonObject.getInt("success") == 1) {
                        userDonationRates.clear();

                        JSONArray rates = jsonObject.getJSONArray("results");
                        for (int i = 0; i < rates.length(); i++) {
                            JSONObject item = rates.getJSONObject(i);

                            int id = item.getInt("CharityID");
                            int percent = item.getInt("Percent");

                            userDonationRates.put(id, percent);
                        }

                        // Get charity list after getting user data
                        populatePage();
                        getCharityList();

                    } else {
                        Toast.makeText(getContext(), "Failed to get charity list.", Toast.LENGTH_LONG).show();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
