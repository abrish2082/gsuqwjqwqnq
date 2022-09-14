/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.fcms.businessLogic.Singleton;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
//import javax.inject.Singleton;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author pc
 */
@Named(value = "taxReportConroller")
@ViewScoped
public class TaxReportConroller implements Serializable {

    @EJB
    private HrPayrollPeriodsBeanLocal taxReportbean;
    @EJB
    private HrPayrollEarningDeductionsBeanLocal taxType;
    @EJB
    private HrPayrollPeriodsBeanLocal hrPayrollPeriodsFacade;
    @Inject
    HrPayrollEarningDeductions payrollEarningDeductions;
    @Inject
    HrPayrollPeriods payRollPeriod;
    @Inject
    private ReportViewer reportViewerbean;
    private String monthReport;
    private String reportFormat;
    private String taxName;
    private String reportType;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreate = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAcc() {
        return renderPnlCreate;
    }

    public void setRenderPnlCreateAcc(boolean renderPnlCreate) {
        this.renderPnlCreate = renderPnlCreate;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    /**
     *
     * @return
     */
    public HrPayrollPeriods getPayRollPeriod() {
        return payRollPeriod;
    }

    /**
     *
     * @param payRollPeriod
     */
    public void setPayRollPeriod(HrPayrollPeriods payRollPeriod) {
        this.payRollPeriod = payRollPeriod;
    }

    /**
     *
     * @return
     */
    public HrPayrollEarningDeductions getPayrollEarningDeductions() {
        if (payrollEarningDeductions == null) {
            payrollEarningDeductions = new HrPayrollEarningDeductions();
        }
        return payrollEarningDeductions;
    }

    /**
     *
     * @param payrollEarningDeductions
     */
    public void setPayrollEarningDeductions(HrPayrollEarningDeductions payrollEarningDeductions) {
        this.payrollEarningDeductions = payrollEarningDeductions;
    }

    List<HrPayrollEarningDeductions> payrolEarningDeductionList;

    /**
     *
     * @return
     */
    public List<HrPayrollEarningDeductions> getPayrolEarningDeductionList() {
        payrolEarningDeductionList = taxType.taxTypeLists();
        return payrolEarningDeductionList;
    }

    /**
     *
     * @param payrolEarningDeductionList
     */
    public void setPayrolEarningDeductionList(List<HrPayrollEarningDeductions> payrolEarningDeductionList) {
        this.payrolEarningDeductionList = payrolEarningDeductionList;
    }

    /**
     *
     * @return
     */
    public SelectItem[] getTaxList() {
        return JsfUtil.getSelectItems(taxType.taxTypeLists(), true);
    }

//    public SelectItem[] getMonthList() {
//        return JsfUtil.getSelectItems(taxReportbean.payrollPeriodList(), true);
//    }
    String taxReportType;

    /**
     *
     * @param event
     */
    public void taxTypeValueChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            taxReportType = event.getNewValue().toString();
            payrollEarningDeductions.setCriterias(taxReportType);
            payrollEarningDeductions = taxType.getTaxType(payrollEarningDeductions);

        }
    }
    private String code;

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
    private String Names;

    /**
     *
     * @return
     */
    public String getNames() {
        return Names;
    }

    /**
     *
     * @param Names
     */
    public void setNames(String Names) {
        this.Names = Names;
    }

    /**
     *
     * @param event
     */
    public void handleChange(ValueChangeEvent event) {
        try {
            code = event.getNewValue().toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.print("The selected earning deduction code is1 " + code);
    }

    /**
     *
     */
    public void handleChange1() {
        try {
            System.out.print("The selected earning deduction code is " + code);
            BigDecimal selectedCode = new BigDecimal(code);
            payrollEarningDeductions.setCode(selectedCode);
            System.out.println(" tax type     ==   " + code);

        } catch (Exception e) {
        }

    }

    /**
     *
     * @param dateToSplit
     * @return
     */
    public String getReturnYM(String dateToSplit) {
        System.out.print("Date ot be splited" + dateToSplit);
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }
    private List<HrPayrollPeriods> listOfPayrollPeriods;

    /**
     *
     * @return
     */
    public List<HrPayrollPeriods> getListOfPayrollPeriods() {
        listOfPayrollPeriods = hrPayrollPeriodsFacade.findAll();
        return listOfPayrollPeriods;
    }
    String srNum = "select";

    /**
     *
     * @param event
     */
    public void handleReportTypes(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            srNum = event.getNewValue().toString();
        }
    }

    /**
     *
     * @return
     */
    public String getReportType() {
        return reportType;
    }

    /**
     *
     * @param reportType
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     *
     * @return
     */
    public String getTaxName() {

        return taxName;
    }

    /**
     *
     * @param taxName
     */
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    /**
     *
     * @return
     */
    public String getReportFormat() {
        return reportFormat;
    }

    /**
     *
     * @param reportFormat
     */
    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    /**
     *
     * @return
     */
    public String getMonthReport() {
        return monthReport;
    }

    /**
     *
     * @param monthReport
     */
    public void setMonthReport(String monthReport) {
        this.monthReport = monthReport;
    }

    private String taxTypeReport;

    /**
     *
     * @return
     */
    public String getTaxReportType() {
        return taxReportType;
    }

    /**
     *
     * @param taxReportType
     */
    public void setTaxReportType(String taxReportType) {
        this.taxReportType = taxReportType;
    }

    /**
     *
     * @return
     */
    public String getTaxTypeReport() {
        return taxTypeReport;
    }

    /**
     *
     * @param taxTypeReport
     */
    public void setTaxTypeReport(String taxTypeReport) {
        this.taxTypeReport = taxTypeReport;
    }

    /**
     *
     * @param event
     */
    public void handlTaxName(ValueChangeEvent event) {

        if (null != event.getNewValue() && !event.getNewValue().equals("")) {
            taxName = String.valueOf(event.getNewValue());
            Names = String.valueOf(event.getNewValue());
        }
    }

