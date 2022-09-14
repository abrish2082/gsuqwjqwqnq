/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.perdiem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowanceSettlementBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowansBeanLocal;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.lookup.HrLuJobLevels;

/**
 *
 * @author Mubarek jebel
 */
@Named(value = "fmsPerdiemSettlementController")
@ViewScoped
public class FmsPerdiemSettlementController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="@Inject and @EJB">
    @Inject
    SessionBean SessionBean;
    @Inject
    private FmsFieldAllowance fmsFieldAllowanceEnty;
    @Inject
    private FmsFieldAllowanceSettlement allowanceSettlement;
    @Inject
    private HrEmployees empEnty;
    @Inject
    private FmsLuPerdimeRate fmsLuPerdimeRate;
    @Inject
    HrLuJobLevels hrLuJobLevels;
    @Inject
    WorkFlow workFlow;
    @Inject
    private WfFcmsProcessed wfFcmsProcessed;
    @EJB
    private WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    FmsFieldAllowanceSettlementBeanLocal allowanceSettlementBeanLocal;
    @EJB
    FmsFieldAllowansBeanLocal allowansBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsFieldAllowanceSettlement selectSettlement;
    List<FmsFieldAllowance> allowanceLocalList;
    List<FmsFieldAllowanceSettlement> settlementsList;
    ArrayList<FmsFieldAllowanceSettlement> settlementModel;
    DataModel<FmsFieldAllowanceSettlement> allowanceSettlementDModel;
    DataModel<FmsFieldAllowanceSettlement> fildAllowanceSettlementDataModel;
    private List<FmsFieldAllowanceSettlement> fmsFaSList;
    private DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    private String saveorUpdateBundle = "Save";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateSettlement = false;
    private boolean renderPnlManPage = true;
    private boolean isRenderDecision = false;
    private boolean isRenderedIconNitification = false;
    private boolean isRenderedIconWorkFlow = false;
    private boolean isRenderedBtnNew = false;
    private boolean isRenderedBtnSave = false;
    String selectedDecision = "";
    String userName;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    boolean txtReturnDatevisibility = false;
    private boolean renderBtnPrint = false;
    boolean btnVisibility = true;
    boolean isReadOnlyTxtTravel = false;
    boolean isDisabledDrpDwnEmpName = false;
    String txtvisbility = "display";
    String message = "";
    String allowanceSettlementDeparture = "";
    String Dtime;
    Double DtimeAmount = 0.0;
    Double RtimeAmount = 0.0;
    int updateStatus = 0;
    int dayDifference = 0;
    int day = 0;
    int monthDifference = 0;
    int yearDifference = 0;
    Integer dateCompStatus;
    Integer FieldAllowanceID;
    Integer fmsFaId;
    Double breakfast = 0.0;
    Double lunch = 0.0;
    Double dinner = 0.0;
    Double totalMealsExpense = 0.0;
    Double totalHotelExpense = 0.0;
    Double travel = 0.0;
    Double miscellaneousExpense = 0.0;
    Double totalExpense;
    Double tExpense = 0.0;
    Double fieldAllowanceEntyTotalExpence = 0.0;
    Double allowanceSettlementTotalExpence = 0.0;
    Double difference = 0.0;
    BigDecimal level;
    private NumberConverter numberConverter = new NumberConverter();
    Integer wfStatusApproved = Constants.APPROVE_VALUE;
