/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.hrms;

import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.mapper.FmsLuBudgetYearFacade;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.ifrs.businessLogic.hrms.SeveranceLiabilityLocal;
import et.gov.eep.ifrs.entity.HrEmployeeSeverance;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author User
 */
@Named(value = "severanceLiabilityController")
@ViewScoped
public class SeveranceLiabilityController implements Serializable {

    @Inject
    HrEmployeeSeverance hrEmployeeSeverance;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    private HrEmployees employee;
    @Inject
    SessionBean SessionBean;
    @Inject
    private FmsAccountingPeriod accountingPeriod;
    @EJB
    private FmsLuBudgetYearBeanLocal budgetYearBeanLocal;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
    @EJB
    FmsLuBudgetYearFacade budgetYearFacadeLocal;
    @EJB
    SeveranceLiabilityLocal SeveranceLiabilityLocal;
    @EJB
    HrEmployeeBeanLocal profileBeanLocal;

    DataModel<HrEmployeeSeverance> SeveranceLiablilityDataModel;
    List<HrEmployeeSeverance> severanceList = new ArrayList<>();

    List<FmsLuBudgetYear> budgetYears = new ArrayList<>();
    List<FmsLuBudgetYear> budgetYearsForAccruedLeave = new ArrayList<>();
    List<HrEmployees> hrEmployeeList = new ArrayList<>();

    String budjetEnd;
    boolean balanceButonShow = false;
    Calendar cal;
    String CurrentYear;

