package et.gov.eep.fcms.controller.Voucher;

  //<editor-fold defaultstate="collapsed" desc="import block">
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Voucher.CashReceiptVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowanceSettlementBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowansBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
//import et.gov.eep.fcms.controller.Constants;
import securityBean.Constants;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsAccountUseResult;
import et.gov.eep.fcms.entity.Vocher.FmsCashReceiptVoucher;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement;
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
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import et.gov.eep.hrms.entity.payroll.HrPayrollMonTransactions;
import et.gov.eep.pms.entity.PmsProjectPaymentRequest;
import et.gov.eep.prms.businessLogic.BidRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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

    //</editor-fold>
@Named(value = "cashReceiptVoucherController")
@ViewScoped
public class CashReceiptVoucherController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    //EJB object declaration
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    CashReceiptVoucherBeanLocal fmsJournalBeanLocal;
    @EJB
    private FmsAccountUseBeanLocal fmsAccountUseBeanLocal;
    @EJB
    FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    @Inject
    private FmsLuPaymentType luPaymentType;
    @EJB
    FmsLuPaymentTypeFacade paymentTypeFacade;
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    @EJB
    BidRegBeanLocal bidRegBeanLocal;
    @EJB
    FmsFieldAllowanceSettlementBeanLocal allowanceSettlementBeanLocal;
    @EJB
    FmsFieldAllowansBeanLocal allowansBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;

    //dependencey injections
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    FmsCashReceiptVoucher cashReceiptVoucher;
    @Inject
    FmsFieldAllowanceSettlement allowanceSettlement;
    @Inject
    FmsFieldAllowance fieldAllowance;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    PrmsBid prmsBid;
    @Inject
    PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    WorkFlow workFlow;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
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
    private FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;
    @Inject
    localbean Languagelocalbean;

    //list object declaration
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    List<FmsGeneralLedger> fmsGeneralLedgerList;
    List<FmsLuSystem> fmsLuSystemList;
    List<FmsCostCenter> fmsCostCenterList;
    List<FmsVoucher> VoucherList;
    List<PrmsSupplyProfile> prmsSupplyProfilesList;
    List<PrmsBid> bidNoLists;
    List<FmsVoucher> listVoucher = null;
    List<FmsAccountUseResult> accountUseResults = new ArrayList<>();
    List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList;
    List<PrmsBid> prmsBids;
    List<HrLocalMedSettlements> hrmedSettlmentList;
    List<HrPayrollMonTransactions> hrPayrollMonTransactionsList;
    List<PmsProjectPaymentRequest> pmsProjectPaymentReqsList;
    List<String> referenceNumbers;
    List<FmsFieldAllowanceSettlement> allowanceSettlements;
    List<FmsFieldAllowance> allowances;
    List<FmsGeneralLedger> listGeneralLedger = null;

    //private list declaration
    private List<FmsLuPaymentType> fmsLuPaymentType;
    private List<FmsLuSystem> listOfSystems;
    private List<FmsVoucher> selectedJv;
    private List<Object> accountCredit;
    private List<Map.Entry<String, BigDecimal>> entries;
    private List<Map.Entry<String, BigDecimal>> entries_;

    //datamodel declaration
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsAccountUse> accountUseDetailDataModelposting;
    DataModel<FmsVoucher> voucherDataModel;
    DataModel<FmsCashReceiptVoucher> journalVoucherDataModel;
    DataModel<PrmsSupplyProfile> PrmsSupplyProfileDataModel;
    DataModel<WfFcmsProcessed> workflowDataModel;

    //private string variables declaration
    private String Conc = "";
    private String display_conn;
    private String costName;
    private String sysName;
    private String generalLName;
    private String saveorUpdateBundle = "Create";
    private String ActionDebitCredit = "";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String selCodedTransactionVal = "codedTransaction";
    private String selPayrollSummaryVal = "payrollSummary";
    private String selOtherVal = "other";
    private String accountNo;
    private String subLName;
    private String icone1 = "ui-icon-plus";
    private String createOrSearchBundle1 = "New";

    //string variables declaration
    String amountInword;
    String voucherNO = "";
    String voucherId = null;
    String processedBy = "";
    String reftype = "nun";
    String types = "Prepared By";

    //boolean variables declaration
    private boolean disableBtnCreate;
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    private Boolean projOption = false;
    private Boolean projRender = false;
    private Boolean printe = false;
    private Boolean void_dis = true;

    //private boolean variables declaration
    Boolean decision = false;
    boolean saveVochNo = false;
    boolean popvocjNo = false;
    boolean cc = true;
    boolean sl = true;
    boolean refbondlocal = false;
    boolean refRender = false;
    boolean refdisable = true;
    Boolean addButtonStatus = false;
    Boolean selectOptionStatus = false;
    Boolean editRemoveStatus = false;
    boolean renderJobNo = false;
    boolean createNEWrend = false;
    boolean new1 = false;

    //int variables declaration
    int updteStatus = 0;
    int onupdate = 0;
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    int MajorCatagorylistId = 0;
    int GLListSize = 0;
    int dataTableUpdateStatus = 0;

    //double variables declaration
    double Credit = 0.0;
    double Debit = 0.0;

    //final 
    final Integer projectType = 2;
    final Integer nonProjectType = 1;

    //private objects
    private FmsAccountUse selectedAccountUses;
    private HashMap<String, BigDecimal> accountDebit = new HashMap<>();
    private HashMap<String, BigDecimal> accountcredit = new HashMap<>();
    private HashMap<HashMap, HashMap> account = new HashMap<>();
    private DataModel<HashMap> accounthashmapModel;
    private NumberConverter numberConverter = new NumberConverter();
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);

    //public objects declaration
    FmsVouchersNoRange vouchersNoRange = new FmsVouchersNoRange();
    FmsAccountUseResult accountUseResult;
    Set<BigInteger> checkDebitCredit = new HashSet<>();
    Set<String> checkSubsidiaryCode = new HashSet<>();
    List<HashMap> list = new ArrayList<HashMap>() {
    };

    //big decimal variables declaration
    private BigDecimal value;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CashReceipt Voucher Controller constractor">
    public CashReceiptVoucherController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create new cash receipt voucher">

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
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

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

    public List<PrmsSupplyProfile> getPrmsSupplyProfilesList() {
        return prmsSupplyProfilesList;
    }

    public void setPrmsSupplyProfilesList(List<PrmsSupplyProfile> prmsSupplyProfilesList) {
        this.prmsSupplyProfilesList = prmsSupplyProfilesList;
    }

    public PrmsSupplyProfile getPrmsSupplyProfile() {
        if (prmsSupplyProfile == null) {
            prmsSupplyProfile = new PrmsSupplyProfile();
        }
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public PrmsBid getPrmsBid() {
        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
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

    public boolean isRefRender() {
        return refRender;
    }

    public void setRefRender(boolean refRender) {
        this.refRender = refRender;
    }

    public List<String> getReferenceNumbers() {
        return referenceNumbers;
    }

    public void setReferenceNumbers(List<String> referenceNumbers) {
        this.referenceNumbers = referenceNumbers;
    }

    public FmsLuPaymentType getLuPaymentType() {
        if (luPaymentType == null) {
            luPaymentType = new FmsLuPaymentType();
        }
        return luPaymentType;
    }

    public void setLuPaymentType(FmsLuPaymentType luPaymentType) {
        this.luPaymentType = luPaymentType;
    }

    public List<FmsLuPaymentType> getFmsLuPaymentType() {
        FmsLuPaymentType flpt = new FmsLuPaymentType();
        flpt.setPayment("CRV");
        fmsLuPaymentType = paymentTypeFacade.findByPaymentType(flpt);
        return fmsLuPaymentType;
    }

    public void setFmsLuPaymentType(List<FmsLuPaymentType> fmsLuPaymentType) {
        this.fmsLuPaymentType = fmsLuPaymentType;
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

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public double getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(double totalamt) {
        this.totalamt = totalamt;
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

    public List<FmsLuSystem> getListOfSystems() {
        listOfSystems = fmsLuSystemBeanLocal.findAll();
        return listOfSystems;
    }

    public void setListOfSystems(List<FmsLuSystem> listOfSystems) {
        this.listOfSystems = listOfSystems;
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

    public FmsVouchersNoRange getFmsVouchersNoRange() {
        if (fmsVouchersNoRange == null) {
            fmsVouchersNoRange = new FmsVouchersNoRange();
        }
        return fmsVouchersNoRange;
    }

    public void setFmsVouchersNoRange(FmsVouchersNoRange fmsVouchersNoRange) {
        this.fmsVouchersNoRange = fmsVouchersNoRange;
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
//    boolean popvocjNo = false;

    public boolean isPopvocjNo() {
        return popvocjNo;
    }

    public void setPopvocjNo(boolean popvocjNo) {
        this.popvocjNo = popvocjNo;
    }

    public DataModel<WfFcmsProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfFcmsProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public List<Object> getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(List<Object> accountCredit) {
        this.accountCredit = accountCredit;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isSaveVochNo() {
        return saveVochNo;
    }

    public void setSaveVochNo(boolean saveVochNo) {
        this.saveVochNo = saveVochNo;
    }

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public WfFcmsProcessed getWfFcmsProcessed() {
        return wfFcmsProcessed;
    }

    public List<PrmsBid> getBidNoLists() {
        if (bidNoLists == null) {
            bidNoLists = new ArrayList<>();
        }
        return bidNoLists;
    }

    public void setBidNoLists(List<PrmsBid> bidNoLists) {
        this.bidNoLists = bidNoLists;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Boolean getprinteCashReceiptVoucher() {
        return printe;
    }

    public void setprinteCashReceiptVoucher(Boolean printe) {
        this.printe = printe;
    }

    public Boolean getVoid_dis() {
        return void_dis;
    }

    public void setVoid_dis(Boolean void_dis) {
        this.void_dis = void_dis;
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
//
//    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
//        if (fmsSubsid1aryLedger1 == null) {
//            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
//        }
//        return fmsSubsid1aryLedger1;
//    }
//
//    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
//        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
//    }
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
//
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
    public FmsDocumentFollowup getFmsDocumentFollowup() {
        if (fmsDocumentFollowup == null) {
            fmsDocumentFollowup = new FmsDocumentFollowup();

        }
        return fmsDocumentFollowup;
    }

    public void setFmsDocumentFollowup(FmsDocumentFollowup fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

    public FmsCashReceiptVoucher getCashReceiptVoucher() {
        if (cashReceiptVoucher == null) {
            cashReceiptVoucher = new FmsCashReceiptVoucher();
        }
        return cashReceiptVoucher;
    }

    public void setCashReceiptVoucher(FmsCashReceiptVoucher cashReceiptVoucher) {
        this.cashReceiptVoucher = cashReceiptVoucher;
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

    public void setAccountUseDetailDataModelposting(DataModel<FmsAccountUse> accountUseDetailDataModelposting) {
        this.accountUseDetailDataModelposting = accountUseDetailDataModelposting;
    }

    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="save - Update">
    public String saveCashReceiptVoucher() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveCashReceiptVoucher", dataset)) {
//                 put ur code here...!
            if (ValidDebitCredit()) {
                double amt = cashReceiptVoucher.getAmountInFigure();
                convertAmountInWored(amt);
                cashReceiptVoucher.setAmountInWord(amountInword);
                if (((amt == Debit) && (Debit == Credit)) || (onupdate == 1)) {
                    if (updteStatus == 0) {
                        String fmsVoucherID, preparedate;
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        preparedate = dateFormat.format(cashReceiptVoucher.getPreparedDate());
                        fmsVoucherID = preparedate + "-" + Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + "-" + "CRV";
                        fmsVoucher.setVoucherId(fmsVoucherID);
                        cashReceiptVoucher.setSuppId(prmsSupplyProfile);
                        cashReceiptVoucher.setBidId(prmsBid);
                        if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher)) {
                            System.err.println("Voucher No. value is duplicated. Try again reloading the page!");
                            JsfUtil.addFatalMessage("Voucher No. value is duplicated. Try again reloading the page!");
                        } else {

                            if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                                JsfUtil.addFatalMessage("Data table shoud be filled");
                            } else {
                                try {
                                    cashReceiptVoucher.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
                                    cashReceiptVoucher.setStatus(Integer.valueOf(Constants.PREPARE_VALUE));
                                    fmsVoucher.setVoucherId(fmsVoucherID);
                                    fmsVoucher.setStatus(securityBean.Constants.PREPARE_VALUE);
                                    fmsVoucher.setType("CRV");
                                    fmsVoucher.setVoucherNo(fmsVouchersNoRange.getCurrentNo());
                                    fmsVoucher.addToFmscashReceiptDetail(cashReceiptVoucher);
                                    fmsDocumentFollowup.setDocumentReference("23526");
                                    fmsDocumentFollowup.setType("CRV");
                                    int currentNo1 = Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + 1;
                                    fmsVouchersNoRange.setCurrentNo(String.valueOf(currentNo1));
                                    noRangeBeanLocal.edit(fmsVouchersNoRange);

                                    wfFcmsProcessed.setFmsVoucherId(fmsVoucher);
                                    wfFcmsProcessed.setDecision(fmsVoucher.getStatus());
                                    wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                                    wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getPreparedDate()));
                                    wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(workFlow.getUserAccount()));
                                    fmsVoucherBeanLocal.create(fmsVoucher);
                                    JsfUtil.addSuccessMessage("Cash Receipt Voucher data saved successfully!");
                                    wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);
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
                    } else {
                        JsfUtil.addFatalMessage("Pls check for balance");
                        return null;
                    }
                }
            } else {
                JsfUtil.addFatalMessage("Error!. Debit & Credit value must be balanced!");
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
        cashReceiptVoucher = null;
        accountUseEnty = null;
        accountUseDetailDataModel = null;
        updteStatus = 0;
        listVoucher = null;
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
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

    public void cashRecieptVoucherSearchByVoucherId() {
        try {
            //find all
            if (voucherId.isEmpty()) {
                fmsVoucher.setType("CRV");
                VoucherList = fmsVoucherBeanLocal.findAllbytype(fmsVoucher);
                recreateJvDataModel();
            } else if (!(voucherId.isEmpty())) {
                fmsVoucher.setVoucherId(voucherId);
                VoucherList = fmsVoucherBeanLocal.searchVoucheidTypeCRV(fmsVoucher);
                recreateJvDataModel();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getJVInformation(SelectEvent event) {

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
    @PostConstruct
    public void getCurrentVoucherNumber() {
        String refNo = null;
        Integer seqNo = 0;
        fmsVouchersNoRange.setStatus(1);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(3);
        fmsVouchersNoRange.setTypeId(luVouchersType);
        void_dis = false;
        if (Languagelocalbean.getLangsession().getAttribute("lang") != null) {
            if (Languagelocalbean.getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
                Languagelocalbean.changeLanguage("ET");
            } else {
                Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
            }
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate cash receipt voucher">
    public void recreateJvDataModel() {
        voucherDataModel = null;
        voucherDataModel = new ListDataModel(new ArrayList(fmsVouchersList1));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="create New CashReceip tVoucher">
    public void createNewCashReceiptVoucher() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateJv = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            saveVochNo = true;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateJv = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            saveVochNo = false;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate cash receipt voucher">

    public void populate(SelectEvent event) {
        fmsVoucher = (FmsVoucher) event.getObject();
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        clearPopup();
        popvocjNo = true;
        fmsVoucher.getFmsAccountUseList().size();
        account.put(accountcredit, accountDebit);
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
            Credit = accountUseResult.getCREDIT();
        }
        entries = new ArrayList<>(accountDebit.entrySet());

        for (int i = 0; i < entries.size(); i++) {
            if ((entries.size() <= entries_.size()) || i < entries_.size()) {
                accountUseResult = new FmsAccountUseResult();
                accountUseResult = accountUseResults.get(i);
                accountUseResult.setAC_NO_DEBIT(entries.get(i).getKey());
                accountUseResult.setDEBIT(entries.get(i).getValue().doubleValue());
                accountUseResults.set(i, accountUseResult);
                Debit = accountUseResult.getDEBIT();
            } else {
                accountUseResult = new FmsAccountUseResult();
                accountUseResult.setAC_NO_DEBIT(entries.get(i).getKey());
                accountUseResult.setDEBIT(entries.get(i).getValue().doubleValue());
                accountUseResults.add(accountUseResult);
                Debit = accountUseResult.getDEBIT();
            }
        }

        receactWorkflow();
        recreateModelDetail();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        updteStatus = 1;
        renderPnlCreateJv = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updteStatus = 1;
        onupdate = 1;
        printe = true;
        new1 = true;
        createNEWrend = true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="convert Amount In Wored">
    public void convertAmountInWored(double AmountInfigure) {
        System.err.println("inside the event");
        try {
            double num = AmountInfigure;
            int birr = (int) Math.floor(num);
            int cents = (int) Math.floor((num - birr) * 100);
            int cent1;
            cent1 = (int) ((num - birr) * 10000);
            if (!(cent1 % 10 == 0)) {
                double cent_1 = cent1 / 100.0;
                cents = (int) Math.ceil(cent_1);
            }
            String amountInwordConverted = EnglishNumberToWords.convert(birr) + " Birr" + " and "
                    + EnglishNumberToWords.convert(cents) + " Cents";
            amountInword = amountInwordConverted;
        } catch (Exception ex) {
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate Account Detail">

    public void populateAccDetail(SelectEvent event) {
        accountUseEnty.setFmsAccountUseId(Integer.parseInt(event.getObject().toString()));
        dataTableUpdateStatus = 1;
        String subsidari;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
        subsidari = getAccountUseDetailDataModel().getRowData().getSubsidaryId();
        HashMap<String, Integer> words_fre = new HashMap<String, Integer>();
        int x = 0;
        for (String retval : subsidari.split("-")) {
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

    public void setFmsCostCenterList(List<FmsCostCenter> fmsCostCenterList) {
        this.fmsCostCenterList = fmsCostCenterList;
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

    public void setFmsSubsidiaryLedgerList(List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList) {
        this.fmsSubsidiaryLedgerList = fmsSubsidiaryLedgerList;
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

    public void optionHandiler(ValueChangeEvent event) {

        String selected = cashReceiptVoucher.getPrepareRemark();
        if (selected.matches("other")) {
            accountUseDetailDataModel = null;
            addButtonStatus = true;
        }

    }
    double totalamt = 0.0;

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
        cashReceiptVoucher = fmsVoucher.getFmsCashReceiptVoucher();
    }

    public void recreateModelDetail() {
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
    }

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

    public void remove() {
        accountUseEnty = null;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        fmsVoucher.getFmsAccountUseList().remove(accountUseEnty);
        recreateModelDetail();
    }
//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Ref - No">

    public void ref(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            luPaymentType.setPaymentType(event.getNewValue().toString());
            if (luPaymentType.getPaymentType().equalsIgnoreCase("Bid Sales")) {
                bidNoLists = fmsVoucherBeanLocal.getBidNoLists();
                bidNoLists = fmsVoucherBeanLocal.getBidNoLists();
                if (!bidNoLists.isEmpty()) {
                    refRender = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }

                prmsSupplyProfilesList = fmsVoucherBeanLocal.findBySuppProfile(prmsSupplyProfile);
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Settlement")) {
                referenceNumbers = new ArrayList<>();
                allowances = allowansBeanLocal.searchSetlementchekeVocher();
                if (!allowances.isEmpty()) {
                    allowances.stream().forEach((fieldAllowanceobj) -> {
                        getReferenceNumbers().add(fieldAllowanceobj.getRequestNo().toString());
                        System.err.println("issss---" + fieldAllowanceobj.getId().toString());
                    });
                    refNoList();
                    refRender = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
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
        if (null != event.getNewValue()) {
            System.out.println("2");
            String ref = event.getNewValue().toString();
            if (luPaymentType.getPaymentType().equalsIgnoreCase("Bid Sales")) {
                prmsBid.setId(event.getNewValue().toString());
                prmsBid = fmsVoucherBeanLocal.getBidInfoById(prmsBid);
                cashReceiptVoucher.setAmountInFigure(prmsBid.getBidDocPrice());
                cashReceiptVoucher.setReferenceNumber(prmsBid.getRefNo());
            } else if (luPaymentType.getPaymentType().equalsIgnoreCase("Settlement")) {
                fieldAllowance.setRequestNo((event.getNewValue().toString()));
                fieldAllowance = allowansBeanLocal.getByrequestno(fieldAllowance);
                int id = fieldAllowance.getId();
                FmsFieldAllowanceSettlement allowanceSettlementObject = new FmsFieldAllowanceSettlement();
                allowanceSettlementObject = allowanceSettlementBeanLocal.searchSetlementchekeVocher(id);
                cashReceiptVoucher.setAmountInFigure(allowanceSettlementObject.getTotalExpense());
                cashReceiptVoucher.setReferenceNumber(allowanceSettlementObject.getId().toString());
            }
        }
    }

    public void referenceNumEvent1(ValueChangeEvent event) {
        if (!(event.getNewValue().toString() == null)) {
            prmsSupplyProfile = (PrmsSupplyProfile) event.getNewValue();

            prmsSupplyProfile = fmsVoucherBeanLocal.getOthersBySuppId(prmsSupplyProfile);
            cashReceiptVoucher.setRegion(prmsSupplyProfile.getCountryId().getCountry());
            cashReceiptVoucher.setCity(prmsSupplyProfile.getAgentAddress());
            cashReceiptVoucher.setTin(prmsSupplyProfile.getTinNo());
        }
    }

    public void refno() {
        if (reftype != null) {
            if (reftype.matches("Bid Sales")) {
                refdisable = false;

            } else if (reftype.matches("Foreign Bond")) {
            } else {
            }

        } else {
        }
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="receactWorkflow">
    public void receactWorkflow() {
        workflowDataModel = null;
        workflowDataModel = new ListDataModel(new ArrayList(fmsVoucher.getWfFcmsProcessedList()));
        for (int i = 0; i < fmsVoucher.getWfFcmsProcessedList().size(); i++) {
            if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(0)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Prepared");
            } else if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(1) || fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(39)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Voucher posted");
            } else if (fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(2) || fmsVoucher.getWfFcmsProcessedList().get(i).getDecision().equals(38)) {
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("Voucher voided");
            }
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Chart of Account Handler: Account Code Handler. Author: Mubarek Shejebel">

    public void onChangeSystem(ValueChangeEvent event) {
        try {
            if (event.getNewValue() != null) {
                fmsLuSystem = (FmsLuSystem) event.getNewValue();
                renderJobNo = false;
                if (fmsLuSystem.getType().toString().equals(projectType)) {
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
        fmsVoucher.setType("CRPV");
        fmsVouchersList1 = fmsVoucherBeanLocal.searchAllVochNo(fmsVoucher);
        recreateJvDataModel();
        fmsVoucher = new FmsVoucher();
    }
}
