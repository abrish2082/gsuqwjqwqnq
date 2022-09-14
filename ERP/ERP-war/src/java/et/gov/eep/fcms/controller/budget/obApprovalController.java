package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="sampl">
//</editor-fold>

import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
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
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
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

/**
 *
 * @author Me
 */
@Named(value = "obApprovalController")
@ViewScoped
public class obApprovalController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;
    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1;
    @Inject
    FmsOperatingBudgetTasks fmsOperatingBudgetTasks;
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
    FmsCostcSystemJunction fmsCostcSystemJunction;
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
    FMSOBTasksBeanLocal oBTasksBeanLocal;
    @EJB
    FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @EJB
    budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="varial declaration">
    Constants constants;
    DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsOperatingBudget1> fmsOperatingBudget1List = new ArrayList<>();
    DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel;
    DataModel<FmsOperatingBudgetTasks> fmsOperatingBudgettaskmodel;
    DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel;
    FmsOperatingBudget1 operatingBudgetSelection;
    FmsOperatingBudgetTasks operatingBudgetTaskSelection;
    FmsOperatingBudgetDetail operatingBudgetDetailSelection;
    List<FmsOperatingBudget1> requestList = new ArrayList<>();
    List<FmsOperatingBudget1> obrequestList = new ArrayList<>();
    List<FmsOperatingBudgetTasks> obtaskList = new ArrayList<>();
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
    private String reqheaderTitle = "Operating Budget Approval";
    private String saveOrUpdate = "Create";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
     public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
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

    public FmsOperatingBudget1 getOperatingBudgetSelection() {
        return operatingBudgetSelection;
    }

    public void setOperatingBudgetSelection(FmsOperatingBudget1 operatingBudgetSelection) {
        this.operatingBudgetSelection = operatingBudgetSelection;
    }

    public FmsOperatingBudgetTasks getOperatingBudgetTaskSelection() {
        return operatingBudgetTaskSelection;
    }

    public void setOperatingBudgetTaskSelection(FmsOperatingBudgetTasks operatingBudgetTaskSelection) {
        this.operatingBudgetTaskSelection = operatingBudgetTaskSelection;
    }

    public FmsOperatingBudgetDetail getOperatingBudgetDetailSelection() {
        return operatingBudgetDetailSelection;
    }

    public void setOperatingBudgetDetailSelection(FmsOperatingBudgetDetail operatingBudgetDetailSelection) {
        this.operatingBudgetDetailSelection = operatingBudgetDetailSelection;
    }

    public FmsOperatingBudgetTasks getFmsOperatingBudgetTasks() {
        return fmsOperatingBudgetTasks;
    }

    public void setFmsOperatingBudgetTasks(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        this.fmsOperatingBudgetTasks = fmsOperatingBudgetTasks;
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
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
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

    public Constants getConstants() {
        return constants;
    }

    public void setConstants(Constants constants) {
        this.constants = constants;
    }
    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public List<FmsOperatingBudgetTasks> getObtaskList() {
        return obtaskList;
    }

    public void setObtaskList(List<FmsOperatingBudgetTasks> obtaskList) {
        this.obtaskList = obtaskList;
    }

    public List<FmsOperatingBudget1> getObrequestList() {
        if (obrequestList == null) {
            obrequestList = new ArrayList<>();
        }
        return obrequestList;
    }

    public void setObrequestList(List<FmsOperatingBudget1> obrequestList) {
        this.obrequestList = obrequestList;
    }

    public List<FmsOperatingBudget1> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<FmsOperatingBudget1> requestList) {
        this.requestList = requestList;
    }

    public Integer getRequestNotificationCounter() {
        requestList = fmsOperatingBudgetBeanLocal.findRequestForApproval();
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

    public DataModel<FmsOperatingBudgetTasks> getFmsOperatingBudgettaskmodel() {
        return fmsOperatingBudgettaskmodel;
    }

    public void setFmsOperatingBudgettaskmodel(DataModel<FmsOperatingBudgetTasks> fmsOperatingBudgettaskmodel) {
        this.fmsOperatingBudgettaskmodel = fmsOperatingBudgettaskmodel;
    }

    public List<FmsOperatingBudget1> getFmsOperatingBudget1List() {
        if (fmsOperatingBudget1List == null) {
            fmsOperatingBudget1List = new ArrayList<>();
        }
        return fmsOperatingBudget1List;
    }

    public void setFmsOperatingBudget1List(List<FmsOperatingBudget1> fmsOperatingBudget1List) {
        this.fmsOperatingBudget1List = fmsOperatingBudget1List;
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
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>

    

   

    public obApprovalController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }

    public void createNewOBApprovalView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Operating Budget Approval";
            buttonRenderd = true;
            approveRenderd = false;
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Operating Budget Approval";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
        }
        resetOBApproval();
        recreateWFDataModel();
    }

    public void recreateWFDataModel() {
        wfFcmsProcessedDataModel = null;
        for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
            wfFcmsProcessedList = fmsOperatingBudget1List.get(i).getWfFcmsProcessedList();
        }
        wfFcmsProcessedDataModel = new ListDataModel(new ArrayList(wfFcmsProcessedList));
    }

    public void requestCounter() {
        requestNotificationCounter = requestList.size();
    }

    public void addOBDetail() {
        fmsOperatingBudget1.addOperatingBudgetDetail(fmsOperatingBudgetDetail);
        recreateDataModel();
    }

    public void recreateDataModel() {
        fmsOperatingBudgetDetailmodel = null;
        fmsOperatingBudgetDetailmodel = new ListDataModel(new ArrayList(fmsOperatingBudget1.getFmsOperatingBudgetDetailList()));
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
    }

    public void recreateTaskDataModel() {
        fmsOperatingBudgettaskmodel = null;
        fmsOperatingBudgettaskmodel = new ListDataModel(new ArrayList(obtaskList));
    }

    public void save() {

        if ("Requested".equals(workFlowLevel)) {
            if ("Reject".equals(decisionType)) {
                fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1);
                JsfUtil.addSuccessMessage("Operating Budget Request Rejected.");
                resetOBApproval();
            } else if ("Approve".equals(decisionType)) {
                fmsOperatingBudget1.setStatus(Constants.PREPARE_VALUE);
                fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1);
                JsfUtil.addSuccessMessage("Operating Budget Request Prepared.");
                resetOBApproval();
            }
        } else if ("Prepared".equals(workFlowLevel)) {
            if ("Reject".equals(decisionType)) {
                fmsOperatingBudget1.setStatus(Constants.REQUEST_VALUE);
                fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1);
                JsfUtil.addSuccessMessage("Operating Budget Request Rejected.");
                resetOBApproval();
            } else if ("Approve".equals(decisionType)) {
                fmsOperatingBudget1.setStatus(Constants.CHECK_APPROVE_VALUE);
                fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1);
                JsfUtil.addSuccessMessage("Operating Budget Request Checked.");
                resetOBApproval();
            }
        }
    }

    public void resetOBApproval() {
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
        decisionType = null;
        workFlowLevel = null;
        userRemarkDate = null;
        userRemark = null;
    }

    public void OBRequestListChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            String reqCode = event.getNewValue().toString();
            fmsOperatingBudget1.setRequestCode(reqCode);
            fmsOperatingBudget1 = fmsOperatingBudgetBeanLocal.findByRequestCodeOnRequest(fmsOperatingBudget1);
            System.out.println("change " + fmsOperatingBudget1.getOperatingBudgetId());
            obtaskList = oBTasksBeanLocal.findByOBudgetReqCode(fmsOperatingBudget1);
            FmsLuBudgetYear budgetYear = new FmsLuBudgetYear();
            FmsLuSystem luSystem = new FmsLuSystem();
            FmsCostCenter costCenter = new FmsCostCenter();
            FmsCostcSystemJunction ccssJunction;
            budgetYear = fmsOperatingBudget1.getBudgetYear();
            luSystem = fmsOperatingBudget1.getCcSsJunction().getFmsLuSystem();
            costCenter = fmsOperatingBudget1.getCcSsJunction().getFmsCostCenter();
            ccssJunction = fmsOperatingBudget1.getCcSsJunction();
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearSystem(budgetYear, ccssJunction);
            recreateSearchDModel();
            recreateWFDataModel();
            fmsLuBudgetYear = budgetYear;
            fmsLuSystem = ccssJunction.getFmsLuSystem();
            decisionChange();
            statusChange();
            recreateTaskDataModel();

        }
    }

    public void RequestList(ValueChangeEvent event) {
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
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearSystem(budgetYear, ccssJunction);
            System.out.println("1. starting");
            recreateSearchDModel();
            System.out.println("2. starting");
            recreateWFDataModel();
            fmsLuBudgetYear = budgetYear;
            fmsLuSystem = ccssJunction.getFmsLuSystem();
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
            if (!fmsOperatingBudget1List.isEmpty()) {
                for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
                    if (fmsOperatingBudget1List.get(i).getStatus() == 17 ^ fmsOperatingBudget1List.get(i).getStatus() == 18) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Requested");
                        workFlowLevel = "Requested";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 0 ^ fmsOperatingBudget1List.get(i).getStatus() == 2 ^ fmsOperatingBudget1List.get(i).getStatus() == 4) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Prepared");
                        workFlowLevel = "Prepared";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 1) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Checked");
                        workFlowLevel = "Checked";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 3 ^ fmsOperatingBudget1List.get(i).getStatus() == 11) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Approved");
                        workFlowLevel = "Approved";
                    } else if (fmsOperatingBudget1List.get(i).getStatus() == 10) {
                        fmsOperatingBudget1List.get(i).setChangedStatus("Authorized");
                        workFlowLevel = "Authorized";
                    }
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
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
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }

    public List<FmsOperatingBudget1> getOBRequestList() {
        obrequestList = fmsOperatingBudgetBeanLocal.findRequestForApproval();
        return obrequestList;
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
        if (workFlowLevel.equalsIgnoreCase("Requested")) {
            if (fmsLuSystem.getSystemCode() == null && fmsLuBudgetYear.getBudgetYear() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findAllRequest(SessionBean.getUserId());
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYear(fmsLuBudgetYear);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() != null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndSystem(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsCostCenter.getSystemName() != null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenter(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() == null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystem(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAndCostCenter(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByRequestCode(fmsOperatingBudget1);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            }
        } else if (workFlowLevel.equalsIgnoreCase("Prepared")) {
            if (fmsLuSystem.getSystemCode() == null && fmsLuBudgetYear.getBudgetYear() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findAllRequestPrepared();
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearPrepared(fmsLuBudgetYear);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() != null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndSystemPrepared(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsCostCenter.getSystemName() != null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenterPrepared(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() == null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemPrepared(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAndCostCenterPrepared(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByRequestCodePrepared(fmsOperatingBudget1);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            }
        } else if (workFlowLevel.equalsIgnoreCase("Checked")) {
            if (fmsLuSystem.getSystemCode() == null && fmsLuBudgetYear.getBudgetYear() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findAllRequestChecked();
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearChecked(fmsLuBudgetYear);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() != null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndSystemChecked(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsCostCenter.getSystemName() != null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenterChecked(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() == null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemChecked(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAndCostCenterChecked(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByRequestCodeChecked(fmsOperatingBudget1);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            }
        } else if (workFlowLevel.equalsIgnoreCase("Approved")) {
            if (fmsLuSystem.getSystemCode() == null && fmsLuBudgetYear.getBudgetYear() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findAllRequestApproved();
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearApproved(fmsLuBudgetYear);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() != null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndSystemApproved(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsCostCenter.getSystemName() != null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenterApproved(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() == null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemApproved(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAndCostCenterApproved(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByRequestCodeApproved(fmsOperatingBudget1);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            }
        } else if (workFlowLevel.equalsIgnoreCase("Authorized")) {
            if (fmsLuSystem.getSystemCode() == null && fmsLuBudgetYear.getBudgetYear() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findAllRequestAuthorized();
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() == null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAuthorized(fmsLuBudgetYear);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsLuSystem.getSystemCode() != null && fmsCostCenter.getSystemName() == null && fmsOperatingBudget1.getRequestCode() == null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndSystemAuthorized(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuBudgetYear.getBudgetYear() != null && fmsCostCenter.getSystemName() != null && fmsOperatingBudget1.getRequestCode() == null) {
//                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenterAuthorized(fmsLuBudgetYear, fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() == null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAuthorized(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
//                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findBySystemAndCostCenterAuthorized(fmsCostCenter);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            } else if (fmsLuSystem.getSystemCode() != null || fmsLuBudgetYear.getBudgetYear() != null || fmsCostCenter.getSystemName() != null || fmsOperatingBudget1.getRequestCode() != null) {
                fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.findByRequestCodeAuthorized(fmsOperatingBudget1);
                fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);
            }
        }
    }

    public void searchOBapproval() {
        if (workFlowLevel.equalsIgnoreCase("Requested")) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.fetchByOBforProcessOnReq(fmsLuBudgetYear, fmsCostCenter);

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
        if (workFlowLevel.equalsIgnoreCase("Prepared")) {
            fmsOperatingBudget1List = fmsOperatingBudgetBeanLocal.fetchByOBforProcessPrepared(fmsLuBudgetYear, fmsCostCenter);

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

    public void saveOBApproval() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveOBApproval", dataset)) {
                if (null != workFlowLevel) {
                    switch (workFlowLevel) {
                        case "Requested":
                            if (null != decisionType) {
                                switch (decisionType) {
                                    case "Reject":
                                        for (FmsOperatingBudget1 fmsOperatingBudget1List1 : fmsOperatingBudget1List) {
                                            fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.PREPARE_REJECT_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Operating Budget Request Rejected.");
                                        resetOBApproval();
                                        break;
                                    case "Approve":
                                        for (FmsOperatingBudget1 fmsOperatingBudget1List1 : fmsOperatingBudget1List) {
                                            fmsOperatingBudget1List1.setStatus(Constants.PREPARE_VALUE);
                                            fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Operating Budget Request Prepared.");
                                        resetOBApproval();
                                        break;
                                }
                            }
                            break;
                        case "Prepared":
                            if (null != decisionType) {
                                switch (decisionType) {
                                    case "Reject":
                                        for (FmsOperatingBudget1 fmsOperatingBudget1List1 : fmsOperatingBudget1List) {
                                            fmsOperatingBudget1List1.setStatus(Constants.CHECK_REJECT_VALUE);
                                            fmsOperatingBudget1List1.setRemark(userRemark);
                                            fmsOperatingBudget1List1.setRequestDate(userRemarkDate);
                                            fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.CHECK_REJECT_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Operating Budget Request Rejected.");
                                        resetOBApproval();
                                        break;
                                    case "Approve":
                                        for (FmsOperatingBudget1 fmsOperatingBudget1List1 : fmsOperatingBudget1List) {
                                            fmsOperatingBudget1List1.setStatus(Constants.CHECK_APPROVE_VALUE);
                                            fmsOperatingBudgetBeanLocal.edit(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setOperatingBudgetId(fmsOperatingBudget1List1);
                                            wfFcmsProcessed.setProcessedOn(fmsOperatingBudget1List1.getRequestDate());
                                            wfFcmsProcessed.setCommentGiven(userRemark);
                                            wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                            wfFcmsProcessed.setDecision(Constants.CHECK_APPROVE_VALUE);
                                            wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                        }
                                        JsfUtil.addSuccessMessage("Operating Budget Request Checked.");
                                        resetOBApproval();
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

                security.addEventLog(eventEntry, dataset);

            }
        } catch (Exception ex) {
        }
    }

    public void recreateSearchDModel() {
        System.out.println("total amount" + fmsOperatingBudget1List.get(0).getTotalReqAmount().doubleValue());
        double x = 0;
        for (int i = 0; i < fmsOperatingBudget1List.size(); i++) {
            x += fmsOperatingBudget1List.get(i).getTotalReqAmount().doubleValue();
        }
        setTotalRequestedAmount(new BigDecimal(x));
        fmsOperatingBudgetDetailSrcmodel = null;
        fmsOperatingBudgetDetailSrcmodel = new ListDataModel(fmsOperatingBudget1List);

    }

    public void searchPopulate(SelectEvent event) {
        fmsOperatingBudgetTasks = (FmsOperatingBudgetTasks) event.getObject();
        recreateDataModel();

        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        buttonRenderd = true;
        fmsLuBudgetYear = fmsOperatingBudget1.getBudgetYear();

        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        reqheaderTitle = "Operating Budget Approval";
        buttonRenderd = true;
        approveRenderd = false;
        saveOrUpdate = "Edit";
        setIcone("ui-icon-search");
    }

    public void detailPopulate(SelectEvent event) {
        fmsOperatingBudgetDetail = (FmsOperatingBudgetDetail) event.getObject();
        detailPopulate = 1;
    }

}
