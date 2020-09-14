/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swimm_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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
                .forEach(date -> System.out.print(date.getDayOfMonth() + " "+date.isBefore(currentdate)+" "));
        ////////////SATURDAY
        System.out.println("\nSUNDAY");
        IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                || date.getDayOfWeek() == DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.THURSDAY)
                .forEach(date -> System.out.print(date.getDayOfMonth() + " "));
    }

}
