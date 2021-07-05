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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emin Can
 */
@Named(value = "noteBean")
@SessionScoped
public class NoteBean implements Serializable {

    /**
     * Creates a new instance of NoteBean
     */
    private int noteID;
    private int notebookID;
    private int userID;
    private String noteName;
    private String title;
    private List<String> tags;
    private String notecontent;
    private String createddate;
    private String lastedited;
    private boolean isdeleted;
    private String search;

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getNotebookID() {
        return notebookID;
    }

    public void setNotebookID(int notebookID) {
        this.notebookID = notebookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getNotecontent() {
        return notecontent;
    }

    public void setNotecontent(String notecontent) {
        this.notecontent = notecontent;
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

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public NoteBean() {
        this.isdeleted = false;
    }

    public HashMap getNotes(int userid, int notebookid) throws SQLException {
        HashMap<String, ArrayList<String>> notes = new HashMap<>();
        ArrayList<String> noteids = new ArrayList<>();
        ArrayList<String> isdeleteds = new ArrayList<>();
        ArrayList<String> notebookids = new ArrayList<>();
        ArrayList<String> notenames = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> notecontents = new ArrayList<>();
        ArrayList<String> createddates = new ArrayList<>();
        ArrayList<String> lastediteds = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTE WHERE ISDELETED=FALSE AND USERID=" + userid + " AND NOTEBOOKID=" + notebookid);
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                noteids.add(rs.getString("NOTEID"));
                notenames.add(rs.getString("NOTENAME"));
                notebookids.add(rs.getString("NOTEBOOKID"));
                titles.add(rs.getString("TITLE"));
                notecontents.add(rs.getString("NOTECONTENT"));
                Date createddate1 = rs.getDate("CREATEDDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
                createddates.add(dateFormat.format(createddate1));
                Date lastedited1 = rs.getDate("LASTEDITED");
                lastediteds.add(dateFormat.format(lastedited1));
                isdeleteds.add((rs.getString("ISDELETED")));
            }
            notes.put("notebookidler", notebookids);
            notes.put("notadlari", notenames);
            notes.put("notbasliklari", titles);
            notes.put("noticerikleri", notecontents);
            notes.put("notolusturmatarihleri", createddates);
            notes.put("notduzenlemetarihleri", lastediteds);
            notes.put("notsilinmeleri", isdeleteds);
            notes.put("notidleri", noteids);

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return notes;
    }

    public HashMap getAllnotes(int userid) throws SQLException {
        HashMap<String, ArrayList<String>> notes = new HashMap<>();
        ArrayList<String> noteids = new ArrayList<>();
        ArrayList<String> isdeleteds = new ArrayList<>();
        ArrayList<String> notebookids = new ArrayList<>();
        ArrayList<String> notenames = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> notecontents = new ArrayList<>();
        ArrayList<String> createddates = new ArrayList<>();
        ArrayList<String> lastediteds = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTE WHERE ISDELETED=FALSE AND USERID=" + userid);
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                noteids.add(rs.getString("NOTEID"));
                notenames.add(rs.getString("NOTENAME"));
                notebookids.add(rs.getString("NOTEBOOKID"));
                titles.add(rs.getString("TITLE"));
                notecontents.add(rs.getString("NOTECONTENT"));
                Date createddate1 = rs.getDate("CREATEDDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
                createddates.add(dateFormat.format(createddate1));
                Date lastedited1 = rs.getDate("LASTEDITED");
                lastediteds.add(dateFormat.format(lastedited1));
                isdeleteds.add((rs.getString("ISDELETED")));
            }
            notes.put("notebookidler", notebookids);
            notes.put("notadlari", notenames);
            notes.put("notbasliklari", titles);
            notes.put("noticerikleri", notecontents);
            notes.put("notolusturmatarihleri", createddates);
            notes.put("notduzenlemetarihleri", lastediteds);
            notes.put("notsilinmeleri", isdeleteds);
            notes.put("notidleri", noteids);

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return notes;
    }

    public HashMap getSearchnotes(int userid) throws SQLException {
        HashMap<String, ArrayList<String>> notes = new HashMap<>();
        ArrayList<String> noteids = new ArrayList<>();
        ArrayList<String> isdeleteds = new ArrayList<>();
        ArrayList<String> notebookids = new ArrayList<>();
        ArrayList<String> notenames = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> notecontents = new ArrayList<>();
        ArrayList<String> createddates = new ArrayList<>();
        ArrayList<String> lastediteds = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTE WHERE ISDELETED=FALSE AND USERID=" + userid + " AND (UPPER(NOTENAME) LIKE '%" + search.toUpperCase() + "%' OR UPPER(TITLE) LIKE '%" + search.toUpperCase() + "%' OR UPPER(NOTECONTENT) LIKE '%" + search.toUpperCase() + "%')");

            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                noteids.add(rs.getString("NOTEID"));
                notenames.add(rs.getString("NOTENAME"));
                notebookids.add(rs.getString("NOTEBOOKID"));
                titles.add(rs.getString("TITLE"));
                notecontents.add(rs.getString("NOTECONTENT"));
                Date createddate1 = rs.getDate("CREATEDDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
                createddates.add(dateFormat.format(createddate1));
                Date lastedited1 = rs.getDate("LASTEDITED");
                lastediteds.add(dateFormat.format(lastedited1));
                isdeleteds.add((rs.getString("ISDELETED")));
            }
            notes.put("notebookidler", notebookids);
            notes.put("notadlari", notenames);
            notes.put("notbasliklari", titles);
            notes.put("noticerikleri", notecontents);
            notes.put("notolusturmatarihleri", createddates);
            notes.put("notduzenlemetarihleri", lastediteds);
            notes.put("notsilinmeleri", isdeleteds);
            notes.put("notidleri", noteids);

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return notes;
    }

    public HashMap getDeletedNotes(int userid) throws SQLException {
        HashMap<String, ArrayList<String>> notes = new HashMap<>();
        ArrayList<String> noteids = new ArrayList<>();
        ArrayList<String> isdeleteds = new ArrayList<>();
        ArrayList<String> notebookids = new ArrayList<>();
        ArrayList<String> notenames = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> notecontents = new ArrayList<>();
        ArrayList<String> createddates = new ArrayList<>();
        ArrayList<String> lastediteds = new ArrayList<>();
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTE INNER JOIN NOTEBOOK ON NOTE.NOTEBOOKID=NOTEBOOK.NOTEBOOKID WHERE NOTEBOOK.ISDELETED=FALSE AND NOTE.ISDELETED=TRUE AND NOTE.USERID=" + userid);
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                noteids.add(rs.getString("NOTEID"));
                notenames.add(rs.getString("NOTENAME"));
                notebookids.add(rs.getString("NOTEBOOKID"));
                titles.add(rs.getString("TITLE"));
                notecontents.add(rs.getString("NOTECONTENT"));
                Date createddate1 = rs.getDate("CREATEDDATE");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
                createddates.add(dateFormat.format(createddate1));
                Date lastedited1 = rs.getDate("LASTEDITED");
                lastediteds.add(dateFormat.format(lastedited1));
                isdeleteds.add((rs.getString("ISDELETED")));
            }
            notes.put("notebookidler", notebookids);
            notes.put("notadlari", notenames);
            notes.put("notbasliklari", titles);
            notes.put("noticerikleri", notecontents);
            notes.put("notolusturmatarihleri", createddates);
            notes.put("notduzenlemetarihleri", lastediteds);
            notes.put("notsilinmeleri", isdeleteds);
            notes.put("notidleri", noteids);

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return notes;
    }

    public ArrayList getLastEditedNotes(int notebookid, int userid) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        PreparedStatement p = con.prepareStatement("SELECT NOTENAME FROM NOTE WHERE NOTEBOOKID =" + notebookid + "AND USERID=" + userid + "AND ISDELETED=FALSE ORDER BY NOTE.LASTEDITED DESC");
        ResultSet rs;
        rs = p.executeQuery();
        ArrayList<String> last = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (rs.next()) {
                last.add(rs.getString("NOTENAME"));
            }
        }
        con.close();
        return last;
    }

    public ArrayList getNotesFilterByTags(int userid, int tagid) throws SQLException {
        ArrayList<Integer> tagids = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        ArrayList<Integer> tmpnoteids = new ArrayList<>();
        ArrayList<Integer> noteids = new ArrayList<>();

        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTE WHERE ISDELETED=FALSE AND USERID=" + userid);
            ResultSet rs;
            rs = p.executeQuery();
            while (rs.next()) {
                tags.add(rs.getString("TAGS"));
                tmpnoteids.add(rs.getInt("NOTEID"));
            }

            p = con.prepareStatement("SELECT * FROM TAG WHERE ISDELETED=FALSE AND USERID=" + userid);

            rs = p.executeQuery();
            while (rs.next()) {
                tagids.add(rs.getInt("TAGID"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }

        for (int i = 0; i < tagids.size(); i++) {
            for (int j = 0; j < tags.size(); j++) {
                for (int k = 0; k < tags.get(j).split(",").length; k++) {
                    if ((tags.get(j).split(","))[k].equals(String.valueOf(tagids.get(i))) != false && tagids.get(i) == tagid) {
                        noteids.add(tmpnoteids.get(j));
                    }
                }
            }
        }
        return noteids;
    }

    public String getNote(int userid, int noteid, boolean redirect) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM NOTE WHERE NOTE.NOTEID=" + noteid + " AND NOTE.USERID=" + userid);
            ResultSet rs;
            rs = p.executeQuery();
            rs.next();
            this.tags = new ArrayList<>();
            for (int i = 1; i < rs.getString("TAGS").split(",").length; i++) {
                tags.add(rs.getString("TAGS").split(",")[i]);
            }
            userID = rs.getInt("USERID");
            noteID = Integer.parseInt(rs.getString("NOTEID"));
            noteName = rs.getString("NOTENAME");
            title = rs.getString("TITLE");
            notebookID = rs.getInt("NOTEBOOKID");
            notecontent = rs.getString("NOTECONTENT");
            Date createddate1 = rs.getDate("CREATEDDATE");
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            createddate = dateFormat.format(createddate1);
            Date lastedited1 = rs.getDate("LASTEDITED");
            lastedited = dateFormat.format(lastedited1);

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        if (redirect == true) {
            return "note?faces-redirect=true";
        } else {
            return null;
        }
    }

    public String addNote(int userid, int notebookid) throws SQLException {
        String tags2 = ",";
        for (int i = 0; i < tags.size(); i++) {
            tags2 += tags.get(i) + ",";
        }
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("INSERT INTO NOTE (NOTEBOOKID,USERID,NOTENAME,TITLE,TAGS,NOTECONTENT,CREATEDDATE,LASTEDITED,ISDELETED)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDateTime now = LocalDateTime.now();
            p.setInt(1, notebookid);
            p.setInt(2, userid);
            p.setString(3, noteName);
            p.setString(4, title);
            p.setString(5, tags2);
            p.setString(6, notecontent);
            p.setString(7, dtf.format(now));
            p.setString(8, dtf.format(now));
            p.setBoolean(9, false);
            p.executeUpdate();

            p = con.prepareStatement("UPDATE NOTEBOOK SET LASTEDITED=? WHERE NOTEBOOKID=?");
            p.setString(1, dtf.format(now));
            p.setInt(2, notebookid);
            p.executeUpdate();
            NoteCount();
        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "notebook?faces-redirect=true";
    }

    public void deleteNote(int noteId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDateTime now = LocalDateTime.now();
            PreparedStatement p = con.prepareStatement("UPDATE NOTE SET ISDELETED=TRUE,LASTEDITED=? WHERE NOTEID=" + noteId);
            p.setString(1, dtf.format(now));
            p.executeUpdate();
            p = con.prepareStatement("UPDATE NOTEBOOK SET LASTEDITED=? WHERE NOTEBOOKID=(SELECT NOTEBOOKID FROM NOTE WHERE NOTEID=?)");
            p.setString(1, dtf.format(now));
            p.setInt(2, noteId);
            p.executeUpdate();
            NoteCount();
        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
    }

    public String updateNote(int noteId) throws SQLException {
        String tags2 = ",";
        for (int i = 0; i < tags.size(); i++) {
            tags2 += tags.get(i) + ",";
        }
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE NOTE SET NOTENAME=?,TITLE=?,NOTECONTENT=?,LASTEDITED=?, TAGS=? WHERE NOTEID=?");
            p.setString(1, noteName);
            p.setString(2, title);
            p.setString(3, notecontent);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDateTime now = LocalDateTime.now();
            p.setString(4, dtf.format(now));
            p.setString(5, tags2);
            p.setInt(6, noteId);
            p.executeUpdate();
            p = con.prepareStatement("UPDATE NOTEBOOK SET LASTEDITED=? WHERE NOTEBOOKID=(SELECT NOTEBOOKID FROM NOTE WHERE ISDELETED=FALSE AND NOTEID=?)");
            p.setString(1, dtf.format(now));
            p.setInt(2, noteId);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "notes.xhtml?faces-redirect=true";
    }

    public String deleteNoteCompletely(int noteId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("DELETE FROM NOTE WHERE NOTEID=" + noteId);
            p.executeUpdate();
            NoteCount();
        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "deletednotes?faces-redirect=true";

    }

    public String restoreNote(int noteId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("UPDATE NOTE SET ISDELETED=FALSE, LASTEDITED=? WHERE NOTEID=" + noteId);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
            LocalDateTime now = LocalDateTime.now();
            p.setString(1, dtf.format(now));
            p.executeUpdate();
            p = con.prepareStatement("UPDATE NOTEBOOK SET LASTEDITED=? WHERE NOTEBOOKID=(SELECT NOTEBOOKID FROM NOTE WHERE NOTEID=?)");
            p.setString(1, dtf.format(now));
            p.setInt(2, noteId);
            p.executeUpdate();
            NoteCount();
        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "deletednotes?faces-redirect=true";
    }

    public String getNotebookName(int noteId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        PreparedStatement p = con.prepareStatement("SELECT NOTEBOOK.NOTEBOOKNAME FROM NOTEBOOK INNER JOIN NOTE ON NOTE.NOTEBOOKID=NOTEBOOK.NOTEBOOKID WHERE NOTEBOOK.ISDELETED=FALSE AND NOTE.ISDELETED=FALSE AND NOTE.NOTEID =" + noteId);
        ResultSet rs;
        rs = p.executeQuery();
        String val = "";
        if (rs.next()) {
            val = rs.getString("NOTEBOOKNAME");
        }
        con.close();
        return val;
    }

    public String tagName(int tagid) throws SQLException {

        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM TAG WHERE ISDELETED=FALSE AND TAGID=" + tagid);
            ResultSet rs;
            rs = p.executeQuery();
            rs.next();
            return rs.getString("TAGNAME");

        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return null;
    }

    public void NoteCount() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            PreparedStatement p = con.prepareStatement("SELECT COUNT(NOTEID) AS COUNTER FROM NOTE WHERE ISDELETED=FALSE AND USERID=" + userID);
            ResultSet rs;
            rs = p.executeQuery();
            rs.next();

            p = con.prepareStatement("UPDATE USERS SET NOTECOUNT=? WHERE ISDELETED=FALSE AND USERID=" + userID);
            p.setInt(1, rs.getInt("COUNTER"));
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
    }

}
