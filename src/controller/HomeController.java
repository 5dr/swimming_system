/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.*;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import javafx.geometry.Rectangle2D;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.swing.JOptionPane;
import model.all_information_for_attend_swimmer;
import model.all_information_for_swimmer;
import model.attend_swimmer;
import model.coach;
import org.controlsfx.control.textfield.TextFields;

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
    private Button b_home, b_add_group, b_add_swimmer, b_add_coach, b_search;
    @FXML
    private AnchorPane anchorpane, pane_table, pane_search_table;
    @FXML
    private VBox p_list, vbox_search_group, vbox_search_coach, vbox_search_swimmer, v_s_attend, vbox_group_inf;
    @FXML
    private StackPane big_Stack;
    @FXML
    private Pane p_home, p_add_group, p_s_add, p_C_add, p_search, select_pane_search, information_swimmer, p_group_inf;
    @FXML
    private JFXComboBox<Time> combobox_all_group, search_g_time, search_s_time, search_att_time;
    @FXML
    private JFXComboBox<String> combobox_all_group_day;
    @FXML
    private ScrollPane scrooll, scroll_search;

    @FXML
    private JFXComboBox<String> add_group_coach, add_group_day, add_group_line, add_group_level;

    @FXML
    private JFXTimePicker add_group_time;
    @FXML
    private JFXButton search_group, search_coach, search_swimmer;

    @FXML
    private JFXRadioButton r_g_name, r_g_time, r_g_day, r_g_line, r_g_level, r_s_gender, r_s_day, r_s_time, r_s_name, r_att_s_name, r_att_time, r_att_day, r_att_num;

    @FXML
    private TextField search_g_name, search_s_name, inf_s_name, inf_s_level, inf_s_coach, inf_s_time, inf_s_day, inf_s_address, inf_s_phone, inf_s_age, inf_s_gender, inf_s_group, inf_s_start_day, inf_s_end_day, search_att_name;

    @FXML
    private JFXComboBox<String> search_g_day, search_g_line, search_g_level, search_s_day, search_s_gender;

    @FXML
    private JFXComboBox<Integer> search_att_num;

    @FXML
    private HBox hbox_select_search;

    @FXML
    private JFXTextField text_s_note;

    @FXML
    private JFXDatePicker search_att_day;

    public void swi(ActionEvent actionEvent) {
        if (actionEvent.getSource() == b_home) {
            p_home.toFront();
        }
        if (actionEvent.getSource() == b_add_group) {
            p_add_group.toFront();
        }

        if (actionEvent.getSource() == b_add_swimmer) {
            p_s_add.toFront();
        }
        if (actionEvent.getSource() == b_add_coach) {
            p_C_add.toFront();

        }
        if (actionEvent.getSource() == b_search) {
            p_search.toFront();
        }
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

    public void add_group(ActionEvent actionEvent) throws SQLException {

        if (add_group_coach.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار الكابتن");
        } else if (add_group_day.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
        } else if (add_group_level.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
        } else if (add_group_line.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار الحارة");
        } else {
            System.out.println(add_group_coach.getValue());
            Time time = Time.valueOf(add_group_time.getValue());

            allDb.DB_connection();
            if (allDb.is_group_exist(coach.get(add_group_coach.getSelectionModel().getSelectedIndex()).getC_id(), add_group_day.getValue(), time)) {
                JOptionPane.showMessageDialog(null, "الكابتن  " + add_group_coach.getValue() + " " + " عنده مجموعة ف نفس المعاد");
            } else {
                allDb.Add_group(coach.get(add_group_coach.getSelectionModel().getSelectedIndex()).getC_id(), add_group_line.getValue(),
                        add_group_level.getValue(), add_group_day.getValue(), time);
                JOptionPane.showMessageDialog(null, "تم اضافة الجروب");

            }
            allDb.DB_close();
        }
    }

    public void switch_search(ActionEvent actionEvent) throws SQLException {
        if (actionEvent.getSource() == search_group) {
            search_group.setStyle("-fx-background-color: #ffffff;");
            search_coach.setStyle("-fx-background-color:   #181a1b;");
            search_swimmer.setStyle("-fx-background-color:   #181a1b;");

            vbox_search_group.setDisable(false);
            vbox_search_coach.setDisable(true);
            vbox_search_swimmer.setDisable(true);

            try {
                allDb.DB_connection();
                search_group_list = allDb.search_group_all();
                allDb.DB_close();
                BuildSearch(search_group_list);
            } catch (SQLException ex) {
            }

        }

        if (actionEvent.getSource() == search_coach) {
            search_group.setStyle("-fx-background-color:   #181a1b;");
            search_coach.setStyle("-fx-background-color:  #ffffff;");
            search_swimmer.setStyle("-fx-background-color:   #181a1b;");

            vbox_search_group.setDisable(true);
            vbox_search_coach.setDisable(false);
            vbox_search_swimmer.setDisable(true);

            try {
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer();
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            } catch (SQLException ex) {
            }

        }
        if (actionEvent.getSource() == search_swimmer) {
            search_group.setStyle("-fx-background-color:   #181a1b;");
            search_coach.setStyle("-fx-background-color:   #181a1b;");
            search_swimmer.setStyle("-fx-background-color:  #ffffff;");

            vbox_search_group.setDisable(true);
            vbox_search_coach.setDisable(true);
            vbox_search_swimmer.setDisable(false);

            try {
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_all();
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            } catch (SQLException ex) {
            }
        }
    }

    public void group_search(ActionEvent actionEvent) throws SQLException {
// 1 1 1 1 1
        if (r_g_name.isSelected() && r_g_time.isSelected() && r_g_day.isSelected() && r_g_line.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time_and_day_and_line_and_level(search_g_name.getText(), search_g_time.getValue(), b, search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 1 1 1 1 0
        else if (r_g_name.isSelected() && r_g_time.isSelected() && r_g_day.isSelected() && r_g_line.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time_and_day_and_line(search_g_name.getText(), search_g_time.getValue(), b, search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 1 0 1 1
        else if (r_g_name.isSelected() && r_g_time.isSelected() && r_g_line.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time_and_line_and_level(search_g_name.getText(), search_g_time.getValue(), search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 1 1 0 1
        else if (r_g_name.isSelected() && r_g_time.isSelected() && r_g_day.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time_and_day_and_level(search_g_name.getText(), search_g_time.getValue(), b, search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        } //1 0 1 1 1
        else if (r_g_name.isSelected() && r_g_day.isSelected() && r_g_line.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_day_and_line_and_level(search_g_name.getText(), b, search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        } //0 1 1 1 1
        else if (r_g_time.isSelected() && r_g_day.isSelected() && r_g_line.isSelected() && r_g_level.isSelected()) {
            if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time_and_day_and_line_and_level(search_g_time.getValue(), b, search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 1 1 1 0 0
        else if (r_g_name.isSelected() && r_g_time.isSelected() && r_g_day.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time_and_day(search_g_name.getText(), search_g_time.getValue(), b);
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 1 0 1 0
        else if (r_g_name.isSelected() && r_g_time.isSelected() && r_g_line.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time_and_line(search_g_name.getText(), search_g_time.getValue(), search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 1 0 0 1
        else if (r_g_name.isSelected() && r_g_time.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time_and_level(search_g_name.getText(), search_g_time.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 0 1 1 1
        else if (r_g_level.isSelected() && r_g_line.isSelected() && r_g_day.isSelected()) {
            if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_day_and_line_and_level(b, search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 1 0 1 1
        else if (r_g_level.isSelected() && r_g_line.isSelected() && r_g_time.isSelected()) {
            if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time_and_line_and_level(search_g_time.getValue(), search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 1 1 0 1
        else if (r_g_level.isSelected() && r_g_day.isSelected() && r_g_time.isSelected()) {
            if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time_and_day_and_level(search_g_time.getValue(), b, search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 1 1 1 0
        else if (r_g_day.isSelected() && r_g_line.isSelected() && r_g_time.isSelected()) {
            if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time_and_day_and_line(search_g_time.getValue(), b, search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 0 1 0 1
        else if (r_g_name.isSelected() && r_g_day.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_day_and_level(search_g_name.getText(), b, search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 0 0 1 1
        else if (r_g_name.isSelected() && r_g_line.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_line_and_level(search_g_name.getText(), search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 0 1 1 0
        else if (r_g_name.isSelected() && r_g_day.isSelected() && r_g_line.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_day_and_line(search_g_name.getText(), b, search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        } // 1 1 0 0 0 
        else if (r_g_name.isSelected() && r_g_time.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_time(search_g_name.getText(), search_g_time.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 0 1 0 0
        else if (r_g_name.isSelected() && r_g_day.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_day(search_g_name.getText(), b);
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 0 0 1 0
        else if (r_g_name.isSelected() && r_g_line.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحاره");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_line(search_g_name.getText(), search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//1 0 0 0 1
        else if (r_g_name.isSelected() && r_g_level.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name_and_level(search_g_name.getText(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//0 0 0 1 1
        else if (r_g_line.isSelected() && r_g_level.isSelected()) {
            if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحارة");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_line_and_level(search_g_line.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//0 0 1 0 1
        else if (r_g_day.isSelected() && r_g_level.isSelected()) {
            if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_day_and_level(b, search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//0 0 1 1 0
        else if (r_g_line.isSelected() && r_g_day.isSelected()) {
            if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحارة");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_day_and_line(b, search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//0 1 0 0 1
        else if (r_g_time.isSelected() && r_g_level.isSelected()) {
            if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time_and_level(search_g_time.getValue(), search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//0 1 0 1 0
        else if (r_g_line.isSelected() && r_g_time.isSelected()) {
            if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحارة");
            } else if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time_and_line(search_g_time.getValue(), search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }//0 1 1 0 0
        else if (r_g_time.isSelected() && r_g_day.isSelected()) {
            if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time_and_day(search_g_time.getValue(), b);
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 1 0 0 0 0
        else if (r_g_name.isSelected()) {
            if (search_g_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else {
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_name(search_g_name.getText());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 1 0 0 0
        else if (r_g_time.isSelected()) {
            if (search_g_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_time(search_g_time.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 0 1 0 0
        else if (r_g_day.isSelected()) {
            if (search_g_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                boolean b = search_g_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_day(b);
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 0 0 1 0
        else if (r_g_line.isSelected()) {
            if (search_g_line.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الحارة");
            } else {
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_line(search_g_line.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }// 0 0 0 0 1
        else if (r_g_level.isSelected()) {
            if (search_g_level.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
            } else {
                allDb.DB_connection();
                search_group_list = allDb.search_group_by_level(search_g_level.getValue());
                allDb.DB_close();
                BuildSearch(search_group_list);
            }
        }

    }

    public void swimmer_search(ActionEvent actionEvent) throws SQLException {
        // 1 1 1
        if (r_s_day.isSelected() && r_s_time.isSelected() && r_s_gender.isSelected()) {
            if (search_s_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_s_gender.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
            } else if (search_s_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                boolean b = search_s_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_time_and_day_and_gender(search_s_time.getValue(), b, search_s_gender.getValue());
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_time.isSelected() && r_s_gender.isSelected()) {
            if (search_s_gender.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
            } else if (search_s_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                boolean b = search_s_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_time_and_gender(search_s_time.getValue(), search_s_gender.getValue());
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_day.isSelected() && r_s_gender.isSelected()) {
            if (search_s_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_s_gender.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
            } else {
                boolean b = search_s_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_day_and_gender(b, search_s_gender.getValue());
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_day.isSelected() && r_s_time.isSelected()) {
            if (search_s_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_s_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                boolean b = search_s_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_time_and_day(search_s_time.getValue(), b);
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_day.isSelected()) {
            if (search_s_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                boolean b = search_s_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_day(b);
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_time.isSelected()) {
            if (search_s_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                // boolean b = search_s_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_time(search_s_time.getValue());
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_gender.isSelected()) {
            if (search_s_gender.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
            } else {
                //  boolean b = search_s_day.getValue() == "Saturday" ? false : true;
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_gender(search_s_gender.getValue());
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_name.isSelected()) {
            if (search_s_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else {
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_name(search_s_name.getText());
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        }
    }

    public void att_search(ActionEvent actionEvent) throws SQLException {

        if (r_att_day.isSelected() && r_att_num.isSelected() && r_att_time.isSelected()) {
            if (search_att_day.getValue() == null) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_att_num.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار رقم اليوم");
            } else if (search_att_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_day_and_time_and_num(sqlDate, search_att_time.getValue(), search_att_num.getValue());
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_att_day.isSelected() && r_att_time.isSelected()) {
            if (search_att_day.getValue() == null) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_att_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_day_and_time(sqlDate, search_att_time.getValue());
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_att_day.isSelected() && r_att_num.isSelected()) {
            if (search_att_day.getValue() == null) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_att_num.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار رقم اليوم");
            } else {
                Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_day_and_num(sqlDate, search_att_num.getValue());
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_att_num.isSelected() && r_att_time.isSelected()) {
            if (search_att_num.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار رقم اليوم");
            } else if (search_att_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                // Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_time_and_num(search_att_time.getValue(), search_att_num.getValue());
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_att_day.isSelected()) {
            if (search_att_day.getValue() == null) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_day(sqlDate);
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_att_time.isSelected()) {
            if (search_att_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                // Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_time(search_att_time.getValue());
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_att_num.isSelected()) {
            if (search_att_num.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار رقم اليوم");
            } else {
                // Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_num(search_att_num.getValue());
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_att_s_name.isSelected()) {
            if (search_att_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else {
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer_by_name(search_att_name.getText());
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            }
        }
    }

    DB allDb;
    Rectangle2D bounds;
    List<all_information_for_group> id;
    List<all_information_for_group> search_group_list;
    List<all_information_for_swimmer> search_swimmer_list;
    List<all_information_for_attend_swimmer> search_att_swimmer_list;
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
            add_group_line.getItems().addAll("L0", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11",
                    "L12", "L13", "L14", "L15", "L16", "L17", "L18", "L19", "L20");
            add_group_level.getItems().addAll("Beginner", "level 1", "level 2", "level 3", "level 4", "level 5", "level 6", "level 7", "level 8");
            add_group_time.setValue(LocalTime.MIN);
        } catch (SQLException ex) {
        }

        ///////////////////////search group//////
        initialize_search_group();
        ///////////////////////search group//////

        ///////////////////////search swimmer//////
        initialize_search_swimmer();
        ///////////////////////search swimmer//////
        ///////////////////////search attend//////
        initialize_search_att();
        ///////////////////////search attend//////

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
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                Label la = make_lable("", 0.1735);
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
                            if (allDb.is_note_exist(Integer.parseInt(t.getId()))) {
                                allDb.update_note(Integer.parseInt(t.getId()), t.getText());
                            } else {
                                allDb.Add_note_swimmer(Integer.parseInt(t.getId()), t.getText());
                            }
                            allDb.DB_close();
                        } catch (Exception ex) {
                        }
                    }
                });
                all_nots.getChildren().add(t);

            }
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                TextField t = new TextField();
                t.setPrefWidth(bounds.getWidth() * .19);
                t.setStyle("-fx-font-size: 12px;-fx-background-color:	 #ffb3b3;-fx-border-color:#000;");
                t.setAlignment(Pos.CENTER);
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
        l.setStyle("-fx-font-size: 16px;-fx-background-color:#ffb3b3;-fx-border-color:#000;");
        l.setAlignment(Pos.CENTER);

        return l;
    }

    private Label make_lable_g(String name, double d) throws SQLException {

        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        l.setStyle("-fx-font-size: 16px;-fx-border-color:#000;");
        l.setAlignment(Pos.CENTER);

        return l;
    }

    private void initialize_home() {
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

    ////////////////////////search//////////
    private void initialize_search_group() {

        search_group_list = new ArrayList<all_information_for_group>();
        search_g_day.getItems().addAll("Saturday", "Sunday");
        search_g_line.getItems().addAll("L0", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11",
                "L12", "L13", "L14", "L15", "L16", "L17", "L18", "L19", "L20");
        search_g_level.getItems().addAll("Beginner", "level 1", "level 2", "level 3", "level 4", "level 5", "level 6", "level 7", "level 8");

        try {
            allDb.DB_connection();
            search_g_time.getItems().addAll(allDb.All_time_of_group_without_repeat());
            TextFields.bindAutoCompletion(search_g_name, allDb.search_group_by_name());
            allDb.DB_close();
        } catch (SQLException ex) {
        }
        r_g_name.setOnAction((event) -> {
            if (r_g_name.isSelected()) {
                search_g_name.setDisable(false);
            } else {
                search_g_name.setText("");
                search_g_name.setDisable(true);
            }

        });
        r_g_time.setOnAction((event) -> {
            if (r_g_time.isSelected()) {
                search_g_time.setDisable(false);
            } else {
                search_g_time.setValue(null);
                search_g_time.setDisable(true);
            }
        });
        r_g_day.setOnAction((event) -> {
            if (r_g_day.isSelected()) {
                search_g_day.setDisable(false);
            } else {
                search_g_day.setValue(null);
                search_g_day.setDisable(true);
            }
        });
        r_g_line.setOnAction((event) -> {
            if (r_g_line.isSelected()) {
                search_g_line.setDisable(false);
            } else {
                search_g_line.setValue(null);
                search_g_line.setDisable(true);
            }
        });
        r_g_level.setOnAction((event) -> {
            if (r_g_level.isSelected()) {
                search_g_level.setDisable(false);
            } else {
                search_g_level.setValue(null);
                search_g_level.setDisable(true);
            }
        });

        scroll_search.setPrefSize(bounds.getWidth() * 0.55, bounds.getHeight() * 0.51);
        pane_search_table.setPrefSize(bounds.getWidth() * 0.54, bounds.getHeight() * 0.55);
        select_pane_search.setPrefWidth(bounds.getWidth() * 0.55);
        hbox_select_search.setPrefWidth(bounds.getWidth() * 0.55);

        try {
            allDb.DB_connection();
            search_group_list = allDb.search_group_all();
            allDb.DB_close();
            BuildSearch(search_group_list);
        } catch (SQLException ex) {
        }

    }

    private void initialize_search_swimmer() {

        search_swimmer_list = new ArrayList<all_information_for_swimmer>();
        search_s_day.getItems().addAll("Saturday", "Sunday");
        search_s_gender.getItems().addAll("male", "female");
        try {
            allDb.DB_connection();
            search_swimmer_list = allDb.search_swimmer_all();
            search_s_time.getItems().addAll(allDb.All_time_of_group_without_repeat());
            TextFields.bindAutoCompletion(search_s_name, allDb.get_all_name_swimmer());
            allDb.DB_close();
        } catch (SQLException ex) {
        }

        r_s_name.setOnAction((event) -> {
            if (r_s_name.isSelected()) {
                search_s_name.setDisable(false);
                r_s_day.setSelected(false);
                r_s_gender.setSelected(false);
                r_s_time.setSelected(false);
                search_s_time.setValue(null);
                search_s_time.setDisable(true);
                search_s_gender.setValue(null);
                search_s_gender.setDisable(true);
                search_s_day.setValue(null);
                search_s_day.setDisable(true);
            } else {
                search_s_name.setText("");
                search_s_name.setDisable(true);
            }
        });
        r_s_time.setOnAction((event) -> {
            if (r_s_time.isSelected()) {
                search_s_time.setDisable(false);
                r_s_name.setSelected(false);
                search_s_name.setText("");
                search_s_name.setDisable(true);
            } else {
                search_s_time.setValue(null);
                search_s_time.setDisable(true);
            }
        });
        r_s_day.setOnAction((event) -> {
            if (r_s_day.isSelected()) {
                search_s_day.setDisable(false);
                r_s_name.setSelected(false);
                search_s_name.setText("");
                search_s_name.setDisable(true);
            } else {
                search_s_day.setValue(null);
                search_s_day.setDisable(true);
            }
        });
        r_s_gender.setOnAction((event) -> {
            if (r_s_gender.isSelected()) {
                search_s_gender.setDisable(false);
                r_s_name.setSelected(false);
                search_s_name.setText("");
                search_s_name.setDisable(true);
            } else {
                search_s_gender.setValue(null);
                search_s_gender.setDisable(true);
            }
        });
    }

    private void initialize_search_att() {
        search_att_swimmer_list = new ArrayList<all_information_for_attend_swimmer>();
        search_att_num.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        try {
            allDb.DB_connection();
            allDb.search_attend_swimmer();
            // search_swimmer_list = allDb.search_swimmer_all();
            search_att_time.getItems().addAll(allDb.All_time_of_group_without_repeat());
            TextFields.bindAutoCompletion(search_att_name, allDb.get_all_name_swimmer());
            allDb.DB_close();
        } catch (SQLException ex) {
        }

        r_att_s_name.setOnAction((event) -> {
            if (r_att_s_name.isSelected()) {
                search_att_name.setDisable(false);
                r_att_day.setSelected(false);
                r_att_num.setSelected(false);
                r_att_time.setSelected(false);
                search_att_time.setValue(null);
                search_att_time.setDisable(true);
                search_att_day.setValue(null);
                search_att_day.setDisable(true);
                search_att_num.setValue(null);
                search_att_num.setDisable(true);
            } else {
                search_att_name.setText("");
                search_att_name.setDisable(true);
            }
        });
        r_att_time.setOnAction((event) -> {
            if (r_att_time.isSelected()) {
                search_att_time.setDisable(false);
                r_att_s_name.setSelected(false);
                search_att_name.setText("");
                search_att_name.setDisable(true);
            } else {
                search_att_time.setValue(null);
                search_att_time.setDisable(true);
            }
        });
        r_att_day.setOnAction((event) -> {
            if (r_att_day.isSelected()) {
                search_att_day.setDisable(false);
                r_att_s_name.setSelected(false);
                search_att_name.setText("");
                search_att_name.setDisable(true);
            } else {
                search_att_day.setValue(null);
                search_att_day.setDisable(true);
            }
        });
        r_att_num.setOnAction((event) -> {
            if (r_att_num.isSelected()) {
                search_att_num.setDisable(false);
                r_att_s_name.setSelected(false);
                search_att_name.setText("");
                search_att_name.setDisable(true);
            } else {
                search_att_num.setValue(null);
                search_att_num.setDisable(true);
            }
        });
    }

    VBox table_search = new VBox();

    private void BuildSearch(List<all_information_for_group> id) throws SQLException {
        table_search.getChildren().clear();
        pane_search_table.setPrefSize(bounds.getWidth() * 0.54, bounds.getHeight() * 0.55);
        Label num = make_lable_search_head("num", .039, "b6bec2", 20);
        Label name = make_lable_search_head("Name", 0.1, "b6bec2", 20);
        Label level = make_lable_search_head("level", .1, "b6bec2", 20);
        Label track = make_lable_search_head("Line", .1, "b6bec2", 20);
        Label time = make_lable_search_head("Time", .1, "b6bec2", 20);
        Label day = make_lable_search_head("Day", .1, "b6bec2", 20);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .54, 0);
        title.getChildren().addAll(day, time, track, level, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_g((i + 1) + "", .039);
            Label name1 = make_lable_g(id.get(i).getName(), 0.1);
            Label level1 = make_lable_g(id.get(i).getG_level(), .1);
            Label track1 = make_lable_g(id.get(i).getTrack(), .1);
            Label time1 = make_lable_g(id.get(i).getTime() + "", .1);
            Label day1 = make_lable_g(id.get(i).isDay() ? "Sunday" : "Saturday", .1);

            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3b3;");
            title1.setPrefSize(bounds.getWidth() * .54, 0);
            title1.getChildren().addAll(day1, time1, track1, level1, name1, num1);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3b3 ");
            });
            title1.setOnMouseClicked((event) -> {
                List<attend_swimmer> s = new ArrayList<attend_swimmer>();

                vbox_group_inf.getChildren().clear();
                try {
                    Label num_inf = make_lable_search_head("num", .039, "b6bec2", 20);
                    Label id_inf = make_lable_search_head("Id", 0.05, "b6bec2", 20);
                    Label name_inf = make_lable_search_head("Name", 0.09, "b6bec2", 20);
                    Label level_inf = make_lable_search_head("level", .09, "b6bec2", 20);
                    Label track_inf = make_lable_search_head("Line", .09, "b6bec2", 20);
                    Label time_inf = make_lable_search_head("Time", .09, "b6bec2", 20);
                    Label day_inf = make_lable_search_head("Day", .09, "b6bec2", 20);

                    HBox title_inf = new HBox();
                    title_inf.setStyle("-fx-background-color:  #b6bec2;");
                    title_inf.setPrefSize(bounds.getWidth() * .54, 0);
                    title_inf.getChildren().addAll(day_inf, time_inf, track_inf, level_inf, name_inf, id_inf, num_inf);
                    title_inf.setAlignment(Pos.CENTER_RIGHT);

                    vbox_group_inf.getChildren().add(title_inf);

                    Label num1_inf = make_lable_g(num1.getText(), .039);
                    Label name1_inf = make_lable_g(id.get(Integer.parseInt(num1.getText()) - 1).getName(), 0.09);
                    Label id1_inf = make_lable_g(id.get(Integer.parseInt(num1.getText()) - 1).getC_id() + "", 0.05);
                    Label level1_inf = make_lable_g(id.get(Integer.parseInt(num1.getText()) - 1).getG_level(), .09);
                    Label track1_inf = make_lable_g(id.get(Integer.parseInt(num1.getText()) - 1).getTrack(), .09);
                    Label time1_inf = make_lable_g(id.get(Integer.parseInt(num1.getText()) - 1).getTime() + "", .09);
                    Label day1_inf = make_lable_g(id.get(Integer.parseInt(num1.getText()) - 1).isDay() ? "Sunday" : "Saturday", .09);

                    HBox title1_inf = new HBox();
                    title1_inf.setStyle("-fx-background-color: #ffb3b3;");
                    title1_inf.setPrefSize(bounds.getWidth() * .54, 0);
                    title1_inf.getChildren().addAll(day1_inf, time1_inf, track1_inf, level1_inf, name1_inf, id1_inf, num1_inf);
                    title1_inf.setAlignment(Pos.CENTER_RIGHT);

                    vbox_group_inf.getChildren().add(title1_inf);

                    Label swimmer = make_lable_search_head("The Swimmer in Group", .539, "303436", 20);
                    swimmer.setTextFill(rgb(255, 255, 255));

                    vbox_group_inf.getChildren().add(swimmer);

                    List<swimmer> t = new ArrayList<swimmer>();

                    allDb.DB_connection();
                    t = allDb.get_swimmer_by_group(id.get(Integer.parseInt(num1.getText()) - 1).getG_id());
                    allDb.DB_close();

                    Label num_s = make_lable_search_head("ID", .04, "b6bec2", 20);
                    Label id_s = make_lable_search_head("ID", .139, "b6bec2", 20);
                    Label name_s = make_lable_search_head("Name", 0.18, "b6bec2", 20);
                    Label gender_s = make_lable_search_head("Gender", .18, "b6bec2", 20);

                    HBox title_s = new HBox();
                    title_s.setStyle("-fx-background-color:  #b6bec2;");
                    title_s.setPrefSize(bounds.getWidth() * .54, 0);
                    title_s.getChildren().addAll(gender_s, name_s, id_s,num_s);
                    title_s.setAlignment(Pos.CENTER_RIGHT);

                    vbox_group_inf.getChildren().add(title_s);

                    for (int x = 0; x < t.size(); x++) {
                        
                        Label num_s1 = make_lable_g((x+1)+"", .04);
                        Label id_s1 = make_lable_g(t.get(x).getS_id() + "", .139);
                        Label name_s1 = make_lable_g(t.get(x).getName(), 0.18);
                        Label gender_s1 = make_lable_g(t.get(x).getGender(), .18);

                        HBox title_s1 = new HBox();
                        title_s1.setStyle("-fx-background-color:  #ffb3b3;");
                        title_s1.setPrefSize(bounds.getWidth() * .54, 0);
                        title_s1.getChildren().addAll(gender_s1, name_s1, id_s1,num_s1);
                        title_s1.setAlignment(Pos.CENTER_RIGHT);

                        vbox_group_inf.getChildren().add(title_s1);

                    }

                } catch (SQLException ex) {
                }

                p_group_inf.toFront();
            });
            table_search.getChildren().add(title1);

            if (i > 15) {

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .017);
            }
        }

        pane_search_table.getChildren().clear();
        pane_search_table.getChildren().add(table_search);
    }

    private Label make_lable_search_head(String name, double d, String color, int font) throws SQLException {

        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        l.setStyle("-fx-background-color:#" + color + ";-fx-border-color:#000;");
        l.setFont(Font.font("Verdana", FontWeight.BOLD, font));
        l.setAlignment(Pos.CENTER);

        return l;
    }

    private void BuildSearch_Swimmer(List<all_information_for_swimmer> id) throws SQLException {
        table_search.getChildren().clear();
        pane_search_table.setPrefSize(bounds.getWidth() * 0.54, bounds.getHeight() * 0.55);

        Label num = make_lable_search_head("num", .04, "b6bec2", 17);
        Label name = make_lable_search_head("Name", 0.09, "b6bec2", 17);
        Label address = make_lable_search_head("Coach", 0.07, "b6bec2", 17);
        Label age = make_lable_search_head("Age", 0.047, "b6bec2", 17);
        Label gender = make_lable_search_head("Gender", 0.065, "b6bec2", 17);
        Label phone = make_lable_search_head("Phone", 0.06, "b6bec2", 17);
        Label level = make_lable_search_head("level", .05, "b6bec2", 17);
        Label s_day = make_lable_search_head("Start Day", .073, "b6bec2", 17);
        Label e_day = make_lable_search_head("End Day", .065, "b6bec2", 17);
        Label time = make_lable_search_head("Time", .05, "b6bec2", 17);
        Label day = make_lable_search_head("Day", .05, "b6bec2", 17);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .54, 0);
        title.getChildren().addAll(day, time, e_day, s_day, level, phone, gender, age, address, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_search_swimmer((i + 1) + "", .04);
            Label name1 = make_lable_search_swimmer(id.get(i).getName(), 0.09);
            Label address1 = make_lable_search_swimmer(allDb.coach_by_name(id.get(i).getC_id()), 0.07);
            Label age1 = make_lable_search_swimmer(id.get(i).getAge() + "", .047);
            Label gender1 = make_lable_search_swimmer(id.get(i).getGender(), 0.065);
            Label phone1 = make_lable_search_swimmer(id.get(i).getPhone(), .06);
            Label level1 = make_lable_search_swimmer(id.get(i).getLevel(), .05);
            Label s_day1 = make_lable_search_swimmer(id.get(i).getStart() + "", .073);
            Label e_day1 = make_lable_search_swimmer(id.get(i).getEnd() + "", .065);
            Label time1 = make_lable_search_swimmer(id.get(i).getG_time() + "", .05);
            Label day1 = make_lable_search_swimmer(id.get(i).isDay() ? "Sunday" : "Saturday", .05);

            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3b3;");
            title1.setPrefSize(bounds.getWidth() * .54, 0);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.getChildren().addAll(day1, time1, e_day1, s_day1, level1, phone1, gender1, age1, address1, name1, num1);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3b3 ");
            });
            title1.setOnMouseClicked((event) -> {
                List<attend_swimmer> s = new ArrayList<attend_swimmer>();

                inf_s_name.setText(name1.getText());
                inf_s_address.setText(address1.getText());
                inf_s_age.setText(age1.getText());
                try {
                    allDb.DB_connection();
                    text_s_note.setText(allDb.get_note_id(id.get(Integer.parseInt(num1.getText()) - 1).getS_id()));
                    inf_s_coach.setText(allDb.coach_by_name(id.get(Integer.parseInt(num1.getText()) - 1).getC_id()));
                    s = allDb.get_att_swimmer(id.get(Integer.parseInt(num1.getText()) - 1).getS_id());
                    allDb.DB_close();
                } catch (SQLException ex) {
                }
                inf_s_day.setText(day1.getText());
                inf_s_end_day.setText(e_day1.getText());
                inf_s_gender.setText(gender1.getText());
                inf_s_level.setText(level1.getText());
                inf_s_phone.setText(phone1.getText());
                inf_s_start_day.setText(s_day1.getText());
                inf_s_time.setText(time1.getText());

                v_s_attend.getChildren().clear();
                s.forEach((t) -> {

                    HBox att = new HBox();
                    //att.setStyle("-fx-background-color: #ffb3b3;");
                    att.setPrefSize(bounds.getWidth() * .1, 0);
                    att.setAlignment(Pos.CENTER);
                    att.setSpacing(10);

                    try {
                        Label att_num = make_lable_search_swimmer(t.getNum() + "", .04);
                        Label att_day = make_lable_search_swimmer(t.getDay() + "", .07);
                        att.getChildren().addAll(att_day, att_num);
                    } catch (SQLException ex) {
                    }
                    v_s_attend.getChildren().add(att);
                });

                information_swimmer.toFront();
            });
            table_search.getChildren().add(title1);

            if (i > 15) {

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .017);
            }
        }

        pane_search_table.getChildren().clear();
        pane_search_table.getChildren().add(table_search);
    }

    private Label make_lable_search_swimmer(String name, double d) throws SQLException {

        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        l.setStyle("-fx-font-size: 14px;-fx-border-color:#000;");
        l.setAlignment(Pos.CENTER);

        return l;
    }

    private void BuildSearch_att(List<all_information_for_attend_swimmer> id) throws SQLException {

        table_search.getChildren().clear();
        pane_search_table.setPrefSize(bounds.getWidth() * 0.54, bounds.getHeight() * 0.55);

        Label num = make_lable_search_head("num", .04, "b6bec2", 18);
        Label name = make_lable_search_head("Name", 0.1, "b6bec2", 18);
        Label Id = make_lable_search_head("Id", 0.048, "b6bec2", 18);
        Label Date = make_lable_search_head("Date", 0.07, "b6bec2", 18);
        Label n_day = make_lable_search_head("N_Day", 0.05, "b6bec2", 18);
        Label coach = make_lable_search_head("Coach", 0.06, "b6bec2", 18);
        Label level = make_lable_search_head("level", .05, "b6bec2", 18);
        Label time = make_lable_search_head("Time", .06, "b6bec2", 18);
        Label day = make_lable_search_head("Day", .06, "b6bec2", 18);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .54, 0);
        title.getChildren().addAll(day, time, level, coach, n_day, Date, Id, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_search_swimmer((i + 1) + "", .04);
            Label name1 = make_lable_search_swimmer(id.get(i).getName(), 0.1);
            Label id1 = make_lable_search_swimmer(id.get(i).getS_id() + "", .048);
            Label date1 = make_lable_search_swimmer(id.get(i).getDay() + "", 0.07);
            Label n_day1 = make_lable_search_swimmer(id.get(i).getNum() + "", .05);
            Label coach1 = make_lable_search_swimmer(allDb.coach_by_name(id.get(i).getC_id()), 0.06);
            Label level1 = make_lable_search_swimmer(id.get(i).getLevel() + "", .05);
            Label time1 = make_lable_search_swimmer(id.get(i).getG_time() + "", .06);
            Label day1 = make_lable_search_swimmer(id.get(i).isG_day() ? "Sunday" : "Saturday", .06);

            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3b3;");
            title1.setPrefSize(bounds.getWidth() * .54, 0);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.getChildren().addAll(day1, time1, level1, coach1, n_day1, date1, id1, name1, num1);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3b3 ");
            });
            table_search.getChildren().add(title1);

            if (i > 15) {

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .017);
            }
        }

        pane_search_table.getChildren().clear();
        pane_search_table.getChildren().add(table_search);

    }
}
