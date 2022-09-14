/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.termination;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.termination.CompensationBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.leave.HrLeaveBalance;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;
import et.gov.eep.hrms.entity.termination.HrEmpCompensationDetail;
import et.gov.eep.hrms.entity.termination.HrEmployeeCompensation;
import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import et.gov.eep.hrms.mapper.leave.HrLeaveBalanceFacade;
import et.gov.eep.hrms.mapper.transfer.HrEmpInternalHistoryFacade;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollTaxRatesBeanLocal;

/**
 *
 * @author User
 */
@Named(value = "compensationController")
@ViewScoped
public class CompensationController implements Serializable {

    @EJB
    HrLeaveBalanceFacade hrLeaveBalanceFacade;

    @EJB
    CompensationBeanLocal compensationBeanLocal;
    @Inject
    HrPayrollTaxRates hrPayrollTaxRates;
    @EJB
    HrEmployeeBeanLocal profileBeanLocal;
    @Inject
    SessionBean SessionBean;
    @Inject
    private HrEmployees employee;
    @Inject
    private HrLeaveBalance leaveBalance;
    @Inject
    HrEmpCompensationDetail hrEmpCompensationDetail;
    @Inject
    HrEmployeeCompensation hrEmployeeCompensation;

    @Inject
    HrEmpInternalHistory hrEmpInternalHistory;
    @EJB
    HrPayrollTaxRatesBeanLocal hrPayrollTaxRatesFacadeLocal;

    @EJB
    HrEmpInternalHistoryFacade hrEmpInternalHistoryFacade;
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private String addorUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private int serviceYear = 0;
    private Integer remainingleaveDays;
    private String Today;
    private boolean renderSave = false;
    //serecanceClass serecanceClassobj;
    DataModel<HrEmployeeCompensation> CompensationDataModel;
    DataModel<HrLeaveBalance> balanceDataModel;

    DataModel<HrEmpCompensationDetail> CompensationDetailDataModel;
    List<HrEmpCompensationDetail> CompensationdetailList = new ArrayList<>();
    List<HrLeaveBalance> balanceList = new ArrayList<>();
    List<serecanceClass> compList = new ArrayList<>();
    private Integer totalAccruedLeaveLiability = 0;
    double LeaveAccruedAtOldSalary = 0;
    double LeaveAccruedAtNewSalary = 0;
    private String totalAccruedLeaveIndecimal;
    private String fullName;

    @PostConstruct
    public void Init() {

        Today = StringDateManipulation.toDayInEc();
        hrEmployeeCompensation.setProcessDate(Today);
        setFullName(SessionBean.getUserName());
    }

    public class serecanceClass implements Serializable {

        private Double firstYearAmount1 = 0.0;
        private Double YearsAfterFirstYearAmount1 = 0.0;
        private Double MonthsAmount1 = 0.0;
        private Double DaysAmount1 = 0.0;

        public serecanceClass(Double FY, Double YAFY, Double MA, Double DA) {
            System.out.println("inside constructor" + FY + YAFY + MA + DA);
            this.DaysAmount1 = DA;
            this.MonthsAmount1 = MA;
            this.YearsAfterFirstYearAmount1 = YAFY;
            this.firstYearAmount1 = FY;
        }

//<editor-fold defaultstate="collapsed" desc="getter and setter">
        public Double getFirstYearAmount1() {
            return firstYearAmount1;
        }

        public void setFirstYearAmount1(Double firstYearAmount1) {
            this.firstYearAmount1 = firstYearAmount1;
        }

        public Double getYearsAfterFirstYearAmount1() {
            return YearsAfterFirstYearAmount1;
        }

        public void setYearsAfterFirstYearAmount1(Double YearsAfterFirstYearAmount1) {
            this.YearsAfterFirstYearAmount1 = YearsAfterFirstYearAmount1;
        }

        public Double getMonthsAmount1() {
            return MonthsAmount1;
        }

        public void setMonthsAmount1(Double MonthsAmount1) {
            this.MonthsAmount1 = MonthsAmount1;
        }

        public Double getDaysAmount1() {
            return DaysAmount1;
        }

        public void setDaysAmount1(Double DaysAmount1) {
            this.DaysAmount1 = DaysAmount1;
        }

        public String getFirstYearDuration() {
            return firstYearDuration;
        }

    }

    public List<serecanceClass> getCompList() {
        return compList;
    }

