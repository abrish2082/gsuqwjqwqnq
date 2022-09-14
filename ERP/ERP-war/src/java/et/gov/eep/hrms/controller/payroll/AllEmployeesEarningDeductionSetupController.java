/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollGroupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollAllEmpEdSetups;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollGroups;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
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
@Named(value = "allEmployeesEarningDeductionSetupController")
@ViewScoped
public class AllEmployeesEarningDeductionSetupController implements Serializable {

    public AllEmployeesEarningDeductionSetupController() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">
    @Inject
    HrPayrollMaintainEds hrPayrollMaintainEds;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups;
    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;
    @EJB
    HrPayrollGroupsBeanLocal hrPayrollGroupsFacadeLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;

    private String testExample;
    private String code;
    private String groupId;
    private static String sDate;
    private static String eDate;
    private String activePayrollDate;
    private String text;
    private String allEmpEdId;
    private Double monthlyPayment;
    private Double total;
    private boolean isEarning;
    private boolean isDeduction;
    private int monthDifference = 0;

    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
    private List<HrPayrollGroups> listOfHrPayrollGroups;
    private List<HrPayrollAllEmpEdSetups> listOfAllEmployeesEarningDed;
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostCOnstract">

    @PostConstruct
    public void _init() {
        try {
            if (hrPayrollPeriodsLocal.activePayrollDate() == null) {
                activePayrollDate = "[No Active Date is Defined]";
            } else {
                hrPayrollPeriods = hrPayrollPeriodsLocal.activePayrollDate();
                activePayrollDate = hrPayrollPeriods.getPaymentMadeForTheMonthOf();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>  
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getAllEmpEdId() {
        return allEmpEdId;
    }

    public void setAllEmpEdId(String allEmpEdId) {
        this.allEmpEdId = allEmpEdId;
    }

    public void handleChangeOnAllEmpEd(ValueChangeEvent event) {
        allEmpEdId = event.getNewValue().toString();

    }

    public void handleChange2() {

    }
    //-end of generate code

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

    public int getMonthDifference() {
        return monthDifference;
    }

    public void setMonthDifference(int monthDifference) {
        this.monthDifference = monthDifference;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getActivePayrollDate() {
        return activePayrollDate;
    }

    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }

    public HrPayrollPeriods getHrPayrollPeriods() {
        if (hrPayrollPeriods == null) {
            hrPayrollPeriods = new HrPayrollPeriods();
        }
        return hrPayrollPeriods;
    }

    public void setHrPayrollPeriods(HrPayrollPeriods hrPayrollPeriods) {
        this.hrPayrollPeriods = hrPayrollPeriods;
    }

    public HrPayrollMaintainEds getHrPayrollMaintainEds() {

        if (hrPayrollMaintainEds == null) {
            hrPayrollMaintainEds = new HrPayrollMaintainEds();
        }
        return hrPayrollMaintainEds;
    }

    public void setHrPayrollMaintainEds(HrPayrollMaintainEds hrPayrollMaintainEds) {
        this.hrPayrollMaintainEds = hrPayrollMaintainEds;
    }

    public HrPayrollAllEmpEdSetups getHrPayrollAllEmpEdSetups() {
        return hrPayrollAllEmpEdSetups;
    }

    public void setHrPayrollAllEmpEdSetups(HrPayrollAllEmpEdSetups hrPayrollAllEmpEdSetups) {
        this.hrPayrollAllEmpEdSetups = hrPayrollAllEmpEdSetups;
    }

    public String getTestExample() {
        return testExample;
    }

    public void setTestExample(String testExample) {
        this.testExample = testExample;
    }

    public void calculate() {
        testExample = "Sheet";
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<HrPayrollGroups> getListOfHrPayrollGroups() {
        listOfHrPayrollGroups = hrPayrollGroupsFacadeLocal.findAll();

        return listOfHrPayrollGroups;
    }

    public void setListOfHrPayrollGroups(List<HrPayrollGroups> listOfHrPayrollGroups) {
        this.listOfHrPayrollGroups = listOfHrPayrollGroups;
    }

    public List<HrPayrollEarningDeductions> getListOfEarningDeductions() {
        return listOfEarningDeductions;
    }

    public void setListOfEarningDeductions(List<HrPayrollEarningDeductions> listOfEarningDeductions) {
        this.listOfEarningDeductions = listOfEarningDeductions;
    }

    public List<HrPayrollAllEmpEdSetups> getListOfAllEmployeesEarningDed() {
        listOfAllEmployeesEarningDed = hrPayrollAllEmpEdSetupsFacade.findAll();
        return listOfAllEmployeesEarningDed;
    }

    public void setListOfAllEmployeesEarningDed(List<HrPayrollAllEmpEdSetups> listOfAllEmployeesEarningDed) {
        this.listOfAllEmployeesEarningDed = listOfAllEmployeesEarningDed;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void edit() {
        try {

            code = String.valueOf(hrPayrollAllEmpEdSetups.getEarningDeductionCode().getCode());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleChange(ValueChangeEvent event) {
        code = event.getNewValue().toString();

    }

    public void handleChange1() {

    }

    public void saveAllEmpEd() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAllEmpEd", dataset)) {
                System.out.print("The statat date is " + hrPayrollMaintainEds.getStartDate());
                if (code == null) {
                    JsfUtil.addFatalMessage("Select Either Earning or Deduction!");
                } else {
                    hrPayrollAllEmpEdSetupsFacade.saveAllEmpEd(activePayrollDate, hrPayrollMaintainEds, hrPayrollEarningDeductions);
                    JsfUtil.addSuccessMessage("Successfully Saved!");
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
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occured Wile Saving");
        }
    }

    public void onFromDateSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sDate = format.format((Date) event.getObject());
    }

    public void onToDateSelect(SelectEvent event) {
        monthlyPayment = 0d;
        total = 0d;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        eDate = format.format((Date) event.getObject());
    }

    public void loadAllEmpEArnings() {
        try {
            if (isEarning) {
                isDeduction = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.listOfEarnngForAllEmp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAllEmpDeductions() {
        try {
            if (isDeduction) {
                isEarning = false;
                listOfEarningDeductions = null;
                listOfEarningDeductions = hrPayrollEarningDeductionsLocal.listOfDeductionsForAllEmp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateDateDifference() {
        try {
            if (eDate == null) {
                hrPayrollMaintainEds.setMonthlyAmount(hrPayrollMaintainEds.getTotal());
            } else {
                monthDifference = Integer.valueOf(hrPayrollEarningDeductionsLocal.returnNumberOfMonths(sDate, eDate));
                if (monthDifference == 0) {
                    hrPayrollMaintainEds.setMonthlyAmount(hrPayrollMaintainEds.getTotal());
                } else {
                    Double difference = (hrPayrollMaintainEds.getTotal() / (monthDifference));
                    hrPayrollMaintainEds.setMonthlyAmount(difference);
                }
            }
        } catch (Exception e) {
        }
    }

    public void calculateMonthlyPayment1() {
        try {
            monthlyPayment = 0d;
            monthDifference = 0;
            total = 0d;
            hrPayrollMaintainEds.setEndDate(null);
            eDate = null;
            System.out.print("The end date is on key press" + eDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateMonthlyPayment() {
        try {
            if (hrPayrollMaintainEds.getEndDate() == null) {
                monthlyPayment = total;
            } else {
                monthDifference = Integer.valueOf(hrPayrollEarningDeductionsLocal.returnNumberOfMonths(sDate, eDate));
                if (monthDifference == 0) {
                    monthlyPayment = total;
                } else {
                    monthlyPayment = total / monthDifference;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleKeyEvent() {
        text = text.toUpperCase();
    }
//</editor-fold>
}
