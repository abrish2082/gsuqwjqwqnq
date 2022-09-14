/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.perdiem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.perDiem.FmsLuCountryBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.fmsFieldAllowansForeignBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.fcms.controller.Constants;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceForeign;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuCountry;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "fmsFieldAllowanceForeignManagedBean")
@ViewScoped
public class FmsFieldAllowanceForeignManagedBean implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
    //Inject entities

    @Inject
    SessionBean SessionBean;
    @Inject
    private FmsLuCountry fmsLuCountry;
    @Inject
    private FmsFieldAllowanceForeign allowanceForeign;
    @Inject
    private WfFcmsProcessed wfFcmsProcessed;
    @Inject
    private HrEmployees employeeEnty;
    @Inject
    private HrEmployees empEnty;
    @Inject
    private HrLuJobLevels hrLuJobLevels;
    @Inject
    FmsLuAdditionalAmount additionalAmount;
    @Inject
    FmsGoodWillingPayment fmsGoodWillingPayment;
    @Inject
    WorkFlow workFlow;
    //Inject @EJB
    @EJB
    private fmsFieldAllowansForeignBeanLocal fieldAllowansForeignBeanLocal;
    @EJB
    private HrEmployeeBeanLocal hrEmployeeBean;
    @EJB
    private WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    private FmsLuCountryBeanLocal fmsLuCountryBeanLocal;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    List<FmsLuCountry> luCountryLists;
    List<FmsFieldAllowanceForeign> allowanceForeignList;
    List<FmsFieldAllowanceForeign> listemployeeName;
    List<FmsFieldAllowanceForeign> EmpNameList = new ArrayList<>();
    private List<FmsFieldAllowanceForeign> fmsFaFList;
    DataModel<FmsFieldAllowanceForeign> allowanceForeignSearchDataModel;
    private DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    int updateStatus = 0;
    private Date prepareDate;
    private String saveorUpdateBundle = Constants.getComponentBundle("Create");
    private NumberConverter numberConverter = new NumberConverter();
    String firstname;
    String country = "Ethiopia";
    String levelId;
    Integer dateStatus;
    String allowanceForeignFromDate;
    Double lodgeExpence = 0.0;
    Double lunchDinnerExpense = 0.0;
    Double travelf = 0.0;
    Double reservePayment = 0.0;
    Double reserve = 0.0;
    Double foodLodge = 0.0;
    String level;
    Double miscellaneousExpense = 0.0;
    Double totalExpense = 0.0;
    Double goodWillingPayment = 0.0;
    double mealExpense;
    double hotelExpense;
    double travel;
    Integer dayDifference = 0;
    Integer day = 0;
    Integer monthDifference = 0;
    Integer yearDifference = 0;
    String allowanceForeignToDate;
    Integer dateCompStatus;
    FmsFieldAllowanceForeign selectRequest;
    boolean txtRDatevisibility = false;
    private boolean renderBtnPrint = false;
    private boolean disableBtnCreate;
    private boolean renderPnlCreatePerdiemForeign = false;
    private boolean renderPnlManPage = true;
    private boolean isRenderDecision = false;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkFlow = false;
    private boolean isRenderedBtnNew = false;
    String selectedDecision = "";
    String userName;
    Integer id;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    String requestNumber = "";
