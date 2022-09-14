/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.eclipse.persistence.internal.sessions.ArrayRecord;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author user
 */
@Named(value = "generateReportsController")
@ViewScoped
public class GenerateReportsController implements Serializable {

    public GenerateReportsController() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrPayrollPeriods hrPayrollPeriodsEntity;
    @Inject
    HrPayrollPeriods payrollPeriod;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrPayrollPeriods hrMontPayroll;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    private List<HrPayrollPeriods> payrollTo;
    @EJB
    HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransaction;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;

    private String fromDate;
    private static String sqlPara;
    private static String sqlParaForPaySlip;
    private String fromCode;
    private String reportType;
    private String activePayrollDate;
    private static String concEarningDedCodes;
    static String ones[] = {" ", " ONE", " TWO", " THREE", " FOUR", " FIVE", " SIX", " SEVEN", " EIGHT", " NINE", " TEN", " ELEVEN", " TWELVE", " THIRTEEN", " FOURTEEN", " FIFTEEN", " SIXTEEN", " SEVENTEEN", " EIGHTEEN", " NINETEEN"};
    static String tens[] = {" ", " ", " TWENTY", " THIRTY", " FOURTY", " FIFTY", " SIXTY", " SEVENTY", " EIGHTY", " NINETY"};
    private boolean isEarningForReport = false;
    private boolean isDedectionForReport = false;
    private boolean isBoth = false;
    private boolean isAll = false;
    private boolean isEarning;
    private int numberOfDays = 30;
    private double totalAmount;
    private DecimalFormat dec = new DecimalFormat("0.00");
    private List<HrPayrollPeriods> payrollFrom;
    private List<HrPayrollPeriods> hrPayrollPeriods;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
    private List<HrPayrollEarningDeductions> listOfSelectedEd;
    private List<HrPayrollMonTransactions> listOfSelectedMonTransactions;
    private List<HrPayrollMonTransactions> listOfMonTransactions;
    private List<HrPayrollMonTransactions> listOfSelectedMonthlyTransactions;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostCOnstract">

