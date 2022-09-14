package et.gov.eep.fcms.controller.Voucher;
//<editor-fold defaultstate="collapsed" desc="Imports">

import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.BondForeignScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.CashReceiptVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowansBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsAccountUseResult;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.fcms.mapper.FmsLuPaymentTypeFacade;
import et.gov.eep.hrms.businessLogic.insure.HrinsurancePaymentLocal;
import et.gov.eep.hrms.businessLogic.medical.CashRefundApproveBeanLocal;
import et.gov.eep.hrms.businessLogic.medical.LocalMedSettlementBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import et.gov.eep.hrms.entity.insurance.HrInsurancePaymentDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlementDetail;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.hrms.entity.payroll.HrPayrollPeriods;
import et.gov.eep.pms.businessLogic.PmsProjectPaymentReqBeanLocal;
import et.gov.eep.pms.entity.PmsProjectPaymentRequest;
import et.gov.eep.prms.businessLogic.RequestforPaymentBeanLocal;
import et.gov.eep.prms.entity.PrmsPaymentRequisition;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

@Named(value = "cashPaymentOrderController")
@ViewScoped
public class CashPaymentOrderController implements Serializable {
//<editor-fold defaultstate="collapsed" desc="CashPayment Order Controller">

    public CashPaymentOrderController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    //private EJB 
    @EJB
    private FmsAccountUseBeanLocal fmsAccountUseBeanLocal;
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    CashReceiptVoucherBeanLocal cashPaymentOrderBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    @EJB
    FmsLuPaymentTypeFacade paymentTypeFacade;
    @EJB
    CashRefundApproveBeanLocal cashRefundApproveBeanLocal;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransactionLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsFacadeLocal;
    @EJB
    FmsFieldAllowansBeanLocal fieldAllowansBeanLocal;
    @EJB
    HrinsurancePaymentLocal hrinsurancePaymentLocal;
    @EJB
    PmsProjectPaymentReqBeanLocal pmsProjectPaymentReqBeanLocal;
    @EJB
    RequestforPaymentBeanLocal prmsRequestforPaymentBeanLocal;
    @EJB
    BondLocalScheduleBeanLocal bondLocalScheduleBeanLocal;
    @EJB
    BondForeignScheduleBeanLocal bondForeignScheduleBeanLocal;
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;

    //private dependecy injections
    @Inject
    private FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;

    //dependecy enjections
    @Inject
    LocalMedSettlementBeanLocal localMedSettlementBeanLocal;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    FmsCashPaymentOrder fmsCashPaymentOrder;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    FmsLuPaymentType luPaymentType;
    @Inject
    FmsFieldAllowance fieldAllowance;
    @Inject
    HrInsurancePaymentDetail hrInsurancePaymentDetail;
    @Inject
    WorkFlow workFlow;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsVouchersNoRange fmsVouchersNoRange;
    @Inject
    localbean Languagelocalbean;

    //list obcet declarations
    List<FmsVoucher> listVoucher = null;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<FmsVoucher> VoucherList;
    List<HrLocalMedSettlementDetail> hrmedSettlmentDetailList;
    List<HrPayrollMonTransactions> hrPayrollMonTransactionsList;
    List<FmsFieldAllowance> fieldAllowances;
    List<HrInsurancePaymentDetail> hrInsurancePaymentList;
    List<PmsProjectPaymentRequest> pmsProjectPaymentReqsList;
    List<PrmsPaymentRequisition> prmsPaymentRequisitionsList;
    List<String> referenceNumbers;
    List<FmsAccountUseResult> accountUseResults = new ArrayList<>();
    List<FmsGeneralLedger> fmsGeneralLedgerList;
    List<FmsLuSystem> fmsLuSystemList;
    List<FmsCostCenter> fmsCostCenterList;

    private List<FmsLuSystem> listOfSystems;
    private List<FmsBondLocalSchedule> bondLocalSchedules;
    private List<FmsBondForeignSchedule> bondForeignSchedules;
    private List<FmsVoucher> selectedJv;
    private List<FmsLuPaymentType> fmsLuPaymentType;
    private List<Map.Entry<String, BigDecimal>> entries;
    private List<Map.Entry<String, BigDecimal>> entries_;

    private HashMap<String, BigDecimal> accountDebit = new HashMap<>();
    private HashMap<String, BigDecimal> accountcredit = new HashMap<>();

    //datamodel declarations
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsAccountUse> accountUseDetailDataModelposting;
    DataModel<FmsVoucher> voucherDataModel;
    DataModel<WfFcmsProcessed> workflowDataModel;
    DataModel<FmsCashPaymentOrder> journalVoucherDataModel;

    //private string variables declaration
    private String Conc = "";
    private String display_conn;
    private String generalLName;
    private String saveorUpdateBundle = "Create";
    private String ActionDebitCredit = "";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String costName;
    private String subLName;
    private String selCodedTransactionVal = "codedTransaction";
    private String selPayrollSummaryVal = "payrollSummary";
    private String selOtherVal = "other";
    private String accountNo;
    private String sysName;
    private String createOrSearchBundle1 = "New";
    private String icone1 = "ui-icon-plus";
    String voucherId = null;
    String voucherNO = "";
    String processedBy = "";
    String types = "Prepared By";
    String reftype = "nun";

    //boolean variable declarations
    boolean new1 = false;
    boolean renderJobNo = false;
    boolean refRender = false;
    Boolean addButtonStatus = false;
    Boolean selectOptionStatus = false;
    Boolean editRemoveStatus = false;
    Boolean decision = false;
    boolean refbondlocal = false;
    boolean refbondforeign = false;
    boolean refdisable = true;
    boolean others = false;
    boolean saveVochNo = false;
    boolean popvocjNo = false;
    boolean cc = true;
    boolean sl = true;
    boolean createNEWrend = false;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    private boolean other = true;
    private Boolean projOption = false;
    private Boolean projRender = false;
    private Boolean printe = false;
    private Boolean void_dis = true;

    //constant variable declarations
    final Integer projectType = 2;
    final Integer nonProjectType = 1;

    //int variable declarations
    int updteStatus = 0;
    int onupdate = 0;
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    int MajorCatagorylistId = 0;
    int dataTableUpdateStatus = 0;

