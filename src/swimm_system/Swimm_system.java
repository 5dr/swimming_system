/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swimm_system;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author asd
 */
public class Swimm_system extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/screen/Home.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
//               ProcessBuilder P1 = new ProcessBuilder("C:\\xampp\\mysql\\bin\\mysqld.exe");
//        try {
//////
//            P1.start();
// } catch (IOException ex) {
////            System.out.println("process");
//        }
//            launch(args);
//        try {
////
//             Runtime.getRuntime().exec("taskkill /IM mysqld.exe /F");
//        } catch (IOException ex) {
//        }

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        System.out.println(currentYear);
        Month currentMonth = currentdate.getMonth();
        System.out.println(currentMonth);
////////////SATURDAY
        System.out.println("SATURDAY");
        IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                .forEach(date ->
                        System.out.print(date.getDayOfMonth() + " "+date.isBefore(currentdate)+" ")
                
                );
        ////////////SATURDAY
        System.out.println("\nSUNDAY");
        IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                || date.getDayOfWeek() == DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.THURSDAY)
                .forEach(date ->  System.out.print(date.getDayOfMonth() + " "+date.isBefore(currentdate)+" "));
   System.out.println();
    
Date last_day_Month = null ;
            List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                    .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                    .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                    || date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                    .collect(Collectors.toList());
            int count=0;
            for(int i=0;i<ldate.size();i++){
                if (ldate.size() == 13) {
                            ldate.remove(12);
                        }
               if(!ldate.get(i).isBefore(currentdate)){
               count++;
               if(count==3){
               last_day_Month=java.sql.Date.valueOf(ldate.get(i));
               }
                  
               }
            }
            System.out.println(last_day_Month +"");
    }

}
