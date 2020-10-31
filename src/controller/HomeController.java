/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import javafx.geometry.Rectangle2D;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.swing.JOptionPane;
import model.all_information_for_attend_coach;
import model.all_information_for_attend_swimmer;
import model.all_information_for_swimmer;
import model.attend_couch;
import model.attend_swimmer;
import model.coach;
import org.controlsfx.control.textfield.TextFields;
import service.BillPrint_coach;
import service.BillPrintable;

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
    private Button b_print_home, b_home, b_add_group, b_add_swimmer, b_add_coach, b_search, b_Settings;
    @FXML
    private AnchorPane anchorpane, pane_table, pane_search_table;
    @FXML
    private VBox p_list, vbox_search_group, vbox_search_coach, vbox_search_swimmer, v_s_attend, vbox_group_inf, vbox_search_att_s, vbox_search_att_c;
    @FXML
    private StackPane big_Stack;
    @FXML
    private Pane p_c_punish, p_update_group, p_home, p_add_group, p_s_add, p_C_add, p_search, p_Settings, select_pane_search, information_swimmer, p_group_inf;
    @FXML
    private JFXComboBox<Time> search_att_c_time, combobox_all_group, search_g_time, search_s_time, search_att_time, time_swimmer;
    @FXML
    private JFXComboBox<String> combobox_all_group_day;
    @FXML
    private ScrollPane scrooll, scroll_search;

    @FXML
    private JFXComboBox<String> add_group_coach, add_group_day, add_group_line, add_group_level, update_group_day, update_coach, update_group_line, update_group_level, add_s_level;
    @FXML
    private DatePicker add_s_age;
    @FXML
    private JFXTimePicker add_group_time, update_group_time;
    @FXML
    private JFXButton b_punish_late_1, b_punish_late_5, b_punish_glass, b_punish_behavior, b_punish_talk, search_group, search_att_s, search_swimmer, search_coach, search_att_c;

    @FXML
    private JFXRadioButton r_att_c_day, r_att_c_time, r_att_c_name, r_att_c_id, r_att_s_id, r_s_id, r_g_id, r_g_name, r_g_time, r_g_day, r_g_line, r_g_level, r_s_gender, r_s_day, r_s_time, r_s_name, r_att_s_name, r_att_time, r_att_day, r_att_num;

    @FXML
    private TextField t_mount, inf_c_phone, inf_c_level, inf_c_id, inf_c_name, search_att_c_name, search_att_c_id, search_att_id, search_s_id, search_g_id, add_C_name, add_C_adress, add_C_phone, search_g_name, punish_field, search_s_name, inf_s_name, inf_s_level, inf_s_coach, inf_s_time, inf_s_day, inf_s_address, inf_s_phone, inf_s_age, inf_s_gender, inf_s_group, inf_s_start_day, inf_s_end_day, search_att_name;

    @FXML
    private JFXComboBox<String> add_C_level, search_g_day, search_g_line, search_g_level, search_s_day, search_s_gender, add_s_gender, day_swimmer, coach_swimmer;

    @FXML
    private JFXComboBox<Integer> add_s_range, search_att_num;

    @FXML
    private HBox hbox_select_search;

    @FXML
    private JFXTextField date_print_home, text_s_note, add_s_name, add_s_phone;

    @FXML
    private JFXDatePicker search_att_day, search_att_c_day;
    @FXML
    private ButtonBar button_bar;
    @FXML
    private Label l_punish_minus, l_punish_bouns, l_absent, l_punish_late_1, l_punish_late_5, l_punish_glass, l_punish_behavior, l_punish_re, l_punish_talk;

    public void print_salary_coach(ActionEvent actionEvent) throws SQLException, PrinterException {

        if (t_mount.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "لم يتم ادخال قيمة الشهر");

        } else {
            allDb.DB_connection();
            java.awt.print.PrinterJob pj = java.awt.print.PrinterJob.getPrinterJob();
            pj.setPrintable(new BillPrint_coach(l_punish_late_1.getText(), l_punish_late_5.getText(),
                    l_absent.getText(), l_punish_glass.getText(), l_punish_behavior.getText(),
                    l_punish_talk.getText(), l_punish_re.getText(), inf_c_name.getText(),
                    allDb.get_cost_for_late1(), allDb.get_cost_for_late5(), allDb.get_cost_for_absent(),
                    allDb.get_cost_for_glass(), allDb.get_cost_for_Behavior(), allDb.get_cost_for_talk(),
                    allDb.get_cost_for_re(), Integer.parseInt(inf_c_id.getText()),
                    Double.parseDouble(t_mount.getText()) + minus() - bouns()), getPageFormat(pj));
            allDb.DB_close();
            pj.print();
            pj.cancel();

        }
    }

    public void add_coach(ActionEvent actionEvent) throws SQLException {

        if (add_C_name.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم ادخال الاسم");
        } else if (add_C_adress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم ادخال العنوان");
        } else if (add_C_phone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم ادخال الفون");
        } else if (add_C_level.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم ادخال المستوى");
        } else {
            allDb.DB_connection();

            allDb.addcoach(add_C_name.getText(), add_C_phone.getText(), add_C_adress.getText(), add_C_level.getValue());

            allDb.DB_close();

            initialize_search_group();
            initialize_add_group();
            initialize_add_swimmer();

            JOptionPane.showMessageDialog(null, "تم اضافه الكابتن");

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
            boolean bool = false;

            allDb.DB_connection();
            if (allDb.is_group_exist(coach.get(add_group_coach.getSelectionModel().getSelectedIndex()).getC_id(), add_group_day.getValue(), time)) {
                JOptionPane.showMessageDialog(null, "الكابتن  " + add_group_coach.getValue() + " " + " عنده مجموعة ف نفس المعاد");
            } else {
                allDb.Add_group(coach.get(add_group_coach.getSelectionModel().getSelectedIndex()).getC_id(), add_group_line.getValue(),
                        add_group_level.getValue(), add_group_day.getValue(), time);
                JOptionPane.showMessageDialog(null, "تم اضافة الجروب");

                for (int i = 0; i < combobox_all_group.getItems().size(); i++) {
                    if (combobox_all_group.getItems().get(i).equals(time)) {
                        bool = true;
                    }
                }
                if (!bool) {
                    combobox_all_group.getItems().add(time);
                    search_g_time.getItems().add(time);
                    search_s_time.getItems().add(time);
                    search_att_time.getItems().add(time);
                    time_swimmer.getItems().add(time);
                }
                initialize_search_group();
                initialize_add_swimmer();

            }
            allDb.DB_close();
        }
    }

    public void update_group(ActionEvent actionEvent) throws SQLException {

        if (update_coach.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار الكابتن");
        } else if (update_group_day.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
        } else if (update_group_level.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار المستوى");
        } else if (update_group_line.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار الحارة");
        } else {
            Time time = Time.valueOf(update_group_time.getValue());
            int b = 0;
            if (day_swimmer.getValue() == "Saturday") {
                b = 0;
            } else if (day_swimmer.getValue() == "Sunday") {
                b = 1;
            } else if (day_swimmer.getValue() == "friday") {
                b = 2;
            }
            boolean bool = false;

            allDb.DB_connection();
            if (allDb.is_group_exist(coach.get(update_coach.getSelectionModel().getSelectedIndex()).getC_id(), update_group_day.getValue(), time)) {
                JOptionPane.showMessageDialog(null, "الكابتن  " + update_coach.getValue() + " " + " عنده مجموعة ف نفس المعاد");
            } else {
//                allDb.Add_group(coach.get(update_coach.getSelectionModel().getSelectedIndex()).getC_id(), update_group_line.getValue(),
//                        update_group_level.getValue(), update_group_day.getValue(), time);
                allDb.update_group(id_update, coach.get(update_coach.getSelectionModel().getSelectedIndex()).getC_id(), update_group_line.getValue(),
                        update_group_level.getValue(), update_group_day.getValue(), time);

                JOptionPane.showMessageDialog(null, "تم تعديل الجروب");

                for (int i = 0; i < combobox_all_group.getItems().size(); i++) {
                    if (combobox_all_group.getItems().get(i).equals(time)) {
                        bool = true;
                    }
                }
                if (!bool) {
                    combobox_all_group.getItems().add(time);
                    search_g_time.getItems().add(time);
                    search_s_time.getItems().add(time);
                    search_att_time.getItems().add(time);
                    time_swimmer.getItems().add(time);
                }
                initialize_search_group();
                initialize_add_swimmer();

            }
            allDb.DB_close();
        }
    }

    public void add_swimmer(ActionEvent actionEvent) throws SQLException, PrinterException, ParseException {
        if (add_s_name.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم ادخال الاسم");
        } else if (add_s_phone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم ادخال الفون");
        } else if (add_s_gender.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
        } else if (day_swimmer.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
        } else if (time_swimmer.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
        } else if (coach_swimmer.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار المدرب");
        } else if (add_s_range.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار مده الاشتراك");
        } else if (add_s_level.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار  المستوى");
        } else {
            int b = 0;
            if (day_swimmer.getValue() == "Saturday") {
                b = 0;
            } else if (day_swimmer.getValue() == "Sunday") {
                b = 1;
            } else if (day_swimmer.getValue() == "friday") {
                b = 2;
            }
            allDb.DB_connection();

            int g_id = allDb.get_id_check_group_exist(coach_swimmer.getValue(), time_swimmer.getValue(), b);

            if (allDb.check_group_exist(coach_swimmer.getValue(), time_swimmer.getValue(), b)) {

                int count = allDb.get_swimmer_by_group(g_id).size();
                String n_coach=allDb.get_coach_name_by_g_id(g_id);
                System.out.println(count);
                Date sqlDate = java.sql.Date.valueOf(add_s_age.getValue());
                // Date date1=   (Date) new SimpleDateFormat("dd/MM/yyyy").parse(add_s_age.getText());  

                if (count < 8) {
                    int curr_swimmer_id = allDb.addswimmer(add_s_name.getText(), sqlDate, add_s_gender.getValue(), add_s_level.getValue(), add_s_phone.getText(), g_id, add_s_range.getValue(), b);

                    if (curr_swimmer_id != 0) {
                        List<all_information_for_group> id = new ArrayList<all_information_for_group>();
                        id = allDb.search_group_by_name_and_time_and_day(coach_swimmer.getValue(), time_swimmer.getValue(), b);

                        System.out.println(id);

                        java.awt.print.PrinterJob pj = java.awt.print.PrinterJob.getPrinterJob();
                        if (id.get(0).getDay() == 0) {
                            pj.setPrintable(new BillPrintable(add_s_name.getText(), add_s_phone.getText(), sqlDate, id.get(0).getG_level(), add_s_level.getValue(), "Saturday", id.get(0).getTime().toString(), id.get(0).getTrack(),n_coach, curr_swimmer_id, allDb.get_cost_for_one_day() * add_s_range.getValue(), add_s_range.getValue()), getPageFormat(pj));

                        } else if (id.get(0).getDay() == 1) {
                            pj.setPrintable(new BillPrintable(add_s_name.getText(), add_s_phone.getText(), sqlDate, id.get(0).getG_level(), add_s_level.getValue(), "Sunday", id.get(0).getTime().toString(), id.get(0).getTrack(),n_coach, curr_swimmer_id, allDb.get_cost_for_one_day() * add_s_range.getValue(), add_s_range.getValue()), getPageFormat(pj));

                        } else if (id.get(0).getDay() == 2) {
                            pj.setPrintable(new BillPrintable(add_s_name.getText(), add_s_phone.getText(), sqlDate, id.get(0).getG_level(), add_s_level.getValue(), "friday", id.get(0).getTime().toString(), id.get(0).getTrack(),n_coach, curr_swimmer_id, allDb.get_cost_for_one_day() * add_s_range.getValue(), add_s_range.getValue()), getPageFormat(pj));

                        }
                        pj.print();
                        pj.cancel();

                        JOptionPane.showMessageDialog(null, "تم اضافه السباح");

                        initialize_search_swimmer();

                        add_s_name.setText("");
                        add_s_phone.setText("");
                        add_s_level.getItems().clear();

                    }
                } else {

                    JOptionPane.showMessageDialog(null, "هذا الجروب مكتمل");

                    add_s_name.setText("");
                    add_s_phone.setText("");

                }

            } else {
                JOptionPane.showMessageDialog(null, "الكابتن  " + coach_swimmer.getValue() + " " + "مفيش مجموعة ف نفس المعاد");

            }
            allDb.DB_close();
        }

    }

    Time time_to_tranfer_to_add_swimmer;
    String coach_to_tranfer_to_add_swimmer, day_to_tranfer_to_add_swimmer;

    public void tranfer_to_add_swimmer(ActionEvent actionEvent) throws SQLException {

        coach_swimmer.setValue(coach_to_tranfer_to_add_swimmer);
        time_swimmer.setValue(time_to_tranfer_to_add_swimmer);
        day_swimmer.setValue(day_to_tranfer_to_add_swimmer);

        p_s_add.toFront();
    }

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
        if (actionEvent.getSource() == b_Settings) {
            p_Settings.toFront();
        }
    }

    public void print(ActionEvent actionEvent) {
        //  Node p_home = new Circle(100, 200, 200);

        java.util.Date now = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(now));
        date_print_home.setVisible(true);
        date_print_home.setDisable(false);
        date_print_home.setText(sdf.format(now));
        b_print_home.setVisible(false);

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double scaleX = 1.0;
            if (pageLayout.getPrintableWidth() < p_home.getBoundsInParent().getWidth()) {
                scaleX = pageLayout.getPrintableWidth() / p_home.getBoundsInParent().getWidth();
            }
            double scaleY = 1.0;
            if (pageLayout.getPrintableHeight() < p_home.getBoundsInParent().getHeight()) {
                scaleY = pageLayout.getPrintableHeight() / p_home.getBoundsInParent().getHeight();
            }
            double scaleXY = Double.min(scaleX, scaleY);
            Scale scale = new Scale(scaleXY, scaleXY);
            p_home.getTransforms().add(scale);
            boolean success = job.printPage(p_home);
            p_home.getTransforms().remove(scale);
            if (success) {
                job.endJob();
            }
        }
        date_print_home.setVisible(false);
        b_print_home.setVisible(true);
    }

    public void print_search(ActionEvent actionEvent) {
        //  Node p_home = new Circle(100, 200, 200);
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double scaleX = 1.0;
            if (pageLayout.getPrintableWidth() < table_search.getBoundsInParent().getWidth()) {
                scaleX = pageLayout.getPrintableWidth() / table_search.getBoundsInParent().getWidth();
            }
            double scaleY = 1.0;
            if (pageLayout.getPrintableHeight() < table_search.getBoundsInParent().getHeight()) {
                scaleY = pageLayout.getPrintableHeight() / table_search.getBoundsInParent().getHeight();
            }
            double scaleXY = Double.min(scaleX, scaleY);
            Scale scale = new Scale(scaleXY, scaleXY);
            table_search.getTransforms().add(scale);
            boolean success = job.printPage(table_search);
            table_search.getTransforms().remove(scale);
            if (success) {
                job.endJob();
            }
        }
    }

    public void print_g_inf(ActionEvent actionEvent) {
        //  Node p_home = new Circle(100, 200, 200);
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double scaleX = 1.0;
            if (pageLayout.getPrintableWidth() < vbox_group_inf.getBoundsInParent().getWidth()) {
                scaleX = pageLayout.getPrintableWidth() / vbox_group_inf.getBoundsInParent().getWidth();
            }
            double scaleY = 1.0;
            if (pageLayout.getPrintableHeight() < vbox_group_inf.getBoundsInParent().getHeight()) {
                scaleY = pageLayout.getPrintableHeight() / vbox_group_inf.getBoundsInParent().getHeight();
            }
            double scaleXY = Double.min(scaleX, scaleY);
            Scale scale = new Scale(scaleXY, scaleXY);
            vbox_group_inf.getTransforms().add(scale);
            boolean success = job.printPage(vbox_group_inf);
            vbox_group_inf.getTransforms().remove(scale);
            if (success) {
                job.endJob();
            }
        }
    }

    public void switch_search(ActionEvent actionEvent) throws SQLException {
        if (actionEvent.getSource() == search_group) {
            search_group.setStyle("-fx-background-color: #ffffff;");
            search_coach.setStyle("-fx-background-color:   #181a1b;");
            search_swimmer.setStyle("-fx-background-color:   #181a1b;");
            search_att_s.setStyle("-fx-background-color: #181a1b;");
            // search_att_c.setStyle("-fx-background-color: #181a1b;");

            // vbox_search_att_c.setDisable(true);
            vbox_search_att_s.setDisable(true);
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
            search_att_s.setStyle("-fx-background-color: #181a1b;");

            // search_att_c.setStyle("-fx-background-color: #181a1b;");
            // search_att_c.setStyle("-fx-background-color: #181a1b;");
            //vbox_search_att_c.setDisable(true);   
            vbox_search_att_s.setDisable(true);
            vbox_search_group.setDisable(true);
            vbox_search_coach.setDisable(false);
            vbox_search_swimmer.setDisable(true);

            try {

                allDb.DB_connection();
                search_att_coach_list = allDb.search_attend_coach();
                BuildCoach_att(search_att_coach_list);
                allDb.DB_close();
            } catch (SQLException ex) {
            }

        }
        if (actionEvent.getSource() == search_swimmer) {
            search_group.setStyle("-fx-background-color:   #181a1b;");
            search_coach.setStyle("-fx-background-color:   #181a1b;");
            search_swimmer.setStyle("-fx-background-color:  #ffffff;");
            search_att_s.setStyle("-fx-background-color: #181a1b;");
            // search_att_c.setStyle("-fx-background-color: #181a1b;");

            //  vbox_search_att_c.setDisable(true);
            vbox_search_att_s.setDisable(true);
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
        if (actionEvent.getSource() == search_att_s) {
            search_group.setStyle("-fx-background-color: #181a1b;");
            search_coach.setStyle("-fx-background-color:   #181a1b;");
            search_swimmer.setStyle("-fx-background-color:   #181a1b;");
            search_att_s.setStyle("-fx-background-color: #ffffff;");
            // search_att_c.setStyle("-fx-background-color: #181a1b;");

            //  vbox_search_att_c.setDisable(true);
            vbox_search_att_s.setDisable(false);
            vbox_search_group.setDisable(true);
            vbox_search_coach.setDisable(true);
            vbox_search_swimmer.setDisable(true);

            try {
                allDb.DB_connection();
                search_att_swimmer_list = allDb.search_attend_swimmer();
                BuildSearch_att(search_att_swimmer_list);
                allDb.DB_close();
            } catch (SQLException ex) {
            }

        }
//           if (actionEvent.getSource() == search_att_c) {
//            search_group.setStyle("-fx-background-color: #181a1b;");
//            search_coach.setStyle("-fx-background-color:   #181a1b;");
//            search_swimmer.setStyle("-fx-background-color:   #181a1b;");
//            search_att_s.setStyle("-fx-background-color: #181a1b;");
//            search_att_c.setStyle("-fx-background-color: #ffffff;");
//            
//            vbox_search_att_c.setDisable(false);
//            vbox_search_att_s.setDisable(true);
//            vbox_search_group.setDisable(true);
//            vbox_search_coach.setDisable(true);
//            vbox_search_swimmer.setDisable(true);
//
//           try {
//                allDb.DB_connection();
//                search_att_swimmer_list = allDb.search_attend_swimmer();
//                BuildSearch_att(search_att_swimmer_list);
//                allDb.DB_close();
//            } catch (SQLException ex) {
//            }
//
//        }
    }

    public void punish(ActionEvent actionEvent) {
        if (actionEvent.getSource() == b_punish_late_1) {
            try {
                allDb.DB_connection();
                allDb.Add_punish(coach_id_punish, 1);
                l_punish_late_1.setText((Integer.parseInt(l_punish_late_1.getText()) + 1) + "");
                l_punish_bouns.setText((Double.parseDouble(l_punish_bouns.getText()) + allDb.get_cost_for_late1()) + "");
                allDb.DB_close();
            } catch (SQLException ex) {
            }
        }
        if (actionEvent.getSource() == b_punish_late_5) {
            try {
                allDb.DB_connection();
                allDb.Add_punish(coach_id_punish, 2);
                l_punish_late_5.setText((Integer.parseInt(l_punish_late_5.getText()) + 1) + "");
                l_punish_bouns.setText((Double.parseDouble(l_punish_bouns.getText()) + allDb.get_cost_for_late5()) + "");
                allDb.DB_close();
            } catch (SQLException ex) {
            }
        }
        if (actionEvent.getSource() == b_punish_glass) {
            try {
                allDb.DB_connection();
                allDb.Add_punish(coach_id_punish, 3);
                l_punish_glass.setText((Integer.parseInt(l_punish_glass.getText()) + 1) + "");
                l_punish_bouns.setText((Double.parseDouble(l_punish_bouns.getText()) + allDb.get_cost_for_glass()) + "");
                allDb.DB_close();
            } catch (SQLException ex) {
            }
        }
        if (actionEvent.getSource() == b_punish_behavior) {
            try {
                allDb.DB_connection();
                allDb.Add_punish(coach_id_punish, 4);
                l_punish_behavior.setText((Integer.parseInt(l_punish_behavior.getText()) + 1) + "");
                l_punish_bouns.setText((Double.parseDouble(l_punish_bouns.getText()) + allDb.get_cost_for_Behavior()) + "");
                allDb.DB_close();
            } catch (SQLException ex) {
            }
        }
        if (actionEvent.getSource() == b_punish_talk) {
            try {
                allDb.DB_connection();
                allDb.Add_punish(coach_id_punish, 5);
                l_punish_talk.setText((Integer.parseInt(l_punish_talk.getText()) + 1) + "");
                l_punish_bouns.setText((Double.parseDouble(l_punish_bouns.getText()) + allDb.get_cost_for_talk()) + "");
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {

                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {

                    b = 1;
                } else if (search_g_day.getValue() == "friday") {

                    b = 2;
                }

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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }

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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_g_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_g_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_g_day.getValue() == "friday") {
                    b = 2;
                }
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
        } else if (r_g_id.isSelected()) {
            if (search_g_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم كتابة الرقم");
            } else {
                try {
                    allDb.DB_connection();
                    search_group_list = allDb.search_group_by_id(Integer.parseInt(search_g_id.getText()));
                    allDb.DB_close();
                    BuildSearch(search_group_list);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "يجب كتابه رقم و ليس حرف");
                }
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
                int b = 0;
                if (search_s_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_s_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_s_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_s_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_s_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_s_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_s_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_s_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_s_day.getValue() == "friday") {
                    b = 2;
                }
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
                int b = 0;
                if (search_s_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_s_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_s_day.getValue() == "friday") {
                    b = 2;
                }
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_by_time_and_day(search_s_time.getValue(), b);
                BuildSearch_Swimmer(search_swimmer_list);
                allDb.DB_close();
            }
        } else if (r_s_day.isSelected()) {
            if (search_s_day.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                int b = 0;
                if (search_s_day.getValue() == "Saturday") {
                    b = 0;
                } else if (search_s_day.getValue() == "Sunday") {
                    b = 1;
                } else if (search_s_day.getValue() == "friday") {
                    b = 2;
                }

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
        } else if (r_s_id.isSelected()) {
            if (search_s_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم كتابه رقم السباح");
            } else {
                try {
                    allDb.DB_connection();
                    search_swimmer_list = allDb.search_swimmer_id(Integer.parseInt(search_s_id.getText()));
                    BuildSearch_Swimmer(search_swimmer_list);
                    allDb.DB_close();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "يجب كتابه رقم و ليس حرف");
                }

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
        } else if (r_att_s_id.isSelected()) {
            if (search_att_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم كتابه رقم  السباح");
            } else {
                try {
                    allDb.DB_connection();
                    search_att_swimmer_list = allDb.search_attend_swimmer_by_id(Integer.parseInt(search_att_id.getText()));
                    BuildSearch_att(search_att_swimmer_list);
                    allDb.DB_close();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "يجب كتابه رقم و ليس حرف");
                }
            }
        }
    }

    public void att_search_coach(ActionEvent actionEvent) throws SQLException {

        if (r_att_c_day.isSelected() && r_att_c_time.isSelected()) {
            if (search_att_c_day.getValue() == null) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else if (search_att_c_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                Date sqlDate = java.sql.Date.valueOf(search_att_c_day.getValue());
                allDb.DB_connection();
                search_att_coach_list = allDb.search_attend_coach_by_day_time(sqlDate, search_att_c_time.getValue());
                BuildCoach_att(search_att_coach_list);
                allDb.DB_close();
            }
        } else if (r_att_c_day.isSelected()) {
            if (search_att_c_day.getValue() == null) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار اليوم");
            } else {
                Date sqlDate = java.sql.Date.valueOf(search_att_c_day.getValue());
                allDb.DB_connection();
                search_att_coach_list = allDb.search_attend_coach_by_day(sqlDate);
                BuildCoach_att(search_att_coach_list);
                allDb.DB_close();
            }
        } else if (r_att_c_time.isSelected()) {
            if (search_att_c_time.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الوقت");
            } else {
                // Date sqlDate = java.sql.Date.valueOf(search_att_day.getValue());
                allDb.DB_connection();
                search_att_coach_list = allDb.search_attend_coach_by_time(search_att_c_time.getValue());
                BuildCoach_att(search_att_coach_list);
                allDb.DB_close();
            }
        } else if (r_att_c_name.isSelected()) {
            if (search_att_c_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار الاسم");
            } else {
                allDb.DB_connection();
                search_att_coach_list = allDb.search_attend_coach_by_name(search_att_c_name.getText());
                BuildCoach_att(search_att_coach_list);
                allDb.DB_close();
            }
        } else if (r_att_c_id.isSelected()) {
            if (search_att_c_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم كتابه رقم  الكابتن");
            } else {
                try {
                    allDb.DB_connection();
                    search_att_coach_list = allDb.search_attend_coach_by_id(Integer.parseInt(search_att_c_id.getText()));
                    BuildCoach_att(search_att_coach_list);
                    allDb.DB_close();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "يجب كتابه رقم و ليس حرف");
                }
            }
        }
    }

    DB allDb;
    Rectangle2D bounds;
    List<all_information_for_group> id;
    List<all_information_for_group> search_group_list;
    List<all_information_for_swimmer> search_swimmer_list;
    List<all_information_for_attend_swimmer> search_att_swimmer_list;
    List<all_information_for_attend_coach> search_att_coach_list;
    List<Integer> all_g_id;
    List<List<swimmer>> t;
    List<coach> coach;
    int bool;
    int coach_id_punish;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initialize_home();

        initialize_add_group();

        ///////////////////add coach///////////
        add_C_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", " l6", "l7", "l8");
        ///////////////////add coach///////////

        ///////////////////add swimmer///////////
        initialize_add_swimmer();

        ///////////////////add swimmer///////////
        ///////////////////add swimmer///////////
        ///////////////////////search group//////
        initialize_search_group();
        ///////////////////////search group//////

        ///////////////////////search swimmer//////
        initialize_search_swimmer();
        ///////////////////////search swimmer//////
        ///////////////////////search attend//////
        initialize_search_att();
        ///////////////////////search attend//////
