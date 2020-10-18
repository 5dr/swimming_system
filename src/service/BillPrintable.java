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

    String name,phone,level,day,time,track ,s_level;
    Date age;
    int group,id;

    public BillPrintable(String name, String phone, Date age, String level,String s_level, String day, String time, String track, int id) {
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.level = level;
           this.s_level = s_level;
        this.day = day;
        this.time = time;
        this.track = track;
        this.id = id;
 
    }


 
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat month=new SimpleDateFormat("MMMM");
            SimpleDateFormat year=new SimpleDateFormat("YYYY");
    

    private BufferedImage i;

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        
        try {
            i = ImageIO.read(getClass().getResourceAsStream("/images/logo.png"));
        } catch (IOException ex) {
            System.out.println("ImageIO :"+ex);
        }
        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {

            Graphics2D g2d = (Graphics2D) graphics;
            double width = pageFormat.getImageableWidth();
            g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

            //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
            try {
                int y = 20;
                int yShift = 10;
                int headerRectHeight = 15;
  
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
                g2d.drawImage(i, 10, 61, 100, 50, null);
                y += yShift + 30;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
              //  g2d.drawString(" ليالى الحلمية               ", 12, y);
                y += yShift;
               // g2d.drawString("   الباجور المنوفيه طريق مصر         ", 12, y);
                y += yShift;
               // g2d.drawString(" امام مدرسة التجارة             ", 12, y);
                y += yShift;
               // g2d.drawString("                01093696146      ", 12, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString(" User Name :    كابتن احمد فوزي       ", 10, y);
                y += yShift;
                g2d.drawString(" Print Date :  "+sdf.format(now)+"   ", 10, y);
                y += yShift;
                y += headerRectHeight;
                
                g2d.drawString(" ID :          "+id, 10, y);
                y += yShift;
                g2d.drawString(" Name :        "+name+"   ", 10, y);
                y += yShift;
                g2d.drawString(" Mobile :      "+phone+"    ", 10, y);
                y += yShift;
                g2d.drawString(" Season :      "+month.format(now)+"    ", 10, y);
                y += yShift;
                g2d.drawString(" Year :        "+year.format(now)+" ", 10, y);
                y += yShift;
                g2d.drawString(" Level :           "+level, 10, y);
                y += yShift;
                g2d.drawString(" lane :           "+track, 10, y);
                y += yShift;
                g2d.drawString(" Day :           "+day, 10, y);
                y += yShift;
                g2d.drawString(" Time :           "+time, 10, y);
                y += yShift;
                 g2d.drawString(" level_swimmer :           "+s_level, 10, y);
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
