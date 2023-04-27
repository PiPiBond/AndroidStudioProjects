package com.example.androidsession_05.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidsession_05.R;


public class DiscoverFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_discover, container, false);
            TextView tabTitle = getActivity().findViewById(R.id.tabTitle);
            tabTitle.setText("发现");
            RelativeLayout topBar = getActivity().findViewById(R.id.topBar);
            topBar.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }
}