    public void setCompList(List<serecanceClass> compList) {
        this.compList = compList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public DataModel<HrEmpCompensationDetail> getCompensationDetailDataModel() {
        return CompensationDetailDataModel;
    }

    public void setCompensationDetailDataModel(DataModel<HrEmpCompensationDetail> CompensationDetailDataModel) {
        this.CompensationDetailDataModel = CompensationDetailDataModel;
    }

    public List<HrEmpCompensationDetail> getCompensationdetailList() {
        return CompensationdetailList;
    }

    public void setCompensationdetailList(List<HrEmpCompensationDetail> CompensationdetailList) {
        this.CompensationdetailList = CompensationdetailList;
    }

    public CompensationBeanLocal getCompensationBeanLocal() {
        return compensationBeanLocal;
    }

    public void setCompensationBeanLocal(CompensationBeanLocal compensationBeanLocal) {
        this.compensationBeanLocal = compensationBeanLocal;
    }

    public boolean isRenderSave() {
        return renderSave;
    }

    public void setRenderSave(boolean renderSave) {
        this.renderSave = renderSave;
    }

    public HrEmpCompensationDetail getHrEmpCompensationDetail() {
        return hrEmpCompensationDetail;
    }

    public void setHrEmpCompensationDetail(HrEmpCompensationDetail hrEmpCompensationDetail) {
        this.hrEmpCompensationDetail = hrEmpCompensationDetail;
    }

    public DataModel<HrLeaveBalance> getBalanceDataModel() {
        return balanceDataModel;
    }

    public void setBalanceDataModel(DataModel<HrLeaveBalance> balanceDataModel) {
        this.balanceDataModel = balanceDataModel;
    }

    public List<HrLeaveBalance> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<HrLeaveBalance> balanceList) {
        this.balanceList = balanceList;
    }

    public int getEmpServiceInYears() {
        return empServiceInYears;
    }

    public void setEmpServiceInYears(int empServiceInYears) {
        this.empServiceInYears = empServiceInYears;
    }

    public String getToday() {
        return Today;
    }

    public void setToday(String Today) {
        this.Today = Today;
    }

    public Integer getRemainingleaveDays() {
        return remainingleaveDays;
    }

    public void setRemainingleaveDays(Integer remainingleaveDays) {
        this.remainingleaveDays = remainingleaveDays;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String Experience) {
        this.Experience = Experience;
    }

    public int getExpday() {
        return expday;
    }

    public void setExpday(int expday) {
        this.expday = expday;
    }

    public int getExpInYear() {
        return expInYear;
    }

    public void setExpInYear(int expInYear) {
        this.expInYear = expInYear;
    }

    public int getExpInMonth() {
        return expInMonth;
    }

    public void setExpInMonth(int expInMonth) {
        this.expInMonth = expInMonth;
    }

    public int getExpInYearAfetrFirst() {
        return expInYearAfetrFirst;
    }

    public void setExpInYearAfetrFirst(int expInYearAfetrFirst) {
        this.expInYearAfetrFirst = expInYearAfetrFirst;
    }

    public DataModel<HrEmployeeCompensation> getCompensationDataModel() {
        return CompensationDataModel;
    }

    public void setCompensationDataModel(DataModel<HrEmployeeCompensation> CompensationDataModel) {
        this.CompensationDataModel = CompensationDataModel;
    }

    public HrEmployees getEmployee() {
        return employee;
    }

    public void setEmployee(HrEmployees employee) {
        this.employee = employee;
    }

