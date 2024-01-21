package com.sample.movie.demo.config;

public final class Config {

    private Config(){}

    private static volatile int cancelTime = 120;
     
    public int getCancelTime(){
        return cancelTime;
    }

    public void setCancelTime(int time){
        cancelTime = time;
    }

}
