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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import model.all_information_for_attend_coach;
import model.all_information_for_attend_swimmer;
import model.all_information_for_group;
import model.all_information_for_swimmer;
import model.attend_couch;
import model.attend_swimmer;
import model.coach;
import model.group;
import model.overview;
import model.s;
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
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swimming?useUnicode=yes&characterEncoding=UTF-8", "root", "root");

            System.out.println("connected");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "connected : " + ex);
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

 public void update_coach(int id,String phone, String level) throws SQLException {

        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE `couch` SET `phone` = '"+phone+"', `c_level` = '"+level+"' WHERE `couch`.`c_id` = "+id+";");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "update_coach :" + ex);
        }

    }    public void update_swimmer_level(String name, String level) throws SQLException {

        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE `swimmer` SET `s_level` = '"+level+"' WHERE `name` ='"+name+"';");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "update_swimmer_level:" + ex);
        }

    }

     public void update_swimmer_phone(String name, String phone) throws SQLException {

        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE `swimmer` SET `phone` = '"+phone+"' WHERE `name` ='"+name+"';");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "update_swimmer_phone:" + ex);
        }

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

    public String coach_by_level(int id) throws SQLException {

        String name = null;
        try {
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT c_level FROM `couch` WHERE c_id=" + id + "");

            while (r.next()) {
                System.out.println(r.getString("c_level"));
                name = r.getString("c_level");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "coach_by_c_level :" + ex);
        }

        return name;
    }

    public String coach_by_phone(int id) throws SQLException {

        String name = null;
        try {
            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT phone FROM `couch` WHERE c_id=" + id + "");

            while (r.next()) {
                System.out.println(r.getString("phone"));
                name = r.getString("phone");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "coach_by_phone :" + ex);
        }

        return name;
    }

    public List<Integer> get_All_Id_Of_coach() {
        List<Integer> id = new ArrayList<Integer>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT c_id FROM `couch` ORDER by c_id ASC");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id.add(r.getInt("c_id"));
            }
        } catch (SQLException ex) {
            System.out.println(" get_All_Id_Of_coach :" + ex);
        }

        return id;
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
                    t1.add(new swimmer(r.getInt("s_id"), r.getString("name"), r.getString("s_level"),
                            r.getString("address"), r.getDate("age"),
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
                t.add(new swimmer(r.getInt("s_id"), r.getString("name"), r.getString("s_level"),
                        r.getString("address"), r.getDate("age"),
                        r.getString("gender"), r.getString("phone"),
                        r.getInt("g_id"), r.getDate("start_date"), r.getDate("end_date")));

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "coach :" + ex);
        }

        return t;
    }
    
   
    public List<swimmer> sdate(String date) throws SQLException {

        List<swimmer> t = new ArrayList<swimmer>();

        try {

            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("select *  from swimmer where start_date='"+ date+"'; ");
            while (r.next()) {
              
                t.add(new swimmer(r.getInt("s_id"), r.getString("name"), r.getString("s_level"),
                        r.getString("address"), r.getDate("age"),
                        r.getString("gender"), r.getString("phone"),
                        r.getInt("g_id"), r.getDate("start_date"), r.getDate("end_date")));

            }
      
        } catch (SQLException ex) {
          //  JOptionPane.showMessageDialog(null, "date :" + ex);
        }

        return t;
    }
      public List<attend_swimmer> s_att(String date) throws SQLException {

        List<attend_swimmer> t = new ArrayList<attend_swimmer>();

        try {

            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("select * from attend_swimmer where day ='"+ date+"'; ");
            while (r.next()) {
              
                t.add(new attend_swimmer(r.getInt("attend_id"), r.getInt("s_id"),
                        
                        r.getInt("num"), r.getDate("day")));

            }

        } catch (SQLException ex) {
          //  JOptionPane.showMessageDialog(null, "date :" + ex);
        }

        return t;
    }
      
       public List<attend_couch> c_att(String date) throws SQLException {

        List<attend_couch> t = new ArrayList<attend_couch>();

        try {

            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("select * from attend_couch where absent_day ='"+ date+"'; ");
            while (r.next()) {
              
                t.add(new attend_couch(r.getInt("g_id"), r.getInt("attend_id"),
                        
                        r.getDate("absent_day") , r.getInt("replace_c_id")));

            }

        } catch (SQLException ex) {
          //  JOptionPane.showMessageDialog(null, "date :" + ex);
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
//////////////////////////////////////////add swimmer with all methods//////////
    public boolean check_group_exist(String c_name, String type, Time t, int b) {

        boolean bool = false;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + c_name + "' AND groups.g_type='" + type + "'  AND groups.g_day = " + b);
            bool = r.next();
        } catch (SQLException ex) {
            System.out.println(" check_group_exist : " + ex);
        }
        return bool;
    }

    public int get_id_check_group_exist(String c_name, String type, Time t, int b) {

        int bool = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + c_name + "' AND groups.g_type='" + type + "'  AND groups.g_day = " + b);

            while (r.next()) {
                bool = r.getInt("g_id");
            }

        } catch (SQLException ex) {
            System.out.println(" get_id_check_group_exist : " + ex);
        }
        return bool;

    }

    public int addswimmer(String name, Date age, String gender, String s_level, String phone, int group, int range, int b) throws SQLException {
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
            Date last_day_Month = null;
            System.out.println(last_day_Month);

            LocalDate currentdate = LocalDate.now();
            int currentYear = currentdate.getYear();
            Month currentMonth = currentdate.getMonth();
            List<LocalDate> ldate = null;
            System.out.println("bbbbbbbbbbbb" + b);
            if (b == 0) {
                ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                        .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                        .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                        .collect(Collectors.toList());
            } else if (b == 1) {
                ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                        .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                        .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                        || date.getDayOfWeek() == DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.THURSDAY)
                        .collect(Collectors.toList());

            } else if (b == 2) {
                ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                        .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                        .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.FRIDAY)
                        .collect(Collectors.toList());
            }
            int count = 0;
            boolean bool = false;
            for (int i = 0; i < ldate.size(); i++) {
                if (ldate.size() == 13) {
                    ldate.remove(12);
                }
                if (!ldate.get(i).isBefore(currentdate)) {
                    count++;
                    if (count == range) {
                        last_day_Month = java.sql.Date.valueOf(ldate.get(i));
                        bool = true;
                    }

                }
            }

            if (bool == true) {
                statement = connection.createStatement();
                ResultSet r = statement
                        .executeQuery("SELECT MAX(s_id) FROM swimmer");
                while (r.next()) {
                    // System.out.println(r.getInt("g_id"));
                    Max_id = (r.getInt("MAX(s_id)"));
                }
                Max_id++;
                System.out.println(name);
                statement.executeUpdate("INSERT INTO `swimmer` (`s_id`, `name`, `age`, `gender`,`s_level`, `phone`, `g_id`, `start_date`, `end_date`, `day_range`) VALUES (" + Max_id + ", '" + name + "', '" + age + "', '" + gender + "', '" + s_level + "', '" + phone + "', '" + group + "', '" + sdf.format(now) + "', '" + sdf.format(last_day_Month) + "' , " + range + ");");

            } else {
                JOptionPane.showMessageDialog(null, "عدد الحصص المدخله اكثر من المتبقية ف الشهر");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Add swimmer :" + ex);
        }
        return Max_id;
    }

    public int addswimmer_for_trf(String name) throws SQLException {
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
            Date last_day_Month = null;
            System.out.println(last_day_Month);

            LocalDate currentdate = LocalDate.now();
            int currentYear = currentdate.getYear();
            Month currentMonth = currentdate.getMonth();
            List<LocalDate> ldate = null;

            statement = connection.createStatement();
            ResultSet r = statement
                    .executeQuery("SELECT MAX(tr_ld) FROM trfihee");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                Max_id = (r.getInt("MAX(tr_ld)"));
            }
            Max_id++;
            System.out.println(name);
            statement.executeUpdate("INSERT INTO `trfihee` (`tr_ld`, `tr_name`, `date`) VALUES (" + Max_id + ", '" + name + "', '" + sdf.format(now) + "');");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Add swimmer :" + ex);
        }
        return Max_id;
    }

    //////////////////////////////Group//////////////////////
  

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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), t, r.getInt("g_day"), r.getString("g_type")));
            }
        } catch (SQLException ex) {
            System.out.println(" all_information_for_group :" + ex);
        }
        return id;
    }
     public List< all_information_for_group> report_swimmer_time(String date) {
  List<all_information_for_group> id = new ArrayList<all_information_for_group>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN swimmer ON swimmer.g_id=groups.g_id where swimmer.start_date='"+date+"';");
     while (r.next()) {
                                                                
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
            }
                JOptionPane.showMessageDialog(null, "yessssssssss : " + id);
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
       return id;
    
    }
    
        public List<group> report_attend_s_time(String date) {
  List<group> id = new ArrayList<group>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery(" SELECT groups.g_id,groups.c_id,groups.track,groups.level,groups.g_time,groups.g_day,groups.g_type FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id INNER JOIN attend_swimmer ON attend_swimmer.s_id=swimmer.s_id WHERE day='"+date+"';");
     while (r.next()) {
                                                       
   id.add(new group(r.getInt("g_id"),r.getInt("c_id"), r.getString("track"),
                        r.getString("level"), r.getString("g_time"), r.getString("g_day"),r.getString("g_type")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
       return id;
    
    }
          
        public List<group> report_s_time(String date) {
  List<group> id = new ArrayList<group>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery(" SELECT groups.g_id,groups.c_id,groups.track,groups.level,groups.g_time,groups.g_day,groups.g_type FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE start_date='"+date+"';");
     while (r.next()) {
                                                       
   id.add(new group(r.getInt("g_id"),r.getInt("c_id"), r.getString("track"),
                        r.getString("level"), r.getString("g_time"), r.getString("g_day"),r.getString("g_type")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
       return id;
    
    }
     
     public List<s> report_attend_swimmer(String date) {
  List<s> id = new ArrayList<s>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT swimmer.s_id ,swimmer.s_level,swimmer.age ,swimmer.name,swimmer.phone, swimmer.address,swimmer.gender ,swimmer.start_date,swimmer.end_date FROM `swimmer` INNER JOIN attend_swimmer ON attend_swimmer.s_id=swimmer.s_id where swimmer.start_date='"+date+"';");
     while (r.next()) {
                                                       
                id.add(new s(r.getInt("s_id"), r.getString("name"), r.getString("s_level"), r.getString("address"), r.getDate("age"), r.getString("gender"), r.getString("phone"), r.getDate("start_date"), r.getDate("end_date")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_coach : " + ex);
        }
       return id;
    
    }
      public List<coach> report_attend_coach(String date) {
        String s = " ";
  List<coach> id = new ArrayList<coach>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `couch` INNER JOIN attend_couch ON attend_couch.replace_c_id=couch.c_id where absent_day='"+date +"';");
           
           while (r.next()) {
                //System.out.println(r.getString("name"));
                id.add(new coach(r.getInt("c_id"), r.getString("name"),
                        r.getString("phone"), r.getString("address"), r.getString("c_level")));


            }
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "noooooooooo : " + ex);
        }
        return id;

    }
      public List<group> report_time_replace(String date) {
        String s = " ";
  List<group> id = new ArrayList<group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT groups.g_id,groups.c_id,groups.track,groups.level,groups.g_time,groups.g_day,groups.g_type FROM `groups` INNER JOIN attend_couch ON attend_couch.g_id=groups.g_id where absent_day='"+date +"';");
           
           while (r.next()) {
                //System.out.println(r.getString("name"));
                id.add(new group(r.getInt("g_id"),r.getInt("c_id"), r.getString("track"),
                        r.getString("level"), r.getString("g_time"), r.getString("g_day"),r.getString("g_type")));

            }
               
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "noooooooooo : " + ex);
        }
        return id;

    }
    public void Add_group(int c_id, String track, String level, String day, Time t, String ty) {

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
            statement.executeUpdate("INSERT INTO `groups` (`g_id`, `c_id`, `track`, `level`, `g_time`, `g_day`,`g_type`) VALUES (NULL, '" + c_id + "', '" + track + "', '" + level + "', '" + t + "', '" + b + "', '" + ty + "');");
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

    public void update_group(int g_id, int c_id, String track, String level, String day, Time t, String ty) {

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

        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE `groups` SET `c_id` = '" + c_id + "', `track` = '" + track + "', `level` = '" + level + "', `g_time` = '" + t + "', `g_day` = '" + b + "', `g_type` = '" + ty + "' WHERE `groups`.`g_id` =" + g_id);
        } catch (SQLException ex) {
            System.out.println(" update_group :" + ex);
        }
    }

    //UPDATE `groups` SET `c_id` = '1016', `track` = 'L4', `level` = 'level 8', `g_time` = '01:00:00', `g_day` = '1' WHERE `groups`.`g_id` = 2027;
    public boolean is_group_exist(int c_id, String day, Time t, String ty) {
        boolean bool = false;
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
            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` WHERE `c_id` = " + c_id + " AND `g_time` = '" + t + "' AND `g_day` = " + b + " AND `g_type` = '" + ty + "'");
            bool = r.next();
        } catch (SQLException ex) {
            System.out.println(" is_group_exist :" + ex);
        }
        return bool;
    }
    //////////////////////////////attend_couch//////////////////////

    
    public void Add_couch_attend(int g_id, int re_id) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("add " + sdf.format(now));

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
        int v_month = currentMonth.getValue();

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
            System.out.println(" get_att_coach :" + ex);
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
            System.out.println(" get_att_coach_bool :" + ex);
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
   
    
   //////////////////////////////attend_swimmer//////////////////////
    public void Add_attend_swimmer(int swimmer, int n) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
        int v_month = currentMonth.getValue();

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

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
        int v_month = currentMonth.getValue();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` WHERE `s_id` = " + id + " AND `day` = '" + sdf.format(now) + "' AND `num` = " + n + "");
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
  
    public List<all_information_for_group> search_group_all() {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id ");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }


    public List<all_information_for_group> search_group_by_time_and_line_and_level(Time t, String line, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "' AND groups.track = '" + line + "'  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
   
    public List<all_information_for_group> search_group_by_name_and_time_and_day(String name, Time t, int b) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.g_day = " + b);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
  

    public List<all_information_for_group> search_group_by_name_and_time_and_day_and_level(String name, Time t, int b, String level) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.level = '" + level + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
    

    public List<all_information_for_group> search_group_by_name_and_time_and_day_and_line(String name, Time t, int b, String line) {

        List<all_information_for_group> id = new ArrayList<all_information_for_group>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_time='" + t + "'  AND couch.name='" + name + "'  AND groups.g_day = " + b + "  AND groups.track = '" + line + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_swimmer(r.getInt("s_id"), r.getString("s_level"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("gender"), r.getString("track"), r.getString("level"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

  
    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_num(int n) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.num=" + n);
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
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
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }


    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_day_and_time(Date d, Time t) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.day='" + d + "' AND groups.g_time ='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

  
    public List<all_information_for_attend_swimmer> search_attend_swimmer_by_day_and_time_and_num(Date d, Time t, int n) {

        List<all_information_for_attend_swimmer> id = new ArrayList<all_information_for_attend_swimmer>();

        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT * FROM `attend_swimmer` INNER JOIN swimmer ON attend_swimmer.s_id=swimmer.s_id INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE attend_swimmer.day='" + d + "' AND attend_swimmer.num=" + n + " AND groups.g_time ='" + t + "'");
            while (r.next()) {
                System.out.println(r.getString("name"));
                id.add(new all_information_for_attend_swimmer(r.getInt("attend_id"), r.getString("s_level"), r.getInt("s_id"), r.getInt("num"), r.getDate("age"), r.getInt("g_id"), r.getInt("c_id"), r.getDate("day"), r.getString("name"), r.getString("phone"), r.getString("level"), r.getString("gender"), r.getString("track"), r.getDate("start_date"), r.getDate("end_date"), r.getTime("g_time"), r.getInt("g_day")));
            }
        } catch (SQLException ex) {
            System.out.println(" search_attend_swimmer : " + ex);
        }
        return id;

    }

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

    public List<all_information_for_attend_coach> search_attend_coach_by_day_time(Date d, Time t) {

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
                    id.add(new all_information_for_group(r.getInt("g_id"), r.getInt("c_id"), r.getString("track"), r.getString("level"), r.getString("name"), r.getString("phone"), r.getString("address"), r.getString("c_level"), r.getTime("g_time"), r.getInt("g_day"), r.getString("g_type")));

                }

            }
        } catch (SQLException ex) {
            System.out.println(" search_group_by_name_and_time_and_day : " + ex);
        }
        return id;

    }
////////////////////////////////////////cost/////////////////////////////

    public int get_cost_for_one_day() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT total_cost FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("total_cost");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_one_day :" + ex);
        }

        return id;
    }

    public int get_cost_for_late1() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT late_1 FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("late_1");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_late1 :" + ex);
        }

        return id;
    }

    public int get_cost_for_late5() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT late_5 FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("late_5");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_late5 :" + ex);
        }

        return id;
    }

    public int get_cost_for_glass() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT glass FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("glass");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_glass :" + ex);
        }

        return id;
    }

    public int get_cost_for_Behavior() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT Behavior FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("Behavior");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_Behavior :" + ex);
        }

        return id;
    }

    public int get_cost_for_talk() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT talk FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("talk");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_talk :" + ex);
        }

        return id;
    }

    public int get_cost_for_re() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT re FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("re");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_re :" + ex);
        }

        return id;
    }

    public int get_cost_for_coach_cost() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT coach_cost FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("coach_cost");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_coach_cost :" + ex);
        }

        return id;
    }

    public int get_cost_for_absent() {

        int id = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT absent FROM `cost`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("absent");
            }
        } catch (SQLException ex) {
            System.out.println(" get_cost_for_absent :" + ex);
        }

        return id;
    }

    public void update_cost(int tarfih, int coach_cost, int absent, int late_1, int late_5, int glass, int Behavior, int talk, int re) {
        try {
            statement = connection.createStatement();

            statement.executeUpdate("UPDATE `cost` SET `total_cost`=" + tarfih + ",`coach_cost`=" + coach_cost + ",`absent`=" + absent + ",`late_1`=" + late_1 + ",`late_5`=" + late_5 + ",`glass`=" + glass + ",`Behavior`=" + Behavior + ",`talk`=" + talk + ",`re`=" + re + " WHERE cost.cost_id=1");

        } catch (SQLException ex) {
            System.out.println(" update_cost :" + ex);
        }

    }
   