    public HrLeaveBalance getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(HrLeaveBalance leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public HrEmployeeCompensation getHrEmployeeCompensation() {
        return hrEmployeeCompensation;
    }

    public void setHrEmployeeCompensation(HrEmployeeCompensation hrEmployeeCompensation) {
        this.hrEmployeeCompensation = hrEmployeeCompensation;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }
//</editor-fold>

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                clear();
                searchPage = false;
                newPage = true;
                System.out.println("newPage===" + newPage);
                System.out.println("searchPage===" + searchPage);
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                clear();
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                System.out.println("newPage===" + newPage);
                System.out.println("searchPage===" + searchPage);
                break;
        }
    }

    public void clear() {
        hrEmployeeCompensation = new HrEmployeeCompensation();
        employee = new HrEmployees();
        //Exprience = "";
        Experience = "";
        balanceDataModel = new ArrayDataModel<>();
        CompensationDetailDataModel = null;
        compList = new ArrayList<>();
        severanceDecimal = "";
        AccruedLeaveAmountDecimal = "";
        Compensationdecimal = "";
        totalLeavedays = 0;
        expInMonth = 0;
        expInYear = 0;
        expInday = 0;
        expday = 0;
        expInYearAfetrFirst = 0;

    }

    public List<HrEmployees> searchEmployee(String employeename) {
        employee = new HrEmployees();
        List<HrEmployees> registeredEmployee = null;
        employee.setEmpId(employeename);
        registeredEmployee = profileBeanLocal.SearchByEmpId(employee);
        return registeredEmployee;

    }
    Integer remaininDays = 0;
    Double firstYearAmount = 0.0;
    Double YearsAfterFirstYearAmount = 0.0;
    Double MonthsAmount;
    Double DaysAmount;
    Double severance = 0.0;
    Double AccruedLeaveAmount = 0.0;
    double totalLeavedays = 0;
    Double pension;
    Double Tax;
    Double NetPay;

    private String AccruedLeaveAmountDecimal;
    private String severanceDecimal;
    private String Compensationdecimal;

    private String firstYearDuration;
    private String YearsAfterFirstYearDuration;
    private String MonthsDuration;
    private String DaysDuration;

    //<editor-fold defaultstate="collapsed" desc="GetterAndSetter">
    public Double getFirstYearAmount() {
        return firstYearAmount;
    }

    public void setFirstYearAmount(Double firstYearAmount) {
        this.firstYearAmount = firstYearAmount;
    }

    public Double getPension() {
        return pension;
    }

    public void setPension(Double pension) {
        this.pension = pension;
    }

    public Double getTax() {
        return Tax;
    }

    public void setTax(Double Tax) {
        this.Tax = Tax;
    }

    public Double getNetPay() {
        return NetPay;
    }

    public void setNetPay(Double NetPay) {
        this.NetPay = NetPay;
    }

    public double getTotalLeavedays() {
        return totalLeavedays;
    }

    public void setTotalLeavedays(double totalLeavedays) {
        this.totalLeavedays = totalLeavedays;
    }

    public String getFirstYearDuration() {
        return firstYearDuration;
    }

    public void setFirstYearDuration(String firstYearDuration) {
        this.firstYearDuration = firstYearDuration;
    }

    public String getYearsAfterFirstYearDuration() {
        return YearsAfterFirstYearDuration;
    }

    public void setYearsAfterFirstYearDuration(String YearsAfterFirstYearDuration) {
        this.YearsAfterFirstYearDuration = YearsAfterFirstYearDuration;
    }

    public String getMonthsDuration() {
        return MonthsDuration;
    }

    public void setMonthsDuration(String MonthsDuration) {
        this.MonthsDuration = MonthsDuration;
    }

    public String getDaysDuration() {
        return DaysDuration;
    }

    public void setDaysDuration(String DaysDuration) {
        this.DaysDuration = DaysDuration;
    }

    public String getAccruedLeaveAmountDecimal() {
        return AccruedLeaveAmountDecimal;
    }

    public void setAccruedLeaveAmountDecimal(String AccruedLeaveAmountDecimal) {
        this.AccruedLeaveAmountDecimal = AccruedLeaveAmountDecimal;
    }

    public String getSeveranceDecimal() {
        return severanceDecimal;
    }

    public void setSeveranceDecimal(String severanceDecimal) {
        this.severanceDecimal = severanceDecimal;
    }

    public String getCompensationdecimal() {
        return Compensationdecimal;
    }

    public void setCompensationdecimal(String Compensationdecimal) {
        this.Compensationdecimal = Compensationdecimal;
    }

    public Double getSeverance() {
        return severance;
    }

    public void setSeverance(Double severance) {
        this.severance = severance;
    }

    public Double getAccruedLeaveAmount() {
        return AccruedLeaveAmount;
    }

    public void setAccruedLeaveAmount(Double AccruedLeaveAmount) {
        this.AccruedLeaveAmount = AccruedLeaveAmount;
    }

    public Double getYearsAfterFirstYearAmount() {
        return YearsAfterFirstYearAmount;
    }

    public void setYearsAfterFirstYearAmount(Double YearsAfterFirstYearAmount) {
        this.YearsAfterFirstYearAmount = YearsAfterFirstYearAmount;
    }

    public Double getMonthsAmount() {
        return MonthsAmount;
    }

    public void setMonthsAmount(Double MonthsAmount) {
        this.MonthsAmount = MonthsAmount;
    }

    public Double getDaysAmount() {
        return DaysAmount;
    }

    public void setDaysAmount(Double DaysAmount) {
        this.DaysAmount = DaysAmount;
    }