    //big decimal variable declarations
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);
    private BigDecimal value;

    //private objectr declarations
    private FmsAccountUse selectedAccountUses;
    private NumberConverter numberConverter = new NumberConverter();
    FmsAccountUseResult accountUseResult;

    List<FmsSubsidiaryLedger> FmsSubsidiaryLedgerList;
    Set<BigInteger> checkDebitCredit = new HashSet<>();
    Set<String> checkSubsidiaryCode = new HashSet<>();
    List<HashMap> list = new ArrayList<HashMap>() {
    };
    double Credit = 0.0;
    double Debit = 0.0;
    double totalamt = 0.0;
    List<FmsGeneralLedger> listGeneralLedger = null;
    int GLListSize = 0;
    private List<FmsVoucher> selectedVouches;
    String rederedCheckBox = "true";
    private DataModel<FmsVoucher> vouchersDetailDataModel;
    private String PRStatus;
    List<FmsVoucher> vouchersList = new ArrayList<>();

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public boolean isNew1() {
        return new1;
    }

    public void setNew1(boolean new1) {
        this.new1 = new1;
    }

    public String getIcone1() {
        return icone1;
    }

    public void setIcone1(String icone1) {
        this.icone1 = icone1;
    }

    public boolean isCreateNEWrend() {
        return createNEWrend;
    }

    public void setCreateNEWrend(boolean createNEWrend) {
        this.createNEWrend = createNEWrend;
    }

    public String getCreateOrSearchBundle1() {
        return createOrSearchBundle1;
    }

    public void setCreateOrSearchBundle1(String createOrSearchBundle1) {
        this.createOrSearchBundle1 = createOrSearchBundle1;
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

    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    public FmsCostcSystemJunction getFmsCostcSystemJunction() {
        if (fmsCostcSystemJunction == null) {
            fmsCostcSystemJunction = new FmsCostcSystemJunction();
        }
        return fmsCostcSystemJunction;
    }

    public void setFmsCostcSystemJunction(FmsCostcSystemJunction fmsCostcSystemJunction) {
        this.fmsCostcSystemJunction = fmsCostcSystemJunction;
    }

    public FmsSystemJobJunction getFmsSystemJobJunction() {
        if (fmsSystemJobJunction == null) {
            fmsSystemJobJunction = new FmsSystemJobJunction();
        }
        return fmsSystemJobJunction;
    }

    public void setFmsSystemJobJunction(FmsSystemJobJunction fmsSystemJobJunction) {
        this.fmsSystemJobJunction = fmsSystemJobJunction;
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

    public FmsSubsidiaryLedger getFmsSubsidiaryLedger() {
        if (fmsSubsidiaryLedger == null) {
            fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
        }
        return fmsSubsidiaryLedger;
    }

    public void setFmsSubsidiaryLedger(FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        this.fmsSubsidiaryLedger = fmsSubsidiaryLedger;
    }

    public List<FmsCostcSystemJunction> getSsCcJunctionList() {
        if (ssCcJunctionList == null) {
            ssCcJunctionList = new ArrayList<>();
        }
        return ssCcJunctionList;
    }

    public void setSsCcJunctionList(List<FmsCostcSystemJunction> ssCcJunctionList) {
        this.ssCcJunctionList = ssCcJunctionList;
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

    public List<FmsGeneralLedger> getGlList() {
        if (glList == null) {
            glList = new ArrayList<>();
        }
        return glList;
    }

    public void setGlList(List<FmsGeneralLedger> glList) {
        this.glList = glList;
    }

    public List<FmsSubsidiaryLedger> getSlList() {
        if (slList == null) {
            slList = new ArrayList<>();
        }
        return slList;
    }

    public void setSlList(List<FmsSubsidiaryLedger> slList) {
        this.slList = slList;
    }

    public List<FmsLuSystem> getSystemList() {
        if (systemList == null) {
            systemList = new ArrayList<>();
        }
        systemList = fmsLuSystemBeanLocal.findAll();
        return systemList;
    }

    public void setSystemList(List<FmsLuSystem> systemList) {
        this.systemList = systemList;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    public boolean isRenderJobNo() {
        return renderJobNo;
    }

    public void setRenderJobNo(boolean renderJobNo) {
        this.renderJobNo = renderJobNo;
    }

    public List<FmsVoucher> getSelectedVouches() {
        return selectedVouches;
    }

    public void setSelectedVouches(List<FmsVoucher> selectedVouches) {
        this.selectedVouches = selectedVouches;
    }

    public String getRederedCheckBox() {
        return rederedCheckBox;
    }

    public void setRederedCheckBox(String rederedCheckBox) {
        this.rederedCheckBox = rederedCheckBox;
    }

    public Boolean getVoid_dis() {
        return void_dis;
    }

    public void setVoid_dis(Boolean void_dis) {
        this.void_dis = void_dis;
    }

    public DataModel<FmsVoucher> getVouchersDetailDataModel() {
        if (vouchersDetailDataModel == null) {
            vouchersDetailDataModel = new ListDataModel<>();
        }
        return vouchersDetailDataModel;
    }

    public void setVouchersDetailDataModel(DataModel<FmsVoucher> vouchersDetailDataModel) {
        this.vouchersDetailDataModel = vouchersDetailDataModel;
    }

    public List<FmsVoucher> getVouchersList() {
        return vouchersList;
    }

    public void setVouchersList(List<FmsVoucher> vouchersList) {
        this.vouchersList = vouchersList;
    }

    public int getDataTableUpdateStatus() {
        return dataTableUpdateStatus;
    }

    public void setDataTableUpdateStatus(int dataTableUpdateStatus) {
        this.dataTableUpdateStatus = dataTableUpdateStatus;
    }

    public String getPRStatus() {
        return PRStatus;
    }

    public void setPRStatus(String PRStatus) {
        this.PRStatus = PRStatus;
    }

    public void setFmsSubsidiaryLedgerList(List<FmsSubsidiaryLedger> FmsSubsidiaryLedgerList) {
        this.FmsSubsidiaryLedgerList = FmsSubsidiaryLedgerList;
    }

    public double getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(double totalamt) {
        this.totalamt = totalamt;
    }

    public double getCredit() {
        return Credit;
    }

    public void setCredit(double Credit) {
        this.Credit = Credit;
    }

    public double getDebit() {
        return Debit;
    }

    public void setDebit(double Debit) {
        this.Debit = Debit;
    }

    public String getSubLName() {
        return subLName;
    }

    public void setSubLName(String subLName) {
        this.subLName = subLName;
    }

    public String getGeneralLName() {
        return generalLName;
    }

    public void setGeneralLName(String generalLName) {
        this.generalLName = generalLName;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public void setFmsCostCenterList(List<FmsCostCenter> fmsCostCenterList) {
        this.fmsCostCenterList = fmsCostCenterList;
    }

    public List<FmsGeneralLedger> getFmsGeneralLedgerList() {
        return fmsGeneralLedgerBeanLocal.findAll();
    }

    public void setFmsGeneralLedgerList(List<FmsGeneralLedger> fmsGeneralLedgerList) {
        this.fmsGeneralLedgerList = fmsGeneralLedgerList;
    }

    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    public List<FmsLuSystem> getListOfSystems() {
        listOfSystems = fmsLuSystemBeanLocal.findAll();
        return listOfSystems;
    }

    public void setListOfSystems(List<FmsLuSystem> listOfSystems) {
        this.listOfSystems = listOfSystems;
    }

    public List<FmsLuSystem> getFmsLuSystemList() {
        return fmsLuSystemBeanLocal.findAll();
    }

    public void setFmsLuSystemList(List<FmsLuSystem> fmsLuSystemList) {
        this.fmsLuSystemList = fmsLuSystemList;
    }

    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subLedgerBeanLocal.findAll(), true);
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public List<FmsAccountUseResult> getAccountUseResults() {
        return accountUseResults;
    }

    public void setAccountUseResults(List<FmsAccountUseResult> accountUseResults) {
        this.accountUseResults = accountUseResults;
    }

    public FmsAccountUseResult getAccountUseResult() {
        return accountUseResult;
    }

    public void setAccountUseResult(FmsAccountUseResult accountUseResult) {
        this.accountUseResult = accountUseResult;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public String getVoucherNO() {
        return voucherNO;
    }

    public void setVoucherNO(String voucherNO) {
        this.voucherNO = voucherNO;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public boolean isSaveVochNo() {
        return saveVochNo;
    }

    public void setSaveVochNo(boolean saveVochNo) {
        this.saveVochNo = saveVochNo;
    }

    public boolean isPopvocjNo() {
        return popvocjNo;
    }

    public void setPopvocjNo(boolean popvocjNo) {
        this.popvocjNo = popvocjNo;
    }

    public List<FmsBondForeignSchedule> getBondForeignSchedules() {
        bondForeignSchedules = bondForeignScheduleBeanLocal.searchForingns_Bondpayed();
        return bondForeignSchedules;
    }

    public void setBondForeignSchedules(List<FmsBondForeignSchedule> bondForeignSchedules) {
        this.bondForeignSchedules = bondForeignSchedules;
    }

    public List<FmsBondLocalSchedule> getBondLocalSchedules() {
        if (bondLocalSchedules == null) {
            bondLocalSchedules = new ArrayList<>();
        }
        return bondLocalSchedules;
    }

    public void setBondLocalSchedules(List<FmsBondLocalSchedule> bondLocalSchedules) {
        this.bondLocalSchedules = bondLocalSchedules;
    }

    public DataModel<WfFcmsProcessed> getWorkflowDataModel() {
        if (workflowDataModel == null) {
            workflowDataModel = new ArrayDataModel<>();
        }
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfFcmsProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public WorkFlow getWorkFlow() {
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

    public FmsLuPaymentType getLuPaymentType() {
        return luPaymentType;
    }

    public void setLuPaymentType(FmsLuPaymentType luPaymentType) {
        this.luPaymentType = luPaymentType;
    }

    public List<HrLocalMedSettlementDetail> getHrmedSettlmentDetailList() {
        if (hrmedSettlmentDetailList == null) {
            hrmedSettlmentDetailList = new ArrayList<>();
        }
        return hrmedSettlmentDetailList;
    }

    public void setHrmedSettlmentDetailList(List<HrLocalMedSettlementDetail> hrmedSettlmentDetailList) {
        this.hrmedSettlmentDetailList = hrmedSettlmentDetailList;
    }

    public boolean isRefRender() {
        return refRender;
    }

    public void setRefRender(boolean refRender) {
        this.refRender = refRender;
    }

    public List<FmsVoucher> getListVoucher() {
        if (listVoucher == null) {
            listVoucher = new ArrayList<>();
        }
        return listVoucher;
    }

    public void setListVoucher(List<FmsVoucher> listVoucher) {
        this.listVoucher = listVoucher;
    }

    public Boolean getPrinteCashPaymentOrder() {
        return printe;
    }

    public void setPrinteCashPaymentOrder(Boolean printe) {
        this.printe = printe;
    }

    public Boolean getProjOption() {
        return projOption;
    }

    public void setProjOption(Boolean projOption) {
        this.projOption = projOption;
    }

    public Boolean getProjRender() {
        return projRender;
    }

    public void setProjRender(Boolean projRender) {
        this.projRender = projRender;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateJv() {
        return renderPnlCreateJv;
    }

    public void setRenderPnlCreateJv(boolean renderPnlCreateJv) {
        this.renderPnlCreateJv = renderPnlCreateJv;
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

    public List<String> getReferenceNumbers() {
        return referenceNumbers;
    }

    public void setReferenceNumbers(List<String> referenceNumbers) {
        this.referenceNumbers = referenceNumbers;
    }

    public DataModel<FmsVoucher> getVoucherDataModel() {
        if (voucherDataModel == null) {
            voucherDataModel = new ListDataModel<>();
        }
        return voucherDataModel;
    }

    public void setVoucherDataModel(DataModel<FmsVoucher> voucherDataModel) {
        this.voucherDataModel = voucherDataModel;
    }

    public Boolean getEditRemoveStatus() {
        return editRemoveStatus;
    }

    public void setEditRemoveStatus(Boolean editRemoveStatus) {
        this.editRemoveStatus = editRemoveStatus;
    }

    public Boolean getSelectOptionStatus() {
        return selectOptionStatus;
    }

    public void setSelectOptionStatus(Boolean selectOptionStatus) {
        this.selectOptionStatus = selectOptionStatus;
    }

    public boolean isCc() {
        return cc;
    }

    public void setCc(boolean cc) {
        this.cc = cc;
    }

    public boolean isSl() {
        return sl;
    }

    public void setSl(boolean sl) {
        this.sl = sl;
    }

    public String getSelCodedTransactionVal() {
        return selCodedTransactionVal;
    }

    public void setSelCodedTransactionVal(String selCodedTransactionVal) {
        this.selCodedTransactionVal = selCodedTransactionVal;
    }

    public String getSelPayrollSummaryVal() {
        return selPayrollSummaryVal;
    }

    public void setSelPayrollSummaryVal(String selPayrollSummaryVal) {
        this.selPayrollSummaryVal = selPayrollSummaryVal;
    }

    public String getSelOtherVal() {
        return selOtherVal;
    }

    public void setSelOtherVal(String selOtherVal) {
        this.selOtherVal = selOtherVal;
    }

    public FmsCostCenterBeanLocal getFmsCostCenterBeanLocal() {
        return fmsCostCenterBeanLocal;
    }

    public void setFmsCostCenterBeanLocal(FmsCostCenterBeanLocal fmsCostCenterBeanLocal) {
        this.fmsCostCenterBeanLocal = fmsCostCenterBeanLocal;
    }

    public FmsGeneralLedgerBeanLocal getFmsGeneralLedgerBeanLocal() {

        return fmsGeneralLedgerBeanLocal;
    }

    public void setFmsGeneralLedgerBeanLocal(FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal) {
        this.fmsGeneralLedgerBeanLocal = fmsGeneralLedgerBeanLocal;
    }

    public FmsLuSystemBeanLocal getFmsLuSystemBeanLocal() {
        return fmsLuSystemBeanLocal;
    }

    public void setFmsLuSystemBeanLocal(FmsLuSystemBeanLocal fmsLuSystemBeanLocal) {
        this.fmsLuSystemBeanLocal = fmsLuSystemBeanLocal;
    }

    public subsidiaryBeanLocal getSubLedgerBeanLocal() {
        return subLedgerBeanLocal;
    }

    public void setSubLedgerBeanLocal(subsidiaryBeanLocal subLedgerBeanLocal) {
        this.subLedgerBeanLocal = subLedgerBeanLocal;
    }

//    public FmsGeneralLedger getFmsGeneralLedger() {
//        if (fmsGeneralLedger == null) {
//            fmsGeneralLedger = new FmsGeneralLedger();
//        }
//        return fmsGeneralLedger;
//    }
//
//    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
//        this.fmsGeneralLedger = fmsGeneralLedger;
//    }
    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if (fmsSubsid1aryLedger1 == null) {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger1;
    }

    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
    }

//    public FmsLuSystem getFmsLuSystem() {
//        if (fmsLuSystem == null) {
//            fmsLuSystem = new FmsLuSystem();
//        }
//        return fmsLuSystem;
//    }
//
//    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
//        this.fmsLuSystem = fmsLuSystem;
//    }
//    public FmsCostCenter getFmsCostCenter() {
//        if (fmsCostCenter == null) {
//            fmsCostCenter = new FmsCostCenter();
//        }
//        return fmsCostCenter;
//    }
//
//    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
//        this.fmsCostCenter = fmsCostCenter;
//    }
    public boolean isOthers() {
        return others;
    }

    public void setOthers(boolean others) {
        this.others = others;
    }

    public FmsVouchersNoRange getFmsVouchersNoRange() {
        if (fmsVouchersNoRange == null) {
            fmsVouchersNoRange = new FmsVouchersNoRange();
        }
        return fmsVouchersNoRange;
    }

    public void setFmsVouchersNoRange(FmsVouchersNoRange fmsVouchersNoRange) {
        this.fmsVouchersNoRange = fmsVouchersNoRange;
    }

    public FmsDocumentFollowup getFmsDocumentFollowup() {
        if (fmsDocumentFollowup == null) {
            fmsDocumentFollowup = new FmsDocumentFollowup();

        }
        return fmsDocumentFollowup;
    }

    public void setFmsDocumentFollowup(FmsDocumentFollowup fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

    public FmsCashPaymentOrder getFmsCashPaymentOrder() {
        if (fmsCashPaymentOrder == null) {
            fmsCashPaymentOrder = new FmsCashPaymentOrder();
        }
        return fmsCashPaymentOrder;
    }

    public void setFmsCashPaymentOrder(FmsCashPaymentOrder fmsCashPaymentOrder) {
        this.fmsCashPaymentOrder = fmsCashPaymentOrder;
    }

    public BigDecimal getValueDebitCredit() {
        return ValueDebitCredit;
    }

    public void setValueDebitCredit(BigDecimal ValueDebitCredit) {
        this.ValueDebitCredit = ValueDebitCredit;
    }

    public String getActionDebitCredit() {
        return ActionDebitCredit;
    }

    public void setActionDebitCredit(String ActionDebitCredit) {
        this.ActionDebitCredit = ActionDebitCredit;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModel() {
        if (accountUseDetailDataModel == null) {
            accountUseDetailDataModel = new ListDataModel<>();
        }
        return accountUseDetailDataModel;
    }

    public void setAccountUseDetailDataModel(DataModel<FmsAccountUse> accountUseDetailDataModel) {
        this.accountUseDetailDataModel = accountUseDetailDataModel;
    }

    private List<FmsAccountUse> acUseDetil;

    public List<FmsAccountUse> getAcUseDetil() {

        return acUseDetil;
    }

    public void setAcUseDetil(List<FmsAccountUse> acUseDetil) {
        this.acUseDetil = acUseDetil;
    }

// enty and buss-bean and local
    public FmsAccountUseBeanLocal getFmsAccountUseBeanLocal() {
        return fmsAccountUseBeanLocal;
    }

    public void setFmsAccountUseBeanLocal(FmsAccountUseBeanLocal fmsAccountUseBeanLocal) {
        this.fmsAccountUseBeanLocal = fmsAccountUseBeanLocal;
    }

    public FmsAccountUse getAccountUseEnty() {
        if (accountUseEnty == null) {
            accountUseEnty = new FmsAccountUse();
        }
        return accountUseEnty;
    }

    public void setAccountUseEnty(FmsAccountUse accountUseEnty) {
        this.accountUseEnty = accountUseEnty;
    }

    public FmsVoucherBeanLocal getFmsVoucherBeanLocal() {
        return fmsVoucherBeanLocal;
    }

    public void setFmsVoucherBeanLocal(FmsVoucherBeanLocal fmsVoucherBeanLocal) {
        this.fmsVoucherBeanLocal = fmsVoucherBeanLocal;
    }

    public FmsVoucher getFmsVoucher() {
        if (fmsVoucher == null) {
            fmsVoucher = new FmsVoucher();
        }
        return fmsVoucher;
    }

    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    //--------------------------------------------
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getConc() {
        return Conc;
    }

    public void setConc(String Conc) {
        this.Conc = Conc;
    }

    public Boolean getAddButtonStatus() {
        return addButtonStatus;
    }

    public void setAddButtonStatus(Boolean addButtonStatus) {
        this.addButtonStatus = addButtonStatus;
    }

    public List<FmsVoucher> getSelectedJv() {
        return selectedJv;
    }

    public void setSelectedJv(List<FmsVoucher> selectedJv) {
        this.selectedJv = selectedJv;
    }

    public FmsAccountUse getSelectedAccountUses() {
        return selectedAccountUses;
    }

    public void setSelectedAccountUses(FmsAccountUse selectedAccountUses) {
        this.selectedAccountUses = selectedAccountUses;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModelposting() {
        accountUseDetailDataModelposting = new ListDataModel(vouchersDetailDataModel.getRowData().getFmsAccountUseList());
        return accountUseDetailDataModelposting;
    }

    public void setAccountUseDetailDataModelposting(DataModel<FmsAccountUse> accountUseDetailDataModelposting) {
        this.accountUseDetailDataModelposting = accountUseDetailDataModelposting;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    public Boolean getDecision() {
        return decision;
    }

    public void setDecision(Boolean decision) {
        this.decision = decision;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public boolean isRefdisable() {
        return refdisable;
    }

    public void setRefdisable(boolean refdisable) {
        this.refdisable = refdisable;
    }

    public boolean isRefbondlocal() {
        return refbondlocal;
    }

    public void setRefbondlocal(boolean refbondlocal) {
        this.refbondlocal = refbondlocal;
    }

    public boolean isRefbondforeign() {
        return refbondforeign;
    }

    public void setRefbondforeign(boolean refbondforeign) {
        this.refbondforeign = refbondforeign;
    }
    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="Ref - No">

    public List<FmsBondLocalSchedule> fetchBondLocalSchedules() {
        referenceNumbers = new ArrayList<>();
        bondLocalSchedules = bondLocalScheduleBeanLocal.searchpayedSchedule();
        for (int i = 0; i < bondLocalSchedules.size(); i++) {
            String ref = null;
            ref = bondLocalSchedules.get(i).getLocalBondId().getSerialNo() + "/" + bondLocalSchedules.get(i).getNoOfInstallmen();
            getReferenceNumbers().add(ref);
        }
        return bondLocalSchedules;
    }

    public List<FmsBondForeignSchedule> fetchBondForeignSchedules() {
        referenceNumbers = new ArrayList<>();
        bondForeignSchedules = bondForeignScheduleBeanLocal.searchForingns_Bondpayed();
        for (int i = 0; i < bondForeignSchedules.size(); i++) {
            String ref = null;
            ref = bondForeignSchedules.get(i).getBondForeignId().getSerialNo() + "/" + bondForeignSchedules.get(i).getNoOfInstallmen();
            getReferenceNumbers().add(ref);
        }
        return bondForeignSchedules;
    }

    public void ref(ValueChangeEvent event) {
        System.out.println("event.getNewValue().toString)==" + event.getNewValue().toString());
        if (!event.getNewValue().toString().isEmpty()) {
            reftype = event.getNewValue().toString();
            luPaymentType.setPaymentType(event.getNewValue().toString());
            if (reftype.matches("Medical")) {
                System.out.println("inside rer method___");
                referenceNumbers = new ArrayList<>();
                hrmedSettlmentDetailList = localMedSettlementBeanLocal.fetchApprovedPayments();
                if (!hrmedSettlmentDetailList.isEmpty()) {
                    for (HrLocalMedSettlementDetail hrmedSettlmentDetailList1 : hrmedSettlmentDetailList) {
                        HrLocalMedSettlements localMedObject;
                        localMedObject = cashRefundApproveBeanLocal.getSelectedRequest(hrmedSettlmentDetailList1.getId());
                        getReferenceNumbers().add(localMedObject.getReferenceNo());
                    }
                    refNoList();
                    refRender = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;
            } else if (reftype.matches("Local Bond")) {
                fetchBondLocalSchedules();
                refNoList();
                refRender = true;
            } else if (reftype.matches("Foreign Bond")) {
                fetchBondForeignSchedules();
                refNoList();
                refRender = true;
                others = true;
            } else if (reftype.matches("Court Order")) {
                referenceNumbers = new ArrayList<>();
                hrPayrollMonTransactionsList = hrPayrollMonthlyTransactionLocal.fetchCourtOrderPaymentsforCash();
                if (!hrPayrollMonTransactionsList.isEmpty()) {
                    for (HrPayrollMonTransactions hrPayrollMonTransactionsList1 : hrPayrollMonTransactionsList) {
                        HrPayrollMonTransactions monthlyPayrollObject;
                        HrPayrollPeriods hrPayrollPeriodsObj;
                        monthlyPayrollObject = hrPayrollMonthlyTransactionLocal.getSelectedPayment(hrPayrollMonTransactionsList1.getId());
                        hrPayrollPeriodsObj = hrPayrollPeriodsFacadeLocal.findPayrollPeriodById(monthlyPayrollObject.getPayrollPeriodId().getId().intValue());
                        getReferenceNumbers().add(monthlyPayrollObject.getId() + " - " + monthlyPayrollObject.getEmpId().toString() + " - " + hrPayrollPeriodsObj.getPaymentMadeForTheMonthOf());
                    }
                    refNoList();
                    refRender = true;
                }
                others = true;
            } else if (reftype.matches("Projects")) {
                referenceNumbers = new ArrayList<>();
                pmsProjectPaymentReqsList = pmsProjectPaymentReqBeanLocal.fetchNewProjectPayments();
                if (!pmsProjectPaymentReqsList.isEmpty()) {
                    pmsProjectPaymentReqsList.stream().forEach((pmsProjectPaymentReqsList1) -> {
                        getReferenceNumbers().add(pmsProjectPaymentReqsList1.getPaymentNo());
                    });
                    refNoList();
                    refRender = true;
                }
                others = true;
            } else if (reftype.matches("Field Allowance")) {
                referenceNumbers = new ArrayList<>();
                fieldAllowances = fieldAllowansBeanLocal.searchnotexitvocher();
                if (!fieldAllowances.isEmpty()) {
                    fieldAllowances.stream().forEach((fieldAllowanceobj) -> {
                        getReferenceNumbers().add(fieldAllowanceobj.getRequestNo());
                    });
                    refNoList();
                    refRender = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;
            } else if (reftype.matches("Payment Requisition")) {
                referenceNumbers = new ArrayList<>();
                prmsPaymentRequisitionsList = prmsRequestforPaymentBeanLocal.getPrmsPaymentRequisitions();
                if (!prmsPaymentRequisitionsList.isEmpty()) {
                    for (PrmsPaymentRequisition prmsPaymentRequisitions_ : prmsPaymentRequisitionsList) {
                        getReferenceNumbers().add(prmsPaymentRequisitions_.getReqPaymentNo());
                    }
                    refNoList();
                    refRender = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;
            } else if (reftype.matches("Insurance")) {
                referenceNumbers = new ArrayList<>();
                hrInsurancePaymentList = hrinsurancePaymentLocal.fetchNewInsurancePayments();
                if (!hrInsurancePaymentList.isEmpty()) {
                    for (HrInsurancePaymentDetail hrInsurancePaymentList1 : hrInsurancePaymentList) {

                        getReferenceNumbers().add(hrInsurancePaymentList1.getReferenceNo());
                    }
                    refNoList();
                    refRender = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;
            } else if (reftype.matches("Other")) {
                refRender = false;
                others = false;

            }
        }
    }

    public SelectItem[] refNoList() {
        SelectItem[] listSl = null;
        if (referenceNumbers.size() > 0) {
            listSl = new SelectItem[referenceNumbers.size() + 1];
            listSl[0] = new SelectItem(null, "--- Select One ---");
            for (int i = 0; i < referenceNumbers.size(); i++) {
                listSl[i + 1] = new SelectItem(referenceNumbers.get(i));
            }
        }
        return listSl;
    }

    public void referenceNumEvent(ValueChangeEvent event) {
        if (!(event.getNewValue().toString() == null)) {
            String ref = event.getNewValue().toString();
            if (luPaymentType.getPaymentType().equalsIgnoreCase("Medical")) {
                HrLocalMedSettlements localMedObject;
                HrLocalMedSettlementDetail localMedDetailObject;
                localMedObject = cashRefundApproveBeanLocal.fetchSettlement(event.getNewValue().toString());
                localMedDetailObject = localMedSettlementBeanLocal.fetchPayment(localMedObject);
                fmsCashPaymentOrder.setAmountInFigure(new BigDecimal(localMedDetailObject.getAmount()));
                fmsCashPaymentOrder.setPaidTo(localMedObject.getRequesterId().getFirstName());
                fmsCashPaymentOrder.setReferenceNo(localMedObject.getReferenceNo());
            } else if (luPaymentType.getPaymentType().matches("Local Bond")) {
                String splitref[] = event.getNewValue().toString().split("/");
                FmsBondLocalSchedule bondLocalSchedule = new FmsBondLocalSchedule();
                bondLocalSchedule.setSerialNo(splitref[0]);
                int inst = Integer.valueOf(splitref[1]);
                bondLocalSchedule.setNoOfInstallmen(inst);
                bondLocalSchedule = bondLocalScheduleBeanLocal.getSchedule_instlment(bondLocalSchedule);
                System.out.println("good");
                fmsCashPaymentOrder.setAmountInFigure(new BigDecimal(bondLocalSchedule.getPrincipal() + bondLocalSchedule.getPrincipal()));
                fmsCashPaymentOrder.setPaidTo(bondLocalSchedule.getLocalBondId().getBuyerFullName());
            } else if (reftype.matches("Foreign Bond")) {
                String splitref[] = event.getNewValue().toString().split("/");
                FmsBondForeignSchedule bondForeignSchedule = new FmsBondForeignSchedule();
                FmsBondForeign bondForeign = new FmsBondForeign();
                bondForeign.setSerialNo(splitref[0]);
                bondForeignSchedule.setBondForeignId(bondForeign);
                int instlement = Integer.valueOf(splitref[1]);
                bondForeignSchedule.setNoOfInstallmen(instlement);
                bondForeignSchedule = bondForeignScheduleBeanLocal.getForeignSchedule_intslment(bondForeignSchedule);
                fmsCashPaymentOrder.setAmountInFigure(new BigDecimal(bondForeignSchedule.getInterest()));
                fmsCashPaymentOrder.setPaidTo(bondForeignSchedule.getBondForeignId().getBuyerFullName());
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Court Order")) {
                HrPayrollMonTransactions monthlyPayrollObject;
                HrPayrollPeriods hrPayrollPeriodsObj;
                String IdPayroll = event.getNewValue().toString();
                String[] splitIdPyrl = IdPayroll.split("-");
                String id = splitIdPyrl[0];
                Long pkId = Long.parseLong(id.trim());
                String Emp = splitIdPyrl[1];
                monthlyPayrollObject = hrPayrollMonthlyTransactionLocal.getSelectedPayment(pkId);
                hrPayrollPeriodsObj = hrPayrollPeriodsFacadeLocal.findPayrollPeriodById(monthlyPayrollObject.getPayrollPeriodId().getId().intValue());
                fmsCashPaymentOrder.setAmountInFigure((monthlyPayrollObject.getAmount()));
                fmsCashPaymentOrder.setPaidTo(Emp);
                fmsCashPaymentOrder.setReferenceNo(String.valueOf(monthlyPayrollObject.getId()));
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Projects")) {
                PmsProjectPaymentRequest localPrjPaymentObject;
                localPrjPaymentObject = pmsProjectPaymentReqBeanLocal.getSelectePayment(event.getNewValue().toString());
                fmsCashPaymentOrder.setAmountInFigure(new BigDecimal(localPrjPaymentObject.getAmount()));
                fmsCashPaymentOrder.setPaidTo(localPrjPaymentObject.getJobNo());
                fmsCashPaymentOrder.setReferenceNo(localPrjPaymentObject.getPaymentNo());
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Insurance")) {
                HrInsurancePaymentDetail localInsuranceObject;
                localInsuranceObject = hrinsurancePaymentLocal.getSelectedPayment(event.getNewValue().toString());
                fmsCashPaymentOrder.setAmountInFigure(localInsuranceObject.getMedicalExpence());
                fmsCashPaymentOrder.setPaidTo(localInsuranceObject.getPaymentRequestId().getNameOfBank());
                fmsCashPaymentOrder.setReferenceNo(localInsuranceObject.getReferenceNo());
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Payment Requisition")) {
                PrmsPaymentRequisition paymentRequisition;
                paymentRequisition = prmsRequestforPaymentBeanLocal.getPrmsPaymentReq(event.getNewValue().toString());
                fmsCashPaymentOrder.setAmountInFigure(new BigDecimal(paymentRequisition.getTotalAmount()));
                fmsCashPaymentOrder.setPaidTo(paymentRequisition.getPaymentRecievedBy());
                fmsCashPaymentOrder.setReferenceNo(paymentRequisition.getReqPaymentNo());
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Field Allowance")) {
                FmsFieldAllowance fieldAllowanceObj = new FmsFieldAllowance();
                fieldAllowanceObj.setRequestNo(event.getNewValue().toString());
                System.out.println("fieldAllowanceObj.setRequestNo(event.getNewValue().toString()) ===" + fieldAllowanceObj.getRequestNo());
                fieldAllowance = fieldAllowansBeanLocal.getByrequestno(fieldAllowanceObj);
                fmsCashPaymentOrder.setAmountInFigure(new BigDecimal(fieldAllowance.getTotalExpense()));
                fmsCashPaymentOrder.setPaidTo(fieldAllowance.getEmpId().getEmpId());
                fmsCashPaymentOrder.setReferenceNo(fieldAllowance.getRequestNo());
            }
        }
    }

    public void refno() {
        if (reftype != null) {
            if (reftype.matches("Local Bond")) {
                refdisable = false;

            } else if (reftype.matches("Foreign Bond")) {
                System.out.println("notting" + reftype);

            } else {
                System.out.println("notting2 " + reftype);
            }

        } else {
            System.out.println("notting3");
        }
    }
    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="save - Update">

    public String saveCashPaymentOrder() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveCashPaymentOrder", dataset)) {
//                 put ur code here...! 
//                        if (ValidDebitCredit()) {
            double amt = fmsCashPaymentOrder.getAmountInFigure().doubleValue();
            if (((amt == Debit) && (Debit == Credit)) || (onupdate == 1)) {
                if (updteStatus == 0) {
                    String fmsVoucherID, preparedate;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    preparedate = dateFormat.format(fmsCashPaymentOrder.getPreparedDate());
                    fmsVoucherID = preparedate + "-" + Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + "-" + "CPO";
                    fmsVoucher.setVoucherId(fmsVoucherID);
                    if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher)) {
                        System.err.println("Voucher No. value is duplicated. Try again reloading the page!");
                        JsfUtil.addFatalMessage("Voucher No. value is duplicated. Try again reloading the page!");
                    } else {
                        if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                            JsfUtil.addFatalMessage("Data table shoud be filled");
                        } else {
                            try {
                                fmsCashPaymentOrder.setStatus(String.valueOf(Constants.PREPARE_VALUE));
                                fmsCashPaymentOrder.setPreparedBy(String.valueOf(sessionBeanLocal.getUserId()));
                                fmsVoucher.setProcessedBy(String.valueOf(wfFcmsProcessed.getProcessedBy()));
                                fmsVoucher.setVoucherId(fmsVoucherID);
                                fmsVoucher.setStatus(Constants.PREPARE_VALUE);
                                fmsVoucher.setType("CPO");
                                fmsVoucher.setVoucherNo(fmsVouchersNoRange.getCurrentNo());
                                fmsVoucher.addToFmsCashPaymentOrderDetail(fmsCashPaymentOrder);
                                int currentNo1 = Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + 1;
                                fmsVouchersNoRange.setCurrentNo(String.valueOf(currentNo1));

                                noRangeBeanLocal.edit(fmsVouchersNoRange);
                                wfFcmsProcessed.setFmsVoucherId(fmsVoucher);
                                System.out.println("wfFcmsProcessed.setFmsVoucherId(fmsVoucher)===" + wfFcmsProcessed.getFmsVoucherId());
                                wfFcmsProcessed.setDecision(fmsVoucher.getStatus());
                                wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                                wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getPreparedDate()));
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(sessionBeanLocal.getUserId()));
                                fmsVoucherBeanLocal.create(fmsVoucher);
                                System.out.println("befor wf cration");
                                wfFcmsProcessedBeanLocal.create(wfFcmsProcessed);
                                System.out.println("after wf cration");
                                JsfUtil.addSuccessMessage("Cash Payment Voucher data saved successfully!");
                                return clearPage();
                            } catch (Exception e) {
                                e.printStackTrace();
                                JsfUtil.addFatalMessage("Error!. Something occured on data created");
                                return null;
                            }
                        }
                    }
                } else if (updteStatus == 1) {
                    if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data table shoud be filled");
                        return null;
                    }
                    fmsVoucherBeanLocal.edit(fmsVoucher);
                    JsfUtil.addSuccessMessage("Cash Voucher data update successfully!");
                    selectOptionStatus = false;
                    return clearPage();
                } else if (updteStatus == 3) {
                    if (listVoucher.size() > 0) {
                        try {
                            for (int i = 0; i < listVoucher.size(); i++) {
                                FmsVoucher fmsVoucher = new FmsVoucher();
                                fmsVoucher = listVoucher.get(i);
                                fmsVoucher.setStatus(3);
                                fmsVoucherBeanLocal.edit(listVoucher.get(i));
                                fmsVoucherBeanLocal.edit(fmsVoucher);
                                JsfUtil.addSuccessMessage("Cash Payment Voucher Data is posting Done");
                            }
                            return clearPage();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Occured on Data Posting");
                            return null;
                        }
                    } else {
                        JsfUtil.addFatalMessage("Select one to Posting");
                        return null;
                    }
                } else {
                    JsfUtil.addFatalMessage("Pls check for balance");
                    return null;
                }
            }
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(sessionBeanLocal.getSessionID());
            eventEntry.setUserId(sessionBeanLocal.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
            eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
            security.addEventLog(eventEntry, dataset);
        }
        return null;
    }

    public String clearPage() {
        fmsVoucher = null;
        fmsCashPaymentOrder = null;
        accountUseEnty = null;
        accountUseDetailDataModel = null;
        updteStatus = 0;
        listVoucher = null;
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public List<FmsLuPaymentType> getFmsLuPaymentType() {
        FmsLuPaymentType flpt = new FmsLuPaymentType();
        flpt.setPayment("PV");
        return paymentTypeFacade.findByPaymentType(flpt);
    }

    public void setFmsLuPaymentType(List<FmsLuPaymentType> fmsLuPaymentType) {
        this.fmsLuPaymentType = fmsLuPaymentType;
    }

    public void OptionChecker() {
        if (projOption == true) {
            accountUseDetailDataModel = null;
            addButtonStatus = true;
            projRender = true;
        } else {
            projRender = false;
        }

    }

    public List<FmsVoucher> JVSearchlist(String voucheid) {
        listVoucher = null;
        fmsVoucher.setVoucherId(voucheid);
        listVoucher = fmsVoucherBeanLocal.searchVoucheidTypeJV(fmsVoucher);
        return listVoucher;
    }

    public void searchCashPaymentByVoucherId() {
        //find all
        if (!(voucherId.isEmpty())) {
            //find by begins with
            fmsVoucher.setVoucherId(voucherId);
            VoucherList = fmsVoucherBeanLocal.searchVoucheidTypeCPO(fmsVoucher);
            recreateJvDataModel();
        } else if (voucherId.isEmpty()) {
            fmsVoucher.setType("CPO");
            VoucherList = fmsVoucherBeanLocal.findAllbytype(fmsVoucher);
            recreateJvDataModel();
        }
    }

    public void getVoucherInformation(SelectEvent event) {
        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        String splitedVoucherNO[] = fmsVoucher.getVoucherId().split("-");
        clearPopup();
        setVoucherNO(splitedVoucherNO[1]);
        recreateModelDetail();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        updteStatus = 1;
        saveorUpdateBundle = "Update";

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" PostConstruct getCurrentNumber - Implimentation"> 
    //FmsVouchersNoRange vouchersNoRange = new FmsVouchersNoRange();
    @PostConstruct
    public void getCurrentVoucherNumber() {
        String refNo = null;
        Integer seqNo = 0;
        fmsVouchersNoRange.setStatus(Constants.CHECK_APPROVE_VALUE);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(3);
        fmsVouchersNoRange.setTypeId(luVouchersType);
        voucherId = null;
        fmsVoucher.setProcessedBy(workFlow.getUserName());
        if (workFlow.isPrepareStatus()) {
            processedBy = "Prepared By";
            decision = true;
        } else if (workFlow.isApproveStatus()) {
            processedBy = "Approved By";
            decision = false;
        } else if (workFlow.isCheckStatus()) {
            processedBy = "check By";
            decision = true;
        }
        if (Languagelocalbean.getLangsession().getAttribute("lang") != null) {
            if (Languagelocalbean.getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
                Languagelocalbean.changeLanguage("ET");
            } else {
                Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
            }
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreatecash payment order DataModel">
    public void recreateJvDataModel() {
        voucherDataModel = null;
        voucherDataModel = new ListDataModel(new ArrayList(fmsVouchersList1));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create New Cash Payment Order">
    public void createNewCashPaymentOrder() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateJv = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            saveVochNo = true;
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateJv = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            saveVochNo = false;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create New cash payment order">

    public void createNewJv1() {
        clearPage();
        clearPopup();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderPnlManPage = false;
        createOrSearchBundle1 = "New";
        setIcone1("ui-icon-plus");
        selectOptionStatus = false;
        saveVochNo = false;
        new1 = false;
        createNEWrend = false;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate">
    public void populate(SelectEvent event) {
        fmsVoucher = (FmsVoucher) event.getObject();
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        clearPopup();
        popvocjNo = true;
        for (int i = 0; i < fmsVoucher.getFmsAccountUseList().size(); i++) {
            int CR = 0;
            int dB = 0;
            if (fmsVoucher.getFmsAccountUseList().get(i).getCredit().doubleValue() != 0
                    && fmsVoucher.getFmsAccountUseList().get(i).getDebit().doubleValue() == 0) {
                String Ac = fmsVoucher.getFmsAccountUseList().get(i).getDisplay_conn();
                setValue(fmsVoucher.getFmsAccountUseList().get(i).getCredit());
                setAccountNo(Ac);
                accountcredit.put(Ac, value);
            }
            if (fmsVoucher.getFmsAccountUseList().get(i).getDebit().doubleValue() != 0) {
                String Ac = fmsVoucher.getFmsAccountUseList().get(i).getDisplay_conn();
                setValue(fmsVoucher.getFmsAccountUseList().get(i).getDebit());
                setAccountNo(Ac);
                accountDebit.put(Ac, value);
            }
        }
        for (int i = 0; i < accountUseResults.size(); i++) {
            System.out.println("a/c - " + accountUseResults.get(i).getAC_NO_CREDIT()
                    + "Creadt = " + accountUseResults.get(i).getCREDIT() + "a/c - " + accountUseResults.get(i).getAC_NO_DEBIT()
                    + "Debit = " + accountUseResults.get(i).getDEBIT());
        }
        entries_ = new ArrayList<>(accountcredit.entrySet());
        for (int i = 0; i < entries_.size(); i++) {
            accountUseResult = new FmsAccountUseResult();
            accountUseResult.setAC_NO_CREDIT(entries_.get(i).getKey());
            accountUseResult.setCREDIT(entries_.get(i).getValue().doubleValue());
            accountUseResults.add(accountUseResult);
        }
        entries = new ArrayList<>(accountDebit.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            if ((entries.size() <= entries_.size()) || i < entries_.size()) {
                accountUseResult = new FmsAccountUseResult();
                accountUseResult = accountUseResults.get(i);
                accountUseResult.setAC_NO_DEBIT(entries.get(i).getKey());
                accountUseResult.setDEBIT(entries.get(i).getValue().doubleValue());
                accountUseResults.set(i, accountUseResult);
            } else {
                accountUseResult = new FmsAccountUseResult();
                accountUseResult.setAC_NO_DEBIT(entries.get(i).getKey());
                accountUseResult.setDEBIT(entries.get(i).getValue().doubleValue());
                accountUseResults.add(accountUseResult);
            }
        }
        receactWorkflow();
        recreateModelDetail();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        updteStatus = 1;
        onupdate = 1;
        renderPnlCreateJv = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
        printe = true;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate Accunt Detail">

    public void populateAccDetail(SelectEvent event) {
        int accotId = Integer.parseInt(event.getObject().toString());
        accountUseEnty.setFmsAccountUseId(accotId);
        dataTableUpdateStatus = 1;
        String subsidari;
        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
        subsidari = getAccountUseDetailDataModel().getRowData().getSubsidaryId();
        HashMap<String, Integer> words_fre = new HashMap<String, Integer>();
        int x = 0;
        for (String retval : subsidari.split("-")) {
            System.out.println(retval);
            if (x == 0) {
                int sys = Integer.parseInt(retval);
                fmsLuSystem.setSystemId(sys);
                x = 1;
            } else if (x == 1 && !retval.matches("CC")) {
                int sys = Integer.parseInt(retval);
                fmsCostCenter.setCostCenterId(sys);
                x = 2;
            } else if (x == 2) {
                int sys = Integer.parseInt(retval);
                fmsGeneralLedger.setGeneralLedgerId(sys);
                x = 3;
            } else if (x == 3 && !retval.matches("SL")) {
                int sys = Integer.parseInt(retval);
                fmsSubsid1aryLedger1.setSubsidiaryId(sys);
            }
        }
        if (accountUseEnty.getCredit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getCredit();
            ActionDebitCredit = "Credit";
        }
        if (accountUseEnty.getDebit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getDebit();
            ActionDebitCredit = "Debit";
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="remove">
    public void remove() {
        accountUseEnty = null;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        fmsVoucher.getFmsAccountUseList().remove(accountUseEnty);
        recreateModelDetail();
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Concatenate">

    public void Concatenate() {
        String CC = "CC";
        String SL = "SL";
        String CC_display = "CC";
        String SL_display = "SL";
        if (fmsCostCenter.getCostCenterId() != null) {
            CC = fmsCostCenter.getCostCenterId().toString();
            CC_display = fmsCostCenter.getSystemName();
        }
        if (fmsSubsid1aryLedger1.getSubsidiaryCode() != null) {
            SL = fmsSubsid1aryLedger1.getSubsidiaryId().toString();
            SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
        }
        display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerCode() + "-" + SL_display;
        Conc = fmsLuSystem.getSystemId() + "-" + CC + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="receact Workflow">
    public void receactWorkflow() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(fmsVoucher.getWfFcmsProcessedList()));
        for (int i = 0; i < fmsVoucher.getWfFcmsProcessedList().size(); i++) {
            if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(0)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Prepared");
            } else if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(1) || fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(39)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Approved");
            } else if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(2) || fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(38)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Rejected");
            }
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="add Subsidiary Ledger Detail">
    public String addSubsidiaryLedgerDetail() {
        accountUseEnty = new FmsAccountUse();
        accountUseEnty.setSubsidaryId(fmsSubsidiaryLedger.getSubsidiaryCode());
        if (ActionDebitCredit.equals("Debit")) {
            accountUseEnty.setDebit(ValueDebitCredit);
            accountUseEnty.setCredit(new BigDecimal(0.00));
            accountUseEnty.setAmt(0 - accountUseEnty.getDebit().doubleValue());
        }
        if (ActionDebitCredit.equals("Credit")) {
            accountUseEnty.setDebit(new BigDecimal(0.00));
            accountUseEnty.setCredit(ValueDebitCredit);
            accountUseEnty.setAmt(accountUseEnty.getCredit().doubleValue());
        }
        if (checkSubsidiaryCode.contains(accountUseEnty.getSubsidaryId())) {
            if (checkDebitCredit.contains(accountUseEnty.getDebit()) && ActionDebitCredit.equals("Debit")) {
                JsfUtil.addErrorMessage("Duplicate Debit is not allowed with duplicate Subsidiary Code!");
                return null;
            } else if (checkDebitCredit.contains(accountUseEnty.getCredit()) && ActionDebitCredit.equals("Credit")) {
                JsfUtil.addErrorMessage("Duplicate Credit is not allowed with duplicate Subsidiary Code!");
                return null;
            }
        } else {
            Debit = Debit + accountUseEnty.getDebit().doubleValue();
            Credit = Credit + accountUseEnty.getCredit().doubleValue();
            fmsVoucher.addToFmsSubsidiaryLSrDetail(accountUseEnty);
            checkSubsidiaryCode.add(accountUseEnty.getSubsidaryId());
            totalamt = Credit - Debit;
            recreateModelDetail();
            return clearPopup();
        }
        return null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AccountUse,GeneralLedger,Subsidiary - Implimentation">
    public List<FmsCostCenter> getFmsCostCenterList() {
        fmsLuSystem = fmsCostCenter.getSystemId();
        List<FmsCostCenter> costcenterList = new ArrayList<>();

        if (fmsLuSystem != null) {
            costcenterList = fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem);
            if (costcenterList.size() > 0) {
                cc = false;
            } else {
                cc = true;
            }
            return fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem);
        } else {
            return null;
        }
    }

    public SelectItem[] getCostCenterBySystemLU() {
        fmsLuSystem = fmsCostCenter.getSystemId();
        List<FmsCostCenter> costcenterList = new ArrayList<>();
        if (fmsLuSystem != null) {
            SelectItem[] listcc = null;
            costcenterList = fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem);
            if (costcenterList.size() > 0) {
                cc = false;
            } else {
                cc = true;
            }
            return JsfUtil.getSelectItems(fmsCostCenterBeanLocal.findCostCenter(fmsLuSystem), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            return items;
        }
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            sysName = fmsLuSystem.getSystemName();
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }

    public void CostCenterChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            costName = fmsCostCenter.getDescription();
        }
    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            generalLName = fmsGeneralLedger.getAccountTitle();
            fmsSubsid1aryLedger1.setGeneralLedgerId(fmsGeneralLedger);
        }
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
        }
    }

    public List<FmsSubsidiaryLedger> getFmsSubsidiaryLedgerList() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            subsidaryList = subLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                sl = false;
            } else {
                sl = true;
            }
            return subsidaryList;
        } else {
            return null;
        }
    }

    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;
            subsidaryList = subLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                sl = false;
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "---Select One---");
                for (int i = 0; i < subsidaryList.size(); i++) {
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                }
            } else {
                sl = true;

            }
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--Select CC & GL--");
            return items;
        }
    }

    public String addCashPaymentOrderSubsidiaryLedgerDetail() {
        accountUseEnty = new FmsAccountUse();
        Concatenate();
        accountUseEnty.setSubsidaryId(Conc);
        accountUseEnty.setDisplay_conn(display_conn);
        if (ActionDebitCredit.equals("Debit")) {
            accountUseEnty.setDebit(ValueDebitCredit);
            accountUseEnty.setCredit(new BigDecimal(0.00));
            accountUseEnty.setAmt(0 - accountUseEnty.getDebit().doubleValue());
        }
        if (ActionDebitCredit.equals("Credit")) {
            accountUseEnty.setDebit(new BigDecimal(0.00));
            accountUseEnty.setCredit(ValueDebitCredit);
            accountUseEnty.setAmt(accountUseEnty.getCredit().doubleValue());
        }
        if (checkSubsidiaryCode.contains(accountUseEnty.getSubsidaryId())) {
            if (checkDebitCredit.contains(accountUseEnty.getDebit()) && ActionDebitCredit.equals("Debit")) {
                JsfUtil.addErrorMessage("Duplicate Debit is not allowed with duplicate Subsidiary Code!");
                return null;
            } else if (checkDebitCredit.contains(accountUseEnty.getCredit()) && ActionDebitCredit.equals("Credit")) {
                JsfUtil.addErrorMessage("Duplicate Credit is not allowed with duplicate Subsidiary Code!");
                return null;
            }
        } else {
            Debit = Debit + accountUseEnty.getDebit().doubleValue();
            Credit = Credit + accountUseEnty.getCredit().doubleValue();
            fmsVoucher.addToFmsSubsidiaryLSrDetail(accountUseEnty);
            checkSubsidiaryCode.add(accountUseEnty.getSubsidaryId());
            totalamt = Credit - Debit;
            recreateModelDetail();
            return clearPopup();
        }
        return null;
    }

    public String clearPopup() {
        accountUseEnty = null;
        fmsLuSystem = null;
        fmsSubsid1aryLedger1 = null;
        fmsCostCenter = null;
        fmsGeneralLedger = null;
        accountUseEnty = new FmsAccountUse();
        ValueDebitCredit = new BigDecimal(0.00);
        ActionDebitCredit = "";
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsLuSystem = new FmsLuSystem();
        fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        fmsCostCenter = new FmsCostCenter();
        return null;
    }

    public void deleteAccountUseDetail() {
        dataTableUpdateStatus = 1;
        fmsVoucher.removedata(getAccountUseDetailDataModel().getRowData());
        JsfUtil.addSuccessMessage("Data is removed");
    }

    public void updateAccountUseDetail() {
        dataTableUpdateStatus = 1;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
        if (accountUseEnty.getCredit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getCredit();
            ActionDebitCredit = "Credit";
        }
        if (accountUseEnty.getDebit().doubleValue() > 0) {
            ValueDebitCredit = accountUseEnty.getDebit();
            ActionDebitCredit = "Debit";
        }
    }

    public void recreateJVModelDetail() {
        fmsCashPaymentOrder = fmsVoucher.getFmsCashPaymentOrder();
    }

    public void recreateModelDetail() {
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" calc and check Debit Credit balance - Implimentation"> 

    public BigDecimal toBigDecimal(double val) {
        BigDecimal newVal = new BigDecimal(val);
        return newVal;
    }

    public boolean ValidDebitCredit() {
        double creditValid = 0.0;
        double debitValid = 0.0;
        int size = fmsVoucher.getFmsAccountUseList().size();
        for (int i = 0; i < size; i++) {
            creditValid = creditValid + fmsVoucher.getFmsAccountUseList().get(i).getCredit().longValue();
            debitValid = debitValid + fmsVoucher.getFmsAccountUseList().get(i).getDebit().longValue();
        }
        if (creditValid == debitValid) {
            return true;
        } else {
            return false;
        }
    }

    // </editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="posting - Implimentation"> 
    public void check(SelectEvent events) {
        fmsVoucher = new FmsVoucher();
        fmsVoucher = (FmsVoucher) events.getObject();
        listVoucher.add(fmsVoucher);
        void_dis = false;
    }

    public void allcheck(SelectEvent events) {
        fmsVoucher = new FmsVoucher();
        listVoucher = (List<FmsVoucher>) events.getObject();
        void_dis = false;
        updteStatus = 3;
    }

    public void uncheck(UnselectEvent uncheckevent) {
        System.err.println("uncheck");
        fmsVoucher = (FmsVoucher) uncheckevent.getObject();
        if (listVoucher.contains(fmsVoucher)) {
            listVoucher.remove(fmsVoucher);
            if (listVoucher.size() == 0) {
                void_dis = true;
            }
        }
    }

    public void alluncheck(UnselectEvent uncheckevent) {
        listVoucher.clear();
        void_dis = true;
    }

    public void recreateVoucherModelDetail() {
        vouchersDetailDataModel = null;
        vouchersDetailDataModel = new ListDataModel(vouchersList);
    }

    public void recreateAccountModelDetail() {
        System.err.println("fgggggggggggggggggggggggggggggggggggggggggggggggg");
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(getVouchersDetailDataModel().getRowData().getFmsAccountUseList());
    }

    public String GeneratePostingCpo() {
        vouchersList = fmsVoucherBeanLocal.handleSearchPost();
        for (int j = 0; j < vouchersList.size(); j++) {
            for (int i = 0; i < vouchersList.get(j).getFmsAccountUseList().size(); i++) {
                String splitedaccoutid[] = vouchersList.get(j).getFmsAccountUseList().get(i).getSubsidaryId().split("-");
                fmsLuSystem.setSystemId(Integer.parseInt(splitedaccoutid[0]));
                fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
                String CC_display = "CC";
                String SL_display = "SL";
                if (!"CC".equals(splitedaccoutid[1])) {
                    fmsCostCenter.setCostCenterId(Integer.parseInt(splitedaccoutid[1]));
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                    CC_display = fmsCostCenter.getSystemName();
                }
                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(splitedaccoutid[2]));
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
                if (!"SL".equals(splitedaccoutid[2])) {
                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(splitedaccoutid[3]));
                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
                }
                display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                vouchersList.get(j).getFmsAccountUseList().get(i).setDisplay_conn(display_conn);
            }
        }
        recreateVoucherModelDetail();
        updteStatus = 3;
        listVoucher = null;
        listVoucher = new ArrayList<>();
        return null;
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Chart of Account Handler: Account Code Handler. Author: Mubarek Shejebel">
    public void onChangeSystem(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsLuSystem = (FmsLuSystem) event.getNewValue();
                renderJobNo = false;
                if (Integer.valueOf(fmsLuSystem.getType()) == (projectType)) {
                    searchProjectType();
                    renderJobNo = true;
                    sysJobNOList = fmsLuSystem.getFmsSystemJobJunctionList();
                } else {
                    searchNonProjectType();
                }
                ssCcJunctionList = fmsLuSystem.getFmsCostcSystemJunctionList();
                fmsVouchersNoRange = fmsVoucherBeanLocal.getVoucherNo(fmsLuSystem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void searchProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(projectType);
    }

    public void searchNonProjectType() {
        glList = fmsGeneralLedgerBeanLocal.getByAccountType(nonProjectType);
    }

    public void onChangeCostCenter(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsCostcSystemJunction = (FmsCostcSystemJunction) event.getNewValue();
                fmsCostCenter = fmsCostcSystemJunction.getFmsCostCenter();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void onChangeGeneralLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                fmsGeneralLedger = (FmsGeneralLedger) event.getNewValue();
                slList = subsidiaryLedgerBeanLocal.findBySsCcJuncAndGL(fmsCostcSystemJunction, fmsGeneralLedger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void onChangeSubsidiaryLedger(ValueChangeEvent event) {
        try {
            if (!event.getNewValue().toString().isEmpty()) {
                fmsSubsidiaryLedger = (FmsSubsidiaryLedger) event.getNewValue();
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void onChangeJobNo(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsSystemJobJunction = (FmsSystemJobJunction) event.getNewValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("Unable to process. Try again reloading the page.");
        }
    }

    public void clearAccCodes() {
        fmsLuSystem = new FmsLuSystem();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsCostCenter = new FmsCostCenter();
        fmsSystemJobJunction = new FmsSystemJobJunction();
        fmsCostcSystemJunction = new FmsCostcSystemJunction();
        fmsSubsidiaryLedger = new FmsSubsidiaryLedger();
    }
//</editor-fold>

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsVoucher.setColumnName(event.getNewValue().toString());
            fmsVoucher.setColumnValue(null);
        }
    }
    List<FmsVoucher> FmsVoucherSearchingParameterList;

    public List<FmsVoucher> getFmsVoucherSearchingParameterList() {
        if (FmsVoucherSearchingParameterList == null) {
            FmsVoucherSearchingParameterList = new ArrayList<>();
            FmsVoucherSearchingParameterList = fmsVoucherBeanLocal.getFmsVoucherSearchingParameterList();
            System.out.println("FmsVoucherSearchingParameterList==" + FmsVoucherSearchingParameterList);
        }
        return FmsVoucherSearchingParameterList;

    }
    List<FmsVoucher> fmsVouchersList1;

    public void search_vouchers() {
        fmsVoucher.setPreparedBy(workFlow.getUserName());
        fmsVoucher.setType("CPO");
        fmsVouchersList1 = fmsVoucherBeanLocal.searchAllVochNo(fmsVoucher);
        recreateJvDataModel();
        fmsVoucher = new FmsVoucher();
    }

}