////////////////////////////////////////punish/////////////////////////////
    public void Add_punish(int c_id, int punish) {

        try {
            LocalDate currentdate = LocalDate.now();
            int currentYear = currentdate.getYear();
            Month currentMonth = currentdate.getMonth();
            int v_month = currentMonth.getValue();
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `punishment` (`p_id`, `c_id`, `month`,`year`, `punish`) VALUES (NULL, '" + c_id + "', '" + v_month + "', '" + currentYear + "', '" + punish + "');");
        } catch (SQLException ex) {
            System.out.println(" Add_punish :" + ex);
        }

    }

    public int get_count_of_punish(int c_id, int punish) {

        int id = 0;
        try {
            LocalDate currentdate = LocalDate.now();
            int currentYear = currentdate.getYear();
            Month currentMonth = currentdate.getMonth();
            int v_month = currentMonth.getValue();

            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT COUNT(*) FROM `punishment` WHERE `c_id` = " + c_id + " AND `month` = " + v_month + " AND `year` = " + currentYear + " AND `punish` = " + punish);
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("COUNT(*)");
            }
        } catch (SQLException ex) {
            System.out.println(" get_count_of_punish :" + ex);
        }

        return id;
    }

    public int get_count_of_punish_of_attend(int id) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
        int v_month = currentMonth.getValue();

        int s = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT COUNT(*) FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id WHERE groups.c_id=" + id + " AND attend_couch.month=" + v_month + " AND attend_couch.year=" + currentYear);
            while (r.next()) {
                s = r.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            System.out.println(" get_att_swimmer :" + ex);
        }
        return s;
    }

    public int get_count_of_punish_of_attend_re(int id) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(now));

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
        int v_month = currentMonth.getValue();

        int s = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT COUNT(*) FROM `attend_couch` INNER JOIN groups ON attend_couch.g_id=groups.g_id WHERE attend_couch.replace_c_id=" + id + " AND attend_couch.month=" + v_month + " AND attend_couch.year=" + currentYear);
            while (r.next()) {
                s = r.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            System.out.println(" get_att_swimmer :" + ex);
        }
        return s;
    }
