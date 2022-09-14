package securityBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import et.gov.eep.commonApplications.localbean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import webService.AAA;
import webService.AumsUser;
import webService.EmployeeInfo;
import webService.IAdministration;
import webService.UserSession;

/**
 * @Product Name: CFSS
 * @OWNER = INSA
 * @Developed by = ASP Team
 * @Version = 3.4
 * @author Hilwa
 */
@Named("logIn")
@ViewScoped
public class LogIn implements Serializable {

    private int __placeholder;
    private String url;
    private String queary;
    int loginAttempt = 0;
    String sessionId = null;

    @Inject
    Captcha captcha;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    localbean Languagelocalbean;

//    private IAdministration ad;
    public LogIn() {

        if (FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().get("continue") != null) {

            url = FacesContext.getCurrentInstance().getExternalContext().
                    getRequestParameterMap().get("continue");

        }
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        banner = Utility.getBundleValue(systemBundle, "orgName");
    }

    @PostConstruct
    public void _int() {

    }

    private String passwordInter;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordInter() {
        return passwordInter;
    }

    public void setPasswordInter(String passwordInter) {
        this.passwordInter = passwordInter;
    }

    private Boolean captchaBox = false;

    public Boolean getCaptchaBox() {
        return captchaBox;
    }

    public void setCaptchaBox(Boolean captchaBox) {
        this.captchaBox = captchaBox;
    }

    public localbean getLanguagelocalbean() {
        return Languagelocalbean;
    }

    public void setLanguagelocalbean(localbean Languagelocalbean) {
        this.Languagelocalbean = Languagelocalbean;
    }

    private String banner = "TO Your System";

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    /**
     *
     * @return
     */
    public String btnSignIn_action() {
        Languagelocalbean.getLangsession().setAttribute("lang", "am");
        FacesContext context = FacesContext.getCurrentInstance();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = Utility.getBundleValue(systemBundle, "dataSet");
        IAdministration security = null;
        try {
            AAA securityService = new AAA();
            security = securityService.getMetadataExchangeHttpBindingIAdministration();
        } catch (javax.xml.ws.soap.SOAPFaultException soapFaultException) {
            javax.xml.soap.SOAPFault fault = soapFaultException.getFault(); //<Fault> node
            javax.xml.soap.Detail detail = fault.getDetail(); // <detail> node
            Iterator detailEntries = detail.getDetailEntries(); //nodes under <detail>
        }

        if (!security.isBlockedLogin(userName, dataset)) {
            UserSession userSession = security.login(userName, passwordInter, false, dataset);

            if (userSession != null) {
                // authenticated                    
                sessionBeanLocal.setSessionID(userSession.getSessionId());
                sessionBeanLocal.setUserName(userSession.getUserName().getValue());
                sessionBeanLocal.setUserId(userSession.getUserID());

                EmployeeInfo employeeInfo = security.getEmpInfo(userSession.getUserID(), dataset);

                if (employeeInfo != null) {
                    if (employeeInfo.getEmpId() != null) {
                        sessionBeanLocal.setEmpId(employeeInfo.getEmpId());
                           System.out.println("EmployeeID is==" + employeeInfo.getEmpId());
                    }
                    if (employeeInfo.getFirstName().getValue() != null) {
                        sessionBeanLocal.setFirstName(employeeInfo.getFirstName().getValue());
                          System.out.println("Employee Firstname is==" + employeeInfo.getFirstName().getValue());
                    }
                    if (employeeInfo.getMiddleName().getValue() != null) {
                        sessionBeanLocal.setMiddleName(employeeInfo.getMiddleName().getValue());
                          System.out.println("Employee Middlename is==" + employeeInfo.getMiddleName().getValue());
                    }
                    if (employeeInfo.getLastName().getValue() != null) {
                        sessionBeanLocal.setLastName(employeeInfo.getLastName().getValue());
                        
                    System.out.println("Employee Lastname is==" + employeeInfo.getLastName().getValue());
                    }
                    if (employeeInfo.getWorkId().getValue() != null) {
                        sessionBeanLocal.setWorkId(employeeInfo.getWorkId().getValue());
                         System.out.println("Employee WorkID is==" + employeeInfo.getWorkId().getValue());
                    }

                }

                HttpServletRequest request
                        = (HttpServletRequest) context.getExternalContext().getRequest();
                HttpServletResponse response
                        = (HttpServletResponse) context.getExternalContext().getResponse();

                request.getSession().setAttribute("sessionTicket", security.encrypt(userSession.getSessionId().toString() + "/" + userSession.getUserName().getValue()));

                request.getSession().setAttribute("clientUserId", userSession.getUserID());
                String requestedPage = null;
                request.getSession().setAttribute("clientUser", userSession.getUserName().getValue());
                if (url != null) {
                    if (url.endsWith("xhtml")) {
                        requestedPage = url;
                        if (queary != null) {
                            requestedPage = url + "?" + queary;
                        }
                    }
                } else {
                    if (request.getSession().getAttribute("continue").toString().endsWith(".xhtml")) {
                        requestedPage = request.getSession().getAttribute("continue").toString();
                    }
                }
                if (requestedPage != null) {
                    request.getSession().setAttribute("requestedPage", requestedPage);

                    return "LoggedIn";
                } else {
                    context.addMessage(null, new FacesMessage("Typing error in the address bar."));
                    return null;
                }
            } else {
                int maxValue = security.maxLoginRetryCount(dataset);
                AumsUser aumsUser = security.userState(userName, dataset);
                security.incrementWrongLogins(aumsUser, maxValue, loginAttempt++, dataset);

                context.addMessage(null, new FacesMessage("The username or password you entered is incorrect."));
                return null;
            }
        } else {
            context.addMessage(null, new FacesMessage("Your account is locked ,please contact your system administrator"));
            return null;
        }
    }
}
