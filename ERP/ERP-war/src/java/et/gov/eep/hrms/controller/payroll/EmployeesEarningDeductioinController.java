/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainEdsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
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
@Named(value = "employeesEarningDeductioinController")
@ViewScoped
public class EmployeesEarningDeductioinController implements Serializable {

    public EmployeesEarningDeductioinController() {
    }
//<editor-fold defaultstate="collapsed" desc="Objects & Variables">

    @Inject
    HrPayrollPeriods hrPayrollPeriods;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
    HrPayrollEarningDeductions earningDeductionToSearch;
    @Inject
    HrPayrollMaintainEds hrPayrollMaintainEds;
    @Inject
    HrPayrollMaintainEds checkForUpdate;
    @Inject
    HrPayrollPeriods paymentStartDate;
    @Inject
    HrPayrollPeriods carryForwardFrom;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsLocal;
    @EJB
    HrPayrollMaintainEdsBeanLocal hrPayrollMaintainEdsLocal;
    @EJB
    HrEmployeeBeanLocal employeeBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    private HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsFacade;

    private String lableValue = "Birr:";
    private String basicSalary;
    private String paymentType;
    private String _startDate;
    private String endDate;
    private String appliedFrom;
    private String carryForward;
    private String numberOfMonth;
    private String activePayrollDate;
    private String code;
    private String respectiveAmount = "0";
    private String saveOrUpdate = "Save";
    private String sDate;
    private String eDate;
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private int monthDifference = 0;
    private boolean searchPage = true;
    private boolean newPage = false;
    private Double total;
    private Double monthlyPayment;
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
    private List<HrPayrollPeriods> payrollFrom;
    private List<HrPayrollMaintainEds> listOfEarnigs;
    private List<HrPayrollMaintainEds> listOfDeductions;
    private List<HrPayrollEarningDeductions> listOfEarningDeductions;
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void _init() {
        try {
            if (hrPayrollPeriodsLocal.activePayrollDate() != null) {
                hrPayrollPeriods = hrPayrollPeriodsLocal.activePayrollDate();
                activePayrollDate = hrPayrollPeriods.getPaymentMadeForTheMonthOf();
            } else {
                activePayrollDate = "[No Active Date is Defined]";
            }
            hrPayrollMaintainEds.setRespectivePaymentTypeAmt(BigInteger.ZERO);
            hrPayrollMaintainEds.setMonthlyAmount(0.0);
            payrollFrom = hrPayrollPeriodsLocal.findAll();//this is for back payment
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters & Setters">

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

    public HrPayrollMaintainEds getCheckForUpdate() {
        if (checkForUpdate == null) {
            checkForUpdate = new HrPayrollMaintainEds();
        }
        return checkForUpdate;
    }

    public void setCheckForUpdate(HrPayrollMaintainEds checkForUpdate) {
        this.checkForUpdate = checkForUpdate;
    }

    public List<HrPayrollMaintainEds> getListOfEarnigs() {
        return listOfEarnigs;
    }

    public void setListOfEarnigs(List<HrPayrollMaintainEds> listOfEarnigs) {
        this.listOfEarnigs = listOfEarnigs;
    }

    public List<HrPayrollMaintainEds> getListOfDeductions() {
        return listOfDeductions;
    }

    public void setListOfDeductions(List<HrPayrollMaintainEds> listOfDeductions) {
        this.listOfDeductions = listOfDeductions;
    }

    private boolean isEarning;

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

    public void handleChanges() {

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void handleChange(ValueChangeEvent event) {
        code = returnId(event.getNewValue().toString());
    }

    public HrPayrollPeriods getPaymentStartDate() {
        if (paymentStartDate == null) {
            paymentStartDate = new HrPayrollPeriods();
        }
        return paymentStartDate;
    }

    public void setPaymentStartDate(HrPayrollPeriods paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public HrPayrollPeriods getCarryForwardFrom() {
        if (carryForwardFrom == null) {
            carryForwardFrom = new HrPayrollPeriods();
        }
        return carryForwardFrom;
    }

    public void setCarryForwardFrom(HrPayrollPeriods carryForwardFrom) {
        this.carryForwardFrom = carryForwardFrom;
    }

    public String getRespectiveAmount() {
        return respectiveAmount;
    }

    public void setRespectiveAmount(String respectiveAmount) {
        this.respectiveAmount = respectiveAmount;
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

    public HrPayrollEarningDeductions getHrPayrollEarningDeductions() {
        if (hrPayrollEarningDeductions == null) {
            hrPayrollEarningDeductions = new HrPayrollEarningDeductions();
        }
        return hrPayrollEarningDeductions;
    }

    public void setHrPayrollEarningDeductions(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        this.hrPayrollEarningDeductions = hrPayrollEarningDeductions;
    }

    public String getActivePayrollDate() {
        return activePayrollDate;
    }

    public void setActivePayrollDate(String activePayrollDate) {
        this.activePayrollDate = activePayrollDate;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public void loadActiveDate() {
        if (hrPayrollPeriodsLocal.activePayrollDate() != null) {
            hrPayrollPeriods = hrPayrollPeriodsLocal.activePayrollDate();
            activePayrollDate = hrPayrollPeriods.getPaymentMadeForTheMonthOf();
        }
    }

    public List<HrPayrollPeriods> getPayrollFrom() {
        return payrollFrom;
    }

    public void setPayrollFrom(List<HrPayrollPeriods> payrollFrom) {
        this.payrollFrom = payrollFrom;
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

    public void handleChange() {
        System.out.println("New value: ");
    }

    public String getLableValue() {
        return lableValue;
    }

    public void setLableValue(String lableValue) {
        this.lableValue = lableValue;
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStartDate() {
        return _startDate;
    }

    public void setStartDate(String _startDate) {
        this._startDate = _startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public void load() {
        _startDate = hrPayrollMaintainEds.getStartDate();
    }

    public String getCarryForward() {
        return carryForward;
    }

    public void setCarryForward(String carryForward) {
        this.carryForward = carryForward;
    }

    public String getAppliedFrom() {
        return appliedFrom;
    }

    public void setAppliedFrom(String appliedFrom) {
        this.appliedFrom = appliedFrom;
    }

    public String getNumberOfMonth() {
        return numberOfMonth;
    }

    public void setNumberOfMonth(String numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void populateEaring(SelectEvent events) {
        checkForUpdate = null;
        checkForUpdate = (HrPayrollMaintainEds) events.getObject();
        try {
            saveOrUpdate = "Update";
            searchPage = false;
            newPage = true;
            if (hrPayrollMaintainEds.getEarningDeductionCode().getType().equalsIgnoreCase("Earning")) {
                isEarning = true;
                isDeduction = false;
                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyEarnings();
            } else {
                isDeduction = true;
                isEarning = false;
                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyDeductions();
            }
            code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
            appliedFrom = hrPayrollMaintainEds.getStartDate();
            total = hrPayrollMaintainEds.getTotal();
            numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
        } catch (Exception e) {
        }
        btnNewOrSearch();
    }

    public void populateDeduction(SelectEvent events) {
        checkForUpdate = null;
        checkForUpdate = (HrPayrollMaintainEds) events.getObject();
        try {
            saveOrUpdate = "Update";
            searchPage = false;
            newPage = true;
            if (hrPayrollMaintainEds.getEarningDeductionCode().getType().equalsIgnoreCase("Earning")) {
                isEarning = true;
                isDeduction = false;
                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyEarnings();
            } else {
                isDeduction = true;
                isEarning = false;
                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyDeductions();
            }
            code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
            appliedFrom = hrPayrollMaintainEds.getStartDate();
            total = hrPayrollMaintainEds.getTotal();
            numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnNewOrSearch();
    }

    public ArrayList<HrEmployees> searchId(Integer empId) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setId(empId);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employees;
    }

    public ArrayList<HrEmployees> search(String hrEmployee) {
        ArrayList<HrEmployees> employe = null;
        hrEmployees.setEmpId(hrEmployee);
        employe = employeeBeanLocal.SearchByEmpId(hrEmployees);
        return employe;
    }

    public ArrayList<HrEmployees> SearchByFname(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setEmpId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employees;
    }

    public ArrayList<HrEmployees> SearchByFname1(Integer emp) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setId(emp);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employees;
    }

    public ArrayList<HrEmployees> SearchByFname2(int hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        hrEmployees.setId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(hrEmployees);
        return employees;
    }

    public void getByFirstName(SelectEvent event) {
        try {
            System.out.println("event.getObject().toString() ==" + event.getObject().toString());
            System.out.println("event.getObject().toString() ==" + event.getObject().toString());
            hrEmployees.setFirstName(event.getObject().toString());
            System.out.print("The First Name is  " + hrEmployees.getFirstName());
            //modefied right now
            hrEmployees = employeeBeanLocal.getByEmpId(hrEmployees);
            System.out.print("The salary is " + hrEmployees.getBasicSalary());
            basicSalary = hrEmployees.getBasicSalary().toString();
            listOfEarnigs = null;
            listOfDeductions = null;
            listOfEarnigs = new ArrayList<HrPayrollMaintainEds>();
            listOfDeductions = new ArrayList<HrPayrollMaintainEds>();
            listOfEarnigs = hrPayrollMaintainEdsLocal.loadEarningDeductions("Earning", hrEmployees.getId());
            listOfDeductions = hrPayrollMaintainEdsLocal.loadEarningDeductions("Deduction", hrEmployees.getId());
            for (HrPayrollMaintainEds ld : listOfDeductions) {
                System.out.print("The remaining month is  " + ld.getRemainingMonth());
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
                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyEarnings();
                loadActiveDate();
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
                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyDeductions();
                loadActiveDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFromDateSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sDate = format.format((Date) event.getObject());
    }

    public void onToDateSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        eDate = format.format((Date) event.getObject());
    }

    public void calculateDateDifference() {
        try {
            if (eDate == null) {
                hrPayrollMaintainEds.setMonthlyAmount(hrPayrollMaintainEds.getTotal());
            } else {
                monthDifference = Integer.valueOf(hrPayrollEarningDeductionsFacade.returnNumberOfMonths(sDate, eDate));
                if (monthDifference == 0) {
                    hrPayrollMaintainEds.setMonthlyAmount(hrPayrollMaintainEds.getTotal());
                } else {
                    Double difference = (hrPayrollMaintainEds.getTotal() / monthDifference);
                    hrPayrollMaintainEds.setMonthlyAmount(difference);
                }
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

    public void calculateEDOnRespectiveValues() {
        try {
            if (hrEmployees.getId() == null) {
                JsfUtil.addFatalMessage("Search employee!");
                total = 0.0;
            } else if (isEarning == false && isDeduction == false) {
                JsfUtil.addFatalMessage("Atleaset Select Earning or Deduction!");
                total = 0.0;
            } else if (code == null) {
                JsfUtil.addFatalMessage("Select Earning/Deduction");
                total = 0.0;
            } else if (appliedFrom == null) {
                JsfUtil.addFatalMessage("Select Payment Starting month");
                total = 0.0;
            } else {
                //hrPayrollMaintainEds.setRespectivePaymentTypeAmt(BigInteger.ZERO);
//                if (numberOfMonth.equalsIgnoreCase("Unknown")) {
//                    if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Birr")) {
//                        hrPayrollMaintainEds.setMonthlyAmount(total);
//                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent")) {
//                        hrPayrollMaintainEds.setMonthlyAmount(total * (Double.valueOf(respectiveAmount) / 100));
//                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Hour")) {
//                        hrPayrollMaintainEds.setMonthlyAmount(total);
//                    }
//                } else {

//                    if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Birr")) {
//                        hrPayrollMaintainEds.setMonthlyAmount(total / Double.valueOf(numberOfMonth));
//                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent")) {
//                        hrPayrollMaintainEds.setMonthlyAmount((hrEmployees.getBasicSalary() * (Double.valueOf(respectiveAmount) / 100)) / (Double.valueOf(numberOfMonth)));
//                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Hour")) {//for the hour there is no clearly given rule
//                        hrPayrollMaintainEds.setMonthlyAmount(total / Double.valueOf(numberOfMonth));
//                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Day")) {
//
//                        //if 5000=30 then if one works for 20 days it will be minimized of 5000*20/30  this is the formula
//                        hrPayrollMaintainEds.setMonthlyAmount(((hrEmployees.getBasicSalary() * (Double.valueOf(respectiveAmount) / 30))) / (Double.valueOf(numberOfMonth)));
//                    }
//                }
                if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent")) {
                    hrPayrollMaintainEds.setTotal(2000.0);
//                        hrPayrollMaintainEds.setMonthlyAmount((hrEmployees.getBasicSalary() * (Double.valueOf(respectiveAmount) / 100)) / (Double.valueOf(numberOfMonth)));
                } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Hour")) {//for the hour there is no clearly given rule
                    hrPayrollMaintainEds.setTotal(hrEmployees.getBasicSalary());
//                        hrPayrollMaintainEds.setMonthlyAmount(total / Double.valueOf(numberOfMonth));
                } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Day")) {
                    hrPayrollMaintainEds.setTotal(hrEmployees.getBasicSalary());
                    //if 5000=30 then if one works for 20 days it will be minimized of 5000*20/30  this is the formula
//                        hrPayrollMaintainEds.setMonthlyAmount(((hrEmployees.getBasicSalary() * (Double.valueOf(respectiveAmount) / 30))) / (Double.valueOf(numberOfMonth)));
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void calculateED1() {
        try {
//            hrPayrollMaintainEds.setRemark("Done!");
            hrPayrollMaintainEds.setTotal(hrEmployees.getBasicSalary());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void calculateED() {
        try {
            if (hrEmployees.getId() == null) {
                JsfUtil.addFatalMessage("Search employee!");
                total = 0.0;
            } else if (isEarning == false && isDeduction == false) {
                JsfUtil.addFatalMessage("Atleaset Select Earning or Deduction!");
                total = 0.0;
            } else if (code == null) {
                JsfUtil.addFatalMessage("Select Earning/Deduction");
                total = 0.0;
            } else if (appliedFrom == null) {
                JsfUtil.addFatalMessage("Select Payment Starting month");
                total = 0.0;
            } else {
                hrPayrollMaintainEds.setRespectivePaymentTypeAmt(BigInteger.ZERO);
                if (numberOfMonth.equalsIgnoreCase("Unknown")) {
                    if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Birr")) {
                        hrPayrollMaintainEds.setMonthlyAmount(total);
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent")) {
                        hrPayrollMaintainEds.setMonthlyAmount(((total) * (Double.valueOf(respectiveAmount) / 100)));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Hour")) {
                        hrPayrollMaintainEds.setMonthlyAmount(total);
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Day")) {
                        hrPayrollMaintainEds.setMonthlyAmount(((total) * (Double.valueOf(respectiveAmount) / 30)));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent+unlimited")) {
                        //if 5000=30 then if one works for 20 days it will be minimized of 5000*20/30  this is the formula
                        hrPayrollMaintainEds.setMonthlyAmount(((total) * (Double.valueOf(respectiveAmount) / 30)) / Double.valueOf(numberOfMonth));
                    }

                } else {
                    if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Birr")) {
                        hrPayrollMaintainEds.setMonthlyAmount(total / Double.valueOf(numberOfMonth));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent")) {
//                        hrPayrollMaintainEds.setMonthlyAmount(((total) * (Double.valueOf(respectiveAmount) / 100)) / Double.valueOf(numberOfMonth));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Hour")) {//for the hour there is no clearly given rule
//                        hrPayrollMaintainEds.setMonthlyAmount(total / Double.valueOf(numberOfMonth));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Day")) {
                        //Do nothing the calculation is made on the procedure
                        //if 5000=30 then if one works for 20 days it will be minimized of 5000*20/30  this is the formula
//                        hrPayrollMaintainEds.setMonthlyAmount(((total) * (Double.valueOf(respectiveAmount) / 30)) / Double.valueOf(numberOfMonth));
                    } else if (hrPayrollMaintainEds.getPaymentIn().equalsIgnoreCase("Percent+unlimited")) {
                        //if 5000=30 then if one works for 20 days it will be minimized of 5000*20/30  this is the formula
                        hrPayrollMaintainEds.setMonthlyAmount(((total) * (Double.valueOf(respectiveAmount) / 30)) / Double.valueOf(numberOfMonth));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearOnSave() {
        hrPayrollMaintainEds = null;
        isEarning = false;
        isDeduction = false;
        code = null;
        total = 0.0;

        appliedFrom = null;
        payrollFrom = hrPayrollPeriodsLocal.findAll();
        respectiveAmount = "0";
    }

    public void saveEmpEarningDeductions() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveEmpEarningDeductions", dataset)) {
                if (hrEmployees.getId() == null) {
                    JsfUtil.addFatalMessage("Search employee");
                } else if (isEarning == false && isDeduction == false) {
                    JsfUtil.addFatalMessage("Atleast select earning or deduction!");
                } else if (code == null) {
                    JsfUtil.addFatalMessage("Select earning/deduction");
                } else {
                    if (total <= 0) {
                        JsfUtil.addFatalMessage("Total should be greater than zero!");
                    } else if (hrPayrollMaintainEds.getMonthlyAmount() <= 0) {
                        JsfUtil.addFatalMessage("Monthly amount should be greater than zero!");
                    } else {
                        hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(code)));
                        BigInteger resVal = new BigInteger(respectiveAmount);
                        hrPayrollMaintainEds.setRespectivePaymentTypeAmt(resVal);
                        hrPayrollMaintainEds.setCarryForward(carryForward);
                        if (saveOrUpdate.equalsIgnoreCase("Save")) {
                            hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(code)));
                            if (hrPayrollMaintainEdsLocal.cheackRepeatedEarningOrDed(code, hrEmployees) == null) {
                                hrPayrollMaintainEds.setEarningDeductionCode(hrPayrollEarningDeductions);
                                hrPayrollMaintainEds.setStartDate(appliedFrom);
                                hrPayrollMaintainEds.setTotal(total);
                                hrPayrollMaintainEds.setEmpId(hrEmployees);
                                hrPayrollMaintainEds.setRemainingMonth(hrPayrollMaintainEds.getNumberOfMonth());
                                hrPayrollMaintainEdsLocal.edit(hrPayrollMaintainEds);
                                JsfUtil.addSuccessMessage("Successfully saved");
//                                clearOnSave();
                            } else {
                                JsfUtil.addFatalMessage("First close the earning/deduction");
                            }
                        } else {
                            hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(code)));
                            hrPayrollMaintainEds.setEarningDeductionCode(hrPayrollEarningDeductions);
                            hrPayrollMaintainEds.setStartDate(appliedFrom);
                            hrPayrollMaintainEds.setTotal(total);
                            hrPayrollMaintainEds.setEmpId(hrEmployees);
                            hrPayrollMaintainEds.setRemainingMonth(hrPayrollMaintainEds.getNumberOfMonth());
                            hrPayrollMaintainEdsLocal.edit(hrPayrollMaintainEds);
                            hrPayrollMaintainEds = null;
                            JsfUtil.addSuccessMessage("Successfully Updated");
//                            clearOnSave();
                        }
                        if (hrPayrollMaintainEdsLocal.loadEarningDeductions("Earning", hrEmployees.getId()) != null) {
                            listOfEarnigs = hrPayrollMaintainEdsLocal.loadEarningDeductions("Earning", hrEmployees.getId());
                        }
                        if (hrPayrollMaintainEdsLocal.loadEarningDeductions("Deduction", hrEmployees.getId()) != null) {
                            listOfDeductions = hrPayrollMaintainEdsLocal.loadEarningDeductions("Deduction", hrEmployees.getId());
                        }
                        clearOnSave();
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setUserLogin(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveOrUpdatgeEmployeesEarningDeductions");
                eventEntry.setUserLogin(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public void edit() {
//        try {
//            saveOrUpdate = "Update";
//            if (hrPayrollMaintainEds.getEarningDeductionCode().getType().equalsIgnoreCase("Earning")) {
//                isEarning = true;
//                isDeduction = false;
//                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyEarnings();
//            } else {
//                isDeduction = true;
//                isEarning = false;
//                listOfEarningDeductions = hrPayrollEarningDeductionsFacade.loadOnlyDeductions();
//            }
//            code = String.valueOf(hrPayrollMaintainEds.getEarningDeductionCode().getCode());
//            appliedFrom = hrPayrollMaintainEds.getStartDate();
//            total = hrPayrollMaintainEds.getTotal();
//            numberOfMonth = hrPayrollMaintainEds.getNumberOfMonth();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
    public void setPaymentLabel(ValueChangeEvent event) {
        try {
            paymentType = event.getNewValue().toString();
            if (event.getNewValue().toString().equalsIgnoreCase("Birr")) {
                lableValue = "Birr:";
                total = 0.0;
            } else if (event.getNewValue().toString().equalsIgnoreCase("Percent")) {
                lableValue = "%";
                total = hrEmployees.getBasicSalary();

            } else if (event.getNewValue().toString().equalsIgnoreCase("Hour")) {
                lableValue = "Hour:";
                total = hrEmployees.getBasicSalary();
            } else if (event.getNewValue().toString().equalsIgnoreCase("Day")) {
                lableValue = "Day:";
                total = hrEmployees.getBasicSalary();
            } else if (event.getNewValue().toString().equalsIgnoreCase("Percent+unlimited")) {
                lableValue = " Percent+unlimited:";
                total = hrEmployees.getBasicSalary();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void calculateMonthlyPayment() {
        try {
            if (_startDate == null) {
                JsfUtil.addErrorMessage("End date is empty");
                monthlyPayment = total;
            } else {
                monthlyPayment = 10.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleValueChangeFrom1(ValueChangeEvent event) {
        try {
            appliedFrom = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleValueChangeFromCarryForward(ValueChangeEvent event) {
        try {
            carryForward = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleChangeFrom() {
        try {
            total = 0.0;
            hrPayrollMaintainEds.setMonthlyAmount(0.0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getReturnYM(String dateToSplit) {
        String dates[] = dateToSplit.split("/");
        return dates[1] + "/" + dates[2];
    }

    public void handlePaymentMonth() {
        try {
            total = 0.0;
            hrPayrollMaintainEds.setMonthlyAmount(0.0);

        } catch (Exception e) {
        }
    }

    public void handlePaymentTypeChange() {
        try {

            total = 0.0;
            hrPayrollMaintainEds.setMonthlyAmount(0.0);
            hrPayrollMaintainEds.setRespectivePaymentTypeAmt(BigInteger.ZERO);
            respectiveAmount = "0";

            if (paymentType.equalsIgnoreCase("Birr")) {
                lableValue = "Birr:";
                total = 0.0;
            } else if (paymentType.equalsIgnoreCase("Percent")) {
                lableValue = "%";
                total = hrEmployees.getBasicSalary();

            } else if (paymentType.equalsIgnoreCase("Hour")) {
                lableValue = "Hour:";
                total = hrEmployees.getBasicSalary();
            } else if (paymentType.equalsIgnoreCase("Day")) {
                lableValue = "Day:";
                total = hrEmployees.getBasicSalary();
            } else if (paymentType.equalsIgnoreCase("Percent+unlimited")) {
                lableValue = " Percent+unlimited:";
                total = hrEmployees.getBasicSalary();
            }

        } catch (Exception e) {
        }

    }

    public void handleNoMonthChange(ValueChangeEvent event) {
        try {
            numberOfMonth = event.getNewValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void calculateMonthlyAmount() {
        try {
            if (hrEmployees.getId() == null) {
                JsfUtil.addFatalMessage("Search employee!");
            } else if (isEarning == false && isDeduction == false) {
                JsfUtil.addFatalMessage("Atleaset Select Earning or Deduction!");
            } else if (code == null) {
                JsfUtil.addFatalMessage("Select Earning/Deduction");
            } else {
                hrPayrollEarningDeductions.setCode(BigDecimal.valueOf(Double.valueOf(code)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//</editor-fold>
}
