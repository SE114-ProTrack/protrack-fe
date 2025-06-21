package com.example.protrack.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.protrack.R;

import com.example.protrack.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private enum Tab {OVERVIEW, ANALYTICS}

    private Tab currentTab = Tab.OVERVIEW;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showOverview();

        binding.overviewTab.setOnClickListener(v -> showOverview());
        binding.analyticsTab.setOnClickListener(v -> showAnalytics());

        replaceFragment(new TabOverviewFragment());
    }

    private void showOverview() {
        if (currentTab == Tab.OVERVIEW) return;
        currentTab = Tab.OVERVIEW;
        binding.overviewTab.setBackground(
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_contained_14));
        binding.analyticsTab.setBackground(
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_transparent));
        binding.overviewTab.setTextColor(getResources().getColor(R.color.colorOnPrimary, null));
        binding.analyticsTab.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        binding.overviewTab.setAlpha(1.0F);
        binding.analyticsTab.setAlpha(0.5F);

        replaceFragment(new TabOverviewFragment());
    }

    private void showAnalytics() {
        if (currentTab == Tab.ANALYTICS) return;
        currentTab = Tab.ANALYTICS;
        binding.analyticsTab.setBackground(
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_contained_14));
        binding.overviewTab.setBackground(
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_transparent));
        binding.analyticsTab.setTextColor(getResources().getColor(R.color.colorOnPrimary, null));
        binding.overviewTab.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        binding.overviewTab.setAlpha(0.5F);
        binding.analyticsTab.setAlpha(1.0F);

        replaceFragment(new TabAnalyticsFragment());
    }

    private void replaceFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.contentContainer, fragment)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}