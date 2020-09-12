/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author asd
 */
public class group {
    
    int g_id,c_id;
    String track,level,time,day;

    public group(int g_id, int c_id, String track, String level, String time, String day) {
        this.g_id = g_id;
        this.c_id = c_id;
        this.track = track;
        this.level = level;
        this.time = time;
        this.day = day;
    }

    public int getG_id() {
        return g_id;
    }

    public int getC_id() {
        return c_id;
    }

    public String getTrack() {
        return track;
    }

    public String getLevel() {
        return level;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }
    
    
}