//</editor-fold>
    public void getEmployeeInfo(SelectEvent event) {

//        
        String empId = event.getObject().toString();
        HrEmployees employeeObj = new HrEmployees();
        employeeObj.setEmpId(empId);
        employee = profileBeanLocal.getByEmpId(employeeObj);
        compList = new ArrayList<>();

        hrEmployeeCompensation = compensationBeanLocal.findByEmpId(employee);
        if (hrEmployeeCompensation != null) {
            balanceList = new ArrayList<>();
            ServiceYearCalculator(employee);
            serviceYear = expInYear;
            //serviceYear = generateEmpServiceInYears(employee);
            System.out.println("serviceYear=====" + serviceYear);
            //  Exprience = serviceYear + "year";
            Compensationdecimal = insertCommas(new Integer((int) Math.rint(hrEmployeeCompensation.getCompensationAmount())));
            AccruedLeaveAmountDecimal = insertCommas(new Integer((int) Math.rint(hrEmployeeCompensation.getAccruedLeaveInBirr())));
            severanceDecimal = insertCommas(new Integer((int) Math.rint(hrEmployeeCompensation.getSeveranceAmount())));
            serecanceClass serecanceClassobj = new serecanceClass(hrEmployeeCompensation.getFirst_year_sev_amount(), hrEmployeeCompensation.getOther_years_sev_amount(), hrEmployeeCompensation.getMonths_sev_amount(), hrEmployeeCompensation.getDays_sev_amount());
            compList.add(serecanceClassobj);
//            if (hrEmployeeCompensation.getHrEmpCompensationDetailList().size() > 0) {
//                for (int i = 0; i < hrEmployeeCompensation.getHrEmpCompensationDetailList().size(); i++) {
//                    leaveBalance = hrEmployeeCompensation.getHrEmpCompensationDetailList().get(i).getLeaveBalaceId();
//                    balanceList.add(leaveBalance);
//                }
//
//               
//            }

            expInYearAfetrFirst = expInYear - 1;

            recreateBalanceDataModel();
            renderSave = true;
            totalLeavedays = 0;
            if (hrEmployeeCompensation.getHrEmpCompensationDetailList().size() > 0) {
                for (int i = 0; i < hrEmployeeCompensation.getHrEmpCompensationDetailList().size(); i++) {
                    hrEmpCompensationDetail = new HrEmpCompensationDetail();
                    hrEmpCompensationDetail = hrEmployeeCompensation.getHrEmpCompensationDetailList().get(i);

                    totalLeavedays = totalLeavedays + hrEmpCompensationDetail.getAccruedLeavedays();
                }
                String hiredate[] = hrEmployeeCompensation.getEmpId().getHireDate().split("/");
                int hYY = Integer.parseInt(hiredate[2]);
                int hMM = Integer.parseInt(hiredate[1]);
                int hDD = Integer.parseInt(hiredate[0]);
                String processDate[] = hrEmployeeCompensation.getProcessDate().split("-");
                int cYY = Integer.parseInt(processDate[0]);
                int cMM = Integer.parseInt(processDate[1]);
                int cDD = Integer.parseInt(processDate[2]);
                if (hDD - 1 > 0) {
                    firstYearDuration = hrEmployeeCompensation.getEmpId().getHireDate() + "-" + (hDD - 1) + "/" + hMM + "/" + (hYY + 1);
                    YearsAfterFirstYearDuration = hDD + "/" + hMM + "/" + (hYY + 1) + "-" + (hDD - 1) + "/" + hMM + "/" + (cYY - 1);
                    MonthsDuration = (hDD) + "/" + hMM + "/" + (cYY - 1) + "-" + (hDD - 1) + "/" + cMM + "/" + (cYY);
                    DaysDuration = (hDD) + "/" + cMM + "/" + (cYY) + "-" + (cDD) + "/" + cMM + "/" + (cYY);
                }

                if (employee.getBasicSalary() < 10900) {
                    hrPayrollTaxRates = hrPayrollTaxRatesFacadeLocal.findBySalaryRange(employee.getBasicSalary());
                    System.out.println("hrPayrollTaxRates.getRateInPerercent()==" + hrPayrollTaxRates.getRateInPerercent());
                    System.out.println("hrPayrollTaxRates.getConstantAmount()==" + hrPayrollTaxRates.getConstantAmount());

                    Tax = ((employee.getBasicSalary() * hrPayrollTaxRates.getRateInPerercent()) / 100) - (hrPayrollTaxRates.getConstantAmount());
                    pension = (7 * employee.getBasicSalary()) / 100;
                    NetPay = employee.getBasicSalary() - (Tax + pension);
                } else {
                    Tax = ((employee.getBasicSalary() * 35) / 100) - 1500;
                    pension = (7 * employee.getBasicSalary()) / 100;
                    NetPay = employee.getBasicSalary() - (Tax + pension);
                }

            }

        } else {
            hrEmployeeCompensation = new HrEmployeeCompensation();
            hrEmployeeCompensation.setEmpId(employee);
            hrEmployeeCompensation.setProcessDate(StringDateManipulation.toDayInEc());

            ServiceYearCalculator(employee);
            serviceYear = expInYear;
            //serviceYear = generateEmpServiceInYears(employee);
            System.out.println("serviceYear=====" + serviceYear);
            //  Exprience = serviceYear + "year";

            if (serviceYear >= 5) {

                firstYearAmount = hrEmployeeCompensation.getEmpId().getBasicSalary();
                YearsAfterFirstYearAmount = ((hrEmployeeCompensation.getEmpId().getBasicSalary()) / 3) * (serviceYear - 1);
                YearsAfterFirstYearAmount = Math.rint(YearsAfterFirstYearAmount * 100) / 100;
                Double DexpInMonth = new Double(expInMonth);
                Double Dexpday = new Double(expday);
                expInYearAfetrFirst = expInYear - 1;
                MonthsAmount = (DexpInMonth / 12) * ((hrEmployeeCompensation.getEmpId().getBasicSalary()) / 3);
                MonthsAmount = Math.rint(MonthsAmount * 100) / 100;
                DaysAmount = (Dexpday / 365) * ((hrEmployeeCompensation.getEmpId().getBasicSalary()) / 3);
                DaysAmount = Math.rint(DaysAmount * 100) / 100;
                severance = firstYearAmount + YearsAfterFirstYearAmount + MonthsAmount + DaysAmount;
                String hiredate[] = hrEmployeeCompensation.getEmpId().getHireDate().split("/");
                int hYY = Integer.parseInt(hiredate[2]);
                int hMM = Integer.parseInt(hiredate[1]);
                int hDD = Integer.parseInt(hiredate[0]);
                String processDate[] = hrEmployeeCompensation.getProcessDate().split("-");
                int cYY = Integer.parseInt(processDate[0]);
                int cMM = Integer.parseInt(processDate[1]);
                int cDD = Integer.parseInt(processDate[2]);
                if (hDD - 1 > 0) {
                    firstYearDuration = hrEmployeeCompensation.getEmpId().getHireDate() + "-" + (hDD - 1) + "/" + hMM + "/" + (hYY + 1);
                    YearsAfterFirstYearDuration = hDD + "/" + hMM + "/" + (hYY + 1) + "-" + (hDD - 1) + "/" + hMM + "/" + (cYY - 1);
                    if (cMM < hMM) {
                        MonthsDuration = (hDD) + "/" + hMM + "/" + (cYY - 1) + "-" + (hDD - 1) + "/" + cMM + "/" + (cYY);
                    } else {
                        MonthsDuration = (hDD) + "/" + hMM + "/" + (cYY) + "-" + (hDD - 1) + "/" + cMM + "/" + (cYY);
                    }
                    DaysDuration = (hDD) + "/" + cMM + "/" + (cYY) + "-" + (cDD) + "/" + cMM + "/" + (cYY);
                }

                serecanceClass serecanceClassobj = new serecanceClass(firstYearAmount, YearsAfterFirstYearAmount, MonthsAmount, DaysAmount);
//                serecanceClassobj.setFirstYearAmount1(firstYearAmount);
//                serecanceClassobj.setYearsAfterFirstYearAmount1(YearsAfterFirstYearAmount);
//                serecanceClassobj.setDaysAmount1(DaysAmount);
//                serecanceClassobj.setMonthsAmount1(MonthsAmount);
                compList.add(serecanceClassobj);

                Double yearlySal = 0.0;
                yearlySal = employee.getBasicSalary() * 12;
                if (severance > yearlySal) {
                    severance = yearlySal;
                }
            }

            severance = Math.rint(severance * 100) / 100;
            hrEmployeeCompensation.setSeveranceAmount(severance);

            //populateLeaveList();
            loadBalance();
            AccruedLeaveAmount = 0.0;
            totalLeavedays = 0;
            Double accruedLeaveInBirr = 0.0;
            Double accruedLeaveAmountOfAYear = 0.0;
            double totalLeavedaysOfaYear = 0;

            if (balanceList.size() > 0) {
                for (int i = 0; i < balanceList.size(); i++) {
                    accruedLeaveAmountOfAYear = 0.0;
                    totalLeavedaysOfaYear = 0;
                    leaveBalance = new HrLeaveBalance();
                    HrEmpInternalHistory emphistory = new HrEmpInternalHistory();
                    leaveBalance = balanceList.get(i);
                    LeaveAccruedAtOldSalary = 0;
                    LeaveAccruedAtNewSalary = 0;
                    double oldSalary;
                    double salary = 0;
                    String curentyear[] = Today.split("-");
                    int thisyear = Integer.parseInt(curentyear[0]);
                    String reccyear[] = leaveBalance.getRecordDate().split("-");
                    int reccordyear = Integer.parseInt(reccyear[0]);

                    if (thisyear - 1 == reccordyear) {

                        int thisyearCompleteWorkdays = dateDiffrence(reccordyear + "-11-1", StringDateManipulation.toDayInEc());
                        System.out.println("thisyearCompleteWorkdays====" + thisyearCompleteWorkdays);
                        double dailyAccrue = (balanceList.get(i).getTotalDays() / 365);
                        System.out.println("dailyAccrue=====" + dailyAccrue);
                        totalLeavedaysOfaYear = dailyAccrue * thisyearCompleteWorkdays;
                        totalLeavedays = totalLeavedays + dailyAccrue * thisyearCompleteWorkdays;
                        System.out.println("totalLeavedays=====" + totalLeavedays);
                        if (dailyAccrue * thisyearCompleteWorkdays > balanceList.get(i).getRemainingDays()) {
                            totalLeavedaysOfaYear = balanceList.get(i).getRemainingDays();
                            totalLeavedays = balanceList.get(i).getRemainingDays();
                        }

                    } else {
                        totalLeavedaysOfaYear = balanceList.get(i).getRemainingDays();
                        totalLeavedays = totalLeavedays + balanceList.get(i).getRemainingDays();
                    }

                    if (EmpHistory(balanceList.get(i).getEmployeeId().getId()) != null) {
                        emphistory = EmpHistory(balanceList.get(i).getEmployeeId().getId());
                        oldSalary = emphistory.getOldSalary();
                        double dailyLeaveAccrue = balanceList.get(i).getTotalDays() / 365;

                        String recyear[] = StringDateManipulation.toDayInEc().split("-");
                        String record[] = leaveBalance.getRecordDate().split("-");
                        int recordyear = Integer.parseInt(record[0]);
                        String RecDate = recordyear + "/10/30";

                        String processydate[] = emphistory.getProcessDate().split("/");
                        String recorddate[] = RecDate.split("/");

                        String process_date = processydate[2] + "-" + processydate[1] + "-" + processydate[0];
                        String record_date = recorddate[0] + "-" + recorddate[1] + "-" + recorddate[2];
                        int WorkingDaysInOldsal = dateDiffrence(record_date, process_date);
                        String currentYear = String.valueOf(StringDateManipulation.currentYear);
                        int workdaysInNewSal = dateDiffrence(process_date, currentYear + "-10-30");

                        LeaveAccruedAtOldSalary = dailyLeaveAccrue * WorkingDaysInOldsal;
                        LeaveAccruedAtNewSalary = dailyLeaveAccrue * workdaysInNewSal;
                        System.out.println("LeaveAccruedAtOldSalary==" + LeaveAccruedAtOldSalary);
                        System.out.println("LeaveAccruedAtNewSalary==" + LeaveAccruedAtNewSalary);
                        if (balanceList.get(i).getRemainingDays() > LeaveAccruedAtNewSalary) {
                            accruedLeaveAmountOfAYear = LeaveAccruedAtOldSalary * (emphistory.getOldSalary() / 30) + LeaveAccruedAtNewSalary * (balanceList.get(i).getEmployeeId().getBasicSalary() / 30);
                            AccruedLeaveAmount = AccruedLeaveAmount + LeaveAccruedAtOldSalary * (emphistory.getOldSalary() / 30) + LeaveAccruedAtNewSalary * (balanceList.get(i).getEmployeeId().getBasicSalary() / 30);
                            AccruedLeaveAmount = Math.rint(AccruedLeaveAmount * 100) / 100;
                        } else {
                            salary = balanceList.get(i).getEmployeeId().getBasicSalary();
                            remaininDays = balanceList.get(i).getRemainingDays();
                            AccruedLeaveAmount = AccruedLeaveAmount + ((salary / 30) * remaininDays);
                            accruedLeaveAmountOfAYear = AccruedLeaveAmount;
                            AccruedLeaveAmount = Math.rint(AccruedLeaveAmount * 100) / 100;
                        }

                    }
                    if (employee.getBasicSalary() < 10900) {
                        hrPayrollTaxRates = hrPayrollTaxRatesFacadeLocal.findBySalaryRange(employee.getBasicSalary());
                        System.out.println("hrPayrollTaxRates.getRateInPerercent()==" + hrPayrollTaxRates.getRateInPerercent());
                        System.out.println("hrPayrollTaxRates.getConstantAmount()==" + hrPayrollTaxRates.getConstantAmount());

                        Tax = ((employee.getBasicSalary() * hrPayrollTaxRates.getRateInPerercent()) / 100) - (hrPayrollTaxRates.getConstantAmount());
                        pension = (7 * employee.getBasicSalary()) / 100;
                        NetPay = employee.getBasicSalary() - (Tax + pension);
                    } else {
                        Tax = ((employee.getBasicSalary() * 35) / 100) - 1500;
                        pension = (7 * employee.getBasicSalary()) / 100;
                        NetPay = employee.getBasicSalary() - (Tax + pension);
                    }
                    accruedLeaveAmountOfAYear = totalLeavedaysOfaYear * (NetPay / 30);
                    accruedLeaveAmountOfAYear = Math.rint(accruedLeaveAmountOfAYear * 100) / 100;
                    totalLeavedaysOfaYear = Math.rint(totalLeavedaysOfaYear * 100) / 100;
                    hrEmpCompensationDetail.setAccruedLeavedays(totalLeavedaysOfaYear);
                    hrEmpCompensationDetail.setAccruedLeaveAmount(accruedLeaveAmountOfAYear);
                    hrEmpCompensationDetail.setLeaveBalaceId(balanceList.get(i));
                    System.out.println("AccruedLeavedays===" + hrEmpCompensationDetail.getAccruedLeavedays());
                    System.out.println("AccruedLeaveamount===" + hrEmpCompensationDetail.getAccruedLeaveAmount());
                    System.out.println("getLeaveBalaceId===" + hrEmpCompensationDetail.getLeaveBalaceId());

                    hrEmployeeCompensation.adddetail(hrEmpCompensationDetail);
                    hrEmpCompensationDetail = new HrEmpCompensationDetail();

                }
                AccruedLeaveAmount = AccruedLeaveAmount + totalLeavedays * (NetPay / 30);
                AccruedLeaveAmount = Math.rint(AccruedLeaveAmount * 100) / 100;
                totalLeavedays = Math.rint(totalLeavedays * 100) / 100;
                severanceDecimal = insertCommas(new Integer((int) Math.rint(severance)));
                double compensationAmuont = 0;
                compensationAmuont = AccruedLeaveAmount + hrEmployeeCompensation.getSeveranceAmount();
                compensationAmuont = Math.rint(compensationAmuont * 100) / 100;
                hrEmployeeCompensation.setFirst_year_sev_amount(firstYearAmount);
                hrEmployeeCompensation.setOther_years_sev_amount(YearsAfterFirstYearAmount);
                hrEmployeeCompensation.setMonths_sev_amount(MonthsAmount);
                hrEmployeeCompensation.setDays_sev_amount(DaysAmount);
                hrEmployeeCompensation.setAccruedLeaveDays(totalLeavedays);
                hrEmployeeCompensation.setAccruedLeaveInBirr(AccruedLeaveAmount);
                hrEmployeeCompensation.setProcessDate(Today);
                hrEmployeeCompensation.setCompensationAmount(compensationAmuont);
                Compensationdecimal = insertCommas(new Integer((int) Math.rint(compensationAmuont)));
                AccruedLeaveAmountDecimal = insertCommas(new Integer((int) Math.rint(AccruedLeaveAmount)));
                renderSave = false;
            }
            loadBalance();
        }

    }

    private static String insertCommas(Integer number) {
        System.out.println(" insideinsertCommas== " + number);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println(" output== " + df.format(number));
        return df.format(number);
    }

    public int dateDiffrence(String date1, String date2) {
        String Y1[] = date1.split("-");
        int yy1 = Integer.valueOf(Y1[0]);
        int mm1 = Integer.valueOf(Y1[1]);
        int dd1 = Integer.valueOf(Y1[2]);
        String Y2[] = date2.split("-");
        int yy2 = Integer.valueOf(Y2[0]);
        int mm2 = Integer.valueOf(Y2[1]);
        int dd2 = Integer.valueOf(Y2[2]);
        int datediff = (((yy2 - yy1) * 365) + ((mm2 - mm1) * 30) + dd2 - dd1);
        return datediff;
    }

    public HrEmpInternalHistory EmpHistory(int empId) {
        String record[] = leaveBalance.getRecordDate().split("-");
        int recordyear = Integer.parseInt(record[0]);
        String RecordDate = "30/10/" + recordyear;
        hrEmpInternalHistory = new HrEmpInternalHistory();
        hrEmpInternalHistory = hrEmpInternalHistoryFacade.findByEmpIdAndBudgerYear(empId, RecordDate, StringDateManipulation.toDayInEc());

        return hrEmpInternalHistory;
    }

    int empServiceInYears = 0;
    int monthired;
    int yearhired;
    private String Experience;
    int expday = 0;
    int expInYear = 0;
    int expInYearAfetrFirst = 0;
    int expInMonth = 0;
    int expInday = 0;
    int TotalexpriencesDays = 0;

    public String ServiceYearCalculator(HrEmployees empl) {
        System.out.println("");
        System.out.println("empl.getHireDate()==" + empl.getHireDate());
        String Shday[] = empl.getHireDate().split("/");
        int Ihday = Integer.parseInt(Shday[0]);
        String Shmonth[] = empl.getHireDate().split("/");
        int Ihmonth = Integer.parseInt(Shmonth[1]);
        String Shyear[] = empl.getHireDate().split("/");
        int Ihyear = Integer.parseInt(Shyear[2]);

        System.out.println("empl.getHireDate()==" + empl.getHireDate());
        System.out.println("current date()==" + StringDateManipulation.toDayInEc());
        String Scday[] = StringDateManipulation.toDayInEc().split("-");
        int Icday = Integer.parseInt(Scday[2]);
        String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
        int Icmonth = Integer.parseInt(Scmonth[1]);
        String Scyear[] = StringDateManipulation.toDayInEc().split("-");
        int Icyear = Integer.parseInt(Scyear[0]);

        TotalexpriencesDays = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
        System.out.println("TotalexpriencesDays===" + TotalexpriencesDays);
        expInYear = TotalexpriencesDays / 365;
        System.out.println("TotalexpriencesDays===" + TotalexpriencesDays);
        expInMonth = ((TotalexpriencesDays % 365) / 30);
        System.out.println("expInMonth===" + expInMonth);
        if (Icday - Ihday >= 0) {
            expday = (Icday - Ihday) + 1;
        } else {
            expday = (Icday - Ihday) + 1 + 30;
        }
        //expday = ((TotalexpriencesDays % 365) % 30);
        System.out.println("expday===" + expday);

        Experience = (expInYear + "-" + "Year , " + expInMonth + "- Month and " + expday + "-Days");
        System.out.println("Experience======" + Experience);
        return Experience;
    }

    public int generateEmpServiceInYears(HrEmployees empl) {
        try {
            System.out.println("HrEmployees Id==" + empl.getEmpId());
            System.out.println("empl.getHireDate() ==" + empl.getHireDate());
            String hired[] = empl.getHireDate().split("/");
//            System.err.println("=============accunting  period============" + budjetEnd);
            if (hired != null) {
                int dayhired = Integer.parseInt(hired[0]);
                monthired = Integer.parseInt(hired[1]);
                yearhired = Integer.parseInt(hired[2]);
                empServiceInYears = StringDateManipulation.getDuration(monthired, dayhired, yearhired, Today);
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

    public void loadBalance() {
        if (employee != null) {
           // renderBalance = checkToBalance();
            /*loading leave active year*/
            // loadLeaveYear();
            /*loading leave balance list*/

            balanceList = hrLeaveBalanceFacade.populateActiveLeaveBalance(employee);
            System.out.println("balanceList==" + balanceList);
            System.out.println("hrEmployeeCompensation.getHrEmpCompensationDetailList()==" + hrEmployeeCompensation.getHrEmpCompensationDetailList());
            recreateBalanceDataModel();
        }
    }

    public void recreateBalanceDataModel() {

        balanceDataModel = null;
        CompensationDataModel = null;
        CompensationDetailDataModel = null;
        CompensationDetailDataModel = new ListDataModel(new ArrayList<>(hrEmployeeCompensation.getHrEmpCompensationDetailList()));
        //   System.out.println("CompensationDataModel=row count="+CompensationDetailDataModel.getRowCount());
        //balanceDataModel = new ListDataModel(new ArrayList<>(balanceList));
        CompensationDataModel = new ListDataModel(new ArrayList<>(compList));
        String todayDate[] = et.gov.eep.commonApplications.utility.StringDateManipulation.toDayInEc().split("-");
//        int workedMonth = Integer.valueOf(todayDate[1]) - 1;
//        int x = balanceList.size();
//        int lastIndex = balanceList.size() - 1;
//        if (x > 0) {
//            totalLeaveDays = 0;
//            for (int i = 0; i < lastIndex; i++) {
//                totalLeaveDays = totalLeaveDays + balanceList.get(i).getRemainingDays();
//            }
//            HrLeaveBalance allowedLeaveDayss = balanceList.get(lastIndex);
//            if (workedMonth == 11) {
//                totalLeaveDays = totalLeaveDays + allowedLeaveDayss.getTotalDays();
//            } else {
//                totalLeaveDays = totalLeaveDays + df.decimalFormat(allowedLeaveDayss.getTotalDays() / workedMonth);
//            }
//        }
    }

    public void saveCompensation() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveCompensation", dataset)) {
                hrEmployeeCompensation.setPreparedBy(SessionBean.getUserId());
                compensationBeanLocal.save(hrEmployeeCompensation);
                JsfUtil.addSuccessMessage("Data Saved Successfully");
                clear();
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveCompensation");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Unable to Modify data");
            updateStatus = 0;
            saveorUpdateBundle = "Save";
        }
    }
}
