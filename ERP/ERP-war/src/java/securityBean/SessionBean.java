package securityBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.StreamedContent;
import webService.UserStatus;

/**
 *
 * @author admin
 */
@Named("sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    @PostConstruct
    public void _init() {

    }

    //*** Session String Values ***********************
    private String _userName = "UserID";
    private int _userId = -1;
    private int _sessionID = 0;
    private int empId = -1;
    private String firstName = "";
    private String lastName = "";
    private String middleName;
    private String workId;
    private String loggerLine;
   

    /**
     * <p>
     * Construct a new session data bean instance.</p>
     */
//<editor-fold defaultstate="collapsed" desc="getter and setter">
    public SessionBean() {
        this.getPermission();
//        this.employeeInformation();
    }
    
    private StreamedContent content;
    
    public StreamedContent getContent() {
        return content;
    }
    
    public void setContent(StreamedContent content) {
        this.content = content;
    }
    
    public String getUserName() {
        return _userName;
    }
    
    public void setUserName(String _userName) {
        this._userName = _userName;
    }
    
    public int getUserId() {
        return _userId;
    }
    
    public void setUserId(int _userId) {
        this._userId = _userId;
    }
    
    public int getSessionID() {
        return _sessionID;
    }
    
    public void setSessionID(int _sessionID) {
        this._sessionID = _sessionID;
    }
    
    private String captcha = "";
    
    public String getCaptcha() {
        return captcha;
    }
    
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    
    private UserStatus permissions;
    
    public UserStatus getPermissions() {
        return permissions;
    }
    
    public void setPermissions(UserStatus permissions) {
        this.permissions = permissions;
    }
    public int getEmpId() {
        return empId;
    }
    
    public void setEmpId(int empId) {
        this.empId = empId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public String getWorkId() {
        return workId;
    }
    
    public void setWorkId(String workId) {
        this.workId = workId;
    }
      public String getLoggerLine() {
        if (_userName.equalsIgnoreCase("UserID")) {
            loggerLine = "Offline";
        } else {
            loggerLine = "Online";

        }
        return loggerLine;
    }

    public void setLoggerLine(String loggerLine) {
        this.loggerLine = loggerLine;
    }
//</editor-fold>

    public UserStatus getPermission() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            if (request.getSession().getAttribute("permission") != null) {
                permissions = (UserStatus) request.getSession().getAttribute("permission");
            }
        }
        return permissions;
    }

 

  

}
