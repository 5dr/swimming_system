/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.*;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.io.IOException;
import javafx.geometry.Rectangle2D;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Translate;
import javax.swing.JOptionPane;
import model.all_information_for_attend_coach;
import model.all_information_for_attend_swimmer;
import model.all_information_for_swimmer;
import model.attend_couch;
import model.attend_swimmer;
import model.coach;
import model.group;
import model.s;
import model.swimmer_and_group;
//import model.swimmer_and_group;
import model.trfihee;
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
    private Button b_report, b_salary, b_print_home, b_home, b_add_group, b_add_swimmer, b_add_coach, b_search, b_Settings;
    @FXML
    private AnchorPane pane_table_salary, pane_report, anchorpane, pane_table, pane_search_table;
    @FXML
    private VBox all_report, vbox_total_trfihee, vbox_total_add_s, v_box1_type, vbox_report_trfihee, vbox_report_att_c, p_list, vbox_search_group, vbox_search_coach, vbox_report_att_s, vbox_search_swimmer, v_s_attend, vbox_group_inf, vbox_report, vbox_search_att_s, vbox_search_att_c;
    @FXML
    private StackPane big_Stack;
    @FXML
    private Pane loading, report, p_settings, p_salary, p_c_punish, p_update_group, p_home, p_add_group, p_s_add, p_C_add, p_search, p_Settings, select_pane_search, information_swimmer, p_group_inf;
    @FXML
    private JFXComboBox<Time> search_att_c_time, combobox_all_group, search_g_time, search_s_time, search_att_time, time_swimmer;
    @FXML
    private JFXComboBox<String> combobox_all_group_day;
    @FXML
    private ScrollPane scrooll_salary, scrooll, scroll_search;

    @FXML
    private JFXComboBox<String> update_group_type, add_s_type, add_group_coach, add_group_day, add_group_level, add_group_line, add_group_type, update_group_day, update_coach, update_group_line, update_group_level, add_s_level;
    @FXML
    private DatePicker add_s_age, select_date_report;
    @FXML
    private JFXTimePicker add_group_time, update_group_time;
    @FXML
    private JFXButton inf_c_b_level, inf_c_s_level, inf_c_s_phone, inf_c_s_DOB, inf_c_s_end, inf_c_b_phone, setting_b_coach_cost, setting_b_tr, setting_b_re, setting_b_absent, setting_b_talk, setting_b_behavior, setting_b_glass, setting_b_5, setting_b, b_punish_late_1, b_punish_late_5, b_punish_glass, b_punish_behavior, b_punish_talk, search_group, search_att_s, search_swimmer, search_coach, search_att_c;

    @FXML
    private JFXRadioButton r_att_c_day, r_att_c_time, r_att_c_name, r_att_c_id, r_att_s_id, r_s_id, r_g_id, r_g_name, r_g_time, r_g_day, r_g_line, r_g_level, r_s_gender, r_s_day, r_s_time, r_s_name, r_att_s_name, r_att_time, r_att_day, r_att_num;

    @FXML
    private TextField text_bouns, setting_coach_cost, setting_trfihe, setting_re, setting_absent, setting_talk, setting_behaveor, setting_glass, setting_t25er15, setting_t25er1, t_mount, inf_c_phone, inf_c_id, inf_c_name, search_att_c_name, search_att_c_id, search_att_id, search_s_id, search_g_id, add_C_name, add_C_adress, add_C_phone, search_g_name, punish_field, search_s_name, inf_s_name, inf_s_coach, inf_s_time, inf_s_day, inf_s_address, inf_s_phone, inf_s_age, inf_s_gender, inf_s_group, inf_s_start_day, inf_s_end_day, search_att_name;

    @FXML
    private JFXComboBox<String> inf_c_level, inf_s_level, add_C_level, search_g_day, search_g_line, search_g_level, search_s_day, search_s_gender, add_s_gender, day_swimmer, coach_swimmer;

    @FXML
    private JFXComboBox<Integer> add_s_range, search_att_num;

    @FXML
    private HBox setting_hb, hbox_select_search;

    @FXML
    private JFXTextField search_add_s_id, setting_add_type, setting_add_cost, date_print_home, text_s_note, add_s_name, add_s_phone;

    @FXML
    private JFXDatePicker search_att_day, search_att_c_day;
    @FXML
    private ButtonBar button_bar;
    @FXML
    private Label total_add_s, l_bouns, setting_la, l_punish_minus, l_punish_bouns, l_absent, l_punish_late_1, l_punish_late_5, l_punish_glass, l_punish_behavior, l_punish_re, l_punish_talk;

    public void sign_out(ActionEvent actionEvent) throws SQLException {
        try {
            Runtime.getRuntime().exec("taskkill /IM mysqld.exe /F");
        } catch (IOException ex) {
        }
        System.exit(0);
    }

    public void delet_coach(ActionEvent actionEvent) throws SQLException {

        int reply = JOptionPane.showConfirmDialog(null, "هل حقك تريد مسح المجموعة", "تأكيد", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            allDb.DB_connection();
            allDb.deletcoach(coach_id_punish);
            allDb.DB_close();
            p_c_punish.toBack();
            JOptionPane.showMessageDialog(null, "تم مسح الكابتن");
        } else {
        }

    }

    public void add_bouns_for_coach(ActionEvent actionEvent) {
        try {
            if (text_bouns.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "لم يتم كتابه قيمه البونص");
            } else {
                int s = Integer.parseInt(text_bouns.getText());
                double x = Double.parseDouble(l_punish_minus.getText());
                allDb.DB_connection();
                if (allDb.is_bouns_exist(coach_id_punish)) {
                    int tot = allDb.get_bouns_id(coach_id_punish) + s;
                    allDb.update_bouns(coach_id_punish, tot);
                    text_bouns.setText("");
                    l_bouns.setText(tot + "");
                    l_punish_minus.setText((s + x) + "");
                } else {
                    allDb.Add_bouns_swimmer(coach_id_punish, Integer.parseInt(text_bouns.getText()));
                    text_bouns.setText("");
                    l_bouns.setText(s + "");
                    l_punish_minus.setText((s + x) + "");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "يجب كتابه رقم و ليس حرف");
        } catch (SQLException e) {
        }

    }

    public void Resubscribe(ActionEvent actionEvent) {
        if (search_add_s_id.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "لم يتم كتابه رقم السباح");
        } else {
            try {
                String day = "";
                allDb.DB_connection();
                search_swimmer_list = allDb.search_swimmer_id(Integer.parseInt(search_add_s_id.getText()));
                if (search_swimmer_list.size() != 0) {
                    add_s_name.setText(search_swimmer_list.get(0).getName());
                    add_s_type.setValue(search_swimmer_list.get(0).getType());
                    add_s_phone.setText(search_swimmer_list.get(0).getPhone());
                    add_s_age.setValue(search_swimmer_list.get(0).getAge().toLocalDate());
                    add_s_gender.setValue(search_swimmer_list.get(0).getGender());
                    add_s_level.setValue(search_swimmer_list.get(0).getS_level());
                    time_swimmer.setValue(search_swimmer_list.get(0).getG_time());
                    coach_swimmer.setValue(allDb.get_coach_name_by_g_id(search_swimmer_list.get(0).getG_id()));
                    if (search_swimmer_list.get(0).getDay() == 0) {
                        day = "Saturday";
                    } else if (search_swimmer_list.get(0).getDay() == 1) {
                        day = "Sunday";
                    } else {
                        day = "friday";
                    }
                    day_swimmer.setValue(day);
                } else {
                    JOptionPane.showMessageDialog(null, "لا يوجد سباح بهذا الرقم");
                }
                allDb.DB_close();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "يجب كتابه رقم و ليس حرف");
            } catch (SQLException e) {
            }

        }

    }

    public void update_coach(ActionEvent actionEvent) {

        try {
            //add_s_phone.getText().matches("\\d{11}")
            allDb.DB_connection();
            allDb.update_coach(coach_id_punish, inf_c_phone.getText(), inf_c_level.getValue());
            allDb.DB_close();
        } catch (SQLException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "يجب ادخال القيم ارقام صحيحة");
        }

    }

    public void update_swimmer_level(ActionEvent actionEvent) {

        try {
            //add_s_phone.getText().matches("\\d{11}")
            allDb.DB_connection();
            allDb.update_swimmer_level(update_id_s, inf_s_level.getValue());
            allDb.DB_close();

        } catch (SQLException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "يجب ادخال القيم ارقام صحيحة");
        }

    }

    public void update_swimmer_phone(ActionEvent actionEvent) {

        try {
            //add_s_phone.getText().matches("\\d{11}")
            allDb.DB_connection();
            allDb.update_swimmer_phone(inf_s_name.getText(), inf_s_phone.getText());
            allDb.DB_close();

        } catch (SQLException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "يجب ادخال القيم ارقام صحيحة");
        }

    }

    public void update_swimmer_DOB(ActionEvent actionEvent) {

        try {
            //add_s_phone.getText().matches("\\d{11}")
            allDb.DB_connection();
            // allDb.update_swimmer_(inf_s_name.getText(), inf_s_age.getText());
            allDb.DB_close();

        } catch (SQLException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "يجب ادخال القيم ارقام صحيحة");
        }

    }

    public void add_type(ActionEvent actionEvent) {

        try {
            //add_s_phone.getText().matches("\\d{11}")
            allDb.DB_connection();
            allDb.insert_type(Integer.parseInt(setting_add_cost.getText()), setting_add_type.getText());
            initialize_Settings_insert(te.size(), setting_add_type.getText());
            allDb.DB_close();

        } catch (SQLException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "يجب ادخال القيم ف السعر ارقام صحيحة");
        }

    }

    public void update_setting(ActionEvent actionEvent) {

        try {
            //add_s_phone.getText().matches("\\d{11}")
            allDb.DB_connection();
            for (int i = 0; i < id_setting.size(); i++) {
                allDb.update_type_cost(Integer.parseInt(te.get(i).getText()), id_setting.get(i), te1.get(i).getText());
            }
            allDb.update_cost(Integer.parseInt(setting_trfihe.getText()), Integer.parseInt(setting_coach_cost.getText()), Integer.parseInt(setting_absent.getText()), Integer.parseInt(setting_t25er1.getText()), Integer.parseInt(setting_t25er15.getText()), Integer.parseInt(setting_glass.getText()), Integer.parseInt(setting_behaveor.getText()), Integer.parseInt(setting_talk.getText()), Integer.parseInt(setting_re.getText()));
            allDb.DB_close();
        } catch (SQLException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "يجب ادخال القيم ارقام صحيحة");
        }

    }

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
                    allDb.get_cost_for_re(), allDb.get_bouns_id(Integer.parseInt(inf_c_id.getText())), Integer.parseInt(inf_c_id.getText()),
                    get_all_salary(Integer.parseInt(inf_c_id.getText()))), getPageFormat(pj));
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
        } else if (add_group_type.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
        } else {
            Time time = Time.valueOf(add_group_time.getValue());
            boolean bool = false;

            allDb.DB_connection();
            if (allDb.is_group_exist_without_type(coach.get(add_group_coach.getSelectionModel().getSelectedIndex()).getC_id(), add_group_day.getValue(), time)) {
                JOptionPane.showMessageDialog(null, "الكابتن  " + add_group_coach.getValue() + " " + " عنده مجموعة ف نفس المعاد");
            } else {
                allDb.Add_group(coach.get(add_group_coach.getSelectionModel().getSelectedIndex()).getC_id(), add_group_line.getValue(),
                        add_group_level.getValue(), add_group_day.getValue(), time, add_group_type.getValue());
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
        } else if (update_group_type.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
            if (allDb.is_group_exist(coach.get(update_coach.getSelectionModel().getSelectedIndex()).getC_id(), update_group_day.getValue(), time, update_group_type.getValue())) {
                JOptionPane.showMessageDialog(null, "الكابتن  " + update_coach.getValue() + " " + " عنده مجموعة ف نفس المعاد");
            } else {
//                allDb.Add_group(coach.get(update_coach.getSelectionModel().getSelectedIndex()).getC_id(), update_group_line.getValue(),
//                        update_group_level.getValue(), update_group_day.getValue(), time);
                allDb.update_group(id_update, coach.get(update_coach.getSelectionModel().getSelectedIndex()).getC_id(), update_group_line.getValue(),
                        update_group_level.getValue(), update_group_day.getValue(), time, update_group_type.getValue());

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
        //   
        if (add_s_type.getSelectionModel().isSelected(0)) {
            if (add_s_name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم ادخال الاسم");
            } else {
                allDb.DB_connection();

                int curr_swimmer_id = allDb.addswimmer_for_trf(add_s_name.getText());

                java.awt.print.PrinterJob pj = java.awt.print.PrinterJob.getPrinterJob();
                pj.setPrintable(new BillPrintable(add_s_name.getText(), add_s_phone.getText(), null, "====", "====", "====", "", "====", "====", curr_swimmer_id, allDb.get_cost_for_one_day(), 0, null, null), getPageFormat(pj));

                pj.print();
                pj.cancel();

                JOptionPane.showMessageDialog(null, "تم اضافه السباح");

                initialize_search_swimmer();

                add_s_name.setText("");
                add_s_phone.setText("");

                allDb.DB_close();

            }
        } else {
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
            } else if (add_s_type.getSelectionModel().isEmpty()) {
                JOptionPane.showMessageDialog(null, "لم يتم اختيار  النوع");
            } else if (add_s_age.getValue() == null) {
                JOptionPane.showMessageDialog(null, "لم يتم ادخال تاريخ الميلاد");
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

                int g_id = allDb.get_id_check_group_exist(coach_swimmer.getValue(), add_s_type.getValue(), time_swimmer.getValue(), b);

                if (allDb.check_group_exist(coach_swimmer.getValue(), add_s_type.getValue(), time_swimmer.getValue(), b)) {
                    boolean boo = false;
                    int count = allDb.get_swimmer_by_group(g_id).size();
                    String n_coach = allDb.get_coach_name_by_g_id(g_id);
                    Date sqlDate = java.sql.Date.valueOf(add_s_age.getValue());
                    // Date date1=   (Date) new SimpleDateFormat("dd/MM/yyyy").parse(add_s_age.getText());  
                    if (add_s_type.getSelectionModel().isSelected(2)) {
                        count = 0;
                    }
                    try {

                        boo = allDb.is_swimmer_exist(search_add_s_id.getText().equals("") ? 0 : Integer.parseInt(search_add_s_id.getText()));
                        count = boo ? count - 1 : count;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "يجب كتابه رقم و ليس حرف");
                    }

                    if (count < 8) {
                        int curr_swimmer_id = 0;
                        if (boo == true) {
                            curr_swimmer_id = Integer.parseInt(search_add_s_id.getText());
                            allDb.update_swimmer(Integer.parseInt(search_add_s_id.getText()), add_s_name.getText(), sqlDate, add_s_gender.getValue(), add_s_level.getValue(), add_s_phone.getText(), g_id, add_s_range.getValue(), b, ((allDb.get_type_cost(add_s_type.getValue()) / 12) * add_s_range.getValue()), add_s_type.getValue());
                        } else {
                            curr_swimmer_id = allDb.addswimmer(add_s_name.getText(), sqlDate, add_s_gender.getValue(), add_s_level.getValue(), add_s_phone.getText(), g_id, add_s_range.getValue(), b, ((allDb.get_type_cost(add_s_type.getValue()) / 12) * add_s_range.getValue()), add_s_type.getValue());
                        }

                        if (curr_swimmer_id != 0) {
                            List<all_information_for_group> id = new ArrayList<all_information_for_group>();
                            id = allDb.search_group_by_name_and_time_and_day(coach_swimmer.getValue(), time_swimmer.getValue(), b);

                            java.awt.print.PrinterJob pj = java.awt.print.PrinterJob.getPrinterJob();
                            if (id.get(0).getDay() == 0) {
                                pj.setPrintable(new BillPrintable(add_s_name.getText(), add_s_phone.getText(), sqlDate, id.get(0).getG_level(), add_s_level.getValue(), "Saturday", id.get(0).getTime().toString(), id.get(0).getTrack(), n_coach, curr_swimmer_id, ((allDb.get_type_cost(add_s_type.getValue()) / 12.0) * add_s_range.getValue()), add_s_range.getValue(), allDb.get_start_date(curr_swimmer_id), allDb.get_end_date(curr_swimmer_id)), getPageFormat(pj));

                            } else if (id.get(0).getDay() == 1) {
                                pj.setPrintable(new BillPrintable(add_s_name.getText(), add_s_phone.getText(), sqlDate, id.get(0).getG_level(), add_s_level.getValue(), "Sunday", id.get(0).getTime().toString(), id.get(0).getTrack(), n_coach, curr_swimmer_id, ((allDb.get_type_cost(add_s_type.getValue()) / 12) * add_s_range.getValue()), add_s_range.getValue(), allDb.get_start_date(curr_swimmer_id), allDb.get_end_date(curr_swimmer_id)), getPageFormat(pj));

                            } else if (id.get(0).getDay() == 2) {
                                pj.setPrintable(new BillPrintable(add_s_name.getText(), add_s_phone.getText(), sqlDate, id.get(0).getG_level(), add_s_level.getValue(), "friday", id.get(0).getTime().toString(), id.get(0).getTrack(), n_coach, curr_swimmer_id, ((allDb.get_type_cost(add_s_type.getValue()) / 12) * add_s_range.getValue()), add_s_range.getValue(), allDb.get_start_date(curr_swimmer_id), allDb.get_end_date(curr_swimmer_id)), getPageFormat(pj));

                            }
                            pj.print();
                            pj.cancel();

                            JOptionPane.showMessageDialog(null, "تم اضافه السباح");

                            initialize_search_swimmer();

                            add_s_name.setText("");
                            add_s_phone.setText("");

                        }
                    } else {

                        JOptionPane.showMessageDialog(null, "هذا الجروب مكتمل");

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "الكابتن  " + coach_swimmer.getValue() + " " + "مفيش مجموعة ف نفس المعاد");

                }
                allDb.DB_close();
            }
        }

    }

    Time time_to_tranfer_to_add_swimmer;
    String coach_to_tranfer_to_add_swimmer, day_to_tranfer_to_add_swimmer, type_to_tranfer_to_add_swimmer;

    public void tranfer_to_add_swimmer(ActionEvent actionEvent) throws SQLException {

        coach_swimmer.setValue(coach_to_tranfer_to_add_swimmer);
        time_swimmer.setValue(time_to_tranfer_to_add_swimmer);
        day_swimmer.setValue(day_to_tranfer_to_add_swimmer);
        add_s_type.setValue(type_to_tranfer_to_add_swimmer);

        p_s_add.toFront();
    }

    public void swi(ActionEvent actionEvent) throws SQLException {
        if (actionEvent.getSource() == b_home) {
            p_home.toFront();
            initialize_combobox_all_group();

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
            p_settings.toFront();
        }
        if (actionEvent.getSource() == b_report) {
            try {
                ///////////////////////setting//////

                report(LocalDate.now().toString());
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            report.toFront();
        }

        if (actionEvent.getSource() == b_salary) {

            allDb.DB_connection();
            BuildSalary(allDb.get_All_Id_Of_coach());
            allDb.DB_close();
            p_salary.toFront();

        }
    }
//

    public void print(ActionEvent actionEvent) {
        //  Node p_home = new Circle(100, 200, 200);

        java.util.Date now = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date_print_home.setVisible(true);
        date_print_home.setDisable(false);
        date_print_home.setText(sdf.format(now));
        b_print_home.setVisible(false);
        scrooll.setVbarPolicy(ScrollBarPolicy.NEVER);

        Printer printer = Printer.getDefaultPrinter();

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {

            PageLayout pageLayout = job.getJobSettings().getPageLayout();

            double pagePrintableWidth = pageLayout.getPrintableWidth();
            double pagePrintableHeight = pageLayout.getPrintableHeight();
            double scaleX = 1.0;
            if (pagePrintableWidth < p_home.getBoundsInParent().getWidth()) {
                scaleX = pagePrintableWidth / p_home.getBoundsInParent().getWidth();
            }
            double scaleY = 1.0;
            if (pagePrintableHeight < p_home.getBoundsInParent().getHeight()) {
                scaleY = pagePrintableHeight / p_home.getBoundsInParent().getHeight();
            }
            double scaleXY = Double.min(scaleX, scaleY);
            Scale scale = new Scale(.39, .5);

//
//            p_home.minHeightProperty().bind(p_home.prefHeightProperty());
//            p_home.maxHeightProperty().bind(p_home.prefHeightProperty());
//           
            int lengh = id.size() - 5;
            double numberOfPages = Math.ceil(((lengh > 0) ? lengh : 0) / 6.0);
            numberOfPages++;

            p_home.getTransforms().add(scale);
            p_home.getTransforms().add(new Translate(0, 0));

            Translate gridTransform = new Translate();
            p_home.getTransforms().add(gridTransform);

            boolean success = false;
            for (int i = 0; i < numberOfPages; i++) {
                int x = (i == 1) ? 1210 : 1268;//1157
                x = (i == 3) ? (x + 28) : x;
                gridTransform.setY(-i * x);
                success = job.printPage(pageLayout, p_home);

            }
            // boolean success = job.printPage(p_home);
            p_home.getTransforms().remove(scale);
            p_home.getTransforms().remove(gridTransform);
            //p_home.getTransforms().remove(new Translate(0, 0));
            if (success) {
                job.endJob();
            }
        }
        date_print_home.setVisible(false);
        b_print_home.setVisible(true);
        scrooll.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    }

    public void print_search(ActionEvent actionEvent) {
        //  Node p_home = new Circle(100, 200, 200);
        Printer printer = Printer.getDefaultPrinter();

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {

            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            Scale scale = new Scale(.66, .8);

            System.out.println("table_search.getHeight() : " + table_search.getHeight());
            System.out.println("pane_search_table.getHeight() : " + pane_search_table.getHeight());

            double lengh = table_search.getHeight() - 800;
            double numberOfPages = Math.ceil(((lengh > 0) ? lengh : 0) / 800.0);
            numberOfPages++;
            System.out.println("numberOfPages : " + numberOfPages);

            table_search.getTransforms().add(scale);
            table_search.getTransforms().add(new Translate(0, 0));

            Translate gridTransform = new Translate();
            table_search.getTransforms().add(gridTransform);

            boolean success = false;
            for (int i = 0; i < numberOfPages; i++) {
                gridTransform.setY(-i * 810);
                success = job.printPage(pageLayout, table_search);

            }
            // boolean success = job.printPage(p_home);
            table_search.getTransforms().remove(scale);
            table_search.getTransforms().remove(gridTransform);
            //p_home.getTransforms().remove(new Translate(0, 0));
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
//          
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
                JOptionPane.showMessageDialog(null, "لم يتم اختيار النوع");
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
    int bool = 0;
    int coach_id_punish;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initialize_home();

        initialize_add_group();

        ///////////////////add coach///////////
        add_C_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", " l6", "l7", "l8");
        inf_c_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", " l6", "l7", "l8");
        inf_s_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", " l6", "l7", "l8");
///////////////////add coach///////////
        ///////////////////add swimmer///////////
        initialize_add_swimmer();

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

        ///////////////////////setting//////
        initialize_Settings();
        ///////////////////////setting//////

    }

    public void report_date(ActionEvent actionEvent) throws SQLException {

        vbox_report.getChildren().clear();
        vbox_total_add_s.getChildren().clear();
        vbox_report_trfihee.getChildren().clear();
        vbox_total_trfihee.getChildren().clear();
        vbox_report_att_s.getChildren().clear();
        vbox_report_att_c.getChildren().clear();
        String r_date = select_date_report.getValue().toString();
        report(r_date);
    }

    private String report(String r_date) throws SQLException {
        vbox_report.getChildren().clear();
        vbox_total_add_s.getChildren().clear();
        vbox_report_trfihee.getChildren().clear();
        vbox_total_trfihee.getChildren().clear();
        vbox_report_att_s.getChildren().clear();
        vbox_report_att_c.getChildren().clear();
        Label trfihee = make_lable_search_head("trfihee ", .539, "303436", 15);
        trfihee.setTextFill(rgb(255, 255, 255));

        Label trfihee_n = make_lable_search_head("N", .04, "b6bec2", 15);
        Label trfihee_id = make_lable_search_head("trfihee_id", 0.25, "b6bec2", 15);
        Label trfihee_name = make_lable_search_head("trfihee_name", 0.25, "b6bec2", 15);

        HBox trfihee_name_r = new HBox();
        trfihee_name_r.setStyle("-fx-background-color:  #b6bec2;");
        trfihee_name_r.setPrefSize(bounds.getWidth() * .54, 0);
        trfihee_name_r.getChildren().addAll(trfihee_name, trfihee_id, trfihee_n);
        trfihee_name_r.setAlignment(Pos.CENTER_RIGHT);
        allDb.DB_connection();

        List<trfihee> r_trfihee = new ArrayList<trfihee>();

        r_trfihee = allDb.report_trfihee(r_date);

        for (int x = 0; x < r_trfihee.size(); x++) {
            vbox_report_trfihee.getChildren().add(trfihee);
            vbox_report_trfihee.getChildren().add(trfihee_name_r);

            Label trfihee_r_n = make_lable_g((x + 1) + "", .04);
            Label trfihee_r_id = make_lable_g(r_trfihee.get(x).getTrfihee_id() + "", .25);
            Label trfihee_r_name = make_lable_g(r_trfihee.get(x).getName(), 0.25);

            HBox title_trfihee = new HBox();
            title_trfihee.setStyle("-fx-background-color:  #ffb3e6;");
            title_trfihee.setPrefSize(bounds.getWidth() * .54, 0);
            title_trfihee.getChildren().addAll(trfihee_r_name, trfihee_r_id, trfihee_r_n);
            title_trfihee.setAlignment(Pos.CENTER_RIGHT);

            vbox_report_trfihee.getChildren().add(title_trfihee);

        }
        Label t_trfihee = make_lable_search_head(" total trfihee ", .539, "303436", 15);
        t_trfihee.setTextFill(rgb(255, 255, 255));

        Label trfihee_t_n = make_lable_search_head("Num", .3, "b6bec2", 15);
        Label trfihee_t_cost = make_lable_search_head("cost", 0.3, "b6bec2", 15);

        HBox trfihee_t = new HBox();
        trfihee_t.setStyle("-fx-background-color:  #b6bec2;");
        trfihee_t.setPrefSize(bounds.getWidth() * .54, 0);
        trfihee_t.getChildren().addAll(trfihee_t_cost, trfihee_t_n);
        trfihee_t.setAlignment(Pos.CENTER_RIGHT);
        vbox_total_trfihee.getChildren().add(t_trfihee);

        vbox_total_trfihee.getChildren().add(trfihee_t);

        Label trfihee_t_num = make_lable_g(String.valueOf(r_trfihee.size()) + "", .3);
        Label trfihee_t_c = make_lable_g(String.valueOf(r_trfihee.size() * allDb.get_total_cost()), 0.3);

        HBox title_trfihee_t = new HBox();
        title_trfihee_t.setStyle("-fx-background-color:  #ffb3e6;");
        title_trfihee_t.setPrefSize(bounds.getWidth() * .54, 0);
        title_trfihee_t.getChildren().addAll(trfihee_t_c, trfihee_t_num);
        title_trfihee_t.setAlignment(Pos.CENTER_RIGHT);

        vbox_total_trfihee.getChildren().add(title_trfihee_t);

/////////////////////////
        Label swimmer_att = make_lable_search_head("The attend swimmer ", .539, "303436", 15);
        swimmer_att.setTextFill(rgb(255, 255, 255));

        Label num_att_s = make_lable_search_head("N", .04, "b6bec2", 15);
        Label attend_id_s = make_lable_search_head("attend_id", 0.18, "b6bec2", 15);
        Label attend_name_s = make_lable_search_head("attend_name", 0.18, "b6bec2", 15);
        Label time_s = make_lable_search_head("time_c", 0.18, "b6bec2", 15);

        HBox title_s = new HBox();
        title_s.setStyle("-fx-background-color:  #b6bec2;");
        title_s.setPrefSize(bounds.getWidth() * .54, 0);
        title_s.getChildren().addAll(time_s, attend_name_s, attend_id_s, num_att_s);
        title_s.setAlignment(Pos.CENTER_RIGHT);

        List<s> s_att = new ArrayList<s>();
        List<group> st_att = new ArrayList<group>();

        s_att = allDb.report_attend_swimmer(r_date);
        st_att = allDb.report_attend_s_time(r_date);

        vbox_report_att_s.getChildren().addAll(swimmer_att);
        vbox_report_att_s.getChildren().add(title_s);

        for (int x = 0; x < s_att.size(); x++) {

            Label num_s = make_lable_g((x + 1) + "", .02);
            Label Attend_s = make_lable_g(s_att.get(x).getAtt_id() + "", .16);
            Label name_s_att = make_lable_g(s_att.get(x).getName(), 0.16);
            Label time_a_s = make_lable_g(st_att.get(x).getTime(), 0.16);
//            if (num_s.getText().isEmpty() && Attend_s.getText().isEmpty() && name_s_att.getText().isEmpty() && time_a_s.getText().isEmpty()) {
//
//            } else {

            HBox title_att_s = new HBox();
            title_att_s.setStyle("-fx-background-color:  #ffb3e6;");
            title_att_s.setPrefSize(bounds.getWidth() * .54, 0);
            title_att_s.getChildren().addAll(time_a_s, name_s_att, Attend_s, num_s);
            title_att_s.setAlignment(Pos.CENTER_RIGHT);

            vbox_report_att_s.getChildren().add(title_att_s);

        }
        Label swimmer = make_lable_search_head("add Swimmer in Group", .539, "303436", 15);
        swimmer.setTextFill(rgb(255, 255, 255));
        Label num_r_s = make_lable_search_head("N", .04, "b6bec2", 15);
        Label id_s = make_lable_search_head("ID", .09, "b6bec2", 15);
        Label name_s = make_lable_search_head("Name", 0.16, "b6bec2", 15);
        Label num_day_s = make_lable_search_head("num_day", 0.14, "b6bec2", 15);
        Label type_s = make_lable_search_head("type", 0.14, "b6bec2", 15);

        HBox title_r_s = new HBox();
        title_r_s.setStyle("-fx-background-color:  #b6bec2;");
        title_r_s.setPrefSize(bounds.getWidth() * .54, 0);
        title_r_s.getChildren().addAll(type_s, num_day_s, name_s, id_s, num_r_s);
        title_r_s.setAlignment(Pos.CENTER_RIGHT);
        vbox_report.getChildren().add(swimmer);
        vbox_report.getChildren().add(title_r_s);

        List<swimmer_and_group> s_g = new ArrayList<swimmer_and_group>();

        s_g = allDb.sdate(r_date);
        int sum = 0;
        int cost = 0;
        for (int x = 0; x < s_g.size(); x++) {

            Label num_sw = make_lable_g((x + 1) + "", .04);
            Label id_s1 = make_lable_g(s_g.get(x).getS_id() + "", .09);
            Label name_s1 = make_lable_g(s_g.get(x).getName(), 0.16);
            Label time_r_s = make_lable_g(s_g.get(x).getG_time(), 0.14);
            Label type_r_s = make_lable_g(s_g.get(x).getType(), 0.14);

            List<all_information_for_group> s_t = new ArrayList<all_information_for_group>();

            allDb.get_type_of_swimmer(name_s1.getText());
            String s = allDb.get_type_of_swimmer(name_s1.getText());
            cost = allDb.get_type_total_cost(s);

            sum = sum + cost;
            s_t = (allDb.report_swimmer_time(r_date));

            HBox title_s1 = new HBox();
            title_s1.setStyle("-fx-background-color:  #ffb3e6;");
            title_s1.setPrefSize(bounds.getWidth() * .54, 0);
            title_s1.getChildren().addAll(type_r_s, time_r_s, name_s1, id_s1, num_sw);
            title_s1.setAlignment(Pos.CENTER_RIGHT);

            vbox_report.getChildren().add(title_s1);
        }
///////////////////////////
        Label coach_att = make_lable_search_head("The attend coach ", .539, "303436", 15);
        coach_att.setTextFill(rgb(255, 255, 255));

        Label num_att_c = make_lable_search_head("N", .04, "b6bec2", 15);
        Label attend_id_c = make_lable_search_head("attend_id", 0.18, "b6bec2", 15);
        Label attend_name_c = make_lable_search_head("replace_name", 0.18, "b6bec2", 15);
        Label time_c = make_lable_search_head("time_c", 0.18, "b6bec2", 15);

        HBox title_c = new HBox();
        title_c.setStyle("-fx-background-color:  #b6bec2;");
        title_c.setPrefSize(bounds.getWidth() * .54, 0);
        title_c.getChildren().addAll(time_c, attend_name_c, attend_id_c, num_att_c);
        title_c.setAlignment(Pos.CENTER_RIGHT);
        vbox_report_att_c.getChildren().add(coach_att);
        vbox_report_att_c.getChildren().add(title_c);

        List<attend_couch> c = new ArrayList<attend_couch>();
        List<coach> time_co = new ArrayList<coach>();
        List<group> time_g = new ArrayList<group>();
        c = allDb.c_att(r_date);
        time_co = allDb.report_attend_coach(r_date);
        time_g = allDb.report_time_replace(r_date);

        for (int x = 0; x < c.size(); x++) {

            Label num_c = make_lable_g((x + 1) + "", .02);
            Label Attend_c = make_lable_g(c.get(x).getAttend_id() + "", .16);

            Label Rep_name = make_lable_g(time_co.get(x).getName(), .16);

            Label time_go = make_lable_g(time_g.get(x).getTime(), .16);

            HBox title_att_c = new HBox();
            title_att_c.setStyle("-fx-background-color:  #ffb3e6;");
            title_att_c.setPrefSize(bounds.getWidth() * .54, 0);
            title_att_c.getChildren().addAll(time_go, Rep_name, Attend_c, num_c);
            title_att_c.setAlignment(Pos.CENTER_RIGHT);
            vbox_report_att_c.getChildren().add(title_att_c);

            //     int size=(int) (vbox_report_att_c.getHeight()+vbox_report_att_s.getHeight()+vbox_report_trfihee.getHeight()+vbox_report.getHeight()+vbox_total_add_s.getHeight()+vbox_total_trfihee.getHeight());
            //      pane_report.resize(report.getPrefWidth(), report.getPrefHeight());
            // report.setPrefSize(pane_report.getPrefWidth(), pane_report.getPrefHeight()); //didn't work
        }
        Label t_add_s = make_lable_search_head(" total add swimmer ", .539, "303436", 15);
        t_add_s.setTextFill(rgb(255, 255, 255));

        Label t_a_s_num = make_lable_search_head("Num", .3, "b6bec2", 15);
        Label t_a_s_cost = make_lable_search_head("cost", 0.3, "b6bec2", 15);

        HBox total_add_s = new HBox();
        total_add_s.setStyle("-fx-background-color:  #b6bec2;");
        total_add_s.setPrefSize(bounds.getWidth() * .54, 0);
        total_add_s.getChildren().addAll(t_a_s_cost, t_a_s_num);
        total_add_s.setAlignment(Pos.CENTER_RIGHT);

        vbox_total_add_s.getChildren().add(t_add_s);

        vbox_total_add_s.getChildren().add(total_add_s);
        Label total_add_s_n = make_lable_g(String.valueOf(s_g.size()) + "", .3);
        Label total_add_s_c = make_lable_g(String.valueOf(sum), 0.3);

        HBox total_add_swim = new HBox();
        total_add_swim.setStyle("-fx-background-color:  #ffb3e6;");
        total_add_swim.setPrefSize(bounds.getWidth() * .54, 0);
        total_add_swim.getChildren().addAll(total_add_s_c, total_add_s_n);
        total_add_swim.setAlignment(Pos.CENTER_RIGHT);

        int tot = r_trfihee.size() + s_att.size() + c.size() + 2;
        if (tot > 5) {

            pane_report.setPrefHeight(pane_report.getPrefHeight() + ((tot - 5) * 30));
        }

        allDb.DB_close();

        vbox_total_add_s.getChildren().add(total_add_swim);

        return r_date;
    }

    List<TextField> te = new ArrayList<TextField>();
    List<TextField> te1 = new ArrayList<TextField>();

    List<String> id_setting = new ArrayList<String>();

    private void initialize_combobox_all_group() {

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
            t = allDb.get_swimmerWithgroup(all_g_id);
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
            System.out.println("initialize_combobox_all_group" + ex);
        }

    }

    private void initialize_Settings_insert(int i, String l) {

        //Label l11 = make_lable_setting(l + " :", .08);
        TextField t1 = new TextField();
        t1.setText(l);
        t1.setPrefSize(86.0, 30.0);
        t1.setDisable(true);
        t1.setAlignment(Pos.CENTER);
        te1.add(t1);

        TextField t = new TextField();
        t.setText(allDb.get_type_cost(l) + "");
        t.setPrefSize(64.0, 30.0);
        t.setDisable(true);
        t.setAlignment(Pos.CENTER);
        te.add(t);

        Image image1 = new Image(getClass().getResourceAsStream("/images/edit.png"));
        ImageView Iview1 = new ImageView(image1);
        Iview1.setFitHeight(22);
        Iview1.setFitWidth(18);
        Iview1.setPickOnBounds(true);
        Iview1.setPreserveRatio(true);

        JFXButton b = new JFXButton(" ", Iview1);
        b.setPrefSize(27, 26);
        b.setStyle("-fx-background-color: #258bbf;;");
        b.setOnAction((event) -> {
            if (t.isDisable()) {
                t.setDisable(false);
                t1.setDisable(false);
            } else {
                t.setDisable(true);
                t1.setDisable(true);
            }
        });

        HBox title1 = new HBox();
        //title1.setStyle("-fx-background-color: #ffe6f9;");
        title1.setPrefSize(1000, 0);
        title1.setMaxSize(10000, 10000);
        title1.setSpacing(5);
        title1.getChildren().addAll(b, te.get(i), te1.get(i));
        title1.setAlignment(Pos.CENTER);
        title1.setId(l + "");

        v_box1_type.getChildren().add(title1);
    }

    private void initialize_Settings() {

        try {
            allDb.DB_connection();
            id_setting = allDb.get_type_name();

            for (int i = 0; i < id_setting.size(); i++) {

                initialize_Settings_insert(i, id_setting.get(i));
            }
            p_settings.getChildren().add(v_box);

            setting_t25er1.setText(allDb.get_cost_for_late1() + "");
            setting_t25er15.setText(allDb.get_cost_for_late5() + "");
            setting_glass.setText(allDb.get_cost_for_glass() + "");
            setting_behaveor.setText(allDb.get_cost_for_Behavior() + "");
            setting_talk.setText(allDb.get_cost_for_talk() + "");
            setting_absent.setText(allDb.get_cost_for_absent() + "");
            setting_re.setText(allDb.get_cost_for_re() + "");
            setting_trfihe.setText(allDb.get_cost_for_one_day() + "");
            setting_coach_cost.setText(allDb.get_cost_for_coach_cost() + "");

            setting_b.setOnAction((event) -> {
                if (setting_t25er1.isDisable()) {
                    setting_t25er1.setDisable(false);
                } else {
                    setting_t25er1.setDisable(true);
                }
            });
            setting_b_5.setOnAction((event) -> {
                if (setting_t25er15.isDisable()) {
                    setting_t25er15.setDisable(false);
                } else {
                    setting_t25er15.setDisable(true);
                }
            });
            setting_b_glass.setOnAction((event) -> {
                if (setting_glass.isDisable()) {
                    setting_glass.setDisable(false);
                } else {
                    setting_glass.setDisable(true);
                }
            });
            setting_b_behavior.setOnAction((event) -> {
                if (setting_behaveor.isDisable()) {
                    setting_behaveor.setDisable(false);
                } else {
                    setting_behaveor.setDisable(true);
                }
            });
            setting_b_talk.setOnAction((event) -> {
                if (setting_talk.isDisable()) {
                    setting_talk.setDisable(false);
                } else {
                    setting_talk.setDisable(true);
                }
            });
            setting_b_absent.setOnAction((event) -> {
                if (setting_absent.isDisable()) {
                    setting_absent.setDisable(false);
                } else {
                    setting_absent.setDisable(true);
                }
            });
            setting_b_re.setOnAction((event) -> {
                if (setting_re.isDisable()) {
                    setting_re.setDisable(false);
                } else {
                    setting_re.setDisable(true);
                }
            });
            setting_b_tr.setOnAction((event) -> {
                if (setting_trfihe.isDisable()) {
                    setting_trfihe.setDisable(false);
                } else {
                    setting_trfihe.setDisable(true);
                }
            });
            setting_b_coach_cost.setOnAction((event) -> {
                if (setting_coach_cost.isDisable()) {
                    setting_coach_cost.setDisable(false);
                } else {
                    setting_coach_cost.setDisable(true);
                }
            });

        } catch (SQLException ex) {
        }

    }

    private Label make_lable_setting(String name, double d) {
        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        //l.setStyle("-fx-border-color:#fff;-fx-textFill:#258bbf;");
        l.setFont(Font.font("System Bold Italic", FontWeight.BOLD, 20));
        l.setTextFill(rgb(37, 139, 191));
        l.setAlignment(Pos.CENTER_LEFT);

        return l;

    }

    ////////////////////////////////home/////////////
    VBox v_box = new VBox();
    VBox table_salary = new VBox();
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
        Label notes = make_lable("Comment", .19);
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
                inf_c_level.setValue(id.get(Integer.parseInt(comp[1])).getC_level());
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
                    l_bouns.setText(allDb.get_bouns_id(coach_id_punish) + "");
                    l_punish_bouns.setText(bouns(coach_id_punish) + "");
                    l_punish_minus.setText(minus(coach_id_punish) + "");
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
                    //        t1.setValue(l_name.get(l_re_id.indexOf(s.get(0).getRep_name())));
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

                    }
                    ch.setOnAction((event) -> {
                        try {
                            String[] companies = ch.getId().split("\\|");

                            allDb.DB_connection();
                            if (!ch.isSelected()) {
                                allDb.delet_attend_swimmer(Integer.parseInt(companies[0]));

                            } else {
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

                pane_table.setPrefHeight(pane_table.getPrefHeight() + bounds.getWidth() * .25);
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
        Label notes = make_lable("Comment", .27);
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
                inf_c_level.setValue(id.get(Integer.parseInt(comp[1])).getC_level());
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
                    l_bouns.setText(allDb.get_bouns_id(coach_id_punish) + "");
                    l_punish_bouns.setText(bouns(coach_id_punish) + "");
                    l_punish_minus.setText(minus(coach_id_punish) + "");

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

                    List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                            .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                            .filter(date -> date.getDayOfWeek() == DayOfWeek.FRIDAY
                            || date.getDayOfWeek() == DayOfWeek.SATURDAY)
                            .collect(Collectors.toList());
                    if (ldate.size() == 9) {
                        ldate.remove(8);
                    }
                    if (!ldate.get(j - 1).isEqual(currentdate)) {
                        ch.setDisable(true);
                    }

                    ch.setOnAction((event) -> {
                        try {
                            String[] companies = ch.getId().split("\\|");

                            allDb.DB_connection();
                            if (!ch.isSelected()) {
                                allDb.delet_attend_swimmer(Integer.parseInt(companies[0]));

                            } else {
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
                t.setPrefWidth(bounds.getWidth() * .27);
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
                t.setPrefWidth(bounds.getWidth() * .27);
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

                pane_table.setPrefHeight(pane_table.getPrefHeight() + bounds.getWidth() * .25);
            }
        }
        pane_table.getChildren().clear();
        pane_table.getChildren().add(table);
        //p_home.getChildren().add(table);

    }

    private Label make_lable(String name, double d) {

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
        scrooll.setPrefSize(bounds.getWidth() * 0.805, bounds.getHeight() * 0.880);
        scrooll.setHbarPolicy(ScrollBarPolicy.NEVER);
        pane_table.setPrefSize(bounds.getWidth() * 0.800, bounds.getHeight() * 0.880);
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
                    t = allDb.get_swimmerWithgroup(all_g_id);
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

//      
    }
//////////////////////////home/////////////

    /////////////////////add////////////
    private void initialize_add_group() {

        add_group_coach.getItems().clear();
        add_group_day.getItems().clear();
        add_group_line.getItems().clear();
        add_group_level.getItems().clear();
        add_group_type.getItems().clear();
        update_coach.getItems().clear();
        update_group_day.getItems().clear();
        update_group_line.getItems().clear();
        update_group_level.getItems().clear();
        update_group_type.getItems().clear();

        try {
            coach = new ArrayList<coach>();
            allDb.DB_connection();
            coach = allDb.allcoach();

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

            add_group_type.getItems().addAll(allDb.get_type_name());
            update_group_type.getItems().addAll(allDb.get_type_name());

            add_group_time.setValue(LocalTime.MIN);
            update_group_time.setValue(LocalTime.MIN);

            allDb.DB_close();
        } catch (SQLException ex) {
        }

    }

    private void initialize_add_swimmer() {
        day_swimmer.getItems().clear();
        add_s_gender.getItems().clear();
        time_swimmer.getItems().clear();
        coach_swimmer.getItems().clear();
        add_s_type.getItems().clear();

        day_swimmer.getItems().addAll("Saturday", "Sunday", "friday");
        add_s_gender.getItems().addAll("Male", "Female");
        add_s_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8");
        add_s_range.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        add_s_type.getItems().add("الترفيهي");
        try {
            allDb.DB_connection();
            time_swimmer.getItems().addAll(allDb.All_time_of_group_without_repeat());
            coach_swimmer.getItems().addAll(allDb.search_group_by_name());
            add_s_type.getItems().addAll(allDb.get_type_name());
            allDb.DB_close();

            add_s_range.setOnAction((event) -> {
                try {
                    allDb.DB_connection();
                    total_add_s.setText(((allDb.get_type_cost(add_s_type.getValue()) / 12) * ((add_s_range.getSelectionModel().isEmpty()) ? 0 : add_s_range.getValue())) + "");
                    allDb.DB_close();
                } catch (SQLException ex) {
                }
            });
            add_s_type.setOnAction((event) -> {

                if (add_s_type.getSelectionModel().isSelected(0)) {
                    try {
                        allDb.DB_connection();
                        total_add_s.setText(allDb.get_cost_for_one_day() + "");
                        allDb.DB_close();
                    } catch (SQLException ex) {
                    }

                    add_s_level.setDisable(true);
                    add_s_range.setDisable(true);
                    add_s_age.setDisable(true);
                    add_s_gender.setDisable(true);
                    coach_swimmer.setDisable(true);
                    day_swimmer.setDisable(true);
                    time_swimmer.setDisable(true);

                } else {

                    try {
                        allDb.DB_connection();
                        total_add_s.setText(((allDb.get_type_cost(add_s_type.getValue()) / 12) * ((add_s_range.getSelectionModel().isEmpty()) ? 0 : add_s_range.getValue())) + "");
                        allDb.DB_close();
                    } catch (SQLException ex) {
                    }

                    add_s_level.setDisable(false);
                    add_s_range.setDisable(false);
                    add_s_age.setDisable(false);
                    add_s_gender.setDisable(false);
                    coach_swimmer.setDisable(false);
                    day_swimmer.setDisable(false);
                    time_swimmer.setDisable(false);
                }

            });
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
        search_g_level.getItems().addAll("B", "l1", "l2", "l3", "l4", "l5", "l6", "l7", "l8");

        try {
            allDb.DB_connection();
            search_g_line.getItems().addAll(allDb.get_type_name());
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

        scroll_search.setPrefSize(bounds.getWidth() * 0.60, bounds.getHeight() * 0.4);
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
        Label num = make_lable_search_head("N", .039, "b6bec2", 20);
        Label name = make_lable_search_head("Name", 0.09, "b6bec2", 20);
        Label c_id = make_lable_search_head("c_id", 0.08, "b6bec2", 20);
        Label level = make_lable_search_head("level", .06, "b6bec2", 20);
        Label track = make_lable_search_head("Line", .06, "b6bec2", 20);
        Label type = make_lable_search_head("Type", .07, "b6bec2", 20);
        Label time = make_lable_search_head("Time", .07, "b6bec2", 20);
        Label day = make_lable_search_head("Day", .07, "b6bec2", 20);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .54, 0);
        title.getChildren().addAll(day, time, type, track, level, name, c_id, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_g((i + 1) + "", .039);
            Label name1 = make_lable_g(id.get(i).getName(), 0.09);
            Label c_id1 = make_lable_g(id.get(i).getC_id() + "", 0.08);
            Label level1 = make_lable_g(id.get(i).getG_level(), .06);
            Label track1 = make_lable_g(id.get(i).getTrack(), .06);
            Label time1 = make_lable_g(id.get(i).getTime() + "", .07);
            Label type1 = make_lable_g(id.get(i).getG_type(), .07);
            Label d1 = make_lable_g("Saturday", .07);
            if ((id.get(i).getDay() == 0)) {
                d1 = make_lable_g("Saturday", .07);
            } else if ((id.get(i).getDay() == 1)) {
                d1 = make_lable_g("Sunday", .07);
            } else if ((id.get(i).getDay() == 2)) {
                d1 = make_lable_g("friday", .07);
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
                update_group_type.setValue(type1.getText());
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
            title1.getChildren().addAll(day1, time1, type1, track1, level1, name1, c_id1, num1);
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
                    Label num_inf = make_lable_search_head("N", .039, "b6bec2", 20);
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
                    type_to_tranfer_to_add_swimmer = id.get(Integer.parseInt(num1.getText()) - 1).getG_type();
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

                    Label num_s = make_lable_search_head("N", .04, "b6bec2", 15);
                    Label id_s = make_lable_search_head("ID", .139, "b6bec2", 15);
                    Label name_s = make_lable_search_head("Name", 0.18, "b6bec2", 15);
                    Label gender_s = make_lable_search_head("Gender", .18, "b6bec2", 15);

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

            if (i > 9) {

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .035);
            }
        }

////////////////
        pane_search_table.getChildren().clear();
        pane_search_table.getChildren().add(table_search);

    }

    private Label make_lable_search_head(String name, double d, String color, int font) {

        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        l.setStyle("-fx-background-color:#" + color + ";-fx-border-color:#000;");
        l.setFont(Font.font("Verdana", FontWeight.BOLD, font));
        l.setAlignment(Pos.CENTER);

        return l;
    }

    int id_update;
    int update_id_s;

    private void BuildSearch_Swimmer(List<all_information_for_swimmer> id) throws SQLException {
        table_search.getChildren().clear();
        pane_search_table.setPrefSize(bounds.getWidth() * 0.59, bounds.getHeight() * 0.55);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Label num = make_lable_search_head("Id", .03, "b6bec2", 17);
        Label name = make_lable_search_head("Name", 0.08, "b6bec2", 17);
        Label address = make_lable_search_head("Coach", 0.059, "b6bec2", 17);
        Label age = make_lable_search_head("DOB", 0.035, "b6bec2", 17);
        Label gender = make_lable_search_head("gender", 0.0539, "b6bec2", 17);
        Label phone = make_lable_search_head("Phone", 0.05, "b6bec2", 17);
        Label level = make_lable_search_head("level", .039, "b6bec2", 17);
        Label s_day = make_lable_search_head("S_Day", .061, "b6bec2", 17);
        Label e_day = make_lable_search_head("E_Day", .061, "b6bec2", 17);
        Label time = make_lable_search_head("Time", .064, "b6bec2", 17);
        Label day = make_lable_search_head("Day", .064, "b6bec2", 17);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .55, 0);
        title.getChildren().addAll(day, time, e_day, s_day, level, gender, age, address, name, num);
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
            Label level1 = make_lable_search_swimmer(id.get(i).getS_level(), .04);
            Label s_day1 = make_lable_search_swimmer(id.get(i).getStart() + "", .063);
            Label e_day1 = make_lable_search_swimmer(id.get(i).getEnd() + "", .063);
            Label time1 = make_lable_search_swimmer(id.get(i).getG_time() + "", .065);
            Label da = make_lable_g("Saturday", .065);
            if (id.get(i).getDay() == 0) {
                da = make_lable_search_swimmer("Saturday", .065);
            } else if (id.get(i).getDay() == 1) {
                da = make_lable_search_swimmer("Sunday", .065);
            } else if (id.get(i).getDay() == 2) {
                da = make_lable_search_swimmer("friday", .065);
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
            HBox title2 = new HBox();
            title2.setStyle("-fx-background-color: #ffb3e6;-fx-border-color:#000;");
            title2.setPrefSize(bounds.getWidth() * .04, 0);
            title2.getChildren().addAll(Iview);
            title2.setAlignment(Pos.CENTER);
            title2.setSpacing(15);

            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffb3e6;");
            title1.setPrefSize(bounds.getWidth() * .55, 0);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.getChildren().addAll(day1, time1, e_day1, s_day1, level1, gender1, age1, address1, name1, id1);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3e6 ");
            });
            title1.setOnMouseClicked((MouseEvent event) -> {
                List<attend_swimmer> s = new ArrayList<attend_swimmer>();
                inf_s_phone.setDisable(false);
                inf_s_level.setDisable(false);
                inf_s_age.setDisable(false);
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
                inf_s_level.setValue(level1.getText());
                inf_s_phone.setText(phone1.getText());
                inf_s_start_day.setText(s_day1.getText());
                inf_s_time.setText(time1.getText());
                update_id_s = Integer.parseInt(id1.getText());
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

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .035);
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

        Label num = make_lable_search_head("N", .04, "b6bec2", 18);
        Label name = make_lable_search_head("Name", 0.1, "b6bec2", 18);
        Label Id = make_lable_search_head("Id", 0.048, "b6bec2", 18);
        Label Date = make_lable_search_head("Date", 0.07, "b6bec2", 18);
        Label n_day = make_lable_search_head("N_Day", 0.06, "b6bec2", 18);
        Label coach = make_lable_search_head("Coach", 0.06, "b6bec2", 18);
        Label level = make_lable_search_head("level", .05, "b6bec2", 18);
        Label time = make_lable_search_head("Time", .06, "b6bec2", 18);
        Label day = make_lable_search_head("Day", .06, "b6bec2", 18);

        HBox title = new HBox();
        title.setStyle("-fx-background-color:  #b6bec2;");
        title.setPrefSize(bounds.getWidth() * .59, 0);
        title.getChildren().addAll(day, time, level, coach, n_day, Date, name, Id, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_search.getChildren().add(title);

        for (int i = 0; i < id.size(); i++) {
            Label num1 = make_lable_search_swimmer((i + 1) + "", .04);
            Label name1 = make_lable_search_swimmer(id.get(i).getName(), 0.1);
            Label id1 = make_lable_search_swimmer(id.get(i).getS_id() + "", .048);
            Label date1 = make_lable_search_swimmer(id.get(i).getDay() + "", 0.07);
            Label n_day1 = make_lable_search_swimmer(id.get(i).getNum() + "", .06);
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
            title1.getChildren().addAll(day1, time1, level1, coach1, n_day1, date1, name1, id1, num1);
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffb3e6 ");
            });

            table_search.getChildren().add(title1);
            if (i > 15) {

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .035);
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

                pane_search_table.setPrefHeight(pane_search_table.getPrefHeight() + bounds.getWidth() * .035);
            }
        }

        pane_search_table.getChildren().clear();
        pane_search_table.getChildren().add(table_search);

    }

    ////////////////////////search//////////
    ////////////////////////salary//////////
    private Label make_lable_salary_head(String name, double d) {

        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        l.setStyle("-fx-background-color:#e6f2ff;-fx-border-color:#fff;");
        l.setFont(Font.font("Verdana", FontWeight.THIN, 18));
        l.setAlignment(Pos.CENTER);

        return l;
    }
    //l.setStyle("-fx-font-size: 16px;-fx-background-color:#ffe6f9;-fx-border-color:#fff;");

    private Label make_lable_salary_bady(String name, double d) {
//-fx-background-color:#ffe6f9;
        Label l = new Label(name);
        l.setPrefWidth(bounds.getWidth() * d);
        l.setStyle("-fx-font-size: 16px;-fx-border-color:#fff;");
        l.setAlignment(Pos.CENTER);

        return l;
    }

    private void BuildSalary(List<Integer> id) throws SQLException {
        table_salary.getChildren().clear();

        table_salary.setPrefSize(bounds.getWidth() * 0.800, bounds.getHeight() * 0.90);
        table_salary.setLayoutX(0);
        table_salary.setLayoutY(0);
        table_salary.setStyle("-fx-background-color:   #fff;");
        table_salary.setStyle("-fx-background-color:  #ffb3b3;");
        table_salary.setSpacing(2);
        scrooll_salary.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.880);
        pane_table_salary.setPrefSize(bounds.getWidth() * 0.800, bounds.getHeight() * 0.880);

        Label num = make_lable_salary_head("ID", .05);
        Label name = make_lable("Name", 0.076);
        Label l1 = make_lable("السبت", .04);
        Label l1s = make_lable("الاحد", .03);
        Label l1f = make_lable("الجمعة", .05);
        Label main_salary = make_lable("الاساسي", .05);
        Label l2 = make_lable("تأخير1د", .05);
        Label l3 = make_lable("تأخير5د", .05);
        Label l4 = make_lable("النضاره", .05);
        Label l5 = make_lable("السلوك", .045);
        Label l6 = make_lable("التحدث مع ولي الامر", .11);
        Label l7 = make_lable("الغياب", .045);
        Label l8 = make_lable("الاحتياطى", .06);
        Label lbouns = make_lable(" + ", .03);
        Label l9 = make_lable("الكل", .06);
        HBox title = new HBox();
        title.setStyle("-fx-background-color: #ffffff;");
        title.setPrefSize(1000, 0);
        title.setMaxSize(10000, 10000);
        title.getChildren().addAll(l9, lbouns, l8, l7, l6, l5, l4, l3, l2, main_salary, l1f, l1s, l1, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table_salary.getChildren().add(title);
        for (int i = 0; i < id.size(); i++) {

            allDb.DB_connection();
            Label num1 = make_lable_salary_bady(id.get(i) + "", .05);
            Label name1 = make_lable_salary_bady(allDb.coach_by_name(id.get(i)), 0.076);
            Label l11 = make_lable_salary_bady(allDb.get_count_of_swimmer_with_caoch(id.get(i), 0) + "", .04);
            Label l11s = make_lable_salary_bady(allDb.get_count_of_swimmer_with_caoch(id.get(i), 1) + "", .03);
            Label l11f = make_lable_salary_bady(allDb.get_count_of_swimmer_with_caoch(id.get(i), 2) + "", .05);
            Label l21 = make_lable_salary_bady(allDb.get_count_of_punish(id.get(i), 1) + "", .05);
            Label l31 = make_lable_salary_bady(allDb.get_count_of_punish(id.get(i), 2) + "", .05);
            Label l41 = make_lable_salary_bady(allDb.get_count_of_punish(id.get(i), 3) + "", .05);
            Label l51 = make_lable_salary_bady(allDb.get_count_of_punish(id.get(i), 4) + "", .045);
            Label l61 = make_lable_salary_bady(allDb.get_count_of_punish(id.get(i), 5) + "", .11);
            Label l71 = make_lable_salary_bady(allDb.get_count_of_punish_of_attend(id.get(i)) + "", .045);
            Label l81 = make_lable_salary_bady(allDb.get_count_of_punish_of_attend_re(id.get(i)) + "", .06);
            Label lbouns1 = make_lable_salary_bady(allDb.get_bouns_id(id.get(i)) + "", .03);
            Label main_salary1 = make_lable_salary_bady(get_main_salary(id.get(i)) + "", .05);
            Label l91 = make_lable_salary_bady(get_all_salary(id.get(i)) + "", .06);

            allDb.DB_close();
            HBox title1 = new HBox();
            title1.setStyle("-fx-background-color: #ffe6f9;");
            title1.setPrefSize(1000, 0);
            title1.setMaxSize(10000, 10000);
            title1.getChildren().addAll(l91, lbouns1, l81, l71, l61, l51, l41, l31, l21, main_salary1, l11f, l11s, l11, name1, num1);
            title1.setAlignment(Pos.CENTER_RIGHT);
            title1.setId(id.get(i) + "");
            title1.setOnMouseEntered(event -> {
                title1.setStyle("-fx-background-color :#b6bec2");
            });
            title1.setOnMouseExited(event -> {
                title1.setStyle("-fx-background-color :#ffe6f9 ");
            });
            title1.setOnMouseClicked((event) -> {
                coach_id_punish = Integer.parseInt(title1.getId());

                inf_c_name.setText(name1.getText());
                inf_c_id.setText(title1.getId());
                l_absent.setText(l71.getText());
                l_punish_late_1.setText(l21.getText());
                l_punish_late_5.setText(l31.getText());
                l_punish_glass.setText(l41.getText());
                l_punish_behavior.setText(l51.getText());
                l_punish_talk.setText(l61.getText());
                l_punish_re.setText(l81.getText());
                l_bouns.setText(lbouns1.getText());
                t_mount.setText(l91.getText());
                try {

                    l_punish_minus.setText(minus(coach_id_punish) + "");
                    l_punish_bouns.setText(bouns(coach_id_punish) + "");
                    allDb.DB_connection();
                    inf_c_level.setValue(allDb.coach_by_level(coach_id_punish));
                    inf_c_phone.setText(allDb.coach_by_phone(coach_id_punish));
                    allDb.DB_close();
                } catch (SQLException ex) {
                }
                p_c_punish.toFront();
            });
            table_salary.getChildren().add(title1);

            if (i > 18) {

                pane_table_salary.setPrefHeight(pane_table_salary.getPrefHeight() + bounds.getWidth() * .035);
            }
        }

        pane_table_salary.getChildren().clear();
        pane_table_salary.getChildren().add(table_salary);

    }

//////////////////////////////printer////////////
    public PageFormat getPageFormat(java.awt.print.PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double bodyHeight;
        bodyHeight = 8.0;

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

    private double bouns(int id) throws SQLException {
        double bouns = 0;

        allDb.DB_connection();

        double late1 = allDb.get_cost_for_late1() * allDb.get_count_of_punish(id, 1);
        double late5 = allDb.get_cost_for_late5() * allDb.get_count_of_punish(id, 2);
        double late_glass = allDb.get_cost_for_glass() * allDb.get_count_of_punish(id, 3);
        double late_beh = allDb.get_cost_for_Behavior() * allDb.get_count_of_punish(id, 4);
        double late_talk = allDb.get_cost_for_talk() * allDb.get_count_of_punish(id, 5);
        double late_absent = allDb.get_cost_for_absent() * allDb.get_count_of_punish_of_attend(id);

        allDb.DB_close();
        bouns = late1 + late5 + late_glass + late_beh + late_talk + late_absent;
        return bouns;
    }

    private double minus(int id) throws SQLException {
        double minus = 0;

        allDb.DB_connection();

        minus = allDb.get_cost_for_re() * allDb.get_count_of_punish_of_attend_re(id);
        minus += allDb.get_bouns_id(id);
        allDb.DB_close();
        return minus;
    }

    private int count_of_SATURDAY() {
        int c = 0;

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
////////////SATURDAY
        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                .collect(Collectors.toList());
        if (ldate.size() == 13) {
            ldate.remove(12);
        }
        for (int i = 0; i < ldate.size(); i++) {
            if (ldate.get(i).isBefore(currentdate)) {
                c++;
            }
        }

        return c;
    }

    private int count_of_Sunday() {
        int c = 0;

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
////////////SATURDAY
        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY
                || date.getDayOfWeek() == DayOfWeek.TUESDAY || date.getDayOfWeek() == DayOfWeek.THURSDAY)
                .collect(Collectors.toList());
        if (ldate.size() == 13) {
            ldate.remove(12);
        }
        for (int i = 0; i < ldate.size(); i++) {
            if (ldate.get(i).isBefore(currentdate)) {
                c++;
            }
        }

        return c;
    }

    private int count_of_Friday() {
        int c = 0;

        LocalDate currentdate = LocalDate.now();
        int currentYear = currentdate.getYear();
        Month currentMonth = currentdate.getMonth();
////////////SATURDAY
        List<LocalDate> ldate = IntStream.rangeClosed(1, YearMonth.of(currentYear, currentMonth).lengthOfMonth())
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> date.getDayOfWeek() == DayOfWeek.FRIDAY
                || date.getDayOfWeek() == DayOfWeek.SATURDAY)
                .collect(Collectors.toList());
        if (ldate.size() == 9) {
            ldate.remove(8);
        }
        for (int i = 0; i < ldate.size(); i++) {
            if (ldate.get(i).isBefore(currentdate)) {
                c++;
            }
        }

        return c;
    }

    private double get_all_salary(int id) throws SQLException {
        double all_s = get_main_salary(id);
        double abse = allDb.get_count_of_all_attend_coach(allDb.get_g_id_of_attend_coach(id)) * allDb.get_cost_for_coach_cost();
        double all_salary = all_s + minus(id) - (bouns(id) + abse);
        return all_salary;
    }

    private double get_main_salary(int id) throws SQLException {
        double sut = allDb.get_count_of_swimmer_with_caoch(id, 0) * count_of_SATURDAY() * allDb.get_cost_for_coach_cost();
        double sun = allDb.get_count_of_swimmer_with_caoch(id, 1) * count_of_Sunday() * allDb.get_cost_for_coach_cost();
        double fri = allDb.get_count_of_swimmer_with_caoch(id, 2) * count_of_Friday() * allDb.get_cost_for_coach_cost();
        double all_s = sut + sun + fri;
        return all_s;
    }

    private int Double(int size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
