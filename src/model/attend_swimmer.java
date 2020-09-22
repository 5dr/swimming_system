/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author asd
 */
public class attend_swimmer {
    
    int attend_id,s_id,num;
    Date day;

    public attend_swimmer(int attend_id, int s_id, int num, Date day) {
        this.attend_id = attend_id;
        this.s_id = s_id;
        this.num = num;
        this.day = day;
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

    public Date getDay() {
        return day;
    }
    
    
    
}
