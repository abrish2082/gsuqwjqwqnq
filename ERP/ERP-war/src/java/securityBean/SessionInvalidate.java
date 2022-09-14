package securityBean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webService.AAA;
import webService.IAdministration;

/**
 *
 * @author Administrator
 */
public class SessionInvalidate {

    SessionBean sessionBeanLocal = new SessionBean();

    public void invalidate() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response
                = (HttpServletResponse) context.getExternalContext().getResponse();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = Utility.getBundleValue(systemBundle, "dataSet");

        AAA securityHandler = new AAA();
        IAdministration admin = securityHandler.getMetadataExchangeHttpBindingIAdministration();
        boolean result = false;
        if (request.getSession().getAttribute("sessionTicket") != null) {
            result = admin.logOff(request.getSession().getAttribute("sessionTicket").toString(), dataset);
        }
        if (result) {
            sessionBeanLocal.setSessionID(0);
            sessionBeanLocal.setUserName("");
            request.getSession().removeAttribute("userAuthorizedResource");
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("firstTimeFlag");
            request.getSession().removeAttribute("requestedPage");
            request.getSession().removeAttribute("permission");
            request.getSession().removeAttribute("sessionTicket");
            request.getSession().removeAttribute("clientUser");
            request.getSession().removeAttribute("clientIdentity");
            Cookie[] cookies = request.getCookies();
            int cookieslength = cookies.length;
            for (int i = 0; i < cookieslength; i++) {
                if (cookies[i].getName().equals("JSESSIONID")) {
                    cookies[i].setMaxAge(0);
                    response.addCookie(cookies[i]);
                }
            }
        }

    }
    private String sessionCreated;

    public void sessionCreated() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getSession().getAttribute("sessionTicket") != null) {
            sessionCreated = request.getSession().getAttribute("sessionTicket").toString();
        }
    }

    public void sessionDestroy() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response
                = (HttpServletResponse) context.getExternalContext().getResponse();

        request.getSession().setAttribute("userAuthorizedResource", null);
        request.getSession().setAttribute("user", null);
        request.getSession().setAttribute("firstTimeFlag", null);
        request.getSession().setAttribute("requestedPage", null);
        request.getSession().setAttribute("permission", null);
        request.getSession().setAttribute("sessionTicket", null);
        request.getSession().setAttribute("clientUser", null);
        request.getSession().setAttribute("clientIdentity", null);
        Cookie[] cookies = request.getCookies();
        int cookieslength = cookies.length;
        for (int i = 0; i < cookieslength; i++) {
            if (cookies[i].getName().equals("JSESSIONID")) {
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        }
    }
}
