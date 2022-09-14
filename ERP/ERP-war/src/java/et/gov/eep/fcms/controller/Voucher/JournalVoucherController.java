package et.gov.eep.fcms.controller.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.bank.FmsCodedTransactionBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseTempDataBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCSystemJunctionBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import securityBean.Constants;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsAccountUseTempData;
import et.gov.eep.fcms.entity.bank.FmsCodedTransaction;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.Vocher.FmsJournalVoucher;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollSummeryBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollSummery;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

@Named
@ViewScoped
public class JournalVoucherController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Journal Voucher Controller">
    public JournalVoucherController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="createNewJournal voucher">

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
    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    // EJB declarations
    @EJB
    private FmsAccountUseBeanLocal fmsAccountUseBeanLocal;
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    @EJB
    FmsCodedTransactionBeanLocal fmsCodedTransactionBeanLocal;
    @EJB
    private HrPayrollSummeryBeanLocal hrPayrollSummeryBeanLocal;
    @EJB
    private FmsAccountUseTempDataBeanLocal fmsAccountUseTempDataBeanLocal;
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    FmsCostCSystemJunctionBeanLocal fmsCostCSystemJunctionBeanLocal;
    @EJB
    private FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Database Injection">
    @Inject
    FmsVouchersNoRange fmsVouchersNoRange;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsSubsidiaryLedger fmsSubsidiaryLedger;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    WorkFlow workFlow;
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsAccountUseTempData fmsAccountUseTempData;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    FmsCodedTransaction fmsCodedTransaction;
    @Inject
    HrPayrollSummery hrPayrollSummery;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    FmsJournalVoucher journalVoucherEnty;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    @Inject
    private FmsCostcSystemJunction fmsCostcSystemJunction;
    @Inject
    private FmsSystemJobJunction fmsSystemJobJunction;
    @Inject
    localbean Languagelocalbean;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables dclaration">
    //int variables dclaration
    int GLListSize = 0;
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    int updteStatus = 0;
    int currentNo;
    int MajorCatagorylistId = 0;
    int dataTableUpdateStatus = 0;
    //private string variables declaration
    private String icone1 = "ui-icon-plus";
    private String display_conn;
    private String Conc = "";
    private String selCodedTransactionVal = "codedTransaction";
    private String selPayrollSummaryVal = "payrollSummary";
    private String selBillingTransactionVar = "billingTransaction";
    private String selOtherVal = "other";
    private String selectOption = "";
    private String subLName;
    private String generalLName;
    private String costName;
    private String sysName;
    private String saveorUpdateBundle = "Create";
    private String ActionDebitCredit = "";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String createOrSearchBundle1 = "New";
    private NumberConverter numberConverter = new NumberConverter();
    private FmsAccountUse selectedAccountUses;

    //private boolean variables declaration
    private boolean disableBtnCreate;
    private boolean renderBtnPrint = false;
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    boolean new1 = false;
    boolean renderJobNo = false;

    //private bigdecimal variable declaration
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);

    //private boolean variables declaration
    private Boolean printe = false;

    //string variables declaration
    String voucherId = null;
    String voucherID, preparedate;
    String selected;
    String voucherNO = "";

    //double variables declaration
    double Debit = 0.0;
    double Credit = 0.0;
    double totalamt = 0.0;

    //boolean variable declaration
    boolean saveVochNo = false;
    boolean popvocjNo = false;
    boolean cc = true;
    boolean sl = true;
    Boolean addButtonStatus = false;
    Boolean selectOptionStatus = false;
    Boolean editRemoveStatus = false;
    boolean isReadOnlyTxtRefNo = false;
    Boolean void_dis = true;
    boolean createNEWrend = false;

    //list objects declaration
    List<FmsGeneralLedger> listGeneralLedger = null;
    List<FmsCodedTransaction> codedTranList;
    List<HrPayrollSummery> payrollSummeryList;
    List<FmsAccountUseTempData> billingTransactionList;
    List<FmsSubsidiaryLedger> fmsSubsidiaryLedgerList;
    List<FmsCostCenter> fmsCostCenterList;
    List<FmsVoucher> jvList;
    List<FmsGeneralLedger> fmsGeneralLedgerList;
    List<FmsVoucher> listVoucher = null;
    List<FmsCostcSystemJunction> ssCcJunctionList = new ArrayList<>();
    List<FmsSystemJobJunction> sysJobNOList;
    List<FmsGeneralLedger> glList;
    List<FmsSubsidiaryLedger> slList;
    List<FmsLuSystem> systemList;
    Set<String> checkSubsidiaryCode = new HashSet<>();
    List<HashMap> list = new ArrayList<HashMap>() {
    };
    private List<FmsVoucher> selectedJv;

    //datamodel declaration
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsVoucher> voucherDataModel;
    DataModel<FmsJournalVoucher> journalVoucherDataModel;
    DataModel<FmsAccountUse> accountUseDetailDataModelposting;
    DataModel<WfFcmsProcessed> workflowDataModel;

    //final 
    final Integer projectType = 2;
    final Integer nonProjectType = 1;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="- getters and setters">
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

    public boolean isCreateNEWrend() {
        return createNEWrend;
    }

    public void setCreateNEWrend(boolean createNEWrend) {
        this.createNEWrend = createNEWrend;
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

    public double getDebit() {
        return Debit;
    }

    public void setDebit(double Debit) {
        this.Debit = Debit;
    }

    public double getCredit() {
        return Credit;
    }

    public void setCredit(double Credit) {
        this.Credit = Credit;
    }

    public List<FmsCodedTransaction> getCodedTranList() {
        if (codedTranList == null) {
            codedTranList = new ArrayList<>();
        }
        return codedTranList;
    }

    public void setCodedTranList(List<FmsCodedTransaction> codedTranList) {
        this.codedTranList = codedTranList;
    }

    public List<FmsAccountUseTempData> getBillingTransactionList() {
        if (billingTransactionList == null) {
            billingTransactionList = new ArrayList<>();
        }
        return billingTransactionList;
    }

    public void setBillingTransactionList(List<FmsAccountUseTempData> billingTransactionList) {
        this.billingTransactionList = billingTransactionList;
    }

    public List<HrPayrollSummery> getPayrollMonTranList() {
        if (payrollSummeryList == null) {
            payrollSummeryList = new ArrayList<>();
        }
        return payrollSummeryList;
    }

    public void setPayrollMonTranList(List<HrPayrollSummery> payrollSummeryList) {
        this.payrollSummeryList = payrollSummeryList;
    }

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public String getGeneralLName() {
        return generalLName;
    }

    public void setGeneralLName(String generalLName) {
        this.generalLName = generalLName;
    }

    public String getSubLName() {
        return subLName;
    }

    public void setSubLName(String subLName) {
        this.subLName = subLName;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
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

    private List<FmsLuSystem> listOfSystems;

    public List<FmsLuSystem> getListOfSystems() {
        listOfSystems = fmsLuSystemBeanLocal.findAll();
        return listOfSystems;
    }

    public void setListOfSystems(List<FmsLuSystem> listOfSystems) {
        this.listOfSystems = listOfSystems;
    }

    public SelectItem[] getListSys() {
        return JsfUtil.getSelectItems(fmsLuSystemBeanLocal.findAll(), true);
    }

    public SelectItem[] getSubList() {
        return JsfUtil.getSelectItems(subLedgerBeanLocal.findAll(), true);
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

    public FmsVouchersNoRange getFmsVouchersNoRange() {
        if (fmsVouchersNoRange == null) {
            fmsVouchersNoRange = new FmsVouchersNoRange();
        }
        return fmsVouchersNoRange;
    }

    public void setFmsVouchersNoRange(FmsVouchersNoRange fmsVouchersNoRange) {
        this.fmsVouchersNoRange = fmsVouchersNoRange;
    }

    public DataModel<WfFcmsProcessed> getWorkflowDataModel() {
        return workflowDataModel;
    }

    public void setWorkflowDataModel(DataModel<WfFcmsProcessed> workflowDataModel) {
        this.workflowDataModel = workflowDataModel;
    }

    public FmsAccountUseTempData getFmsAccountUseTempData() {
        if (fmsAccountUseTempData == null) {
            fmsAccountUseTempData = new FmsAccountUseTempData();
        }
        return fmsAccountUseTempData;
    }

    public void setFmsAccountUseTempData(FmsAccountUseTempData fmsAccountUseTempData) {
        this.fmsAccountUseTempData = fmsAccountUseTempData;
    }

    public boolean isIsReadOnlyTxtRefNo() {
        return isReadOnlyTxtRefNo;
    }

    public void setIsReadOnlyTxtRefNo(boolean isReadOnlyTxtRefNo) {
        this.isReadOnlyTxtRefNo = isReadOnlyTxtRefNo;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderBtnPrint() {
        return renderBtnPrint;
    }

    public void setRenderBtnPrint(boolean renderBtnPrint) {
        this.renderBtnPrint = renderBtnPrint;
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

    public String getSelBillingTransactionVar() {
        return selBillingTransactionVar;
    }

    public void setSelBillingTransactionVar(String selBillingTransactionVar) {
        this.selBillingTransactionVar = selBillingTransactionVar;
    }

    public String getSelOtherVal() {
        return selOtherVal;
    }

    public void setSelOtherVal(String selOtherVal) {
        this.selOtherVal = selOtherVal;
    }

    public FmsCodedTransaction getFmsCodedTransaction() {
        if (fmsCodedTransaction == null) {
            fmsCodedTransaction = new FmsCodedTransaction();
        }
        return fmsCodedTransaction;
    }

    public void setFmsCodedTransaction(FmsCodedTransaction fmsCodedTransaction) {
        this.fmsCodedTransaction = fmsCodedTransaction;
    }

    public HrPayrollSummery getHrPayrollSummery() {
        if (hrPayrollSummery == null) {
            hrPayrollSummery = new HrPayrollSummery();
        }
        return hrPayrollSummery;
    }

    public void setHrPayrollSummery(HrPayrollSummery hrPayrollSummery) {
        this.hrPayrollSummery = hrPayrollSummery;
    }

    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if (fmsSubsid1aryLedger1 == null) {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
        }
        return fmsSubsid1aryLedger1;
    }

    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
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

    public FmsJournalVoucher getJournalVoucherEnty() {
        if (journalVoucherEnty == null) {
            journalVoucherEnty = new FmsJournalVoucher();
        }
        return journalVoucherEnty;
    }

    public void setJournalVoucherEnty(FmsJournalVoucher journalVoucherEnty) {
        this.journalVoucherEnty = journalVoucherEnty;
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

    public Boolean getPrinte() {
        return printe;
    }

    public void setPrinte(Boolean printe) {
        this.printe = printe;
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

    public Boolean getVoid_dis() {
        return void_dis;
    }

    public void setVoid_dis(Boolean void_dis) {
        this.void_dis = void_dis;
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
        System.out.println("selectedJv==" + selectedJv);
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

    public List<FmsVoucher> getJvList() {
        if (jvList == null) {
            jvList = new ArrayList();
        }
        return jvList;
    }

    public void setJvList(List<FmsVoucher> jvList) {
        this.jvList = jvList;
    }

    public void setAccountUseDetailDataModelposting(DataModel<FmsAccountUse> accountUseDetailDataModelposting) {
        this.accountUseDetailDataModelposting = accountUseDetailDataModelposting;
    }

    public String getVoucherNO() {
        return voucherNO;
    }

    public void setVoucherNO(String voucherNO) {
        this.voucherNO = voucherNO;
    }

    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="save - Update">
    public void saveJournalVoucher() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(SessionBean.getUserName(), "saveJournalVoucher", dataset)) {
//                 put ur code here...! 
            try {
                //            if (ValidDebitCredit()) {
                System.out.println("---inside try---");
                if (updteStatus == 0) {
                    String voucherID;
                    String preparedate;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    preparedate = dateFormat.format(fmsVoucher.getPreparedDate());
                    voucherID = preparedate + "-" + fmsVouchersNoRange.getCurrentNo() + "-" + "JV";
                    fmsVoucher.setVoucherId(voucherID);
                    if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher)) {
                        JsfUtil.addFatalMessage("Voucher No. value is Duplicated. Try again reloading the page!");
                    } else {
                        if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                            JsfUtil.addFatalMessage("Failed. Data table shoud be filled");
                        } else {
                            try {
                                if (selected.matches("Other")) {
                                    if (ValidDebitCredit()) {
                                        noRangeBeanLocal.edit(fmsVouchersNoRange);
                                    } else {
                                        JsfUtil.addFatalMessage("Debit & Credit Value Should be Balanced!");
                                    }
                                    //coded Transaction Option  
                                } else if (selected.matches("codedTransaction")) {
                                    System.out.println("---inside try6666---");
//                                    if (ValidDebitCredit()) {
                                    for (int i = 0; i < codedTranList.size(); i++) {
                                        System.out.println("|||");
                                        fmsCodedTransaction = new FmsCodedTransaction();
                                        fmsCodedTransaction.setCodedTransactonId(codedTranList.get(i).getCodedTransactonId());
                                        fmsCodedTransaction.setSubsidiaryId(codedTranList.get(i).getSubsidiaryId());
                                        fmsCodedTransaction.setDebit(codedTranList.get(i).getDebit());
                                        fmsCodedTransaction.setCredit(codedTranList.get(i).getCredit());
                                        fmsCodedTransaction.setTranRef(codedTranList.get(i).getTranRef());
                                        fmsCodedTransaction.setStatus(Constants.PREPARE_VALUE);
                                        fmsCodedTransaction.setRecon_status(0);
                                        fmsCodedTransaction.setType(codedTranList.get(i).getType());
                                        fmsCodedTransactionBeanLocal.edit(fmsCodedTransaction);

                                    }
//                                    } else {
//                                        JsfUtil.addFatalMessage("Debit & Credit Value Should be Balanced!");
//                                    }
                                } else if (selected.matches("payrollSummary")) {
                                    if (ValidDebitCredit()) {
                                        for (int i = 0; i < payrollSummeryList.size(); i++) {

                                            hrPayrollSummery = new HrPayrollSummery();
                                            hrPayrollSummery.setId(payrollSummeryList.get(i).getId());
                                            hrPayrollSummery.setAccountCode(payrollSummeryList.get(i).getAccountCode());
                                            hrPayrollSummery.setCredit(payrollSummeryList.get(i).getCredit());
                                            hrPayrollSummery.setDebit(payrollSummeryList.get(i).getDebit());
                                            hrPayrollSummery.setEdCode(payrollSummeryList.get(i).getEdCode());
                                            hrPayrollSummery.setPaymentLevel(payrollSummeryList.get(i).getPaymentLevel());
                                            hrPayrollSummery.setPayrollId(payrollSummeryList.get(i).getPayrollId());
                                            hrPayrollSummery.setStatus(Constants.PREPARE_VALUE);
                                            hrPayrollSummeryBeanLocal.edit(hrPayrollSummery);
                                        }
                                    } else {
                                        JsfUtil.addFatalMessage("Debit & Credit Value Should be Balanced!");
                                    }
                                } else if (selected.matches("billingTransaction")) { //Billing Transaction  Option
                                    if (ValidDebitCredit()) {
                                        for (int i = 0; i < billingTransactionList.size(); i++) {
                                            fmsAccountUseTempData = new FmsAccountUseTempData();
                                            fmsAccountUseTempData.setId(billingTransactionList.get(i).getId());
                                            fmsAccountUseTempData.setAccountCode(billingTransactionList.get(i).getAccountCode());
                                            fmsAccountUseTempData.setCredit(billingTransactionList.get(i).getCredit());
                                            fmsAccountUseTempData.setDebit(billingTransactionList.get(i).getDebit());
                                            fmsAccountUseTempData.setRefNo(billingTransactionList.get(i).getRefNo());
                                            fmsAccountUseTempData.setTransactionDate(billingTransactionList.get(i).getTransactionDate());
                                            fmsAccountUseTempData.setStatus(1);
                                            fmsAccountUseTempDataBeanLocal.edit(fmsAccountUseTempData);
                                        }
                                    } else {
                                        JsfUtil.addFatalMessage("Debit & Credit Value Should be Balanced!");
                                    }
                                }
                                fmsDocumentFollowup.setDocumentReference("12345");
                                fmsVoucher.addToFmsDocumentFollowupDetail(fmsDocumentFollowup);
                                fmsVoucher.addToFmsJournalVoucherDetail(journalVoucherEnty);
                                journalVoucherEnty.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                                journalVoucherEnty.setSourceJeId("0");
                                fmsVoucher.setVoucherNo(fmsVouchersNoRange.getCurrentNo());
                                fmsVoucher.setType("JV");
                                fmsVoucher.setStatus(Constants.PREPARE_VALUE);
                                fmsVoucher.setStatus(Constants.PREPARE_VALUE);
                                fmsVoucher.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                                fmsVoucher.setProcessedBy(String.valueOf(wfFcmsProcessed.getProcessedBy()));
                                wfFcmsProcessed.setFmsVoucherId(fmsVoucher);
                                wfFcmsProcessed.setDecision(fmsVoucher.getStatus());
                                wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                                wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getPreparedDate()));
                                wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(workFlow.getUserAccount()));
                                fmsVoucherBeanLocal.create(fmsVoucher);
                                int currentNo = Integer.parseInt(fmsVouchersNoRange.getCurrentNo()) + 1;
                                fmsVouchersNoRange.setCurrentNo(String.valueOf(currentNo));
                                noRangeBeanLocal.edit(fmsVouchersNoRange);
                                wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);
                                getCurrentVoucherNumber();
                                JsfUtil.addSuccessMessage("Saved Successfully!");
                                clearPage();
                            } catch (Exception e) {
                                JsfUtil.addFatalMessage("Failed to save. Try Again Reloading the Page");
                            }
                        }
                    }
                } else if (updteStatus == 1) {
                    if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data table shoud be filled");
                        //return null;
                    }
                    fmsVoucher.setStatus(Constants.PREPARE_VALUE);
                    fmsVoucherBeanLocal.edit(fmsVoucher);
                    JsfUtil.addSuccessMessage("Updated Successfully!");
                    selectOptionStatus = false;
                    clearPage();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JsfUtil.addFatalMessage("Failed something occur");
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
    }

    public String clearPage() {
        fmsVoucher = null;
        journalVoucherEnty = null;
        fmsLuSystem = null;
        accountUseEnty = null;
        accountUseDetailDataModel = null;
        updteStatus = 0;
        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public List<FmsVoucher> JVSearchlist(String voucheid) {
        listVoucher = null;
        fmsVoucher.setVoucherId(voucheid);
        listVoucher = fmsVoucherBeanLocal.searchVoucheidTypeJV(fmsVoucher);
        return listVoucher;
    }

    public void searchJournalVoucherByVoucherId() {
        try {
            //find all
            jvList = new ArrayList<>();
            if (voucherId.isEmpty()) {
                jvList = fmsVoucherBeanLocal.searchAllJvByType(fmsVoucher);
                recreateJvDataModel();
            } else if (!(voucherId.isEmpty())) {
//                //find by begins with
                fmsVoucher.setVoucherId(voucherId);
                jvList = fmsVoucherBeanLocal.searchVoucheidTypeJV(fmsVoucher);
                recreateJvDataModel();
            } else {
                JsfUtil.addFatalMessage("No data found. Try again.");
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Failed to search jv, Please try again reloading the page.");
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
    FmsVouchersNoRange vouchersNoRange = new FmsVouchersNoRange();

    @PostConstruct
    public void getCurrentVoucherNumber() {
        String refNo = null;
        Integer seqNo = 0;
        fmsVouchersNoRange.setStatus(1);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(3);
        fmsVouchersNoRange.setTypeId(luVouchersType);
        if (Languagelocalbean.getLangsession().getAttribute("lang") != null) {
            if (Languagelocalbean.getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
                Languagelocalbean.changeLanguage("ET");
            } else {
                Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
            }
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate journal voucher DataModel">
    public void recreateJvDataModel() {
        voucherDataModel = null;
        voucherDataModel = new ListDataModel(new ArrayList(fmsVouchersList1));
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="creating journal voucher">
    public void createJournalVocherReg() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equalsIgnoreCase("New")) {
            renderPnlCreateJv = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            saveVochNo = true;
        } else if (createOrSearchBundle.equalsIgnoreCase("Search")) {
            renderPnlCreateJv = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            saveVochNo = false;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate function">

    public void populate(SelectEvent event) {
        fmsVoucher = (FmsVoucher) event.getObject();
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        clearPopup();
        popvocjNo = true;
        receactWorkflow();
        recreateModelDetail();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        renderBtnPrint = true;
        updteStatus = 1;
        renderPnlCreateJv = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        disableBtnCreate = true;
        printe = true;
        new1 = true;
        createNEWrend = true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="populate account details">
    public void populateAccDetail(SelectEvent event) {
        accountUseEnty = (FmsAccountUse) event.getObject();
        String subsidari = accountUseEnty.getSubsidaryId();
        popvocjNo = true;
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
            fmsSubsid1aryLedger1.setGeneralLedgerId(fmsGeneralLedger);
        }
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
            //  subLName = fmsSubsid1aryLedger1.getAccountTitle();

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

    public String journalVoucherAddSubsidiaryLedgerDetail() {
        try {
            FmsAccountUse accountUseEnty = new FmsAccountUse();
//            Concatenate();
//            accountUseEnty.setSubsidaryId(Conc);
            accountUseEnty.setSubsidaryId(fmsSubsidiaryLedger.getSubsidiaryCode());
            System.out.println("accountUseEnty.getSubsidaryId()-----" + accountUseEnty.getSubsidaryId());
            accountUseEnty.setDisplay_conn(display_conn);
            if (ActionDebitCredit.equals("Debit")) {
                accountUseEnty.setDebit(ValueDebitCredit);
                accountUseEnty.setCredit(new BigDecimal(0.0));
                accountUseEnty.setAmt(0 - accountUseEnty.getDebit().doubleValue());
            }
            if (ActionDebitCredit.equals("Credit")) {
                accountUseEnty.setDebit(new BigDecimal(0.0));
                accountUseEnty.setCredit(ValueDebitCredit);
                accountUseEnty.setAmt(accountUseEnty.getCredit().doubleValue());

            }
            //check duplicate Debit Credit with same SL code
            if (checkSubsidiaryCode.contains(accountUseEnty.getSubsidaryId())) {
                JsfUtil.addFatalMessage("Debit/Credit is not allowed with duplicate Subsidiary Code!");
                return null;
            } else {

                Debit = Debit + accountUseEnty.getDebit().doubleValue();
                Credit = Credit + accountUseEnty.getCredit().doubleValue();
                fmsVoucher.addToFmsSubsidiaryLSrDetail(accountUseEnty);
                checkSubsidiaryCode.add(accountUseEnty.getSubsidaryId());
                totalamt = Credit - Debit;

                recreateModelDetail();
                editRemoveStatus = true;
                return clearPopup();
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Failed to add! Try again reloading the page!");
        }
        return null;
    }

    public SelectItem[] findListSubL() {
        ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
        if (fmsGeneralLedger != null) {
            SelectItem[] listSl = null;

            subsidaryList = subLedgerBeanLocal.findSubLedger(fmsGeneralLedger);
            if (subsidaryList.size() > 0) {
                sl = false;
                listSl = new SelectItem[subsidaryList.size() + 1];
                listSl[0] = new SelectItem(null, "--- Select One ---");
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="different types of contatination - Implimentation">
    public void concatenateCT() {//for coded transactions
        try {
            int size = codedTranList.size();
            for (int i = 0; i < size; i++) {
                String accountCode[] = codedTranList.get(i).getSubsidiaryId().split("-");
                fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));//SS
                fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
                String CC_display = "CC";
                String SL_display = "SL";
                if (!"CC".contains(accountCode[1])) {//CC
                    fmsCostCenter.setCostCenterId(Integer.parseInt(accountCode[1]));
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                    CC_display = fmsCostCenter.getSystemName();
                }
                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));//GL
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
                if (!"SL".equals(accountCode[0])) {//SL
                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(accountCode[3]));
                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
                }
                display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                codedTranList.get(i).setDisplay_conn(display_conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void concatenatePS() {//for payroll summery
        try {
            int size = payrollSummeryList.size();
            for (int i = 0; i < size; i++) {
                String SS_display = "SS";
                String CC_display = "CC";
                String SL_display = "SL";
                String accountCode[] = payrollSummeryList.get(i).getAccountCode().split("-");
                if (!"SS".equals(accountCode[0])) {
                    fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));//SS
                    fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
                    SS_display = fmsLuSystem.getSystemCode();
                }
                if (!"CC".contains(accountCode[1])) {//CC
                    fmsCostCenter.setCostCenterId(Integer.parseInt(accountCode[1]));
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                    CC_display = fmsCostCenter.getSystemName();
                }
                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));//GL
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
                if (!"SL".equals(accountCode[3])) {
                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(accountCode[3]));//SL
                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
                }
                display_conn = SS_display + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                payrollSummeryList.get(i).setDisplay_conn(display_conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void concatenateBillTrn() {//for Billing Transactions
        try {
            int size = billingTransactionList.size();
            for (int i = 0; i < size; i++) {
                String SS_display = "SS";
                String CC_display = "CC";
                String SL_display = "SL";
                String accountCode[] = billingTransactionList.get(i).getAccountCode().split("-");
                if (!"SS".equals(accountCode[0])) {
                    fmsLuSystem.setSystemId(Integer.parseInt(accountCode[0]));//SS
                    fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
                    SS_display = fmsLuSystem.getSystemCode();
                }
                if (!"CC".contains(accountCode[1])) {//CC
                    fmsCostCenter.setCostCenterId(Integer.parseInt(accountCode[1]));
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                    CC_display = fmsCostCenter.getSystemName();
                }
                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(accountCode[2]));//GL
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
                if (!"SL".equals(accountCode[3])) {
                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(accountCode[3]));//SL
                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
                }
                display_conn = SS_display + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                billingTransactionList.get(i).setDisplay_conn(display_conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void concatenateAC() {// on populate JV
        for (int i = 0; i < fmsVoucher.getFmsAccountUseList().size(); i++) {
            String splitedaccoutid[] = fmsVoucher.getFmsAccountUseList().get(i).getSubsidaryId().split("-");
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
            if (!"SL".equals(splitedaccoutid[3])) {
                fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(splitedaccoutid[3]));
                fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
            }
            display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsVoucher.getFmsAccountUseList().get(i).setDisplay_conn(display_conn);

        }
    }

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
    //<editor-fold defaultstate="collapsed" desc="optionHandiler function">
    public void optionHandiler(ValueChangeEvent e) {
        try {
            payrollSummeryList = new ArrayList();
            codedTranList = new ArrayList();
            billingTransactionList = new ArrayList();
            accountUseDetailDataModel = null;
            selected = e.getNewValue().toString();
            journalVoucherEnty.setReferenceNumber("");
            setIsReadOnlyTxtRefNo(false);
            if (selected.matches("codedTransaction")) {
                codedTranList = fmsCodedTransactionBeanLocal.getCodedTransactionInfo(fmsLuSystem);
                if (codedTranList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Coded Transactions Ready for JV Yet.");
                } else {
                    addButtonStatus = false;
                    for (FmsCodedTransaction fmsc : codedTranList) {
                        FmsAccountUse ac = new FmsAccountUse();
                        ac.setCredit(BigDecimal.valueOf(fmsc.getCredit()));
                        ac.setDebit(BigDecimal.valueOf(fmsc.getDebit()));
                        ac.setSubsidaryId(fmsc.getSubsidiaryId());
                        ac.setDisplay_conn(fmsc.getDisplay_conn());
                        fmsVoucher.addToFmsSubsidiaryLSrDetail(ac);
                    }
                    accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
                }
            } else if (selected.matches("payrollSummary")) {
                accountUseDetailDataModel = null;
                payrollSummeryList = hrPayrollSummeryBeanLocal.getPayrollMonthlyTrn();
                if (payrollSummeryList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Payroll Summary is Ready for JV Yet.");
                } else {
                    concatenatePS();
                    for (HrPayrollSummery hrPs : payrollSummeryList) {
                        FmsAccountUse ac = new FmsAccountUse();
                        ac.setCredit(hrPs.getCredit());
                        ac.setDebit(hrPs.getDebit());
                        ac.setSubsidaryId(hrPs.getAccountCode());
                        ac.setDisplay_conn(hrPs.getDisplay_conn());
                        fmsVoucher.addToFmsSubsidiaryLSrDetail(ac);
                    }
                    addButtonStatus = false;
                    accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
                }
            } else if (selected.matches("billingTransaction")) {//Billing
                billingTransactionList = fmsAccountUseTempDataBeanLocal.getBillingTransaction();
                journalVoucherEnty.setReferenceNumber(billingTransactionList.get(0).getRefNo());
                setIsReadOnlyTxtRefNo(true);
                if (billingTransactionList.isEmpty()) {
                    JsfUtil.addFatalMessage("No Billing Transation is Ready for JV Yet.");
                } else {
                    concatenateBillTrn();
                    for (FmsAccountUseTempData fmsAUTD : billingTransactionList) {
                        FmsAccountUse ac = new FmsAccountUse();
                        ac.setCredit(fmsAUTD.getCredit());
                        ac.setDebit(fmsAUTD.getDebit());
                        ac.setSubsidaryId(fmsAUTD.getAccountCode());
                        ac.setDisplay_conn(fmsAUTD.getDisplay_conn());
                        fmsVoucher.addToFmsSubsidiaryLSrDetail(ac);
                    }
                    addButtonStatus = false;
                    accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
                }
            } else if (selected.matches("other")) {
                accountUseDetailDataModel = null;
                addButtonStatus = true;
            } else {
                JsfUtil.addFatalMessage("sorry Failed to Retrieve Data!");
            }
        } catch (Exception ex) {
            JsfUtil.addFatalMessage("Failed to Retrieve Data!");
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="clearPopup">
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="deleteAccountUseDetail">
    public void deleteAccountUseDetail() {
        dataTableUpdateStatus = 1;
        fmsVoucher.removedata(getAccountUseDetailDataModel().getRowData());
        JsfUtil.addSuccessMessage("Data is removed");
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="updateAccountUseDetail">
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate journal voucher dataModel Detail">
    public void recreateJVModelDetail() {
        journalVoucherEnty = fmsVoucher.getFmsJournalVoucher();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreateModelDetail">
    public void recreateModelDetail() {
        fmsLuSystem = new FmsLuSystem();
        fmsCostCenter = new FmsCostCenter();
        fmsGeneralLedger = new FmsGeneralLedger();
        fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
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
        System.out.println("rrrrrr");
        double creditValid = 0.0;
        double debitValid = 0.0;
        int size = fmsVoucher.getFmsAccountUseList().size();
        for (int i = 0; i < size; i++) {
            creditValid = creditValid + fmsVoucher.getFmsAccountUseList().get(i).getCredit().longValue();
            debitValid = debitValid + fmsVoucher.getFmsAccountUseList().get(i).getDebit().longValue();
            System.out.println("rrrrrr1111");
        }
        if (creditValid == debitValid) {
            System.out.println("rrrrrr222222");
            return true;
        } else {
            System.out.println("rrrrrr33333");
            return false;
        }

    }

    // </editor-fold> 
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
                fmsVoucher.getWfFcmsProcessedList().get(i).setChangedDecision("voucher Voided");
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
        fmsVoucher.setType("JV");
        fmsVouchersList1 = fmsVoucherBeanLocal.searchAllVochNo(fmsVoucher);
        recreateJvDataModel();
        fmsVoucher = new FmsVoucher();
    }

}
