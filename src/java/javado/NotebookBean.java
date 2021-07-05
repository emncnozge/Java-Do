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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emin Can
 */
@Named(value = "notebookBean")
@SessionScoped
public class NotebookBean implements Serializable {

    /**
     * Creates a new instance of notebookBean
     */
    public NotebookBean() {
    }

    private int userID;
    private String name;
    private String createddate;
    private String lastedited;
    private String isdeleted;
    private int notebookid;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getLastedited() {
        return lastedited;
    }

    public void setLastedited(String lastedited) {
        this.lastedited = lastedited;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(String isdeleted) {
        this.isdeleted = isdeleted;
    }

    public int getNotebookid() {
        return notebookid;
    }

    public void setNotebookid(int notebookid) {
        this.notebookid = notebookid;
    }

    public HashMap getNotebooks(int userid) throws SQLException {
        HashMap<String, ArrayList<String>> notebooks = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> createddates = new ArrayList<>();
        ArrayList<String> lastediteds = new ArrayList<>();
        ArrayList<String> isdeleteds = new ArrayList<>();
        ArrayList<String> notebookids = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTEBOOK WHERE USERID=" + userid + " AND ISDELETED=FALSE");
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                names.add(rs.getString("NOTEBOOKNAME"));
                Date createddate1 = rs.getDate("CREATEDDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
                String createddate2 = dateFormat.format(createddate1);
                createddates.add(createddate2);
                Date lastedited1 = rs.getDate("LASTEDITED");
                String lastedited2 = dateFormat.format(lastedited1);
                lastediteds.add(lastedited2);
                isdeleteds.add((rs.getString("ISDELETED")));
                notebookids.add(rs.getString("NOTEBOOKID"));
            }
            notebooks.put("adlar", names);
            notebooks.put("olusturmalar", createddates);
            notebooks.put("duzenlemeler", lastediteds);
            notebooks.put("silinmeler", isdeleteds);
            notebooks.put("idler", notebookids);

        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return notebooks;
    }

    public HashMap getDeletedNotebooks(int userid) throws SQLException {
        HashMap<String, ArrayList<String>> notebooks = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> createddates = new ArrayList<>();
        ArrayList<String> lastediteds = new ArrayList<>();
        ArrayList<String> isdeleteds = new ArrayList<>();
        ArrayList<String> notebookids = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTEBOOK WHERE USERID=" + userid + " AND ISDELETED=TRUE");
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                names.add(rs.getString("NOTEBOOKNAME"));
                Date createddate1 = rs.getDate("CREATEDDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                String createddate2 = dateFormat.format(createddate1);
                createddates.add(createddate2);
                Date lastedited1 = rs.getDate("LASTEDITED");
                String lastedited2 = dateFormat.format(lastedited1);
                lastediteds.add(lastedited2);
                isdeleteds.add((rs.getString("ISDELETED")));
                notebookids.add(rs.getString("NOTEBOOKID"));
            }
            notebooks.put("adlar", names);
            notebooks.put("olusturmalar", createddates);
            notebooks.put("duzenlemeler", lastediteds);
            notebooks.put("silinmeler", isdeleteds);
            notebooks.put("idler", notebookids);

        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return notebooks;
    }

    public String getNotebook(int notebookId, boolean redirect) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTEBOOK WHERE NOTEBOOKID=" + notebookId);
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                userID = rs.getInt("USERID");
                notebookid = (rs.getInt("NOTEBOOKID"));
                name = rs.getString("NOTEBOOKNAME");
                Date createddate1 = rs.getDate("CREATEDDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
                createddate = dateFormat.format(createddate1);
                Date lastedited1 = rs.getDate("LASTEDITED");
                lastedited = dateFormat.format(lastedited1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        if (redirect) {
            return "notebook?faces-redirect=true";
        }
        return null;
    }

    public String addNotebook(int userid) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("INSERT INTO NOTEBOOK (USERID,NOTEBOOKNAME,CREATEDDATE,LASTEDITED,ISDELETED) VALUES(?,?,?,?,?)");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDateTime now = LocalDateTime.now();
            p.setInt(1, userid);
            p.setString(2, name);
            p.setString(3, dtf.format(now));
            p.setString(4, dtf.format(now));
            p.setBoolean(5, false);
            p.executeUpdate();
            NotebookCount();
        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "notebooks?faces-redirect=true";
    }

    public String updateNotebook(int notebookId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            PreparedStatement p = con.prepareStatement("UPDATE NOTEBOOK SET NOTEBOOKNAME=?,LASTEDITED=? WHERE NOTEBOOKID=?");
            p.setString(1, name);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDateTime now = LocalDateTime.now();
            p.setString(2, dtf.format(now));
            p.setInt(3, notebookId);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "notebooks?faces-redirect=true";
    }

    public String deleteNotebook(int notebookId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE NOTEBOOK SET ISDELETED=TRUE WHERE NOTEBOOK.NOTEBOOKID=" + notebookId);
            p.executeUpdate();
            p = con.prepareStatement("UPDATE NOTE SET ISDELETED=TRUE WHERE NOTE.NOTEBOOKID=" + notebookId);
            p.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        NotebookCount();
        return "notebooks?faces-redirect=true";

    }

    public String deleteNotebookCompletely(int notebookId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("DELETE FROM NOTE WHERE NOTE.NOTEBOOKID=" + notebookId);
            p.executeUpdate();
            p = con.prepareStatement("DELETE FROM NOTEBOOK WHERE NOTEBOOK.NOTEBOOKID=" + notebookId);
            p.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        NotebookCount();
        return "deletednotebooks?faces-redirect=true";

    }

    public String restoreNotebook(int notebookId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE NOTEBOOK SET ISDELETED=FALSE AND LASTEDITED=? WHERE NOTEBOOKID=" + notebookId);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDateTime now = LocalDateTime.now();
            p.setString(1, dtf.format(now));
            p.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        NotebookCount();
        return "deletednotebooks?faces-redirect=true";
    }

    public void NotebookCount() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            PreparedStatement p = con.prepareStatement("SELECT COUNT(NOTEBOOKID) AS COUNTER FROM NOTEBOOK WHERE ISDELETED=FALSE AND USERID=" + userID);
            ResultSet rs;
            rs = p.executeQuery();
            rs.next();
            p = con.prepareStatement("UPDATE USERS SET NOTEBOOKCOUNT=? WHERE ISDELETED=FALSE AND USERID=" + userID);
            p.setInt(1, rs.getInt("COUNTER"));
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NotebookBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
    }
}
