/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javado;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emin Can
 */
@Named(value = "remindersBean")
@SessionScoped
public class RemindersBean implements Serializable {

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getReminderName() {
        return reminderName;
    }

    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public void setReminderContent(String reminderContent) {
        this.reminderContent = reminderContent;
    }

    public String getReminderColor() {
        return reminderColor;
    }

    public void setReminderColor(String reminderColor) {
        this.reminderColor = reminderColor;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    private int reminderID;
    private int userID;
    private String reminderName;
    private String reminderDate;
    private String reminderContent;
    private String reminderColor;
    private boolean isDeleted;

    public RemindersBean() {
    }

    public ArrayList getReminderIDs(int userid, boolean limited) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDateTime now = LocalDateTime.now();
        ArrayList<Integer> reminderIDs = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p;
            if (!limited) {
                p = con.prepareStatement("SELECT * FROM REMINDER WHERE ISDELETED=FALSE AND USERID=" + userid + " ORDER BY REMINDERDATE");
            } else {
                p = con.prepareStatement("SELECT * FROM REMINDER WHERE ISDELETED=FALSE AND USERID=" + userid + " AND REMINDERDATE>='" + dtf.format(now) + "' ORDER BY REMINDERDATE FETCH FIRST 5 ROWS ONLY");
            }
            ResultSet rs;
            rs = p.executeQuery();
            while (rs.next()) {
                reminderIDs.add(rs.getInt("REMINDERID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RemindersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return reminderIDs;
    }

    public ArrayList getDeletedReminderIDs(int userid) throws SQLException {
        ArrayList<Integer> reminderIDs = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            PreparedStatement p;

            p = con.prepareStatement("SELECT * FROM REMINDER WHERE ISDELETED=TRUE AND USERID=" + userid + " ORDER BY REMINDERDATE");

            ResultSet rs;
            rs = p.executeQuery();
            while (rs.next()) {
                reminderIDs.add(rs.getInt("REMINDERID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RemindersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return reminderIDs;
    }

    public String getReminder(int userid, int reminderid, boolean redirect) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM REMINDER WHERE USERID=" + userid + " AND REMINDERID=" + reminderid);
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                reminderID = (rs.getInt("REMINDERID"));
                userID = (rs.getInt("USERID"));
                reminderName = (rs.getString("REMINDERNAME"));
                Date reminderdate1 = rs.getDate("REMINDERDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                reminderDate = dateFormat.format(reminderdate1);
                reminderContent = (rs.getString("REMINDERCONTENT"));
                reminderColor = (rs.getString("REMINDERCOLOR"));
                isDeleted = (rs.getBoolean("ISDELETED"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(RemindersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        if (!redirect) {
            return null;
        } else {
            return "reminderedit.xhtml?faces-redirect=true";
        }
    }
    
    public String addReminder(int userid) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("INSERT INTO REMINDER (USERID,REMINDERNAME,REMINDERDATE,REMINDERCONTENT,REMINDERCOLOR,ISDELETED)"
                    + "VALUES(?,?,?,?,?,?)");
            p.setInt(1, userid);
            p.setString(2, reminderName);
            p.setString(3, reminderDate);
            p.setString(4, reminderContent);
            p.setString(5, reminderColor);
            p.setBoolean(6, false);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RemindersBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } finally {
            con.close();
        }
        return "reminders?faces-redirect=true";
    }

    public String updateReminder(int reminderid) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE REMINDER SET REMINDERNAME=?,REMINDERCONTENT=?,REMINDERDATE=?, REMINDERCOLOR=? WHERE REMINDERID=?");
            p.setString(1, reminderName);
            p.setString(2, reminderContent);
            p.setString(3, reminderDate);
            p.setString(4, reminderColor);

            p.setInt(5, reminderid);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RemindersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "reminders?faces-redirect=true";
    }

    public String deleteReminder(int reminderid) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE REMINDER SET ISDELETED=TRUE WHERE REMINDERID=" + reminderid);
            p.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(RemindersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "reminders?faces-redirect=true";

    }

    public String deleteReminderCompletely(int reminderId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("DELETE FROM REMINDER WHERE REMINDERID=" + reminderId);
            p.executeUpdate();

        } catch (SQLException ex) {
            return ex.getMessage();
        } finally {
            con.close();
        }
        return "deletedreminders?faces-redirect=true";

    }

    public String restoreReminder(int reminderId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE REMINDER SET ISDELETED=FALSE WHERE REMINDERID=" + reminderId);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RemindersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "deletedreminders?faces-redirect=true";
    }

    
}
