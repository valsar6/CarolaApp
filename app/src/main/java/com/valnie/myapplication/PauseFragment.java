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

public class PauseFragment extends Fragment  {
    public interface PauseButtonCallback{
        public void clickedPause();

    }
    private PauseButtonCallback myCallback;


    private  PauseButtonCallback mycallback;

    public PauseFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof PauseButtonCallback)
            myCallback = (PauseButtonCallback) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton continueButton = (ImageButton) view.findViewById(R.id.continuebutton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCallback != null) myCallback.clickedPause();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.pause_layout, container,false);

        ImageButton quitButton = (ImageButton) view.findViewById(R.id.quitbutton);

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