//    public void handlMonthReport(ValueChangeEvent event) {
//        if (event.getNewValue() != null && !event.getNewValue().equals("")) {
//            monthReport = String.valueOf(event.getNewValue());
//            setMonthReport(monthReport);
//        }
//    }
    /**
     *
     * @param event
     */
    public void handlReportType(ValueChangeEvent event) {
        if (null != event.getNewValue() && !event.getNewValue().equals("")) {
            reportType = event.getNewValue().toString();
            setReportType(reportType);
            System.out.println(monthReport);
        }
    }
    private boolean ajax = true;

    /**
     *
     * @return
     */
    public boolean isAjax() {
        return ajax;
    }

    /**
     *
     * @param ajax
     */
    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }

    /**
     *
     * @param e
     */
    public void selectReportType(ValueChangeEvent e) {
        if (null != e.getNewValue() && !e.getNewValue().equals("")) {
            ajax = e.getNewValue().toString().equalsIgnoreCase("html");

        }
    }

    public void createNew() {
//        clear();
//        saveUpdate = "Save";
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreate = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreate = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-document");
        }
    }

    /**
     *
     */
    public void btnGenerateReport() {
        Singleton singleton = Singleton.getInstance();
        HashMap hashMap = new HashMap();
        String fileName = "";
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        if (getTaxName().equalsIgnoreCase("pention") && getReportType().equalsIgnoreCase("att")) {
            System.out.println("");
            // hashMap.put("taxName", getTaxName());        
            hashMap.put("REPORT_MONTH", getMonthReport());
            fileName = servletContext.getRealPath("erp/fcms/tax/Fms_pention_attachment_report.jasper");
            singleton.setReportName("pention_tax");
            singleton.setFormat(reportFormat);
            singleton.setFileName(fileName);
            singleton.setParam(hashMap);
            singleton.setReportIndex("pention_tax");
            if (getReportFormat().equalsIgnoreCase("html")) {
                RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
                        + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
                        + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");
            } else {
                reportViewerbean.generateReport();
                getClearInputParameter();
            }
        } else if (getTaxName().equalsIgnoreCase("pention") && getReportType().equalsIgnoreCase("sum")) {
//            hashMap.put("taxName", getTaxName());
            hashMap.put("monthReport", getMonthReport());
            fileName = servletContext.getRealPath("erp/fcms/tax/pentionSummery.jasper");
            singleton.setReportName("pention_Summery");
            singleton.setReportIndex("pention_Summery");
            singleton.setFormat(getReportFormat());
            singleton.setFileName(fileName);
            singleton.setParam(hashMap);
            if (getReportFormat().equalsIgnoreCase("html")) {
                RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
                        + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
                        + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");
            } else {
                reportViewerbean.generateReport();
                getClearInputParameter();
            }

        } else if (getTaxName().equalsIgnoreCase("income") && getReportType().equalsIgnoreCase("att")) {
            hashMap.put("REPORT_MONTH", getMonthReport());

            fileName = servletContext.getRealPath("erp/fcms/tax/incomeTaxAttachment.jasper");
            singleton.setReportName("income_Tax");
            singleton.setReportIndex("income_Tax");
            singleton.setFormat(getReportFormat());
            singleton.setFileName(fileName);
            singleton.setParam(hashMap);
            if (getReportFormat().equalsIgnoreCase("html")) {
                RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
                        + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
                        + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");
            } else {
                reportViewerbean.generateReport();
                getClearInputParameter();
            }
        } else if (getTaxName().equalsIgnoreCase("income") && getReportType().equalsIgnoreCase("sum")) {
            hashMap.put("incomeTaxMonth", getMonthReport());
            fileName = servletContext.getRealPath("erp/fcms/tax/incomeTaxSummery.jasper");
            singleton.setReportName("income_Summery");
            singleton.setReportIndex("income_Summery");
            singleton.setFormat(getReportFormat());
            singleton.setFileName(fileName);
            singleton.setParam(hashMap);
            if (getReportFormat().equalsIgnoreCase("html")) {
                RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
                        + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
                        + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");
            } else {
                reportViewerbean.generateReport();
                getClearInputParameter();
            }
        } else if (getTaxName().equalsIgnoreCase("cost") && getReportType().equalsIgnoreCase("att")) {
            hashMap.put("REPORT_MONTH", getMonthReport());
            singleton.setReportName("costSharingAttacmentTax");
            singleton.setReportIndex("costSharingAttacmentTax");
            fileName = servletContext.getRealPath("erp/fcms/tax/costSharingAttachment.jasper");
            singleton.setFormat(getReportFormat());
            singleton.setFileName(fileName);
            singleton.setParam(hashMap);
            if (getReportFormat().equalsIgnoreCase("html")) {
                RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
                        + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
                        + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");
            } else {
                reportViewerbean.generateReport();
                getClearInputParameter();
            }
        } else if (getTaxName().equalsIgnoreCase("cost") && getReportType().equalsIgnoreCase("sum")) {
            hashMap.put("REPORT_MONTH", getMonthReport());
            fileName = servletContext.getRealPath("erp/fcms/tax/costSharingSummery.jasper");
            singleton.setReportName("costSharingSummeryTax");
            singleton.setReportIndex("costSharingSummeryTax");
            singleton.setFormat(getReportFormat());
            singleton.setFileName(fileName);
            singleton.setParam(hashMap);
            if (getReportFormat().equalsIgnoreCase("html")) {
                RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
                        + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
                        + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");
            } else {
                reportViewerbean.generateReport();
                getClearInputParameter();
            }
        }

//        singleton.setFormat(getReportFormat());
//        //singleton.setReportName(reportType);
//        singleton.setFileName(fileName);
//        singleton.setParam(hashMap);
//
//        if (getReportFormat().equalsIgnoreCase("html")) { 
//            RequestContext.getCurrentInstance().execute("window.open('../ReportViewer.xhtml', 'ReportViewer', ('width='+.75*screen.width+', "
//                    + "height='+.75*screen.height+',  top='+.1*screen.height+', left='+.1*screen.width+', "
//                    + "dependent=yes, menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=no'));");
//        }
//        else {
//            reportViewerbean.generateReport();
//        }
    }

    private void getClearInputParameter() {
        reportFormat = null;
        reportType = null;
        taxName = null;
        reportFormat = null;
    }
}
