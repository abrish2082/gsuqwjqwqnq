/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.overtime;

import et.gov.eep.commonApplications.businessLogic.WfHrProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.mapper.WfHrProcessedFacade;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.commonApplications.utility.number.NumAndDateFormatter;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.mapper.FmsLuBudgetYearFacade;
import et.gov.eep.fcms.mapper.admin.FmsGeneralLedgerFacade;
import et.gov.eep.fcms.mapper.budget.FmsOperatingBudget1Facade;
import et.gov.eep.fcms.mapper.budget.FmsOperatingBudgetDetailFacade;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.businessLogic.organization.HrDepartmentsBeanLocal;
import et.gov.eep.hrms.businessLogic.overtime.OT_Payroll_DetailBeanLocal;
import et.gov.eep.hrms.businessLogic.overtime.OverTimeRateBeanLocal;
import et.gov.eep.hrms.businessLogic.overtime.OvertimeReuestBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollEarningDeductionsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMaintainEdsBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPgPlDeptBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.entity.attendance.HrAttendances;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuJobCategories;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.overtime.HrOtPayrollDetail;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRates;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRequestDetails;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRequests;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMaintainEds;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPgDept;
import et.gov.eep.hrms.mapper.overtime.HrOtPayrollDetailFacade;
import et.gov.eep.hrms.mapper.overtime.HrOvertimeRequestsFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
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
@Named(value = "overtimeRequestController")
@ViewScoped
public class OvertimeRequestController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    @EJB
            OverTimeRateBeanLocal overtimeratebeanLocal;
    @EJB
            HrDepartmentsBeanLocal hrDepartmentsBeanLocal;
    @EJB
            HrEmployeeBeanLocal hrEmployeesBeanLocal;
    @EJB
            OvertimeReuestBeanLocal OvertimeReuestBeanLocal;
    @EJB
            OT_Payroll_DetailBeanLocal OT_Payroll_DetailBeanLocal;
    @EJB
            WfHrProcessedBeanLocal wfprocessedBeanLocal;
    @EJB
            WfHrProcessedFacade wfHrProcessedFacade;
    @EJB
            HrPayrollPlPgBeanLocal hrPayrollPlPgBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrOvertimeRequests hrOvertimeRequests;
    @Inject
            HrOvertimeRequestDetails hrOvertimeRequestDetails;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrLuJobLevels hrLuJobLevels;
    @Inject
            HrLuJobCategories hrLuJobCategories;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrDepartments hrDepartments;
    @Inject
            HrOvertimeRates hrOvertimeRate;
    @Inject
            HrOtPayrollDetail hrOtPayrollDetail;
    @Inject
            HrPayrollPlPg hrPayrollPlPg;
    @Inject
            WfHrProcessed wfHrProcessed;
    @Inject
            SessionBean SessionBean;
    @Inject
            WorkFlow workFlow;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Data Model and List Declaration">
    DataModel<WfHrProcessed> workFlowDataModel = new ListDataModel<>();
    DataModel<HrOvertimeRequests> hrOvertimeRequestsDataModel = new ListDataModel<>();
    HrOvertimeRequests selectedovertimerequest = null;
    DataModel<HrOvertimeRequestDetails> hrOvertimeRequestDetailsDatamodel = new ListDataModel<>();
    List<SelectItem> filterByStatus = new ArrayList<>();
    List<HrDepartments> DepartmentsList = new ArrayList<>();
    List<HrOvertimeRates> overtimeList = new ArrayList<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="varaible diclaration ">
    HrOvertimeRequestDetails selectedhrOvertimeRequestDetails = null;
    String payLocation;
    String payGroup;
    Integer requestNotificationCounter = 0;
    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean position = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String tab = "disabled";
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;
    private String addorUpdate = "Add";
    private String saveOrUpdateButton = "Save";
    int update = 0;
    int status = 1;
    int empStatus;
    Set<String> fieldCheck = new HashSet<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="list declaration">
    List<HrOvertimeRequests> overtimerequestList;
    List<HrOvertimeRequests> overtimelist;
    List<HrOvertimeRequestDetails> overtimerequestdetaillist;
    private ArrayList<SelectItem> listOfYear = new ArrayList<>();
    private ArrayList<SelectItem> listOfmonth = new ArrayList<>();
    private ArrayList<SelectItem> listOfDays = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        setListOfYear(listOfBonesYear());
        setListOfmonth(ListofMonths());
        setListOfDays(Listofdays());
        String Shday = StringDateManipulation.toDayInEc();
        hrOvertimeRequests.setPreparedOn(Shday);
        DepartmentsList = hrDepartmentsBeanLocal.findAll();
        overtimeList = overtimeratebeanLocal.findAll();
        overtimerequestList = OvertimeReuestBeanLocal.findAll();
        fmsGeneralLedgerList = fmsGeneralLedgerFacade.findAll();
        fmsLuBudgetYearList = fmsLuBudgetYearFacade.findAll();
        requestcounter();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter and settre">
    public HrOtPayrollDetail getHrOtPayrollDetail() {
        if (hrOtPayrollDetail == null) {
            hrOtPayrollDetail = new HrOtPayrollDetail();
        }
        return hrOtPayrollDetail;
    }
    
    public Integer getRequestNotificationCounter() {
        return requestNotificationCounter;
    }
    
    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }
    @EJB
            HrOvertimeRequestsFacade hrOvertimeRequestsFacade;
    
    public void requestcounter() {
        overtimelist = hrOvertimeRequestsFacade.findrequestlist();
        requestNotificationCounter = overtimelist.size();
    }
    
    public HrLuJobLevels getHrLuJobLevels() {
        if (hrLuJobLevels == null) {
            hrLuJobLevels = new HrLuJobLevels();
        }
        return hrLuJobLevels;
    }
    
    public HrPayrollPlPg getHrPayrollPlPg() {
        if (hrPayrollPlPg == null) {
            hrPayrollPlPg = new HrPayrollPlPg();
        }
        return hrPayrollPlPg;
    }
    
    public void setHrPayrollPlPg(HrPayrollPlPg hrPayrollPlPg) {
        this.hrPayrollPlPg = hrPayrollPlPg;
    }
    
    public void setHrLuJobLevels(HrLuJobLevels hrLuJobLevels) {
        this.hrLuJobLevels = hrLuJobLevels;
    }
    
    public String getPayLocation() {
        return payLocation;
    }
    
    public void setPayLocation(String payLocation) {
        this.payLocation = payLocation;
    }
    
    public String getPayGroup() {
        return payGroup;
    }
    
    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }
    
    public HrLuJobCategories getHrLuJobCategories() {
        if (hrLuJobCategories == null) {
            hrLuJobCategories = new HrLuJobCategories();
        }
        return hrLuJobCategories;
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
    
    public void setHrLuJobCategories(HrLuJobCategories hrLuJobCategories) {
        this.hrLuJobCategories = hrLuJobCategories;
    }
    
    public void setHrOtPayrollDetail(HrOtPayrollDetail hrOtPayrollDetail) {
        this.hrOtPayrollDetail = hrOtPayrollDetail;
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
    
    public HrPayrollMaintainEds getHrPayrollMaintainEds() {
        if (hrPayrollMaintainEds == null) {
            hrPayrollMaintainEds = new HrPayrollMaintainEds();
        }
        return hrPayrollMaintainEds;
    }
    
    public void setHrPayrollMaintainEds(HrPayrollMaintainEds hrPayrollMaintainEds) {
        this.hrPayrollMaintainEds = hrPayrollMaintainEds;
    }
    
    public List<HrOvertimeRates> getOvertimeList() {
        return overtimeList;
    }
    
    public void setOvertimeList(List<HrOvertimeRates> overtimeList) {
        this.overtimeList = overtimeList;
    }
    
    public HrEmployeeBeanLocal getHrEmployeesBeanLocal() {
        return hrEmployeesBeanLocal;
    }
    
    public void setHrEmployeesBeanLocal(HrEmployeeBeanLocal hrEmployeesBeanLocal) {
        this.hrEmployeesBeanLocal = hrEmployeesBeanLocal;
    }
    
    public HrOvertimeRequests getHrOvertimeRequests() {
        if (hrOvertimeRequests == null) {
            hrOvertimeRequests = new HrOvertimeRequests();
        }
        return hrOvertimeRequests;
    }
    
    public void setHrOvertimeRequests(HrOvertimeRequests hrOvertimeRequests) {
        this.hrOvertimeRequests = hrOvertimeRequests;
    }
    
    public HrOvertimeRequestDetails getHrOvertimeRequestDetails() {
        if (hrOvertimeRequestDetails == null) {
            hrOvertimeRequestDetails = new HrOvertimeRequestDetails();
        }
        return hrOvertimeRequestDetails;
    }
    
    public void setHrOvertimeRequestDetails(HrOvertimeRequestDetails hrOvertimeRequestDetails) {
        this.hrOvertimeRequestDetails = hrOvertimeRequestDetails;
    }
    
    public HrOvertimeRates getHrOvertimeRate() {
        if (hrOvertimeRate == null) {
            hrOvertimeRate = new HrOvertimeRates();
        }
        return hrOvertimeRate;
    }
    
    public void setHrOvertimeRate(HrOvertimeRates hrOvertimeRate) {
        this.hrOvertimeRate = hrOvertimeRate;
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
    
    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }
    
    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }
    
    public HrDepartments getHrDepartments() {
        if (hrDepartments == null) {
            hrDepartments = new HrDepartments();
        }
        return hrDepartments;
    }
    
    public void setHrDepartments(HrDepartments hrDepartments) {
        this.hrDepartments = hrDepartments;
    }
    
    public DataModel<HrOvertimeRequests> getHrOvertimeRequestsDataModel() {
        return hrOvertimeRequestsDataModel;
    }
    
    public void setHrOvertimeRequestsDataModel(DataModel<HrOvertimeRequests> hrOvertimeRequestsDataModel) {
        this.hrOvertimeRequestsDataModel = hrOvertimeRequestsDataModel;
    }
    
    public HrOvertimeRequests getSelectedovertimerequest() {
        return selectedovertimerequest;
    }
    
    public void setSelectedovertimerequest(HrOvertimeRequests selectedovertimerequest) {
        this.selectedovertimerequest = selectedovertimerequest;
    }
    
    public DataModel<HrOvertimeRequestDetails> getHrOvertimeRequestDetailsDatamodel() {
        return hrOvertimeRequestDetailsDatamodel;
    }
    
    public void setHrOvertimeRequestDetailsDatamodel(DataModel<HrOvertimeRequestDetails> hrOvertimeRequestDetailsDatamodel) {
        this.hrOvertimeRequestDetailsDatamodel = hrOvertimeRequestDetailsDatamodel;
    }
    
    public List<SelectItem> getFilterByStatus() {
        return OvertimeReuestBeanLocal.Filterstatus();
    }
    
    public void setFilterByStatus(List<SelectItem> filterByStatus) {
        this.filterByStatus = filterByStatus;
    }
    
    public List<HrDepartments> getDepartmentsList() {
        return DepartmentsList;
    }
    
    public void setDepartmentsList(List<HrDepartments> DepartmentsList) {
        this.DepartmentsList = DepartmentsList;
    }
    
    public HrOvertimeRequestDetails getSelectedhrOvertimeRequestDetails() {
        return selectedhrOvertimeRequestDetails;
    }
    
    public void setSelectedhrOvertimeRequestDetails(HrOvertimeRequestDetails selectedhrOvertimeRequestDetails) {
        this.selectedhrOvertimeRequestDetails = selectedhrOvertimeRequestDetails;
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
    
    public boolean isPosition() {
        return position;
    }
    
    public void setPosition(boolean position) {
        this.position = position;
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
    
    public String getActiveIndex() {
        return activeIndex;
    }
    
    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }
    
    public boolean isRenderPnlApproved() {
        return renderPnlApproved;
    }
    
    public void setRenderPnlApproved(boolean renderPnlApproved) {
        this.renderPnlApproved = renderPnlApproved;
    }
    
    public boolean isCheckBoxReqst() {
        return checkBoxReqst;
    }
    
    public void setCheckBoxReqst(boolean checkBoxReqst) {
        this.checkBoxReqst = checkBoxReqst;
    }
    
    public boolean isCheckBoxApprove() {
        return checkBoxApprove;
    }
    
    public void setCheckBoxApprove(boolean checkBoxApprove) {
        this.checkBoxApprove = checkBoxApprove;
    }
    
    public String getAddorUpdate() {
        return addorUpdate;
    }
    
    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
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
    
    public int getEmpStatus() {
        return empStatus;
    }
    
    public void setEmpStatus(int empStatus) {
        this.empStatus = empStatus;
    }
    
    public Set<String> getFieldCheck() {
        return fieldCheck;
    }
    
    public void setFieldCheck(Set<String> fieldCheck) {
        this.fieldCheck = fieldCheck;
    }
    
    public ArrayList<SelectItem> getListOfYear() {
        return listOfYear;
    }
    
    public void setListOfYear(ArrayList<SelectItem> listOfYear) {
        this.listOfYear = listOfYear;
    }
    
    public ArrayList<SelectItem> getListOfmonth() {
        return listOfmonth;
    }
    
    public void setListOfmonth(ArrayList<SelectItem> listOfmonth) {
        this.listOfmonth = listOfmonth;
    }
    
    public List<HrOvertimeRequests> getOvertimerequestList() {
        return overtimerequestList;
    }
    
    public void setOvertimerequestList(List<HrOvertimeRequests> overtimerequestList) {
        this.overtimerequestList = overtimerequestList;
    }
    
    public ArrayList<SelectItem> getListOfDays() {
        return listOfDays;
    }

    public void setListOfDays(ArrayList<SelectItem> listOfDays) {
        this.listOfDays = listOfDays;
    }

    public List<HrOvertimeRequests> getOvertimelist() {
        return overtimelist;
    }

    public void setOvertimelist(List<HrOvertimeRequests> overtimelist) {
        this.overtimelist = overtimelist;
    }
    public List<HrOvertimeRequestDetails> getOvertimerequestdetaillist() {
        return overtimerequestdetaillist;
    }
    
    public void setOvertimerequestdetaillist(List<HrOvertimeRequestDetails> overtimerequestdetaillist) {
        this.overtimerequestdetaillist = overtimerequestdetaillist;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public ArrayList<SelectItem> listOfBonesYear() {
        ArrayList<SelectItem> list_Year = new ArrayList<>();
        int year = getYear(StringDateManipulation.toDayInEc());
        list_Year.add(new SelectItem(null, "-- Select --"));
        for (int i = 0; i < 1; i++) {
            list_Year.add(new SelectItem(Integer.toString(year), Integer.toString(year)));
            year -= 1;
            
        }
        return list_Year;
    }
    
    private int getYear(String _date) {
        String dmy[] = _date.split("-");
        
        return Integer.parseInt(dmy[0]);
    }
    
    public ArrayList<SelectItem> ListofMonths() {
        ArrayList<SelectItem> list_Bones = new ArrayList<>();
        list_Bones.add(new SelectItem(null, "-- Select --"));
        list_Bones.add(new SelectItem("1", "September "));
        list_Bones.add(new SelectItem("2", "October "));
        list_Bones.add(new SelectItem("3", "November"));
        list_Bones.add(new SelectItem("4", "December"));
        list_Bones.add(new SelectItem("5", "January"));
        list_Bones.add(new SelectItem("6", "February"));
        list_Bones.add(new SelectItem("7", "March"));
        list_Bones.add(new SelectItem("8", "April"));
        list_Bones.add(new SelectItem("9", "May "));
        list_Bones.add(new SelectItem("10", "June"));
        list_Bones.add(new SelectItem("11", "July"));
        list_Bones.add(new SelectItem("12", "Augest"));
//        String Shday[] = StringDateManipulation.toDayInEc().split("-");
//        Integer month1 = Integer.parseInt(Shday[1]);
//        ArrayList<SelectItem> list_filterdmonth = new ArrayList<>();
//        for (int i = 0; i < list_Bones.size(); i++) {
//            if (Integer.parseInt((list_Bones.get(i).getValue()).toString()) <= month1) {
//               list_filterdmonth.add(list_Bones.get(i));
//            } else {
//                i=list_Bones.size()+1;
//            }
//
//        }
        
        return list_Bones;
        
    }
    
    public ArrayList<SelectItem> Listofdays() {
        ArrayList<SelectItem> list_days = new ArrayList<>();
        list_days.add(new SelectItem(null, "-- Select --"));
        list_days.add(new SelectItem("1", "1 "));
        list_days.add(new SelectItem("2", "2 "));
        list_days.add(new SelectItem("3", "3"));
        list_days.add(new SelectItem("4", "4"));
        list_days.add(new SelectItem("5", "5"));
        list_days.add(new SelectItem("6", "6"));
        list_days.add(new SelectItem("7", "7"));
        list_days.add(new SelectItem("8", "8"));
        list_days.add(new SelectItem("9", "9"));
        list_days.add(new SelectItem("10", "10"));
        list_days.add(new SelectItem("11", "11"));
        list_days.add(new SelectItem("12", "12"));
        list_days.add(new SelectItem("13", "13"));
        list_days.add(new SelectItem("14", "14"));
        list_days.add(new SelectItem("15", "15"));
        list_days.add(new SelectItem("16", "16"));
        list_days.add(new SelectItem("17", "17"));
        list_days.add(new SelectItem("18", "18"));
        list_days.add(new SelectItem("19", "19"));
        list_days.add(new SelectItem("20", "20"));
        list_days.add(new SelectItem("21", "21"));
        list_days.add(new SelectItem("22", "22"));
        list_days.add(new SelectItem("23", "23"));
        list_days.add(new SelectItem("24", "24"));
        list_days.add(new SelectItem("25", "25"));
        list_days.add(new SelectItem("26", "26"));
        list_days.add(new SelectItem("27", "27"));
        list_days.add(new SelectItem("28", "28"));
        list_days.add(new SelectItem("29", "29"));
        list_days.add(new SelectItem("30", "30"));
        return list_days;
    }
    
    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
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
    
    public void recreateDataModel() {
        hrOvertimeRequestDetailsDatamodel = null;
        hrOvertimeRequestDetailsDatamodel = new ListDataModel(new ArrayList(hrOvertimeRequests.getHrOvertimeRequestDetailsList()));
    }
    
    public void clearPopup() {
        hrEmployees = new HrEmployees();
        hrDepartments = new HrDepartments();
        hrJobTypes = new HrJobTypes();
        hrOvertimeRequestDetails = null;
        filterByStatus = null;
        hrOvertimeRequestsDataModel = null;
    }
    Double Total = 0.0;
    
    public Double getTotal() {
        return Total;
    }
    
    public void setTotal(Double Total) {
        this.Total = Total;
    }
    Integer workinghourA = 0, workinghourD = 0;
    
    public void addDetail() {
        if (hrJobTypes.getType().equals(BigInteger.valueOf(1))) {
            JsfUtil.addFatalMessage("he/she is in managerial position !! you can't do overtime for them");
        } else if (fieldCheck.contains(hrOvertimeRequestDetails.getEmpId().getEmpId())
                && fieldCheck.contains(hrOvertimeRequestDetails.getOtRateId().getOtTypes())) {
            JsfUtil.addFatalMessage("data duplicate entry");
        } else {
            System.out.println("inside else");
            if (fieldCheck.contains(hrOvertimeRequestDetails.getEmpId().getEmpId()) == false) {
                if (hrOvertimeRequestDetails.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor A")) {
                    System.out.println("inside else ifif");
                    workinghourA = workinghourA + hrOvertimeRequestDetails.getWorkingHour();
                    System.out.println("workinghourA" + workinghourA);
                    if (hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour() != null) {
                        if (workinghourA <= hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour()) {
                            OTPaymentamount = formatter.decimalFormat(hrEmployees.getBasicSalary() * hrOvertimeRequestDetails.getWorkingHour() * hrOvertimeRequestDetails.getOtRateId().getOtRate() / 185);
                            hrOvertimeRequestDetails.setOtAmount(OTPaymentamount);
                            hrOvertimeRequests.addDetail(hrOvertimeRequestDetails);
                            fieldCheck.add(hrOvertimeRequestDetails.getEmpId().getEmpId());
                            fieldCheck.add(hrOvertimeRequestDetails.getOtRateId().getOtTypes());
                            calculateTotal();
                            recreateDataModel();
                            clearPopup();
                            OTPaymentamount = null;
                        } else {
                            JsfUtil.addFatalMessage("check your summery!! maximum working hour OT Factor A");
                            workinghourA = 0;
                        }
                    } else {
                        JsfUtil.addFatalMessage("Maximum working hour is Empty");
                    }
                } else if (hrOvertimeRequestDetails.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor D")) {
                    System.out.println("inside else if else if");
                    workinghourD = workinghourD + hrOvertimeRequestDetails.getWorkingHour();
                    if (hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour() != null) {
                        if (workinghourD <= hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour()) {
                            OTPaymentamount = formatter.decimalFormat(hrEmployees.getBasicSalary() * hrOvertimeRequestDetails.getWorkingHour() * hrOvertimeRequestDetails.getOtRateId().getOtRate() / 185);
                            hrOvertimeRequestDetails.setOtAmount(OTPaymentamount);
                            hrOvertimeRequests.addDetail(hrOvertimeRequestDetails);
                            fieldCheck.add(hrOvertimeRequestDetails.getEmpId().getEmpId());
                            fieldCheck.add(hrOvertimeRequestDetails.getOtRateId().getOtTypes());
                            calculateTotal();
                            recreateDataModel();
                            clearPopup();
                            OTPaymentamount = null;
                        } else {
                            workinghourD = 0;
                            JsfUtil.addFatalMessage("check your summery!! maximum working hour OT Factor D");
                        }
                    } else {
                        JsfUtil.addFatalMessage("Maximum working hour is Empty");
                    }
                } else {
                    OTPaymentamount = formatter.decimalFormat(hrEmployees.getBasicSalary() * hrOvertimeRequestDetails.getWorkingHour() * hrOvertimeRequestDetails.getOtRateId().getOtRate() / 185);
                    hrOvertimeRequestDetails.setOtAmount(OTPaymentamount);
                    hrOvertimeRequests.addDetail(hrOvertimeRequestDetails);
                    fieldCheck.add(hrOvertimeRequestDetails.getEmpId().getEmpId());
                    fieldCheck.add(hrOvertimeRequestDetails.getOtRateId().getOtTypes());
                    calculateTotal();
                    recreateDataModel();
                    clearPopup();
                    OTPaymentamount = null;
                }
            } else if (fieldCheck.contains(hrOvertimeRequestDetails.getEmpId().getEmpId())) {
                if (hrOvertimeRequestDetails.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor A")) {
                    workinghourA = workinghourA + hrOvertimeRequestDetails.getWorkingHour();
                    if (hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour() != null) {
                        if (workinghourA <= hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour()) {
                            OTPaymentamount = formatter.decimalFormat(hrEmployees.getBasicSalary() * hrOvertimeRequestDetails.getWorkingHour() * hrOvertimeRequestDetails.getOtRateId().getOtRate() / 185);
                            hrOvertimeRequestDetails.setOtAmount(OTPaymentamount);
                            hrOvertimeRequests.addDetail(hrOvertimeRequestDetails);
                            fieldCheck.add(hrOvertimeRequestDetails.getEmpId().getEmpId());
                            fieldCheck.add(hrOvertimeRequestDetails.getOtRateId().getOtTypes());
                            calculateTotal();
                            recreateDataModel();
                            clearPopup();
                            OTPaymentamount = null;
                            workinghourA = 0;
                        } else {
                            workinghourA = 0;
                            JsfUtil.addFatalMessage("check your summery!! maximum working hour OT Factor A");
                        }
                    } else {
                        JsfUtil.addFatalMessage("Maximum working hour is Empty");
                    }
                    
                } else if (hrOvertimeRequestDetails.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor D")) {
                    workinghourD = workinghourD + hrOvertimeRequestDetails.getWorkingHour();
                    if (hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour() != null) {
                        if (workinghourD <= hrOvertimeRequestDetails.getOtRateId().getMaximumWorkingHour()) {
                            OTPaymentamount = formatter.decimalFormat(hrEmployees.getBasicSalary() * hrOvertimeRequestDetails.getWorkingHour() * hrOvertimeRequestDetails.getOtRateId().getOtRate() / 185);
                            hrOvertimeRequestDetails.setOtAmount(OTPaymentamount);
                            hrOvertimeRequests.addDetail(hrOvertimeRequestDetails);
                            fieldCheck.add(hrOvertimeRequestDetails.getEmpId().getEmpId());
                            fieldCheck.add(hrOvertimeRequestDetails.getOtRateId().getOtTypes());
                            calculateTotal();
                            recreateDataModel();
                            clearPopup();
                            OTPaymentamount = null;
                        } else {
                            workinghourD = 0;
                            JsfUtil.addFatalMessage("check your summery!! maximum working hour OT Factor D");
                        }
                    } else {
                        JsfUtil.addFatalMessage("Maximum working hour is Empty");
                    }
                    
                } else {
                    OTPaymentamount = formatter.decimalFormat(hrEmployees.getBasicSalary() * hrOvertimeRequestDetails.getWorkingHour() * hrOvertimeRequestDetails.getOtRateId().getOtRate() / 185);
                    hrOvertimeRequestDetails.setOtAmount(OTPaymentamount);
                    hrOvertimeRequests.addDetail(hrOvertimeRequestDetails);
                    fieldCheck.add(hrOvertimeRequestDetails.getEmpId().getEmpId());
                    fieldCheck.add(hrOvertimeRequestDetails.getOtRateId().getOtTypes());
                    calculateTotal();
                    recreateDataModel();
                    clearPopup();
                    OTPaymentamount = null;
                }
            }
        }
        
    }
    
    Double OTPaymentamount = 0.0;
    
    public Double getOTPaymentamount() {
        return OTPaymentamount;
    }
    
    public void setOTPaymentamount(Double OTPaymentamount) {
        this.OTPaymentamount = OTPaymentamount;
    }
    
    public ArrayList<HrEmployees> searchEmployeeByName(String hrEmployee) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setFirstName(hrEmployee);
        employee = hrEmployeesBeanLocal.searchEmployeeByName(hrEmployees);
        return employee;
    }
    
    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeesBeanLocal.getByFirstName(hrEmployees);
            hrJobTypes = hrEmployees.getJobId();
            hrLuJobLevels = hrJobTypes.getJobLevelId();
            hrDepartments = hrEmployees.getDeptId();
            hrOvertimeRequestDetails.setEmpId(hrEmployees);
        } catch (Exception ex) {
            System.err.println("Exception");
            ex.printStackTrace();
        }
    }
    
    public void editDataTable() {
        hrOvertimeRequestDetails = hrOvertimeRequestDetailsDatamodel.getRowData();
        hrOvertimeRequests = hrOvertimeRequestDetails.getOvertimeRequestId();
        hrEmployees = hrOvertimeRequestDetails.getEmpId();
        hrDepartments = hrEmployees.getDeptId();
        hrJobTypes = hrEmployees.getJobId();
        hrLuJobLevels = hrJobTypes.getJobLevelId();
        addorUpdate = "Add Changes";
    }
    @Inject
            HrPayrollPlPgDept hrPayrollPlPgDept;
    @EJB
            HrPayrollPgPlDeptBeanLocal hrPayrollPgPlDeptBeanLocal;
    @Inject
            FmsCostcSystemJunction fmsCostcSystemJunction;
    @EJB
            FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    
    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        if (fmsCostcSystemJunction == null) {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        return fmsCostcSystemJunction;
    }
    
    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }
    
    public HrPayrollPlPgDept getHrPayrollPlPgDept() {
        if (hrPayrollPlPgDept == null) {
            hrPayrollPlPgDept = new HrPayrollPlPgDept();
        }
        return hrPayrollPlPgDept;
    }
    
    public void setHrPayrollPlPgDept(HrPayrollPlPgDept hrPayrollPlPgDept) {
        this.hrPayrollPlPgDept = hrPayrollPlPgDept;
    }
    
    public void departmentValuechange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrDepartments = (HrDepartments) event.getNewValue();
            hrOvertimeRequests.setDeptId(hrDepartments);
