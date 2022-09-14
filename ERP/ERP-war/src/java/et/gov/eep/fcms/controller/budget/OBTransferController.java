package et.gov.eep.fcms.controller.budget;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsLuBudgetYearBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FMSOBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOBTransferDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsObTransferDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

@Named(value = "oBTransferController")
@ViewScoped
public class OBTransferController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;
    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1;
    @Inject
    FmsOperatingBudget1 fmsOperatingBudget1TO;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetail;
    @Inject
    FmsOperatingBudgetDetail fmsOperatingBudgetDetailTO;
    @Inject
    FmsOperatingBudgetDetail fmsOBDetail;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunctionTo;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunctionFrm;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsCostCenter fmsCostCenterTO;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsLuSystem fmsLuSystemTO;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYearTO;
    @Inject
    FmsGeneralLedger fmsGeneralLedgerSrc;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsGeneralLedger fmsGeneralLedgerTO;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedgerSrc;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedgerTo;
    @Inject
    FmsOperatingBudgetTasks fmsOperatingBudgetTasksSrc;
    @Inject
    FmsOperatingBudgetTasks fmsOperatingBudgetTasks;
    @Inject
    FmsOperatingBudgetTasks fmsOperatingBudgetTasksTo;
    @Inject
    FmsObTransferDetail fmsObTransferDetail;
    @Inject
    FmsObDisbursement obDisbursement;
    @Inject
    FmsObDisbursement obDisbursementTo;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    FmsOBDisbursementBeanLocal oBDisbursementBeanLocal;
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    FmsOBTransferDetailBeanLocal transferDetailBeanLocal;
    @EJB
    subsidiaryBeanLocal subsbeanLocal;
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
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    FMSOBTasksBeanLocal fMSOBTasksBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    FmsOperatingBudgetDetail budgetDetail;
    Constants constants;
    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsOperatingBudget1> fmsOperatingBudget1List = new ArrayList<>();
    List<FmsObDisbursement> fmsObDisbursementList = new ArrayList<>();
    List<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailList = new ArrayList<>();
    List<FmsOperatingBudgetTasks> fmsOperatingBudgetTaskList = new ArrayList<>();
    List<FmsOperatingBudgetTasks> fmsOperatingBudgetTaskListSrc = new ArrayList<>();
    List<FmsObTransferDetail> fmsObTransferDetailsList = new ArrayList<>();
    List<FmsOperatingBudgetTasks> fmsOperatingBudgetTaskListTO = new ArrayList<>();
    List<FmsCostCenter> costCenterList;
    List<FmsCostCenter> costCenterListTo;
    List<FmsObTransferDetail> requestList = new ArrayList<>();
    List<WfFcmsProcessed> wfFcmsProcessedList = new ArrayList<>();

    String userRemark = "";
    String userRemarkDate = "";
    private String decisionType;

    Integer requestNotificationCounter = 0;
    private NumberConverter numberConverter = new NumberConverter();
    BigDecimal transferAmount = BigDecimal.ZERO;
    BigDecimal newAmountFrom = BigDecimal.ZERO;
    BigDecimal newAmountTO = BigDecimal.ZERO;

    DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    DataModel<FmsObDisbursement> fmsObDisbursementmodel;
    DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel;
    DataModel<FmsObTransferDetail> fmsOperatingBudgetTransfermodel;
    DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel;
    FmsOperatingBudget1 operatingBudgetSelection;
    FmsObDisbursement obDisbursementSelection;
    FmsObTransferDetail obTransferDetailSelection;

    boolean newAmtReadonly = true;
    boolean drpDisable = false;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Operating Budget Transfer";
    private String saveOrUpdate = "Create";
    private boolean renderNewSrcBtn;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public void setBudgetDetail(FmsOperatingBudgetDetail budgetDetail) {
        this.budgetDetail = budgetDetail;
    }

    public boolean isRenderNewSrcBtn() {
        return renderNewSrcBtn;
    }

    public void setRenderNewSrcBtn(boolean renderNewSrcBtn) {
        this.renderNewSrcBtn = renderNewSrcBtn;
    }

    public Constants getConstants() {
        return constants;
    }

    public void setConstants(Constants constants) {
        this.constants = constants;
    }

    public boolean isNewAmtReadonly() {
        return newAmtReadonly;
    }

    public void setNewAmtReadonly(boolean newAmtReadonly) {
        this.newAmtReadonly = newAmtReadonly;
    }

    public boolean isDrpDisable() {
        return drpDisable;
    }

    public void setDrpDisable(boolean drpDisable) {
        this.drpDisable = drpDisable;
    }

    public List<FmsObTransferDetail> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<FmsObTransferDetail> requestList) {
        this.requestList = requestList;
    }

    public BigDecimal getNewAmountFrom() {
        return newAmountFrom;
    }

    public void setNewAmountFrom(BigDecimal newAmountFrom) {
        this.newAmountFrom = newAmountFrom;
    }

    public List<FmsCostCenter> getCostCenterList() {
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public List<FmsCostCenter> getCostCenterListTo() {
        return costCenterListTo;
    }

    public void setCostCenterListTo(List<FmsCostCenter> costCenterListTo) {
        this.costCenterListTo = costCenterListTo;
    }

    public BigDecimal getNewAmountTO() {
        return newAmountTO;
    }

    public void setNewAmountTO(BigDecimal newAmountTO) {
        this.newAmountTO = newAmountTO;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public FmsOperatingBudget1 getFmsOperatingBudget1() {
        return fmsOperatingBudget1;
    }

    public void setFmsOperatingBudget1(FmsOperatingBudget1 fmsOperatingBudget1) {
        this.fmsOperatingBudget1 = fmsOperatingBudget1;
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

    public String getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(String decisionType) {
        this.decisionType = decisionType;
    }

    public FmsObDisbursement getObDisbursement() {
        if (obDisbursement == null) {
            obDisbursement = new FmsObDisbursement();
        }
        return obDisbursement;
    }

    public void setObDisbursement(FmsObDisbursement obDisbursement) {
        this.obDisbursement = obDisbursement;
    }

    public FmsObDisbursement getObDisbursementTo() {
        if (obDisbursementTo == null) {
            obDisbursementTo = new FmsObDisbursement();
        }
        return obDisbursementTo;
    }

    public void setObDisbursementTo(FmsObDisbursement obDisbursementTo) {
        this.obDisbursementTo = obDisbursementTo;
    }

    public List<FmsOperatingBudgetDetail> getFmsOperatingBudgetDetailList() {
        if (fmsOperatingBudgetDetailList == null) {
            fmsOperatingBudgetDetailList = new ArrayList<>();
        }
        return fmsOperatingBudgetDetailList;
    }

    public void setFmsOperatingBudgetDetailList(List<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailList) {
        this.fmsOperatingBudgetDetailList = fmsOperatingBudgetDetailList;
    }

    public FmsOperatingBudgetTasks getFmsOperatingBudgetTasksSrc() {
        return fmsOperatingBudgetTasksSrc;
    }

    public void setFmsOperatingBudgetTasksSrc(FmsOperatingBudgetTasks fmsOperatingBudgetTasksSrc) {
        this.fmsOperatingBudgetTasksSrc = fmsOperatingBudgetTasksSrc;
    }

    public FmsOperatingBudgetTasks getFmsOperatingBudgetTasks() {
        return fmsOperatingBudgetTasks;
    }

    public void setFmsOperatingBudgetTasks(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        this.fmsOperatingBudgetTasks = fmsOperatingBudgetTasks;
    }

    public FmsOperatingBudgetTasks getFmsOperatingBudgetTasksTo() {
        return fmsOperatingBudgetTasksTo;
    }

    public void setFmsOperatingBudgetTasksTo(FmsOperatingBudgetTasks fmsOperatingBudgetTasksTo) {
        this.fmsOperatingBudgetTasksTo = fmsOperatingBudgetTasksTo;
    }

    public FmsObTransferDetail getFmsObTransferDetail() {
        if (fmsObTransferDetail == null) {
            fmsObTransferDetail = new FmsObTransferDetail();
        }
        return fmsObTransferDetail;
    }

    public void setFmsObTransferDetail(FmsObTransferDetail fmsObTransferDetail) {
        this.fmsObTransferDetail = fmsObTransferDetail;
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

    public List<FmsOperatingBudgetTasks> getFmsOperatingBudgetTaskList() {
        if (fmsOperatingBudgetTaskList == null) {
            fmsOperatingBudgetTaskList = new ArrayList<>();
        }
        return fmsOperatingBudgetTaskList;
    }

    public void setFmsOperatingBudgetTaskList(List<FmsOperatingBudgetTasks> fmsOperatingBudgetTaskList) {
        this.fmsOperatingBudgetTaskList = fmsOperatingBudgetTaskList;
    }

    public List<FmsOperatingBudgetTasks> getFmsOperatingBudgetTaskListSrc() {
        if (fmsOperatingBudgetTaskListSrc == null) {
            fmsOperatingBudgetTaskListSrc = new ArrayList<>();
        }
        return fmsOperatingBudgetTaskListSrc;
    }

    public void setFmsOperatingBudgetTaskListSrc(List<FmsOperatingBudgetTasks> fmsOperatingBudgetTaskListSrc) {
        this.fmsOperatingBudgetTaskListSrc = fmsOperatingBudgetTaskListSrc;
    }

    public List<FmsObTransferDetail> getFmsObTransferDetailsList() {
        if (fmsObTransferDetailsList == null) {
            fmsObTransferDetailsList = new ArrayList<>();
        }
        return fmsObTransferDetailsList;
    }

    public void setFmsObTransferDetailsList(List<FmsObTransferDetail> fmsObTransferDetailsList) {
        this.fmsObTransferDetailsList = fmsObTransferDetailsList;
    }

    public List<FmsOperatingBudgetTasks> getFmsOperatingBudgetTaskListTO() {
        if (fmsOperatingBudgetTaskListTO == null) {
            fmsOperatingBudgetTaskListTO = new ArrayList<>();
        }
        return fmsOperatingBudgetTaskListTO;
    }

    public void setFmsOperatingBudgetTaskListTO(List<FmsOperatingBudgetTasks> fmsOperatingBudgetTaskListTO) {
        this.fmsOperatingBudgetTaskListTO = fmsOperatingBudgetTaskListTO;
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

    public FmsOperatingBudgetDetail getFmsOBDetail() {
        return fmsOBDetail;
    }

    public void setFmsOBDetail(FmsOperatingBudgetDetail fmsOBDetail) {
        this.fmsOBDetail = fmsOBDetail;
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

    public FmsGeneralLedger getFmsGeneralLedgerSrc() {
        return fmsGeneralLedgerSrc;
    }

    public void setFmsGeneralLedgerSrc(FmsGeneralLedger fmsGeneralLedgerSrc) {
        this.fmsGeneralLedgerSrc = fmsGeneralLedgerSrc;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public FmsSubsidiaryLedger getFmsSubsidiaryLedgerSrc() {
        return fmsSubsidiaryLedgerSrc;
    }

    public void setFmsSubsidiaryLedgerSrc(FmsSubsidiaryLedger fmsSubsidiaryLedgerSrc) {
        this.fmsSubsidiaryLedgerSrc = fmsSubsidiaryLedgerSrc;
    }

    public FmsSubsidiaryLedger getFmsSubsidiaryLedgerTo() {
        return fmsSubsidiaryLedgerTo;
    }

    public void setFmsSubsidiaryLedgerTo(FmsSubsidiaryLedger fmsSubsidiaryLedgerTo) {
        this.fmsSubsidiaryLedgerTo = fmsSubsidiaryLedgerTo;
    }

    public FmsOperatingBudget1 getFmsOperatingBudget1TO() {
        return fmsOperatingBudget1TO;
    }

    public void setFmsOperatingBudget1TO(FmsOperatingBudget1 fmsOperatingBudget1TO) {
        this.fmsOperatingBudget1TO = fmsOperatingBudget1TO;
    }

    public FmsOperatingBudgetDetail getFmsOperatingBudgetDetailTO() {
        if (fmsOperatingBudgetDetailTO == null) {
            fmsOperatingBudgetDetailTO = new FmsOperatingBudgetDetail();
        }
        return fmsOperatingBudgetDetailTO;
    }

    public void setFmsOperatingBudgetDetailTO(FmsOperatingBudgetDetail fmsOperatingBudgetDetailTO) {
        this.fmsOperatingBudgetDetailTO = fmsOperatingBudgetDetailTO;
    }

    public FmsCostCenter getFmsCostCenterTO() {
        return fmsCostCenterTO;
    }

    public void setFmsCostCenterTO(FmsCostCenter fmsCostCenterTO) {
        this.fmsCostCenterTO = fmsCostCenterTO;
    }

    public FmsLuSystem getFmsLuSystemTO() {
        return fmsLuSystemTO;
    }

    public void setFmsLuSystemTO(FmsLuSystem fmsLuSystemTO) {
        this.fmsLuSystemTO = fmsLuSystemTO;
    }

    public FmsLuBudgetYear getFmsLuBudgetYearTO() {
        return fmsLuBudgetYearTO;
    }

    public void setFmsLuBudgetYearTO(FmsLuBudgetYear fmsLuBudgetYearTO) {
        this.fmsLuBudgetYearTO = fmsLuBudgetYearTO;
    }

    public FmsGeneralLedger getFmsGeneralLedgerTO() {
        return fmsGeneralLedgerTO;
    }

    public void setFmsGeneralLedgerTO(FmsGeneralLedger fmsGeneralLedgerTO) {
        this.fmsGeneralLedgerTO = fmsGeneralLedgerTO;
    }

    public budgetYearLookUpBeanLocal getBudgetYearLookUpBeanLocal() {
        return budgetYearLookUpBeanLocal;
    }

    public void setBudgetYearLookUpBeanLocal(budgetYearLookUpBeanLocal budgetYearLookUpBeanLocal) {
        this.budgetYearLookUpBeanLocal = budgetYearLookUpBeanLocal;
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

    public List<FmsOperatingBudget1> getFmsOperatingBudget1List() {
        return fmsOperatingBudget1List;
    }

    public void setFmsOperatingBudget1List(List<FmsOperatingBudget1> fmsOperatingBudget1List) {
        this.fmsOperatingBudget1List = fmsOperatingBudget1List;
    }

    public DataModel<FmsObDisbursement> getFmsObDisbursementmodel() {
        return fmsObDisbursementmodel;
    }

    public void setFmsObDisbursementmodel(DataModel<FmsObDisbursement> fmsObDisbursementmodel) {
        this.fmsObDisbursementmodel = fmsObDisbursementmodel;
    }

    public DataModel<FmsOperatingBudgetDetail> getFmsOperatingBudgetDetailmodel() {
        return fmsOperatingBudgetDetailmodel;
    }

    public void setFmsOperatingBudgetDetailmodel(DataModel<FmsOperatingBudgetDetail> fmsOperatingBudgetDetailmodel) {
        this.fmsOperatingBudgetDetailmodel = fmsOperatingBudgetDetailmodel;
    }

    public DataModel<FmsObTransferDetail> getFmsOperatingBudgetTransfermodel() {
        return fmsOperatingBudgetTransfermodel;
    }

    public void setFmsOperatingBudgetTransfermodel(DataModel<FmsObTransferDetail> fmsOperatingBudgetTransfermodel) {
        this.fmsOperatingBudgetTransfermodel = fmsOperatingBudgetTransfermodel;
    }

    public DataModel<FmsOperatingBudget1> getFmsOperatingBudgetDetailSrcmodel() {
        return fmsOperatingBudgetDetailSrcmodel;
    }

    public void setFmsOperatingBudgetDetailSrcmodel(DataModel<FmsOperatingBudget1> fmsOperatingBudgetDetailSrcmodel) {
        this.fmsOperatingBudgetDetailSrcmodel = fmsOperatingBudgetDetailSrcmodel;
    }

    public FmsOperatingBudget1 getOperatingBudgetSelection() {
        return operatingBudgetSelection;
    }

    public void setOperatingBudgetSelection(FmsOperatingBudget1 operatingBudgetSelection) {
        this.operatingBudgetSelection = operatingBudgetSelection;
    }

    public FmsObDisbursement getObDisbursementSelection() {
        return obDisbursementSelection;
    }

    public void setObDisbursementSelection(FmsObDisbursement obDisbursementSelection) {
        this.obDisbursementSelection = obDisbursementSelection;
    }

    public FmsObTransferDetail getObTransferDetailSelection() {
        return obTransferDetailSelection;
    }

    public void setObTransferDetailSelection(FmsObTransferDetail obTransferDetailSelection) {
        this.obTransferDetailSelection = obTransferDetailSelection;
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

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    public DataModel<WfFcmsProcessed> getWfFcmsProcessedDataModel() {
        return wfFcmsProcessedDataModel;
    }

    public void setWfFcmsProcessedDataModel(DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel) {
        this.wfFcmsProcessedDataModel = wfFcmsProcessedDataModel;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public Integer getRequestNotificationCounter() {
        requestList = transferDetailBeanLocal.findOBTransReqList();
        requestNotificationCounter = requestList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public List<FmsObTransferDetail> getOBTransRequestList() {
        return transferDetailBeanLocal.findOBTransReqList();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="ObTransListener">

    public void ObTransListener(ValueChangeEvent event) {
        FmsObTransferDetail obTrans = new FmsObTransferDetail();
        obTrans.setTransferDtlId(Integer.parseInt(event.getNewValue().toString()));
        obTrans = transferDetailBeanLocal.findByTrasferId(obTrans);
        fmsObTransferDetailsList.add(obTrans);
        recreateTaskDataModel();
        recreateWFDataModel();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateWFDataModel">

    public void recreateWFDataModel() {
        wfFcmsProcessedDataModel = null;
        for (int i = 0; i < fmsObTransferDetailsList.size(); i++) {
            wfFcmsProcessedList = fmsObTransferDetailsList.get(i).getWfFcmsProcessedList();
        }
        wfFcmsProcessedDataModel = new ListDataModel(new ArrayList(wfFcmsProcessedList));
        decisionChange();
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
    //<editor-fold defaultstate="collapsed" desc="createNewOBTransferView">

    public void createNewOBTransferView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            renderNewSrcBtn = false;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Operating Budget Transfer";
            buttonRenderd = true;
            approveRenderd = false;
            setIcone("ui-icon-search");
            resetOBTrans();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            renderNewSrcBtn = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Operating Budget Transfer";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
            resetOBTrans();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="yearValueChangeTo">

    public void yearValueChangeTo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYearTO.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYearTO = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYearTO);
            fmsOperatingBudget1TO.setBudgetYear(fmsLuBudgetYearTO);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="yearValueChange">

    public void yearValueChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYear.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYear = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYear);
            fmsOperatingBudget1.setBudgetYear(fmsLuBudgetYear);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getLuBudgetYearList">

    public SelectItem[] getLuBudgetYearList() {
        listLuBudgetYearList = fmsLuBudgetYearBeanLocal.getLuBudgetYear();
        listLuBudgetYearList.size();

        currPeriod = fmsAccountingPeriodBeanLocal.getCurretActivePeriod();
        nextPeriod = new FmsLuBudgetYear();
        budgetyrList = new ArrayList<>();
        for (int i = 0; i < listLuBudgetYearList.size(); i++) {
            if (Objects.equals(currPeriod.getLuBudgetYearId().getLuBudgetYearId(), listLuBudgetYearList.get(i).getLuBudgetYearId())) {
                nextPeriod = listLuBudgetYearList.get(i );
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SystemChange">

    public void SystemChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getCostCenterBySystemLU">

    public SelectItem[] getCostCenterBySystemLU() {
        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CostCenterChangeFrm">

    public void CostCenterChangeFrm(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestCodeChangeFrom">

    public void RequestCodeChangeFrom(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudget1.setRequestCode(event.getNewValue().toString());
            fmsOperatingBudget1 = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsOperatingBudget1);
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
            fmsOperatingBudgetDetail.setOperatingBudgetId(fmsOperatingBudget1);
            fmsOperatingBudgetDetailBeanLocal.fetchOBDetail(fmsOperatingBudget1);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GeneralLedgerFrom">

    public SelectItem[] GeneralLedgerFrom() {
        if (fmsOperatingBudget1 != null) {
            return JsfUtil.getSelectItems(fmsOperatingBudgetDetailBeanLocal.fetchGLfromOBDetail(fmsOperatingBudget1), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GeneralLedgerChangeFrm">
    public void GeneralLedgerChangeFrm(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedger);
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
            fmsOperatingBudgetDetail = fmsOperatingBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedger, fmsOperatingBudget1);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GeneralLedgerChangeTO">

    public void GeneralLedgerChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedgerTO.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedgerTO = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedgerTO);
            fmsOperatingBudgetDetailTO = new FmsOperatingBudgetDetail();
            fmsOperatingBudgetDetailTO = fmsOperatingBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedgerTO, fmsOperatingBudget1TO);
            fmsOperatingBudgetTaskListTO = fMSOBTasksBeanLocal.findByOBudgetDetailId(fmsOperatingBudgetDetailTO);
            budgetDetail = fmsOperatingBudgetDetailTO;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="resetOBTrans">

    public void resetOBTrans() {
        budgetDetail = new FmsOperatingBudgetDetail();
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
        fmsOperatingBudgetDetailTO = new FmsOperatingBudgetDetail();
        fmsCostCenter = new FmsCostCenter();
        fmsCostCenterTO = new FmsCostCenter();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsLuBudgetYearTO = new FmsLuBudgetYear();
        fmsLuSystem = new FmsLuSystem();
        fmsLuSystemTO = new FmsLuSystem();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsGeneralLedgerTO = new FmsGeneralLedger();
        fmsOperatingBudget1 = new FmsOperatingBudget1();
        fmsOperatingBudget1TO = new FmsOperatingBudget1();
        fmsOperatingBudgetTasks = new FmsOperatingBudgetTasks();
        fmsOperatingBudgetTasksTo = new FmsOperatingBudgetTasks();
        transferAmount = null;
        newAmountFrom = null;
        newAmountTO = null;
        fmsOperatingBudgetDetailmodel = new ArrayDataModel<>();
        fmsOperatingBudgetDetailSrcmodel = new ArrayDataModel<>();
        userRemark = null;
        userRemarkDate = null;
        decisionType = null;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SubsidiaryLedgerChangeFrm">

    public void SubsidiaryLedgerChangeFrm(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudgetTasks = (FmsOperatingBudgetTasks) event.getNewValue();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="OBTransferController">
    public OBTransferController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getSystemList">

    public SelectItem[] getSystemList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findOprSystem(), true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SubsidiaryLedgerChangeSrc">

    public void SubsidiaryLedgerChangeSrc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudgetTasksSrc = (FmsOperatingBudgetTasks) event.getNewValue();
            fmsObTransferDetailsList = transferDetailBeanLocal.fetchOBFromByOBDTasktlID(fmsOperatingBudgetTasksSrc);
            recreateTaskDataModel();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SubsidiaryLedgerChangeTO">

    public void SubsidiaryLedgerChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudgetTasksTo = (FmsOperatingBudgetTasks) event.getNewValue();
            fmsOperatingBudgetTasksTo = fMSOBTasksBeanLocal.findByOBDtlAndSL(fmsOperatingBudgetDetailTO, fmsOperatingBudgetTasksTo.getSlId());
            if (fmsOperatingBudgetTasksTo.getSlId() != fmsOperatingBudgetTasks.getSlId()) {
                newAmtReadonly = false;
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GeneralLedgerChangeFrom">

    public void GeneralLedgerChangeFrom(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedgerSrc.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedgerSrc = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedgerSrc);
            fmsOperatingBudgetDetailList = fmsOperatingBudgetDetailBeanLocal.fetchOBDetailByGL(fmsOperatingBudget1, fmsGeneralLedgerSrc);
            fmsOBDetail = fmsOperatingBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedgerSrc, fmsOperatingBudget1);
            fmsOperatingBudgetTaskListSrc = fMSOBTasksBeanLocal.findByOBudgetDetailId(fmsOBDetail);
            if (fmsOperatingBudgetDetailList.isEmpty()) {
                JsfUtil.addFatalMessage("No result found.");
            } else {
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GLChangeFrm">

    public void GLChangeFrm(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedgerSrc.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedgerSrc = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedgerSrc);
            fmsOperatingBudgetDetailList = fmsOperatingBudgetDetailBeanLocal.fetchOBDetailByGL(fmsOperatingBudget1, fmsGeneralLedgerSrc);
            fmsOBDetail = new FmsOperatingBudgetDetail();
            fmsOBDetail = fmsOperatingBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedgerSrc, fmsOperatingBudget1);
            fmsOperatingBudgetTaskList = fMSOBTasksBeanLocal.findByOBudgetDetailId(fmsOBDetail);
        }
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
                nextPeriod = listLuBudgetYearList.get(i);
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getSystemSearchList">

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findOprSystem(), true);
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
    //<editor-fold defaultstate="collapsed" desc="getCostCenterSearchTO">

    public SelectItem[] getCostCenterSearchTO() {

        fmsLuSystemTO = fmsCostCenterTO.getSystemId();
        if (fmsLuSystemTO != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystemTO), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
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
            costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SystemSearchChangeTO">

    public void SystemSearchChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystemTO.setSystemCode(event.getNewValue().toString());
            fmsLuSystemTO = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystemTO);
            costCenterListTo = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystemTO);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CostCenterChange">
    public void CostCenterChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            fmsCostcSystemJunctionFrm = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
            System.out.println("fmsCostcSystemJunctionFrm1: " + fmsCostcSystemJunctionFrm);

        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CostCenterChangeTO">

    public void CostCenterChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenterTO.setSystemName(event.getNewValue().toString());
            fmsCostCenterTO = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenterTO);
            fmsCostcSystemJunctionTo = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystemTO, fmsCostCenterTO);
            System.out.println("fmsCostcSystemJunctionTo: " + fmsCostcSystemJunctionTo);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="OBReqcodes">

    public SelectItem[] OBReqcodes() {
        if (fmsCostCenter != null) {
            return JsfUtil.getSelectItems(fmsOperatingBudgetBeanLocal.findByBudgetYearAndCostCenterAuthorized(fmsLuBudgetYear, fmsCostcSystemJunctionFrm), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="OBReqcodesTO">

    public SelectItem[] OBReqcodesTO() {
        if (fmsCostCenterTO != null) {
            return JsfUtil.getSelectItems(fmsOperatingBudgetBeanLocal.findBySystemAndCostCenterAuthorized(fmsCostcSystemJunctionTo), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestCodeChange">

    public void RequestCodeChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudget1.setRequestCode(event.getNewValue().toString());
            fmsOperatingBudget1 = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsOperatingBudget1);
            fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
            fmsOperatingBudgetDetail.setOperatingBudgetId(fmsOperatingBudget1);
            fmsOperatingBudgetDetailBeanLocal.fetchOBDetail(fmsOperatingBudget1);
            recreateDataModel();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestCodeChangeTO">

    public void RequestCodeChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsOperatingBudget1TO.setRequestCode(event.getNewValue().toString());
            fmsOperatingBudget1TO = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsOperatingBudget1TO);
            fmsOperatingBudgetDetailTO = new FmsOperatingBudgetDetail();
            fmsOperatingBudgetDetailTO.setOperatingBudgetId(fmsOperatingBudget1TO);
            fmsOperatingBudgetDetailBeanLocal.fetchOBDetail(fmsOperatingBudget1TO);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GeneralLedgerTO">

    public SelectItem[] GeneralLedgerTO() {
        if (fmsOperatingBudget1TO != null) {
            return JsfUtil.getSelectItems(fmsOperatingBudgetDetailBeanLocal.fetchGLfromOBDetail(fmsOperatingBudget1TO), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getBudgetDetail">

    public FmsOperatingBudgetDetail getBudgetDetail() {
        if (budgetDetail == null) {
            budgetDetail = new FmsOperatingBudgetDetail();
        }
        return budgetDetail;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateDataModel">

    public void recreateDataModel() {
        fmsOperatingBudgetDetailmodel = null;
        fmsOperatingBudgetDetailmodel = new ListDataModel(new ArrayList(fmsOperatingBudgetDetailList));
        fmsOperatingBudgetDetail = new FmsOperatingBudgetDetail();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchPopulate">

    public void searchPopulate(SelectEvent event) {
        fmsObTransferDetail = (FmsObTransferDetail) event.getObject();
        fmsLuBudgetYearTO = fmsObTransferDetail.getToObId().getOBDetailFk().getOperatingBudgetId().getBudgetYear();
        fmsLuBudgetYear = fmsObTransferDetail.getFromObId().getOBDetailFk().getOperatingBudgetId().getBudgetYear();
        fmsLuSystemTO = fmsObTransferDetail.getToObId().getOBDetailFk().getOperatingBudgetId().getCcSsJunction().getFmsLuSystem();
        fmsLuSystem = fmsObTransferDetail.getFromObId().getOBDetailFk().getOperatingBudgetId().getCcSsJunction().getFmsLuSystem();
        costCenterListTo = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystemTO);
        costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        fmsCostCenterTO = fmsObTransferDetail.getToObId().getOBDetailFk().getOperatingBudgetId().getCcSsJunction().getFmsCostCenter();
        fmsCostCenter = fmsObTransferDetail.getFromObId().getOBDetailFk().getOperatingBudgetId().getCcSsJunction().getFmsCostCenter();
        fmsCostcSystemJunctionTo = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystemTO, fmsCostCenterTO);
        fmsOperatingBudget1TO = fmsObTransferDetail.getToObId().getOBDetailFk().getOperatingBudgetId();
        fmsOperatingBudget1 = fmsObTransferDetail.getFromObId().getOBDetailFk().getOperatingBudgetId();
        fmsGeneralLedgerTO = fmsObTransferDetail.getToObId().getOBDetailFk().getGeneralLedger();
        fmsOperatingBudgetTasksTo = fmsObTransferDetail.getToObId();
        fmsGeneralLedgerSrc = fmsObTransferDetail.getFromObId().getOBDetailFk().getGeneralLedger();
        fmsOperatingBudget1 = fmsObTransferDetail.getFromObId().getOBDetailFk().getOperatingBudgetId();
        fmsOBDetail = fmsOperatingBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedgerSrc, fmsOperatingBudget1);
        fmsOperatingBudgetTaskList = fMSOBTasksBeanLocal.findByOBudgetDetailId(fmsOBDetail);
        fmsOperatingBudgetTasks = fmsObTransferDetail.getFromObId();
        fmsOperatingBudgetDetailTO = fmsOperatingBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedgerTO, fmsOperatingBudget1TO);
        fmsOperatingBudgetTaskListTO = fMSOBTasksBeanLocal.findByOBudgetDetailId(fmsOperatingBudgetDetailTO);
        setTransferAmount(fmsObTransferDetail.getAmount());
        drpDisable = true;
        transferCalc();

        renderNewSrcBtn = true;
        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        reqheaderTitle = "Operating Budget Transfer";
        buttonRenderd = true;
        approveRenderd = false;
        saveOrUpdate = "Edit";
        setIcone("ui-icon-search");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateTaskDataModel">

    public void recreateTaskDataModel() {
        fmsOperatingBudgetTransfermodel = null;
        fmsOperatingBudgetTransfermodel = new ListDataModel(new ArrayList(fmsObTransferDetailsList));
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="transferCalc">

    public void transferCalc() {
        if (fmsObTransferDetail != null) {
            if ((fmsOperatingBudgetTasks.getRemainingAmount().compareTo(transferAmount) == 1) == true) {
                setNewAmountFrom(fmsOperatingBudgetTasks.getRemainingAmount().subtract(transferAmount));
                setNewAmountTO(fmsOperatingBudgetTasksTo.getRemainingAmount().add(transferAmount));
            } else {
                setNewAmountFrom(null);
                JsfUtil.addFatalMessage("The amount you entered exceeds the remaining balance.");
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="saveOBTransfer">

    public void saveOBTransfer() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveOBTransfer", dataset)) {
                if (fmsOperatingBudgetTasks.getSlId().equals(fmsOperatingBudgetTasksTo.getSlId()) && fmsLuSystem.equals(fmsLuSystemTO)) {
                    resetOBTrans();
                    JsfUtil.addFatalMessage("You can not transfer money to the same ledger and same system.");
                } else {
                    if (!drpDisable) {       //first stage  (request stages)
                        fmsObTransferDetail.setFromObId(fmsOperatingBudgetTasks);
                        fmsObTransferDetail.setToObId(fmsOperatingBudgetTasksTo);
                        fmsObTransferDetail.setAmount(transferAmount);
                        fmsObTransferDetail.setStatus(Constants.REQUEST_VALUE);

                        transferDetailBeanLocal.create(fmsObTransferDetail);
                        wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                        wfFcmsProcessed.setCommentGiven(userRemark);
                        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                        wfFcmsProcessed.setProcessedOn(userRemarkDate);
                        wfFcmsProcessed.setDecision(Constants.REQUEST_VALUE);

                        wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                        JsfUtil.addSuccessMessage("Transfer request is succesfully made.");
                    } else {
                        if (fmsObTransferDetail.getStatus() == Constants.REQUEST_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.PREPARE_REJECT_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            } else if ("Approve".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.PREPARE_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            }
                            JsfUtil.addSuccessMessage("Transfer is prepared succesfully.");
                        } else if (fmsObTransferDetail.getStatus() == Constants.PREPARE_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.APPROVE_REJECT_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                JsfUtil.addSuccessMessage("Transfer is prepared succesfully.");
                            } else if ("Approve".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.APPROVE_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.APPROVE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);

                                fmsOperatingBudgetTasks.setRemainingAmount(newAmountFrom);
                                fmsOperatingBudgetTasksTo.setRemainingAmount(newAmountTO);
                                fMSOBTasksBeanLocal.edit(fmsOperatingBudgetTasks);
                                fMSOBTasksBeanLocal.edit(fmsOperatingBudgetTasksTo);
                                obDisbursement = oBDisbursementBeanLocal.fetchOBDisbByTaskId(fmsOperatingBudgetTasks);
                                obDisbursementTo = oBDisbursementBeanLocal.fetchOBDisbByTaskId(fmsOperatingBudgetTasksTo);
                                BigDecimal equalDisbursment;
                                equalDisbursment = fmsOperatingBudgetTasks.getRemainingAmount().divide(new BigDecimal(12), 2, RoundingMode.HALF_EVEN);

                                obDisbursement.setHamle(equalDisbursment);
                                obDisbursement.setNehasie(equalDisbursment);
                                obDisbursement.setMeskerem(equalDisbursment);
                                obDisbursement.setTikemt(equalDisbursment);
                                obDisbursement.setHidar(equalDisbursment);
                                obDisbursement.setTahsas(equalDisbursment);
                                obDisbursement.setTir(equalDisbursment);
                                obDisbursement.setYekatit(equalDisbursment);
                                obDisbursement.setMegabit(equalDisbursment);
                                obDisbursement.setMiyaziya(equalDisbursment);
                                obDisbursement.setGinbot(equalDisbursment);
                                obDisbursement.setSene(equalDisbursment);

                                obDisbursementTo.setHamle(obDisbursementTo.getHamle().add(transferAmount));
                                oBDisbursementBeanLocal.edit(obDisbursement);
                                oBDisbursementBeanLocal.edit(obDisbursementTo);

                                JsfUtil.addSuccessMessage("Transfer is approved succesfully.");
                            }
                        } else if (fmsObTransferDetail.getStatus() == Constants.PREPARE_REJECT_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.REQUEST_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            } else if ("Approve".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.PREPARE_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            }
                            JsfUtil.addSuccessMessage("Transfer is prepared succesfully.");
                        } else if (fmsObTransferDetail.getStatus() == Constants.APPROVE_REJECT_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.PREPARE_REJECT_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            } else if ("Approve".equals(decisionType)) {
                                fmsObTransferDetail.setStatus(Constants.PREPARE_VALUE);
                                transferDetailBeanLocal.update(fmsObTransferDetail);

                                wfFcmsProcessed.setObTransferDtlId(fmsObTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            }
                        }
                    }
                }
                resetOBTrans();
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
    //</editor-fold> 
//<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>

}
