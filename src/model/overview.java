/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Time;

/**
 *
 * @author asd
 */
public class overview {
    String couch,track,level;
    int g_id;
    Time g_time;

    public overview(String couch, int g_id,String level, String track,  Time g_time) {
        this.couch = couch;
        this.track = track;
        this.level = level;
        this.g_id = g_id;
        this.g_time = g_time;
    }

    public String getCouch() {
        return couch;
    }

    public String getTrack() {
        return track;
    }

    public String getLevel() {
        return level;
    }

    public int getG_id() {
        return g_id;
    }

    public Time getG_time() {
        return g_time;
    }

    
    
}