//            fmsCostCenter = fmsCostCenterBeanLocal.searchbydepidOfcostcent(hrDepartments);
//            if (fmsCostCenter == null) {
//                system = null;
//                costCenter = null;
//            } else {
//                setCostCenter(fmsCostCenter.getSystemName());
//                int sysId = fmsCostCenter.getSystemId().getSystemId();
//                fmsLuSystem = fmsLuSystemBeanLocal.searchbysystemidofsyscode(sysId);
//                setSystem(fmsLuSystem.getSystemCode());
//            }
            
//            hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrDepartments);
//            if (hrPayrollPlPgDept == null) {
//                payLocation = null;
//                payGroup = null;
//            } else {
//                setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
//                setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
//            }
            hrPayrollPlPgDept = hrPayrollPgPlDeptBeanLocal.findBydepOfPlPg(hrEmployees.getDeptId().getDepId());
            if (hrPayrollPlPgDept != null) {
                setPayLocation(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayLocation().toString());
                setPayGroup(hrPayrollPlPgDept.getPayLocationPayGroupId().getPayGroup().toString());
            } else {
                hrPayrollPlPgDept = new HrPayrollPlPgDept();
            }
            fmsCostcSystemJunction = fmsCostCSystemJunctionBeanLocal.findbyDeptId(hrDepartments.getDepId());
            if (fmsCostcSystemJunction != null) {
                if (fmsCostcSystemJunction.getFmsCostCenter() != null) {
                    setCostCenter(fmsCostcSystemJunction.getFmsCostCenter().getSystemName());
                } else {
                    setCostCenter("");
                }
                if (fmsCostcSystemJunction.getFmsLuSystem() != null) {
                    setSystem(fmsCostcSystemJunction.getFmsLuSystem().getSystemCode());
                } else {
                    setSystem("");
                }
            } else {
                fmsCostcSystemJunction = new FmsCostcSystemJunction();
            }
        }
    }
    
    public void VCLBudgetYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsLuBudgetYear = (FmsLuBudgetYear) event.getNewValue();
            hrOvertimeRequests.setYear(fmsLuBudgetYear);
            fmsLuBudgetYear.setLuBudgetYearId(fmsLuBudgetYear.getLuBudgetYearId());
        }
    }
    
    @Inject
            FmsOperatingBudget1 FmsOperatingBudget1;
    @Inject
            FmsOperatingBudgetDetail FmsOperatingBudgetDetail;
    
    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        if (FmsOperatingBudgetDetail == null) {
            FmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return FmsOperatingBudgetDetail;
    }
    
    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail FmsOperatingBudgetDetail) {
        this.FmsOperatingBudgetDetail = FmsOperatingBudgetDetail;
    }
    
    public FmsOperatingBudget1 getFmsOperatingBudget1() {
        if (FmsOperatingBudget1 == null) {
            FmsOperatingBudget1 = new FmsOperatingBudget1();
        }
        return FmsOperatingBudget1;
    }
    
    public void setFmsOperatingBudget1(FmsOperatingBudget1 FmsOperatingBudget1) {
        this.FmsOperatingBudget1 = FmsOperatingBudget1;
    }
    @EJB
            FmsOperatingBudget1Facade FmsOperatingBudget1Facade;
    List<FmsOperatingBudgetDetail> operatingBudgetList;
    
    public List<FmsOperatingBudgetDetail> getOperatingBudgetList() {
        return operatingBudgetList;
    }
    
    public void setOperatingBudgetList(List<FmsOperatingBudgetDetail> operatingBudgetList) {
        this.operatingBudgetList = operatingBudgetList;
    }
    
    BigDecimal valu;
    
    public BigDecimal getValu() {
        return valu;
    }
    
    public void setValu(BigDecimal valu) {
        this.valu = valu;
    }
    @EJB
            FmsOperatingBudgetDetailFacade fmsOperatingBudgetDetailFacade;
    
    public void VCLGeneral(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
            fmsLuBudgetYear.setLuBudgetYearId(fmsLuBudgetYear.getLuBudgetYearId());
            fmsCostcSystemJunction.setDepId(hrOvertimeRequests.getDeptId());
            FmsOperatingBudgetDetail.setGeneralLedger(fmsGeneralLedger);
            FmsOperatingBudgetDetail = fmsOperatingBudgetDetailFacade.fetchUsingDepartmentID(fmsLuBudgetYear, hrDepartments, fmsGeneralLedger);
            
        }
    }
    
    public void RateValuechange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrOvertimeRate = (HrOvertimeRates) event.getNewValue();
            hrOvertimeRequestDetails.setOtRateId(hrOvertimeRate);
        }
    }
    
    public void populateRegistration(SelectEvent events) {
        hrOvertimeRequests = null;
        hrOvertimeRequests = (HrOvertimeRequests) events.getObject();
        hrDepartments = hrOvertimeRequests.getDeptId();
        fmsLuBudgetYear = hrOvertimeRequests.getYear();
        for (HrOvertimeRequestDetails eee : hrOvertimeRequests.getHrOvertimeRequestDetailsList()) {
            fieldCheck.add(eee.getEmpId().getEmpId());
            fieldCheck.add(eee.getOtRateId().getOtTypes());
        }
        calculateTotal();
        recreateDataModel();
        recreateDataModelwf();
        fmsCostCenter = fmsCostCenterBeanLocal.searchbydepidOfcostcent(hrDepartments);
        if (fmsCostCenter == null) {
            system = null;
            costCenter = null;
        } else {
            setCostCenter(fmsCostCenter.getSystemName());
            int sysId = fmsCostCenter.getSystemId().getSystemId();
            fmsLuSystem = fmsLuSystemBeanLocal.searchbysystemidofsyscode(sysId);
            setSystem(fmsLuSystem.getSystemCode());
        }
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "update";
    }
    
    public void recreateDataModelwf() {
        workFlowDataModel = null;
        workFlowDataModel = new ListDataModel(new ArrayList(hrOvertimeRequests.getWfHrProcessedList()));
        for (int i = 0; i < hrOvertimeRequests.getWfHrProcessedList().size(); i++) {
            if (hrOvertimeRequests.getWfHrProcessedList().get(i).getDecision() == 0) {
                hrOvertimeRequests.getWfHrProcessedList().get(i).setAction("Prepared");
            } else if (hrOvertimeRequests.getWfHrProcessedList().get(i).getDecision() == 3) {
                hrOvertimeRequests.getWfHrProcessedList().get(i).setAction("Approved");
            } else if (hrOvertimeRequests.getWfHrProcessedList().get(i).getDecision() == 2 || hrOvertimeRequests.getWfHrProcessedList().get(i).getDecision() == 4) {
                hrOvertimeRequests.getWfHrProcessedList().get(i).setAction("Rejected");
            }
            
        }
    }
    
    public ArrayList<HrAttendances> findByAbsentName(ValueChangeEvent event) {
        hrOvertimeRequests.setMonth(Integer.parseInt(event.getNewValue().toString()));
        hrOvertimeRequestsDataModel = new ListDataModel(new ArrayList(OvertimeReuestBeanLocal.findBymonth(hrOvertimeRequests)));
        return null;
        
    }
    
    public void clearOvertimeRequestPage() {
        hrOvertimeRequests = null;
        hrEmployees = null;
        hrOvertimeRequestDetails = null;
        hrOvertimeRequestDetailsDatamodel = null;
        saveOrUpdateButton = "Save";
        update = 0;
        clearvalues();
    }
    
    @Inject
            HrPayrollEarningDeductions hrPayrollEarningDeductions;
    @Inject
            HrPayrollMaintainEds hrPayrollMaintainEds;
    @EJB
            HrPayrollMaintainEdsBeanLocal hrPayrollMaintainEdsFacade;
    @EJB
            HrPayrollEarningDeductionsBeanLocal hrPayrollEarningDeductionsLocal;
    @EJB
            HrOtPayrollDetailFacade hrOtPayrollDetailFacade;
    private BigDecimal code;
    
    public BigDecimal getCode() {
        return code;
    }
    
    public void setCode(BigDecimal code) {
        this.code = code;
    }
    List<HrPayrollMaintainEds> HrPayrollMaintainEdsList;
    
    public void transferToPayroll() {
        try {
            for (int i = 0; i < hrOvertimeRequests.getHrOvertimeRequestDetailsList().size(); i++) {
                hrPayrollEarningDeductions.setCode(code);
                hrPayrollMaintainEds.setEarningDeductionCode(hrPayrollEarningDeductions);
                hrPayrollMaintainEds.setStartDate(hrPayrollMaintainEds.getStartDate());
                hrPayrollMaintainEds.setEndDate(hrPayrollMaintainEds.getEndDate());
                hrPayrollMaintainEds.setEmpId(hrOvertimeRequests.getHrOvertimeRequestDetailsList().get(i).getEmpId());
                hrPayrollMaintainEds.setTotal(hrOvertimeRequests.getHrOvertimeRequestDetailsList().get(i).getOtAmount());
                hrOtPayrollDetail.setOtId(hrOvertimeRequests.getHrOvertimeRequestDetailsList().get(i));
                hrPayrollMaintainEdsFacade.edit(hrPayrollMaintainEds);
                HrPayrollMaintainEdsList = hrPayrollMaintainEdsFacade.findAll();
                for (int j = 0; j < HrPayrollMaintainEdsList.size(); j++) {
                    hrOtPayrollDetail.setPayrollMaintianId(HrPayrollMaintainEdsList.get(j));
                }
                OT_Payroll_DetailBeanLocal.saveorupdate(hrOtPayrollDetail);
                hrOtPayrollDetail = new HrOtPayrollDetail();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveOvertimeRequest() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveOvertimeRequest", dataset)) {
                int i = 0;
                if ((FmsOperatingBudgetDetail.getRemainingBalance().compareTo(BigDecimal.valueOf(Overalltotal)) < 0)) {
                    JsfUtil.addFatalMessage("please check remaining balance");
                    
                } else {
                    if (update == 0) {
                        if ((!(getHrOvertimeRequestDetailsDatamodel().getRowCount() > 0))) {
                            JsfUtil.addFatalMessage("can not save empty data");
                        } else {
                            try {
//                        hrOvertimeRequests.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                                hrOvertimeRequests.setStatus(Constants.PREPARE_VALUE);
                                OvertimeReuestBeanLocal.SaveOrUpdate(hrOvertimeRequests);
                                wfHrProcessed.setOvertimeId(hrOvertimeRequests);
                                wfHrProcessed.setProcessedOn(hrOvertimeRequests.getPreparedOn());
                                wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                wfHrProcessed.setDecision(hrOvertimeRequests.getStatus());
                                wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                JsfUtil.addSuccessMessage("Saved Successfully.");
                                clearOvertimeRequestPage();
                                clearPopup();
                                clearvalues();
                            } catch (Exception e) {
                                JsfUtil.addFatalMessage("Something occured,unable to saved");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            if ((!(getHrOvertimeRequestDetailsDatamodel().getRowCount() > 0))) {
                                JsfUtil.addFatalMessage("can not update empty data");
                            } else {
                                if (hrOvertimeRequests.getStatus().equals(0)
                                        || hrOvertimeRequests.getStatus().equals(2)
                                        || hrOvertimeRequests.getStatus().equals(3)) {
                                    OvertimeReuestBeanLocal.SaveOrUpdate(hrOvertimeRequests);
                                    wfHrProcessed.setOvertimeId(hrOvertimeRequests);
                                    wfHrProcessed.setProcessedOn(hrOvertimeRequests.getPreparedOn());
                                    wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                    wfHrProcessed.setDecision(hrOvertimeRequests.getStatus());
                                    wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                    JsfUtil.addSuccessMessage("Update Successful.");
                                    update = 1;
                                    saveOrUpdateButton = "Update";
                                    clearOvertimeRequestPage();
                                    clearPopup();
                                    clearvalues();
                                } else if (hrOvertimeRequests.getStatus().equals(1)) {
                                    JsfUtil.addFatalMessage("can not update approved data");
                                    
                                }
                            }
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something occured,unable to update");
                            e.printStackTrace();
                        }
                        
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
//..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void saveOvertimeApprove() {
        
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveOvertimeApprove", dataset)) {
                String Shday[] = hrOvertimeRequests.getApprovedDate().split("/");
                int Ihday = Integer.parseInt(Shday[0]);
                String Shmonth[] = hrOvertimeRequests.getApprovedDate().split("/");
                int Ihmonth = Integer.parseInt(Shmonth[1]);
                String Shyear[] = hrOvertimeRequests.getApprovedDate().split("/");
                int Ihyear = Integer.parseInt(Shyear[2]);
                String Scday[] = hrPayrollMaintainEds.getStartDate().split("/");
                int Icday = Integer.parseInt(Scday[0]);
                String Scmonth[] = hrPayrollMaintainEds.getStartDate().split("/");
                int Icmonth = Integer.parseInt(Scmonth[1]);
                String Scyear[] = hrPayrollMaintainEds.getStartDate().split("/");
                int Icyear = Integer.parseInt(Scyear[2]);
                String Scday1[] = hrPayrollMaintainEds.getEndDate().split("/");
                int Icday1 = Integer.parseInt(Scday[0]);
                String Scmonth1[] = hrPayrollMaintainEds.getEndDate().split("/");
                int Icmonth1 = Integer.parseInt(Scmonth[1]);
                String Scyear1[] = hrPayrollMaintainEds.getEndDate().split("/");
                int Icyear1 = Integer.parseInt(Scyear[2]);
                int expday = (Icday - Ihday) + ((Icmonth - Ihmonth) * 30) + ((Icyear - Ihyear) * 365);
                int expday1 = (Icday1 - Ihday) + ((Icmonth1 - Ihmonth) * 30) + ((Icyear1 - Ihyear) * 365);
                int expday2 = (Icday1 - Icday) + ((Icmonth1 - Icmonth) * 30) + ((Icyear1 - Icyear) * 365);
                if (expday < 0) {
                    JsfUtil.addFatalMessage("approved date can not be greater than payment start date");
                } else if (expday1 < 0) {
                    JsfUtil.addFatalMessage("approved date can not be greater than payment End date");
                } else if (expday2 < 0) {
                    JsfUtil.addFatalMessage("start date can not be greater than payment End date");
                } else {
                    if (hrOvertimeRequestDetails == null) {
                        if (hrOvertimeRequests.getStatus().equals(1)) {
                            JsfUtil.addFatalMessage("Can't Approve empty data");
                        } else if (hrOvertimeRequests.getStatus().equals(2)) {
                            JsfUtil.addFatalMessage("Can't Reject empty data");
                        }
                    } else if ((!(getHrOvertimeRequestDetailsDatamodel().getRowCount() > 0))) {
                        if (hrOvertimeRequests.getStatus().equals(1)) {
                            JsfUtil.addFatalMessage("Can't Approve empty dataTable");
                        } else if (hrOvertimeRequests.getStatus().equals(2)) {
                            JsfUtil.addFatalMessage("Can't Reject empty dataTable");
                        }
                        
                    } else {
                        try {
                            if (hrPayrollEarningDeductionsLocal.findCriteriaInfo("Overtime") == null) {
//                                addMessage("First Define the Overtime");
                            } else {
                                if (hrOvertimeRequests.getStatus().equals(1) && workFlow.isApproveStatus()) {
                                    workFlow.setUserStatus(Constants.APPROVE_VALUE);
                                    hrOvertimeRequests.setStatus(workFlow.getUserStatus());
                                    code = hrPayrollEarningDeductionsLocal.findCriteriaInfo("Overtime").getCode();
                                    transferToPayroll();
                                    wfHrProcessed.setOvertimeId(hrOvertimeRequests);
                                    wfHrProcessed.setProcessedOn(hrOvertimeRequests.getPreparedOn());
                                    wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                    hrOvertimeRequests.setPreparedBy(hrEmployees);
                                    wfHrProcessed.setDecision(hrOvertimeRequests.getStatus());
                                    OvertimeReuestBeanLocal.SaveOrUpdate(hrOvertimeRequests);
                                    wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                    JsfUtil.addSuccessMessage("OT Payment is transfered to the payrolll");
                                    clearOvertimeRequestPage();
                                    clearPopup();
                                    clearvalues();
                                } else if (hrOvertimeRequests.getStatus().equals(1) && workFlow.isCheckStatus()) {
                                    workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                                    hrOvertimeRequests.setStatus(workFlow.getUserStatus());
                                    wfHrProcessed.setOvertimeId(hrOvertimeRequests);
                                    wfHrProcessed.setProcessedOn(hrOvertimeRequests.getPreparedOn());
                                    wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                    wfHrProcessed.setDecision(hrOvertimeRequests.getStatus());
                                    OvertimeReuestBeanLocal.SaveOrUpdate(hrOvertimeRequests);
                                    wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                    JsfUtil.addSuccessMessage("Approved Successfully");
                                    clearOvertimeRequestPage();
                                    clearPopup();
                                    clearvalues();
                                } else if (hrOvertimeRequests.getStatus().equals(2) && workFlow.isApproveStatus()) {
                                    workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                                    hrOvertimeRequests.setStatus(workFlow.getUserStatus());
                                    wfHrProcessed.setOvertimeId(hrOvertimeRequests);
                                    wfHrProcessed.setProcessedOn(hrOvertimeRequests.getPreparedOn());
                                    wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                    wfHrProcessed.setDecision(hrOvertimeRequests.getStatus());
                                    OvertimeReuestBeanLocal.SaveOrUpdate(hrOvertimeRequests);
                                    wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                    JsfUtil.addSuccessMessage("Rejected Successfully");
                                    clearOvertimeRequestPage();
                                    clearPopup();
                                    clearvalues();
                                } else if (hrOvertimeRequests.getStatus().equals(2) && workFlow.isCheckStatus()) {
                                    workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                                    hrOvertimeRequests.setStatus(workFlow.getUserStatus());
                                    wfHrProcessed.setOvertimeId(hrOvertimeRequests);
                                    wfHrProcessed.setProcessedOn(hrOvertimeRequests.getPreparedOn());
                                    wfHrProcessed.setProcessedBy(SessionBean.getUserId());
                                    wfHrProcessed.setDecision(hrOvertimeRequests.getStatus());
                                    OvertimeReuestBeanLocal.SaveOrUpdate(hrOvertimeRequests);
                                    wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
                                    JsfUtil.addSuccessMessage("Rejected Successfully");
                                    clearOvertimeRequestPage();
                                    clearPopup();
                                    clearvalues();
                                }
                                clearPopup();
                                clearOvertimeRequestPage();
                                clearvalues();
                            }
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Unable to Approve Or Reject data");
                            ex.printStackTrace();
                        }
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
    
    public void OvertimeapproveDisplayChanged(SelectEvent event) {
        hrOvertimeRequests = (HrOvertimeRequests) event.getObject();
        hrOvertimeRequests.setId(hrOvertimeRequests.getId());
        calculateTotal();
        recreateDataModel();
        recreateDataModelwf();
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        createOrSearchBundle = "Search";
        update = 1;
        saveOrUpdateButton = "Save";
    }
    int selectedstatus = 1;
    Status status1 = new Status();
    
    public int getSelectedstatus() {
        return selectedstatus;
    }
    
    public void setSelectedstatus(int selectedstatus) {
        this.selectedstatus = selectedstatus;
    }
    
    public void filiterByStatus_VclOvertimePaymentRequest(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            status = Integer.valueOf(event.getNewValue().toString());
            populateTable();
        }
    }
    
    public void filiterByStatus_VclOvertimePaymentRequest1(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedstatus = Integer.valueOf(event.getNewValue().toString());
            if (selectedstatus == Constants.PREPARE_VALUE) {
                status1.setStatus1(Constants.PREPARE_VALUE);
                populateTable(status1);
            } else if (selectedstatus == Constants.APPROVE_VALUE) {
                status1.setStatus1(Constants.APPROVE_VALUE);
                status1.setStatus2(Constants.CHECK_APPROVE_VALUE);
                populateTableApprove(status1);
            } else if (selectedstatus == Constants.APPROVE_REJECT_VALUE) {
                status1.setStatus2(Constants.CHECK_REJECT_VALUE);
                status1.setStatus3(Constants.APPROVE_REJECT_VALUE);
                populateTableReject(status1);
            }
            
        }
    }
    
    private void populateTable(Status status1) {
        try {
            workFlow = new WorkFlow();
            List<HrOvertimeRequests> readFiltereddata = OvertimeReuestBeanLocal.loadFiltereddata(status1, SessionBean.getUserId());
            hrOvertimeRequestsDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void populateTableReject(Status status1) {
        try {
            workFlow = new WorkFlow();
            List<HrOvertimeRequests> readFiltereddata = OvertimeReuestBeanLocal.populateTableReject(status1, SessionBean.getUserId());
            hrOvertimeRequestsDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void populateTableApprove(Status status1) {
        try {
            workFlow = new WorkFlow();
            List<HrOvertimeRequests> readFiltereddata = OvertimeReuestBeanLocal.populateTableApprove(status1, SessionBean.getUserId());
            hrOvertimeRequestsDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public SelectItem[] getPopulateFilterByStatus() {
        SelectItem[] items = new SelectItem[4];
        items[0] = new SelectItem("null", "--- Select ---");
        items[1] = new SelectItem(Constants.PREPARE_VALUE, "Load Request List");
        items[2] = new SelectItem(Constants.APPROVE_REJECT_VALUE, "Load Rejected List");
        items[3] = new SelectItem(Constants.APPROVE_VALUE, "Load Approved List");
        return items;
    }
    
    private void populateTable() {
        try {
            List<HrOvertimeRequests> readFiltereddata = OvertimeReuestBeanLocal.loadFiltereddata(status);
            hrOvertimeRequestsDataModel = new ListDataModel(readFiltereddata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    Integer OTFA = 0, OTFB = 0, OTFC = 0, OTFD = 0, OTFTotal = 0;
    
    public Integer getOTFA() {
        return OTFA;
    }
    
    public void setOTFA(Integer OTFA) {
        this.OTFA = OTFA;
    }
    
    public Integer getOTFB() {
        return OTFB;
    }
    
    public void setOTFB(Integer OTFB) {
        this.OTFB = OTFB;
    }
    
    public Integer getOTFC() {
        return OTFC;
    }
    
    public void setOTFC(Integer OTFC) {
        this.OTFC = OTFC;
    }
    
    public Integer getOTFD() {
        return OTFD;
    }
    
    public void setOTFD(Integer OTFD) {
        this.OTFD = OTFD;
    }
    
    public Integer getOTFTotal() {
        return OTFTotal;
    }
    
    public void setOTFTotal(Integer OTFTotal) {
        this.OTFTotal = OTFTotal;
    }
    
    double Overalltotal = 0;
    double alltotal = 0;
    
    public double getOveralltotal() {
        return Overalltotal;
    }
    
    public void setOveralltotal(double Overalltotal) {
        this.Overalltotal = Overalltotal;
    }
    
    public double getAlltotal() {
        return alltotal;
    }
    
    public void setAlltotal(double alltotal) {
        this.alltotal = alltotal;
    }
    NumAndDateFormatter formatter = new NumAndDateFormatter();
    
    public void sumCal(String empId) {
        double sumtotal = 0;
        Integer workinghour = 0;
        Integer workinghourA = 0;
        Integer workinghourB = 0;
        Integer workinghourC = 0;
        Integer workinghourD = 0;
        DecimalFormat dformat = new DecimalFormat("##.00 ");
        dformat.format(sumtotal);
        if (empId != null) {
            for (int i = 0; i < hrOvertimeRequests.getHrOvertimeRequestDetailsList().size(); i++) {
                HrOvertimeRequestDetails get = hrOvertimeRequests.getHrOvertimeRequestDetailsList().get(i);
                if (empId.equalsIgnoreCase(get.getEmpId().getEmpId())) {
                    if (get.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor A")) {
                        workinghourA = workinghourA + get.getWorkingHour();
                    } else if (get.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor B")) {
                        workinghourB = workinghourB + get.getWorkingHour();
                    } else if (get.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor C")) {
                        workinghourC = workinghourC + get.getWorkingHour();
                    } else if (get.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor D")) {
                        workinghourD = workinghourD + get.getWorkingHour();
                    }
                    workinghour = workinghour + get.getWorkingHour();
                    sumtotal = sumtotal + get.getOtAmount();
                }
            }
            
            OTFD = workinghourD;
            OTFA = workinghourA;
            OTFB = workinghourB;
            OTFC = workinghourC;
            OTFTotal = workinghour;
            Total = formatter.decimalFormat(sumtotal);
        }
    }
    double otamountA = 0;
    double otamountB = 0;
    double otamountC = 0;
    double otamountD = 0;
    
    public double getOtamountA() {
        return otamountA;
    }
    
    public void setOtamountA(double otamountA) {
        this.otamountA = otamountA;
    }
    
    public double getOtamountB() {
        return otamountB;
    }
    
    public void setOtamountB(double otamountB) {
        this.otamountB = otamountB;
    }
    
    public double getOtamountC() {
        return otamountC;
    }
    
    public void setOtamountC(double otamountC) {
        this.otamountC = otamountC;
    }
    
    public double getOtamountD() {
        return otamountD;
    }
    
    public void setOtamountD(double otamountD) {
        this.otamountD = otamountD;
    }
    
    public void calculateTotal() {
        for (HrOvertimeRequestDetails hrInsurancePaymentDetail : hrOvertimeRequests.getHrOvertimeRequestDetailsList()) {
            Overalltotal = formatter.decimalFormat(Overalltotal + hrInsurancePaymentDetail.getOtAmount());
            if (hrInsurancePaymentDetail.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor A")) {
                otamountA = formatter.decimalFormat(otamountA + hrInsurancePaymentDetail.getOtAmount());
                
            } else if (hrInsurancePaymentDetail.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor B")) {
                otamountB = formatter.decimalFormat(otamountB + hrInsurancePaymentDetail.getOtAmount());
                
            } else if (hrInsurancePaymentDetail.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor C")) {
                otamountC = formatter.decimalFormat(otamountC + hrInsurancePaymentDetail.getOtAmount());
                
            } else if (hrInsurancePaymentDetail.getOtRateId().getOtTypes().equalsIgnoreCase("OT Factor D")) {
                otamountD = formatter.decimalFormat(otamountD + hrInsurancePaymentDetail.getOtAmount());
                
            }
        }
    }
    
    public void clearvalues() {
        otamountD = 0;
        otamountA = 0;
        otamountC = 0;
        otamountB = 0;
        Overalltotal = 0;
        Total = null;
        costCenter = null;
        system = null;
        fmsGeneralLedgerList = null;
        operatingBudgetList = null;
        fmsGeneralLedger = null;
        fmsCostCenter = null;
        fmsLuBudgetYear = null;
        hrPayrollMaintainEds = null;
        
    }
    @EJB
            FmsGeneralLedgerFacade fmsGeneralLedgerFacade;
    @Inject
            FmsGeneralLedger fmsGeneralLedger;
    @Inject
            FmsCostCenter fmsCostCenter;
    @EJB
            FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @Inject
            FmsLuSystem fmsLuSystem;
    @EJB
            FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @Inject
            FmsLuBudgetYear fmsLuBudgetYear;
    @EJB
            FmsLuBudgetYearFacade fmsLuBudgetYearFacade;
    String system;
    String costCenter;
    List<FmsLuBudgetYear> fmsLuBudgetYearList;
    
    public List<FmsLuBudgetYear> getFmsLuBudgetYearList() {
        return fmsLuBudgetYearList;
    }
    
    public void setFmsLuBudgetYearList(List<FmsLuBudgetYear> fmsLuBudgetYearList) {
        this.fmsLuBudgetYearList = fmsLuBudgetYearList;
    }
    
    public FmsLuBudgetYear getFmsLuBudgetYear() {
        if (fmsLuBudgetYear == null) {
            fmsLuBudgetYear = new FmsLuBudgetYear();
        }
        return fmsLuBudgetYear;
    }
    
    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
    }
    
    public String getSystem() {
        return system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }
    
    public String getCostCenter() {
        return costCenter;
    }
    
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
    
    List<FmsGeneralLedger> fmsGeneralLedgerList;
    
    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }
    
    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }
    
    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }
    
    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }
    
    public List<FmsGeneralLedger> getFmsGeneralLedgerList() {
        return fmsGeneralLedgerList;
    }
    
    public void setFmsGeneralLedgerList(List<FmsGeneralLedger> fmsGeneralLedgerList) {
        this.fmsGeneralLedgerList = fmsGeneralLedgerList;
    }
    
    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }
    
    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }
//</editor-fold>
}