//</editor-fold>

    public FmsPerdiemSettlementController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }

    @PostConstruct
    public void init() {
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
            renderPnlCreateSettlement = false;
            fmsFaSList = allowanceSettlementBeanLocal.findFaByWfStatus(securityBean.Constants.PREPARE_VALUE);//to be chacked list
        } else if (workFlow.isApproveStatus()) {//Approver
            isRenderedIconNitification = true;
            isRenderDecision = true;
            isRenderedBtnNew = false;
            renderPnlManPage = true;
            renderPnlCreateSettlement = false;
            fmsFaSList = allowanceSettlementBeanLocal.findFaByWfStatus(securityBean.Constants.CHECK_APPROVE_VALUE);//to be approved List
        }
    }

    //  <editor-fold defaultstate="collapsed" desc=" Getter & Setters ">
    public boolean isBtnVisibility() {
        return btnVisibility;
    }

    public void setBtnVisibility(boolean btnVisibility) {
        this.btnVisibility = btnVisibility;
    }

    public boolean isIsReadOnlyTxtTravel() {
        return isReadOnlyTxtTravel;
    }

    public void setIsReadOnlyTxtTravel(boolean isReadOnlyTxtTravel) {
        this.isReadOnlyTxtTravel = isReadOnlyTxtTravel;
    }

    public boolean isIsDisabledDrpDwnEmpName() {
        return isDisabledDrpDwnEmpName;
    }

    public void setIsDisabledDrpDwnEmpName(boolean isDisabledDrpDwnEmpName) {
        this.isDisabledDrpDwnEmpName = isDisabledDrpDwnEmpName;
    }

    public FmsFieldAllowanceSettlement getSelectSettlement() {
        return selectSettlement;
    }

    public void setSelectSettlement(FmsFieldAllowanceSettlement selectSettlement) {
        this.selectSettlement = selectSettlement;
    }

    public HrEmployees getEmpEnty() {
        if (empEnty == null) {
            empEnty = new HrEmployees();
        }
        return empEnty;
    }

    public void setEmpEnty(HrEmployees empEnty) {
        this.empEnty = empEnty;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getDtimeAmount() {
        return DtimeAmount;
    }

    public void setDtimeAmount(Double DtimeAmount) {
        this.DtimeAmount = DtimeAmount;
    }

    public FmsLuPerdimeRate getFmsLuPerdimeRate() {
        return fmsLuPerdimeRate;
    }

    public void setFmsLuPerdimeRate(FmsLuPerdimeRate fmsLuPerdimeRate) {
        this.fmsLuPerdimeRate = fmsLuPerdimeRate;
    }

    public HrLuJobLevels getHrLuJobLevels() {
        if (hrLuJobLevels == null) {
            hrLuJobLevels = new HrLuJobLevels();
        }
        return hrLuJobLevels;
    }

    public void setHrLuJobLevels(HrLuJobLevels hrLuJobLevels) {
        this.hrLuJobLevels = hrLuJobLevels;
    }

    public BigDecimal getLevel() {
        return level;
    }

    public void setLevel(BigDecimal level) {
        this.level = level;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsFieldAllowance getFmsFieldAllowanceEnty() {
        if (fmsFieldAllowanceEnty == null) {
            fmsFieldAllowanceEnty = new FmsFieldAllowance();
        }
        return fmsFieldAllowanceEnty;
    }

    public void setFmsFieldAllowanceEnty(FmsFieldAllowance fmsFieldAllowanceEnty) {
        this.fmsFieldAllowanceEnty = fmsFieldAllowanceEnty;
    }

    public FmsFieldAllowanceSettlement getAllowanceSettlement() {
        if (allowanceSettlement == null) {
            allowanceSettlement = new FmsFieldAllowanceSettlement();
        }
        return allowanceSettlement;
    }

    public void setAllowanceSettlement(FmsFieldAllowanceSettlement allowanceSettlement) {
        this.allowanceSettlement = allowanceSettlement;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateSettlement() {
        return renderPnlCreateSettlement;
    }

    public void setRenderPnlCreateSettlement(boolean renderPnlCreateSettlement) {
        this.renderPnlCreateSettlement = renderPnlCreateSettlement;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public List<FmsFieldAllowanceSettlement> getFmsFaSList() {
        return fmsFaSList;
    }

    public void setFmsFaSList(List<FmsFieldAllowanceSettlement> fmsFaSList) {
        this.fmsFaSList = fmsFaSList;
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

    public boolean isIsRenderedBtnSave() {
        return isRenderedBtnSave;
    }

    public void setIsRenderedBtnSave(boolean isRenderedBtnSave) {
        this.isRenderedBtnSave = isRenderedBtnSave;
    }

    public String getSelectedDecision() {
        return selectedDecision;
    }

    public void setSelectedDecision(String selectedDecision) {
        this.selectedDecision = selectedDecision;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isTxtReturnDatevisibility() {
        return txtReturnDatevisibility;
    }

    public void setTxtReturnDatevisibility(boolean txtReturnDatevisibility) {
        this.txtReturnDatevisibility = txtReturnDatevisibility;
    }

    public boolean isRenderBtnPrint() {
        return renderBtnPrint;
    }

    public void setRenderBtnPrint(boolean renderBtnPrint) {
        this.renderBtnPrint = renderBtnPrint;
    }

    public DataModel<FmsFieldAllowanceSettlement> getAllowanceSettlementDModel() {
        if (allowanceSettlementDModel == null) {
            allowanceSettlementDModel = new ListDataModel<>();
        }
        return allowanceSettlementDModel;
    }

    public DataModel<FmsFieldAllowanceSettlement> getFildAllowanceSettlementDataModel() {
        if (fildAllowanceSettlementDataModel == null) {
            fildAllowanceSettlementDataModel = new ListDataModel<>();
        }
        return fildAllowanceSettlementDataModel;
    }

    public void setFildAllowanceSettlementDataModel(DataModel<FmsFieldAllowanceSettlement> fildAllowanceSettlementDataModel) {
        this.fildAllowanceSettlementDataModel = fildAllowanceSettlementDataModel;
    }

    public ArrayList<FmsFieldAllowanceSettlement> getSettlementModel() {
        if (settlementModel == null) {
            settlementModel = new ArrayList<>();
        }
        return settlementModel;
    }

    public void setSettlementModel(ArrayList<FmsFieldAllowanceSettlement> settlementModel) {
        this.settlementModel = settlementModel;
    }

    public List<FmsFieldAllowanceSettlement> getSettlementsList() {
        return settlementsList;
    }

    public void setSettlementsList(List<FmsFieldAllowanceSettlement> settlementsList) {
        this.settlementsList = settlementsList;
    }

    public void setAllowanceSettlementDModel(DataModel<FmsFieldAllowanceSettlement> allowanceSettlementDModel) {
        this.allowanceSettlementDModel = allowanceSettlementDModel;
    }
//</editor-fold>

    //search
    public void searchPerdiemSettlementLocal() {
        clearPage();
        disableBtnCreate = false;
        renderPnlCreateSettlement = false;
        renderPnlManPage = true;
    }

    //searcg employee
    public void searchSettlementByParameter() {
        settlementModel = new ArrayList<>();
        //search by Emp Name only
        if (!(empEnty.getFirstName().isEmpty()) && empEnty.getEmpId().isEmpty()) {
            settlementsList = allowanceSettlementBeanLocal.searchEmpByEmpName(empEnty);
            //search by EmpId only
        } else if (empEnty.getFirstName().isEmpty() && (!empEnty.getEmpId().isEmpty())) {
            settlementsList = allowanceSettlementBeanLocal.searchEmployeeByEmpId(empEnty);
            //search All Emp
        } else {
            settlementsList = allowanceSettlementBeanLocal.searchAllEmployee();

        }
        recreateDataModel();
    }

    //find by approved status
    public List<FmsFieldAllowance> getEmployeeList() {
        return allowanceLocalList = allowanceSettlementBeanLocal.findByWfStatusApproved(fmsFieldAllowanceEnty, wfStatusApproved);
    }

    //value change event 
    public void findByempIdAndStatus(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsFaId = (Integer.parseInt(event.getNewValue().toString()));
                FmsFieldAllowance fieldAllowance = new FmsFieldAllowance();
                fieldAllowance.setId(fmsFaId);
                fmsFieldAllowanceEnty = allowanceSettlementBeanLocal.findByempIdAndStatus(fieldAllowance);
                level = fmsFieldAllowanceEnty.getEmpId().getGradeId().getLevelId().getId();
                hrLuJobLevels = fmsFieldAllowanceEnty.getEmpId().getGradeId().getLevelId();
                FieldAllowanceID = fmsFieldAllowanceEnty.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Failed to process! Try Again Reloading the Page.");
        }
    }

    //select event 
    public void FromDate(SelectEvent event) {
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM-DD");
        allowanceSettlementDeparture = simpledate.format(allowanceSettlement.getDepartureDate());
    }

    //date compasition
    public int dateCompure() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tdate = sdf.format(allowanceSettlement.getReturnedDate());
        String fdate = sdf.format(allowanceSettlement.getDepartureDate());
        Date fromdate = sdf.parse(fdate);
        Date todate = sdf.parse(tdate);
        if (fromdate.after(todate)) {
            dateCompStatus = 1;
        } else if (fromdate.before(todate)) {
            dateCompStatus = 0;
        } else {
            dateCompStatus = 0;
        }
        return dateCompStatus;

    }

    //date difference select event
    public void datdiff(SelectEvent event) throws ParseException {
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM-DD");
        String y = simpledate.format(allowanceSettlement.getReturnedDate());
        dateCompure();
        if (dateCompStatus == 1) {
            txtReturnDatevisibility = true;
            txtvisbility = "hidden";
            JsfUtil.addErrorMessage("Invalid Date Selection!");
        } else {

            String day1 = allowanceSettlementDeparture.substring(allowanceSettlementDeparture.lastIndexOf('-') + 1);
            String day2 = y.substring(y.lastIndexOf('-') + 1);

            if (Integer.parseInt(day2) > Integer.parseInt(day1)) {
                day = Integer.parseInt(day2) + 1 - Integer.parseInt(day1);
            } else {
                day = Integer.parseInt(day1) - Integer.parseInt(day2);
            }
            String month1 = allowanceSettlementDeparture.substring(allowanceSettlementDeparture.indexOf('-') + 1, allowanceSettlementDeparture.lastIndexOf('-'));
            String month2 = y.substring(y.indexOf('-') + 1, y.lastIndexOf('-'));
            if (Integer.parseInt(month1) > Integer.parseInt(month2)) {
                monthDifference = Integer.parseInt(month1) - Integer.parseInt(month2);
            } else {
                monthDifference = Integer.parseInt(month2) - Integer.parseInt(month1);
            }
            String year1 = allowanceSettlementDeparture.substring(0, allowanceSettlementDeparture.indexOf('-'));
            String year2 = y.substring(0, y.indexOf('-'));
            if (Integer.parseInt(year1) > Integer.parseInt(year2)) {
                yearDifference = Integer.parseInt(year1) - Integer.parseInt(year2);
            } else {
                yearDifference = Integer.parseInt(year2) - Integer.parseInt(year1);
            }
            dayDifference = yearDifference + monthDifference + day;
            allowanceSettlement.setNoOfDays(dayDifference);
            allowanceSettlement.setPreparedDate(allowanceSettlement.getReturnedDate());
        }

    }

    //value change event
    public void getDtime(ValueChangeEvent e) {
        Dtime = allowanceSettlement.getDepartureTime();

    }

    //value change event for select request

    public void onSelectFARequest(ValueChangeEvent event) {
        try {
            if (null != event.getNewValue()) {
                allowanceSettlement = (FmsFieldAllowanceSettlement) event.getNewValue();
                populateFA();
                saveorUpdateBundle = "Save";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Faile to process! Try Again Reloading the Page");
        }
    }

    //value change event
    public void travel(ValueChangeEvent event) {
        travel = allowanceSettlement.getTravelExpense();
    }

    //value change event
    public void totalExpenseSettle(ValueChangeEvent event) {
        miscellaneousExpense = allowanceSettlement.getMiscellaneousExpense();
        totalExpense = miscellaneousExpense + totalMealsExpense + totalHotelExpense + travel;
        allowanceSettlement.setTotalExpense(totalExpense);
    }

    //value change event to get return time

    public void getRtime(ValueChangeEvent e) {
        level = fmsFieldAllowanceEnty.getEmpId().getGradeId().getLevelId().getId();
        fmsLuPerdimeRate.setJobLevelId(hrLuJobLevels);
        fmsLuPerdimeRate = allowanceSettlementBeanLocal.getPer(fmsLuPerdimeRate);
        breakfast = ((fmsLuPerdimeRate.getFood()) * 0.2);
        lunch = ((fmsLuPerdimeRate.getFood()) * 0.4);
        dinner = ((fmsLuPerdimeRate.getFood()) * 0.4);
        String Rtime = allowanceSettlement.getReturnedTime();
        if (null != Dtime) {
            switch (Dtime) {
                case "After 2AM":
                    DtimeAmount = lunch + dinner;
                    break;
                case "After 7PM ":
                    DtimeAmount = dinner;
                    break;
                case "  After 2:30PM":
                    DtimeAmount = DtimeAmount + 0;
                    break;
            }
        }
        if (null != Rtime) {
            switch (Rtime) {
                case "After 2AM":
                    RtimeAmount = breakfast;
                    break;
                case "After 7PM":
                    RtimeAmount = breakfast + lunch;
                    break;
                case "After 12PM":
                    RtimeAmount = breakfast + lunch + dinner;
                    break;
            }
        }

        totalHotelExpense = ((dayDifference - 1) * fmsLuPerdimeRate.getLodging());
        totalMealsExpense = (dayDifference - 2) * (fmsLuPerdimeRate.getFood()) + DtimeAmount + RtimeAmount;
        allowanceSettlement.setLodgingExpense(totalHotelExpense);
        allowanceSettlement.setFoodExpense(totalMealsExpense);
    }

    //select event for settlment row
    public void selectSettlementRow(SelectEvent event) {
        allowanceSettlement = (FmsFieldAllowanceSettlement) event.getObject();
        populateFA();
        if (workFlow.isPrepareStatus()) {
            if (allowanceSettlement.getWfStatus() == 0 || allowanceSettlement.getWfStatus() == 4 || allowanceSettlement.getWfStatus() == 2) {
                workFlow.setReadonly(false);
            } else {
                workFlow.setReadonly(true);
            }
        }
    }

    //populate
    public void populateFA() {
        try {
            fmsFieldAllowanceEnty = allowanceSettlement.getAllowanceReqId();
            if (fmsFieldAllowanceEnty.getTravelExpense() == 0.00) {
                isReadOnlyTxtTravel = true;
            } else {
                isReadOnlyTxtTravel = false;
            }
            level = fmsFieldAllowanceEnty.getEmpId().getGradeId().getLevelId().getId();
            FieldAllowanceID = fmsFieldAllowanceEnty.getId();
            empEnty = fmsFieldAllowanceEnty.getEmpId();
            fmsFieldAllowanceEnty.setEmpId(empEnty);
            calculateDifference();
            setIsDisabledDrpDwnEmpName(true);
            btnVisibility = false;
            renderBtnPrint = true;
            renderPnlCreateSettlement = true;
            renderPnlManPage = false;
            disableBtnCreate = true;
            updateStatus = 1;
            saveorUpdateBundle = "Update";
            isRenderedIconWorkFlow = true;
            wfFcmsProcessed.setProcessedOn(wfFcmsProcessed.getProcessedOn());
            recreateWfDataModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //calculate
    public void calculateDifference() {
        try {

            fieldAllowanceEntyTotalExpence = fmsFieldAllowanceEnty.getTotalExpense();
            allowanceSettlementTotalExpence = allowanceSettlement.getTotalExpense();

            if (fieldAllowanceEntyTotalExpence > allowanceSettlementTotalExpence) {
                difference = fieldAllowanceEntyTotalExpence - allowanceSettlementTotalExpence;
                message = "Birr Returnable!";
            } else if (allowanceSettlementTotalExpence > fieldAllowanceEntyTotalExpence) {
                difference = allowanceSettlementTotalExpence - fieldAllowanceEntyTotalExpence;
                message = "Birr Payable!";
            } else {
                difference = 0.0;
                message = "Balanced!";
            }
            allowanceSettlement.setReturnableAmount(difference);
            isRenderedBtnSave = true;
            JsfUtil.addInformationMessage("Difference Amount Calcuted!");
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to Calculate. Try Again reloading the Page");
        }
    }

    //save
    public void savePerdiemSettlementLocal() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "savePerdiemSettlementLocal", dataset)) {
            try {
                allowanceSettlement.setEmpId(fmsFieldAllowanceEnty.getEmpId());
                allowanceSettlement.setAllowanceReqId(fmsFieldAllowanceEnty);
                wfFcmsProcessed.setPerdiemLocalSettlementId(allowanceSettlement);
                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));

                if (updateStatus == 0) {//on Save
                    fmsFieldAllowanceEnty.setId(FieldAllowanceID);
                    fmsFieldAllowanceEnty.setStatus("Settled");
                    allowanceSettlement.setPreparedBy(wfFcmsProcessed.getProcessedBy().toString());
                    allowanceSettlement.setWfStatus(securityBean.Constants.PREPARE_VALUE);
                    wfFcmsProcessed.setDecision(allowanceSettlement.getWfStatus());
                    allowanceSettlementBeanLocal.create(allowanceSettlement);
                    wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                    allowansBeanLocal.edit(fmsFieldAllowanceEnty);
                    JsfUtil.addSuccessMessage("Settled Successfully");
                    clearPopup();
                } else {//on update
                    if (workFlow.isPrepareStatus() && workFlow.isReadonly() == false) {
                        allowanceSettlementBeanLocal.edit(allowanceSettlement);
                        JsfUtil.addSuccessMessage("Updated Succesfully!");
                        clearPopup();
                    } else if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
                        wfFcmsProcessed.setDecision(allowanceSettlement.getWfStatus());
                        if (selectedDecision.equals("Approved") && workFlow.isApproveStatus()) {
                            allowanceSettlement.setWfStatus(securityBean.Constants.APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isApproveStatus()) {
                            allowanceSettlement.setWfStatus(securityBean.Constants.APPROVE_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.APPROVE_REJECT_VALUE);
                        } else if (selectedDecision.equals("Approved") && workFlow.isCheckStatus()) {
                            allowanceSettlement.setWfStatus(securityBean.Constants.CHECK_APPROVE_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.CHECK_APPROVE_VALUE);
                        } else if (selectedDecision.equals("Rejected") && workFlow.isCheckStatus()) {
                            allowanceSettlement.setWfStatus(securityBean.Constants.CHECK_REJECT_VALUE);
                            wfFcmsProcessed.setDecision(securityBean.Constants.CHECK_REJECT_VALUE);
                        }
                        allowanceSettlementBeanLocal.edit(allowanceSettlement);
                        wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                        fmsFaSList.remove(allowanceSettlement);
                        JsfUtil.addSuccessMessage("Saved Successfully!");
                        clearPopup();
                    }
                    setIsDisabledDrpDwnEmpName(false);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JsfUtil.addFatalMessage("Failed to save! Try again reloading the page.");
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

    //render to assign WfFcmsProcessedList to wfFcmsProcessedDataModel
    public void recreateWfDataModel() {
        wfFcmsProcessedDataModel = null;
        wfFcmsProcessedDataModel = new ListDataModel(allowanceSettlement.getWfFcmsProcessedList());
    }

    //recreate to assign settlement list to settlementModel
    private void recreateDataModel() {
        settlementModel = null;
        settlementModel = new ArrayList<>(settlementsList);
    }

    //clear
    public void clearPopup() {
        allowanceSettlement = null;
        fmsFieldAllowanceEnty = null;
        wfFcmsProcessed = null;
        message = null;
        saveorUpdateBundle = "Save";
    }

    //clear
    private void clearPage() {
        allowanceSettlementDModel = null;
        allowanceSettlement = null;
        empEnty = null;
        saveorUpdateBundle = "Save";

    }

    //create and search render
    public void createNewPerdiemSettlementInfo() {
        clearPage();
        disableBtnCreate = false;
        renderPnlCreateSettlement = true;
        renderPnlManPage = false;
    }
}
