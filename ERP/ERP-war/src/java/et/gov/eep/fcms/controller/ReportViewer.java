/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.fcms.businessLogic.ReportGeneratorBean;
import et.gov.eep.fcms.businessLogic.Singleton;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.insa.report.ReportControl;

/**
 *
 * @author pc
 */
@Named("reportViewer")
@ViewScoped
public class ReportViewer implements Serializable {

    @EJB
    private ReportGeneratorBean reportGeneratorBean;

//    public GenerateReport getGenerateReport() {
//        return generateReport;
//    }
//
//    public void setGenerateReport(GenerateReport generateReport) {
//        this.generateReport = generateReport;
//    }
    /**
     *
     */
    public void generateReport() {
        Singleton singleton = Singleton.getInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response
                = (HttpServletResponse) context.getExternalContext().getResponse();
        int status = generateReports(response, request,
                singleton.getFormat(),
                singleton.getFileName(),
                singleton.getReportName(),
                singleton.getReportIndex(),
                singleton.getParam());
        singleton.getClearSingletonParameter();
        if (status == 0) {
            addMessage("Sorry; No Record Has Been Found!!");
        } else {
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    /**
     *
     * @param response
     * @param request
     * @param format
     * @param fileName
     * @param reportName
     * @param reportIndex
     * @param param
     * @return
     */
    public int generateReports(HttpServletResponse response, HttpServletRequest request,
            String format, String fileName, String reportName, String reportIndex, HashMap param) {
        try {
            Collection rs;

            if (reportIndex.equalsIgnoreCase("pention_tax")) {
                rs = reportGeneratorBean.pentionReport(param);
            } else if (reportIndex.equalsIgnoreCase("pention_Summery")) {
                rs = reportGeneratorBean.pentionSummery(param);
                System.out.println("collection list is  -= " + rs.size());
            } else if (reportIndex.equalsIgnoreCase("income_Tax")) {
                rs = reportGeneratorBean.incomeTaxAttachment(param);
            } else if (reportIndex.equalsIgnoreCase("income_Summery")) {
                rs = reportGeneratorBean.incomeTaxSummery(param);
            } else if (reportIndex.equalsIgnoreCase("costSharingAttacmentTax")) {
                rs = reportGeneratorBean.costSharingAttachmentReport(param);
            } else {
                rs = reportGeneratorBean.costSharingSummeryReport(param);
            }
//        if (rs!= null || (format.equalsIgnoreCase("html") && rs == null)) {
            if ((format.equalsIgnoreCase("html"))) {
                ReportControl.generateReportColl(response, request, format, fileName, reportName, param, rs);
                System.out.println("x y z +");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param summary
     */
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
