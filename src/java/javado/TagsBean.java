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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emin Can
 */
@Named(value = "tagsBean")
@SessionScoped
public class TagsBean implements Serializable {

    /**
     * Creates a new instance of TagsBean
     */
    public TagsBean() {
    }
    int tagID;
    int redirecttagID;
    String redirecttagName;
    int userID;
    String tagName;
    boolean isDeleted;

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public int getRedirecttagID() {
        return redirecttagID;
    }

    public void setRedirecttagID(int redirecttagID) {
        this.redirecttagID = redirecttagID;
    }

    public String getRedirecttagName() {
        return redirecttagName;
    }

    public void setRedirecttagName(String redirecttagName) {
        this.redirecttagName = redirecttagName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ArrayList getTagIDs(int userID) throws SQLException {
        ArrayList<Integer> tagIDs = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM TAG WHERE ISDELETED=FALSE AND USERID=" + userID);
            ResultSet rs;
            rs = p.executeQuery();
            while (rs.next()) {
                tagIDs.add(rs.getInt("TAGID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return tagIDs;
    }

    public HashMap getAlltags(int userid) throws SQLException {
        HashMap<String, ArrayList<String>> tags = new HashMap<>();
        ArrayList<String> tagids = new ArrayList<>();
        ArrayList<String> tagnames = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM TAG WHERE ISDELETED=FALSE AND USERID=" + userid);
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                tagids.add(rs.getString("TAGID"));
                tagnames.add(rs.getString("TAGNAME"));
            }
            tags.put("tagidleri", tagids);
            tags.put("tagadlari", tagnames);

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return tags;
    }

    public ArrayList getDeletedTagIDs(int userID) throws SQLException {
        ArrayList<Integer> tagIDs = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM TAG WHERE ISDELETED=TRUE AND USERID=" + userID);
            ResultSet rs;
            rs = p.executeQuery();
            while (rs.next()) {
                tagIDs.add(rs.getInt("TAGID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return tagIDs;
    }

    public String getTag(int userid, int tagid, boolean redirect) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM TAG WHERE USERID=" + userid + " AND TAGID=" + tagid);
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                tagID = (rs.getInt("TAGID"));
                userID = (rs.getInt("USERID"));
                tagName = (rs.getString("TAGNAME"));
                isDeleted = (rs.getBoolean("ISDELETED"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        if (!redirect) {
            return null;
        } else {
            return "tagedit.xhtml?faces-redirect=true";
        }
    }

    public String addTag(int userID) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("INSERT INTO TAG (USERID,TAGNAME,ISDELETED)"
                    + "VALUES(?,?,?)");
            p.setInt(1, userID);
            p.setString(2, tagName);
            p.setBoolean(3, false);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            con.close();
        }
        return "tags?faces-redirect=true";
    }

    public String updateTag(int tagID) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE TAG SET TAGNAME=? WHERE ISDELETED=FALSE AND TAGID=?");
            p.setString(1, tagName);
            p.setInt(2, tagID);
            System.out.println(tagName + tagID);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "tags.xhtml?faces-redirect=true";
    }
    
    public String deleteTag(int tagID) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE TAG SET ISDELETED=TRUE WHERE TAGID=" + tagID);
            p.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "tags.xhtml?faces-redirect=true";
    }

    public String deleteTagCompletely(int tagId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("DELETE FROM TAG WHERE TAGID=" + tagId);
            p.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "deletedtags?faces-redirect=true";

    }

    public String restoreTag(int tagId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE TAG SET ISDELETED=FALSE WHERE TAGID=" + tagId);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "deletedtags?faces-redirect=true";
    }

    public String redirect(int tagid) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM TAG WHERE ISDELETED=FALSE AND TAGID=" + tagid);
            ResultSet rs;
            rs = p.executeQuery();
            rs.next();
            redirecttagName = (rs.getString("TAGNAME"));

        } catch (SQLException ex) {
            Logger.getLogger(TagsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        redirecttagID = tagid;
        return "notesfiltered.xhtml?faces-redirect=true";
    }
}
