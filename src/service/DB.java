/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.all_information_for_group;
import model.attend_couch;
import model.coach;
import model.overview;
import model.swimmer;

/**
 *
 * @author asd
 */
public class DB {

    private static Connection connection;
    private static Statement statement;
//////////////////////////////connection/////////////////

    public void DB_connection() throws SQLException {

        try {
           // connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/swimming", "root", "");
            connection = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/swimming?useUnicode=yes&characterEncoding=UTF-8", "root", "");
            System.out.println("connected");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void DB_close() throws SQLException {

        try {
            connection.close();
            System.out.println("closed");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
//////////////////////////////connection/////////////////

    //////////////////////////////login//////////////////////
    public boolean login(String user, String pass) throws SQLException {
        boolean b = false;
        //SELECT * FROM `login` WHERE `user` LIKE 'samy' AND `pass` LIKE 'samy'
        try {
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT * FROM `login` WHERE `user` LIKE '" + user + "' AND `pass` LIKE '" + pass + "'");
            b = r.next();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Login :" + ex);
        }
        return b;
    }
    //////////////////////////////login//////////////////////

//SELECT couch.name,groups.g_id,groups.level,groups.track,groups.g_time FROM groups INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_day='السبت' ORDER BY g_time ASC
    //////////////////////////////overview//////////////////////
    public List<overview> over(int b) throws SQLException {

        List<overview> t = new ArrayList<overview>();
        try {
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT couch.name,groups.g_id,groups.level,groups.track,groups.g_time FROM groups INNER JOIN couch ON groups.c_id=couch.c_id WHERE g_day=" + b + " ORDER BY g_time ASC");
            while (r.next()) {
                //System.out.println(r.getString("couch.name"));
                t.add(new overview(r.getString("couch.name"), r.getInt("groups.g_id"),
                        r.getString("groups.level"), r.getString("groups.track"), r.getTime("groups.g_time")));

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "over :" + ex);
        }

        return t;
    }
    //////////////////////////////overview//////////////////////

    //////////////////////////////couch//////////////////////
    public List<coach> allcoach() throws SQLException {

        List<coach> t = new ArrayList<coach>();
        try {
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT * FROM `couch`");

            while (r.next()) {
                //System.out.println(r.getString("name"));
                t.add(new coach(r.getInt("c_id"), r.getString("name"),
                        r.getString("phone"), r.getString("address"), r.getString("c_level")));

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "coach :" + ex);
        }

        return t;
    }

    public void addcoach(String name, String phone, String address, String level) throws SQLException {

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `couch` (`c_id`, `name`, `phone`, `address`, `level`) VALUES (NULL, '" + name + "', '" + phone + "', '" + address + "', '" + level + "');");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Add coach :" + ex);
        }

    }
    //////////////////////////////couch//////////////////////

    //////////////////////////////swimmer//////////////////////
    public List<swimmer> swimmerWithgroup(int g_id) throws SQLException {

        List<swimmer> t = new ArrayList<swimmer>();
        try {
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` WHERE `g_id` = " + g_id + "");
            //System.out.println(r.next());
            while (r.next()) {
                System.out.println(r.getString("s_id"));
                t.add(new swimmer(r.getInt("s_id"), r.getString("name"),
                        r.getString("address"), r.getInt("age"),
                        r.getString("gender"), r.getString("phone"),
                        r.getInt("g_id"), r.getDate("start_date"), r.getDate("end_date")));

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "coach :" + ex);
        }

        return t;
    }

    public List<List<swimmer>> get_swimmerWithgroup(List<Integer> g_id) throws SQLException {

        List<List<swimmer>> t = new ArrayList<List<swimmer>>();

        try {
            for (int i = 0; i < g_id.size(); i++) {
                List<swimmer> t1 = new ArrayList<swimmer>();
                statement = connection.createStatement();
                ResultSet r = statement
                        .executeQuery("SELECT * FROM `swimmer` WHERE `g_id` = " + g_id.get(i) + "");
                //System.out.println(r.next());
                while (r.next()) {
                    System.out.println(r.getString("s_id"));
                    t1.add(new swimmer(r.getInt("s_id"), r.getString("name"),
                            r.getString("address"), r.getInt("age"),
                            r.getString("gender"), r.getString("phone"),
                            r.getInt("g_id"), r.getDate("start_date"), r.getDate("end_date")));

                }
                t.add(t1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "coach :" + ex);
        }

        return t;
    }

    public int addswimmer(String name, String address, int age, String gender, String phone, int group) throws SQLException {
        int Max_id = 0;
        try {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(sdf.format(now));
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.MONTH, 1);
            c.set(Calendar.DATE, 1);
            Date AfterMonth = c.getTime();
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT MAX(s_id) FROM swimmer");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                Max_id = (r.getInt("MAX(s_id)"));
            }
            Max_id++;
            System.out.println(name);
            statement.executeUpdate("INSERT INTO `swimmer` (`s_id`, `name`, `address`, `age`, `gender`, `phone`, `g_id`, `start_date`, `end_date`) VALUES (" + Max_id + ", n'" + name + "', n'" + address + "', '" + age + "', '" + gender + "', '" + phone + "', '" + group + "', '" + sdf.format(now) + "', '" + sdf.format(AfterMonth) + "');");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Add swimmer :" + ex);
        }
        return Max_id;
    }
    //////////////////////////////swimmer//////////////////////

//SELECT MAX(s_id) FROM swimmer
//INSERT INTO `swimmer` (`s_id`, `name`, `address`, `age`, `gender`, `phone`, `g_id`, `start_date`, `end_date`) VALUES (NULL, 'tata', 'el', '5', 'male', '01000000', '2026', NULL, NULL);
//SELECT g_id FROM `groups`
    //////////////////////////////Group//////////////////////
    public List<Integer> get_All_Id_Of_Group() {
        List<Integer> id = new ArrayList<Integer>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT g_id FROM `groups` ORDER by g_id ASC");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id.add(r.getInt("g_id"));
            }
        } catch (SQLException ex) {
            System.out.println(" get_All_Id_Of_Group :" + ex);
        }

        return id;
    }

    public List<Integer> get_All_Id_Of_Group_for_couch(int couch_id) {
        List<Integer> id = new ArrayList<Integer>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT g_id FROM `groups` WHERE c_id=" + couch_id + " ORDER by g_id ASC");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id.add(r.getInt("g_id"));
            }
        } catch (SQLException ex) {
            System.out.println(" get_All_Id_Of_Group_for_couch :" + ex);
        }

        return id;
    }

    public List<Time> All_time_of_group_without_repeat() {
        List<Time> id = new ArrayList<Time>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT DISTINCT g_time FROM `groups`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id.add(r.getTime("g_time"));
            }
        } catch (SQLException ex) {
            System.out.println(" All_time_of_group_without_repeat :" + ex);
        }

        return id;
    }

    public List<all_information_for_group> get_all_group_with_time(Time t, boolean b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND groups.g_day = " + b + "");
            while (r.next()) {
                System.out.println(r.getInt("g_id"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), t, r.getBoolean("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" all_information_for_group :" + ex);
        }
        return id;
    }

//SELECT DISTINCT g_time FROM `groups`
//SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='03:00'
//////////////////////////////Group//////////////////////
    //////////////////////////////attend_couch//////////////////////
    public List<attend_couch> attend_couch(int couch) {

        List<attend_couch> id = new ArrayList<attend_couch>();
        try {
            statement = connection.createStatement();
            System.out.println(couch);
            ResultSet r = statement
                    .executeQuery("SELECT attend_couch.absent_day, attend_couch.g_id,groups.g_time,couch.name FROM attend_couch INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON attend_couch.replace_c_id=couch.c_id WHERE groups.c_id=" + couch + "");
            while (r.next()) {
                id.add(new attend_couch(r.getDate("attend_couch.absent_day"), r.getInt("attend_couch.g_id"), r.getTime("groups.g_time"), r.getString("couch.name")));
                System.out.println(r.getInt("attend_couch.g_id"));
            }
        } catch (SQLException ex) {
            System.out.println(" attend_couch :" + ex);
        }

        return id;
    }

    public void Add_attend_couch(int swimmer) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `attend_swimmer` (`attend_id`, `s_id`, `day`) VALUES (NULL, '" + swimmer + "', '" + sdf.format(now) + "');");
        } catch (SQLException ex) {
            System.out.println(" Add_attend_swimmer :" + ex);
        }

    }

    public void Add_attend_couch_replece(int g_id) {
        Time t = null;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        try {
            ResultSet r = statement
                    .executeQuery("SELECT g_time FROM `groups` WHERE `g_id` = " + g_id + "");
            while (r.next()) {
                t = r.getTime("g_time");
            }

            ResultSet r1 = statement
                    .executeQuery("SELECT * FROM `groups` WHERE `g_time` != '" + t + "'");
            while (r.next()) {
                t = r.getTime("g_time");
            }
        } catch (SQLException ex) {
            System.out.println(" Add_attend_couch_replece :" + ex);
        }

    }

//SELECT * FROM `groups` WHERE `g_time` != '03:00'
    //////////////////////////////attend_couch//////////////////////
    //////////////////////////////attend_swimmer//////////////////////
    public void Add_attend_swimmer(int swimmer, int n) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `attend_swimmer` (`attend_id`, `s_id`, `day`, `num`) VALUES (NULL, '" + swimmer + "', '" + sdf.format(now) + "', '" + n + "');");
        } catch (SQLException ex) {
            System.out.println(" Add_attend_swimmer :" + ex);
        }

    }

    public void delet_attend_swimmer(int swimmer) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `attend_swimmer` WHERE s_id=" + swimmer + " && day='" + sdf.format(now) + "'");
        } catch (SQLException ex) {
            System.out.println(" delet_attend_swimmer :" + ex);
        }
    }

    public boolean get_att(int id, int n) {

        boolean b = false;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` WHERE `s_id` = " + id + " AND `num` = " + n + "");
            b = r.next();

        } catch (SQLException ex) {
            System.out.println(" get_att :" + ex);
        }
        return b;
    }
    //////////////////////////////attend_swimmer//////////////////////

    //////////////////////////////note//////////////////////
    public void Add_note_swimmer(int swimmer, String n) {

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `note`(`note_id`, `note`, `s_id`) VALUES (NULL, '" + n + "', '" + swimmer + "');");
        } catch (SQLException ex) {
            System.out.println(" Add_not_swimmer :" + ex);
        }

    }
    
     public String get_note_id(int id) {

        boolean b = false;
        String s=null;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `note` WHERE `s_id` = "+id+"");
            while (r.next()) {
                s = r.getString("note");
            }

        } catch (SQLException ex) {
            System.out.println(" get_note_id :" + ex);
        }
        return s;
    }
    
