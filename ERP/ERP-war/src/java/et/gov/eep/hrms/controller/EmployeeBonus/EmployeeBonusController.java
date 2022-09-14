/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.EmployeeBonus;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.mapper.WfHrProcessedFacade;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.hrms.businessLogic.EmployeeBonus.EmployeesBeanLocal;
import et.gov.eep.hrms.businessLogic.EmployeeBonus.EmployeesDetailBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollTaxRatesBeanLocal;
import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonus;
import et.gov.eep.hrms.entity.EmployeeBonus.HrEmployeesBonusDetail;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollTaxRates;
import et.gov.eep.hrms.mapper.EmployeeBonus.HrEmployeesBonusFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author meles
 */
@Named(value = "employeeBonus")
@ViewScoped
public class EmployeeBonusController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="variables">
    @EJB
    HrPayrollTaxRatesBeanLocal hrpayroltaxRateFacade;
    @Inject
    HrPayrollTaxRates hrpayroltaxRate;
    @Inject
    private FmsAccountingPeriod accountingPeriod;
    @Inject
    HrEmployeesBonusDetail hrEmployeesBonusDetail;
    @Inject
    HrEmployeesBonus hrEmployeesBonus;
    @Inject
    HrEmployees hremployees;
    @Inject
    WfHrProcessed wfHrProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    @EJB
    EmployeesDetailBeanLocal employeesDetailBeanLocal;
    @EJB
    EmployeesBeanLocal employeesBeanLocal;
    @EJB
    private FmsAccountingPeriodBeanLocal accountingPeriodBeanLocal;
    @EJB
    WfHrProcessedFacade wfHrProcessedFacade;
    private DataModel<HrEmployeesBonusDetail> bonusDataModels;
    private DataModel<HrEmployeesBonusDetail> bonusDataModelss;
    private DataModel<HrEmployeesBonus> bunusmodel;
    private DataModel<HrEmployees> employeesdatamodel;
    List<SelectItem> filterByStatus = new ArrayList<>();
    List<HrEmployeesBonus> hremploybonuslist = new ArrayList<>();
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    private ArrayList<SelectItem> listOfYear = new ArrayList<>();
    private ArrayList<SelectItem> listOfBones = new ArrayList<>();
    private ArrayList<SelectItem> listOfexperiancemonth = new ArrayList<>();
    boolean lockInput = false;
    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    List<HrEmployees> listemployee = new ArrayList<>();
    List<HrPayrollTaxRates> taxRateList = new ArrayList<>();
    private String saveOrUpdateButton = "Save";
    int update = 0;
    int status = 1;
    boolean rendered = true;
    Integer requestNotificationCounter = 0;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="postConstruct">
    @PostConstruct
    public void init() {
        requestcounter();
        setListOfYear(listOfBonesYear());
        setListOfBones(listOfBonesAmout());
        setListOfexperiancemonth(listOfExperianceMonth());
        taxRateList = hrpayroltaxRateFacade.findAll();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getter setter">
    public List<HrEmployeesBonus> getHremploybonuslist() {
        return hremploybonuslist;
    }

    public void setHremploybonuslist(List<HrEmployeesBonus> hremploybonuslist) {
        this.hremploybonuslist = hremploybonuslist;
    }

    public Integer getRequestNotificationCounter() {
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public HrPayrollTaxRates getHrpayroltaxRate() {
        if (hrpayroltaxRate == null) {
            hrpayroltaxRate = new HrPayrollTaxRates();
        }
        return hrpayroltaxRate;
    }

    public void setHrpayroltaxRate(HrPayrollTaxRates hrpayroltaxRate) {
        this.hrpayroltaxRate = hrpayroltaxRate;
    }

    public List<HrPayrollTaxRates> getTaxRateList() {
        return taxRateList;
    }

    public WfHrProcessed getWfHrProcessed() {
        if (wfHrProcessed == null) {
            wfHrProcessed = new WfHrProcessed();
        }
        return wfHrProcessed;
    }

    public void setWfHrProcessed(WfHrProcessed wfHrProcessed) {
        this.wfHrProcessed = wfHrProcessed;
    }

    public DataModel<WfHrProcessed> getWorkFlowDataModel() {
        return workFlowDataModel;
    }

    public void setWorkFlowDataModel(DataModel<WfHrProcessed> workFlowDataModel) {
        this.workFlowDataModel = workFlowDataModel;
    }

    public void setTaxRateList(List<HrPayrollTaxRates> taxRateList) {
        this.taxRateList = taxRateList;
    }

    public DataModel<HrEmployees> getEmployeesdatamodel() {
        return employeesdatamodel;
    }

    public void setEmployeesdatamodel(DataModel<HrEmployees> employeesdatamodel) {
        this.employeesdatamodel = employeesdatamodel;
    }

    public List<HrEmployees> getListemployee() {
        if (listemployee == null) {
            listemployee = new ArrayList<>();
        }
        return listemployee;
    }

    public void setListemployee(List<HrEmployees> listemployee) {
        this.listemployee = listemployee;
    }

    public ArrayList<SelectItem> getListOfBones() {
        return listOfBones;
    }

    public void setListOfBones(ArrayList<SelectItem> listOfBones) {
        this.listOfBones = listOfBones;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public ArrayList<SelectItem> getListOfYear() {
        return listOfYear;
    }

    public void setListOfYear(ArrayList<SelectItem> listOfYear) {
        this.listOfYear = listOfYear;
    }

    public ArrayList<SelectItem> getListOfexperiancemonth() {
        return listOfexperiancemonth;
    }

    public void setListOfexperiancemonth(ArrayList<SelectItem> listOfexperiancemonth) {
        this.listOfexperiancemonth = listOfexperiancemonth;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getNetPay() {
        return netPay;
    }

    public void setNetPay(Double netPay) {
        this.netPay = netPay;
    }

    public HrEmployees getHremployees() {
        if (hremployees == null) {
            hremployees = new HrEmployees();
        }
        return hremployees;
    }

    public void setHremployees(HrEmployees hremployees) {
        this.hremployees = hremployees;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
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

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isLockInput() {
        return lockInput;
    }

    public void setLockInput(boolean lockInput) {
        this.lockInput = lockInput;
    }

    public List<SelectItem> getFilterByStatus() {
        return employeesBeanLocal.filterByStatus();
    }

    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }

    String experiance;

    public String getExperiance() {
        return experiance;
    }

    public void setExperiance(String experiance) {
        this.experiance = experiance;
    }

    public HrEmployeesBonusDetail getHrEmployeesBonusDetail() {
        if (hrEmployeesBonusDetail == null) {
            hrEmployeesBonusDetail = new HrEmployeesBonusDetail();
        }
        return hrEmployeesBonusDetail;
    }

    public void setHrEmployeesBonusDetail(HrEmployeesBonusDetail hrEmployeesBonusDetail) {
        this.hrEmployeesBonusDetail = hrEmployeesBonusDetail;
    }

    public HrEmployeesBonus getHrEmployeesBonus() {
        if (hrEmployeesBonus == null) {
            hrEmployeesBonus = new HrEmployeesBonus();
        }
        return hrEmployeesBonus;
    }

    public void setHrEmployeesBonus(HrEmployeesBonus hrEmployeesBonus) {
        this.hrEmployeesBonus = hrEmployeesBonus;
    }

    public DataModel<HrEmployeesBonusDetail> getBonusDataModels() {
        if (bonusDataModels == null) {
            bonusDataModels = new ListDataModel<>();
        }
        return bonusDataModels;
    }

    public void setBonusDataModels(DataModel<HrEmployeesBonusDetail> bonusDataModels) {
        this.bonusDataModels = bonusDataModels;
    }

    public DataModel<HrEmployeesBonusDetail> getBonusDataModelss() {
        if (bonusDataModelss == null) {
            bonusDataModelss = new ArrayDataModel<>();
        }
        return bonusDataModelss;
    }

    public void setBonusDataModelss(DataModel<HrEmployeesBonusDetail> bonusDataModelss) {

        this.bonusDataModelss = bonusDataModelss;
    }

    public DataModel<HrEmployeesBonus> getBunusmodel() {
        if (bunusmodel == null) {
            bunusmodel = new ArrayDataModel<>();
        }
        return bunusmodel;
    }

    public void setBunusmodel(DataModel<HrEmployeesBonus> bunusmodel) {
        this.bunusmodel = bunusmodel;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="main methods">
    public ArrayList<SelectItem> listOfBonesYear() {
        ArrayList<SelectItem> list_Year = new ArrayList<>();
        int year = getYear(StringDateManipulation.toDayInEc());
        list_Year.add(new SelectItem(null, "-- Select --"));
        for (int i = 0; i < 5; i++) {
            list_Year.add(new SelectItem(Integer.toString(year), Integer.toString(year)));
            year -= 1;
        }
        return list_Year;
    }
    @EJB
            HrEmployeesBonusFacade hrEmployeesBonusFacade;
    
    public void requestcounter() {
        hremploybonuslist = hrEmployeesBonusFacade.findrequestlist();
        requestNotificationCounter = hremploybonuslist.size();
    }
    
    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(hrEmployeesBonus.getWfHrProcessedList()));
        for (int i = 0; i < hrEmployeesBonus.getWfHrProcessedList().size(); i++) {
            if (hrEmployeesBonus.getWfHrProcessedList().get(i).getDecision() == 0) {
                hrEmployeesBonus.getWfHrProcessedList().get(i).setAction("Prepared");
            } else if (hrEmployeesBonus.getWfHrProcessedList().get(i).getDecision() == 3) {
                hrEmployeesBonus.getWfHrProcessedList().get(i).setAction("Approved");
            } else if (hrEmployeesBonus.getWfHrProcessedList().get(i).getDecision() == 2 || hrEmployeesBonus.getWfHrProcessedList().get(i).getDecision() == 4) {
                hrEmployeesBonus.getWfHrProcessedList().get(i).setAction("Rejected");
            }
            
        }
    }
    
    private int getYear(String _date) {
        String dmy[] = _date.split("-");
        return Integer.parseInt(dmy[0]);
    }
    
    public void handleBonusInfo(ValueChangeEvent event) {
        
        try {
            if (event.getNewValue().toString() != null) {
                String year = event.getNewValue().toString();
                hrEmployeesBonus = employeesBeanLocal.findByBgtYear(year);
                if (hrEmployeesBonus == null) {
                    hrEmployeesBonus = new HrEmployeesBonus();
                    bonusDataModels = null;
                    bonusDataModels = new ListDataModel(new ArrayList<>(hrEmployeesBonus.getHrEmployeesBonusDetailList()));
                } else {
                    update = 1;
                    saveOrUpdateButton = "update";
                    rendered = false;
                    recerateModel();
                    recreateDataModelwf();
                }
                recerateModel();
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void createNewAdditionalAmount() {
        
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    public ArrayList<SelectItem> listOfBonesAmout() {
        ArrayList<SelectItem> list_Bones = new ArrayList<>();
        list_Bones.add(new SelectItem(null, "-- Select --"));
        list_Bones.add(new SelectItem("1 ", "One Month "));
        list_Bones.add(new SelectItem("1.5 ", "One and half Month "));
        list_Bones.add(new SelectItem("2", "Two Month"));
        list_Bones.add(new SelectItem("2.5", "Two and half Month"));
        list_Bones.add(new SelectItem("3", "Three Month"));
        list_Bones.add(new SelectItem("3.5", "Three and half Month"));
        list_Bones.add(new SelectItem("4", "Four Month"));
        list_Bones.add(new SelectItem("5", "Five Month"));
        list_Bones.add(new SelectItem("6", "Six Month "));
        list_Bones.add(new SelectItem("7", "Seven Month "));
        list_Bones.add(new SelectItem("8", "Eight Month "));
        list_Bones.add(new SelectItem("9", "Nine Month "));
        list_Bones.add(new SelectItem("10", "Ten Month "));
        return list_Bones;
    }
    
    public ArrayList<SelectItem> listOfExperianceMonth() {
        ArrayList<SelectItem> list_Bones = new ArrayList<>();
        list_Bones.add(new SelectItem(null, "-- Select --"));
        list_Bones.add(new SelectItem("30 ", "Above One Month "));
        list_Bones.add(new SelectItem("45 ", "Above One and half Month "));
        list_Bones.add(new SelectItem("60", "Above Two Month"));
        list_Bones.add(new SelectItem("75", "Above Two and half Month"));
        list_Bones.add(new SelectItem("90", "Above Three Month"));
        list_Bones.add(new SelectItem("105", "Above Three and half Month"));
        list_Bones.add(new SelectItem("120", "Above Four Month"));
        list_Bones.add(new SelectItem("150", "Above Five Month"));
        list_Bones.add(new SelectItem("180", "Above Six Month "));
        list_Bones.add(new SelectItem("210", "Above Seven Month "));
        list_Bones.add(new SelectItem("240", "Above Eight Month "));
        list_Bones.add(new SelectItem("270", "Above Nine Month "));
        list_Bones.add(new SelectItem("300", "Above Ten Month "));
        list_Bones.add(new SelectItem("315", "Above Ten and half month "));
        list_Bones.add(new SelectItem("330", "Above Eleven Month "));
        return list_Bones;
    }
    List<HrEmployeesBonusDetail> listBonusDetail;
    
    public void loademployeeLists(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            
            try {
                if (hrEmployeesBonus.getStatus().equals(0) || hrEmployeesBonus.getStatus().equals(2) || hrEmployeesBonus.getStatus().equals(3)) {
                    double amount = Double.valueOf(event.getNewValue().toString());
                    Double bonus;
                    Double experiance;
                    int listSize = hrEmployeesBonus.getHrEmployeesBonusDetailList().size();
                    for (int i = 0; i < listSize; i++) {
                        String Shday[] = hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).getEmployeeId().getHireDate().split("/");
                        int Ihday = Integer.parseInt(Shday[0]);
                        String Shmonth[] = hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).getEmployeeId().getHireDate().split("/");
                        int Ihmonth = Integer.parseInt(Shmonth[1]);
                        String Shyear[] = hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).getEmployeeId().getHireDate().split("/");
                        int Ihyear = Integer.parseInt(Shyear[2]);
                        String Scday[] = StringDateManipulation.toDayInEc().split("-");
                        int Icday = Integer.parseInt(Scday[2]);
                        String Scmonth[] = StringDateManipulation.toDayInEc().split("-");
                        int Icmonth = Integer.parseInt(Scmonth[1]);
                        String Scyear[] = StringDateManipulation.toDayInEc().split("-");
                        int Icyear = Integer.parseInt(Scyear[0]);
                        int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
                        int expInYear = expday / 365;
                        int expInMonth = ((expday % 365) / 30);
                        budjetEnd = null;
                        budjetEnd = (expInYear + "year-" + expInMonth + "-month " + expday + "-days");
                        if (expday < 365 && expday > 0) {
                            bonus = (expday * hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).getEmployeeId().getBasicSalary() * amount) / 365;
                        } else if (expday > 365) {
                            bonus = hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).getEmployeeId().getBasicSalary() * amount;
                        } else {
                            bonus = 0.00;
                        }
                        for (j = 0; j < taxRateList.size(); j++) {
                            HrPayrollTaxRates get = taxRateList.get(j);
                            if (bonus < get.getToAmount() && bonus > get.getFromAmount()) {
                                tax = bonus * get.getRateInPerercent() / 100;
                                netPay = bonus - tax + get.getConstantAmount();
                                j = taxRateList.size() + 1;
                            } else if (get.getToAmount() < 0 && bonus > get.getFromAmount()) {
                                tax = bonus * get.getRateInPerercent() / 100;
                                netPay = bonus - tax + get.getConstantAmount();
                                j = taxRateList.size() + 1;
                            }
                        }
                        j = 0;
                        hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).setExperiance(budjetEnd);
                        hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).setNum(i + 1);
                        hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).setGrosBonus(bonus);
                        hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).setIncomTax(tax);
                        hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).setNetBonus(netPay);
                        hrEmployeesBonus.addDetail(hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i));
                        bonus = null;
                        tax = 0;
                        netPay = 0;
                    }
                    lockInput = true;
                    
                } else if (hrEmployeesBonus.getStatus().equals(1)) {
                    JsfUtil.addFatalMessage("its aleardy approved can not update data");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        //recreateDataModelwf();
    }
    double tax = 0.0;
    double netPay = 0.0;
    int j = 0;
    Double bonus;
    
    public void loademployeeList() {
        double amount = Double.valueOf(hrEmployeesBonus.getAmount());
        Integer status2 = 2;
        Integer status = 1;
        listemployee = new ArrayList<>();
        listemployee = employeesBeanLocal.searchEmployee(status2, status, hremployees);
        if (listemployee.isEmpty()) {
            JsfUtil.addErrorMessage("Record List not Found");
        } else {
            try {
                
                Double experiance;
                for (int i = 0; i < listemployee.size(); i++) {
                    accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
                    if (accountingPeriod != null) {
                        hrEmployeesBonusDetail = new HrEmployeesBonusDetail();
                        String Shday[] = listemployee.get(i).getHireDate().split("/");
                        int Ihday = Integer.parseInt(Shday[0]);
                        String Shmonth[] = listemployee.get(i).getHireDate().split("/");
                        int Ihmonth = Integer.parseInt(Shmonth[1]);
                        String Shyear[] = listemployee.get(i).getHireDate().split("/");
                        int Ihyear = Integer.parseInt(Shyear[2]);
                        String Scday[] = accountingPeriod.getEndDate().split("-");
                        int Icday = Integer.parseInt(Scday[2]);
                        String Scmonth[] = accountingPeriod.getEndDate().split("-");
                        int Icmonth = Integer.parseInt(Scmonth[1]);
                        String Scyear[] = accountingPeriod.getEndDate().split("-");
                        int Icyear = Integer.parseInt(Scyear[0]);
                        int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
                        int expInYear = expday / 365;
                        int expInMonth = ((expday % 365) / 30);
                        budjetEnd = null;
                        budjetEnd = (expInYear + "year_" + expInMonth + "month " + " " + "OR" + " " + expday + "_" + "In days");
                        listemployee.get(i).setExperiance(budjetEnd);
                        if (hrEmployeesBonus.getExperiancemonth() != null) {
                            if (expday < 365 && expday >= hrEmployeesBonus.getExperiancemonth()) {
                                bonus = (expday * listemployee.get(i).getBasicSalary() * amount) / 365;
                            } else if (expday > 365) {
                                bonus = listemployee.get(i).getBasicSalary() * amount;
                            } else {
                                bonus = 0.00;
                            }
                        } else if (hrEmployeesBonus.getExperiancemonth() == null) {
                            if (expday < 365 && expday > 0) {
                                bonus = (expday * listemployee.get(i).getBasicSalary() * amount) / 365;
                            } else if (expday > 365) {
                                bonus = listemployee.get(i).getBasicSalary() * amount;
                            } else {
                                bonus = 0.00;
                            }
                        }
                        for (j = 0; j < taxRateList.size(); j++) {
                            HrPayrollTaxRates get = taxRateList.get(j);
                            if (bonus < get.getToAmount() && bonus > get.getFromAmount()) {
                                tax = bonus * get.getRateInPerercent() / 100;
                                netPay = bonus - tax + get.getConstantAmount();
                                j = taxRateList.size() + 1;
                            } else if (get.getToAmount() < 0 && bonus > get.getFromAmount()) {
                                tax = bonus * get.getRateInPerercent() / 100;
                                netPay = bonus - tax + get.getConstantAmount();
                                j = taxRateList.size() + 1;
                            }
                        }
                        j = 0;
                        hrEmployeesBonusDetail.setExperiance(budjetEnd);
                        hrEmployeesBonusDetail.setEmployeeId(listemployee.get(i));
                        hrEmployeesBonusDetail.setGrosBonus(bonus);
                        hrEmployeesBonusDetail.setIncomTax(tax);
                        hrEmployeesBonusDetail.setNetBonus(netPay);
                        hrEmployeesBonusDetail.setNum(i + 1);
                        hrEmployeesBonus.addDetail(hrEmployeesBonusDetail);
                        hrEmployeesBonusDetail = null;
                        bonus = null;
                        tax = 0;
                        netPay = 0;
                    } else {
                        JsfUtil.addErrorMessage("check your accounting period or active it!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lockInput = true;
        }
        
    }
    
    public void loademployeeListVCL(ValueChangeEvent event) {
        double amount = Double.valueOf(hrEmployeesBonus.getAmount());
        Integer status2 = 2;
        Integer status = 1;
        listemployee = new ArrayList<>();
        listemployee = employeesBeanLocal.searchEmployee(status2, status, hremployees);
        if (listemployee.isEmpty()) {
            JsfUtil.addErrorMessage("Record List not Found");
        } else {
            try {
                
                Double experiance;
                for (int i = 0; i < listemployee.size(); i++) {
                    accountingPeriod = accountingPeriodBeanLocal.getCurretActivePeriod();
                    if (accountingPeriod != null) {
                        hrEmployeesBonusDetail = new HrEmployeesBonusDetail();
                        String Shday[] = listemployee.get(i).getHireDate().split("/");
                        int Ihday = Integer.parseInt(Shday[0]);
                        String Shmonth[] = listemployee.get(i).getHireDate().split("/");
                        int Ihmonth = Integer.parseInt(Shmonth[1]);
                        String Shyear[] = listemployee.get(i).getHireDate().split("/");
                        int Ihyear = Integer.parseInt(Shyear[2]);
                        String Scday[] = accountingPeriod.getEndDate().split("-");
                        int Icday = Integer.parseInt(Scday[2]);
                        String Scmonth[] = accountingPeriod.getEndDate().split("-");
                        int Icmonth = Integer.parseInt(Scmonth[1]);
                        String Scyear[] = accountingPeriod.getEndDate().split("-");
                        int Icyear = Integer.parseInt(Scyear[0]);
                        int expday = ((Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365));
                        int expInYear = expday / 365;
                        int expInMonth = ((expday % 365) / 30);
                        budjetEnd = null;
                        budjetEnd = (expInYear + "year_" + expInMonth + "month " + " " + "OR" + " " + expday + "_" + "In days");
                        listemployee.get(i).setExperiance(budjetEnd);
                        if (hrEmployeesBonus.getExperiancemonth() != null) {
                            if (expday < 365 && expday >= hrEmployeesBonus.getExperiancemonth()) {
                                bonus = (expday * listemployee.get(i).getBasicSalary() * amount) / 365;
                            } else if (expday > 365) {
                                bonus = listemployee.get(i).getBasicSalary() * amount;
                            } else {
                                bonus = 0.00;
                            }
                        } else if (hrEmployeesBonus.getExperiancemonth() == null) {
                            if (expday < 365 && expday > 0) {
                                bonus = (expday * listemployee.get(i).getBasicSalary() * amount) / 365;
                            } else if (expday > 365) {
                                bonus = listemployee.get(i).getBasicSalary() * amount;
                            } else {
                                bonus = 0.00;
                            }
                        }
                        for (j = 0; j < taxRateList.size(); j++) {
                            HrPayrollTaxRates get = taxRateList.get(j);
                            if (bonus < get.getToAmount() && bonus > get.getFromAmount()) {
                                tax = bonus * get.getRateInPerercent() / 100;
                                netPay = bonus - tax + get.getConstantAmount();
                                j = taxRateList.size() + 1;
                            } else if (get.getToAmount() < 0 && bonus > get.getFromAmount()) {
                                tax = bonus * get.getRateInPerercent() / 100;
                                netPay = bonus - tax + get.getConstantAmount();
                                j = taxRateList.size() + 1;
                            }
                        }
                        j = 0;
                        hrEmployeesBonusDetail.setIncomTax(tax);
                        hrEmployeesBonusDetail.setNetBonus(netPay);
                        hrEmployeesBonusDetail.setExperiance(budjetEnd);
                        hrEmployeesBonusDetail.setEmployeeId(listemployee.get(i));
                        hrEmployeesBonusDetail.setGrosBonus(bonus);
                        hrEmployeesBonusDetail.setNum(i + 1);
                        hrEmployeesBonus.addDetail(hrEmployeesBonusDetail);
                        hrEmployeesBonusDetail = null;
                        bonus = null;
                        tax = 0;
                        netPay = 0;
                    } else {
                        JsfUtil.addErrorMessage("check your accounting period or active it!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lockInput = true;
        }
        
    }
    
    String[] targetArray;
    
    public String[] getTargetArray() {
        return targetArray;
    }
    
    public void setTargetArray(String[] targetArray) {
        this.targetArray = targetArray;
    }
    
    public String getBudjetEnd() {
        return budjetEnd;
    }
    
    public void setBudjetEnd(String budjetEnd) {
        this.budjetEnd = budjetEnd;
    }
    
    String budjetEnd;
    
    public void saveEmployeesBonus() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveEmployeesBonus", dataset)) {
                if (update == 0) {
                    try {
                        hrEmployeesBonus.setStatus(Constants.PREPARE_VALUE);
                        employeesBeanLocal.saveorupdate(hrEmployeesBonus);
                        wfHrProcessed.setBonusId(hrEmployeesBonus);
                        wfHrProcessed.setProcessedOn(hrEmployeesBonus.getRequestdate());
                        wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                        wfHrProcessed.setDecision(hrEmployeesBonus.getStatus());
                        wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                        JsfUtil.addSuccessMessage("Saved Successful.");
                        clearpage();
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Unable to Save Data.");
                    }
                } else {
                    try {
                        if (hrEmployeesBonus.getStatus().equals(0)
                                || hrEmployeesBonus.getStatus().equals(2) || hrEmployeesBonus.getStatus().equals(3)) {
//                         hrEmployeesBonus.setApprovededby(SessionBean.getUserId());
                            employeesBeanLocal.edit(hrEmployeesBonus);
                            wfHrProcessed.setBonusId(hrEmployeesBonus);
                            wfHrProcessed.setProcessedOn(hrEmployeesBonus.getRequestdate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrEmployeesBonus.getStatus());
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Update Successfully.");
                            clearpage();
                            update = 1;
                            
                        } else if (hrEmployeesBonus.getStatus().equals(1)) {
                            JsfUtil.addFatalMessage("Unable to update approved data");
                        }
                    } catch (Exception e) {
                        JsfUtil.addErrorMessage("Unable to update Data.");
                    }
                }
                
            } else {
                JsfUtil.addErrorMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveEmployeesBonus");
                eventEntry.setProgram(program);
                
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void clearpage() {
        listemployee = null;
        hrEmployeesBonus = null;
        bonusDataModels = null;
        hrEmployeesBonusDetail = null;
        saveOrUpdateButton = "Save";
        update = 0;
    }
    
    private void recerateModel() {
        bonusDataModels = null;
        bonusDataModels = new ListDataModel(new ArrayList<>(hrEmployeesBonus.getHrEmployeesBonusDetailList()));
        for (int i = 0; i < hrEmployeesBonus.getHrEmployeesBonusDetailList().size(); i++) {
            hrEmployeesBonus.getHrEmployeesBonusDetailList().get(i).setNum(i + 1);
        }
        
    }
    
    private void recerateModel2() {
        bonusDataModelss = null;
        bonusDataModelss = new ListDataModel(new ArrayList<>(hrEmployeesBonus.getHrEmployeesBonusDetailList()));
        
    }
    
    public void filiterByStatus_VclEmployeeBonus(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    
    private void populateTable() {
        try {
            List<HrEmployeesBonus> readFiltereddata = employeesBeanLocal.loadFiltereddata(status);
            bonusDataModels = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void PaymentapproveDisplayChanged(SelectEvent event) {
        hrEmployeesBonus = (HrEmployeesBonus) event.getObject();
        hrEmployeesBonus.setId(hrEmployeesBonus.getId());
        String year = hrEmployeesBonus.getBudgetYear();
        hrEmployeesBonus = employeesBeanLocal.findByBgtYear(year);
        recreateDataModelwf();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "Save";
        
    }
    
    public void createNewAdditionalAmount1() {
        
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    
    public void approveEmployeeBonus() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "approveEmployeeBonus", dataset)) {
                if (hrEmployeesBonus.getId() == null) {
                    if (hrEmployeesBonus.getStatus().equals(1)) {
                        JsfUtil.addFatalMessage("Can't Approve empty data");
                    } else if (hrEmployeesBonus.getStatus().equals(2)) {
                        JsfUtil.addFatalMessage("Can't Reject empty data");
                    }
                } else {
                    try {
                        hrEmployeesBonus.setStatus(hrEmployeesBonus.getStatus());
                        System.out.println("hrEmployeesBonus.setStatus" + hrEmployeesBonus.getStatus());
                        System.out.println("workFlow.isApproveStatus()" + workFlow.isApproveStatus());
                        if (hrEmployeesBonus.getStatus().equals(1) && workFlow.isApproveStatus()) {
                            System.out.println("in side work flow approve sat");
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            hrEmployeesBonus.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setBonusId(hrEmployeesBonus);
                            wfHrProcessed.setProcessedOn(hrEmployeesBonus.getRequestdate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrEmployeesBonus.getStatus());
                            employeesBeanLocal.saveUpdate(hrEmployeesBonus);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Approve Successfully");
                            clearpage();
                            
                        } else if (hrEmployeesBonus.getStatus().equals(1) && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            hrEmployeesBonus.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setBonusId(hrEmployeesBonus);
                            wfHrProcessed.setProcessedOn(hrEmployeesBonus.getRequestdate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrEmployeesBonus.getStatus());
                            employeesBeanLocal.saveUpdate(hrEmployeesBonus);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("approved Successfully");
                        } else if (hrEmployeesBonus.getStatus().equals(2) && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            hrEmployeesBonus.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setBonusId(hrEmployeesBonus);
                            wfHrProcessed.setProcessedOn(hrEmployeesBonus.getRequestdate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrEmployeesBonus.getStatus());
                            employeesBeanLocal.saveUpdate(hrEmployeesBonus);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Rejected Successfully");
                        } else if (hrEmployeesBonus.getStatus().equals(2) && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            hrEmployeesBonus.setStatus(workFlow.getUserStatus());
                            wfHrProcessed.setBonusId(hrEmployeesBonus);
                            wfHrProcessed.setProcessedOn(hrEmployeesBonus.getRequestdate());
                            wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                            wfHrProcessed.setDecision(hrEmployeesBonus.getStatus());
                            employeesBeanLocal.saveUpdate(hrEmployeesBonus);
                            wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                            JsfUtil.addSuccessMessage("Rejected Successfully");
                        }
                        reset();
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Unable to Approve Or Reject data");
                    }
                }
                
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void reset() {
        listBonusDetail = null;
        hrEmployeesBonus = null;
        hrEmployeesBonusDetail = null;
        filterByStatus = null;
    }
    private transient int row = 0;
    
    public int getRow() {
        return ++row;
    }
    
    public EmployeeBonusController() {
    }
//</editor-fold>
}
