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
public class all_information_for_group {
    
    int g_id,c_id;
    String track,g_level,name,phone,address,c_level;
    Time time;
    int day;

    public all_information_for_group(int g_id, int c_id, String track, String g_level, String name, String phone, String address, String c_level, Time time, int day) {
        this.g_id = g_id;
        this.c_id = c_id;
        this.track = track;
        this.g_level = g_level;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.c_level = c_level;
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

    public String getG_level() {
        return g_level;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getC_level() {
        return c_level;
    }

    public Time getTime() {
        return time;
    }

    public int getDay() {
        return day;
    }

   
    
}