     public void update_note(int id,String n){
     
      boolean b = false;
      String t = null;
        try {
//            statement = connection.createStatement();
//
//            ResultSet r = statement
//                    .executeQuery("SELECT * FROM `note` WHERE `s_id` = "+id+"");
//            while (r.next()) {
//                t = r.getString("note");
//            }
            System.out.println(id);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE `note` SET `note` = '"+n +"' WHERE `note`.`s_id` = "+id+";");

        } catch (SQLException ex) {
            System.out.println(" get_note_id :" + ex);
        }
     }
     
      public boolean is_note_exist(int id){
                boolean b = false;

                try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `note` WHERE `s_id` = "+id+"");
            
            b =r.next();
            
         
        } catch (SQLException ex) {
            System.out.println(" get_note_id :" + ex);
        }
                return b;
      }
    
//SELECT * FROM `note` WHERE `s_id` = 3000
//INSERT INTO `note`(`note_id`, `note`, `s_id`) VALUES ([value-1],[value-2],[value-3])
//SELECT * FROM `attend_swimmer` WHERE 1
//INSERT INTO `attend_swimmer` (`attend_id`, `s_id`, `day`) VALUES (NULL, '2029', '2020-09-05');
//SELECT attend_couch.absent_day, attend_couch.g_id,groups.g_time,couch.name FROM attend_couch INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON attend_couch.replace_c_id=couch.c_id
//SELECT attend_couch.absent_day, attend_couch.g_id,groups.g_time,attend_couch.replace_c_id FROM attend_couch INNER JOIN groups ON attend_couch.g_id=groups.g_id
//SELECT * FROM `attend_couch` WHERE 1
}
