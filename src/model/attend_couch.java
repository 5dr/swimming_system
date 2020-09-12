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
public class attend_couch {
    
    int g_id;
    Date day;
    String rep_name;
    Time time;

    public attend_couch( Date day,int g_id, Time time, String rep_name) {
        this.g_id = g_id;
        this.day = day;
        this.rep_name = rep_name;
        this.time = time;
    }

    public int getG_id() {
        return g_id;
    }

    public Date getDay() {
        return day;
    }

    public String getRep_name() {
        return rep_name;
    }

    public Time getTime() {
        return time;
    }
    
    
}
