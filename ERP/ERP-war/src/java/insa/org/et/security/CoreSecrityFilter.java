/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insa.org.et.security;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceException;
import webService.AAA;
import webService.IAdministration;

/**
 * @Product Name: CFSS
 * @OWNER = INSA
 * @Developed by = ASP Team
 * @Version = 3.4
 * @author Hilwa
 */
@WebFilter(filterName = "CoreSecrityFilter",
        urlPatterns = {"/*"},
        initParams = {
            @WebInitParam(name = "mood", value = "awake")})

public class CoreSecrityFilter implements Filter {

    FilterConfig config = null;
    ServletContext servletContext = null;
    AAA securityService = new AAA();

    public void init(FilterConfig fConfig) throws ServletException {
        config = fConfig;
        servletContext = config.getServletContext();
    }

    /**
     * For Secured resources this method checks the user to be authenticated and
     * authorized to get the resource
     *
     * @param req the Servlet request object
     * @param res Servlet response object
     * @param chain the filterChain object
     * @throws java.io.IOException
     * @throws java.net.ConnectException
     * @throws javax.servlet.ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws WebServiceException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;
        try {
            int httpResponseCode = 0;

            String userAgent = httpReq.getHeader("User-Agent");
            httpReq.getSession().setAttribute("userAgent", userAgent);
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            ------------
            httpRes.addHeader("X-XSS-Protection", "1; mode=block");
            httpRes.addHeader("X-Content-Type-Options", "nosniff");   //Prevent MIME-sniffing attacks using the 
            httpRes.addHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
            httpRes.addHeader("x-frame-options", "SAMEORIGIN");
//           
//            ----------
            String connURL = Utility.getBundleValue(systemBundle, "ip") + ":" + Utility.getBundleValue(systemBundle, "port");
            String dataset = Utility.getBundleValue(systemBundle, "dataSet");
            String servicePath = Utility.getBundleValue(systemBundle, "servicePath");

            // check the requested resource status 
            //  resources are in the application or not
            boolean facesRequest = httpReq.getRequestURI().startsWith(httpReq.getContextPath() + "/javax.faces.resource/");

            boolean resourceRequest = httpReq.getRequestURI().startsWith(httpReq.getContextPath() + "/resources/");
            boolean facesFirstRequest = httpReq.getRequestURI().startsWith(httpReq.getContextPath() + "/faces/javax.faces.resource/");
            boolean filterPage = httpReq.getRequestURI().startsWith(httpReq.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
            boolean resourceRequestImg = httpReq.getRequestURI().startsWith(httpReq.getContextPath() + "/image");

            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            int lastResource = httpReq.getRequestURI().split("/").length - 1;

            URL connectionURL = new URL(servicePath);
            HttpURLConnection connection = (HttpURLConnection) connectionURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            httpResponseCode = connection.getResponseCode();
            if (httpResponseCode == 200 && (filterPage || facesRequest
                    || resourceRequest || facesFirstRequest || resourceRequestImg
                    || !security.isSecured(httpReq.getRequestURI().split("/")[lastResource].split("\\.")[0], dataset))) {

                if (httpReq.getRequestURI().compareTo(httpReq.getContextPath() + "/LoggedIn.xhtml") == 0) {
                    //befor the session we can check first time access
                    //if the user is accessing for the first time -> redirect to ChangePasswordPassword 
                    if (security.mustChangePassword(httpReq.getSession().getAttribute("clientUser").toString()) == 1) {
                        httpRes.sendRedirect(
                                "https://" + connURL
                                + httpReq.getContextPath()
                                + "/ChangePassword.xhtml?" + "continue=https://" + connURL
                                + httpReq.getSession().getAttribute("requestedPage"));
                    } //else redirect to the the requested page                     
                    else {
                        //check session 
                        if (httpReq.getSession().getAttribute("requestedPage") != null) {
                            httpRes.sendRedirect(httpReq.getSession().getAttribute("requestedPage").toString());
                        } else {
                            //if request page is null -> forward to Home Page
                            if (httpReq.getSession().getAttribute("requestedPage").toString().contains("SSID")) {

                                String url = httpReq.getSession().getAttribute("requestedPage").toString().split("\\?")[0];
                                httpRes.sendRedirect("https://"
                                        + url.split("/")[2]
                                        + url.split("/")[3]);
                            } else {
                                String url = httpReq.getSession().getAttribute("requestedPage").toString();
                                httpRes.sendRedirect("https://"
                                        + url.split("/")[2]
                                        + url.split("/")[3]);
                            }
                        }

                    }
                } else if (httpReq.getRequestURI().compareTo(httpReq.getContextPath() + "/AccessDenied.html") == 0) {
                    if (httpReq.getSession().getAttribute("securityPage") != null) {
                        String securityPage = httpReq.getSession().getAttribute("securityPage").toString();
                        httpReq.getSession().setAttribute("securityPage", null);
                        httpRes.sendRedirect(securityPage);
                    }
                } else { //any page which need no security
                    chain.doFilter(httpReq, httpRes);
                }
            } else if (httpResponseCode == 200 && httpReq.getSession().getAttribute("sessionTicket") == null) {

                httpReq.getSession().setAttribute("requestedPage", httpReq.getRequestURI());

                httpRes.sendRedirect(
                        "https://" + connURL
                        + httpReq.getContextPath()
                        + "/Login.xhtml?" + "continue=https://" + connURL
                        + httpReq.getRequestURI());

            } //Check autorization by session id and requested page 
            else if (httpResponseCode == 200 && httpReq.getSession().getAttribute("sessionTicket") != null
                    && security.isAuthorized(httpReq.getSession().getAttribute("sessionTicket").toString(),
                            httpReq.getRequestURI().split("/")[lastResource].split("\\.")[0],
                            dataset)) {

                httpReq.getSession().setAttribute("permission",
                        security.getUserStatus(
                                Integer.parseInt(httpReq.getSession().getAttribute("clientUserId").toString()),
                                httpReq.getRequestURI().split("/")[lastResource].split("\\.")[0].toUpperCase(),
                                dataset
                        ));
                chain.doFilter(httpReq, httpRes);
            } else if (httpResponseCode == 200) {
                // login  error u'r are not allowed the resource???                   
                httpReq.getSession().setAttribute("requestedPage", httpReq.getRequestURI());
                String redirectedPage = "https://" + connURL + httpReq.getContextPath() + "/AccessDenied.html";
                String requestedPage = "https://" + connURL + httpReq.getRequestURI();
                if (!(redirectedPage.equals(requestedPage))) {
                    httpRes.sendRedirect(
                            "https://" + connURL
                            + httpReq.getContextPath()
                            + "/faces/AccessDenied.html?" + "continue=https://" + connURL
                            + httpReq.getRequestURI());
                } else {
                    httpRes.sendRedirect("https://" + connURL + "/" + httpReq.getContextPath() + "/error/connectionLost.html");
                }
            } else {

                httpRes.sendRedirect("https://" + connURL + "/" + httpReq.getContextPath() + "/error/connectionLost.html");
                chain.doFilter(httpReq, httpRes);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void destroy() {
    }
}