//</editor-fold>

    public FmsFieldAllowanceForeignManagedBean() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    @PostConstruct
    public void init() {
        generateReqNo();
        userName = SessionBean.getUserName();
        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(workFlow.getUserAccount()));
        if (workFlow.isPrepareStatus()) {//Preparer
            isRenderDecision = false;
            isRenderedBtnNew = true;
        } else if (workFlow.isCheckStatus()) {//Checker
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            renderPnlManPage = true;
            renderPnlCreatePerdiemForeign = false;
            requestNumber = "";
            fmsFaFList = fieldAllowansForeignBeanLocal.findFaByWfStatus(securityBean.Constants.PREPARE_VALUE);//to be chacked list
        } else if (workFlow.isApproveStatus()) {//Approver
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            renderPnlManPage = true;
            renderPnlCreatePerdiemForeign = false;
            requestNumber = "";
            fmsFaFList = fieldAllowansForeignBeanLocal.findFaByWfStatus(securityBean.Constants.CHECK_APPROVE_VALUE);//to be approved List
        }
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public HrLuJobLevels getHrLuJobLevels() {
        if (hrLuJobLevels == null) {
            hrLuJobLevels = new HrLuJobLevels();
        }
        return hrLuJobLevels;
    }

    public void setHrLuJobLevels(HrLuJobLevels hrLuJobLevels) {
        this.hrLuJobLevels = hrLuJobLevels;
    }

    public String getReqNo() {
        return requestNumber;
    }

    public void setReqNo(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public FmsFieldAllowanceForeign getSelectRequest() {
        return selectRequest;
    }

    public void setSelectRequest(FmsFieldAllowanceForeign selectRequest) {
        this.selectRequest = selectRequest;
    }

    public HrEmployees getEmpEnty() {

        if (empEnty == null) {
            empEnty = new HrEmployees();
        }
        return empEnty;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public boolean isTxtRDatevisibility() {
        return txtRDatevisibility;
    }

    public void setTxtRDatevisibility(boolean txtRDatevisibility) {
        this.txtRDatevisibility = txtRDatevisibility;
    }

    public boolean isRenderBtnPrint() {
        return renderBtnPrint;
    }

    public void setRenderBtnPrint(boolean renderBtnPrint) {
        this.renderBtnPrint = renderBtnPrint;
    }

    public void setEmpEnty(HrEmployees empEnty) {
        this.empEnty = empEnty;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public HrEmployees getEmployeeEnty() {
        if (employeeEnty == null) {
            employeeEnty = new HrEmployees();
        }
        return employeeEnty;
    }

    public void setEmployeeEnty(HrEmployees employeeEnty) {
        this.employeeEnty = employeeEnty;
    }

    public FmsFieldAllowanceForeign getAllowanceForeign() {
        if (allowanceForeign == null) {
            allowanceForeign = new FmsFieldAllowanceForeign();
        }
        return allowanceForeign;
    }

    public void setAllowanceForeign(FmsFieldAllowanceForeign allowanceForeign) {
        this.allowanceForeign = allowanceForeign;
    }

    public Date getPrepareDate() {
        return prepareDate;
    }

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public void setPrepareDate(Date prepareDate) {
        this.prepareDate = prepareDate;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public FmsLuCountry getFmsLuCountry() {
        if (fmsLuCountry == null) {
            fmsLuCountry = new FmsLuCountry();
        }

        return fmsLuCountry;
    }

    public void setFmsLuCountry(FmsLuCountry fmsLuCountry) {
        this.fmsLuCountry = fmsLuCountry;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreatePerdiemForeign() {
        return renderPnlCreatePerdiemForeign;
    }

    public void setRenderPnlCreatePerdiemForeign(boolean renderPnlCreatePerdiemForeign) {
        this.renderPnlCreatePerdiemForeign = renderPnlCreatePerdiemForeign;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isIsRenderDecision() {
        return isRenderDecision;
    }

    public void setIsRenderDecision(boolean isRenderDecision) {
        this.isRenderDecision = isRenderDecision;
    }

    public boolean isIsRenderedIconNitification() {
        return isRenderedIconNitification;
    }

    public void setIsRenderedIconNitification(boolean isRenderedIconNitification) {
        this.isRenderedIconNitification = isRenderedIconNitification;
    }

    public boolean isIsRenderedIconWorkFlow() {
        return isRenderedIconWorkFlow;
    }

    public void setIsRenderedIconWorkFlow(boolean isRenderedIconWorkFlow) {
        this.isRenderedIconWorkFlow = isRenderedIconWorkFlow;
    }

    public boolean isIsRenderedBtnNew() {
        return isRenderedBtnNew;
    }

    public void setIsRenderedBtnNew(boolean isRenderedBtnNew) {
        this.isRenderedBtnNew = isRenderedBtnNew;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSelectedDecision() {
        return selectedDecision;
    }

    public void setSelectedDecision(String selectedDecision) {
        this.selectedDecision = selectedDecision;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public List<FmsFieldAllowanceForeign> getFmsFaFList() {
        if (fmsFaFList == null) {
            fmsFaFList = new ArrayList<>();
        }
        return fmsFaFList;
    }

    public void setFmsFaFList(List<FmsFieldAllowanceForeign> fmsFaFList) {
        this.fmsFaFList = fmsFaFList;
    }

    public DataModel<FmsFieldAllowanceForeign> getAllowanceForeignSearchDataModel() {
        if (allowanceForeignSearchDataModel == null) {
            allowanceForeignSearchDataModel = new ListDataModel<>();
        }
        return allowanceForeignSearchDataModel;
    }

    public void setAllowanceForeignSearchDataModel(DataModel<FmsFieldAllowanceForeign> allowanceForeignSearchDataModel) {
        this.allowanceForeignSearchDataModel = allowanceForeignSearchDataModel;
    }

    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public DataModel<WfFcmsProcessed> getWfFcmsProcessedDataModel() {
        if (wfFcmsProcessedDataModel == null) {
            wfFcmsProcessedDataModel = new ListDataModel<>();
        }
        return wfFcmsProcessedDataModel;
    }

    public void setWfFcmsProcessedDataModel(DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel) {
        this.wfFcmsProcessedDataModel = wfFcmsProcessedDataModel;
    }
//</editor-fold>

    //generate request number
    public void generateReqNo() {
        try {
            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int noOfRows = fieldAllowansForeignBeanLocal.countRow() + 1;
            requestNumber = "Field-Allowance-No-" + noOfRows + "/" + f.format(now);
            allowanceForeign.setRequestNo(requestNumber);
        } catch (Exception e) {
        }
    }

    //search perdiem fireign
    public void searchPerdiemForeign() {
        clearPage();
        disableBtnCreate = false;
        renderPnlCreatePerdiemForeign = false;
        renderPnlManPage = true;
    }

    //select event to get emoloyee level id
    public void getEmpId(SelectEvent event) {
        empEnty.setEmpId(event.getObject().toString());
        empEnty = hrEmployeeBean.getByEmpId(empEnty);
        levelId = empEnty.getGradeId().getLevelId().getDescription();
        additionalAmount = fieldAllowansForeignBeanLocal.searchAdd(levelId);
        level = additionalAmount.getLevelId();
    }

    //search employee by id
    public List<HrEmployees> SearchByEmpId(String hrEmployee) {
        ArrayList<HrEmployees> employees = null;
        employeeEnty.setEmpId(hrEmployee);
        employees = hrEmployeeBean.SearchByEmpId(employeeEnty);
        return employees;

    }

    /**
     * @param empName
     * @return
     */
    //searcg employee
    public List<FmsFieldAllowanceForeign> SearchEmployee(String empName) {
        listemployeeName = null;
        listemployeeName = fieldAllowansForeignBeanLocal.SearchEmpName(allowanceForeign);
        listemployeeName.get(0).getId();
        return listemployeeName;
    }

    //select event for employee list
    public void getEmployeeList(SelectEvent event) {
        String selectedEmployeeName = event.getObject().toString();
        allowanceForeign = fieldAllowansForeignBeanLocal.getAllEmployees(allowanceForeign);
        empEnty.setEmpId(allowanceForeign.getEmpId().getEmpId());
        updateStatus = 1;
        saveorUpdateBundle = "Update";

    }

    //value change event for travel
    public void travelWay(ValueChangeEvent event) {
        fmsGoodWillingPayment = fieldAllowansForeignBeanLocal.searchGoodW(fmsGoodWillingPayment);
        Double singleCountrypayment = fmsGoodWillingPayment.getSingleCountryPayment();
        Double MultipleCountrypayment = fmsGoodWillingPayment.getMultipleCountryPayment();

        String travType = event.getNewValue().toString();
        if (travType.equals("Single Country")) {
            allowanceForeign.setGoodwillingPayment(fmsGoodWillingPayment.getSingleCountryPayment());
        } else {
            allowanceForeign.setGoodwillingPayment(fmsGoodWillingPayment.getMultipleCountryPayment());
        }

    }

    //select event for field allowance foreign
    public void FromDate(SelectEvent event) {
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM-DD");
        allowanceForeignFromDate = simpledate.format(allowanceForeign.getFromDate());
        calc();
        if (dateStatus == 0 || dateStatus == 1) {
            JsfUtil.addFatalMessage("Mr/Mrs " + empEnty.getFirstName() + " is Not Returned ");
            txtRDatevisibility = true;
        } else {
        }
    }

    //calculate
    public int calc() {
        allowanceForeign.setEmpId(empEnty);
        FmsFieldAllowanceForeign des = new FmsFieldAllowanceForeign();
        des = fieldAllowansForeignBeanLocal.findEmploye(allowanceForeign);
        if (fieldAllowansForeignBeanLocal.findEmploye(allowanceForeign) != null) {
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");//SimpleDateFormat rdate = new SimpleDateFormat("YYYY-MM-DD");
            String dateto = format.format(des.getToDate());//returned
            String datefrom = format.format(des.getFromDate());//departured
            String to1 = dateto.substring(dateto.lastIndexOf('-') + 1);
            String from2 = datefrom.substring(datefrom.lastIndexOf('-') + 1);
            if (Integer.parseInt(to1) > Integer.parseInt(from2)) {
                dateStatus = 1;
            } else if (Integer.parseInt(to1) < Integer.parseInt(from2)) {
                dateStatus = 2;
            } else {
                dateStatus = 0;
            }
        } else {
            dateStatus = 3;
        }
        return dateStatus;
    }

    //date comparisiton
    public int dateCompure() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        allowanceForeignToDate = sdf.format(allowanceForeign.getToDate());
        allowanceForeignFromDate = sdf.format(allowanceForeign.getFromDate());
        Date fromdate = sdf.parse(allowanceForeignFromDate);
        Date todate = sdf.parse(allowanceForeignToDate);
        if (fromdate.after(todate)) {
            dateCompStatus = 1;
        } else if (fromdate.before(todate)) {
            dateCompStatus = 0;
        } else {
            dateCompStatus = 0;
        }
        return dateCompStatus;

    }

    //select event for date diffrenece
    public void datdiff(SelectEvent event) throws ParseException {
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM-DD");
        allowanceForeignToDate = simpledate.format(allowanceForeign.getToDate());
        dateCompure();
        if (dateCompStatus == 1) {
            txtRDatevisibility = true;
            JsfUtil.addFatalMessage("Invalid Date Selection!");
        } else {
            String day1 = allowanceForeignFromDate.substring(allowanceForeignFromDate.lastIndexOf('-') + 1);
            String day2 = allowanceForeignToDate.substring(allowanceForeignToDate.lastIndexOf('-') + 1);
            if (Integer.parseInt(day2) > Integer.parseInt(day1)) {
                day = Integer.parseInt(day2) + 1 - Integer.parseInt(day1);
            } else {
                day = Integer.parseInt(day1) - Integer.parseInt(day2);
            }
            String month1 = allowanceForeignFromDate.substring(allowanceForeignFromDate.indexOf('-') + 1, allowanceForeignFromDate.lastIndexOf('-'));
            String month2 = allowanceForeignToDate.substring(allowanceForeignToDate.indexOf('-') + 1, allowanceForeignToDate.lastIndexOf('-'));
            if (Integer.parseInt(month1) > Integer.parseInt(month2)) {
                monthDifference = Integer.parseInt(month1) - Integer.parseInt(month2);
            } else {
                monthDifference = Integer.parseInt(month2) - Integer.parseInt(month1);
            }
            String year1 = allowanceForeignFromDate.substring(0, allowanceForeignFromDate.indexOf('-'));
            String year2 = allowanceForeignToDate.substring(0, allowanceForeignToDate.indexOf('-'));
            if (Integer.parseInt(year1) > Integer.parseInt(year2)) {
                yearDifference = Integer.parseInt(year1) - Integer.parseInt(year2);
            } else {
                yearDifference = Integer.parseInt(year2) - Integer.parseInt(year1);
            }
            dayDifference = yearDifference + monthDifference + day;
            allowanceForeign.setNoOfDays(dayDifference);
        }
    }

    //value change event to handle country 
    public void handleCountry(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            FmsLuCountry country = new FmsLuCountry();
            country.setCountryId((BigDecimal) event.getNewValue());
            fmsLuCountry = fmsLuCountryBeanLocal.getById(country);
        }
        fmsGoodWillingPayment = fieldAllowansForeignBeanLocal.searchGoodW(fmsGoodWillingPayment);
        foodLodge = fmsLuCountry.getLodgingBreakfastAmount() + fmsLuCountry.getLunchDinnerAmount();
        reserve = fmsGoodWillingPayment.getReservePayment();
        reservePayment = foodLodge * (reserve / 100);
        allowanceForeign.setReservePayment(reservePayment);
        Double lodge = fmsLuCountry.getLodgingBreakfastAmount();
        Double lunch = fmsLuCountry.getLunchDinnerAmount();
        additionalAmount = fieldAllowansForeignBeanLocal.searchAdd(levelId);
        level = additionalAmount.getLevelId();
        Integer logAdd = additionalAmount.getLodgingAdditional();
        Integer lunAdd = additionalAmount.getLunchAdditional();
        Double lunchAdd = lunch * (lunAdd.doubleValue() / 100);
        if (additionalAmount != null) {
            if ("Middle Managers".equals(level) || "Executives".equals(level)) {
                lunchDinnerExpense = ((lunch * dayDifference) + lunchAdd);
                lodgeExpence = lodge * dayDifference.doubleValue() + logAdd.doubleValue();
            } else if ("Professionals".equals(level)) {
                lunchDinnerExpense = ((lunch * dayDifference) + lunchAdd);
                lodgeExpence = lodge * dayDifference.doubleValue() + logAdd.doubleValue();
            } else {
                lodgeExpence = lodge * dayDifference.doubleValue();
                lunchDinnerExpense = lunch * dayDifference.doubleValue();
            }
        }
        allowanceForeign.setBreakLodgeExpense(lodgeExpence);
        allowanceForeign.setLunchDinnerExpense(lunchDinnerExpense);
    }

//Travel expense ValueChangeEvent
    public void travelF(ValueChangeEvent event) {
        travelf = allowanceForeign.getTravelExpense();
        calculateTotalExpense();
    }

//calculate Total Expense
    public void calculateTotalExpense() {
        try {
            if (updateStatus != 0) {
                travelf = allowanceForeign.getTravelExpense() * allowanceForeign.getNoOfDays();
                lodgeExpence = allowanceForeign.getBreakLodgeExpense() * allowanceForeign.getNoOfDays();
                lunchDinnerExpense = allowanceForeign.getLunchDinnerExpense() * allowanceForeign.getNoOfDays();
                reservePayment = allowanceForeign.getReservePayment() * allowanceForeign.getNoOfDays();
                goodWillingPayment = allowanceForeign.getGoodwillingPayment() * allowanceForeign.getNoOfDays();
                miscellaneousExpense = allowanceForeign.getMiscellaneousExpense();
                totalExpense = miscellaneousExpense + travelf + lodgeExpence + goodWillingPayment + lunchDinnerExpense.intValue() + reservePayment.intValue();
                allowanceForeign.setTotalExpense(totalExpense);
                JsfUtil.addInformationMessage("Total Expense value updated to  " + totalExpense);
            } else {
                travelf = allowanceForeign.getTravelExpense() * allowanceForeign.getNoOfDays();
                lodgeExpence = allowanceForeign.getBreakLodgeExpense() * allowanceForeign.getNoOfDays();
                lunchDinnerExpense = allowanceForeign.getLunchDinnerExpense() * allowanceForeign.getNoOfDays();
                reservePayment = allowanceForeign.getReservePayment() * allowanceForeign.getNoOfDays();
                goodWillingPayment = allowanceForeign.getGoodwillingPayment() * allowanceForeign.getNoOfDays();
                miscellaneousExpense = allowanceForeign.getMiscellaneousExpense();
                totalExpense = miscellaneousExpense + travelf + lodgeExpence + goodWillingPayment + lunchDinnerExpense + reservePayment;
                allowanceForeign.setTotalExpense(totalExpense);
                JsfUtil.addInformationMessage("Total Expense Calculated");
            }
        } catch (Exception e) {
        }
    }

        //value change event
    public void totalExpenseF(ValueChangeEvent event) {
        calculateTotalExpense();
    }

    //getting counrty list
    public List<FmsLuCountry> getCountriesList() {
        if (luCountryLists == null) {
            luCountryLists = new ArrayList<>();
        }
        luCountryLists = fmsLuCountryBeanLocal.findAllCountry();
        return luCountryLists;
    }

    //value change event
    public void decisionHandler(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedDecision = event.getNewValue().toString();
        }
    }

    //save filed allowance foreign
    public void saveFieldAllowanceForeign() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveFieldAllowanceForeign", dataset)) {
            try {
                allowanceForeign.setEmpId(empEnty);
                allowanceForeign.setFromPlace(country);
                allowanceForeign.setToPlace(fmsLuCountry);
                wfFcmsProcessed.setPerdiemRequestForeignId(allowanceForeign);
                allowanceForeign.setRequestNo(requestNumber);
                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                wfFcmsProcessed.setDecision(allowanceForeign.getWfStatus());
                if (updateStatus == 0) {
                    allowanceForeign.setPreparedBy(wfFcmsProcessed.getProcessedBy().toString());
                    allowanceForeign.setWfStatus(securityBean.Constants.PREPARE_VALUE);
                    wfFcmsProcessed.setDecision(allowanceForeign.getWfStatus());
                    fieldAllowansForeignBeanLocal.create(allowanceForeign);
                    wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                    JsfUtil.addSuccessMessage("Saved Successfully!");
                    clearPopup();
                    generateReqNo();
                } else {
                    if (workFlow.isPrepareStatus() && workFlow.isReadonly() == false) {
                        fieldAllowansForeignBeanLocal.edit(allowanceForeign);
                        JsfUtil.addSuccessMessage("Updated Successfully!");
                        clearPopup();
                        generateReqNo();
                    } else if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
                        if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
                            allowanceForeign.setWfStatus(securityBean.Constants.APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
                            allowanceForeign.setWfStatus(securityBean.Constants.APPROVE_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
                            allowanceForeign.setWfStatus(securityBean.Constants.CHECK_APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
                            allowanceForeign.setWfStatus(securityBean.Constants.CHECK_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.CHECK_REJECT_VALUE);
                        }
                        fieldAllowansForeignBeanLocal.edit(allowanceForeign);
                        wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                        fmsFaFList.remove(allowanceForeign);
                        JsfUtil.addSuccessMessage("Saved Successfully!");
                        clearPopup();
                        requestNumber = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JsfUtil.addFatalMessage("Failed to save! Try Again Relaoding the Page.");
            }
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(SessionBean.getSessionID());
            eventEntry.setUserId(SessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
            eventEntry.setUserLogin(test);
            security.addEventLog(eventEntry, dataset);
        }
    }

    //finding field allowance foreign
    public void findFAForeign() {
        allowanceForeignList = new ArrayList<>();
        //search by Emp Name only
        if (!(empEnty.getFirstName().isEmpty()) && empEnty.getEmpId().isEmpty()) {
            allowanceForeignList = fieldAllowansForeignBeanLocal.searchEmpByEmpName(empEnty);
            //search by EmpId only
        } else if (empEnty.getFirstName().isEmpty() && (!empEnty.getEmpId().isEmpty())) {
            allowanceForeignList = fieldAllowansForeignBeanLocal.searchEmployeeByEmpId(empEnty);
            //search All Emp
        } else {
            allowanceForeignList = fieldAllowansForeignBeanLocal.searchAll();
        }
        allowanceForeignSearchDataModel = new ListDataModel(allowanceForeignList);
    }

    //select event 
    public void selectRequestRow(SelectEvent event) {
        allowanceForeign = (FmsFieldAllowanceForeign) event.getObject();
        populateFA();
        if (workFlow.isPrepareStatus()) {
            if (allowanceForeign.getWfStatus() == 0 || allowanceForeign.getWfStatus() == 4 || allowanceForeign.getWfStatus() == 2) {
                workFlow.setReadonly(false);
            } else {
                workFlow.setReadonly(true);
            }
        }
    }

    //populate
    public void populateFA() {
        try {
            empEnty = allowanceForeign.getEmpId();
            fmsLuCountry = allowanceForeign.getToPlace();
            requestNumber = allowanceForeign.getRequestNo();
            renderBtnPrint = true;
            level = empEnty.getGradeId().getLevelId().getDescription();
            renderPnlCreatePerdiemForeign = true;
            renderPnlManPage = false;
            isRenderedIconWorkFlow = true;
            createOrSearchBundle = "Search";
            disableBtnCreate = true;
            updateStatus = 1;
            saveorUpdateBundle = "Update";
            wfFcmsProcessed.setProcessedOn(wfFcmsProcessed.getProcessedOn());
            recreateWfDataModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //value cnage event for filed allowance request
    public void onSelectFARequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                allowanceForeign = (FmsFieldAllowanceForeign) event.getNewValue();
                populateFA();
                saveorUpdateBundle = "Save";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    //recreate method to assign WfFcmsProcessedList to wfFcmsProcessedDataModel
    public void recreateWfDataModel() {
        wfFcmsProcessedDataModel = null;
        wfFcmsProcessedDataModel = new ListDataModel(allowanceForeign.getWfFcmsProcessedList());
    }

    //clear
    public void clearPopup() {
        allowanceForeign = null;
        allowanceForeign = new FmsFieldAllowanceForeign();
        empEnty = null;
        fmsLuCountry = null;
        wfFcmsProcessed = null;
        saveorUpdateBundle = "Save";
    }

    //clear
    private void clearPage() {
        allowanceForeignSearchDataModel = null;
        allowanceForeign = null;
        empEnty = null;
        updateStatus = 0;
        saveorUpdateBundle = "Create";
    }
    //create and new render
     public void createNewPerdiemForeignInfo() {
        clearPage();
        disableBtnCreate = false;
        renderPnlCreatePerdiemForeign = true;
        renderPnlManPage = false;
    }
}
