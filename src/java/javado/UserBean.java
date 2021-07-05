package javado;

import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private Integer userID;
    private String name;
    private String surname;
    private String borndate;
    private String email;
    private String password;
    private String registerDate;
    private int notebookCount;
    private int noteCount;
    private String lastLoginDate;
    private boolean isDeleted;
    private int success = 3;
    private String oldpassword;
    private String newpassword;
    private int pass = 0;

    public UserBean() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBorndate() {
        return borndate;
    }

    public void setBorndate(String borndate) {
        this.borndate = borndate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getNotebookCount() {
        return notebookCount;
    }

    public void setNotebookCount(int notebookCount) {
        this.notebookCount = notebookCount;
    }

    public int getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public String login() throws IOException, SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            PreparedStatement p = con.prepareStatement("SELECT * FROM USERS WHERE EMAIL=? AND PASSWORD=? AND ISDELETED=FALSE");
            p.setString(1, email);
            p.setString(2, password);
            ResultSet rs;
            rs = p.executeQuery();
            if (rs.next()) {
                setSuccess(1);
                userID = (rs.getInt("USERID"));
                name = (rs.getString("NAME"));
                surname = (rs.getString("SURNAME"));
                borndate = rs.getString("BORNDATE").split("-")[2] + "." + rs.getString("BORNDATE").split("-")[1] + "." + rs.getString("BORNDATE").split("-")[0];
                email = (rs.getString("EMAIL"));
                password = (rs.getString("PASSWORD"));
                registerDate = rs.getString("REGISTERDATE").split("-")[2] + "." + rs.getString("REGISTERDATE").split("-")[1] + "." + rs.getString("REGISTERDATE").split("-")[0];
                notebookCount = (rs.getInt("NOTEBOOKCOUNT"));
                noteCount = (rs.getInt("NOTECOUNT"));
                lastLoginDate = rs.getString("LASTLOGINDATE").split("-")[2] + "." + rs.getString("LASTLOGINDATE").split("-")[1] + "." + rs.getString("LASTLOGINDATE").split("-")[0];
                isDeleted = (rs.getBoolean("ISDELETED"));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
                LocalDateTime now = LocalDateTime.now();
                PreparedStatement p2 = con.prepareStatement("UPDATE USERS SET LASTLOGINDATE=? WHERE USERID=" + userID);
                p2.setString(1, dtf.format(now));
                p2.executeUpdate();
                return "anasayfa?faces-redirect=true";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        setSuccess(0);
        return "login?faces-redirect=true";

    }

    public String logout() throws SQLException {
        setUserID(null);
        setName(null);
        setSurname(null);
        setBorndate(null);
        setEmail(null);
        setPassword(null);
        setRegisterDate(null);
        setNotebookCount(0);
        setNoteCount(0);
        setLastLoginDate(null);
        setIsDeleted(false);
        setSuccess(3);
        return "login?faces-redirect=true";
    }

    public String register() throws SQLException {
        Connection con;
        con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            PreparedStatement p = con.prepareStatement("SELECT * FROM USERS WHERE UPPER(EMAIL)='" + email.toUpperCase() + "'");
            ResultSet rs;
            rs = p.executeQuery();
            PreparedStatement s;

            if (!rs.next()) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
                LocalDateTime now = LocalDateTime.now();
                s = con.prepareStatement("INSERT INTO USERS (NAME, SURNAME, BORNDATE, EMAIL, PASSWORD, REGISTERDATE, NOTEBOOKCOUNT, NOTECOUNT, LASTLOGINDATE, ISDELETED) VALUES(?,?,?,?,?,?,?,?,?,?)");
                s.setString(1, name);
                s.setString(2, surname);
                s.setString(3, borndate);
                s.setString(4, email);
                s.setString(5, password);
                s.setString(6, dtf.format(now));
                s.setInt(7, 0);
                s.setInt(8, 0);
                s.setString(9, dtf.format(now));
                s.setBoolean(10, false);
                s.executeUpdate();
                setUserID(null);
                setName(null);
                setSurname(null);
                setBorndate(null);
                setEmail(null);
                setPassword(null);
                setRegisterDate(null);
                setNotebookCount(0);
                setNoteCount(0);
                setLastLoginDate(null);
                setIsDeleted(false);
                setSuccess(2);
                return "login?faces-redirect=true";
            } else {
                setSuccess(4);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        return "register?faces-redirect=true";
    }

    public void refresh() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT NOTECOUNT,NOTEBOOKCOUNT FROM USERS WHERE USERID=" + userID);
            ResultSet rs;
            rs = p.executeQuery();
            if (rs.next()) {
                noteCount = rs.getInt("NOTECOUNT");
                notebookCount = rs.getInt("NOTEBOOKCOUNT");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }

    }

    public String updateUserPass(int userid) throws SQLException {
        Connection con;
        con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");

        try {
            if (oldpassword.equals(password)) {
                PreparedStatement p = con.prepareStatement("UPDATE USERS SET PASSWORD=? WHERE USERID=" + userid);
                p.setString(1, newpassword);
                p.executeUpdate();
                pass = 1;
                password = newpassword;
            } else {
                pass = 2;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        oldpassword = "";
        newpassword = "";
        return "profile?faces-redirect=true";
    }

    public String updateUser(int userid) throws SQLException {
        Connection con;
        con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");

        try {
            PreparedStatement p = con.prepareStatement("UPDATE USERS SET NAME=?,SURNAME=? WHERE USERID=" + userid);
            p.setString(1, name);
            p.setString(2, surname);
            p.executeUpdate();
            pass = 3;
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        if (pass != 3) {
            pass = 4;
        }
        return "profile?faces-redirect=true";
    }

    public String deleteUser(int userid) throws SQLException {
        Connection con;
        con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {
            PreparedStatement p = con.prepareStatement("UPDATE USERS SET ISDELETED=TRUE WHERE USERID=" + userid);
            p.executeUpdate();
            email = "";
            password = "";
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        logout();
        return "login?faces-redirect=true";
    }

    public String forgotPassword() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Java-Do",
                "javado", "123");
        try {

            PreparedStatement p = con.prepareStatement("SELECT * FROM USERS WHERE ISDELETED=FALSE");
            ResultSet rs;
            rs = p.executeQuery();

            while (rs.next()) {
                if (rs.getString("NAME").toUpperCase().trim().equals(name.toUpperCase().trim()) && rs.getString("SURNAME").toUpperCase().trim().equals(surname.toUpperCase().trim()) && rs.getString("EMAIL").toUpperCase().trim().equals(email.toUpperCase().trim())) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("", new FacesMessage("Şifreniz: " + rs.getString("PASSWORD")));
                    return null;
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            con.close();
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", new FacesMessage("Hatalı giriş yaptınız."));
        return null;

    }
    
    public String control() {
        if (success == 1) {
            return null;
        } else {
            return "login";
        }
    }

    public String loginControl() {
        if (success == 1) {
            return "anasayfa";
        } else {
            return null;
        }
    }
}
