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
public class swimmer {
    
    int s_id,g_id,cost;
    String name,phone,adress,level="1",gender,s_level;
    Date start ,end,age;

    public swimmer(int cost,int s_id, String name,String s_level ,String adress, Date age,String gender,String phone, int g_id,  Date start, Date end) {
        this.cost = cost;
        this.s_id = s_id;
        this.age = age;
        this.g_id = g_id;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.gender = gender;
        this.start = start;
        this.end = end;
         this.s_level = s_level;
    }

    public int getCost() {
        return cost;
    }

    public int getS_id() {
        return s_id;
    }

    public Date getAge() {
        return age;
    }

    public int getG_id() {
        return g_id;
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

    public String getS_level() {
        return s_level;
    }
    

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
    
    
    
}
