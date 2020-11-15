/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.mysql.jdbc.Buffer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author asd
 */
public class BillPrintable implements Printable {

    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat month = new SimpleDateFormat("MMMM");
    SimpleDateFormat year = new SimpleDateFormat("YYYY");
    SimpleDateFormat t = new SimpleDateFormat("hh:mm:00");
    
    String name, phone, level, day, time, track, s_level, c_name;
    Date age;
    int group, id, cost, range;

    public BillPrintable(String name, String phone, Date age, String level, String s_level, String day, String time, String track, String c_name, int id, int cost, int range) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.level = level;
        this.s_level = s_level;
        this.day = day;
        this.time = time;
        this.track = track;
        this.c_name = c_name;
        this.id = id;
        this.cost = cost;
        this.range = range;

    }

    

    private BufferedImage i;

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {

        try {
            i = ImageIO.read(getClass().getResourceAsStream("/images/logo.png"));
        } catch (IOException ex) {
            System.out.println("ImageIO :" + ex);
        }
        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {
            if(day.equals("====")){
            time=t.format(now).toString();
            }
            
            LocalDate currentdate = LocalDate.now();
            day = currentdate.getDayOfWeek().toString();
            

            Graphics2D g2d = (Graphics2D) graphics;
            double width = pageFormat.getImageableWidth();
            g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

            //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
            try {
                int y = 20;
                int yShift = 20;
                int headerRectHeight = 15;

                g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
                g2d.drawImage(i, 10, 0, 120, 100, null);
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("مدرسة الجيل الجديدة", 130, y);
                y += yShift;
                g2d.drawString("الخاصة للغات", 150, y);
                y += yShift;
                g2d.drawString("الباجور بجوار المستشفى العام", 80, y);
                y += yShift;
                g2d.drawString("01008638141", 137, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString(" User Name  :    كابتن احمد فوزي  ", 10, y);
                y += yShift;
                g2d.drawString(" Print Date :  " + sdf.format(now) + "   ", 10, y);
                y += yShift;
                y += headerRectHeight;

                g2d.drawString(" ID    :    " + id, 10, y);
                y += yShift;
                g2d.drawString(" Name  :    " + name + "   ", 10, y);
                y += yShift;
                g2d.drawString(" Mobile:    " + phone + "    ", 10, y);
                y += yShift;
                g2d.drawString(" Season:    " + month.format(now), 10, y);
                y += yShift;
                g2d.drawString(" Year  :    " + year.format(now), 10, y);
                y += yShift;
                g2d.drawString(" Level :    " + s_level, 10, y);
                y += yShift;
                g2d.drawString(" line  :    " + track, 10, y);
                y += yShift;
                g2d.drawString(" Day   :    " + day, 10, y);
                y += yShift;
                g2d.drawString(" Time  :    " + time, 10, y);
                y += yShift;
                g2d.drawString(" Coach :    " + c_name, 10, y);
                y += yShift;
                g2d.drawString(" Range :    " + range + " Days", 10, y);
                y += yShift;
                g2d.drawString(" Cost  :    " + cost + " LE", 10, y);
                y += yShift;

                g2d.drawString("-------------------------------------", 10, y);
                y += headerRectHeight;

                g2d.drawString("*************************************", 10, y);
                y += yShift;
                g2d.drawString("       THANK YOU COME AGAIN            ", 10, y);
                y += yShift;
                g2d.drawString("*************************************", 10, y);
                y += yShift;
                g2d.drawString("    SOFTWARE BY:Eng MOSTAFA SAMY          ", 10, y);
                y += yShift;
                g2d.drawString("         CONTACT: 01010824193       ", 10, y);
                y += yShift;

            } catch (Exception e) {
                e.printStackTrace();
            }

            result = PAGE_EXISTS;
        }
        return result;
    }

}
