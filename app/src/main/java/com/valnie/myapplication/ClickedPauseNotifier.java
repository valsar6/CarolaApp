package com.valnie.myapplication;

public class ClickedPauseNotifier {
    private MainActivity mainEvent;
    private boolean isTriggered;
    public ClickedPauseNotifier(MainActivity mainEvent){
        this.mainEvent = mainEvent;
        isTriggered = false;
    }
    public void playPause(){
        if(isTriggered){
            mainEvent.setCountdown();
        }
    }
}