///////////////////////coach attend//////
        initialize_search_coach_att();
        ///////////////////////coach attend//////
    }

    ////////////////////////////////home/////////////
    VBox table = new VBox();
    List<all_information_for_group> all_re_coach = new ArrayList<all_information_for_group>();
    List<String> l_name = new ArrayList<String>();
    List<Integer> l_re_id = new ArrayList<Integer>();

    private void BuildHome(List<all_information_for_group> id, List<List<swimmer>> all_S) throws SQLException {

        table.setStyle("-fx-background-color:  #ffb3b3;");
        table.setSpacing(5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Label num = make_lable("ID", .039);
        Label name = make_lable("Name", 0.1735);
        Label coach = make_lable("Coach", 0.05);
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
        Label level = make_lable("level", .03);
        Label notes = make_lable("notes", .19);
        Label start = make_lable("start", .05);
        HBox title = new HBox();
        title.setStyle("-fx-background-color: #ffffff;");
        title.setPrefSize(1000, 0);
        title.setMaxSize(10000, 10000);
        title.getChildren().addAll(notes, level, l12, l11, l10, l9, l8, l7, l6, l5, l4, l3, l2, l1, coach, start, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table.getChildren().add(title);

        for (int z = 0; z < id.size(); z++) {
            HBox row = new HBox();
            row.setStyle("-fx-background-color:  #ffb3d9;");
            row.setAlignment(Pos.CENTER_RIGHT);

            VBox all_num = new VBox();
            all_num.setStyle("-fx-background-color:  #ffffff;");
            all_num.setAlignment(Pos.CENTER_RIGHT);

            for (int i = 0; i < 8; i++) {
                Label l = make_lable("" + (i + 1), .039);
                //  all_num.getChildren().add(l);
            }
            for (int i = 0; i < all_S.get(z).size(); i++) {
                Label la = make_lable(all_S.get(z).get(i).getS_id() + "", 0.039);
                all_num.getChildren().add(la);
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");

            }
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                Label la = make_lable("", 0.039);
                all_num.getChildren().add(la);
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");

            }

            VBox Swimer = new VBox();
            Swimer.setStyle("-fx-background-color: #ffe6f9;");
            for (int i = 0; i < all_S.get(z).size(); i++) {
                Label la = make_lable(all_S.get(z).get(i).getName() + "\t \t" + sdf.format(all_S.get(z).get(i).getAge()), 0.1735);
                la.setStyle("-fx-font-size: 14px;-fx-background-color:#e6f2ff;-fx-border-color:#fff;");
                Swimer.getChildren().add(la);
                //
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");
            }
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                Label la = make_lable("", 0.1735);
                Swimer.getChildren().add(la);
                //
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");

            }

            VBox vstart = new VBox();
            vstart.setStyle("-fx-background-color:  #ffffb3;");
            for (int i = 0; i < all_S.get(z).size(); i++) {

                Label l = make_lable(all_S.get(z).get(i).getStart().toLocalDate().getMonth().getValue() + "", .05);
                l.setStyle("-fx-font-size: 16px;-fx-background-color:#ffffb3;-fx-border-color:#fff;");
                vstart.getChildren().add(l);
            }
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                Label la = make_lable("", 0.05);
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffffb3;-fx-border-color:#fff;");
                vstart.getChildren().add(la);
                //

            }

            VBox coach_att = new VBox();
            coach_att.setStyle("-fx-background-color:  #ffb3b3;-fx-border-color:#000;");
            coach_att.setAlignment(Pos.TOP_CENTER);
            coach_att.setSpacing(30);
            coach_att.setPrefSize(bounds.getWidth() * .05, 0);

            JFXCheckBox ch_coach = new JFXCheckBox();
            ch_coach.setCheckedColor(rgb(42, 115, 255));
            ch_coach.setTextFill(rgb(255, 255, 255));
            ch_coach.setStyle("-fx-font-size: 12px;-fx-background-color: #ffb3d9;");
            ch_coach.setPrefSize(bounds.getWidth() * .007, 0);
            ch_coach.setAlignment(Pos.CENTER_RIGHT);
            ch_coach.setId(id.get(z).getC_id() + "|" + z);

            JFXComboBox t1 = new JFXComboBox();
            t1.setPrefWidth(bounds.getWidth() * 0.05);
            t1.setStyle("-fx-font-size: 12px;-fx-background-color: #ffb3d9;-fx-border-color:#000;");
            t1.setDisable(true);

            String[] comp = ch_coach.getId().split("\\|");
            coach_att.setOnMouseClicked((event) -> {
                coach_id_punish = Integer.parseInt(comp[0]);
                inf_c_id.setText(coach_id_punish + "");
                inf_c_name.setText(id.get(Integer.parseInt(comp[1])).getName());
                inf_c_level.setText(id.get(Integer.parseInt(comp[1])).getC_level());
                inf_c_phone.setText(id.get(Integer.parseInt(comp[1])).getPhone());

                try {
                    allDb.DB_connection();
                    l_punish_late_1.setText(allDb.get_count_of_punish(coach_id_punish, 1) + "");
                    l_punish_late_5.setText(allDb.get_count_of_punish(coach_id_punish, 2) + "");
                    l_punish_glass.setText(allDb.get_count_of_punish(coach_id_punish, 3) + "");
                    l_punish_behavior.setText(allDb.get_count_of_punish(coach_id_punish, 4) + "");
                    l_punish_talk.setText(allDb.get_count_of_punish(coach_id_punish, 5) + "");
                    l_absent.setText(allDb.get_count_of_punish_of_attend(coach_id_punish) + "");
                    l_punish_re.setText(allDb.get_count_of_punish_of_attend_re(coach_id_punish) + "");
                    l_punish_bouns.setText(bouns() + "");
                    l_punish_minus.setText(minus() + "");
                } catch (SQLException ex) {
                    System.out.println(" get_count_of_punish :" + ex);
                }

                p_c_punish.toFront();
            });

            try {
                allDb.DB_connection();

                all_re_coach = allDb.search_group_by_notequletime_and_day(id.get(Integer.parseInt(comp[1])).getTime(), id.get(Integer.parseInt(comp[1])).getDay());

                l_name.clear();
                l_re_id.clear();
                for (int list = 0; list < all_re_coach.size(); list++) {
                    l_name.add(all_re_coach.get(list).getName());
                    l_re_id.add(all_re_coach.get(list).getC_id());

                }

                t1.getItems().addAll(l_name);
                t1.setOnAction((e) -> {
                    try {
                        allDb.DB_connection();
                        if (t1.getSelectionModel().isEmpty()) {
                            allDb.Add_couch_attend(id.get(Integer.parseInt(comp[1])).getG_id(), all_re_coach.get(t1.getSelectionModel().getSelectedIndex()).getC_id());
                        } else {

                            int update = allDb.update_attend_coach(id.get(Integer.parseInt(comp[1])).getG_id(), all_re_coach.get(t1.getSelectionModel().getSelectedIndex()).getC_id());
                            if (update == 0) {
                                allDb.Add_couch_attend(id.get(Integer.parseInt(comp[1])).getG_id(), all_re_coach.get(t1.getSelectionModel().getSelectedIndex()).getC_id());
                            }
                        }
                        allDb.DB_connection();

                    } catch (SQLException ex) {
                    }

                });
                allDb.DB_connection();
            } catch (SQLException ex) {
            }

            if (allDb.get_att_coach_bool(id.get(z).getG_id())) {
                List<attend_couch> s = new ArrayList<attend_couch>();
                s = allDb.get_att_coach(id.get(z).getG_id());
                for (int i = 0; i < s.size(); i++) {
                    ch_coach.setSelected(true);
                    l_re_id.indexOf(s.get(0).getRep_name());
                    t1.setValue(l_name.get(l_re_id.indexOf(s.get(0).getRep_name())));
                    t1.setDisable(false);
                }
            }
            ch_coach.setOnAction((event) -> {

                if (ch_coach.isSelected()) {

                    t1.setDisable(false);
                } else {
                    t1.setDisable(true);
                    try {
                        allDb.DB_connection();
                        allDb.delet_attend_coach(id.get(Integer.parseInt(comp[1])).getG_id());
                        allDb.DB_close();
                    } catch (SQLException ex) {
                    }
                }

            });

            Label la = new Label(id.get(z).getName() + " \n " + id.get(z).getTrack() + "");
            la.setPrefWidth(bounds.getWidth() * 0.07);
            la.setMaxHeight(1000);
            la.setStyle("-fx-font-size: 9px; -fx-background-color: #ffb3d9;");
            la.setAlignment(Pos.CENTER);
            la.setRotate(270.0);
            coach_att.getChildren().addAll(t1, ch_coach, la);

            HBox all_att = new HBox();
            for (int j = 12; j > 0; j--) {
                VBox att = new VBox();
                att.setStyle("-fx-background-color:   #ffffff;");
                for (int i = 0; i < all_S.get(z).size(); i++) {
                    JFXCheckBox ch = new JFXCheckBox();
                    ch.setCheckedColor(rgb(42, 115, 255));
                    ch.setTextFill(rgb(255, 255, 255));
                    ch.setStyle("-fx-font-size: 16px;-fx-background-color: #e6f2ff;-fx-border-color:#000;");
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
                    if (bool == 0) {
                        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                                || date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                                .collect(Collectors.toList());
                        if (ldate.size() == 13) {
                            ldate.remove(12);
                        }
                        if (!ldate.get(j - 1).isEqual(currentdate)) {
                            ch.setDisable(true);
                        }
                    }
                    if (bool == 1) {
                        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                                .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                                || date.getDayOfWeek() == DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.THURSDAY)
                                .collect(Collectors.toList());
                        if (ldate.size() == 13) {
                            ldate.remove(12);
                        }
                        if (!ldate.get(j - 1).isEqual(currentdate)) {
                            ch.setDisable(true);
                        }

                    } else if (bool == 2) {
                        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                                || date.getDayOfWeek() == DayOfWeek.FRIDAY)
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
                for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                    Label l = make_lable("", 0.023);
                    att.getChildren().add(l);
                }
                all_att.getChildren().add(att);
            }

            VBox all_level = new VBox();
            all_level.setStyle("-fx-background-color:  #ffe6e6;");
            for (int i = 0; i < 8; i++) {
                Label l = make_lable(id.get(z).getG_level(), .03);
                all_level.getChildren().add(l);
                l.setStyle("-fx-background-color: #ffe6e6;-fx-font-size: 18px;");
            }

            VBox all_nots = new VBox();
            all_nots.setStyle("-fx-background-color:   #ffffb3;");
            for (int i = 0; i < all_S.get(z).size(); i++) {

                TextField t = new TextField();
                t.setPrefWidth(bounds.getWidth() * .19);
                t.setStyle("-fx-font-size: 12px;-fx-background-color:	 #ffffb3;-fx-border-color:#000;");
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
                t.setStyle("-fx-font-size: 12px;-fx-background-color:	#ffffb3;-fx-border-color:#000;");
                t.setAlignment(Pos.CENTER);
                all_nots.getChildren().add(t);

            }

            row.getChildren().add(all_nots);
            row.getChildren().add(all_level);
            row.getChildren().add(all_att);
            row.getChildren().add(coach_att);
            row.getChildren().add(vstart);
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

    private void BuildHomefriday(List<all_information_for_group> id, List<List<swimmer>> all_S) throws SQLException {

        table.setStyle("-fx-background-color:  #ffb3b3;");
        table.setSpacing(5);

        Label num = make_lable("ID", .039);
        Label name = make_lable("Name", 0.1735);
        Label coach = make_lable("Coach", 0.05);
        Label l1 = make_lable("1", .023);
        Label l2 = make_lable("2", .023);
        Label l3 = make_lable("3", .023);
        Label l4 = make_lable("4", .023);
        Label l5 = make_lable("5", .023);
        Label l6 = make_lable("6", .023);
        Label l7 = make_lable("7", .023);
        Label l8 = make_lable("8", .023);
        Label level = make_lable("level", .03);
        Label notes = make_lable("notes", .19);
        Label start = make_lable("start", .05);
        HBox title = new HBox();
        title.setStyle("-fx-background-color: #ffffff;");
        title.setPrefSize(1000, 0);
        title.setMaxSize(10000, 10000);
        title.getChildren().addAll(notes, level, l8, l7, l6, l5, l4, l3, l2, l1, coach, start, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table.getChildren().add(title);

        for (int z = 0; z < id.size(); z++) {
            HBox row = new HBox();
            row.setStyle("-fx-background-color:  #ffb3d9;");
            row.setAlignment(Pos.CENTER_RIGHT);

            VBox all_num = new VBox();
            all_num.setStyle("-fx-background-color:  #ffffff;");
            all_num.setAlignment(Pos.CENTER_RIGHT);

            for (int i = 0; i < 8; i++) {
                Label l = make_lable("" + (i + 1), .039);
                //  all_num.getChildren().add(l);
            }
            for (int i = 0; i < all_S.get(z).size(); i++) {
                Label la = make_lable(all_S.get(z).get(i).getS_id() + "", 0.039);
                all_num.getChildren().add(la);
                //
                //la.setStyle("-fx-font-size: 16px;-fx-background-color: #ffe6f9;");
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");

            }
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                Label la = make_lable("", 0.039);
                all_num.getChildren().add(la);
                //
                //  la.setStyle("-fx-font-size: 12px;-fx-background-color: #ffe6f9;");
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");

            }

            VBox Swimer = new VBox();
            Swimer.setStyle("-fx-background-color: #ffe6f9;");
            for (int i = 0; i < all_S.get(z).size(); i++) {
                Label la = make_lable(all_S.get(z).get(i).getName() + "\t \t" + all_S.get(z).get(i).getAge(), 0.1735);
                Swimer.getChildren().add(la);
                //
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");
            }
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                Label la = make_lable("", 0.1735);
                Swimer.getChildren().add(la);
                //
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");

            }

            VBox vstart = new VBox();
            vstart.setStyle("-fx-background-color:  #ffffb3;");
            for (int i = 0; i < all_S.get(z).size(); i++) {

                Label l = make_lable(all_S.get(z).get(i).getStart().toLocalDate().getMonth().getValue() + "", .05);
                l.setStyle("-fx-font-size: 16px;-fx-background-color:#ffffb3;-fx-border-color:#fff;");
                vstart.getChildren().add(l);
            }
            for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                Label la = make_lable("", 0.05);
                la.setStyle("-fx-font-size: 16px;-fx-background-color:#ffffb3;-fx-border-color:#fff;");
                vstart.getChildren().add(la);
                //

            }

            VBox coach_att = new VBox();
            coach_att.setStyle("-fx-background-color:  #ffb3b3;-fx-border-color:#000;");
            coach_att.setAlignment(Pos.TOP_CENTER);
            coach_att.setSpacing(30);
            coach_att.setPrefSize(bounds.getWidth() * .05, 0);
            JFXCheckBox ch_coach = new JFXCheckBox();
            ch_coach.setCheckedColor(rgb(42, 115, 255));
            ch_coach.setTextFill(rgb(255, 255, 255));
            ch_coach.setStyle("-fx-font-size: 12px;-fx-background-color: #ffb3d9;");
            ch_coach.setPrefSize(bounds.getWidth() * .007, 0);
            ch_coach.setAlignment(Pos.CENTER_RIGHT);
            ch_coach.setId(id.get(z).getC_id() + "|" + z);

            JFXComboBox t1 = new JFXComboBox();
            t1.setPrefWidth(bounds.getWidth() * 0.05);
            t1.setStyle("-fx-font-size: 12px;-fx-background-color: #ffb3d9;-fx-border-color:#000;");
            t1.setDisable(true);

            String[] comp = ch_coach.getId().split("\\|");
            coach_att.setOnMouseClicked((event) -> {
                coach_id_punish = Integer.parseInt(comp[0]);
                inf_c_id.setText(coach_id_punish + "");
                inf_c_name.setText(id.get(Integer.parseInt(comp[1])).getName());
                inf_c_level.setText(id.get(Integer.parseInt(comp[1])).getC_level());
                inf_c_phone.setText(id.get(Integer.parseInt(comp[1])).getPhone());

                try {
                    allDb.DB_connection();
                    l_punish_late_1.setText(allDb.get_count_of_punish(coach_id_punish, 1) + "");
                    l_punish_late_5.setText(allDb.get_count_of_punish(coach_id_punish, 2) + "");
                    l_punish_glass.setText(allDb.get_count_of_punish(coach_id_punish, 3) + "");
                    l_punish_behavior.setText(allDb.get_count_of_punish(coach_id_punish, 4) + "");
                    l_punish_talk.setText(allDb.get_count_of_punish(coach_id_punish, 5) + "");
                    l_absent.setText(allDb.get_count_of_punish_of_attend(coach_id_punish) + "");
                    l_punish_re.setText(allDb.get_count_of_punish_of_attend_re(coach_id_punish) + "");
                    l_punish_bouns.setText(bouns() + "");
                    l_punish_minus.setText(minus() + "");
                } catch (SQLException ex) {
                    System.out.println(" get_count_of_punish :" + ex);
                }
                p_c_punish.toFront();
            });

            try {
                allDb.DB_connection();

                all_re_coach = allDb.search_group_by_notequletime_and_day(id.get(Integer.parseInt(comp[1])).getTime(), id.get(Integer.parseInt(comp[1])).getDay());

                l_name.clear();
                l_re_id.clear();
                for (int list = 0; list < all_re_coach.size(); list++) {
                    l_name.add(all_re_coach.get(list).getName());
                    l_re_id.add(all_re_coach.get(list).getC_id());

                }

                t1.getItems().addAll(l_name);
                t1.setOnAction((e) -> {
                    try {
                        allDb.DB_connection();
                        if (t1.getSelectionModel().isEmpty()) {
                            allDb.Add_couch_attend(id.get(Integer.parseInt(comp[1])).getG_id(), all_re_coach.get(t1.getSelectionModel().getSelectedIndex()).getC_id());
                        } else {

                            int update = allDb.update_attend_coach(id.get(Integer.parseInt(comp[1])).getG_id(), all_re_coach.get(t1.getSelectionModel().getSelectedIndex()).getC_id());
                            if (update == 0) {
                                allDb.Add_couch_attend(id.get(Integer.parseInt(comp[1])).getG_id(), all_re_coach.get(t1.getSelectionModel().getSelectedIndex()).getC_id());
                            }
                        }
                        allDb.DB_connection();

                    } catch (SQLException ex) {
                    }

                });
                allDb.DB_connection();
            } catch (SQLException ex) {
            }

            if (allDb.get_att_coach_bool(id.get(z).getG_id())) {
                List<attend_couch> s = new ArrayList<attend_couch>();
                s = allDb.get_att_coach(id.get(z).getG_id());
                for (int i = 0; i < s.size(); i++) {
                    ch_coach.setSelected(true);
                    l_re_id.indexOf(s.get(0).getRep_name());
                    t1.setValue(l_name.get(l_re_id.indexOf(s.get(0).getRep_name())));
                    t1.setDisable(false);
                }
            }
            ch_coach.setOnAction((event) -> {

                if (ch_coach.isSelected()) {

                    t1.setDisable(false);
                } else {
                    t1.setDisable(true);
                    try {
                        allDb.DB_connection();
                        allDb.delet_attend_coach(id.get(Integer.parseInt(comp[1])).getG_id());
                        allDb.DB_close();
                    } catch (SQLException ex) {
                    }
                }

            });

            Label la = new Label(id.get(z).getName() + " \n " + id.get(z).getTrack() + "");
            la.setPrefWidth(bounds.getWidth() * 0.07);
            la.setMaxHeight(1000);
            la.setStyle("-fx-font-size: 9px; -fx-background-color: #ffb3d9;");
            la.setAlignment(Pos.CENTER);
            la.setRotate(270.0);
            coach_att.getChildren().addAll(t1, ch_coach, la);

            HBox all_att = new HBox();
            for (int j = 8; j > 0; j--) {
                VBox att = new VBox();
                att.setStyle("-fx-background-color:   #ffffff;");
                for (int i = 0; i < all_S.get(z).size(); i++) {
                    JFXCheckBox ch = new JFXCheckBox();
                    ch.setCheckedColor(rgb(42, 115, 255));
                    ch.setTextFill(rgb(255, 255, 255));
                    ch.setStyle("-fx-font-size: 16px;-fx-background-color: #e6f2ff;-fx-border-color:#000;");
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
                    if (bool == 0) {
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
                    }
                    if (bool == 1) {
                        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                                .filter(date -> date.getDayOfWeek() == DayOfWeek.FRIDAY
                                || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                                .collect(Collectors.toList());
                        if (ldate.size() == 13) {
                            ldate.remove(12);
                        }
                        if (!ldate.get(j - 1).isEqual(currentdate)) {
                            ch.setDisable(true);
                        }

                    } else if (bool == 2) {
                        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                                .filter(date -> date.getDayOfWeek() == DayOfWeek.FRIDAY
                                || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                                .collect(Collectors.toList());
                        if (ldate.size() == 9) {
                            ldate.remove(8);
                        }
                        if (!ldate.get(j - 1).isEqual(currentdate)) {
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
                for (int i = 0; i < 8 - all_S.get(z).size(); i++) {
                    Label l = make_lable("", 0.023);
                    att.getChildren().add(l);
                }
                all_att.getChildren().add(att);
            }

            VBox all_level = new VBox();
            all_level.setStyle("-fx-background-color:  #ffe6e6;");
            for (int i = 0; i < 8; i++) {
                Label l = make_lable(id.get(z).getG_level(), .03);
                all_level.getChildren().add(l);
                l.setStyle("-fx-background-color: #ffe6e6;-fx-font-size: 18px;");
            }

            VBox all_nots = new VBox();
            all_nots.setStyle("-fx-background-color:   #ffffb3;");
            for (int i = 0; i < all_S.get(z).size(); i++) {

                TextField t = new TextField();
                t.setPrefWidth(bounds.getWidth() * .19);
                t.setStyle("-fx-font-size: 12px;-fx-background-color:	 #ffffb3;-fx-border-color:#000;");
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
                t.setStyle("-fx-font-size: 12px;-fx-background-color:	#ffffb3;-fx-border-color:#000;");
                t.setAlignment(Pos.CENTER);
                all_nots.getChildren().add(t);

            }

            row.getChildren().add(all_nots);
            row.getChildren().add(all_level);
            row.getChildren().add(all_att);
            row.getChildren().add(coach_att);
            row.getChildren().add(vstart);
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
        l.setStyle("-fx-font-size: 16px;-fx-background-color:#e6f2ff;-fx-border-color:#fff;");
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
        table.setPrefSize(bounds.getWidth() * 0.800, bounds.getHeight() * 0.90);
        table.setLayoutX(0);
        table.setLayoutY(0);
        table.setStyle("-fx-background-color:   #fff;");
        scrooll.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.90);
        pane_table.setPrefSize(bounds.getWidth() * 0.800, bounds.getHeight() * 0.90);
        id = new ArrayList<all_information_for_group>();
        all_g_id = new ArrayList<Integer>();
        t = new ArrayList<List<swimmer>>();
        combobox_all_group_day.getItems().addAll("Saturday", "Sunday", "friday");
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

            id = allDb.get_all_group_with_time(combobox_all_group.getValue(), bool);
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
                        bool = 0;
                    } else if (combobox_all_group_day.getValue().equals("Sunday")) {
                        bool = 1;
                    } else if (combobox_all_group_day.getValue().equals("friday")) {
                        bool = 2;
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
                    if (bool == 2) {
                        BuildHomefriday(id, t);
                    } else {
                        BuildHome(id, t);
                    }
                    allDb.DB_close();

                } catch (SQLException ex) {
                    System.out.println("initialize" + ex);
                }

            });

        } catch (SQLException ex) {
            System.out.println("initialize" + ex);
        }

