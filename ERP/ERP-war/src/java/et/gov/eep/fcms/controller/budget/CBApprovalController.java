/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBTasksBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
    //</editor-fold>

/**
 *
 * @author Me
 */
@Named(value = "cBApprovalController")
@ViewScoped
public class CBApprovalController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;

    @Inject
    FmsCapitalBudget1 fmsCapitalBudget1;
    @Inject
    FmsCapitalBudgetTasks capitalBudgetTasks;
    @Inject
    FmsCapitalBudgetDetail fmsCapitalBudgetDetail;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsBudgetCode fmsBudgetCode;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsLuBudgetYearBeanLocal fmsLuBudgetYearBeanLocal;
    @EJB
    FmsCapitalBudgetBeanLocal fmsCapitalBudgetBeanLocal;
    @EJB
    FmsCapitalBudgetDetailBeanLocal fmsCapitalBudgetDetailBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal cSystemJunctionBeanLocal;
    @EJB
    FmsCBTasksBeanLocal cBTasksBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    Constants constants;

    DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsCapitalBudget1> fmsCapitalBudget1List = new ArrayList<>();
    DataModel<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailmodel;
    DataModel<FmsCapitalBudget1> fmsCapitalBudgetDetailSrcmodel;
    DataModel<FmsCapitalBudgetTasks> fmscapitalBudgetTasksModel;
    FmsCapitalBudgetTasks budgetTasksselection;
    FmsCapitalBudgetDetail capitalBudgetDetailSelection;
    List<FmsCapitalBudget1> requestList = new ArrayList<>();
    List<FmsCapitalBudget1> cbrequestList = new ArrayList<>();
    List<FmsCapitalBudgetTasks> cbtaskList = new ArrayList<>();
    List<WfFcmsProcessed> wfFcmsProcessedList = new ArrayList<>();

    int detailPopulate = 0;
    String workFlowLevel = "";
    private String decisionType;
    private NumberConverter numberConverter = new NumberConverter();
    BigDecimal totalRequestedAmount = new BigDecimal(BigInteger.ZERO);
    String userRemark = "";
    String userRemarkDate = "";
    Integer requestNotificationCounter = 0;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Capital Budget Approval Search";
    private String saveOrUpdate = "Create";

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">

    public Integer getRequestNotificationCounter() {
        requestList = fmsCapitalBudgetBeanLocal.findRequestForApproval();
        requestNotificationCounter = requestList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public Constants getConstants() {
        return constants;
    }

    public List<FmsCapitalBudget1> getRequestList() {
        return requestList;
    }

    public List<FmsCapitalBudgetTasks> getCbtaskList() {
        return cbtaskList;
    }

    public void setCbtaskList(List<FmsCapitalBudgetTasks> cbtaskList) {
        this.cbtaskList = cbtaskList;
    }

    public void setRequestList(List<FmsCapitalBudget1> requestList) {
        this.requestList = requestList;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getUserRemarkDate() {
        return userRemarkDate;
    }

    public void setUserRemarkDate(String userRemarkDate) {
        this.userRemarkDate = userRemarkDate;
    }

    public BigDecimal getTotalRequestedAmount() {
        return totalRequestedAmount;
    }

    public void setTotalRequestedAmount(BigDecimal totalRequestedAmount) {
        this.totalRequestedAmount = totalRequestedAmount;
        gettotalReqAmount();
    }

    public List<FmsCapitalBudget1> getCbrequestList() {
        if (cbrequestList == null) {
            cbrequestList = new ArrayList<>();
        }
        return cbrequestList;
    }

    public void setCbrequestList(List<FmsCapitalBudget1> cbrequestList) {
        this.cbrequestList = cbrequestList;
    }

    public DataModel<FmsCapitalBudgetTasks> getFmscapitalBudgetTasksModel() {
        return fmscapitalBudgetTasksModel;
    }

    public void setFmscapitalBudgetTasksModel(DataModel<FmsCapitalBudgetTasks> fmscapitalBudgetTasksModel) {
        this.fmscapitalBudgetTasksModel = fmscapitalBudgetTasksModel;
    }

    public String gettotalReqAmount() {
        if (getTotalRequestedAmount() != null) {
            String number = getTotalRequestedAmount().toString();
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,##0.00##");
            return formatter.format(amount);
        } else {
            return null;
        }
    }

    public String getWorkFlowLevel() {
        return workFlowLevel;
    }

    public void setWorkFlowLevel(String workFlowLevel) {
        this.workFlowLevel = workFlowLevel;
    }

    public String getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(String decisionType) {
        this.decisionType = decisionType;
    }

    public FmsCapitalBudgetTasks getCapitalBudgetTasks() {
        return capitalBudgetTasks;
    }

    public void setCapitalBudgetTasks(FmsCapitalBudgetTasks capitalBudgetTasks) {
        this.capitalBudgetTasks = capitalBudgetTasks;
    }

    public FmsCapitalBudget1 getFmsCapitalBudget1() {
        if (fmsCapitalBudget1 == null) {
            fmsCapitalBudget1 = new FmsCapitalBudget1();
        }
        return fmsCapitalBudget1;
    }

    public void setFmsCapitalBudget1(FmsCapitalBudget1 fmsCapitalBudget1) {
        this.fmsCapitalBudget1 = fmsCapitalBudget1;
    }

    public FmsCapitalBudgetDetail getFmsCapitalBudgetDetail() {
        if (fmsCapitalBudgetDetail == null) {
            fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        }
        return fmsCapitalBudgetDetail;
    }

    public void setFmsCapitalBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        this.fmsCapitalBudgetDetail = fmsCapitalBudgetDetail;
    }

    public FmsBudgetCode getFmsBudgetCode() {
        if (fmsBudgetCode == null) {
            fmsBudgetCode = new FmsBudgetCode();
        }
        return fmsBudgetCode;
    }

    public void setFmsBudgetCode(FmsBudgetCode fmsBudgetCode) {
        this.fmsBudgetCode = fmsBudgetCode;
    }

    public FmsAccountingPeriod getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(FmsAccountingPeriod currPeriod) {
        this.currPeriod = currPeriod;
    }

    public FmsLuBudgetYear getNextPeriod() {
        return nextPeriod;
    }

    public void setNextPeriod(FmsLuBudgetYear nextPeriod) {
        this.nextPeriod = nextPeriod;
    }

    public List<FmsLuBudgetYear> getBudgetyrList() {
        return budgetyrList;
    }

    public void setBudgetyrList(List<FmsLuBudgetYear> budgetyrList) {
        this.budgetyrList = budgetyrList;
    }

    public List<FmsCapitalBudget1> getFmsCapitalBudget1List() {
        return fmsCapitalBudget1List;
    }

    public void setFmsCapitalBudget1List(List<FmsCapitalBudget1> fmsCapitalBudget1List) {
        this.fmsCapitalBudget1List = fmsCapitalBudget1List;
    }

    public DataModel<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailmodel() {
        return fmsCapitalBudgetDetailmodel;
    }

    public void setFmsCapitalBudgetDetailmodel(DataModel<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailmodel) {
        this.fmsCapitalBudgetDetailmodel = fmsCapitalBudgetDetailmodel;
    }

    public DataModel<FmsCapitalBudget1> getFmsCapitalBudgetDetailSrcmodel() {
        return fmsCapitalBudgetDetailSrcmodel;
    }

    public void setFmsCapitalBudgetDetailSrcmodel(DataModel<FmsCapitalBudget1> fmsCapitalBudgetDetailSrcmodel) {
        this.fmsCapitalBudgetDetailSrcmodel = fmsCapitalBudgetDetailSrcmodel;
    }

    public FmsCapitalBudgetTasks getBudgetTasksselection() {
        return budgetTasksselection;
    }

    public void setBudgetTasksselection(FmsCapitalBudgetTasks budgetTasksselection) {
        this.budgetTasksselection = budgetTasksselection;
    }

    public FmsCapitalBudgetDetail getCapitalBudgetDetailSelection() {
        return capitalBudgetDetailSelection;
    }

    public void setCapitalBudgetDetailSelection(FmsCapitalBudgetDetail capitalBudgetDetailSelection) {
        this.capitalBudgetDetailSelection = capitalBudgetDetailSelection;
    }

    public int getDetailPopulate() {
        return detailPopulate;
    }

    public void setDetailPopulate(int detailPopulate) {
        this.detailPopulate = detailPopulate;
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

    public DataModel<WfFcmsProcessed> getWfFcmsProcessedDataModel() {
        return wfFcmsProcessedDataModel;
    }

    public void setWfFcmsProcessedDataModel(DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel) {
        this.wfFcmsProcessedDataModel = wfFcmsProcessedDataModel;
    }

    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
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

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsLuSystem getFmsLuSystem() {
        return fmsLuSystem;
    }

    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    public FmsLuBudgetYear getFmsLuBudgetYear() {
        return fmsLuBudgetYear;
    }

    public void setFmsLuBudgetYear(FmsLuBudgetYear fmsLuBudgetYear) {
        this.fmsLuBudgetYear = fmsLuBudgetYear;
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

    public boolean isButtonRenderd() {
        return buttonRenderd;
    }

    public void setButtonRenderd(boolean buttonRenderd) {
        this.buttonRenderd = buttonRenderd;
    }

    public boolean isApproveRenderd() {
        return approveRenderd;
    }

    public void setApproveRenderd(boolean approveRenderd) {
        this.approveRenderd = approveRenderd;
    }

    public String getReqheaderTitle() {
        return reqheaderTitle;
    }

    public void setReqheaderTitle(String reqheaderTitle) {
        this.reqheaderTitle = reqheaderTitle;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CBApprovalController">
    public CBApprovalController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods">

    public void recreateWFDataModel() {
        wfFcmsProcessedDataModel = null;
        for (int i = 0; i < fmsCapitalBudget1List.size(); i++) {
            wfFcmsProcessedList = fmsCapitalBudget1List.get(i).getWfFcmsProcessedList();
        }
        wfFcmsProcessedDataModel = new ListDataModel(new ArrayList(wfFcmsProcessedList));
    }

    public void createNewCBApprovalView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Capital Budget Approval Information";
            buttonRenderd = true;
            approveRenderd = false;
            setIcone("ui-icon-search");
            reset();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Capital Budget Approval Search";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
            reset();
        }
        budgetTasksselection = new FmsCapitalBudgetTasks();
        if (!fmsCapitalBudget1List.isEmpty()) {
            double x = 0;
            for (int i = 0; i < fmsCapitalBudget1List.size(); i++) {
                x += fmsCapitalBudget1List.get(i).getTotalReqAmount().doubleValue();
            }
            setTotalRequestedAmount(new BigDecimal(x));
        }
    }

    public List<FmsCapitalBudget1> getCBRequestList() {

        cbrequestList = fmsCapitalBudgetBeanLocal.findRequestForApproval();

        return cbrequestList;
    }

    public void CBRequestListChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            String reqCode = event.getNewValue().toString();
            fmsCapitalBudget1.setRequestCode(reqCode);
            fmsCapitalBudget1 = fmsCapitalBudgetBeanLocal.findByRequestCodeOnRequest(fmsCapitalBudget1);
            cbtaskList = cBTasksBeanLocal.findByCBudgetReqCode(fmsCapitalBudget1);
            FmsLuBudgetYear budgetYear = new FmsLuBudgetYear();
            FmsLuSystem luSystem = new FmsLuSystem();
            FmsCostCenter costCenter = new FmsCostCenter();
            FmsCostcSystemJunction ccssJunction;
            budgetYear = fmsCapitalBudget1.getBudgetYear();
            luSystem = fmsCapitalBudget1.getCcSsJunction().getFmsLuSystem();
            costCenter = fmsCapitalBudget1.getCcSsJunction().getFmsCostCenter();
            ccssJunction = fmsCapitalBudget1.getCcSsJunction();
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findByBudgetYearSystem(budgetYear, ccssJunction);
            recreateSearchDModel();
            recreateWFDataModel();
            fmsLuBudgetYear = budgetYear;
            fmsLuSystem = ccssJunction.getFmsLuSystem();
            decisionChange();
            statusChange();
            recreateTaskDataModel();
        }
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            String[] parts = event.getNewValue().toString().split(" ");
            String part1 = parts[0];
            String part2 = parts[1];
            String part3 = parts[2];
            FmsLuBudgetYear budgetYear = new FmsLuBudgetYear();
            FmsLuSystem luSystem = new FmsLuSystem();
            FmsCostCenter costCenter = new FmsCostCenter();
            FmsCostcSystemJunction ccssJunction = new FmsCostcSystemJunction();
            budgetYear.setBudgetYear(part1);
            luSystem.setSystemCode(part2);
            costCenter.setSystemName(part3);
            budgetYear = fmsLuBudgetYearBeanLocal.findByBudjetYear(budgetYear);
            luSystem = fmsLuSystemBeanLocal.findBySytemCode2(luSystem);
            costCenter.setSystemId(luSystem);
            costCenter = fmsCostCenterBeanLocal.getCCDetail(costCenter);
            ccssJunction = cSystemJunctionBeanLocal.findByCCandSS(luSystem, costCenter);
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findByBudgetYearSystem(budgetYear, ccssJunction);
            recreateSearchDModel();
            recreateWFDataModel();
            fmsLuBudgetYear = budgetYear;
            fmsLuSystem = luSystem;
            decisionChange();
            statusChange();
        }
    }

    public void decisionChange() {
        try {
            if (!wfFcmsProcessedList.isEmpty()) {
                for (int i = 0; i < wfFcmsProcessedList.size(); i++) {
                    if (wfFcmsProcessedList.get(i).getDecision() == 17 ^ wfFcmsProcessedList.get(i).getDecision() == 18) {
                        wfFcmsProcessedList.get(i).setChangedDecision("Requested");
                    } else if (wfFcmsProcessedList.get(i).getDecision() == 0 ^ wfFcmsProcessedList.get(i).getDecision() == 2) {
                        wfFcmsProcessedList.get(i).setChangedDecision("Prepared");
                    } else if (wfFcmsProcessedList.get(i).getDecision() == 1 ^ wfFcmsProcessedList.get(i).getDecision() == 4) {
                        wfFcmsProcessedList.get(i).setChangedDecision("Checked");
                    } else if (wfFcmsProcessedList.get(i).getDecision() == 3 ^ wfFcmsProcessedList.get(i).getDecision() == 11) {
                        wfFcmsProcessedList.get(i).setChangedDecision("Approved");
                    } else if (wfFcmsProcessedList.get(i).getDecision() == 10) {
                        wfFcmsProcessedList.get(i).setChangedDecision("Authorized");
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public void statusChange() {
        try {
            if (!fmsCapitalBudget1List.isEmpty()) {
                for (int i = 0; i < fmsCapitalBudget1List.size(); i++) {
                    if (fmsCapitalBudget1List.get(i).getStatus() == 17 ^ fmsCapitalBudget1List.get(i).getStatus() == 18) {
                        fmsCapitalBudget1List.get(i).setChangedStatus("Requested");
                        workFlowLevel = "Requested";
                    } else if (fmsCapitalBudget1List.get(i).getStatus() == 0 ^ fmsCapitalBudget1List.get(i).getStatus() == 2) {
                        fmsCapitalBudget1List.get(i).setChangedStatus("Prepared");
                        workFlowLevel = "Prepared";
                    } else if (fmsCapitalBudget1List.get(i).getStatus() == 1 ^ fmsCapitalBudget1List.get(i).getStatus() == 4) {
                        fmsCapitalBudget1List.get(i).setChangedStatus("Checked");
                        workFlowLevel = "Checked";
                    } else if (fmsCapitalBudget1List.get(i).getStatus() == 3 ^ fmsCapitalBudget1List.get(i).getStatus() == 11) {
                        fmsCapitalBudget1List.get(i).setChangedStatus("Approved");
                        workFlowLevel = "Approved";
                    } else if (fmsCapitalBudget1List.get(i).getStatus() == 10) {
                        fmsCapitalBudget1List.get(i).setChangedStatus("Authorized");
                        workFlowLevel = "Authorized";
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public void addOBDetail() {
        fmsCapitalBudget1.addCapitalBudgetDetail(fmsCapitalBudgetDetail);
        recreateDataModel();
    }

    public void recreateDataModel() {
        fmsCapitalBudgetDetailmodel = null;
        fmsCapitalBudgetDetailmodel = new ListDataModel(new ArrayList(fmsCapitalBudget1.getFmsCapitalBudgetDetailList()));
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
    }

    public void recreateTaskDataModel() {
        fmscapitalBudgetTasksModel = null;
        fmscapitalBudgetTasksModel = new ListDataModel(new ArrayList(cbtaskList));
    }

    public void save() {
        if ("Requested".equals(workFlowLevel)) {
            if ("Reject".equals(decisionType)) {
                fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1);
                JsfUtil.addSuccessMessage("Capital Budget Request Rejected.");
                reset();
            } else if ("Approve".equals(decisionType)) {
                fmsCapitalBudget1.setStatus(Constants.REQUEST_VALUE);
                fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1);
                JsfUtil.addSuccessMessage("Capital Budget Request Prepared.");
                reset();
            }
        } else if ("Prepared".equals(workFlowLevel)) {
            if ("Reject".equals(decisionType)) {
                fmsCapitalBudget1.setStatus(Constants.PREPARE_VALUE);
                fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1);
                JsfUtil.addSuccessMessage("Capital Budget Request Rejected.");
                reset();
            } else if ("Approve".equals(decisionType)) {
                fmsCapitalBudget1.setStatus(Constants.CHECK_APPROVE_VALUE);
                fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1);
                JsfUtil.addSuccessMessage("Capital Budget Request Checked.");
                reset();
            }
        }
    }

    public void reset() {
        fmsCapitalBudget1 = new FmsCapitalBudget1();
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsGeneralLedger = new FmsGeneralLedger();
        wfFcmsProcessed = new WfFcmsProcessed();
        wfFcmsProcessedDataModel = new ArrayDataModel<>();
        wfFcmsProcessedList = new ArrayList<>();
        fmsCapitalBudgetDetailmodel = new ArrayDataModel<>();
        fmsCapitalBudgetDetailSrcmodel = new ArrayDataModel<>();
        totalRequestedAmount = null;
        decisionType = null;
        workFlowLevel = null;
        userRemarkDate = null;
        userRemark = null;
    }

    public SelectItem[] getLuBudgetYearSearchList() {
        listLuBudgetYearList = fmsLuBudgetYearBeanLocal.getLuBudgetYear();
        listLuBudgetYearList.size();

        currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        nextPeriod = new FmsLuBudgetYear();
        budgetyrList = new ArrayList<>();
        for (int i = 0; i < listLuBudgetYearList.size(); i++) {
            if (Objects.equals(currPeriod.getLuBudgetYearId().getLuBudgetYearId(), listLuBudgetYearList.get(i).getLuBudgetYearId())) {
                nextPeriod = listLuBudgetYearList.get(i + 1);
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }

    public void searchYearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsCapitalBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }

    public SelectItem[] getCostCenterSearch() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    public SelectItem[] getWorkFlow() {
        return JsfUtil.getSelectItems(workFlow(), true);
    }

    public ArrayList<String> workFlow() {

        ArrayList<String> level = new ArrayList<>();
        level.add("Requested");
        level.add("Prepared");
        return level;
    }

    public void searchByCriteria() {
    }

    public void searchCBApproval() {
        if (workFlowLevel.equalsIgnoreCase("Requested")) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.fetchCBforProcessOnReq(fmsLuBudgetYear, fmsCostCenter);

            if (!fmsCapitalBudget1List.isEmpty()) {
                double x = 0;
                for (int i = 0; i < fmsCapitalBudget1List.size(); i++) {
                    x += fmsCapitalBudget1List.get(i).getTotalReqAmount().doubleValue();
                }
                setTotalRequestedAmount(new BigDecimal(x));
                recreateSearchDModel();
                recreateWFDataModel();
            } else {
                JsfUtil.addFatalMessage("No Result Found.");
                totalRequestedAmount = null;
                recreateSearchDModel();
                recreateWFDataModel();
            }
        }
        if (workFlowLevel.equalsIgnoreCase("Prepared")) {
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.fetchCBforProcessPrepared(fmsLuBudgetYear, fmsCostCenter);

            if (!fmsCapitalBudget1List.isEmpty()) {
                recreateSearchDModel();
                recreateWFDataModel();
            } else {
                JsfUtil.addFatalMessage("No Result Found.");
                totalRequestedAmount = null;
                recreateSearchDModel();
                recreateWFDataModel();
            }
        }
    }

    public void saveCBApproval() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveCBApproval", dataset)) {
                if (null != workFlowLevel) {
                    switch (workFlowLevel) {
                        case "Requested":
                            if (null != decisionType) {
                                switch (decisionType) {
                                    case "Reject":
                                        for (FmsCapitalBudget1 fmsCapitalBudget1List1 : fmsCapitalBudget1List) {
                                            fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setCapitalBudgetId(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsCapitalBudget1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.PREPARE_REJECT_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Capital Budget Request Rejected.");
                                        reset();
                                        break;
                                    case "Approve":
                                        for (FmsCapitalBudget1 fmsCapitalBudget1List1 : fmsCapitalBudget1List) {
                                            fmsCapitalBudget1List1.setStatus(Constants.PREPARE_VALUE);
                                            fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setCapitalBudgetId(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsCapitalBudget1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Capital Budget Request Prepared.");
                                        reset();
                                        break;
                                }
                            }
                            break;
                        case "Prepared":
                            if (null != decisionType) {
                                switch (decisionType) {
                                    case "Reject":
                                        for (FmsCapitalBudget1 fmsCapitalBudget1List1 : fmsCapitalBudget1List) {
                                            fmsCapitalBudget1List1.setStatus(Constants.CHECK_REJECT_VALUE);
                                            fmsCapitalBudget1List1.setRequestRemark(userRemark);
                                            fmsCapitalBudget1List1.setRequestDate(userRemarkDate);
                                            fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setCapitalBudgetId(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsCapitalBudget1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Capital Budget Request Rejected.");
                                        reset();
                                        break;
                                    case "Approve":
                                        for (FmsCapitalBudget1 fmsCapitalBudget1List1 : fmsCapitalBudget1List) {
                                            fmsCapitalBudget1List1.setStatus(Constants.CHECK_APPROVE_VALUE);
                                            fmsCapitalBudgetBeanLocal.edit(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setCapitalBudgetId(fmsCapitalBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsCapitalBudget1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Capital Budget Request Checked.");
                                        reset();
                                        break;
                                }
                            }
                            break;
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
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
        }
    }

    public void recreateSearchDModel() {
        double x = 0;
        for (int i = 0; i < fmsCapitalBudget1List.size(); i++) {
            x += fmsCapitalBudget1List.get(i).getTotalReqAmount().doubleValue();
        }
        setTotalRequestedAmount(new BigDecimal(x));
        fmsCapitalBudgetDetailSrcmodel = null;
        fmsCapitalBudgetDetailSrcmodel = new ListDataModel(fmsCapitalBudget1List);
    }

    public void searchPopulate(SelectEvent event) {
        capitalBudgetTasks = (FmsCapitalBudgetTasks) event.getObject();
        fmsLuSystem = capitalBudgetTasks.getCBDetailFk().getCapitalBudgetId().getCcSsJunction().getFmsLuSystem();
        fmsCostCenter = capitalBudgetTasks.getCBDetailFk().getCapitalBudgetId().getCcSsJunction().getFmsCostCenter();
        fmsLuBudgetYear = capitalBudgetTasks.getCBDetailFk().getCapitalBudgetId().getBudgetYear();

        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        buttonRenderd = true;

        recreateDataModel();

        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        reqheaderTitle = "Capital Budget Approval Information";
        buttonRenderd = true;
        approveRenderd = false;
        saveOrUpdate = "Edit";
        setIcone("ui-icon-search");
    }

    public void detailPopulate(SelectEvent event) {
        fmsCapitalBudgetDetail = (FmsCapitalBudgetDetail) event.getObject();
        detailPopulate = 1;
    }
    //</editor-fold>
    
    

}
