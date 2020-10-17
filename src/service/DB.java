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
import model.all_information_for_attend_coach;
import model.all_information_for_attend_swimmer;
import model.all_information_for_group;
import model.all_information_for_swimmer;
import model.attend_couch;
import model.attend_swimmer;
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
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swimming?useUnicode=yes&characterEncoding=UTF-8", "root", "");
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
            statement.executeUpdate("INSERT INTO `couch` (`c_id`, `name`, `phone`, `address`, `c_level`) VALUES (NULL, '" + name + "', '" + phone + "', '" + address + "', '" + level + "');");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Add coach :" + ex);
        }

    }

    public String coach_by_name(int id) throws SQLException {

        String name = null;
        try {
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT name FROM `couch` WHERE c_id=" + id + "");

            while (r.next()) {
                System.out.println(r.getString("name"));
                name = r.getString("name");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "coach_by_name :" + ex);
        }

        return name;
    }

    // SELECT name FROM `couch` WHERE c_id=1000
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

    public List<swimmer> get_swimmer_by_group(int g_id) throws SQLException {

        List<swimmer> t = new ArrayList<swimmer>();

        try {

            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` WHERE `g_id` = " + g_id + "");
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

    public void delet_swimmer(int s_id) {

        try {
            System.out.println("Delet : " + s_id);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `swimmer` WHERE swimmer.s_id=" + s_id);
        } catch (SQLException ex) {
            System.out.println(" delet_swimmer :" + ex);
        }
    }
//DELETE FROM `swimmer` WHERE swimmer.s_id=1

//////////////////////////////////////////add swimmer with all methods//////////
    public boolean check_group_exist(String c_name, Time t, int b) {

        boolean bool = false;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + c_name + "'  AND groups.g_day = " + b);
            bool = r.next();
        } catch (SQLException ex) {
            System.out.println(" check_group_exist : " + ex);
        }
        return bool;
    }

    public int get_id_check_group_exist(String c_name, Time t, int b) {

        int bool = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + c_name + "'  AND groups.g_day = " + b);

            while (r.next()) {
                bool = r.getInt("g_id");
            }

        } catch (SQLException ex) {
            System.out.println(" get_id_check_group_exist : " + ex);
        }
        return bool;

    }

    public int addswimmer(String name, int age, String gender, String phone, int group) throws SQLException {
        int Max_id = 0;
        try {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(sdf.format(now));
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.DATE, -1);
            Date last_day_Month = c.getTime();
            System.out.println(last_day_Month);

            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT MAX(s_id) FROM swimmer");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                Max_id = (r.getInt("MAX(s_id)"));
            }
            Max_id++;
            System.out.println(name);
            statement.executeUpdate("INSERT INTO `swimmer` (`s_id`, `name`, `age`, `gender`, `phone`, `g_id`, `start_date`, `end_date`) VALUES (" + Max_id + ", '" + name + "', '" + age + "', '" + gender + "', '" + phone + "', '" + group + "', '" + sdf.format(now) + "', '" + sdf.format(last_day_Month) + "');");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Add swimmer :" + ex);
        }
        return Max_id;
    }
    //////////////////////////////////////////add swimmer with all methods//////////

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

    public List<all_information_for_group> get_all_group_with_time(Time t, int b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND groups.g_day = " + b + "");
            while (r.next()) {
                System.out.println(r.getInt("g_day"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), t, r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" all_information_for_group :" + ex);
        }
        return id;
    }

    public void Add_group(int c_id, String track, String level, String day, Time t) {

        try {
            int b = 0;
            if (day == "Saturday") {
                b = 0;
            }
            if (day == "Sunday") {
                b = 1;
            }
            if (day == "friday") {
                b = 2;
            }
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `groups` (`g_id`, `c_id`, `track`, `level`, `g_time`, `g_day`) VALUES (NULL, '" + c_id + "', '" + track + "', '" + level + "', '" + t + "', '" + b + "');");
        } catch (SQLException ex) {
            System.out.println(" Add_group :" + ex);
        }

    }

    public void delet_group(int g_id) {

        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `groups` WHERE g_id=" + g_id);
        } catch (SQLException ex) {
            System.out.println(" delet_attend_swimmer :" + ex);
        }
    }
//DELETE FROM `groups` WHERE g_id=1

    public void update_group(int g_id, int c_id, String track, String level, String day, Time t) {

        int b = day == "Saturday" ? 0 : 1;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE `groups` SET `c_id` = '" + c_id + "', `track` = '" + track + "', `level` = '" + level + "', `g_time` = '" + t + "', `g_day` = '" + b + "' WHERE `groups`.`g_id` =" + g_id);
        } catch (SQLException ex) {
            System.out.println(" update_group :" + ex);
        }
    }

    //UPDATE `groups` SET `c_id` = '1016', `track` = 'L4', `level` = 'level 8', `g_time` = '01:00:00', `g_day` = '1' WHERE `groups`.`g_id` = 2027;
    public boolean is_group_exist(int c_id, String day, Time t) {
        boolean bool = false;
        try {

            int b = day == "Saturday" ? 0 : 1;
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` WHERE `c_id` = " + c_id + " AND `g_time` = '" + t + "' AND `g_day` = " + b + " ");
            bool = r.next();
        } catch (SQLException ex) {
            System.out.println(" is_group_exist :" + ex);
        }
        return bool;
    }