////////////////////////////////////////punish/////////////////////////////

    public String get_coach_name_by_g_id(int g_id) {

        String id = "";
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT couch.name FROM `groups` INNER JOIN couch ON groups.c_id=couch.c_id WHERE groups.g_id=" + g_id);
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getString("couch.name");
            }
        } catch (SQLException ex) {
            System.out.println(" get_coach_name_by_g_id :" + ex);
        }

        return id;
    }

    ////////////////////////////////////////type/////////////////////////////
    public List<String> get_type_name() {
        List<String> id = new ArrayList<String>();
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT type.type_name FROM `type`");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id.add(r.getString("type.type_name"));
            }
        } catch (SQLException ex) {
            System.out.println(" get_type_name :" + ex);
        }

        return id;
    }

    public int get_type_cost(String name) {
        int id = 1;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT `cost` FROM `type` WHERE `type_name` = '" + name + "'");
            while (r.next()) {
                // System.out.println(r.getInt("g_id"));
                id = r.getInt("cost");
            }
        } catch (SQLException ex) {
            System.out.println(" get_type_cost :" + ex);
        }

        return id;
    }

    public void update_type_cost(int new_cost, String name) {
        try {
            statement = connection.createStatement();

            statement.executeUpdate("UPDATE `type` SET `cost` = " + new_cost + " WHERE `type`.`type_name` = \"" + name + "\"");

        } catch (SQLException ex) {
            System.out.println(" update_type_cost :" + ex);
        }

    }

    public void insert_type(int new_cost, String name) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `type` (`type_id`, `type_name`, `cost`) VALUES (NULL, '" + name + "', '" + new_cost + "');");
        } catch (SQLException ex) {
            System.out.println(" insert_type :" + ex);
        }

    }
  
    public int get_count_of_swimmer_with_caoch(int id) {

        int s = 0;
        try {
            statement = connection.createStatement();

            ResultSet r = statement
                    .executeQuery("SELECT COUNT(*) FROM `swimmer` INNER JOIN groups ON swimmer.g_id=groups.g_id WHERE groups.c_id=" + id);
            while (r.next()) {
                s = r.getInt("COUNT(*)");
            }

        } catch (SQLException ex) {
            System.out.println(" get_count_of_swimmer_with_caoch :" + ex);
        }
        return s;
    }

    }
