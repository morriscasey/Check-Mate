package com.checkmate.checkmate;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class HomeFragment extends Fragment {
    private String mUser;

    Button mButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity mainActivity = (MainActivity)getActivity();
        mButton = (Button)view.findViewById(R.id.toggleLocations);

        if(mainActivity.mUser == "bob"){
            mUser = "Mary";
        }else {
            mUser = "Bob";
        }

        TextView textView = (TextView)view.findViewById(R.id.notification);
        textView.setText("Accepted date with "+mUser+".");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocations(v);
            }
        });

        return view;
    }

    public void sendLocations(View view){
        if(mButton.getText() == "END DATE"){
            mButton.setText("START DATE");
        }else {
            mButton.setText("END DATE");
        }

    }

}
