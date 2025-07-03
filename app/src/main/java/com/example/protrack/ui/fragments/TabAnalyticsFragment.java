package com.example.protrack.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.protrack.R;
import com.example.protrack.data.ApiClient;
import com.example.protrack.data.Productivity;
import com.example.protrack.data.SharedPrefsManager;
import com.example.protrack.model.response.PersonalProductivityResponse;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabAnalyticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabAnalyticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LineChart lineChart;

    public TabAnalyticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabAnalyticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabAnalyticsFragment newInstance(String param1, String param2) {
        TabAnalyticsFragment fragment = new TabAnalyticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_analytics, container, false);
        lineChart = view.findViewById(R.id.lineChart);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadProductivityData();
    }

    private void loadProductivityData() {
        String token = SharedPrefsManager.getInstance(getContext()).getToken();
        if (token == null) {
            Toast.makeText(getContext(), "Need to log in!", Toast.LENGTH_SHORT).show();
            return;
        }

        String authHeader = "Bearer " + token;

        Productivity api = ApiClient.getInstance().create(Productivity.class);
        api.getMyProductivity(authHeader).enqueue(new Callback<List<PersonalProductivityResponse>>() {
            @Override
            public void onResponse(Call<List<PersonalProductivityResponse>> call, Response<List<PersonalProductivityResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    showChart(response.body());
                } else {
                    Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PersonalProductivityResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void showChart(List<PersonalProductivityResponse> data) {
        // Nhóm dữ liệu theo project
        HashMap<String, List<PersonalProductivityResponse>> projectMap = new HashMap<>();
        for (PersonalProductivityResponse p : data) {
            String key = p.getProjectName();
            if (!projectMap.containsKey(key)) projectMap.put(key, new ArrayList<>());
            projectMap.get(key).add(p);
        }

        List<ILineDataSet> dataSets = new ArrayList<>();
        int[] chartColors = { Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.CYAN };
        int colorIndex = 0;

        for (String project : projectMap.keySet()) {
            List<PersonalProductivityResponse> productivityList = projectMap.get(project);
            // Sắp xếp theo ngày (tăng dần)
            Collections.sort(productivityList, Comparator.comparing(PersonalProductivityResponse::getLastUpdated));
            List<Entry> entries = new ArrayList<>();
            int x = 1;
            for (PersonalProductivityResponse p : productivityList) {
                entries.add(new Entry(x, p.getCompletedTasks()));
                x++;
            }
            LineDataSet dataSet = new LineDataSet(entries, project);
            dataSet.setColor(chartColors[colorIndex % chartColors.length]);
            dataSet.setCircleColor(chartColors[colorIndex % chartColors.length]);
            dataSet.setLineWidth(2f);
            dataSet.setValueTextSize(10f);
            dataSets.add(dataSet);
            colorIndex++;
        }

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        Description desc = new Description();
        desc.setText("Completed Tasks by Project & Date");
        lineChart.setDescription(desc);
        lineChart.animateY(800);
    }
}