//        try {
//            //////////////Show//////////
//            allDb.DB_connection();
//            combobox_all_group.getItems().addAll(allDb.All_time_of_group_without_repeat());
//          
//           id = allDb.get_all_group_with_time(combobox_all_group.getValue(), bool);
//            all_g_id.clear();
//            for (int i = 0; i < id.size(); i++) {
//                all_g_id.add(id.get(i).getG_id());
//            }
//            t = allDb.get_swimmerWithgroup(all_g_id);
//            //System.out.println(t.get(0).get(0));
//            pane_table.getChildren().clear();
//            table.getChildren().clear();
//            pane_table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
//            BuildHome(id, t);
//
//            allDb.DB_close();
//
//            combobox_all_group.setOnAction((event) -> {
//                try {
//
//                    if (combobox_all_group_day.getValue().equals("Saturday")) {
//                        bool = 0;
//                    } else if (combobox_all_group_day.getValue().equals("sunday")){
//                        bool = 1;
//                    } else if (combobox_all_group_day.getValue().equals("fridey")){
//                        bool = 2;
//                    }
//                    allDb.DB_connection();
//                    id = allDb.get_all_group_with_time(combobox_all_group.getValue(), bool);
//                    all_g_id.clear();
//                    for (int i = 0; i < id.size(); i++) {
//                        all_g_id.add(id.get(i).getG_id());
//                    }
//                    System.out.println(all_g_id);
//                    t = allDb.get_swimmerWithgroup(all_g_id);
//                    //System.out.println(t.get(0).get(0));
//                    pane_table.getChildren().clear();
//                    table.getChildren().clear();
//                    pane_table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
//                    BuildHome(id, t);
//                    allDb.DB_close();
//
//                } catch (SQLException ex) {
//                    System.out.println("initialize" + ex);
//                }
//
//            });
//
//        } catch (SQLException ex) {
//            System.out.println("initialize" + ex);
//        }
    }
