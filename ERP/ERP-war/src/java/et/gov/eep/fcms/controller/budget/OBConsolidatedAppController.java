
package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FMSOBTasksBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

@Named(value = "oBConsolidatedAppController")
@ViewScoped
public class OBConsolidatedAppController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;

    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetail;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsObDisbursement fmsOBDisbursement;
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
    FmsOperatingBudgetBeanLocal fmsOperatingBudgetBeanLocal;
    @EJB
    FmsOperatingBudgetDetailBeanLocal fmsOperatingBudgetDetailBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsOBDisbursementBeanLocal fmsOBDisbursementBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    FMSOBTasksBeanLocal fMSOBTasksBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    Constants constants;
    DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsOperatingBudget1> fmsOperatingBudget1List = new ArrayList<>();
    DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel;
    DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel;
    FmsOperatingBudget1 operatingBudgetSelection;
    FmsOperatingBudgetDetail operatingBudgetDetailSelection;
    List<FmsOperatingBudget1> requestList = new ArrayList<>();
    List<WfFcmsProcessed> wfFcmsProcessedList = new ArrayList<>();

    int detailPopulate = 0;
    String workFlowLevel = "";
    private String decisionType;
    private NumberConverter numberConverter = new NumberConverter();
    BigDecimal totalRequestedAmount = new BigDecimal(BigInteger.ZERO);
    boolean decision = true;
    String userRemark = "";
    String userRemarkDate = "";
    Integer requestNotificationCounter = 0;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Operating Budget Consolidated Approval";
    private String saveOrUpdate = "Create";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public List<FmsOperatingBudget1> getOBRequestList() {
        return fmsOperatingBudgetBeanLocal.findRequestForConsApproval();
    }

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public Constants getConstants() {
        return constants;
    }

    public List<FmsOperatingBudget1> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<FmsOperatingBudget1> requestList) {
        this.requestList = requestList;
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

    public Integer getRequestNotificationCounter() {
        requestList = fmsOperatingBudgetBeanLocal.findRequestForConsApproval();
        requestNotificationCounter = requestList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
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

    public boolean isDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public String getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(String decisionType) {
        this.decisionType = decisionType;
    }

    public FmsOperatingBudget1 getOperatingBudgetSelection() {
        return operatingBudgetSelection;
    }

    public void setOperatingBudgetSelection(FmsOperatingBudget1 operatingBudgetSelection) {
        this.operatingBudgetSelection = operatingBudgetSelection;
    }

    public FmsOperatingBudgetDetail getOperatingBudgetDetailSelection() {
        return operatingBudgetDetailSelection;
    }

    public void setOperatingBudgetDetailSelection(FmsOperatingBudgetDetail operatingBudgetDetailSelection) {
        this.operatingBudgetDetailSelection = operatingBudgetDetailSelection;
    }

    public DataModel<FmsOperatingBudgetDetail> getFmsOperatingBudgetDetailmodel() {
        return fmsOperatingBudgetDetailmodel;
    }

    public void setFmsOperatingBudgetDetailmodel(DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel) {
        this.fmsOperatingBudgetDetailmodel = fmsOperatingBudgetDetailmodel;
    }

    public DataModel<FmsOperatingBudget1> getFmsOperatingBudgetDetailSrcmodel() {
        return fmsOperatingBudgetDetailSrcmodel;
    }

    public void setFmsOperatingBudgetDetailSrcmodel(DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel) {
        this.fmsOperatingBudgetDetailSrcmodel = fmsOperatingBudgetDetailSrcmodel;
    }

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetail() {
        if (fmsOperatingBudgetDetail == null) {
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        }
        return fmsOperatingBudgetDetail;
    }

    public void setFmsOperatingBudgetDetail(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        this.fmsOperatingBudgetDetail = fmsOperatingBudgetDetail;
    }

    public FmsObDisbursement getFmsOBDisbursement() {
        return fmsOBDisbursement;
    }

    public void setFmsOBDisbursement(FmsObDisbursement fmsOBDisbursement) {
        this.fmsOBDisbursement = fmsOBDisbursement;
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

    public FmsOperatingBudget1 getFmsOperatingBudget1() {
        if (fmsOperatingBudget1 == null) {
            fmsOperatingBudget1 = new FmsOperatingBudget1();
        }
        return fmsOperatingBudget1;
    }

    public void setFmsOperatingBudget1(FmsOperatingBudget1 fmsOperatingBudget1) {
        this.fmsOperatingBudget1 = fmsOperatingBudget1;
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
    //<editor-fold defaultstate="collapsed" desc="OBConsolidatedAppController">
    public OBConsolidatedAppController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="createNewOBConsView">
    public void createNewOBConsView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Operating Budget Consolidated Approval";
            buttonRenderd = true;
            approveRenderd = false;
            setIcone("ui-icon-search");
            reset();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Operating Budget Consolidated Approval";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
            reset();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateWFDataModel">
    public void recreateWFDataModel() {
        wfFcmsProcessedDataModel = null;
        for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
            wfFcmsProcessedList = fmsOperatingBudget1List.get(i).getWfFcmsProcessedList();
        }
        wfFcmsProcessedDataModel = new ListDataModel(new ArrayList(wfFcmsProcessedList));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestListChange">
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
            costCenter = fmsCostCenterBeanLocal.getCCDetail(costCenter);
            ccssJunction = fmsCostCSystemJunctionBeanLocal.findByCCandSS(luSystem, costCenter);
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearSystemCons(budgetYear, ccssJunction);
            recreateSearchDModel();
            recreateWFDataModel();
            fmsLuBudgetYear = budgetYear;
            fmsLuSystem = luSystem;
            statusChange();
            decisionChange();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="decisionChange">
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="statusChange">
    public void statusChange() {
        try {
            if (!fmsOperatingBudget1List.isEmpty()) {
                for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                    if (fmsOperatingBudget1List.get(i).getStatus() == 17 ^ fmsOperatingBudget1List.get(i).getStatus() == 18) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Requested");
                        workFlowLevel = "Requested";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 0 ^ fmsOperatingBudget1List.get(i).getStatus() == 2) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Prepared");
                        workFlowLevel = "Prepared";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 1 ^ fmsOperatingBudget1List.get(i).getStatus() == 4) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Checked");
                        workFlowLevel = "Checked";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 3 ^ fmsOperatingBudget1List.get(i).getStatus() == 11) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Approved");
                        workFlowLevel = "Approved";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 10) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Authorized");
                        workFlowLevel = "Authorized";
                        decision = false;
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="addOBDetail">
    public void addOBDetail() {
        fmsOperatingBudget1.addOperatingBudgetDetail(fmsOperatingBudgetDetail);
        recreateDataModel();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateDataModel">
    public void recreateDataModel() {
        fmsOperatingBudgetDetailmodel = null;
        fmsOperatingBudgetDetailmodel = new ListDataModel(new ArrayList(fmsOperatingBudget1.getFmsOperatingBudgetDetailList()));
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="reset">
    public void reset() {
        fmsOperatingBudget1 = new FmsOperatingBudget1();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        fmsCostCenter = new FmsCostCenter();
        fmsLuSystem = new FmsLuSystem();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsOperatingBudgetDetailmodel = new ArrayDataModel<>();
        fmsOperatingBudgetDetailSrcmodel = new ArrayDataModel<>();
        wfFcmsProcessed = new WfFcmsProcessed();
        wfFcmsProcessedDataModel = new ArrayDataModel<>();
        wfFcmsProcessedList = new ArrayList<>();
        totalRequestedAmount = null;
        decisionType = null;
        workFlowLevel = null;
        userRemarkDate = null;
        userRemark = null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getLuBudgetYearSearchList">
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchYearValueChange">
    public void searchYearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SystemSearchChange">
    public void SystemSearchChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getCostCenterSearch">

    public SelectItem[] getCostCenterSearch() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchOBConsForProcess">
    public void searchOBConsForProcess() {
        if (workFlowLevel.equalsIgnoreCase("Checked")) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.fetchByOBsChecked(fmsLuBudgetYear, fmsCostCenter);
            if (!fmsOperatingBudget1List.isEmpty()) {
                recreateSearchDModel();
                recreateWFDataModel();
            } else {
                JsfUtil.addFatalMessage("No Result Found.");
                totalRequestedAmount = null;
                recreateSearchDModel();
                recreateWFDataModel();
            }
        }
        if (workFlowLevel.equalsIgnoreCase("Approved")) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.fetchByOBsApproved(fmsLuBudgetYear, fmsCostCenter);
            if (!fmsOperatingBudget1List.isEmpty()) {
                double x = 0;
                for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                    x += fmsOperatingBudget1List.get(i).getTotalReqAmount().doubleValue();
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
        if (workFlowLevel.equalsIgnoreCase("Authorized")) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.fetchByOBsAuthorized(fmsLuBudgetYear, fmsCostCenter);
            if (!fmsOperatingBudget1List.isEmpty()) {
                double x = 0;
                for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                    x += fmsOperatingBudget1List.get(i).getTotalReqAmount().doubleValue();
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
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="saveOBCons">
    public void saveOBCons() {
        System.out.println("Hello");
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveOBCons", dataset)) {
                if (null != workFlowLevel) {
                    switch (workFlowLevel) {
                        case "Checked":
                            if (null != decisionType) {
                                switch (decisionType) {
                                    case "Reject":
                                        for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                                            fmsOperatingBudget1List.get(i).setStatus(Constants.APPROVE_REJECT_VALUE);
                                            fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1List.get(i));
                                            wfFcmsProcessed = new WfFcmsProcessed();
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List.get(i));
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List.get(i).getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Operating Budget Request Rejected.");
                                        reset();
                                        break;
                                    case "Approve":
                                        for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                                            fmsOperatingBudget1List.get(i).setStatus(Constants.APPROVE_VALUE);
                                            fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1List.get(i));
                                            wfFcmsProcessed = new WfFcmsProcessed();
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List.get(i));
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List.get(i).getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.APPROVE_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Operating Budget Request Checked.");
                                        reset();
                                        break;
                                }
                            }
                            break;
                        case "Approved":
                            if (null != decisionType) {
                                switch (decisionType) {
                                    case "Reject":
                                        for (FmsOperatingBudget1 fmsOperatingBudget1List1 : fmsOperatingBudget1List) {
                                            fmsOperatingBudget1List1.setStatus(Constants.AUTHORIZED_REJECT_VALUE);
                                            fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1List1);
                                            wfFcmsProcessed = new WfFcmsProcessed();
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.AUTHORIZED_REJECT_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Operating Budget Request Rejected.");
                                        reset();
                                        break;
                                    case "Approve":
                                        for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                                            fmsOperatingBudget1List.get(i).setStatus(Constants.AUTHORIZED);
                                            for (int j = 0; j < fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().size(); j++) {
                                                for (int k = 0; k < fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().size(); k++) {
                                                    fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).setApprovedAmount(fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getRequestedAmount());
                                                    fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).setRemainingBalance(fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getRequestedAmount());

                                                    fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().get(k).setApprovedAmount(fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().get(k).getAllotedAmount());
                                                    fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().get(k).setRemainingAmount(fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().get(k).getAllotedAmount());

                                                    BigDecimal equalDisbursment;
                                                    equalDisbursment = fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().get(k).getAllotedAmount().divide(new BigDecimal(12), 2, RoundingMode.HALF_EVEN);
                                                    fmsOBDisbursement = new FmsObDisbursement();
                                                    fmsOBDisbursement.setObTaskId(fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().get(k));
                                                    fmsOBDisbursement.setHamle(equalDisbursment);
                                                    fmsOBDisbursement.setNehasie(equalDisbursment);
                                                    fmsOBDisbursement.setMeskerem(equalDisbursment);
                                                    fmsOBDisbursement.setTikemt(equalDisbursment);
                                                    fmsOBDisbursement.setHidar(equalDisbursment);
                                                    fmsOBDisbursement.setTahsas(equalDisbursment);
                                                    fmsOBDisbursement.setTir(equalDisbursment);
                                                    fmsOBDisbursement.setYekatit(equalDisbursment);
                                                    fmsOBDisbursement.setMegabit(equalDisbursment);
                                                    fmsOBDisbursement.setMiyaziya(equalDisbursment);
                                                    fmsOBDisbursement.setGinbot(equalDisbursment);
                                                    fmsOBDisbursement.setSene(equalDisbursment);
                                                    fmsOBDisbursementBeanLocal.create(fmsOBDisbursement);
                                                    fMSOBTasksBeanLocal.edit(fmsOperatingBudget1List.get(i).getFmsOperatingBudgetDetailList().get(j).getFmsOperatingBudgetTasksList().get(k));
                                                }
                                            }
                                            fmsOperatingBudgetBeanLocal.saveOrUpdate(fmsOperatingBudget1List.get(i));
                                            wfFcmsProcessed = new WfFcmsProcessed();
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List.get(i));
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List.get(i).getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.AUTHORIZED);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }

                                        JsfUtil.addSuccessMessage("Operating Budget Request Authorised.");
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateSearchDModel">
    public void recreateSearchDModel() {
        double x = 0;
        for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
            x += fmsOperatingBudget1List.get(i).getTotalReqAmount().doubleValue();
        }
        setTotalRequestedAmount(new BigDecimal(x));
        fmsOperatingBudgetDetailSrcmodel = null;
        fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchPopulate">

    public void searchPopulate(SelectEvent event) {
        fmsOperatingBudget1 = (FmsOperatingBudget1) event.getObject();
        fmsOperatingBudget1.setOperatingBudgetId(fmsOperatingBudget1.getOperatingBudgetId());
        recreateDataModel();
        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        buttonRenderd = true;
//        fmsLuSystem = fmsOperatingBudget1.getCostCenter().getSystemId();
//        fmsCostCenter = fmsOperatingBudget1.getCostCenter();
        fmsLuBudgetYear = fmsOperatingBudget1.getBudgetYear();

        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        reqheaderTitle = "Operating Budget Consolidated Approval";
        buttonRenderd = true;
        approveRenderd = false;
        saveOrUpdate = "Edit";
        setIcone("ui-icon-search");
    }
    //</editor-fold>
    
  
}
