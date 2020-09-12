/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.*;
import java.awt.Button;
import static java.awt.SystemColor.menu;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import javafx.geometry.Rectangle2D;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import static javafx.scene.paint.Color.rgb;
import javafx.stage.Screen;
import model.all_information_for_group;
import model.swimmer;
import service.DB;

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
    private AnchorPane anchorpane;
    @FXML
    private VBox p_list;
    @FXML
    private StackPane big_Stack;
    @FXML
    private Pane p_home;
    @FXML
    private JFXComboBox<Time> combobox_all_group;
    @FXML
    private Label l_rotate;
    @FXML
    private ScrollPane scrooll;

    @FXML
    private AnchorPane pane_table;

    public void swi(ActionEvent actionEvent) {
//        if (actionEvent.getSource() == btnCustomers) {
//            //   pnlCustomer.setStyle("-fx-background-color : #1620A1");
//        }
//         if (actionEvent.getSource() == btnAddSiwmer) {
//            //   pnlCustomer.setStyle("-fx-background-color : #1620A1");
//        }
//        if (actionEvent.getSource() == btnMenus) {
//           
//        }
//        if (actionEvent.getSource() == btnOverview) {
//           // pnlOverview.setStyle("-fx-background-color : #02030A");
//        }
//        if (actionEvent.getSource() == btnSettings) {
//        }
    }

    DB allDb;
    Rectangle2D bounds;
    List<all_information_for_group> id ;
    List<Integer> all_g_id;
    List<List<swimmer>> t ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ///////////////////////initialize//////////////
        allDb = new DB();
        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        combobox_all_group.setStyle("-fx-font-size: 20px;");
        table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
        table.setLayoutX(0);
        table.setLayoutY(0);
        table.setStyle("-fx-background-color:   #fff;");
        scrooll.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
        pane_table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
        id = new ArrayList<all_information_for_group>();
        all_g_id=new ArrayList<Integer>();
        t = new ArrayList<List<swimmer>>();
        
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
            allDb.DB_close();

            combobox_all_group.setOnAction((event) -> {
                try {
                    allDb.DB_connection();
                    id = allDb.get_all_group_with_time(combobox_all_group.getValue());
                    all_g_id.clear();
                    for(int i=0;i<id.size();i++){
                    all_g_id.add(id.get(i).getG_id());
                    }
                    System.out.println(all_g_id);
                    t=allDb.get_swimmerWithgroup(all_g_id);
                    //System.out.println(t.get(0).get(0));
                    pane_table.getChildren().clear();
                    table.getChildren().clear();
                    pane_table.setPrefSize(bounds.getWidth() * 0.801, bounds.getHeight() * 0.81);
                    BuildHome(id,t);
                    allDb.DB_close();
                } catch (SQLException ex) {
                    System.out.println("initialize" + ex);
                }

            });
            //BuildHome();

//       PrinterJob job = PrinterJob.createPrinterJob();
//    if (job != null) {
//        boolean success = job.printPage(big_Stack);
//        if (success) {
//            System.out.println("PRINTING FINISHED");
//            job.endJob();
//        }
//    }
        } catch (SQLException ex) {
            System.out.println("initialize" + ex);
        }

    }

    VBox table = new VBox();

    private void BuildHome(List<all_information_for_group> id,List<List<swimmer>> all_S) throws SQLException {

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
        title.setStyle("-fx-background-color:   #f00;");
        title.setPrefSize(1000, 0);
        title.setMaxSize(10000, 10000);
        title.getChildren().addAll(notes, level, l12, l11, l10, l9, l8, l7, l6, l5, l4, l3, l2, l1, coach, name, num);
        title.setAlignment(Pos.CENTER_RIGHT);
        table.getChildren().add(title);

        for (int z = 0; z < id.size(); z++) {
            HBox row = new HBox();
            row.setStyle("-fx-background-color:   #f00;");
            row.setAlignment(Pos.CENTER_RIGHT);

            VBox all_num = new VBox();
            all_num.setStyle("-fx-background-color:   #00f;");

            for (int i = 0; i < 8; i++) {
                Label l = make_lable("" + (i + 1), .039);
                all_num.getChildren().add(l);
            }

            VBox Swimer = new VBox();
            Swimer.setStyle("-fx-background-color:   #00f;");
            for (int i = 0; i < all_S.get(z).size(); i++) {
                Label la = make_lable(all_S.get(z).get(i).getName(), 0.1735);
                Swimer.getChildren().add(la);

            }

            Label la = new Label(id.get(z).getName() + " \n " + id.get(z).getTrack() + "");
            la.setPrefWidth(bounds.getWidth() * 0.07);
            la.setMaxHeight(1000);
            la.setStyle("-fx-font-size: 16px;-fx-background-color:#0f0;-fx-border-color:#000;");
            la.setAlignment(Pos.CENTER);

            HBox all_att = new HBox();
            for (int j = 0; j < 12; j++) {
                VBox att = new VBox();
                att.setStyle("-fx-background-color:   #00f;");
                for (int i = 0; i < 8; i++) {
                    JFXCheckBox ch = new JFXCheckBox();
                    ch.setCheckedColor(rgb(42, 115, 255));
                    ch.setTextFill(rgb(255, 255, 255));
                    ch.setStyle("-fx-font-size: 16px;-fx-background-color:#0f0;-fx-border-color:#000;");
                    //ch.setSelected(true);
                    ch.setPrefSize(bounds.getWidth() * .023, 0);
                    ch.setAlignment(Pos.CENTER_RIGHT);
                    att.getChildren().add(ch);
                }
                all_att.getChildren().add(att);
            }

            VBox all_level = new VBox();
            all_level.setStyle("-fx-background-color:   #00f;");
            for (int i = 0; i < 8; i++) {
                Label l = make_lable(id.get(z).getG_level(), .05);
                all_level.getChildren().add(l);

            }

            VBox all_nots = new VBox();
            all_nots.setStyle("-fx-background-color:   #00f;");
            for (int i = 0; i < 8; i++) {

                TextField t = new TextField();
                t.setPrefWidth(bounds.getWidth() * .19);
                t.setStyle("-fx-font-size: 12px;-fx-background-color:#0f0;-fx-border-color:#000;");
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
        l.setStyle("-fx-font-size: 16px;-fx-background-color:#0f0;-fx-border-color:#000;");
        l.setAlignment(Pos.CENTER);

        return l;
    }
}