//SELECT * FROM `groups` WHERE `c_id` = 1000 AND `g_time` = '03:00' AND `g_day` = 0 
//SELECT DISTINCT g_time FROM `groups`
//SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='03:00'
//////////////////////////////Group//////////////////////
    //////////////////////////////attend_couch//////////////////////

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

    public void Add_couch_attend(int g_id, int re_id) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("add " + sdf.format(now));

        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `attend_couch`(`attend_id`, `absent_day`, `g_id`, `replace_c_id`) VALUES (NULL, '" + sdf.format(now) + "', " + g_id + "," + re_id + ");");
        } catch (SQLException ex) {
            System.out.println(" Add_couch_attend : " + ex);
        }

    }

    public List<attend_couch> get_att_coach(int id) {

        List<attend_couch> s = new ArrayList<attend_couch>();

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` WHERE attend_couch.absent_day='" + sdf.format(now) + "' AND attend_couch.g_id=" + id + " ORDER BY `g_id` ASC");
            while (r.next()) {
                s.add(new attend_couch(r.getInt("g_id"), r.getInt("attend_id"), r.getDate("absent_day"), r.getInt("replace_c_id")));
            }

        } catch (SQLException ex) {
            System.out.println(" get_att_swimmer :" + ex);
        }
        return s;
    }

    public boolean get_att_coach_bool(int id) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        boolean s = false;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` WHERE attend_couch.absent_day='" + sdf.format(now) + "' AND attend_couch.g_id=" + id + " ORDER BY `g_id` ASC");
//            while (r.next()) {
//                s.add(new attend_couch( r.getInt("g_id"), r.getInt("att_c_id"), r.getDate("absent_day"),r.getInt("att_c_id")));
//            }
            s = r.next();

        } catch (SQLException ex) {
            System.out.println(" get_att_swimmer :" + ex);
        }
        return s;
    }

    public void delet_attend_coach(int g_id) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM `attend_couch` WHERE attend_couch.absent_day='" + sdf.format(now) + "' AND attend_couch.g_id=" + g_id);
        } catch (SQLException ex) {
            System.out.println(" delet_attend_swimmer :" + ex);
        }
    }

    public int update_attend_coach(int g_id, int new_re) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));
        int bool = 1;

        try {
            statement = connection.createStatement();
            bool = statement.executeUpdate("UPDATE `attend_couch` SET `replace_c_id`=" + new_re + " WHERE attend_couch.absent_day='" + sdf.format(now) + "' AND attend_couch.g_id=" + g_id);
            System.out.println(bool);
        } catch (SQLException ex) {
            System.out.println(" delet_attend_swimmer :" + ex);
        }
        return bool;
    }
    //UPDATE `attend_couch` SET `replace_c_id`=1001 WHERE attend_couch.absent_day='2020-09-01' AND attend_couch.g_id=2000
    //DELETE FROM `attend_couch` WHERE attend_couch.absent_day='2020-09-28' AND attend_couch.g_id=2008
    //SELECT * FROM `attend_couch` WHERE attend_couch.absent_day='26-9-2020' AND attend_couch.g_id=2000 ORDER BY `g_id` ASC

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

    public List<attend_swimmer> get_att_swimmer(int id) {

        List<attend_swimmer> s = new ArrayList<attend_swimmer>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` WHERE `s_id` = " + id);
            while (r.next()) {
                s.add(new attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getDate("day")));
            }

        } catch (SQLException ex) {
            System.out.println(" get_att_swimmer :" + ex);
        }
        return s;
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
        String s = null;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `note` WHERE `s_id` = " + id + "");
            while (r.next()) {
                s = r.getString("note");
            }

        } catch (SQLException ex) {
            System.out.println(" get_note_id :" + ex);
        }
        return s;
    }

    public void update_note(int id, String n) {

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
            statement.executeUpdate("UPDATE `note` SET `note` = '" + n + "' WHERE `note`.`s_id` = " + id + ";");

        } catch (SQLException ex) {
            System.out.println(" get_note_id :" + ex);
        }
    }

    public boolean is_note_exist(int id) {
        boolean b = false;

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `note` WHERE `s_id` = " + id + "");

            b = r.next();

        } catch (SQLException ex) {
            System.out.println(" get_note_id :" + ex);
        }
        return b;
    }

    ////////////////////////////////////////////////search/////////////
    public List<String> search_group_by_name() {

        List<String> name = new ArrayList<String>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT DISTINCT couch.name FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id");
            while (r.next()) {
                name.add(r.getString("couch.name"));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name : " + ex);
        }
        return name;

    }

    public List<String> get_all_name_swimmer() {

        List<String> name = new ArrayList<String>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT name FROM `swimmer`");
            while (r.next()) {
                name.add(r.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println(" get_all_name_swimmer : " + ex);
        }
        return name;

    }

    ////////////////////////////////////////////////search_group/////////////
    // 0 0 0 0 0
    public List<all_information_for_group> search_group_all() {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id ");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    public List<all_information_for_group> search_group_by_id(int id_g) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.c_id =" + id_g);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    // 0 0 0 0 1
    public List<all_information_for_group> search_group_by_level(String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }// 0 0 0 1 0

    public List<all_information_for_group> search_group_by_line(String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }// 0 0 0 1 1

    public List<all_information_for_group> search_group_by_line_and_level(String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 0 0 1 0 0

    public List<all_information_for_group> search_group_by_day(int b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_day = " + b);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 0 0 1 0 1

    public List<all_information_for_group> search_group_by_day_and_level(int b, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_day = " + b + " AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 0 0 1 1 0

    public List<all_information_for_group> search_group_by_day_and_line(int b, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_day = " + b + "  AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 0 0 1 1 1

    public List<all_information_for_group> search_group_by_day_and_line_and_level(int b, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_day = " + b + "  AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 0 1 0 0 0

    public List<all_information_for_group> search_group_by_time(Time t) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 0 1 0 0 1

    public List<all_information_for_group> search_group_by_time_and_level(Time t, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "' AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 0 1 0 1 0

    public List<all_information_for_group> search_group_by_time_and_line(Time t, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "' AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    // 0 1 0 1 1
    public List<all_information_for_group> search_group_by_time_and_line_and_level(Time t, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "' AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    // 0 1 1 0 0
    public List<all_information_for_group> search_group_by_time_and_day(Time t, int b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND groups.g_day = " + b);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    // 0 1 1 0 1
    public List<all_information_for_group> search_group_by_time_and_day_and_level(Time t, int b, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND groups.g_day = " + b + " AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    // 0 1 1 1 0
    public List<all_information_for_group> search_group_by_time_and_day_and_line(Time t, int b, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND groups.g_day = " + b + "  AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_time_and_day_and_line : " + ex);
        }
        return id;

    }
    // 0 1 1 1 1

    public List<all_information_for_group> search_group_by_time_and_day_and_line_and_level(Time t, int b, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND groups.g_day = " + b + "  AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

//1 0 0 0 0 
    public List<all_information_for_group> search_group_by_name(String name) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name : " + ex);
        }
        return id;

    }
    // 1 0 0 0 1

    public List<all_information_for_group> search_group_by_name_and_level(String name, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "' AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 1 0 0 1 0

    public List<all_information_for_group> search_group_by_name_and_line(String name, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "' AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_line : " + ex);
        }
        return id;

    }        // 1 0 0 1 1

    public List<all_information_for_group> search_group_by_name_and_line_and_level(String name, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "'  AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }        // 1 0 1 0 0

    public List<all_information_for_group> search_group_by_name_and_day(String name, int b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "'  AND groups.g_day = " + b);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_day : " + ex);
        }
        return id;

    }        // 1 0 1 0 1

    public List<all_information_for_group> search_group_by_name_and_day_and_level(String name, int b, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    // 1 0 1 1 0
    public List<all_information_for_group> search_group_by_name_and_day_and_line(String name, int b, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 1 0 1 1 1

    public List<all_information_for_group> search_group_by_name_and_day_and_line_and_level(String name, int b, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    // 1 1 0 0 0 
    public List<all_information_for_group> search_group_by_name_and_time(String name, Time t) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name : " + ex);
        }
        return id;

    }
    // 1 1 0 0 1

    public List<all_information_for_group> search_group_by_name_and_time_and_level(String name, Time t, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 1 1 0 1 0

    public List<all_information_for_group> search_group_by_name_and_time_and_line(String name, Time t, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 1 1 0 1 1

    public List<all_information_for_group> search_group_by_name_and_time_and_line_and_level(String name, Time t, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 1 1 1 0 0

    public List<all_information_for_group> search_group_by_name_and_time_and_day(String name, Time t, int b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.g_day = " + b);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 1 1 1 0 1

    public List<all_information_for_group> search_group_by_name_and_time_and_day_and_level(String name, Time t, int b, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    //1 1 1 1 0

    public List<all_information_for_group> search_group_by_name_and_time_and_day_and_line(String name, Time t, int b, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    // 1 1 1 1 1

    public List<all_information_for_group> search_group_by_name_and_time_and_day_and_line_and_level(String name, Time t, int b, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    ////////////////////////////////////////////////search_group/////////////

    ////////////////////////////////////////////////search_swimmer/////////////
    public List<all_information_for_swimmer> search_swimmer_name(String name) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE swimmer.name='" + name + "'");
            while (r.next()) {
                System.out.println("nnnnnnnnnnnnnnnnnn : " + r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }

    public List<all_information_for_swimmer> search_swimmer_id(int s_id) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE swimmer.s_id=" + s_id);
            while (r.next()) {
                System.out.println("nnnnnnnnnnnnnnnnnn : " + r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }

    // 0 0 0
    public List<all_information_for_swimmer> search_swimmer_all() {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id");
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }
    // 0 0 1

    public List<all_information_for_swimmer> search_swimmer_by_gender(String gender) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE swimmer.gender='" + gender + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }       // 0 1 0

    public List<all_information_for_swimmer> search_swimmer_by_day(int b) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.g_day=" + b);
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }       // 0 1 1

    public List<all_information_for_swimmer> search_swimmer_by_day_and_gender(int b, String gender) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.g_day=" + b + " AND swimmer.gender='" + gender + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }       // 1 0 0

    public List<all_information_for_swimmer> search_swimmer_by_time(Time t) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.g_time='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }       // 1 0 1

    public List<all_information_for_swimmer> search_swimmer_by_time_and_gender(Time t, String gender) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.g_time='" + t + "' AND swimmer.gender='" + gender + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }
    // 1 1 0

    public List<all_information_for_swimmer> search_swimmer_by_time_and_day(Time t, int b) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.g_time='" + t + "' AND groups.g_day=" + b);
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }
    // 1 1 1

    public List<all_information_for_swimmer> search_swimmer_by_time_and_day_and_gender(Time t, int b, String gender) {

        List<all_information_for_swimmer> id = new ArrayList<all_information_for_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.g_time='" + t + "' AND groups.g_day=" + b + " AND swimmer.gender='" + gender + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
//int s_id, int age, int g_id, int c_id, String name, String phone, String adress, String gender, String track, Date start, Date end, Time g_time, boolean day
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_swimmer_all : " + ex);
        }
        return id;

    }
    ////////////////////////////////////////////////search_swimmer/////////////

    ////////////////////////////////////////////////search_attend/////////////
    public List<all_information_for_attend_swimmer> search_attend_swimmer() {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id");
            while (r.next()) {
                System.out.println(r.getString("name"));
                //int attend_id, int s_id, int num, int age,               int g_id, int c_id, Date day,                      String name,                                                String phone, String level, String gender, String track, Date start, Date end, Time g_time, boolean g_day
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_name(String name) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE swimmer.name= '" + name + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_id(int s_id) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE swimmer.name= " + s_id);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

    //0 0 1
    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_num(int n) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.num=" + n);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }
    //0 1 0

    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_time(Time t) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.g_time ='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }
    //0 1 1

    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_time_and_num(Time t, int n) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.num=" + n + " AND groups.g_time ='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }
    //1 0 0

    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_day(Date d) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.day='" + d + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }
    //1 0 1

    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_day_and_num(Date d, int n) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.day='" + d + "' AND attend_swimmer.num=" + n);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }
    //1 1 0

    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_day_and_time(Date d, Time t) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.day='" + d + "' AND groups.g_time ='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

    //1 1 1
    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_day_and_time_and_num(Date d, Time t, int n) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.day='" + d + "' AND attend_swimmer.num=" + n + " AND groups.g_time ='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"), r.getInt("num"), r.getInt("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

    ////////////////////////////////////////////////search_attend/////////////
    ////////////////////////////////////////////////search_attend_coach/////////////
    public List<all_information_for_attend_coach> search_attend_coach() {

        List<all_information_for_attend_coach> id = new ArrayList<all_information_for_attend_coach>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id");
            while (r.next()) {
                System.out.println("attend coach : " + r.getString("name"));
                //int att_c_id, int g_id, int c_id, int r_id, Date date_absent                            , String track, String level, String name,                                  String c_L, String phone, Time time
                id.add(new all_information_for_attend_coach(r.getInt("attend_id"), r.getInt("g_id"), r.getInt("c_id"), r.getInt("replace_c_id"), r.getDate("absent_day"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("c_level"), r.getString("phone"), r.getTime("g_time")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
        return id;

    }

    public List<all_information_for_attend_coach> search_attend_coach_by_id(int c_id) {

        List<all_information_for_attend_coach> id = new ArrayList<all_information_for_attend_coach>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.c_id=" + c_id);
            while (r.next()) {
                System.out.println("attend coach : " + r.getString("name"));
                //int att_c_id, int g_id, int c_id, int r_id, Date date_absent                            , String track, String level, String name,                                  String c_L, String phone, Time time
                id.add(new all_information_for_attend_coach(r.getInt("attend_id"), r.getInt("g_id"), r.getInt("c_id"), r.getInt("replace_c_id"), r.getDate("absent_day"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("c_level"), r.getString("phone"), r.getTime("g_time")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
        return id;

    }

    public List<all_information_for_attend_coach> search_attend_coach_by_name(String c_name) {

        List<all_information_for_attend_coach> id = new ArrayList<all_information_for_attend_coach>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='" + c_name + "'");
            while (r.next()) {
                System.out.println("attend coach : " + r.getString("name"));
                //int att_c_id, int g_id, int c_id, int r_id, Date date_absent                            , String track, String level, String name,                                  String c_L, String phone, Time time
                id.add(new all_information_for_attend_coach(r.getInt("attend_id"), r.getInt("g_id"), r.getInt("c_id"), r.getInt("replace_c_id"), r.getDate("absent_day"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("c_level"), r.getString("phone"), r.getTime("g_time")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
        return id;

    }

    public List<all_information_for_attend_coach> search_attend_coach_by_time(Time t) {

        List<all_information_for_attend_coach> id = new ArrayList<all_information_for_attend_coach>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'");
            while (r.next()) {
                System.out.println("attend coach : " + r.getString("name"));
                //int att_c_id, int g_id, int c_id, int r_id, Date date_absent                            , String track, String level, String name,                                  String c_L, String phone, Time time
                id.add(new all_information_for_attend_coach(r.getInt("attend_id"), r.getInt("g_id"), r.getInt("c_id"), r.getInt("replace_c_id"), r.getDate("absent_day"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("c_level"), r.getString("phone"), r.getTime("g_time")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
        return id;

    }

    public List<all_information_for_attend_coach> search_attend_coach_by_day(Date d) {

        List<all_information_for_attend_coach> id = new ArrayList<all_information_for_attend_coach>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id WHERE attend_couch.absent_day='" + d + "'");
            while (r.next()) {
                System.out.println("attend coach : " + r.getString("name"));
                //int att_c_id, int g_id, int c_id, int r_id, Date date_absent                            , String track, String level, String name,                                  String c_L, String phone, Time time
                id.add(new all_information_for_attend_coach(r.getInt("attend_id"), r.getInt("g_id"), r.getInt("c_id"), r.getInt("replace_c_id"), r.getDate("absent_day"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("c_level"), r.getString("phone"), r.getTime("g_time")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
        return id;

    }
    public List<all_information_for_attend_coach> search_attend_coach_by_day_time(Date d,Time t) {

        List<all_information_for_attend_coach> id = new ArrayList<all_information_for_attend_coach>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id WHERE attend_couch.absent_day='" + d + "' && groups.g_time='" + t + "'");
            while (r.next()) {
                System.out.println("attend coach : " + r.getString("name"));
                //int att_c_id, int g_id, int c_id, int r_id, Date date_absent                            , String track, String level, String name,                                  String c_L, String phone, Time time
                id.add(new all_information_for_attend_coach(r.getInt("attend_id"), r.getInt("g_id"), r.getInt("c_id"), r.getInt("replace_c_id"), r.getDate("absent_day"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("c_level"), r.getString("phone"), r.getTime("g_time")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
        return id;

    }

    ////////////////////////////////////////////////search_attend_coach/////////////
    ////////////////////////////////////////////////search/////////////
    ////////////////////////////////////////////////re_coach/////////////
    public List<all_information_for_group> search_group_by_notequletime_and_day(Time t, int b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT DISTINCT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time!='" + t + "' AND groups.g_day =" + b + " AND couch.name NOT IN (SELECT couch.name FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "' AND groups.g_day =" + b + ")");
            while (r.next()) {
                System.out.println(r.getString("name"));
                boolean flag = false;
                for (int i = 0; i < id.size(); i++) {
                    if (id.get(i).getName().equals(r.getString("name"))) {
                        flag = true;
                    }
                }
                if (flag == false) {
                    id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day")));

                }

            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }

    //SELECT * FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON groups.c_id=couch.c_id
//SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time!='03:00' AND groups.g_day =0 AND couch.name NOT IN (SELECT couch.name FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='03:00' AND groups.g_day =0)    
//SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time!='03:00' AND groups.g_day =0
//SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id
//SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id
//SELECT * FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id
//SELECT DISTINCT couch.name FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id
//SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE couch.name='tttt'  
//SELECT * FROM `groups` WHERE `c_id` = 1000 AND `g_time` = '03:00' AND `g_day` = 0   
//SELECT * FROM `note` WHERE `s_id` = 3000
//INSERT INTO `note`(`note_id`, `note`, `s_id`) VALUES ([value-1],[value-2],[value-3])
//SELECT * FROM `attend_swimmer` WHERE 1
//INSERT INTO `attend_swimmer` (`attend_id`, `s_id`, `day`) VALUES (NULL, '2029', '2020-09-05');
//SELECT attend_couch.absent_day, attend_couch.g_id,groups.g_time,couch.name FROM attend_couch INNER JOIN groups ON attend_couch.g_id=groups.g_id INNER JOIN couch ON attend_couch.replace_c_id=couch.c_id
//SELECT attend_couch.absent_day, attend_couch.g_id,groups.g_time,attend_couch.replace_c_id FROM attend_couch INNER JOIN groups ON attend_couch.g_id=groups.g_id
//SELECT * FROM `attend_couch` WHERE 1
}
