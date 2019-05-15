package com.valnie.myapplication;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class PauseFragment extends Fragment implements publicInterfaces.pauseButtonCallback {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.pause_layout, container,false);
        ImageButton continueButton = (ImageButton) view.findViewById(R.id.continuebutton);
        ImageButton quitButton = (ImageButton) view.findViewById(R.id.quitbutton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedPause();

            }

        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(1);
            }
        });
        return view;
    }

    public void clickedPause() {
        System.out.println("Ciao!!");
    }

}

