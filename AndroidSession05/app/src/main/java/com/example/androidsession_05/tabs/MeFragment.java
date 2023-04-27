package com.example.androidsession_05.tabs;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.androidsession_05.R;

public class MeFragment extends Fragment implements View.OnClickListener {

    View view;
    Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = this.getActivity();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            RelativeLayout topBar = getActivity().findViewById(R.id.topBar);
            topBar.setVisibility(View.INVISIBLE);

            // 监听按钮
            Button buttonComponent = view.findViewById(R.id.buttonComponent);
            Button buttonString = view.findViewById(R.id.buttonString);
            buttonComponent.setOnClickListener(this);
            buttonString.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button button = (Button) v;
            int buttonId = button.getId();
            switch (buttonId) {
                case R.id.buttonComponent:
                    toastUserInfoView();
                    break;
                case R.id.buttonString:
                    Toast.makeText(view.getContext(), "WOOZIE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    private void toastUserInfoView() {
        View meBinding = View.inflate(view.getContext(), R.layout.user_info, null);

        Toast toast = new Toast(view.getContext());
        toast.setView(meBinding);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}