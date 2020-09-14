/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.*;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import javafx.geometry.Rectangle2D;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import model.all_information_for_group;
import model.swimmer;
import service.DB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import model.coach;

/**
 * FXML Controller class
 *
 * @author asd
 */
public class HomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button b_home, b_add_group;
    @FXML
    private AnchorPane anchorpane, pane_table;
    @FXML
    private VBox p_list;
    @FXML
    private StackPane big_Stack;
    @FXML
    private Pane p_home,p_add_group;
    @FXML
    private JFXComboBox<Time> combobox_all_group;
    @FXML
    private JFXComboBox<String> combobox_all_group_day;
    @FXML
    private ScrollPane scrooll;
    
    @FXML
    private JFXTextField add_group_level;

    @FXML
    private JFXComboBox<String> add_group_coach,add_group_day;

    @FXML
    private JFXTimePicker add_group_time;

    @FXML
    private JFXComboBox<?> add_group_line;

    public void swi(ActionEvent actionEvent) {
        if (actionEvent.getSource() == b_home) {
            p_home.toFront();
        }
        if (actionEvent.getSource() == b_add_group) {
            p_add_group.toFront();
        }
         
//        if (actionEvent.getSource() == btnMenus) {
//           
//        }
//        if (actionEvent.getSource() == btnOverview) {
//           // pnlOverview.setStyle("-fx-background-color : #02030A");
//        }
//        if (actionEvent.getSource() == btnSettings) {
//        }
    }

    public void print(ActionEvent actionEvent) {
        //  Node p_home = new Circle(100, 200, 200);
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double scaleX = 1.0;
            if (pageLayout.getPrintableWidth() < table.getBoundsInParent().getWidth()) {
                scaleX = pageLayout.getPrintableWidth() / table.getBoundsInParent().getWidth();
            }
            double scaleY = 1.0;
            if (pageLayout.getPrintableHeight() < table.getBoundsInParent().getHeight()) {
                scaleY = pageLayout.getPrintableHeight() / table.getBoundsInParent().getHeight();
            }
            double scaleXY = Double.min(scaleX, scaleY);
            Scale scale = new Scale(scaleXY, scaleXY);
            table.getTransforms().add(scale);
            boolean success = job.printPage(table);
            table.getTransforms().remove(scale);
            if (success) {
                job.endJob();
            }
        }
    }

    DB allDb;
    Rectangle2D bounds;
    List<all_information_for_group> id;
    List<Integer> all_g_id;
    List<List<swimmer>> t;
    List<coach> coach;
    boolean bool;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            initialize_home();
            coach = new ArrayList<coach>();
            allDb.DB_connection();
            coach = allDb.allcoach();
            allDb.DB_close();
            coach.forEach((co) -> {
            add_group_coach.getItems().add(co.getName());
            });
            add_group_day.getItems().addAll("Saturday", "Sunday");
           // add_group_line
        } catch (SQLException ex) {
        }
    }

    ////////////////////////////////home/////////////
    VBox table = new VBox();

    private void BuildHome(List<all_information_for_group> id, List<List<swimmer>> all_S) throws SQLException {

        Label num = make_lable("num", .039);
        Label name = make_lable("Name", 0.1735);
        Label coach = make_lable("Coach", 0.07);
        Label l1 = make_lable("1", .023);
        Label l2 = make_lable("2", .023);
        Label l3 = make_lable("3", .023);
        Label l4 = make_lable("4", .023);
        Label l5 = make_lable("5", .023);
        Label l6 = make_lable("6", .023);
        Label l7 = make_lable("7", .023);
        Label l8 = make_lable("8", .023);
        Label l9 = make_lable("9", .023);
        Label l10 = make_lable("10", .023);
        Label l11 = make_lable("11", .023);
        Label l12 = make_lable("12", .023);
        Label level = make_lable("level", .05);
        Label notes = make_lable("notes", .19);

        HBox title = new HBox();
        title.setStyle("-fx-background-color: #ffffff;");
        title.setPrefSize(1000, 0);
        title.setMaxSize(10000, 10000);
        title.getChildren().addAll(notes, level, l12, l11, l10, l9, l8, l7, l6, l5, l4, l3, l2, l1, coach, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table.getChildren().add(title);

        for (int z = 0; z < id.size(); z++) {
            HBox row = new HBox();
            row.setStyle("-fx-background-color:  #ffffff;");
            row.setAlignment(Pos.CENTER_RIGHT);

            VBox all_num = new VBox();
            all_num.setStyle("-fx-background-color:  #ffffff;");

            for (int i = 0; i < 8; i++) {
                Label l = make_lable("" + (i + 1), .039);
                all_num.getChildren().add(l);
            }

            VBox Swimer = new VBox();
            Swimer.setStyle("-fx-background-color:  #ffffff;");
            for (int i = 0; i < all_S.get(z).size(); i++) {
                Label la = make_lable(all_S.get(z).get(i).getName(), 0.1735);
                Swimer.getChildren().add(la);

            }

            Label la = new Label(id.get(z).getName() + " \n " + id.get(z).getTrack() + "");
            la.setPrefWidth(bounds.getWidth() * 0.07);
            la.setMaxHeight(1000);
            la.setStyle("-fx-font-size: 16px;-fx-background-color: #ffb3b3;-fx-border-color:#000;");
            la.setAlignment(Pos.CENTER);

            HBox all_att = new HBox();
            for (int j = 12; j > 0; j--) {
                VBox att = new VBox();
                att.setStyle("-fx-background-color:   #ffffff;");
                for (int i = 0; i < all_S.get(z).size(); i++) {
                    JFXCheckBox ch = new JFXCheckBox();
                    ch.setCheckedColor(rgb(42, 115, 255));
                    ch.setTextFill(rgb(255, 255, 255));
                    ch.setStyle("-fx-font-size: 16px;-fx-background-color: #ffb3b3;-fx-border-color:#000;");
                    ch.setPrefSize(bounds.getWidth() * .023, 0);
                    ch.setAlignment(Pos.CENTER_RIGHT);
                    ch.setId(all_S.get(z).get(i).getS_id() + "|" + j + "|" + i);

                    if (allDb.get_att(all_S.get(z).get(i).getS_id(), j)) {
                        ch.setSelected(true);
                    }

                    LocalDate currentdate = LocalDate.now();
                    int currentYear = currentdate.getYear();
                    Month currentMonth = currentdate.getMonth();
////////////SATURDAY
                    System.out.println("SATURDAY");
                    if (bool == false) {
                        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                                || date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                                .collect(Collectors.toList());
                        if (ldate.size() == 13) {
                            ldate.remove(12);
                        }
                        if (ldate.get(j - 1).isBefore(currentdate)) {
                            ch.setDisable(true);
                        }
                    } else {
                        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                                .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                                || date.getDayOfWeek() == DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.THURSDAY)
                                .collect(Collectors.toList());
                        if (ldate.size() == 13) {
                            ldate.remove(12);
                        }
                        if (ldate.get(j - 1).isBefore(currentdate)) {
                            ch.setDisable(true);
                        }

                    }
                    ch.setOnAction((event) -> {
                        try {
                            String[] companies = ch.getId().split("\\|");

                            allDb.DB_connection();
                            if (!ch.isSelected()) {
                                allDb.delet_attend_swimmer(Integer.parseInt(companies[0]));

                            } else {
                                System.out.println(Integer.parseInt(companies[0]) + "  " + Integer.parseInt(companies[1]));
                                allDb.Add_attend_swimmer(Integer.parseInt(companies[0]), Integer.parseInt(companies[1]));
                            }
                            allDb.DB_close();
                        } catch (Exception ex) {
                            System.out.println("check :" + ex);
                        }

                    });
                    att.getChildren().add(ch);
                }
                all_att.getChildren().add(att);
            }

            VBox all_level = new VBox();
            all_level.setStyle("-fx-background-color:     #ffffff;");
            for (int i = 0; i < 8; i++) {
                Label l = make_lable(id.get(z).getG_level(), .05);
                all_level.getChildren().add(l);

            }

            VBox all_nots = new VBox();
            all_nots.setStyle("-fx-background-color:    #ffffff;");
            for (int i = 0; i < all_S.get(z).size(); i++) {

                TextField t = new TextField();
                t.setPrefWidth(bounds.getWidth() * .19);
                t.setStyle("-fx-font-size: 12px;-fx-background-color:	 #ffb3b3;-fx-border-color:#000;");
                t.setAlignment(Pos.CENTER);
                t.setId(all_S.get(z).get(i).getS_id() + "");
                t.setText(allDb.get_note_id(Integer.parseInt(t.getId())));
                t.setOnKeyPressed((event) -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                       
                        try {
                            allDb.DB_connection();
                            if(allDb.is_note_exist(Integer.parseInt(t.getId()))){
                            allDb.update_note(Integer.parseInt(t.getId()), t.getText());
                            }
                            else{
                            allDb.Add_note_swimmer(Integer.parseInt(t.getId()), t.getText());
                            }
                            allDb.DB_close();
                        } catch (Exception ex) {
                        }
                    }
                });
                all_nots.getChildren().add(t);

            }

            row.getChildren().add(all_nots);
            row.getChildren().add(all_level);
            row.getChildren().add(all_att);
            row.getChildren().add(la);
            row.getChildren().add(Swimer);
            row.getChildren().add(all_num);
            table.getChildren().add(row);
            if (z > 2) {

                pane_table.setPrefHeight(pane_table.getPrefHeight() + bounds.getWidth() * .14);
            }
        }
        pane_table.getChildren().clear();
        pane_table.getChildren().add(table);
        //p_home.getChildren().add(table);

    }

    private Label make_lable(String name, double d) throws SQLException {

        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        l.setStyle("-fx-font-size: 16px;-fx-background-color:	 #ffb3b3;-fx-border-color:#000;");
        l.setAlignment(Pos.CENTER);

        return l;
    }

    private void initialize_home(){
     ///////////////////////initialize//////////////
        Time sqlTime = Time.valueOf("03:00:00");
        allDb = new DB();
        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        //combobox_all_group.setStyle("-fx-font-size: 20px;");
        table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
        table.setLayoutX(0);
        table.setLayoutY(0);
        table.setStyle("-fx-background-color:   #fff;");
        scrooll.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
        pane_table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
        id = new ArrayList<all_information_for_group>();
        all_g_id = new ArrayList<Integer>();
        t = new ArrayList<List<swimmer>>();
        combobox_all_group_day.getItems().addAll("Saturday", "Sunday");
        combobox_all_group_day.setValue("Saturday");
        combobox_all_group.setValue(sqlTime);

        ///////////////////////initialize//////////////
        //////////////size//////////
        p_list.setPrefSize(bounds.getWidth() * 0.20, bounds.getHeight());
        big_Stack.setPrefSize(bounds.getWidth() * (1 - 0.18), bounds.getHeight());

        System.out.println(bounds.getHeight());
        System.out.println(bounds.getWidth());
        //////////////size//////////

        try {
            //////////////Show//////////
            allDb.DB_connection();
            combobox_all_group.getItems().addAll(allDb.All_time_of_group_without_repeat());
            id = allDb.get_all_group_with_time(combobox_all_group.getValue(), false);
            all_g_id.clear();
            for (int i = 0; i < id.size(); i++) {
                all_g_id.add(id.get(i).getG_id());
            }
            t = allDb.get_swimmerWithgroup(all_g_id);
            //System.out.println(t.get(0).get(0));
            pane_table.getChildren().clear();
            table.getChildren().clear();
            pane_table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
            BuildHome(id, t);

            allDb.DB_close();

            combobox_all_group.setOnAction((event) -> {
                try {

                    if (combobox_all_group_day.getValue().equals("Saturday")) {
                        bool = false;
                    } else {
                        bool = true;
                    }
                    allDb.DB_connection();
                    id = allDb.get_all_group_with_time(combobox_all_group.getValue(), bool);
                    all_g_id.clear();
                    for (int i = 0; i < id.size(); i++) {
                        all_g_id.add(id.get(i).getG_id());
                    }
                    System.out.println(all_g_id);
                    t = allDb.get_swimmerWithgroup(all_g_id);
                    //System.out.println(t.get(0).get(0));
                    pane_table.getChildren().clear();
                    table.getChildren().clear();
                    pane_table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
                    BuildHome(id, t);
                    allDb.DB_close();

                } catch (SQLException ex) {
                    System.out.println("initialize" + ex);
                }

            });

        } catch (SQLException ex) {
            System.out.println("initialize" + ex);
        }

    
    }
//////////////////////////home/////////////
    
}
