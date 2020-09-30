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

    int g_id, attend_id, rep_name;
    Date day;

    public attend_couch(int g_id, int attend_id, Date day, int rep_name) {
        this.g_id = g_id;
        this.attend_id = attend_id;
        this.day = day;
        this.rep_name = rep_name;
    }

    public int getG_id() {
        return g_id;
    }

    public int getAttend_id() {
        return attend_id;
    }

    public Date getDay() {
        return day;
    }

    public int getRep_name() {
        return rep_name;
    }

}
