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
    
    int s_id,age,g_id;
    String name,phone,adress,level="1",gender;
    Date start ,end;

    public swimmer(int s_id, String name,String adress, int age,String gender,String phone, int g_id,  Date start, Date end) {
        this.s_id = s_id;
        this.age = age;
        this.g_id = g_id;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.gender = gender;
        this.start = start;
        this.end = end;
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

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
    
    
    
}