//////////////////////////home/////////////

    /////////////////////add////////////
    private void initialize_add_group() {

        add_group_coach.getItems().clear();
        add_group_day.getItems().clear();
        add_group_line.getItems().clear();
        add_group_level.getItems().clear();

        update_coach.getItems().clear();
        update_group_day.getItems().clear();
        update_group_line.getItems().clear();
        update_group_level.getItems().clear();

        try {
            coach = new ArrayList<coach>();
            allDb.DB_connection();
            coach = allDb.allcoach();
            allDb.DB_close();
            coach.forEach((co) -> {
                add_group_coach.getItems().add(co.getName());
                update_coach.getItems().add(co.getName());
            });
            add_group_day.getItems().addAll("Saturday", "Sunday", "friday");
            update_group_day.getItems().addAll("Saturday", "Sunday", "friday");

            add_group_line.getItems().addAll("L0", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11",
                    "L12", "L13", "L14", "L15", "L16", "L17", "L18", "L19", "L20");
            update_group_line.getItems().addAll("L0", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11",
                    "L12", "L13", "L14", "L15", "L16", "L17", "L18", "L19", "L20");

            add_group_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8");
            update_group_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8");

            add_group_time.setValue(LocalTime.MIN);
            update_group_time.setValue(LocalTime.MIN);

        } catch (SQLException ex) {
        }

    }

    private void initialize_add_swimmer() {
        day_swimmer.getItems().clear();
        add_s_gender.getItems().clear();
        time_swimmer.getItems().clear();
        coach_swimmer.getItems().clear();

        day_swimmer.getItems().addAll("Saturday", "Sunday", "friday");
        add_s_gender.getItems().addAll("Male", "Female");
        add_s_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8");
        add_s_range.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        try {
            allDb.DB_connection();
            time_swimmer.getItems().addAll(allDb.All_time_of_group_without_repeat());
            coach_swimmer.getItems().addAll(allDb.search_group_by_name());
            allDb.DB_close();
        } catch (SQLException ex) {
        }
    }

    /////////////////////add////////////
    ////////////////////////search//////////
    private void initialize_search_group() {

        search_g_day.getItems().clear();
        search_g_line.getItems().clear();
        search_g_level.getItems().clear();
        search_g_time.getItems().clear();
        search_g_name.clear();
        search_g_id.clear();

        search_group_list = new ArrayList<all_information_for_group>();
        search_g_day.getItems().addAll("Saturday", "Sunday", "friday");
        search_g_line.getItems().addAll("L0", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11",
                "L12", "L13", "L14", "L15", "L16", "L17", "L18", "L19", "L20");
        search_g_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8");

        try {
            allDb.DB_connection();
            search_g_time.getItems().addAll(allDb.All_time_of_group_without_repeat());
            TextFields.bindAutoCompletion(search_g_name, allDb.search_group_by_name());
            allDb.DB_close();
        } catch (SQLException ex) {
        }
        r_g_id.setOnAction((event) -> {
            if (r_g_id.isSelected()) {
                search_g_id.setDisable(false);
                search_g_name.setText("");
                search_g_name.setDisable(true);
                r_g_name.setSelected(false);
                search_g_level.setValue(null);
                search_g_level.setDisable(true);
                r_g_level.setSelected(false);
                search_g_time.setValue(null);
                search_g_time.setDisable(true);
                r_g_time.setSelected(false);
                search_g_day.setValue(null);
                search_g_day.setDisable(true);
                r_g_day.setSelected(false);
                search_g_line.setValue(null);
                search_g_line.setDisable(true);
                r_g_line.setSelected(false);

            } else {
                search_g_id.setText("");
                search_g_id.setDisable(true);
            }

        });
        r_g_name.setOnAction((event) -> {
            if (r_g_name.isSelected()) {
                search_g_name.setDisable(false);
                search_g_id.setText("");
                search_g_id.setDisable(true);
                r_g_id.setSelected(false);

            } else {
                search_g_name.setText("");
                search_g_name.setDisable(true);

            }

        });
        r_g_time.setOnAction((event) -> {
            if (r_g_time.isSelected()) {
                search_g_time.setDisable(false);
                search_g_id.setText("");
                search_g_id.setDisable(true);
                r_g_id.setSelected(false);

            } else {
                search_g_time.setValue(null);
                search_g_time.setDisable(true);
            }
        });
        r_g_day.setOnAction((event) -> {
            if (r_g_day.isSelected()) {
                search_g_day.setDisable(false);
                search_g_id.setText("");
                search_g_id.setDisable(true);
                r_g_id.setSelected(false);

            } else {
                search_g_day.setValue(null);
                search_g_day.setDisable(true);
            }
        });
        r_g_line.setOnAction((event) -> {
            if (r_g_line.isSelected()) {
                search_g_line.setDisable(false);
                search_g_id.setText("");
                search_g_id.setDisable(true);
                r_g_id.setSelected(false);
            } else {
                search_g_line.setValue(null);
                search_g_line.setDisable(true);
            }
        });
        r_g_level.setOnAction((event) -> {
            if (r_g_level.isSelected()) {
                search_g_level.setDisable(false);
                search_g_id.setText("");
                search_g_id.setDisable(true);
                r_g_id.setSelected(false);
            } else {
                search_g_level.setValue(null);
                search_g_level.setDisable(true);
            }
        });

        scroll_search.setPrefSize(bounds.getWidth() * 0.60, bounds.getHeight() * 0.51);
        pane_search_table.setPrefSize(bounds.getWidth() * 0.59, bounds.getHeight() * 0.55);
        select_pane_search.setPrefWidth(bounds.getWidth() * 0.70);
        hbox_select_search.setPrefWidth(bounds.getWidth() * 0.68);
        button_bar.setPrefWidth(bounds.getWidth() * 0.45);
        vbox_search_group.setPrefWidth(bounds.getWidth() * 0.17);
        vbox_search_att_s.setPrefWidth(bounds.getWidth() * 0.17);
        vbox_search_swimmer.setPrefWidth(bounds.getWidth() * 0.17);
        vbox_search_coach.setPrefWidth(bounds.getWidth() * 0.17);

        try {
            allDb.DB_connection();
            search_group_list = allDb.search_group_all();
            allDb.DB_close();
            BuildSearch(search_group_list);
        } catch (SQLException ex) {
        }

    }

    private void initialize_search_swimmer() {

        search_s_day.getItems().clear();
        search_s_gender.getItems().clear();
        search_s_time.getItems().clear();
        search_s_name.clear();
        search_s_id.clear();

        search_swimmer_list = new ArrayList<all_information_for_swimmer>();
        search_s_day.getItems().addAll("Saturday", "Sunday", "friday");
        search_s_gender.getItems().addAll("male", "female");
        try {
            allDb.DB_connection();
            search_swimmer_list = allDb.search_swimmer_all();
            search_s_time.getItems().addAll(allDb.All_time_of_group_without_repeat());
            TextFields.bindAutoCompletion(search_s_name, allDb.get_all_name_swimmer());
            allDb.DB_close();
        } catch (SQLException ex) {
        }

        r_s_id.setOnAction((event) -> {
            if (r_s_id.isSelected()) {
                search_s_name.setDisable(true);
                r_s_name.setSelected(false);
                search_s_name.setText("");
                search_s_id.setDisable(false);
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
                search_s_id.setText("");
                search_s_id.setDisable(true);
            }
        });
        r_s_name.setOnAction((event) -> {
            if (r_s_name.isSelected()) {
                search_s_name.setDisable(false);
                r_s_id.setSelected(false);
                search_s_id.setText("");
                search_s_id.setDisable(true);
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
                r_s_id.setSelected(false);
                search_s_id.setText("");
                search_s_id.setDisable(true);
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
                r_s_id.setSelected(false);
                search_s_id.setText("");
                search_s_id.setDisable(true);
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
                r_s_id.setSelected(false);
                search_s_id.setText("");
                search_s_id.setDisable(true);
            } else {
                search_s_gender.setValue(null);
                search_s_gender.setDisable(true);
            }
        });
    }

    private void initialize_search_att() {

        search_att_num.getItems().clear();
        search_att_time.getItems().clear();
        search_att_name.clear();

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

        r_att_s_id.setOnAction((event) -> {
            if (r_att_s_id.isSelected()) {
                search_att_name.setDisable(true);
                r_att_s_name.setSelected(false);
                search_att_id.setDisable(false);
                search_att_name.setText("");
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
                search_att_id.setText("");
                search_att_id.setDisable(true);
            }
        });
        r_att_s_name.setOnAction((event) -> {
            if (r_att_s_name.isSelected()) {
                search_att_name.setDisable(false);
                r_att_s_id.setSelected(false);
                search_att_id.setDisable(true);
                search_att_id.setText("");
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
                r_att_s_id.setSelected(false);
                search_att_id.setDisable(true);
                search_att_id.setText("");
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
                r_att_s_id.setSelected(false);
                search_att_id.setDisable(true);
                search_att_id.setText("");
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
                r_att_s_id.setSelected(false);
                search_att_id.setDisable(true);
                search_att_id.setText("");
            } else {
                search_att_num.setValue(null);
                search_att_num.setDisable(true);
            }
        });
    }

    private void initialize_search_coach_att() {

        search_att_c_name.clear();
        search_att_c_time.getItems().clear();
        search_att_c_id.clear();

        try {
            allDb.DB_connection();
            search_att_c_time.getItems().addAll(allDb.All_time_of_group_without_repeat());
            TextFields.bindAutoCompletion(search_att_c_name, allDb.search_group_by_name());
            allDb.DB_close();
        } catch (SQLException ex) {
        }

        r_att_c_id.setOnAction((event) -> {
            if (r_att_c_id.isSelected()) {
                search_att_c_name.setDisable(true);
                r_att_c_name.setSelected(false);
                search_att_c_id.setDisable(false);
                search_att_c_name.setText("");
                r_att_c_day.setSelected(false);
                r_att_c_time.setSelected(false);
                search_att_c_time.setValue(null);
                search_att_c_time.setDisable(true);
                search_att_c_day.setValue(null);
                search_att_c_day.setDisable(true);
            } else {
                search_att_c_id.setText("");
                search_att_c_id.setDisable(true);
            }
        });
        r_att_c_name.setOnAction((event) -> {
            if (r_att_c_name.isSelected()) {
                search_att_c_name.setDisable(false);
                r_att_c_id.setSelected(false);
                search_att_c_id.setDisable(true);
                search_att_c_id.setText("");
                r_att_c_day.setSelected(false);
                r_att_c_time.setSelected(false);
                search_att_c_time.setValue(null);
                search_att_c_time.setDisable(true);
                search_att_c_day.setValue(null);
                search_att_c_day.setDisable(true);
            } else {
                search_att_c_name.setText("");
                search_att_c_name.setDisable(true);
            }
        });
        r_att_c_time.setOnAction((event) -> {
            if (r_att_c_time.isSelected()) {
                search_att_c_time.setDisable(false);
                r_att_c_name.setSelected(false);
                search_att_c_name.setText("");
                search_att_c_name.setDisable(true);
                r_att_c_id.setSelected(false);
                search_att_c_id.setDisable(true);
                search_att_c_id.setText("");
            } else {
                search_att_c_time.setValue(null);
                search_att_c_time.setDisable(true);
            }
        });
        r_att_c_day.setOnAction((event) -> {
            if (r_att_c_day.isSelected()) {
                search_att_c_day.setDisable(false);
                r_att_c_name.setSelected(false);
                search_att_c_name.setText("");
                search_att_c_name.setDisable(true);
                r_att_c_id.setSelected(false);
                search_att_c_id.setDisable(true);
                search_att_c_id.setText("");
            } else {
                search_att_c_day.setValue(null);
                search_att_c_day.setDisable(true);
            }
        });
    }

    VBox table_search = new VBox();

    private void BuildSearch(List<all_information_for_group> id) throws SQLException {
        table_search.getChildren().clear();
        pane_search_table.setPrefSize(bounds.getWidth() * 0.59, bounds.getHeight() * 0.55);
        Label num = make_lable_search_head("num", .039, "b6bec2", 20);
        Label name = make_lable_search_head("Name", 0.1, "b6bec2", 20);
        Label c_id = make_lable_search_head("c_id", 0.08, "b6bec2", 20);
        Label level = make_lable_search_head("level", .08, "b6bec2", 20);
        Label track = make_lable_search_head("Line", .08, "b6bec2", 20);
        Label time = make_lable_search_head("Time", .08, "b6bec2", 20);
        Label day = make_lable_search_head("Day", .08, "b6bec2", 20);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .54, 0);
        title.getChildren().addAll(day, time, track, level, name, c_id, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_g((i + 1) + "", .039);
            Label name1 = make_lable_g(id.get(i).getName(), 0.1);
            Label c_id1 = make_lable_g(id.get(i).getC_id() + "", 0.08);
            Label level1 = make_lable_g(id.get(i).getG_level(), .08);
            Label track1 = make_lable_g(id.get(i).getTrack(), .08);
            Label time1 = make_lable_g(id.get(i).getTime() + "", .08);
            Label d1 = make_lable_g("Saturday", .08);
            if ((id.get(i).getDay() == 0)) {
                d1 = make_lable_g("Saturday", .08);
            } else if ((id.get(i).getDay() == 1)) {
                d1 = make_lable_g("Sunday", .08);
            } else if ((id.get(i).getDay() == 2)) {
                d1 = make_lable_g("friday", .08);
            }
            Label day1 = d1;
            Image image = new Image(getClass().getResourceAsStream("/images/delet.png"));
            ImageView Iview = new ImageView(image);
            Iview.setFitHeight(25);
            Iview.setFitWidth(25);
            Iview.setPickOnBounds(true);
            Iview.setPreserveRatio(true);
            Iview.setId(id.get(i).getG_id() + "");
            Iview.setOnMousePressed((event) -> {
                try {
                    int reply = JOptionPane.showConfirmDialog(null, "هل حقك تريد مسح المجموعة", "تأكيد", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        allDb.DB_connection();
                        allDb.delet_group(Integer.parseInt(Iview.getId()));
                        search_group_list = allDb.search_group_all();
                        BuildSearch(search_group_list);
                        allDb.DB_close();
                        JOptionPane.showMessageDialog(null, "تم مسح المجموعة");
                    } else {
                    }

                } catch (SQLException ex) {
                }
            });
            Image image1 = new Image(getClass().getResourceAsStream("/images/edit.png"));
            ImageView Iview1 = new ImageView(image1);
            Iview1.setFitHeight(25);
            Iview1.setFitWidth(25);
            Iview1.setId(id.get(i).getG_id() + "|" + i);
            Iview1.setPickOnBounds(true);
            Iview1.setPreserveRatio(true);
            Iview1.setOnMousePressed((event) -> {
                String[] comp = Iview1.getId().split("\\|");
                id_update = Integer.parseInt(comp[0]);
                update_coach.setValue(name1.getText());
                update_group_day.setValue(day1.getText());
                update_group_line.setValue(track1.getText());
                update_group_level.setValue(level1.getText());
                update_group_time.setValue(id.get(Integer.parseInt(comp[1])).getTime().toLocalTime());
                p_update_group.toFront();
            });
            HBox title2 = new HBox();
            title2.setStyle("-fx-background-color: #ffb3e6;-fx-border-color:#000;");
            title2.setPrefSize(bounds.getWidth() * .04, 0);
            title2.getChildren().addAll(Iview, Iview1);
            title2.setAlignment(Pos.CENTER_RIGHT);
            title2.setSpacing(15);

//            JFXButton l = new JFXButton();
//            l.setButtonType(JFXButton.ButtonType.RAISED);
//            l.setContentDisplay(ContentDisplay.TOP);
//            l.setPrefSize(5,5);
//            l.setTextFill(rgb(54, 27, 20));
//            l.setGraphic(Iview);
//            l.setFont(javafx.scene.text.Font.font("System Bold", 22));
            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3e6;");
            title1.setPrefSize(bounds.getWidth() * .54, 10);
            title1.getChildren().addAll(day1, time1, track1, level1, name1, c_id1, num1);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3e6 ");
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
                    Label day1_inf = make_lable_g("Saturday", .08);
                    if (id.get(Integer.parseInt(num1.getText()) - 1).getDay() == 0) {
                        day1_inf = make_lable_g("Saturday", .09);
                    } else if (id.get(Integer.parseInt(num1.getText()) - 1).getDay() == 1) {
                        day1_inf = make_lable_g("Sunday", .09);
                    } else if (id.get(Integer.parseInt(num1.getText()) - 1).getDay() == 2) {
                        day1_inf = make_lable_g("friday", .09);
                    }
                    coach_to_tranfer_to_add_swimmer = id.get(Integer.parseInt(num1.getText()) - 1).getName();
                    day_to_tranfer_to_add_swimmer = day1_inf.getText();
                    time_to_tranfer_to_add_swimmer = id.get(Integer.parseInt(num1.getText()) - 1).getTime();

                    HBox title1_inf = new HBox();
                    title1_inf.setStyle("-fx-background-color: #ffb3e6;");
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
                    title_s.getChildren().addAll(gender_s, name_s, id_s, num_s);
                    title_s.setAlignment(Pos.CENTER_RIGHT);

                    vbox_group_inf.getChildren().add(title_s);

                    for (int x = 0; x < t.size(); x++) {

                        Label num_s1 = make_lable_g((x + 1) + "", .04);
                        Label id_s1 = make_lable_g(t.get(x).getS_id() + "", .139);
                        Label name_s1 = make_lable_g(t.get(x).getName(), 0.18);
                        Label gender_s1 = make_lable_g(t.get(x).getGender(), .18);

                        HBox title_s1 = new HBox();
                        title_s1.setStyle("-fx-background-color:  #ffb3e6;");
                        title_s1.setPrefSize(bounds.getWidth() * .54, 0);
                        title_s1.getChildren().addAll(gender_s1, name_s1, id_s1, num_s1);
                        title_s1.setAlignment(Pos.CENTER_RIGHT);

                        vbox_group_inf.getChildren().add(title_s1);

                    }

                } catch (SQLException ex) {
                }

                p_group_inf.toFront();
            });
            HBox title_all = new HBox();
            title_all.getChildren().addAll(title2, title1);

            table_search.getChildren().add(title_all);

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

    int id_update;

    private void BuildSearch_Swimmer(List<all_information_for_swimmer> id) throws SQLException {
        table_search.getChildren().clear();
        pane_search_table.setPrefSize(bounds.getWidth() * 0.59, bounds.getHeight() * 0.55);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Label num = make_lable_search_head("s_id", .03, "b6bec2", 17);
        Label name = make_lable_search_head("Name", 0.08, "b6bec2", 17);
        Label address = make_lable_search_head("Coach", 0.0601, "b6bec2", 17);
        Label age = make_lable_search_head("DOB", 0.037, "b6bec2", 17);
        Label gender = make_lable_search_head("Gender", 0.055, "b6bec2", 17);
        Label phone = make_lable_search_head("Phone", 0.05, "b6bec2", 17);
        Label level = make_lable_search_head("level", .04, "b6bec2", 17);
        Label s_day = make_lable_search_head("Start Day", .063, "b6bec2", 17);
        Label e_day = make_lable_search_head("End Day", .055, "b6bec2", 17);
        Label time = make_lable_search_head("Time", .04, "b6bec2", 17);
        Label day = make_lable_search_head("Day", .04, "b6bec2", 17);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .55, 0);
        title.getChildren().addAll(day, time, e_day, s_day, level, phone, gender, age, address, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_search_swimmer((i + 1) + "", .04);
            Label id1 = make_lable_search_swimmer(id.get(i).getS_id() + "", .03);
            Label name1 = make_lable_search_swimmer(id.get(i).getName(), 0.08);
            Label address1 = make_lable_search_swimmer(allDb.coach_by_name(id.get(i).getC_id()), 0.0601);
            Label age1 = make_lable_search_swimmer(sdf.format(id.get(i).getAge()) + "", .037);
            Label gender1 = make_lable_search_swimmer(id.get(i).getGender(), 0.055);
            Label phone1 = make_lable_search_swimmer(id.get(i).getPhone(), .05);
            Label level1 = make_lable_search_swimmer(id.get(i).getLevel(), .04);
            Label s_day1 = make_lable_search_swimmer(id.get(i).getStart() + "", .063);
            Label e_day1 = make_lable_search_swimmer(id.get(i).getEnd() + "", .055);
            Label time1 = make_lable_search_swimmer(id.get(i).getG_time() + "", .04);
            Label da = make_lable_g("Saturday", .04);
            if (id.get(i).getDay() == 0) {
                da = make_lable_search_swimmer("Saturday", .04);
            } else if (id.get(i).getDay() == 1) {
                da = make_lable_search_swimmer("Sunday", .04);
            } else if (id.get(i).getDay() == 2) {
                da = make_lable_search_swimmer("friday", .04);
            }
            Label day1 = da;

            Image image = new Image(getClass().getResourceAsStream("/images/delet.png"));
            ImageView Iview = new ImageView(image);
            Iview.setFitHeight(20);
            Iview.setFitWidth(20);
            Iview.setPickOnBounds(true);
            Iview.setPreserveRatio(true);
            Iview.setId(id.get(i).getS_id() + "");
            Iview.setOnMousePressed((event) -> {
                try {
                    int reply = JOptionPane.showConfirmDialog(null, "هل حقك تريد مسح السباح", "تأكيد", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        allDb.DB_connection();
                        allDb.delet_swimmer(id.get(Integer.parseInt(num1.getText()) - 1).getS_id());
                        search_swimmer_list = allDb.search_swimmer_all();
                        BuildSearch_Swimmer(search_swimmer_list);
                        allDb.DB_close();
                        JOptionPane.showMessageDialog(null, "تم مسح السباح");
                    } else {
                    }

                } catch (SQLException ex) {
                }
            });
            Image image1 = new Image(getClass().getResourceAsStream("/images/edit.png"));
            ImageView Iview1 = new ImageView(image1);
            Iview1.setFitHeight(20);
            Iview1.setFitWidth(20);
            Iview1.setId(id.get(i).getG_id() + "");
            Iview1.setPickOnBounds(true);
            Iview1.setPreserveRatio(true);
            Iview1.setOnMousePressed((event) -> {

            });
            HBox title2 = new HBox();
            title2.setStyle("-fx-background-color: #ffb3e6;-fx-border-color:#000;");
            title2.setPrefSize(bounds.getWidth() * .04, 0);
            title2.getChildren().addAll(Iview, Iview1);
            title2.setAlignment(Pos.CENTER_RIGHT);
            title2.setSpacing(15);

            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3e6;");
            title1.setPrefSize(bounds.getWidth() * .55, 0);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.getChildren().addAll(day1, time1, e_day1, s_day1, level1, phone1, gender1, age1, address1, name1, id1);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3e6 ");
            });
            title1.setOnMouseClicked((MouseEvent event) -> {
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
            HBox title_all = new HBox();
            title_all.getChildren().addAll(title2, title1);

            table_search.getChildren().add(title_all);

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
        pane_search_table.setPrefSize(bounds.getWidth() * 0.59, bounds.getHeight() * 0.55);

        Label num = make_lable_search_head("s_id", .04, "b6bec2", 18);
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
        title.setPrefSize(bounds.getWidth() * .59, 0);
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
            Label day1 = make_lable_g("Saturday", .06);
            if (id.get(i).getG_day() == 0) {
                day1 = make_lable_search_swimmer("Saturday", .06);
            } else if (id.get(i).getG_day() == 1) {
                day1 = make_lable_search_swimmer("Sunday", .06);
            } else if (id.get(i).getG_day() == 2) {
                day1 = make_lable_search_swimmer("friday", .06);
            }

            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3e6;");
            title1.setPrefSize(bounds.getWidth() * .59, 0);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.getChildren().addAll(day1, time1, level1, coach1, n_day1, date1, id1, name1, num1);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3e6 ");
            });

            table_search.getChildren().add(title1);
            if (i > 15) {

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .017);
            }
        }

        pane_search_table.getChildren().clear();
        pane_search_table.getChildren().add(table_search);

    }

    private void BuildCoach_att(List<all_information_for_attend_coach> id) throws SQLException {

        table_search.getChildren().clear();
        pane_search_table.setPrefSize(bounds.getWidth() * 0.59, bounds.getHeight() * 0.55);

        Label num = make_lable_search_head("Num", .04, "b6bec2", 18);
        Label name = make_lable_search_head("Name", 0.1, "b6bec2", 18);
        Label Id = make_lable_search_head("C_Id", 0.048, "b6bec2", 18);
        Label re_id = make_lable_search_head("Re_id", .048, "b6bec2", 18);
        Label Date = make_lable_search_head("Date", 0.07, "b6bec2", 18);
        Label n_day = make_lable_search_head("N_Day", 0.05, "b6bec2", 18);
        Label coach = make_lable_search_head("re_Coach", 0.08, "b6bec2", 18);
        Label level = make_lable_search_head("level", .05, "b6bec2", 18);
        Label time = make_lable_search_head("Time", .06, "b6bec2", 18);
        Label day = make_lable_search_head("Day", .06, "b6bec2", 18);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .59, 0);
        title.getChildren().addAll(re_id, coach, day, time, level, Date, name, Id, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_search_swimmer((i + 1) + "", .04);
            Label name1 = make_lable_search_swimmer(id.get(i).getName(), 0.1);
            Label id1 = make_lable_search_swimmer(id.get(i).getC_id() + "", .048);
            Label date1 = make_lable_search_swimmer(id.get(i).getDate_absent() + "", 0.07);
            //Label n_day1 = make_lable_search_swimmer(id.get(i).getNum() + "", .05);
            Label coach1 = make_lable_search_swimmer(allDb.coach_by_name(id.get(i).getR_id()), 0.08);
            Label level1 = make_lable_search_swimmer(id.get(i).getLevel() + "", .05);
            Label time1 = make_lable_search_swimmer(id.get(i).getTime() + "", .06);
            String d = "";
            if (id.get(i).getDay() == 0) {
                d = "Saturday";
            } else if (id.get(i).getDay() == 1) {
                d = "Sunday";
            } else if (id.get(i).getDay() == 2) {
                d = "friday";
            }

            Label day1 = make_lable_search_swimmer(d, .06);
            Label re_id1 = make_lable_search_swimmer(id.get(i).getR_id() + "", 0.048);

            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3e6;");
            title1.setPrefSize(bounds.getWidth() * .59, 0);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.getChildren().addAll(re_id1, coach1, day1, time1, level1, date1, name1, id1, num1);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3e6 ");
            });

            table_search.getChildren().add(title1);
            if (i > 15) {

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .017);
            }
        }

        pane_search_table.getChildren().clear();
        pane_search_table.getChildren().add(table_search);

    }
    ////////////////////////search//////////

