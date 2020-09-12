/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author asd
 */
public class coach {
    
    int c_id;
    String name,phone,adress,level;

    public coach(int c_id, String name, String phone, String adress, String level) {
        this.c_id = c_id;
        this.name = name;
        this.phone = phone;
        this.adress = adress;
        this.level = level;
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
    
    
    
}
