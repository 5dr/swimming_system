/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author like
 */
public class s {

    int s_id,att_id;
    String name,phone,adress,level="1",gender,s_level;
    Date start ,end,age;

   

    public s(int att_id,int s_id, String name, String s_level, String adress, Date age, String gender,String phone, Date start, Date end) {
        this.att_id = att_id;
        this.s_id = s_id;
        this.age = age;
      
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.gender = gender;
        this.start = start;
        this.end = end;
         this.s_level = s_level;
    }

    public int getAtt_id() {
        return att_id;
    }

    public int getS_id() {
        return s_id;
    }

    public Date getAge() {
        return age;
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