    /**
     * Creates a new instance of SeveranceLiabilityController
     */
    @PostConstruct
    public void loadbudjets() {

        try {
            FmsAccountingPeriod fap = new FmsAccountingPeriod();
            fap = accountingPeriodBeanLocal.getCurretActivePeriod();
            budgetYears = null;
            budgetYears = new ArrayList<>();
            budgetYears = budgetYearFacadeLocal.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SeveranceLiabilityController() {
    }

    //<editor-fold defaultstate="collapsed" desc="getter &&Setter">
    public HrEmployeeSeverance getHrEmployeeSeverance() {
        return hrEmployeeSeverance;
    }

    public void setHrEmployeeSeverance(HrEmployeeSeverance hrEmployeeSeverance) {
        this.hrEmployeeSeverance = hrEmployeeSeverance;
    }

    public List<FmsLuBudgetYear> getBudgetYearsForAccruedLeave() {
        String today[] = StringDateManipulation.currentDateInGC().split("-");
        int recentYear = Integer.valueOf(today[0]);
        for (int i = 0; i < budgetYears.size(); i++) {
            String year[] = budgetYears.get(i).getBudgetYear().split("-");
            int IntYear = Integer.valueOf(year[1]);
            if (IntYear <= recentYear) {
                budgetYearsForAccruedLeave.add(budgetYears.get(i));
            }

        }

        if (budgetYearsForAccruedLeave == null) {
            budgetYearsForAccruedLeave = new ArrayList<>();
        }
        return budgetYearsForAccruedLeave;
    }

    public void setBudgetYearsForAccruedLeave(List<FmsLuBudgetYear> budgetYearsForAccruedLeave) {
        this.budgetYearsForAccruedLeave = budgetYearsForAccruedLeave;
    }

    public List<HrEmployeeSeverance> getSeveranceList() {
        return severanceList;
    }

    public void setSeveranceList(List<HrEmployeeSeverance> severanceList) {
        this.severanceList = severanceList;
    }

    public boolean isBalanceButonShow() {
        return balanceButonShow;
    }

    public void setBalanceButonShow(boolean balanceButonShow) {
        this.balanceButonShow = balanceButonShow;
    }

    public FmsLuBudgetYear getFmsLuBudgetYear() {
        return fmsLuBudgetYear;
    }

    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }

    public FmsAccountingPeriod getAccountingPeriod() {
        return accountingPeriod;
    }

    public void setAccountingPeriod(FmsAccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public HrEmployees getEmployee() {
        return employee;
    }

    public void setEmployee(HrEmployees employee) {
        this.employee = employee;
    }

    public DataModel<HrEmployeeSeverance> getSeveranceLiablilityDataModel() {
        return SeveranceLiablilityDataModel;
    }

    public void setSeveranceLiablilityDataModel(DataModel<HrEmployeeSeverance> SeveranceLiablilityDataModel) {
        this.SeveranceLiablilityDataModel = SeveranceLiablilityDataModel;
    }

    public List<FmsLuBudgetYear> getBudgetYears() {
        return budgetYears;
    }

    public void setBudgetYears(List<FmsLuBudgetYear> budgetYears) {
        this.budgetYears = budgetYears;
    }

//</editor-fold>
    int empServiceInYears = 0;
    int monthired;
    int yearhired;

    public int generateEmpServiceInYears(HrEmployees empl) {
        try {
            String hired[] = empl.getHireDate().split("/");
//            System.err.println("=============accunting  period============" + budjetEnd);
            if (hired != null && budjetEnd != null) {
                int dayhired = Integer.parseInt(hired[0]);
                monthired = Integer.parseInt(hired[1]);
                yearhired = Integer.parseInt(hired[2]);
                empServiceInYears = StringDateManipulation.getDuration(monthired, dayhired, yearhired, budjetEnd);
//                System.err.println("=================Duration==============>" + empServiceInYears + "" + "Years");
            } else {
                JsfUtil.addFatalMessage("Hire Date budjet year is null");

            }
            return empServiceInYears;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    public void recreateAccruedLeavebalanceModel() {
        SeveranceLiablilityDataModel = null;
        SeveranceLiablilityDataModel = new ListDataModel(new ArrayList<>(severanceList));
        System.out.println("accruedbalanceDataModel==" + SeveranceLiablilityDataModel.getRowCount());
    }
    private Integer totalSeverance = 0;
    BigInteger totalSeveranceInBI;
    private String totalSeveranceIndecimal;

//<editor-fold defaultstate="collapsed" desc="gett and sett">
    public Integer getTotalSeverance() {
        return totalSeverance;
    }

    public void setTotalSeverance(Integer totalSeverance) {
        this.totalSeverance = totalSeverance;
    }

    public String getTotalSeveranceIndecimal() {
        return totalSeveranceIndecimal;
    }

    public void setTotalSeveranceIndecimal(String totalSeveranceIndecimal) {
        this.totalSeveranceIndecimal = totalSeveranceIndecimal;
    }

    public BigInteger getTotalSeveranceInBI() {
        return totalSeveranceInBI;
    }

    public void setTotalSeveranceInBI(BigInteger totalSeveranceInBI) {
        this.totalSeveranceInBI = totalSeveranceInBI;
    }

//</editor-fold>
    public void getLeaveYear(ValueChangeEvent event) {
        String accountingEndDate = "";
        String budgetEndDate = "";
        totalSeverance = 0;
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear = null;
            fmsLuBudgetYear = (FmsLuBudgetYear) event.getNewValue();
            //fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearBeanLocal.findByBudjetYear(fmsLuBudgetYear);

            accountingPeriod = accountingPeriodBeanLocal.findAccountingPeriodByBudjetYear(fmsLuBudgetYear);
            accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
            accountingEndDate = accountingPeriod.getEndDate();
            budjetEnd = GregorianCalendarManipulation.gregorianToEthiopian(accountingEndDate);
            System.out.println("fmsLuBudgetYear==" + fmsLuBudgetYear.getBudgetYear());
            severanceList = SeveranceLiabilityLocal.findBYBudgetYear(fmsLuBudgetYear);
            //fmsLuBudgetYear = leaveBalance.getLeaveYearId();
            //  fmsLuBudgetYear = budgetYearBeanLocal.findByBudjetYearById(fmsLuBudgetYear);
            String today[] = StringDateManipulation.currentDateInGC().split("-");
            int recentYear = Integer.valueOf(today[0]);

            String selectedYear[] = fmsLuBudgetYear.getBudgetYear().split("-");
            int selectedbudgetYear = Integer.valueOf(selectedYear[1]);

            System.out.println("recentYear=====" + recentYear);

            if (severanceList.size() > 0) {
                balanceButonShow = false;
                for (int i = 0; i < severanceList.size(); i++) {
                    totalSeverance = totalSeverance + Integer.valueOf(severanceList.get(i).getSeveranceAmount().intValue());
                }
                recreateAccruedLeavebalanceModel();
         } 
     //           else if (recentYear > selectedbudgetYear-1) {
//                recreateAccruedLeavebalanceModel();
//                JsfUtil.addFatalMessage(selectedbudgetYear-1 + "Severance Liability haven't been Reported");
//            }
            else {

                hrEmployeeList = profileBeanLocal.findActiveEmployees();
                System.out.println("emp size=" + hrEmployeeList.size());
                for (int i = 0; i < hrEmployeeList.size(); i++) {
                    HrEmployees emp = new HrEmployees();
                    hrEmployeeSeverance = new HrEmployeeSeverance();
                    emp = hrEmployeeList.get(i);
                    hrEmployeeSeverance.setEmployeeId(emp);
                    hrEmployeeSeverance.setBudgetYear(fmsLuBudgetYear.getBudgetYear());
                    hrEmployeeSeverance.setExprience(generateEmpServiceInYears(emp));
                    if (hrEmployeeSeverance.getExprience() >= 5) {
                        double yearlySalary = 0;
                        yearlySalary = hrEmployeeSeverance.getEmployeeId().getBasicSalary() * 12;
                        Double severance = 0.0;
                        for (int j = 0; j < hrEmployeeSeverance.getExprience(); j++) {
                            if (j == 0) {
                                severance = hrEmployeeSeverance.getEmployeeId().getBasicSalary();
                            } else {
                                severance = severance + ((hrEmployeeSeverance.getEmployeeId().getBasicSalary()) / 3);
                            }
                        }
                        if (severance > yearlySalary) {
                            severance = yearlySalary;
                        }

                        totalSeverance = totalSeverance + Integer.valueOf(severance.intValue());

                        severance = Math.rint(severance * 100) / 100;

                        hrEmployeeSeverance.setSeveranceAmount(severance);
                        severanceList.add(hrEmployeeSeverance);
                    }

                }
                balanceButonShow = true;
                recreateAccruedLeavebalanceModel();
            }
            totalSeveranceIndecimal = insertCommas(new Integer(totalSeverance));
            totalSeveranceInBI = new BigInteger(Integer.toString(totalSeverance));

            System.out.println("totalSeverance==" + totalSeveranceInBI);
            System.out.println("totalSeveranceIndecimal==" + totalSeveranceIndecimal);
            System.gc();
        }
    }

    private static String insertCommas(Integer number) {
        System.out.println(" insideinsertCommas== " + number);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println(" output== " + df.format(number));
        return df.format(number);
    }

    public SelectItem[] getLeavetYearList() {
        return JsfUtil.getSelectItems(budgetYears, true);
    }

    public void saveSeveranceLiability() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveSeveranceLiability", dataset)) {
                System.out.println("inside save");
                for (int i = 0; i < severanceList.size(); i++) {
                    hrEmployeeSeverance = new HrEmployeeSeverance();
                    hrEmployeeSeverance = severanceList.get(i);
                    SeveranceLiabilityLocal.save(hrEmployeeSeverance);
                }
                fmsLuBudgetYear = new FmsLuBudgetYear();
                JsfUtil.addSuccessMessage("Data saved Sucessfully");
                System.gc();
                setSeveranceLiablilityDataModel(null);
            }else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);

                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMassSalIncDetailInfo");
                eventEntry.setProgram(program);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);

            }
        }catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Unable to Modify data");
        }
            
           
        }

        }