    @PostConstruct
    public void _init() {
        if (hrPayrollPeriodsLocal.activePayrollDate() != null) {
            activePayrollDate = hrPayrollPeriodsLocal.activePayrollDate().getPaymentMadeForTheMonthOf();
        } else {
            activePayrollDate = "[No Active Date is Defined]";
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public List<HrPayrollMonTransactions> getListOfSelectedMonthlyTransactions() {
        return listOfSelectedMonthlyTransactions;
    }

    public void setListOfSelectedMonthlyTransactions(List<HrPayrollMonTransactions> listOfSelectedMonthlyTransactions) {
        this.listOfSelectedMonthlyTransactions = listOfSelectedMonthlyTransactions;
    }

    public List<HrPayrollMonTransactions> getListOfMonTransactions() {
        return listOfMonTransactions;
    }

    public void setListOfMonTransactions(List<HrPayrollMonTransactions> listOfMonTransactions) {
        this.listOfMonTransactions = listOfMonTransactions;
    }

    public HrPayrollPeriods getPayrollPeriod() {
        if (payrollPeriod == null) {
            payrollPeriod = new HrPayrollPeriods();
        }
        return payrollPeriod;
    }

    public void setPayrollPeriod(HrPayrollPeriods payrollPeriod) {
        this.payrollPeriod = payrollPeriod;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public List<HrPayrollMonTransactions> getListOfSelectedMonTransactions() {
        return listOfSelectedMonTransactions;
    }

    public void setListOfSelectedMonTransactions(List<HrPayrollMonTransactions> listOfSelectedMonTransactions) {
        this.listOfSelectedMonTransactions = listOfSelectedMonTransactions;
    }
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void handleChange(ValueChangeEvent event) {
        code = returnId(event.getNewValue().toString());

    }

    public HrPayrollPeriods getHrMontPayroll() {
        if (hrMontPayroll == null) {
            hrMontPayroll = new HrPayrollPeriods();
        }
        return hrMontPayroll;
    }

    public void setHrMontPayroll(HrPayrollPeriods hrMontPayroll) {
        this.hrMontPayroll = hrMontPayroll;
    }

    public List<HrPayrollPeriods> getPayrollTo() {

        return payrollTo;
    }

    public void setPayrollTo(List<HrPayrollPeriods> payrollTo) {
        this.payrollTo = payrollTo;
    }

    public List<HrPayrollPeriods> getHrPayrollPeriods() {
        return hrPayrollPeriods;
    }

    public void setHrPayrollPeriods(List<HrPayrollPeriods> hrPayrollPeriods) {
        this.hrPayrollPeriods = hrPayrollPeriods;
    }

    public String getActivePayrollDate() {
        return activePayrollDate;
    }

    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        payrollFrom = hrPayrollPeriodsLocal.findAll();

        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public boolean isIsAll() {
        return isAll;
    }

    public void setIsAll(boolean isAll) {
        this.isAll = isAll;
    }

    public boolean isIsEarningForReport() {
        return isEarningForReport;
    }

    public void setIsEarningForReport(boolean isEarningForReport) {
        this.isEarningForReport = isEarningForReport;
    }

    public boolean isIsDedectionForReport() {
        return isDedectionForReport;
    }

    public void setIsDedectionForReport(boolean isDedectionForReport) {
        this.isDedectionForReport = isDedectionForReport;
    }

    public boolean isIsBoth() {
        return isBoth;
    }

    public void setIsBoth(boolean isBoth) {
        this.isBoth = isBoth;
    }

    private boolean isDeduction;

    public boolean isIsEarning() {
        return isEarning;
    }

    public void setIsEarning(boolean isEarning) {
        this.isEarning = isEarning;
    }

    public boolean isIsDeduction() {
        return isDeduction;
    }

    public void setIsDeduction(boolean isDeduction) {
        this.isDeduction = isDeduction;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    public List<HrPayrollEarningDeductions> getListOfSelectedEd() {
        return listOfSelectedEd;
    }

    public void setListOfSelectedEd(List<HrPayrollEarningDeductions> listOfSelectedEd) {
        this.listOfSelectedEd = listOfSelectedEd;
    }

    private List<HrPayrollEarningDeductions> selectedEarningDeductions;

    public List<HrPayrollEarningDeductions> getSelectedEarningDeductions() {
        return selectedEarningDeductions;
    }

    public void setSelectedEarningDeductions(List<HrPayrollEarningDeductions> selectedEarningDeductions) {
        this.selectedEarningDeductions = selectedEarningDeductions;
    }

    private List<HrEmployees> listOfBackPayedEmployees;

    public List<HrEmployees> getListOfBackPayedEmployees() {
        return listOfBackPayedEmployees;
    }

    public void setListOfBackPayedEmployees(List<HrEmployees> listOfBackPayedEmployees) {
        this.listOfBackPayedEmployees = listOfBackPayedEmployees;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">
    public String getReturnYM(String dateToSplit) {
        if (dateToSplit.equalsIgnoreCase("[No Active Date is Defined]")) {
            return "[No Active Date is Defined]";
        } else {
            String dates[] = dateToSplit.split("/");
            return dates[1] + "/" + dates[2];
        }
    }

    public void handleValueChangeFrom(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save(ActionEvent actionEvent) throws JRException, IOException {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("title", "hello");
        System.out.println("Test 1");
        String fileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Report3.jasper");
        System.out.println("Test 2");
        File jasper = new File("D:\\report8.jasper");
        JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayroll(""), false);
        JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
        System.out.println("Test 3");
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        System.out.println("Test 4");
        response.addHeader("Content-disposition", "attachment; filename=Report2.pdf");
        System.out.println("Test 5");
        ServletOutputStream stream = response.getOutputStream();
        System.out.println("Test 6");
        JasperExportManager.exportReportToPdfStream(print, stream);
        System.out.println("Test 7");
        stream.flush();
        stream.close();
        System.out.println("Test 8");
        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("Test 9");
    }

    public String returnReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\report2.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnEdReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\EarningAndDeductionReports.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnEdSummeryReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\edSummeryReport.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnOneThirdRep() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\deductionWithGreaterOnetTirdSal.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnFamilyReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\FamilyReport.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnMortageReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\MortageReport.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnCourtCaseReport() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\CourtCaseReport.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnMonthlyPaySlip() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\individualPaySlip.jasper";
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnLogo() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "resources\\image\\logo2.jpg";
            System.out.print("The resource name is " + returnResources);
            return returnResources;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getReturnResources() {
        try {
            System.out.print("hello");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            ServletContext servletContext = (ServletContext) externalContext.getContext();
            String returnResources = servletContext.getRealPath("\\");
            returnResources = returnResources + "erp\\hrms\\payroll\\report8.jasper";
            System.out.print("The resources are" + returnResources);
            File folder = new File(returnResources);
            File[] listOfFiles = folder.listFiles();
            File[] allFiles = folder.listFiles();
            int m = 0;
            int directory = 0;
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    directory = directory + 1;
                }
            }
            String[] directorySize = new String[directory];
            int dd = 0;
            for (File s : listOfFiles) {
                if (s.isDirectory()) {
                    directorySize[dd] = s.getCanonicalPath();
                    dd = dd + 1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public BufferedImage returnImage() {
        try {
            File imgFile = new File(returnLogo());
            BufferedImage img = ImageIO.read(imgFile);
            return img;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void loadEarningsForPSummery() {
        try {
            if (isEarning) {
                isDeduction = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyDeductions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadEarnings() {
        try {
            if (isEarning) {
                isDeduction = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyEarnings();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadLists() {
        ArrayList<HashMap> hm = new ArrayList<HashMap>();
        HashMap hash = new HashMap();
        hash.put("", "");
        hm.add(hash);

    }

    public void generatePaySlipWithSelectedCriterias(ActionEvent actionEvent) throws JRException, IOException {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                sqlParaForPaySlip = " ";
                String singleQoute = "'";
                for (HrPayrollEarningDeductions ed : listOfSelectedEd) {
                    //    sqlParaForPaySlip = sqlParaForPaySlip +singleQoute+ ed.getCode() +singleQoute+ " OR ED.CODE=";
                    sqlParaForPaySlip = sqlParaForPaySlip + ed.getCode() + ",";
                }
                StringBuffer buffer = new StringBuffer(sqlParaForPaySlip);
                String comma = ",";
                buffer.lastIndexOf("a");
                buffer.replace(buffer.lastIndexOf(comma), buffer.lastIndexOf(",") + 1, ""); // Shift the positions front.
                String sqlParramer = buffer.toString();
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("LOGO", returnImage());
                BigDecimal big = new BigDecimal(fromCode);
                payrollPeriod.setId(big);
                payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
                hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
                File jasper = new File(returnReport());
                if (hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayroll(fromCode).isEmpty()) {
                    JsfUtil.addErrorMessage("No Data to Displaly");
                } else {
                    JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayrollWithSelectedEd(payrollPeriod.getId().toString(), sqlParramer), false);
                    JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    response.addHeader("Content-disposition", "attachment; filename=All Employees Pay Slip.pdf");
                    ServletOutputStream stream = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(print, stream);
                    stream.flush();
                    stream.close();
                    FacesContext.getCurrentInstance().responseComplete();
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generatePaySlipForAllEmployees(ActionEvent actionEvent) throws JRException, IOException {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("LOGO", returnImage());
                BigDecimal big = new BigDecimal(fromCode);
                payrollPeriod.setId(big);
                payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
                hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
                File jasper = new File(returnReport());
                if (hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayroll(fromCode).isEmpty()) {
                    JsfUtil.addErrorMessage("No Data to Displaly");
                } else {
                    JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadMonthlyPayroll(fromCode), false);
                    JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    response.addHeader("Content-disposition", "attachment; filename=All Employees Pay Slip.pdf");
                    ServletOutputStream stream = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(print, stream);
                    stream.flush();
                    stream.close();
                    FacesContext.getCurrentInstance().responseComplete();
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateOneThirdForPayroll(ActionEvent actionEvent) throws JRException, IOException {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                Map<String, Object> hm = new HashMap<String, Object>();
                //   hm.put("LOGO", returnImage());
                BigDecimal big = new BigDecimal(fromCode);
                payrollPeriod.setId(big);
                payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
                hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
                File jasper = new File(returnOneThirdRep());
                if (hrPayrollAllEmpEdSetupsFacade.loadOneThirdSal(fromCode).isEmpty()) {
                    JsfUtil.addErrorMessage("No Data to Displaly");
                } else {
                    JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadOneThirdSal(fromCode), false);
                    JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    response.addHeader("Content-disposition", "attachment; filename=Deductions Greater than 1/3 of Basic Salary.pdf");
                    ServletOutputStream stream = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(print, stream);
                    stream.flush();
                    stream.close();
                    FacesContext.getCurrentInstance().responseComplete();
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateThirdPartyPayment(ActionEvent actionEvent) throws JRException, IOException {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                Map<String, Object> hm = new HashMap<String, Object>();
                String reportName = null;
                hm.put("LOGO", returnImage());
                hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
                File jasper = null;
                JRDataSource datasource = null;
                if (reportType.equalsIgnoreCase("Family")) {
                    reportName = "attachment; filename=Family Report.pdf";
                    jasper = new File(returnFamilyReport());
                    datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadFamily(String.valueOf(payrollPeriod.getId())), false);
                } else if (reportType.equalsIgnoreCase("Mortage")) {
                    reportName = "attachment; filename=Mortage Report.pdf";
                    jasper = new File(returnMortageReport());
                    datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadMortage(String.valueOf(payrollPeriod.getId())), false);
                } else if (reportType.equalsIgnoreCase("Court")) {
                    reportName = "attachment; filename=CourtCase Report.pdf";
                    jasper = new File(returnCourtCaseReport());
                    datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadCourtCase(String.valueOf(payrollPeriod.getId())), false);
                }
                JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", reportName);
                ServletOutputStream stream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(print, stream);
                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateFamily(ActionEvent actionEvent) throws JRException, IOException {
        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("LOGO", returnImage());
        BigDecimal big = new BigDecimal(fromCode);
        payrollPeriod.setId(big);
        payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
        hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
        File jasper = new File(returnFamilyReport());
        JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadOneThirdSal(fromCode), false);
        JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Payroll.pdf");
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void generateEachEmployeesPaySlip(ActionEvent actionEvent) throws JRException, IOException {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("LOGO", returnImage());
                hm.put("FULL_NAME", hrEmployees.getFirstName() + " " + hrEmployees.getMiddleName() + " " + hrEmployees.getLastName());
                BigDecimal big = new BigDecimal(fromCode);
                payrollPeriod.setId(big);
                payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
                hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
                File jasper = new File(returnMonthlyPaySlip());
                if (hrPayrollAllEmpEdSetupsFacade.loadEachEmployeesPaySlip(payrollPeriod, hrEmployees).isEmpty()) {
                    JsfUtil.addErrorMessage("No Data to Displaly");
                } else {
                    JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadEachEmployeesPaySlip(payrollPeriod, hrEmployees), false);
                    JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    response.addHeader("Content-disposition", "attachment; filename=Payroll.pdf");
                    ServletOutputStream stream = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(print, stream);
                    stream.flush();
                    stream.close();
                    FacesContext.getCurrentInstance().responseComplete();
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadEarningsForSummery() {
        try {
            if (isEarningForReport) {
                isDedectionForReport = false;
                isAll = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyEarnings();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDeductionsForSummery() {
        try {
            if (isDedectionForReport) {
                isEarningForReport = false;
                isAll = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyDeductions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadEarningAndDeductions() {
        try {

            if (isAll) {
                isEarningForReport = false;
                isDedectionForReport = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadEarningAndDeductions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDeductions() {
        try {
            if (isDeduction) {
                isEarning = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.loadOnlyDeductions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String returnId(String splitedValue) {
        try {
            String id = null;
            String conc[];
            conc = splitedValue.split("-");
            id = conc[0];
            return id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String returnYear(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[2];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String returnMonth(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[1];
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void handleChangeFrom() {
        try {

            BigDecimal selectedCode = new BigDecimal(fromCode);
            hrPayrollEarningDeductions.setCode(selectedCode);
            hrMontPayroll.setId(selectedCode);
            hrMontPayroll = hrPayrollPeriodsLocal.findPayrollPeriod(hrMontPayroll);
            payrollTo = hrPayrollPeriodsLocal.payrollTo(returnYear(hrMontPayroll.getPaymentMadeForTheMonthOf()), returnMonth(hrMontPayroll.getPaymentMadeForTheMonthOf()));

        } catch (Exception e) {
        }

    }

    public String returnSelectedEds() {
        try {
            concEarningDedCodes = "";
            for (HrPayrollEarningDeductions ed : selectedEarningDeductions) {
                concEarningDedCodes = ed.getCode().toString();
                concEarningDedCodes = concEarningDedCodes + "-";
            }
            return concEarningDedCodes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static final String[] specialNames = {
        "",
        " thousand",
        " million",
        " billion",
        " trillion",
        " quadrillion",
        " quintillion"
    };

    private static final String[] tensNames = {
        "",
        " ten",
        " twenty",
        " thirty",
        " forty",
        " fifty",
        " sixty",
        " seventy",
        " eighty",
        " ninety"
    };

    private static final String[] numNames = {
        "",
        " one",
        " two",
        " three",
        " four",
        " five",
        " six",
        " seven",
        " eight",
        " nine",
        " ten",
        " eleven",
        " twelve",
        " thirteen",
        " fourteen",
        " fifteen",
        " sixteen",
        " seventeen",
        " eighteen",
        " nineteen"
    };

    private static String convertValue(double number) {
        String numberToBeConcated = null;
        String reminder = null;
        if (number % 1000 > 1) {
            numberToBeConcated = " " + (int) number / 1000 + "THOUSAND";
        }
        if (number % 100 > 1) {
            numberToBeConcated = numberToBeConcated + " " + (int) (number % 100) / 100 + "HUNDRED";
        }
        return numberToBeConcated;
    }

    private String convertLessThanOneThousand(int number) {
        String current;
        if (number % 100 < 20) {
            current = numNames[number % 100];
            number /= 100;
        } else {
            current = numNames[number % 10];
            number /= 10;
            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) {
            return current;
        }
        return numNames[number] + " hundred" + current;
    }

    public String convert(int number) {
        if (number == 0) {
            return "zero";
        }
        String prefix = "";
        if (number < 0) {
            number = -number;
            prefix = "negative";
        }
        String current = "";
        int place = 0;
        do {
            int n = number % 1000;
            if (n != 0) {
                String s = convertLessThanOneThousand(n);
                current = s + specialNames[place] + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);
        return (prefix + current).trim();
    }

    public static String returnSpliedAfIndex(String number) {
        String str = new Double(number).toString().substring(number.indexOf('.') + 1, number.lastIndexOf(number));
        return str;
    }

    public static String returnSplied(String number) {
        String str = new Double(number).toString().substring(0, number.indexOf('.'));;
        return str;
    }

    public static String returnNumber(String number, String decimal) {
        if (decimal.equalsIgnoreCase("1")) {
            double current = (Double.valueOf(number)) / 1000;
            return String.valueOf(current);
        } else {
            double current = (Double.valueOf(number)) / 100;
            return String.valueOf(current);
        }
    }

    public static String returnVal(String number, String length) {
        double num = Double.valueOf(number);
        double current = 0d;
        if (length.equalsIgnoreCase("4")) {
            current = Double.valueOf(number) / 1000;
            return "THAUSAND";
        } else if (length.equalsIgnoreCase("3")) {
            return "HUNDRED";
        }
        return null;
    }

    public static String splitAndReturnFirstVal(String number) {
        String[] strs = number.split("\\.");
        return strs[0];
    }

    public static String splitAndReturnSecondtVal(String number) {
        String[] strs = number.split("\\.");
        return strs[1];
    }

    public static String returnCent(String cent) {
        String centInword = "";
        if (Double.valueOf(cent) == 0) {
            centInword = "";
            return centInword;
        } else {

            String x3 = String.valueOf(Double.valueOf((cent)) / 10);
            int firstDigit = Integer.valueOf(splitAndReturnFirstVal(x3));
            int secondDigit = Integer.valueOf(splitAndReturnSecondtVal(x3));
            double multipleResult = Double.valueOf((cent)) * 10;
            //5 ===> 5*10= 50
            //54==> 54*10=  540
            //05     05*10   5
            if (multipleResult >= 10 && multipleResult < 100) {
                centInword = tens[Integer.valueOf(cent)];
            } else if (multipleResult >= 1 && multipleResult < 10) {
                centInword = ones[secondDigit];
            } else if (multipleResult >= 100) {//5.4 or 54
                centInword = tens[firstDigit] + " " + ones[secondDigit];
            }
        }
        return centInword;
    }

    public static String returnTensWtihTwoDigit(String towDigit) {
        String result = "";
        String tensValue = String.valueOf(Double.valueOf((towDigit)) / 10);
        int tensDigiy = Integer.valueOf(splitAndReturnFirstVal(tensValue));
        int onesDigit = Integer.valueOf(splitAndReturnSecondtVal(tensValue));
        if (Integer.valueOf(towDigit) >= 1 && Integer.valueOf(towDigit) <= 19) {
            result = result + ones[Integer.valueOf(towDigit)];
        } else if (Integer.valueOf(towDigit) >= 20 && Integer.valueOf(towDigit) < 100) {
            result = result + tens[tensDigiy] + " " + ones[onesDigit];
        }
        return result;
    }

    public static String returnHundredsForHund(String threeDigit) {
        System.out.println("### xx" + threeDigit);
        String result = "";
        String hundresValue = String.valueOf(Double.valueOf((threeDigit)) / 100);
        int hundred = Integer.valueOf(splitAndReturnFirstVal(hundresValue));
        String tensAndOnes = splitAndReturnSecondtVal(hundresValue);
        String tensValue = String.valueOf(Double.valueOf(splitAndReturnSecondtVal(hundresValue)) / 10);
        int tensDigiy = Integer.valueOf(splitAndReturnFirstVal(tensValue));
        int onesDigit = Integer.valueOf(splitAndReturnSecondtVal(tensValue));
        result = ones[hundred];
        if (Double.valueOf(hundresValue) < 1) {
            result = returnTensWtihTwoDigit(tensAndOnes);
            return result;
        } else if (Double.valueOf(threeDigit) == 0) {
            return "";
        } else if (Double.valueOf(tensValue) == 0) {
            result = result + " HUNDRED ";
            return result;
        } else if (Double.valueOf(tensValue) < 1) {
            result = result + " HUNDRED " + ones[onesDigit];
            return result;
        } else if (Integer.valueOf(tensAndOnes) >= 1 && Integer.valueOf(tensAndOnes) <= 19) {
            result = result + " HUNDRED " + ones[tensDigiy];
            return result;
        } else if (Integer.valueOf(tensAndOnes) >= 20 && Integer.valueOf(tensAndOnes) < 100) {
            result = result + " HUNDRED " + tens[tensDigiy] + " " + ones[onesDigit];
            return result;
        }
        return result;
    }

    public static String returnHundreds(String threeDigit) {
        String result = "";
        String hundresValue = String.valueOf(Double.valueOf((threeDigit)) / 100);
        int hundred = Integer.valueOf(splitAndReturnFirstVal(hundresValue));
        String tensAndOnes = splitAndReturnSecondtVal(hundresValue);
        String tensValue = String.valueOf(Double.valueOf(splitAndReturnSecondtVal(hundresValue)) / 10);
        int tensDigiy = Integer.valueOf(splitAndReturnFirstVal(tensValue));
        int onesDigit = Integer.valueOf(splitAndReturnSecondtVal(tensValue));
        result = ones[hundred];
        if (Double.valueOf(threeDigit) == 0) {
            return "";
        } else if (Double.valueOf(tensValue) == 0) {
            result = result + " HUNDRED ";
            return result;
        } else if (Double.valueOf(tensValue) < 1) {
            result = result + " HUNDRED " + ones[onesDigit];
            return result;
        } else if (Integer.valueOf(tensAndOnes) >= 1 && Integer.valueOf(tensAndOnes) <= 19) {
            result = result + " HUNDRED " + ones[tensDigiy];
            return result;
        } else if (Integer.valueOf(tensAndOnes) >= 20 && Integer.valueOf(tensAndOnes) < 100) {
            result = result + " HUNDRED " + tens[tensDigiy] + " " + ones[onesDigit];
            return result;
        }

        return result;
    }

    public static String returnTrilion(String number) {
        String conc = "";
        String result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoSplitedVal = String.valueOf(Double.valueOf(actualBirr) / Double.valueOf(1000000000000d));//first tow digit of thousand value
        // System.out.println("Th trilion valu  when devide is "+firstTwoSplitedVal);
        //2,285,974
        //first split val=2;
        //second split will be 285,974
        int firstVal = Integer.valueOf(splitAndReturnFirstVal(firstTwoSplitedVal));
        result = returnNumberToWordsWithThreeDigits(String.valueOf(firstVal)) + " TRILION ";
        String secondSplitedVal = splitAndReturnSecondtVal(firstTwoSplitedVal) + "." + cent;
//        //concatinating the incomming birr witth cent that is te remainng
        result = result + returnBillionOnly(secondSplitedVal);
        if (Double.valueOf(cent) == 0) {
            result = result + " ONLY";
        } else {
            result = result + " AND " + returnCent(cent) + " CENT ONLY";
        }
        return result;
    }

    public static String returnNumberInWords(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Double formattedNumber = Double.valueOf(decimalFormat.format(Double.valueOf(number)));
        System.out.println("The formatted number is " + formattedNumber);
        Double x = Double.valueOf(formattedNumber);
        String numberToWords = "";
        if (x == 0) {
            return numberToWords;
        }
        int count = returnNumberODigits(String.valueOf(formattedNumber));
        int total = Integer.valueOf(splitAndReturnFirstVal(String.valueOf(formattedNumber)));
        String cent = splitAndReturnSecondtVal(String.valueOf(formattedNumber));
        if (count == 3) {
            numberToWords = returnTenAndHund((total / 100) % 10, " HUNDRED") + returnTenAndHund(((int) total % 100), " ");
        } else if (count == 4) {
            numberToWords = returnThreeDigitThousand(total);
        } else if (count == 5) {
            numberToWords = returnThusandFinal((int) total);
        } else if (count == 6) {
            numberToWords = returnThusandFinal((int) total);
        } else if (count == 7) {
            numberToWords = returnMillionFinal((int) total);
        } else if (count == 8) {
            numberToWords = returnBillionFinal((int) total);
        } else if (count == 9) {
            System.out.println("Called g");
            numberToWords = returnTrilion(String.valueOf(total));
        }
        if (Integer.valueOf(cent) == 0) {
            numberToWords = numberToWords + " ONLY";
        } else if ((Integer.valueOf(cent) > 0) && total == 0) {//0.56 if the number is 0.56
            numberToWords = returnCent(cent) + " CENT ONLY";
        } else {
            numberToWords = numberToWords + " AND" + returnCent(cent) + " CENT ONLY";
        }
        return numberToWords;
    }

    public static String returnBillionOnly(String number) {
        String conc = "";
        String result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoSplitedVal = String.valueOf(Double.valueOf(actualBirr) / 1000000000);//first tow digit of thousand value
//2,285,974
        //first split val=2;
        //second split will be 285,974
        if (Double.valueOf(number) == 0) {
            return "";
        }
        int firstVal = Integer.valueOf(splitAndReturnFirstVal(firstTwoSplitedVal));
        result = returnNumberToWordsWithThreeDigits(String.valueOf(firstVal)) + " BILLION ";
        String secondSplitedVal = splitAndReturnSecondtVal(firstTwoSplitedVal) + "." + cent;
        //concatinating the incomming birr witth cent that is te remainng
        result = result + returnMillionOnly(secondSplitedVal);
        if (Double.valueOf(cent) == 0) {
            result = result + " ONLY";
        } else {
            result = result + " AND " + returnCent(cent) + " ONLY";
        }
        return result;
    }

    public static String returnBillion(String number) {
        String conc = "";
        String result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoSplitedVal = String.valueOf(Double.valueOf(actualBirr) / 1000000000);//first tow digit of thousand value
//2,285,974
        //first split val=2;
        //second split will be 285,974
        if (Double.valueOf(number) == 0) {
            return "";
        }
        int firstVal = Integer.valueOf(splitAndReturnFirstVal(firstTwoSplitedVal));
        result = returnNumberToWordsWithThreeDigits(String.valueOf(firstVal)) + " MILLION ";
        String secondSplitedVal = splitAndReturnSecondtVal(firstTwoSplitedVal) + "." + cent;
        //concatinating the incomming birr witth cent that is te remainng
        result = result + returnMillionOnly(secondSplitedVal);
        if (Double.valueOf(cent) == 0) {
            result = result + " ONLY";
        } else {
            result = result + " AND " + returnCent(cent) + " CENT ONLY";
        }
        return result;
    }

    public static String returnMillionOnly(String number) {
        String conc = "";
        String result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoSplitedVal = String.valueOf(Double.valueOf(actualBirr) / 1000000);//first tow digit of thousand value
        //2,285,974
        //first split val=2;
        //second split will be 285,974
        int firstVal = Integer.valueOf(splitAndReturnFirstVal(firstTwoSplitedVal));
        result = returnNumberToWordsWithThreeDigits(String.valueOf(firstVal)) + " MILLION ";
        String secondSplitedVal = splitAndReturnSecondtVal(firstTwoSplitedVal) + "." + cent;
        //concatinating the incomming birr witth cent that is te remainng
        result = result + returnHundredThusandOnly(secondSplitedVal);
        return result;
    }

    public static String returnMillion(String number) {
        String conc = "";
        String result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoSplitedVal = String.valueOf(Double.valueOf(actualBirr) / 1000000);//first tow digit of thousand value
//2,285,974
        //first split val=2;
        //second split will be 285,974
        int firstVal = Integer.valueOf(splitAndReturnFirstVal(firstTwoSplitedVal));
        result = returnNumberToWordsWithThreeDigits(String.valueOf(firstVal)) + " MILLION ";;
        String secondSplitedVal = splitAndReturnSecondtVal(firstTwoSplitedVal) + "." + cent;
        //concatinating the incomming birr witth cent that is te remainng
        result = result + returnHundredThusandOnly(secondSplitedVal);
        if (Double.valueOf(cent) == 0) {
            result = result + " ONLY";
        } else {
            result = result + " AND " + returnCent(cent) + " CENT ONLY";
        }
        return result;
    }

    public static String returnHundredThusandOnly(String number) {
        String conc = "";
        String result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoSplitedVal = String.valueOf(Double.valueOf(actualBirr) / 1000);//first tow digit of thousand value
//256456
        //first val = 256
        //second val=456
        String firstVal = splitAndReturnFirstVal(firstTwoSplitedVal);
        String secondVal = splitAndReturnSecondtVal(firstTwoSplitedVal);
        if (Double.valueOf(number) == 0) {
            return "";
        }
        result = returnHundreds(firstVal) + " THOUSAND " + returnHundredsForHund(secondVal);
        result = returnNumberToWordsWithThreeDigits(firstVal) + " THOUSAND " + returnNumberToWordsWithThreeDigits(secondVal);
        return result;
    }

    public static String returnHundredThusand(String number) {
        String conc = "";
        String result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoSplitedVal = String.valueOf(Double.valueOf(actualBirr) / 1000);//first tow digit of thousand value
//256456
        //first val = 256
        //second val=456
        String firstVal = splitAndReturnFirstVal(firstTwoSplitedVal);
        String secondVal = splitAndReturnSecondtVal(firstTwoSplitedVal);
        if (Double.valueOf(number) == 0) {
            return "";
        }
        result = returnNumberToWordsWithThreeDigits(firstVal) + " THOUSAND " + returnNumberToWordsWithThreeDigits(secondVal);
        if (Double.valueOf(cent) == 0) {
            result = result + " ONLY";
        } else {
            result = result + " AND " + returnCent(cent) + " CENT ONLY";
        }
        return result;
    }

    public static String returnTenThusand(String number) {
        String conc = "";
        String Result = "";
        String actualBirr = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String firstTwoDigit = String.valueOf(Double.valueOf(actualBirr) / 1000);//first tow digit of thousand value
        int firstVal = Integer.valueOf(splitAndReturnFirstVal(firstTwoDigit));
        int secondVal = Integer.valueOf(splitAndReturnSecondtVal(firstTwoDigit));
        if (firstVal >= 1 && firstVal < 10) {
            firstVal = firstVal * 10;
        }
        if (Integer.valueOf(cent) == 0) {
            if (secondVal == 0) {//==>10,000
                conc = returnTensWtihTwoDigit(String.valueOf(firstVal)) + " THOUSAND " + " ONLY";;
            } else {
                conc = returnTensWtihTwoDigit(String.valueOf(firstVal)) + " THOUSAND " + returnHundredsForHund(String.valueOf(secondVal)) + " ONLY";;
            }
        } else {
            if (secondVal == 0) {//==>10,000
                conc = returnTensWtihTwoDigit(String.valueOf(firstVal)) + " THOUSAND " + returnCent(cent) + " CENT ONLY";;
            } else {
                conc = returnTensWtihTwoDigit(String.valueOf(firstVal)) + " THOUSAND " + returnHundredsForHund(String.valueOf(secondVal)) + " AND " + returnCent(cent) + " CENT ONLY";;
            }
        }
        //+ " THAUSAND "+ returnThusand(String.valueOf(secondVal));
        return conc;
    }

    public static String returnBillionFinal(int number) {
        String result = "";
        double firstVal = Double.valueOf(number) / 1000000000;
        double secondVal = Double.valueOf(number) % 1000000000;
        int first = (int) firstVal;
        int second = (int) secondVal;
        result = returnTenAndHund((first / 100) % 10, " HUNDRED") + returnTenAndHund((first % 100), " BILLION")
                + returnMillionFinal(second);
        return result;
    }

    public static String returnMillionFinal(int number) {
        String result = "";
        double firstVal = Double.valueOf(number) / 1000000;
        double secondVal = Double.valueOf(number) % 1000000;
        int first = (int) firstVal;
        int second = (int) secondVal;
        result = returnTenAndHund((first / 100) % 10, " HUNDRED") + returnTenAndHund((first % 100), " MILLION")
                + returnThusandFinal(second);
        return result;
    }

    public static String returnFinalCent(int number) {
        String result = "";
        double firstVal = Double.valueOf(number) / 1000;
        double secondVal = Double.valueOf(number) % 1000;
        int first = (int) firstVal;
        int second = (int) secondVal;
        result = returnTenAndHund((first / 100) % 10, " HUNDRED") + returnTenAndHund((first % 100), " THOUSAND") + returnTenAndHund((second / 100) % 10, " HUNDRED") + returnTenAndHund((second % 100), " ");
        return result;
    }

    public static String returnThreeDigitThousand(int number) {
        String result = "";
        double firstVal = Double.valueOf(number) / 1000;
        double secondVal = Double.valueOf(number) % 1000;
        int first = (int) firstVal;
        int second = (int) secondVal;
        result = returnTenAndHund(first, " THOUSAND") + returnTenAndHund((second / 100) % 10, " HUNDRED") + returnTenAndHund((second % 100), " ");
//        result = returnTenAndHund((first / 100) % 10, " HUNDRED") + returnTenAndHund((first % 100), " THOUSAND") + returnTenAndHund((second / 100) % 10, " HUNDRED") + returnTenAndHund((second % 100), " ");
        return result;
    }

    public static String returnThusandFinal(int number) {
        String result = "";
        double firstVal = Double.valueOf(number) / 1000;
        double secondVal = Double.valueOf(number) % 1000;

        int first = (int) firstVal;
        int second = (int) secondVal;
        result = returnTenAndHund((first / 100) % 10, " HUNDRED") + returnTenAndHund((first % 100), " THOUSAND") + returnTenAndHund((second / 100) % 10, " HUNDRED") + returnTenAndHund((second % 100), " ");
        return result;
    }

    public static String returnThusand(String number) {
        String Result = "";
        String vas = splitAndReturnFirstVal(number);
        String cent = splitAndReturnSecondtVal(number);
        String x = String.valueOf(Double.valueOf(vas) / 1000);
        int thusand = Integer.valueOf(splitAndReturnFirstVal(x));
        //HUNDRED ONE
        String x1 = String.valueOf(Double.valueOf(splitAndReturnSecondtVal(x)) / 100);
        int hundred = Integer.valueOf(splitAndReturnFirstVal(x1));
        String x3 = String.valueOf(Double.valueOf(splitAndReturnSecondtVal(x1)) / 10);
        int tensVal = Integer.valueOf(splitAndReturnFirstVal(x3));
        int finalVal = Integer.valueOf(splitAndReturnSecondtVal(x3));
        if (hundred == 0) {
            return Result = ones[thusand] + " THAUSAND " + tens[tensVal] + ones[finalVal] + " AND " + returnCent(cent) + " ONLY";
        } else {
            return Result = ones[thusand] + " THAUSAND " + ones[hundred] + " HUNDRED " + tens[tensVal] + ones[finalVal] + " AND " + returnCent(cent) + " CENT ONLY";
        }
    }

    public static int returnNumberODigits(String number) {
        int string_form = number.indexOf('.');
        return string_form;
    }

    /**
     *
     * @param number this should be a three digit number 251 412 002 014 102
     * @return
     */
    public static String returnNumberToWordsWithThreeDigits(String number) {
        //256
        //025
        //0025
        // 103,311.00
        double threeDigit = Double.valueOf(number) / 100;
        int firstDigit = Integer.valueOf(splitAndReturnFirstVal(String.valueOf(threeDigit)));
        double onesAndTens = Double.valueOf(splitAndReturnSecondtVal(String.valueOf(threeDigit))) / 10;
        int secondDigit = Integer.valueOf(splitAndReturnFirstVal(String.valueOf(onesAndTens)));
        int thirdDigit = Integer.valueOf(splitAndReturnSecondtVal(String.valueOf(onesAndTens)));
        String numToWordVal = "";
        if (Integer.valueOf(number) % 100 == 0) {
            numToWordVal = ones[Integer.valueOf(Integer.valueOf(number) / 100)] + " HUNDRED ";
        } else if (onesAndTens < 1 && firstDigit >= 1) {
            int x = Integer.valueOf(splitAndReturnSecondtVal(String.valueOf(threeDigit))) * 10;
            if (x >= 10 && x <= 19) {
                numToWordVal = ones[firstDigit] + " HUNDRED " + ones[x];
            } else if (x >= 20 && x < 100) {
                System.out.println("Second");
                numToWordVal = ones[firstDigit] + " HUNDRED " + tens[secondDigit] + " " + ones[thirdDigit];
            }

        } else if (firstDigit >= 1 && secondDigit >= 1 && thirdDigit >= 1) {//240
            int total = (secondDigit * 10) + thirdDigit;
            if (total >= 10 && total <= 19) {
                numToWordVal = ones[firstDigit] + " HUNDRED " + ones[total];
            } else if (total >= 20 && total < 100) {
                numToWordVal = ones[firstDigit] + " HUNDRED " + tens[secondDigit] + " " + ones[thirdDigit];
            }
        } else if (firstDigit >= 1 && secondDigit >= 1 && thirdDigit == 0) {//250
//            numToWordVal = ones[firstDigit] + " HUNDRED " + tens[secondDigit];
        } else if (firstDigit >= 1 && secondDigit == 0 && thirdDigit >= 1) {//205
            int total = (secondDigit * 10) + thirdDigit;
            if (total >= 10 && total <= 19) {
                numToWordVal = ones[firstDigit] + " HUNDRED " + ones[total];
            } else if (total >= 20 && total < 100) {
                numToWordVal = ones[firstDigit] + " HUNDRED " + tens[secondDigit] + " " + ones[thirdDigit];
            }
//            numToWordVal = ones[firstDigit] + " HUNDRED " + ones[thirdDigit];
        } else if (firstDigit >= 1 && secondDigit == 0 && thirdDigit == 0) {//200
            numToWordVal = ones[firstDigit] + " HUNDRED ";
        } else if (firstDigit == 0 && secondDigit >= 1 && thirdDigit >= 1) {//056
            numToWordVal = tens[secondDigit] + " " + ones[thirdDigit];
        } else if (firstDigit == 0 && secondDigit >= 1 && thirdDigit == 0) {//050
            numToWordVal = tens[secondDigit];
        } else if (firstDigit == 0 && secondDigit == 0 && thirdDigit >= 1) {//005
            numToWordVal = ones[thirdDigit];
        }
        return numToWordVal;
    }

    public static String returnNumberToWordsWithTwoDigits(String number) {
        //256
        //025
        //0025
        double threeDigit = Double.valueOf(number) / 100;
        int firstDigit = Integer.valueOf(splitAndReturnFirstVal(String.valueOf(threeDigit)));
        String secondVal = "0." + splitAndReturnSecondtVal(String.valueOf(threeDigit));
        double onesAndTens = Double.valueOf(secondVal) * 10;
        int secondDigit = Integer.valueOf(splitAndReturnFirstVal(String.valueOf(onesAndTens)));
        int thirdDigit = Integer.valueOf(splitAndReturnSecondtVal(String.valueOf(onesAndTens)));
        String numToWordVal = "";
        if (firstDigit >= 1 && secondDigit >= 1 && thirdDigit >= 1) {//240
            numToWordVal = ones[firstDigit] + " HUNDRED " + tens[secondDigit] + " " + ones[thirdDigit];
        } else if (firstDigit >= 1 && secondDigit >= 1 && thirdDigit == 0) {//250
            numToWordVal = ones[firstDigit] + " HUNDRED " + tens[secondDigit];
        } else if (firstDigit >= 1 && secondDigit == 0 && thirdDigit >= 1) {//205
            numToWordVal = ones[firstDigit] + " HUNDRED " + ones[thirdDigit];
        } else if (firstDigit >= 1 && secondDigit == 0 && thirdDigit == 0) {//200
            numToWordVal = ones[firstDigit] + " HUNDRED ";
        } else if (firstDigit == 0 && secondDigit >= 1 && thirdDigit >= 1) {//056
            numToWordVal = tens[secondDigit] + " " + ones[thirdDigit];
        } else if (firstDigit == 0 && secondDigit >= 1 && thirdDigit == 0) {//050
            numToWordVal = tens[secondDigit];
        } else if (firstDigit == 0 && secondDigit == 0 && thirdDigit >= 1) {//005
            numToWordVal = ones[thirdDigit];
        }
        return numToWordVal;
    }

    public static String load(int number) {
        if (number >= 0 && number <= 999) {
            if (number == 0) {
            } else {
                numberToWord(((number / 100) % 10), " HUNDRED");
                numberToWord((number % 100), " sss");
            }
        }
        return null;
    }

    public static String returnTenAndHund(int num, String val) {
        String result = "";
        if (num > 19) {
            result = tens[num / 10] + " " + ones[num % 10];
        } else {
            result = ones[num];
        }
        if (num > 0) {
            result = result + val;
        }
        return result;
    }

    public static void main(String[] args) {
    }

    public static void numberToWord(int num, String val) {
        if (num > 19) {
            System.out.print(tens[num / 10] + " " + ones[num % 10]);
        } else {
            System.out.print(ones[num]);
        }
        if (num > 0) {
            System.out.print(val);
        }
    }

    public String returnMonthInAmharic(int month) {
        if (month == 1) {
            return "Meskerem";
        } else if (month == 2) {
            return "Tikemt";
        }
        if (month == 3) {
            return "Hidar";
        }
        if (month == 4) {
            return "Tahesas";
        }
        if (month == 5) {
            return "Tir";
        }
        if (month == 6) {
            return "Yekatit";
        }
        if (month == 7) {
            return "Megabit";
        }
        if (month == 8) {
            return "Miazia";
        }
        if (month == 9) {
            return "Ginbot";
        }
        if (month == 10) {
            return "Sene";
        }
        if (month == 11) {
            return "Hamle";
        }
        if (month == 12) {
            return "Nehase";
        }
        if (month == 13) {
            return "Phugume";
        }
        return null;
    }

    final private static String[] units = {"Zero", "One", "Two", "Three", "Four",
        "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
        "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    final private static String[] tensS = {"", "", "Twenty", "Thirty", "Forty", "Fifty",
        "Sixty", "Seventy", "Eighty", "Ninety"};

    public void generatePayrollSummeryReport(ActionEvent actionEvent) throws JRException, IOException {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                sqlPara = "";
                for (HrPayrollEarningDeductions ed : listOfSelectedEd) {
                    sqlPara = sqlPara + ed.getCode() + ",";
                }
                StringBuffer buffer = new StringBuffer(sqlPara);
                String comma = ",";
                buffer.lastIndexOf("a");
                buffer.replace(buffer.lastIndexOf(comma), buffer.lastIndexOf(comma) + 1, ""); // Shift the positions front.
                String sqlParramer = buffer.toString();
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("LOGO", returnImage());
                BigDecimal big = new BigDecimal(fromCode);
                payrollPeriod.setId(big);
                payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
                hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
                String monthName = returnMonthInAmharic(Integer.valueOf(returnMonth(payrollPeriod.getPaymentMadeForTheMonthOf())));
                String Year = returnYear(payrollPeriod.getPaymentMadeForTheMonthOf());
                hm.put("TITLE", "Salary and Allownce Payment To Employees For The Month of " + monthName + " " + Year
                        + " Is Sent According To The Following List");
                List reportDatas = hrPayrollAllEmpEdSetupsFacade.loadEdSummery(payrollPeriod.getId().toString(), sqlParramer);
                ArrayList<HashMap> listOfReportDatas = new ArrayList<HashMap>();
                Iterator i = reportDatas.iterator();
                double total = 0d;
                Double d = 0d;
                BigDecimal reallyBig = BigDecimal.ZERO;
                while (i.hasNext()) {
                    BigDecimal initialNum = BigDecimal.ZERO;
                    HashMap hash = new HashMap();
                    ArrayRecord ar = (ArrayRecord) i.next();
                    hash.put("CODE", ar.get("CODE"));
                    hash.put("AMOUNT", ar.get("AMOUNT"));
                    hash.put("DESCRIPTION", ar.get("DESCRIPTION"));
                    listOfReportDatas.add(hash);
                    initialNum = new BigDecimal(ar.get("AMOUNT").toString());
                    System.out.print("Before" + initialNum);
                    reallyBig.add(initialNum);
                }
                System.out.print("The total Amounmt is" + reallyBig);
                System.out.print("The total Amounmt is====>" + reallyBig);
                String numberToWords = "";
                int count = returnNumberODigits(String.valueOf(reallyBig));
                numberToWords = returnNumberInWords(String.valueOf(reallyBig));

                hm.put("TOTAL_WORD", numberToWords);
                File jasper = new File(returnEdSummeryReport());
                JRDataSource datasource = new JRBeanCollectionDataSource(listOfReportDatas, false);
                JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=Earning Deduction Summery.pdf");
                ServletOutputStream stream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(print, stream);
                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().responseComplete();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateEarningDeductionReports(ActionEvent actionEvent) throws JRException, IOException {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveUpdateOvertimeRate", dataset)) {
                Map<String, Object> hm = new HashMap<String, Object>();
                hm.put("LOGO", returnImage());
                BigDecimal big = new BigDecimal(fromCode);
                payrollPeriod.setId(big);
                payrollPeriod = hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod);
                hm.put("MONTH", hrPayrollPeriodsLocal.findPayrollPeriod(payrollPeriod).getPaymentMadeForTheMonthOf());
                hm.put("ED_CODE", code);
                BigDecimal edCode = new BigDecimal(code);
                hrPayrollEarningDeductions.setCode(edCode);
                hm.put("ED_DESCRIPTION", hrPayrollEarningDeductionsLocal.findByCode(hrPayrollEarningDeductions).getDescription());
                File jasper = new File(returnEdReport());
                if (hrPayrollAllEmpEdSetupsFacade.loadEarningDeductions(payrollPeriod.getId().toString(), code).isEmpty()) {
                    JsfUtil.addErrorMessage("No Data to Displaly");
                } else {
                    JRDataSource datasource = new JRBeanCollectionDataSource(hrPayrollAllEmpEdSetupsFacade.loadEarningDeductions(payrollPeriod.getId().toString(), code), false);
                    JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), hm, datasource);
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    response.addHeader("Content-disposition", "attachment; filename=EarningAndDeductionLists.pdf");
                    ServletOutputStream stream = response.getOutputStream();
                    JasperExportManager.exportReportToPdfStream(print, stream);
                    stream.flush();
                    stream.close();
                    FacesContext.getCurrentInstance().responseComplete();
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        try {
            ArrayList<HrEmployees> employees = null;
            hrEmployees.setFirstName(hrEmployee);
            employees = hrEmployeeBean.SearchByFname(hrEmployees);
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByFirstName(hrEmployees);
            totalAmount = Double.valueOf(hrEmployees.getBasicSalary().longValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleChanges() {

    }
//</editor-fold>
}