//////////////////////////////printer////////////
    public PageFormat getPageFormat(java.awt.print.PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double bodyHeight;
        bodyHeight = 10.0;

        double headerHeight = 10.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(10);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }
//////////////////////////////printer////////////

    private double bouns() throws SQLException {
        double bouns = 0;

        allDb.DB_connection();

        double late1 = allDb.get_cost_for_late1() * allDb.get_count_of_punish(coach_id_punish, 1);
        double late5 = allDb.get_cost_for_late5() * allDb.get_count_of_punish(coach_id_punish, 2);
        double late_glass = allDb.get_cost_for_glass() * allDb.get_count_of_punish(coach_id_punish, 3);
        double late_beh = allDb.get_cost_for_Behavior() * allDb.get_count_of_punish(coach_id_punish, 4);
        double late_talk = allDb.get_cost_for_talk() * allDb.get_count_of_punish(coach_id_punish, 5);
        double late_absent = allDb.get_cost_for_absent() * allDb.get_count_of_punish_of_attend(coach_id_punish);

        allDb.DB_close();
        bouns = late1 + late5 + late_glass + late_beh + late_talk + late_absent;
        return bouns;
    }

    private double minus() throws SQLException {
        double minus = 0;

        allDb.DB_connection();

        minus = allDb.get_cost_for_re() * allDb.get_count_of_punish_of_attend_re(coach_id_punish);

        allDb.DB_close();
        return minus;
    }
}
