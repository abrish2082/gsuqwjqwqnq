
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
import et.gov.eep.fcms.businessLogic.budget.FmsCBDisbursementBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBTasksBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCBTransferDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsCapitalBudgetDetailBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.budgetCodeBeanLocal;
import et.gov.eep.fcms.businessLogic.budgetYearLookUpBeanLocal;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbDisbursement;
import et.gov.eep.fcms.entity.budget.FmsCbTransferDetail;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
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

@Named(value = "cBTransferController")
@ViewScoped
public class CBTransferController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Inject">

    @Inject
    SessionBean SessionBean;

    @Inject
    FmsCapitalBudget1 fmsCapitalBudget1;
    @Inject
    FmsCapitalBudget1 fmsCapitalBudget1TO;

    @Inject
    FmsCapitalBudgetDetail fmsCapitalBudgetDetail;
    @Inject
    FmsCapitalBudgetDetail fmsCapitalBudgetDetailTO;
    @Inject
    FmsCapitalBudgetDetail fmsCBDetail;

    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunctionTo;
    @Inject
    FmsCostcSystemJunction fmsCostcSystemJunctionFrm;

    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsCostCenter fmsCostCenterTO;

    @Inject
    PmsWorkAuthorization pmsWorkAuthorization;
    @Inject
    PmsWorkAuthorization pmsWorkAuthorizationTo;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsLuSystem fmsLuSystemTO;

    @Inject
    FmsLuBudgetYear fmsLuBudgetYear;
    @Inject
    FmsLuBudgetYear fmsLuBudgetYearTO;

    @Inject
    FmsBudgetCode fmsBudgetCode;
    @Inject
    FmsBudgetCode fmsBudgetCodeTo;
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
    FmsCapitalBudgetTasks capitalBudgetTasksSrc;
    @Inject
    FmsCapitalBudgetTasks capitalBudgetTasks;
    @Inject
    FmsCapitalBudgetTasks capitalBudgetTasksTo;
    @Inject
    FmsCbTransferDetail cbTransferDetail;
    @Inject
    FmsCbDisbursement cbDisbursement;
    @Inject
    FmsCbDisbursement cbDisbursementTo;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">

    @EJB
    FmsCBDisbursementBeanLocal cBDisbursementBeanLocal;
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    FmsCBTasksBeanLocal cBTasksBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    FmsCBTransferDetailBeanLocal transferDetailBeanLocal;
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
    budgetCodeBeanLocal fmsBudgetCodeBeanLocal;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    String userRemark = "";
    String userRemarkDate = "";
    private String decisionType;

    FmsAccountingPeriod currPeriod = new FmsAccountingPeriod();
    FmsLuBudgetYear nextPeriod = new FmsLuBudgetYear();
    List<FmsLuBudgetYear> budgetyrList = new ArrayList<>();
    List<FmsLuBudgetYear> listLuBudgetYearList = new ArrayList<>();
    List<FmsCapitalBudget1> fmsCapitalBudget1List = new ArrayList<>();
    List<FmsCapitalBudget1> fmsCapitalBudget1ListTo = new ArrayList<>();
    List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList = new ArrayList<>();
    List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailListTo = new ArrayList<>();
    List<FmsCapitalBudgetTasks> capitalBudgetTasksList = new ArrayList<>();
    List<FmsCapitalBudgetTasks> capitalBudgetTasksListTo = new ArrayList<>();
    List<FmsCostCenter> costCenterList;
    List<FmsCostCenter> costCenterListTo;
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsSystemJobJunction> sysJobNOListTo;
    private NumberConverter numberConverter = new NumberConverter();
    BigDecimal transferAmount = BigDecimal.ZERO;
    BigDecimal newAmount = BigDecimal.ZERO;
    BigDecimal newAmountTO = BigDecimal.ZERO;
    List<FmsCbTransferDetail> requestList = new ArrayList<>();
    List<WfFcmsProcessed> wfFcmsProcessedList = new ArrayList<>();
    List<FmsCbTransferDetail> fmsCbTransferDetailsList = new ArrayList<>();
    DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel;
    DataModel<FmsCbDisbursement> fmsCbDisbursementmodel;
    DataModel<FmsCbTransferDetail> fmsCapitalBudgetTransfermodel;
    DataModel<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailmodel;
    DataModel<FmsCapitalBudget1> fmsCapitalBudgetDetailSrcmodel;
    FmsCapitalBudget1 capitalBudgetSelection;
    FmsObDisbursement cbDisbursementSelection;
    FmsCbTransferDetail fmsCbTransferDetailSelection;
    FmsCapitalBudgetDetail budgetDetail;
    Integer requestNotificationCounter = 0;

    boolean drpDisable = false;

    boolean newAmtReadonly = true;

    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    private boolean buttonRenderd = false;
    private boolean approveRenderd = false;
    private String reqheaderTitle = "Capital Budget Transfer";
    private String saveOrUpdate = "Create";
    private boolean renderNewSrcBtn;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getter and setters">

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

    public boolean isRenderNewSrcBtn() {
        return renderNewSrcBtn;
    }

    public void setRenderNewSrcBtn(boolean renderNewSrcBtn) {
        this.renderNewSrcBtn = renderNewSrcBtn;
    }

    public void setFmsSubsidiaryLedgerSrc(FmsSubsidiaryLedger fmsSubsidiaryLedgerSrc) {
        this.fmsSubsidiaryLedgerSrc = fmsSubsidiaryLedgerSrc;
    }

    public void setFmsSubsidiaryLedgerTo(FmsSubsidiaryLedger fmsSubsidiaryLedgerTo) {
        this.fmsSubsidiaryLedgerTo = fmsSubsidiaryLedgerTo;
    }

    public FmsCapitalBudgetDetail getFmsCBDetail() {
        return fmsCBDetail;
    }

    public void setFmsCBDetail(FmsCapitalBudgetDetail fmsCBDetail) {
        this.fmsCBDetail = fmsCBDetail;
    }

    public FmsCapitalBudgetTasks getCapitalBudgetTasksSrc() {
        return capitalBudgetTasksSrc;
    }

    public void setCapitalBudgetTasksSrc(FmsCapitalBudgetTasks capitalBudgetTasksSrc) {
        this.capitalBudgetTasksSrc = capitalBudgetTasksSrc;
    }

    public FmsCapitalBudgetTasks getCapitalBudgetTasks() {
        if (capitalBudgetTasks == null) {
            capitalBudgetTasks = new FmsCapitalBudgetTasks();
        }
        return capitalBudgetTasks;
    }

    public void setCapitalBudgetTasks(FmsCapitalBudgetTasks capitalBudgetTasks) {
        this.capitalBudgetTasks = capitalBudgetTasks;
    }

    public FmsCbTransferDetail getCbTransferDetail() {
        return cbTransferDetail;
    }

    public void setCbTransferDetail(FmsCbTransferDetail cbTransferDetail) {
        this.cbTransferDetail = cbTransferDetail;
    }

    public FmsCbDisbursement getCbDisbursement() {
        return cbDisbursement;
    }

    public void setCbDisbursement(FmsCbDisbursement cbDisbursement) {
        this.cbDisbursement = cbDisbursement;
    }

    public FmsCbDisbursement getCbDisbursementTo() {
        return cbDisbursementTo;
    }

    public void setCbDisbursementTo(FmsCbDisbursement cbDisbursementTo) {
        this.cbDisbursementTo = cbDisbursementTo;
    }

    public WfFcmsProcessed getWfFcmsProcessed() {
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public FmsCapitalBudgetTasks getCapitalBudgetTasksTo() {
        if (capitalBudgetTasksTo == null) {
            capitalBudgetTasksTo = new FmsCapitalBudgetTasks();
        }
        return capitalBudgetTasksTo;
    }

    public void setCapitalBudgetTasksTo(FmsCapitalBudgetTasks capitalBudgetTasksTo) {
        this.capitalBudgetTasksTo = capitalBudgetTasksTo;
    }

    public List<FmsCostCenter> getCostCenterList() {
        return costCenterList;
    }

    public void setCostCenterList(List<FmsCostCenter> costCenterList) {
        this.costCenterList = costCenterList;
    }

    public List<FmsCbTransferDetail> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<FmsCbTransferDetail> requestList) {
        this.requestList = requestList;
    }

    public List<WfFcmsProcessed> getWfFcmsProcessedList() {
        return wfFcmsProcessedList;
    }

    public void setWfFcmsProcessedList(List<WfFcmsProcessed> wfFcmsProcessedList) {
        this.wfFcmsProcessedList = wfFcmsProcessedList;
    }

    public boolean isDrpDisable() {
        return drpDisable;
    }

    public void setDrpDisable(boolean drpDisable) {
        this.drpDisable = drpDisable;
    }

    public List<FmsSystemJobJunction> getSysJobNOList() {
        if (sysJobNOList == null) {
            sysJobNOList = new ArrayList<>();
        }
        return sysJobNOList;
    }

    public void setSysJobNOList(List<FmsSystemJobJunction> sysJobNOList) {
        this.sysJobNOList = sysJobNOList;
    }

    public List<FmsCostCenter> getCostCenterListTo() {
        return costCenterListTo;
    }

    public List<FmsSystemJobJunction> getSysJobNOListTo() {
        if (sysJobNOListTo == null) {
            sysJobNOListTo = new ArrayList<>();
        }
        return sysJobNOListTo;
    }

    public void setSysJobNOListTo(List<FmsSystemJobJunction> sysJobNOListTo) {
        this.sysJobNOListTo = sysJobNOListTo;
    }

    public void setCostCenterListTo(List<FmsCostCenter> costCenterListTo) {
        this.costCenterListTo = costCenterListTo;
    }

    public FmsCapitalBudgetDetail getBudgetDetail() {
        if (budgetDetail == null) {
            budgetDetail = new FmsCapitalBudgetDetail();
        }
        return budgetDetail;
    }

    public void setBudgetDetail(FmsCapitalBudgetDetail budgetDetail) {
        this.budgetDetail = budgetDetail;
    }

    public List<FmsCbTransferDetail> getFmsCbTransferDetailsList() {
        if (fmsCbTransferDetailsList == null) {
            fmsCbTransferDetailsList = new ArrayList<>();
        }
        return fmsCbTransferDetailsList;
    }

    public void setFmsObTransferDetailsList(List<FmsCbTransferDetail> fmsCbTransferDetailsList) {
        this.fmsCbTransferDetailsList = fmsCbTransferDetailsList;
    }

    public List<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailList() {
        return fmsCapitalBudgetDetailList;
    }

    public void setFmsCapitalBudgetDetailList(List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailList) {
        this.fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailList;
    }

    public List<FmsCapitalBudgetDetail> getFmsCapitalBudgetDetailListTo() {
        return fmsCapitalBudgetDetailListTo;
    }

    public void setFmsCapitalBudgetDetailListTo(List<FmsCapitalBudgetDetail> fmsCapitalBudgetDetailListTo) {
        this.fmsCapitalBudgetDetailListTo = fmsCapitalBudgetDetailListTo;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunctionTo() {
        return fmsCostcSystemJunctionTo;
    }

    public void setFmsCostcSystemJunctionTo(FmsCostcSystemJunction fmsCostcSystemJunctionTo) {
        this.fmsCostcSystemJunctionTo = fmsCostcSystemJunctionTo;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunctionFrm() {
        return fmsCostcSystemJunctionFrm;
    }

    public void setFmsCostcSystemJunctionFrm(FmsCostcSystemJunction fmsCostcSystemJunctionFrm) {
        this.fmsCostcSystemJunctionFrm = fmsCostcSystemJunctionFrm;
    }

    public FmsBudgetCode getFmsBudgetCode() {
        return fmsBudgetCode;
    }

    public void setFmsBudgetCode(FmsBudgetCode fmsBudgetCode) {
        this.fmsBudgetCode = fmsBudgetCode;
    }

    public FmsBudgetCode getFmsBudgetCodeTo() {
        return fmsBudgetCodeTo;
    }

    public void setFmsBudgetCodeTo(FmsBudgetCode fmsBudgetCodeTo) {
        this.fmsBudgetCodeTo = fmsBudgetCodeTo;
    }

    public DataModel<FmsCbDisbursement> getFmsCbDisbursementmodel() {
        return fmsCbDisbursementmodel;
    }

    public void setFmsCbDisbursementmodel(DataModel<FmsCbDisbursement> fmsCbDisbursementmodel) {
        this.fmsCbDisbursementmodel = fmsCbDisbursementmodel;
    }

    public FmsCapitalBudget1 getCapitalBudgetSelection() {
        return capitalBudgetSelection;
    }

    public void setCapitalBudgetSelection(FmsCapitalBudget1 capitalBudgetSelection) {
        this.capitalBudgetSelection = capitalBudgetSelection;
    }

    public FmsObDisbursement getCbDisbursementSelection() {
        return cbDisbursementSelection;
    }

    public void setCbDisbursementSelection(FmsObDisbursement cbDisbursementSelection) {
        this.cbDisbursementSelection = cbDisbursementSelection;
    }

    public FmsCbTransferDetail getFmsCbTransferDetailSelection() {
        return fmsCbTransferDetailSelection;
    }

    public void setFmsCbTransferDetailSelection(FmsCbTransferDetail fmsCbTransferDetailSelection) {
        this.fmsCbTransferDetailSelection = fmsCbTransferDetailSelection;
    }

    public boolean isNewAmtReadonly() {
        return newAmtReadonly;
    }

    public void setNewAmtReadonly(boolean newAmtReadonly) {
        this.newAmtReadonly = newAmtReadonly;
    }

    public BigDecimal getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(BigDecimal newAmount) {
        this.newAmount = newAmount;
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

    public FmsCapitalBudget1 getFmsCapitalBudget1() {
        return fmsCapitalBudget1;
    }

    public void setFmsCapitalBudget1(FmsCapitalBudget1 fmsCapitalBudget1) {
        this.fmsCapitalBudget1 = fmsCapitalBudget1;
    }

    public FmsCapitalBudgetDetail getFmsCapitalBudgetDetail() {
        return fmsCapitalBudgetDetail;
    }

    public void setFmsCapitalBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        this.fmsCapitalBudgetDetail = fmsCapitalBudgetDetail;
    }

    public List<FmsCapitalBudgetTasks> getCapitalBudgetTasksList() {
        if (capitalBudgetTasksList == null) {
            capitalBudgetTasksList = new ArrayList<>();
        }
        return capitalBudgetTasksList;
    }

    public void setCapitalBudgetTasksList(List<FmsCapitalBudgetTasks> capitalBudgetTasksList) {
        this.capitalBudgetTasksList = capitalBudgetTasksList;
    }

    public List<FmsCapitalBudgetTasks> getCapitalBudgetTasksListTo() {
        if (capitalBudgetTasksListTo == null) {
            capitalBudgetTasksListTo = new ArrayList<>();
        }
        return capitalBudgetTasksListTo;
    }

    public void setCapitalBudgetTasksListTo(List<FmsCapitalBudgetTasks> capitalBudgetTasksListTo) {
        this.capitalBudgetTasksListTo = capitalBudgetTasksListTo;
    }

    public FmsCostCenter getFmsCostCenter() {
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public PmsWorkAuthorization getPmsWorkAuthorization() {
        if (pmsWorkAuthorization == null) {
            pmsWorkAuthorization = new PmsWorkAuthorization();
        }
        return pmsWorkAuthorization;
    }

    public void setPmsWorkAuthorization(PmsWorkAuthorization pmsWorkAuthorization) {
        this.pmsWorkAuthorization = pmsWorkAuthorization;
    }

    public PmsWorkAuthorization getPmsWorkAuthorizationTo() {
        if (pmsWorkAuthorizationTo == null) {
            pmsWorkAuthorizationTo = new PmsWorkAuthorization();
        }
        return pmsWorkAuthorizationTo;
    }

    public void setPmsWorkAuthorizationTo(PmsWorkAuthorization pmsWorkAuthorizationTo) {
        this.pmsWorkAuthorizationTo = pmsWorkAuthorizationTo;
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

    public FmsCapitalBudget1 getFmsCapitalBudget1TO() {
        return fmsCapitalBudget1TO;
    }

    public void setFmsCapitalBudget1TO(FmsCapitalBudget1 fmsCapitalBudget1TO) {
        this.fmsCapitalBudget1TO = fmsCapitalBudget1TO;
    }

    public FmsCapitalBudgetDetail getFmsCapitalBudgetDetailTO() {
        return fmsCapitalBudgetDetailTO;
    }

    public void setFmsCapitalBudgetDetailTO(FmsCapitalBudgetDetail fmsCapitalBudgetDetailTO) {
        this.fmsCapitalBudgetDetailTO = fmsCapitalBudgetDetailTO;
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

    public List<FmsCapitalBudget1> getFmsCapitalBudget1List() {
        return fmsCapitalBudget1List;
    }

    public void setFmsCapitalBudget1List(List<FmsCapitalBudget1> fmsCapitalBudget1List) {
        this.fmsCapitalBudget1List = fmsCapitalBudget1List;
    }

    public List<FmsCapitalBudget1> getFmsCapitalBudget1ListTo() {
        return fmsCapitalBudget1ListTo;
    }

    public void setFmsCapitalBudget1ListTo(List<FmsCapitalBudget1> fmsCapitalBudget1ListTo) {
        this.fmsCapitalBudget1ListTo = fmsCapitalBudget1ListTo;
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

    public DataModel<WfFcmsProcessed> getWfFcmsProcessedDataModel() {
        return wfFcmsProcessedDataModel;
    }

    public void setWfFcmsProcessedDataModel(DataModel<WfFcmsProcessed> wfFcmsProcessedDataModel) {
        this.wfFcmsProcessedDataModel = wfFcmsProcessedDataModel;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public DataModel<FmsCbTransferDetail> getFmsCapitalBudgetTransfermodel() {
        return fmsCapitalBudgetTransfermodel;
    }

    public void setFmsCapitalBudgetTransfermodel(DataModel<FmsCbTransferDetail> fmsCapitalBudgetTransfermodel) {
        this.fmsCapitalBudgetTransfermodel = fmsCapitalBudgetTransfermodel;
    }

    public CBTransferController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(50);
        numberConverter.setMaxFractionDigits(2);
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public Integer getRequestNotificationCounter() {
        requestList = transferDetailBeanLocal.findCBTransReqList();
        requestNotificationCounter = requestList.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public List<FmsCbTransferDetail> getCBTransRequestList() {
        return transferDetailBeanLocal.findCBTransReqList();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="createNewCBTransferView">

    public void createNewCBTransferView() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            renderNewSrcBtn = false;
            createOrSearchBundle = "Search";
            reqheaderTitle = "Capital Budget Transfer";
            buttonRenderd = true;
            approveRenderd = false;
            setIcone("ui-icon-search");
            resetCBTrans();
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            renderNewSrcBtn = false;
            createOrSearchBundle = "New";
            reqheaderTitle = "Capital Budget Transfer";
            buttonRenderd = false;
            approveRenderd = true;
            setIcone("ui-icon-plus");
            resetCBTrans();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestCodeChangeFrom">

    public void RequestCodeChangeFrom(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCapitalBudget1.setRequestCode(event.getNewValue().toString());
            fmsCapitalBudget1 = fmsCapitalBudgetBeanLocal.findByRequestCodeOnRequest(fmsCapitalBudget1);
            fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
            fmsCapitalBudgetDetail.setCapitalBudgetId(fmsCapitalBudget1);
            fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailBeanLocal.fetchSelectedCBRequest(fmsCapitalBudget1);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestCodeChangeTo">

    public void RequestCodeChangeTo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCapitalBudget1TO.setRequestCode(event.getNewValue().toString());
            fmsCapitalBudget1TO = fmsCapitalBudgetBeanLocal.findByRequestCodeOnRequest(fmsCapitalBudget1TO);
            fmsCapitalBudgetDetailTO = new FmsCapitalBudgetDetail();
            fmsCapitalBudgetDetailTO.setCapitalBudgetId(fmsCapitalBudget1TO);
            fmsCapitalBudgetDetailListTo = fmsCapitalBudgetDetailBeanLocal.fetchSelectedCBRequest(fmsCapitalBudget1TO);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="onChangeJobNo">

    public void onChangeJobNo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            //pmsWorkAuthorization = (PmsWorkAuthorization) event.getNewValue();
            pmsWorkAuthorization.setJobNo((event.getNewValue().toString()));
            fmsCapitalBudget1List = fmsCapitalBudgetBeanLocal.findByCCSSJandPMSandBYRAuthorized(fmsLuBudgetYear, fmsCostcSystemJunctionFrm, pmsWorkAuthorization);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="onChangeJobNoTo">

    public void onChangeJobNoTo(ValueChangeEvent event) {
        //if (event.getNewValue() != null) {
        // pmsWorkAuthorizationTo = (PmsWorkAuthorization) event.getNewValue();
        if (!event.getNewValue().toString().isEmpty()) {
            //pmsWorkAuthorization = (PmsWorkAuthorization) event.getNewValue();
            pmsWorkAuthorizationTo.setJobNo((event.getNewValue().toString()));

            fmsCapitalBudget1ListTo = fmsCapitalBudgetBeanLocal.findByCCSSJandPMSandBYRAuthorized(fmsLuBudgetYearTO, fmsCostcSystemJunctionTo, pmsWorkAuthorizationTo);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BudgetCodeFrom">

    public SelectItem[] BudgetCodeFrom() {
        if (fmsCapitalBudget1 != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetDetailBeanLocal.fetchSelectedCBRequest(fmsCapitalBudget1), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BudgetCodeTo">

    public SelectItem[] BudgetCodeTo() {
        if (fmsCapitalBudget1TO != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetDetailBeanLocal.fetchSelectedCBRequest(fmsCapitalBudget1TO), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SubsidiaryLedgerChangeFrm">

    public void SubsidiaryLedgerChangeFrm(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            capitalBudgetTasks = (FmsCapitalBudgetTasks) event.getNewValue();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SubsidiaryLedgerChangeTo">

    public void SubsidiaryLedgerChangeTo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            capitalBudgetTasksTo = (FmsCapitalBudgetTasks) event.getNewValue();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CbBgtCodeChange">

    public void CbBgtCodeChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsBudgetCode.setBudgetCode(event.getNewValue().toString());
            fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailBeanLocal.getCapBudgetDetailList(fmsLuBudgetYear, fmsCapitalBudget1, fmsBudgetCode);
//            capitalBudgetTasksSrc = cBTasksBeanLocal.findByCBudgetReqCode(fmsCapitalBudgetDetail);
//            recreateDisbursementModel();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CbTransListener">

    public void CbTransListener(ValueChangeEvent event) {
        FmsCbTransferDetail obTrans = new FmsCbTransferDetail();
        obTrans.setTransferDtlId(Integer.parseInt(event.getNewValue().toString()));
        obTrans = transferDetailBeanLocal.findByTrasferId(obTrans);
        fmsCbTransferDetailsList.add(obTrans);
        recreateTaskDataModel();
        recreateWFDataModel();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateWFDataModel">

    public void recreateWFDataModel() {
        wfFcmsProcessedDataModel = null;
        for (int i = 0; i < fmsCbTransferDetailsList.size(); i++) {
            wfFcmsProcessedList = fmsCbTransferDetailsList.get(i).getWfFcmsProcessedList();
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
    //<editor-fold defaultstate="collapsed" desc="resetCBTrans">

    public void resetCBTrans() {
        fmsBudgetCode = new FmsBudgetCode();
        fmsCapitalBudget1 = new FmsCapitalBudget1();
        fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
        fmsCapitalBudgetDetailTO = new FmsCapitalBudgetDetail();
        fmsCostCenter = new FmsCostCenter();
        fmsCapitalBudget1TO = new FmsCapitalBudget1();
        fmsLuSystem = new FmsLuSystem();
        fmsLuSystemTO = new FmsLuSystem();
        fmsLuBudgetYear = new FmsLuBudgetYear();
        fmsLuBudgetYearTO = new FmsLuBudgetYear();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCapitalBudgetDetailmodel = new ArrayDataModel<>();
        fmsCapitalBudgetDetailSrcmodel = new ArrayDataModel<>();
        transferAmount = null;
        newAmount = null;
        newAmountTO = null;
        decisionType = null;
        userRemark = "";
        userRemarkDate = "";

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
                nextPeriod = listLuBudgetYearList.get(i + 1);
            }
        }
        budgetyrList.add(currPeriod.getLuBudgetYearId());
        budgetyrList.add(nextPeriod);
        return JsfUtil.getSelectItems(budgetyrList, true);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="yearValueChangeTo">

    public void yearValueChangeTo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuBudgetYearTO.setBudgetYear(event.getNewValue().toString());
            fmsLuBudgetYearTO = budgetYearLookUpBeanLocal.getYearInfo(fmsLuBudgetYearTO);
            fmsCapitalBudget1TO.setBudgetYear(fmsLuBudgetYearTO);
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
    //<editor-fold defaultstate="collapsed" desc="getCostCenterSearch">

    public SelectItem[] getCostCenterSearch() {

        fmsLuSystem = fmsCostCenter.getSystemId();
        if (fmsLuSystem != null) {
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findAll(), true);
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
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystemTO), true);
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
            fmsCapitalBudget1.setBudgetYear(fmsLuBudgetYear);
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
            sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SystemSearchChangeTO">

    public void SystemSearchChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystemTO.setSystemCode(event.getNewValue().toString());
            fmsLuSystemTO = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystemTO);
            costCenterListTo = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystemTO);
            sysJobNOListTo = fmsLuSystemTO.getFmsSystemJobJunctionList();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CostCenterChange">

    public void CostCenterChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            fmsCostcSystemJunctionFrm = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CostCenterChangeTO">

    public void CostCenterChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenterTO.setSystemName(event.getNewValue().toString());
            fmsCostCenterTO = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenterTO);
            fmsCostcSystemJunctionTo = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystemTO, fmsCostCenterTO);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CBReqcodes">

    public SelectItem[] CBReqcodes() {
        if (fmsCostCenter != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetBeanLocal.findByBudgetYearAndCostCenterAuthorized(fmsLuBudgetYear, fmsCostcSystemJunctionFrm), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CBReqcodesTo">

    public SelectItem[] CBReqcodesTo() {
        if (fmsCostCenterTO != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetBeanLocal.findByBudgetYearAndCostCenterAuthorized(fmsLuBudgetYearTO, fmsCostcSystemJunctionTo), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="OBReqcodesTO">

    public SelectItem[] OBReqcodesTO() {
        if (fmsCostCenterTO != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetBeanLocal.findCBSystemAndCostCenterAuthorized(fmsCostCenterTO), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestCodeChange">

    public void RequestCodeChange(ValueChangeEvent event) {
//        if (!event.getNewValue().toString().isEmpty()) {
//            fmsCapitalBudget1.setRequestCode(event.getNewValue().toString());
//            fmsCapitalBudget1 = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsCapitalBudget1);
//            fmsCapitalBudgetDetail = new FmsCapitalBudgetDetail();
//            fmsCapitalBudgetDetail.setOperatingBudgetId(fmsCapitalBudget1);
//            fmsCapitalBudgetDetailBeanLocal.fetchOBDetail(fmsCapitalBudget1);
//            recreateDataModel();
//        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RequestCodeChangeTO">

    public void RequestCodeChangeTO(ValueChangeEvent event) {
//        if (!event.getNewValue().toString().isEmpty()) {
//            fmsCapitalBudget1TO.setRequestCode(event.getNewValue().toString());
//            fmsCapitalBudget1TO = fmsOperatingBudgetBeanLocal.fetchByRequestCode(fmsCapitalBudget1TO);
//            fmsCapitalBudgetDetailTO = new FmsCapitalBudgetDetail();
//            fmsCapitalBudgetDetailTO.setOperatingBudgetId(fmsCapitalBudget1TO);
//            fmsCapitalBudgetDetailBeanLocal.fetchOBDetail(fmsCapitalBudget1TO);
//        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GeneralLedgerTO">

    public SelectItem[] GeneralLedgerTO() {
//        if (fmsCapitalBudget1TO != null) {
//            return JsfUtil.getSelectItems(fmsCapitalBudgetDetailBeanLocal.fetchGLfromOBDetail(fmsCapitalBudget1TO), true);
//        } else {
        SelectItem[] items = new SelectItem[1];
        return items;
//        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GeneralLedgerChangeTO">

    public void GeneralLedgerChangeTO(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedgerTO.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedgerTO = fmsGeneralLedgerBeanLocal.getGlCode(fmsGeneralLedgerTO);
//            fmsCapitalBudgetDetailTO = fmsCapitalBudgetDetailBeanLocal.fetchByGLfromOBDetail(fmsGeneralLedgerTO,fmsCapitalBudget1TO);  

        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateDataModel">

    public void recreateDataModel() {
        fmsCapitalBudgetDetailmodel = null;
        fmsCapitalBudgetDetailmodel = new ListDataModel(new ArrayList(fmsCapitalBudgetDetailList));
        fmsCapitalBudgetDetailList = new ArrayList<>();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateTaskDataModel">

    public void recreateTaskDataModel() {
        fmsCapitalBudgetTransfermodel = null;
        fmsCapitalBudgetTransfermodel = new ListDataModel(new ArrayList(fmsCbTransferDetailsList));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchPopulate">

    public void searchPopulate(SelectEvent event) {
        cbTransferDetail = (FmsCbTransferDetail) event.getObject();
        fmsLuBudgetYearTO = cbTransferDetail.getToCbId().getCBDetailFk().getCapitalBudgetId().getBudgetYear();
        fmsLuBudgetYear = cbTransferDetail.getFromCbId().getCBDetailFk().getCapitalBudgetId().getBudgetYear();
        fmsLuSystem = cbTransferDetail.getFromCbId().getCBDetailFk().getCapitalBudgetId().getCcSsJunction().getFmsLuSystem();
        fmsLuSystemTO = cbTransferDetail.getToCbId().getCBDetailFk().getCapitalBudgetId().getCcSsJunction().getFmsLuSystem();
        costCenterList = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystem);
        costCenterListTo = fmsCostCenterBeanLocal.findMappedCostCenter(fmsLuSystemTO);
        fmsCostCenter = cbTransferDetail.getFromCbId().getCBDetailFk().getCapitalBudgetId().getCcSsJunction().getFmsCostCenter();
        fmsCostCenterTO = cbTransferDetail.getToCbId().getCBDetailFk().getCapitalBudgetId().getCcSsJunction().getFmsCostCenter();
        fmsCostcSystemJunctionFrm = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystem, fmsCostCenter);
        fmsCostcSystemJunctionTo = fmsCostCSystemJunctionBeanLocal.findByCCandSS(fmsLuSystemTO, fmsCostCenterTO);
        sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
        sysJobNOListTo = fmsLuSystemTO.getFmsSystemJobJunctionList();
        fmsCapitalBudget1TO = cbTransferDetail.getToCbId().getCBDetailFk().getCapitalBudgetId();
        fmsBudgetCodeTo = cbTransferDetail.getToCbId().getCBDetailFk().getBudgetCode();
        capitalBudgetTasksTo = cbTransferDetail.getToCbId();
        fmsBudgetCode = cbTransferDetail.getFromCbId().getCBDetailFk().getBudgetCode();
        pmsWorkAuthorization.setJobNo(cbTransferDetail.getFromCbId().getCBDetailFk().getCapitalBudgetId().getJobNo().getJobNo());
        pmsWorkAuthorizationTo.setJobNo(cbTransferDetail.getToCbId().getCBDetailFk().getCapitalBudgetId().getJobNo().getJobNo());
        fmsCapitalBudget1 = cbTransferDetail.getToCbId().getCBDetailFk().getCapitalBudgetId();
        fmsCBDetail = fmsCapitalBudgetDetailBeanLocal.fetchCapBudgetDetail(fmsLuBudgetYear, fmsCostCenter, fmsBudgetCode);
        capitalBudgetTasksList = cBTasksBeanLocal.findByCBudgetDetail(fmsCBDetail);
        capitalBudgetTasks = cbTransferDetail.getFromCbId();
        fmsCapitalBudgetDetailTO = fmsCapitalBudgetDetailBeanLocal.fetchCapBudgetDetail(fmsLuBudgetYearTO, fmsCostCenterTO, fmsBudgetCodeTo);
        capitalBudgetTasksListTo = cBTasksBeanLocal.findByCBudgetDetail(fmsCapitalBudgetDetailTO);
        setTransferAmount(cbTransferDetail.getAmount());
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

        //---------------------------------------------
        fmsLuBudgetYearTO = fmsLuBudgetYear;
        renderNewSrcBtn = true;
        renderPnlCreateAdditional = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        reqheaderTitle = "Capital Budget Transfer";
        buttonRenderd = true;
        approveRenderd = false;
        saveOrUpdate = "Edit";
        setIcone("ui-icon-search");
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="searchCBTransferByCriteria">

    public void searchCBTransferByCriteria() {
        fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailBeanLocal.fetchCBDetail(fmsLuBudgetYear, fmsCostCenter);

        if (!fmsCapitalBudgetDetailList.isEmpty()) {
            recreateDataModel();
        } else {
            resetCBTrans();
            JsfUtil.addFatalMessage("No result found.");
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BCChangeFrm">

    public void BCChangeFrm(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsBudgetCode.setBudgetCode(event.getNewValue().toString());
            fmsCapitalBudgetDetailList = fmsCapitalBudgetDetailBeanLocal.getCapBudgetDetailList(fmsLuBudgetYear, fmsCapitalBudget1, fmsBudgetCode);
            fmsCBDetail = fmsCapitalBudgetDetailBeanLocal.fetchCapBudgetDetail(fmsLuBudgetYear, fmsCostCenter, fmsBudgetCode);
            capitalBudgetTasksList = cBTasksBeanLocal.findByCBudgetDetail(fmsCBDetail);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BudgetCodeChangeTo">

    public void BudgetCodeChangeTo(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsBudgetCodeTo.setBudgetCode(event.getNewValue().toString());
            fmsBudgetCodeTo = fmsBudgetCodeBeanLocal.findBudgetCode(fmsBudgetCodeTo);
            fmsCapitalBudgetDetailTO = fmsCapitalBudgetDetailBeanLocal.fetchCapBudgetDetail(fmsLuBudgetYearTO, fmsCostCenterTO, fmsBudgetCodeTo);
            capitalBudgetTasksListTo = cBTasksBeanLocal.findByCBudgetDetail(fmsCapitalBudgetDetailTO);
            budgetDetail = fmsCapitalBudgetDetailTO;
            newAmtReadonly = false;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BudgetCodeChangeFrom">

    public void BudgetCodeChangeFrom(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsBudgetCode.setBudgetCode(event.getNewValue().toString());
            fmsBudgetCode = fmsBudgetCodeBeanLocal.findBudgetCode(fmsBudgetCode);
            fmsCapitalBudgetDetail = fmsCapitalBudgetDetailBeanLocal.fetchCapBudgetDetail(fmsLuBudgetYear, fmsCostCenter, fmsBudgetCode);
            capitalBudgetTasksList = cBTasksBeanLocal.findByCBudgetDetail(fmsCapitalBudgetDetail);
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SubsidiaryLedgerChangeSrc">

    public void SubsidiaryLedgerChangeSrc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            capitalBudgetTasksSrc = (FmsCapitalBudgetTasks) event.getNewValue();
            fmsCbTransferDetailsList = transferDetailBeanLocal.fetchCBFromByCBDTasktlID(capitalBudgetTasksSrc);
            recreateTaskDataModel();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getBudgetCodesTO">

    public SelectItem[] getBudgetCodesTO() {
        if (fmsCostCenterTO != null) {
            return JsfUtil.getSelectItems(fmsCapitalBudgetDetailBeanLocal.fetchCBDetail(fmsLuBudgetYearTO, fmsCostCenterTO), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="transferCalc">

    public void transferCalc() {
        if (cbTransferDetail != null) {
            if ((capitalBudgetTasks.getRemainingAmount().compareTo(transferAmount) == 1) == true) {
                setNewAmount(capitalBudgetTasks.getRemainingAmount().subtract(transferAmount));
                setNewAmountTO(capitalBudgetTasksTo.getRemainingAmount().add(transferAmount));
            } else {
                setNewAmount(null);
                JsfUtil.addFatalMessage("The amount you entered exceeds the remaining balance.");
            }
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="saveCBTransfer">

    public void saveCBTransfer() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveCBTransfer", dataset)) {
                if (capitalBudgetTasks.getSlId().equals(capitalBudgetTasksTo.getSlId()) && fmsLuSystem.equals(fmsLuSystemTO)) {
                    resetCBTrans();
                    JsfUtil.addFatalMessage("You can not transfer money to the same ledger and same system.");
                } else {
                    if (!drpDisable) {       //first stage  (request stages)
                        cbTransferDetail.setFromCbId(capitalBudgetTasks);
                        cbTransferDetail.setToCbId(capitalBudgetTasksTo);
                        cbTransferDetail.setAmount(transferAmount);
                        cbTransferDetail.setStatus(Constants.REQUEST_VALUE);

                        transferDetailBeanLocal.create(cbTransferDetail);
                        wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                        wfFcmsProcessed.setCommentGiven(userRemark);
                        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                        wfFcmsProcessed.setProcessedOn(userRemarkDate);
                        wfFcmsProcessed.setDecision(Constants.REQUEST_VALUE);

                        wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                        JsfUtil.addSuccessMessage("Transfer request is succesfully made.");
                    } else {
                        if (cbTransferDetail.getStatus() == Constants.REQUEST_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.PREPARE_REJECT_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            } else if ("Approve".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.PREPARE_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            }
                            JsfUtil.addSuccessMessage("Transfer is prepared succesfully.");
                        } else if (cbTransferDetail.getStatus() == Constants.PREPARE_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.APPROVE_REJECT_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                JsfUtil.addSuccessMessage("Transfer is prepared succesfully.");
                            } else if ("Approve".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.APPROVE_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.APPROVE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);

                                capitalBudgetTasks.setRemainingAmount(newAmount);
                                capitalBudgetTasksTo.setRemainingAmount(newAmountTO);
                                cBTasksBeanLocal.edit(capitalBudgetTasks);
                                cBTasksBeanLocal.edit(capitalBudgetTasksTo);
                                cbDisbursement = cBDisbursementBeanLocal.fetchCBDisbByTaskId(capitalBudgetTasks);
                                cbDisbursementTo = cBDisbursementBeanLocal.fetchCBDisbByTaskId(capitalBudgetTasksTo);
                                BigDecimal equalDisbursment;
                                equalDisbursment = capitalBudgetTasks.getRemainingAmount().divide(new BigDecimal(12), 2, RoundingMode.HALF_EVEN);

                                cbDisbursement.setHamle(equalDisbursment);
                                cbDisbursement.setNehasie(equalDisbursment);
                                cbDisbursement.setMeskerem(equalDisbursment);
                                cbDisbursement.setTikemt(equalDisbursment);
                                cbDisbursement.setHidar(equalDisbursment);
                                cbDisbursement.setTahsas(equalDisbursment);
                                cbDisbursement.setTir(equalDisbursment);
                                cbDisbursement.setYekatit(equalDisbursment);
                                cbDisbursement.setMegabit(equalDisbursment);
                                cbDisbursement.setMiyaziya(equalDisbursment);
                                cbDisbursement.setGinbot(equalDisbursment);
                                cbDisbursement.setSene(equalDisbursment);

                                cbDisbursementTo.setHamle(cbDisbursementTo.getHamle().add(transferAmount));
                                cBDisbursementBeanLocal.edit(cbDisbursement);
                                cBDisbursementBeanLocal.edit(cbDisbursementTo);

                                JsfUtil.addSuccessMessage("Transfer is approved succesfully.");
                            }
                        } else if (cbTransferDetail.getStatus() == Constants.PREPARE_REJECT_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.REQUEST_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.APPROVE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            } else if ("Approve".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.PREPARE_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            }
                            JsfUtil.addSuccessMessage("Transfer is prepared succesfully.");
                        } else if (cbTransferDetail.getStatus() == Constants.APPROVE_REJECT_VALUE) {
                            if ("Reject".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.PREPARE_REJECT_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_REJECT_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            } else if ("Approve".equals(decisionType)) {
                                cbTransferDetail.setStatus(Constants.PREPARE_VALUE);
                                transferDetailBeanLocal.update(cbTransferDetail);

                                wfFcmsProcessed.setCbTransferDtlId(cbTransferDetail);
                                wfFcmsProcessed.setCommentGiven(userRemark);
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(SessionBean.getUserId()));
                                wfFcmsProcessed.setProcessedOn(userRemarkDate);
                                wfFcmsProcessed.setDecision(Constants.PREPARE_VALUE);
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                            }
                        }
                    }
                }
                resetCBTrans();

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
    //<editor-fold defaultstate="collapsed" desc="getSystemSearchList">

    public SelectItem[] getSystemSearchList() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findProjSystem(), true);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="sampl">
    //</editor-fold>

}
