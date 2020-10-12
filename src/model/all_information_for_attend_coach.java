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
public class all_information_for_attend_coach {
     //SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id

    int att_c_id,g_id,c_id,r_id,day=0;
    Date date_absent;
    String track,level,name,c_L,phone;
    Time time;

    public all_information_for_attend_coach(int att_c_id, int g_id, int c_id, int r_id, Date date_absent, String track, String level, String name, String c_L, String phone, Time time) {
        this.att_c_id = att_c_id;
        this.g_id = g_id;
        this.c_id = c_id;
        this.r_id = r_id;
        this.date_absent = date_absent;
        this.track = track;
        this.level = level;
        this.name = name;
        this.c_L = c_L;
        this.phone = phone;
        this.time = time;
    }

    public int getAtt_c_id() {
        return att_c_id;
    }

    public void setAtt_c_id(int att_c_id) {
        this.att_c_id = att_c_id;
    }

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getDate_absent() {
        return date_absent;
    }

    public void setDate_absent(Date date_absent) {
        this.date_absent = date_absent;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getC_L() {
        return c_L;
    }

    public void setC_L(String c_L) {
        this.c_L = c_L;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
}

