/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author asd
 */
public class all_information_for_swimmer {
 
     int s_id,age,g_id,c_id;
    String name,phone,adress,level,gender,track;
    Date start ,end;
    Time g_time;
    int day;

    public all_information_for_swimmer(int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track,String level, Date start, Date end, Time g_time, int day) {
        this.s_id = s_id;
        this.age = age;
        this.g_id = g_id;
        this.c_id = c_id;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.gender = gender;
        this.track = track;
        this.start = start;
        this.end = end;
        this.g_time = g_time;
        this.day = day;
        this.level = level;
    }

    public int getS_id() {
        return s_id;
    }

    public int getAge() {
        return age;
    }

    public int getG_id() {
        return g_id;
    }

    public int getC_id() {
        return c_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

    public String getLevel() {
        return level;
    }

    public String getGender() {
        return gender;
    }

    public String getTrack() {
        return track;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Time getG_time() {
        return g_time;
    }

    public int getDay() {
        return day;
    }

  
    
}
