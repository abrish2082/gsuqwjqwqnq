/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.stock;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import et.gov.eep.fcms.businessLogic.Singleton;
import et.gov.eep.fcms.controller.ReportViewer;

/**
 *
 * @author admin
 */
@Named(value = "fmsStockReportController")
@ViewScoped
public class FmsStockReportController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    private ReportViewer reportViewerBean;
    String fromdates;
    String todates;
    private String fromDate;
    private String toDate;
    private String reportFormat;
    private String reportType;
    private String bachId;
    private boolean ajax = true;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//</editor-fold>

    /**
     * Creates a new instance of FmsStockReportController
     */
    public FmsStockReportController() {
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public ReportViewer getReportViewerBean() {
        return reportViewerBean;
    }

    public void setReportViewerBean(ReportViewer reportViewerBean) {
        this.reportViewerBean = reportViewerBean;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public boolean isAjax() {
        return ajax;
    }

    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }

    public String getBachId() {
        return bachId;
    }

    public void setBachId(String bachId) {
        this.bachId = bachId;
    }
//</editor-fold>

    //value change event
    public void onSelectReportType(ValueChangeEvent e) {
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {
            ajax = e.getNewValue().toString().equalsIgnoreCase("html");
        }

    }

    //handle date from
    public void handleDateFrom(SelectEvent event) throws ParseException {
        fromdates = format.format(event.getObject());
    }

    //handle date to
    public void handleDateTo(SelectEvent event) throws ParseException {
        todates = format.format(event.getObject());
    }

    //get minimum date
    public String getMinDate() {
        return this.fromdates;
    }

    //generate report
    public void generateStockReport() {
        Singleton singleton = Singleton.getInstance();
        HashMap hashMap = new HashMap();
        String fileName = "";
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        hashMap.put("fromDate", fromdates);
        hashMap.put("toDate", todates);
        hashMap.put("pBATCH_ID", bachId);
        fileName = servletContext.getRealPath("erp/fcms/Report/report11.jasper");
        singleton.setReportName("report11");
        singleton.setReportIndex("stockreports");
        singleton.setFormat(getReportFormat());
        singleton.setFileName(fileName);
        singleton.setParam(hashMap);

        if (getReportFormat().equalsIgnoreCase("html")) {
            RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
                    + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
                    + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");

        } else {
            System.out.print("we45    " + getReportFormat());
            reportViewerBean.generateReport();
            System.out.print("ttest13    ");
            getClearInputParameter();
        }
    }

    //clear
    private void getClearInputParameter() {
        reportFormat = null;
        reportType = null;
        reportFormat = null;
    }
}
