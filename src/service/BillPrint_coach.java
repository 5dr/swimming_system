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
public class BillPrint_coach implements Printable {

    String  m_late1, m_late5, m_absent, m_glass, m_behavior, m_talk, m_re;
    String name;
    int late1, late5, absent, glass, behavior, talk, re, id,bouns;
    double  salary;
    public BillPrint_coach(String m_late1, String m_late5, String m_absent, String m_glass, String m_behavior, String m_talk, String m_re, String name, int late1, int late5, int absent, int glass, int behavior, int talk, int re, int bouns, int id, double salary) {
        this.m_late1 = m_late1;
        this.m_late5 = m_late5;
        this.m_absent = m_absent;
        this.m_glass = m_glass;
        this.m_behavior = m_behavior;
        this.m_talk = m_talk;
        this.m_re = m_re;
        this.name = name;
        this.late1 = late1;
        this.late5 = late5;
        this.absent = absent;
        this.glass = glass;
        this.behavior = behavior;
        this.talk = talk;
        this.re = re;
        this.bouns = bouns;
        this.id = id;
        this.salary = salary;
    }

    

    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat month = new SimpleDateFormat("MMMM");
    SimpleDateFormat year = new SimpleDateFormat("YYYY");

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
                g2d.drawString(" Print Date :  " + sdf.format(now) + "   ", 10, y);
                y += yShift;
                y += headerRectHeight;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString(" ID    :    " + id, 10, y);
                y += yShift;
                g2d.drawString(" Name  :    " + name + "   ", 10, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("الخصومات:", 185, y);
                y += yShift;
                 g2d.drawString(" العقوبة      " +"عدد المرات"+ "    " + " القيمة", 10, y);
                y += yShift+5;
                g2d.drawString(" تأخير    1 دقيقة:  " + m_late1 + "            " + late1+" ", 10, y);
                y += yShift;
                g2d.drawString("تأخير     5دقايق :  " + m_late5 + "            " + late5, 10, y);
                y += yShift;
                g2d.drawString("   النضاره+البونيه:  " + m_glass + "            " + glass, 10, y);
                y += yShift;
                g2d.drawString(" السلوك           :  " + m_behavior + "            " + behavior, 10, y);
                y += yShift;
                g2d.drawString("التحدث مع ولي الامر:  " + m_talk + "            " + talk, 10, y);
                y += yShift;
                g2d.drawString(" الغياب           :  " + m_absent + "            " + absent, 10, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("الاضافات:", 170, y);
                y += yShift;
                g2d.drawString(" الاحتياطى         :  " + m_re + "            " + re, 10, y);
                y += yShift;
                g2d.drawString("البونص : " + bouns, 110, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString(" القيمة بعد الخصم و الاضافة:  "+ salary, 20, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString(" Season: " + month.format(now)+"  "+ year.format(now), 10, y);
                y += yShift;
                g2d.drawString("*************************************", 10, y);
                y += yShift;
               

            } catch (Exception e) {
                e.printStackTrace();
            }

            result = PAGE_EXISTS;
        }
        return result;
    }

}
