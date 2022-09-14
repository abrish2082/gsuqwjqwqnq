/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollAllEmpEdSetupsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
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
@Named(value = "generatePayroll")
@ViewScoped
public class GenaratePayroll implements Serializable {

    public GenaratePayroll() {
    }

//<editor-fold defaultstate="collapsed" desc="Object & Variables">
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @Inject
    HrEmployees hrEmployees;

    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
    HrPayrollAllEmpEdSetupsBeanLocal hrPayrollAllEmpEdSetupsFacade;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;

    private String fromCode;
    private String activePayrollDate;
    private String userName;
    private int numberOfDays = 0;
    private double totalAmount;
    private List<HrPayrollPeriods> payrollFrom;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void _init() {
        try {
            if (hrPayrollPeriodsLocal.activePayrollDate() != null) {
                hrPayrollPeriods = hrPayrollPeriodsLocal.activePayrollDate();
                activePayrollDate = hrPayrollPeriodsLocal.activePayrollDate().getPaymentMadeForTheMonthOf();
            } else {
                activePayrollDate = "[No Active Date is Defined]";
            }
            //start of individual
            payrollFrom = hrPayrollPeriodsLocal.findAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
    }

    public int getNumberOfDays() {
        System.out.print("The current date in EC is ==>" + StringDateManipulation.toDayInEc());
//        numberOfDays=StringDateManipulation.differenceInDays(StringDateManipulation.toDayInEc(),hrEmployees.getHireDate());
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void login() {
        System.out.println("The user name is  " + userName);
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

    public String getActivePayrollDate() {
        return activePayrollDate;
    }

    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void saveMonthlySalary() {
        try {
            if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary") == null) {
                JsfUtil.addErrorMessage("First Define the Gross Salary");
            } else {
                hrPayrollAllEmpEdSetupsFacade.saveMonthlySalary(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary"));
                JsfUtil.addSuccessMessage("Successfully Saved");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void savePension() {
        try {
            if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition") == null) {
                JsfUtil.addErrorMessage("First Define the Pension Addition");
            } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction") == null) {
                JsfUtil.addErrorMessage("First Define the Pension Deduction");
            } else {
                hrPayrollAllEmpEdSetupsFacade.savePension(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction"));
                JsfUtil.addSuccessMessage("Successfully Saved");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveTax() {
        try {
            if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax") == null) {
                JsfUtil.addErrorMessage("First Define the Tax");
            } else {
                hrPayrollAllEmpEdSetupsFacade.saveTaxAndPension(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction"));
                JsfUtil.addSuccessMessage("Successfully Saved");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveNetPay() {
        try {
            if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay") == null) {
                JsfUtil.addErrorMessage("First Define the Net Pay");
            } else {
                hrPayrollAllEmpEdSetupsFacade.saveNetPay(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay"));
                JsfUtil.addSuccessMessage("Successfully Saved");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveEarnings() {
        try {
            hrPayrollAllEmpEdSetupsFacade.saveEarnings(hrPayrollPeriods);
            JsfUtil.addSuccessMessage("Successfully Saved");
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addErrorMessage("System Error");
        }
    }

    public void clsePayrllPeriod() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "clsePayrllPeriod", dataset)) {
                if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                    JsfUtil.addErrorMessage("First Define Active payroll date");
                } else {
                    //When the payroll is closed the remaining month for the payment will be deduced
                    hrPayrollAllEmpEdSetupsFacade.updatePayedEd(activePayrollDate, hrPayrollPeriods);
                    hrPayrollAllEmpEdSetupsFacade.activateNextPayroll(activePayrollDate, hrPayrollPeriods);
                    hrPayrollPeriodsLocal.finalizePayroll(hrPayrollPeriods);
                    hrPayrollAllEmpEdSetupsFacade.generatePayrollSummery(activePayrollDate, hrPayrollPeriods);
                    JsfUtil.addSuccessMessage("Successfully Closed");
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
            JsfUtil.addErrorMessage("Error while closing the payroll");
        }
    }

    public void generateIndPay() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "generateIndPay", dataset)) {
                if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                    JsfUtil.addErrorMessage("First Define Active payroll date");
                } else {
                    if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay") == null) {
                        JsfUtil.addErrorMessage("First Define the Net Pay");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax") == null) {
                        JsfUtil.addErrorMessage("First Define the Tax");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition") == null) {
                        JsfUtil.addErrorMessage("First Define the Pension Addition");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction") == null) {
                        JsfUtil.addErrorMessage("First Define the Pension Deduction");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary") == null) {
                        JsfUtil.addErrorMessage("First Define the Gross Salary");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings") == null) {
                        JsfUtil.addErrorMessage("First Define the Total Earnings");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction") == null) {
                        JsfUtil.addErrorMessage("First Define the Total Deduction");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings") == null) {
                        JsfUtil.addErrorMessage("First Define the Total Taxable Earnings");
                    } else {
                        hrPayrollAllEmpEdSetupsFacade.deleteMonTrans(hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveEarnings(hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveTotalEarnings(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings"));
                        hrPayrollAllEmpEdSetupsFacade.saveDeductions(hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveTotalDed(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction"));
                        hrPayrollAllEmpEdSetupsFacade.saveMonthlySalary(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary"));
//                    hrPayrollAllEmpEdSetupsFacade.saveTaxAndPension(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction"));
//                    hrPayrollAllEmpEdSetupsFacade.saveIndTaxAndPension(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction"), numberOfDays, totalAmount);
                        hrPayrollAllEmpEdSetupsFacade.saveNetPay(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay"));
                        hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobLevel(activePayrollDate, hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobTitle(activePayrollDate, hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobLocation(activePayrollDate, hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.calcAndSavepymtRemMonht(activePayrollDate, hrPayrollPeriods);
                        //CALCULATE REMAINING MONTHS
                        JsfUtil.addSuccessMessage("Successfully Generated!");
                        //When The payroll is clsed the final remining months will be calculateed on the method clsePayrllPeriod();
                    }
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
        }
    }

    public void saveIndPayroll() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveIndPayroll", dataset)) {
                if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay") == null) {
                    JsfUtil.addFatalMessage("First Define the Net Pay");
                } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax") == null) {
                    JsfUtil.addFatalMessage("First Define the Tax");
                } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition") == null) {
                    JsfUtil.addFatalMessage("First Define the Pension Addition");
                } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction") == null) {
                    JsfUtil.addFatalMessage("First Define the Pension Deduction");
                } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary") == null) {
                    JsfUtil.addFatalMessage("First Define the Gross Salary");
                } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings") == null) {
                    JsfUtil.addFatalMessage("First Define the Total Earnings");
                } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction") == null) {
                    JsfUtil.addFatalMessage("First Define the Total Deduction");
                } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings") == null) {
                    JsfUtil.addFatalMessage("First Define the Total Taxable Earnings");
                } else if (hrEmployees.getEmpId() == null) {
                    JsfUtil.addFatalMessage("First search an employee!");
                } else if (totalAmount <= 0) {
                    JsfUtil.addFatalMessage("Amount should greater than 0!");
                } else if (numberOfDays <= 0) {
                    JsfUtil.addFatalMessage("Number of days should greater than 0!");
                } else {
                    //u need to correct the parameters datatype
                    hrPayrollAllEmpEdSetupsFacade.deleteIndMonTrans(hrPayrollPeriods, hrEmployees);
                    //u need also to delete from the individual earning deduction registration
//                    hrPayrollAllEmpEdSetupsFacade.saveIndMonthlySalary(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary"), numberOfDays, totalAmount, hrEmployees);
                    hrPayrollAllEmpEdSetupsFacade.saveIndEarning(hrPayrollPeriods, numberOfDays, totalAmount, hrEmployees);
                    hrPayrollAllEmpEdSetupsFacade.saveTotIndEarning(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings"), hrEmployees);
                    hrPayrollAllEmpEdSetupsFacade.saveIndDeductions(hrPayrollPeriods, numberOfDays, totalAmount, hrEmployees);
                    hrPayrollAllEmpEdSetupsFacade.saveTotIndDeductions(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction"), hrEmployees);
                    hrPayrollAllEmpEdSetupsFacade.saveIndSalary(hrPayrollPeriods, numberOfDays, totalAmount, hrEmployees, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction"));
                    hrPayrollAllEmpEdSetupsFacade.saveIndTaxAndPension(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction"), numberOfDays, totalAmount, hrEmployees);
                    hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobLevel(activePayrollDate, hrPayrollPeriods, hrEmployees, numberOfDays);
                    hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobTitle(activePayrollDate, hrPayrollPeriods, hrEmployees, numberOfDays);
                    hrPayrollAllEmpEdSetupsFacade.saveIndAllowanceInJobLocation(activePayrollDate, hrPayrollPeriods, hrEmployees, numberOfDays);
                    hrPayrollAllEmpEdSetupsFacade.saveIndNeyPay(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay"), numberOfDays, totalAmount, hrEmployees);
                    hrPayrollAllEmpEdSetupsFacade.calcIndAndSavepymtRemMonht(activePayrollDate, hrPayrollPeriods, hrEmployees);//correct the quesry already the emp id is passed correct on the where close
                    //u need to write a procedure for it not yet completed
                    Date date = new Date();
                    hrPayrollAllEmpEdSetupsFacade.savePayrollGeneratorsInfo(formatter.format(date), sessionBeanLocal.getUserId(), String.valueOf(hrPayrollPeriods.getId()));
                    //cheak the deduction set for selected employuee
//        public boolean saveIndSalary(HrPayrollPeriods payrollCode, int numberOfDays, double salary, HrEmployees empId, HrPayrollEarningDeductions hrPayrollEarningDeductions) ;
                    //TAX NET PAY AND calcAndSavepymtRemMonht IS LEFT
                    JsfUtil.addSuccessMessage("Individual Payroll Generated!");
                }
                //When The payroll is clsed the final remining months will be calculateed on the method clsePayrllPeriod();
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
        }
    }

    public void generateMonthlyPayrollForAllEmp() {
        try {
//            AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            if (security.checkAccess(sessionBeanLocal.getUserName(), "generateMonthlyPayrollForAllEmp", dataset)) {
                 System.out.println("Insede security");
                if (activePayrollDate.equalsIgnoreCase("[No Active Date is Defined]")) {
                    JsfUtil.addErrorMessage("First Define Active payroll date");
                } else {
                    if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay") == null) {
                        JsfUtil.addErrorMessage("First Define the Net Pay");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax") == null) {
                        JsfUtil.addErrorMessage("First Define the Tax");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition") == null) {
                        JsfUtil.addErrorMessage("First Define the Pension Addition");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction") == null) {
                        JsfUtil.addErrorMessage("First Define the Pension Deduction");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary") == null) {
                        JsfUtil.addErrorMessage("First Define the Gross Salary");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings") == null) {
                        JsfUtil.addErrorMessage("First Define the Total Earnings");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction") == null) {
                        JsfUtil.addErrorMessage("First Define the Total Deduction");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings") == null) {
                        JsfUtil.addErrorMessage("First Define the Total Taxable Earnings");
                    } else if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Ded first Made Before Tax") == null) {
                        JsfUtil.addErrorMessage("First Define Ded first Made Before Tax");
                    } else {
                        System.out.println("Insede else");
                        hrPayrollAllEmpEdSetupsFacade.deleteMonTrans(hrPayrollPeriods);
                        //Since it needs to be first maintained we first need to insert the transactions if so we can calculate tax and other
                        hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobLevel(activePayrollDate, hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobTitle(activePayrollDate, hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveAllowanceInJobLocation(activePayrollDate, hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveEarnings(hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveTotalEarnings(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Earnings"));
                        //newly added code
                        hrPayrollAllEmpEdSetupsFacade.saveTotalTaxableEarnings(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings"));
                        hrPayrollAllEmpEdSetupsFacade.saveDeductions(hrPayrollPeriods);
                        hrPayrollAllEmpEdSetupsFacade.saveTotalDed(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Deduction"));
                        //newly added code
                        hrPayrollAllEmpEdSetupsFacade.saveTotalDedFirstMadeBeforeTax(hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Ded first Made Before Tax"));
                        hrPayrollAllEmpEdSetupsFacade.saveMonthlySalary(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Gross Salary"));
                        hrPayrollAllEmpEdSetupsFacade.saveTaxAndPension(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Tax"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Total Taxable Earnings"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Addition"), hrPayrollEarningDeductionsLocal.findCriteriaInfo("Pension Deduction"));
                        hrPayrollAllEmpEdSetupsFacade.saveNetPay(activePayrollDate, hrPayrollPeriods, hrPayrollEarningDeductionsLocal.findCriteriaInfo("Net Pay"));
                        hrPayrollAllEmpEdSetupsFacade.calcAndSavepymtRemMonht(activePayrollDate, hrPayrollPeriods);
                        Date date = new Date();
                        hrPayrollAllEmpEdSetupsFacade.savePayrollGeneratorsInfo(formatter.format(date), sessionBeanLocal.getUserId(), String.valueOf(hrPayrollPeriods.getId()));
                        //CALCULATE REMAINING MONTHS
                        JsfUtil.addSuccessMessage("Successfully Generated!");
                        //When The payroll is clsed the final remining months will be calculateed on the method clsePayrllPeriod();
                    }
                }
//            } else {
//                EventEntry eventEntry = new EventEntry();
//                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
//                eventEntry.setUserId(sessionBeanLocal.getUserId());
//                QName qualifiedName = new QName("", "project");
//                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
//                eventEntry.setUserLogin(test);
//                security.addEventLog(eventEntry, dataset);
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveDeductions() {
        try {
            hrPayrollAllEmpEdSetupsFacade.saveDeductions(hrPayrollPeriods);
            JsfUtil.addSuccessMessage("Successfully Saved");
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addErrorMessage("System Error");
        }
    }

    //start of routines individual payments
    public void handleChangeToFrom() {
        try {
//             monthlyTransactions = hrPayrollMonthlyTransactionLocal.findAll();
        } catch (Exception e) {
        }
    }

    public void handleInd(ValueChangeEvent event) {
        try {
            fromCode = event.getNewValue().toString();
            BigDecimal big = new BigDecimal(fromCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);

        return employees;
    }

    public String returnDate(String date, String separator) {
        String[] dates = null;
        dates = date.split("/");
        return dates[0] + separator + dates[1] + separator + dates[2];
    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeeBean.getByEmpId(hrEmployees);
            //this ia going to be effective when the current date is correctedS
            numberOfDays = StringDateManipulation.differenceInYears(returnDate(activePayrollDate, "-"), returnDate(hrEmployees.getHireDate(), "-"));
            totalAmount = Double.valueOf(hrEmployees.getBasicSalary().longValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>
}
