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
public class all_information_for_attend_swimmer {

    int attend_id, s_id, num, age, g_id,c_id;
    Date day;
    String name, phone, level, gender, track;
    Date start, end;
    Time g_time;
    int g_day;

    public all_information_for_attend_swimmer(int attend_id, int s_id, int num, int age, int g_id, int c_id, Date day, String name, String phone, String level, String gender, String track, Date start, Date end, Time g_time, int g_day) {
        this.attend_id = attend_id;
        this.s_id = s_id;
        this.num = num;
        this.age = age;
        this.g_id = g_id;
        this.c_id = c_id;
        this.day = day;
        this.name = name;
        this.phone = phone;
        this.level = level;
        this.gender = gender;
        this.track = track;
        this.start = start;
        this.end = end;
        this.g_time = g_time;
        this.g_day = g_day;
    }

    public int getAttend_id() {
        return attend_id;
    }

    public int getS_id() {
        return s_id;
    }

    public int getNum() {
        return num;
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

    public Date getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
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

    public int getG_day() {
        return g_day;
    }

     
}
