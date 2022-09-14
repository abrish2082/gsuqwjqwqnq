package et.gov.eep.fcms.controller.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Voucher.ChequePaymentVoucherbeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowansBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsAccountUseResult;
import et.gov.eep.fcms.entity.Vocher.FmsChequePaymentVoucher;
import et.gov.eep.fcms.entity.FmsDocumentFollowup;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.FmsLuPaymentType;
import et.gov.eep.fcms.entity.Vocher.FmsVoucher;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsLuVouchersType;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.admin.FmsVouchersNoRange;
import et.gov.eep.fcms.mapper.FmsLuPaymentTypeFacade;
import et.gov.eep.hrms.businessLogic.insure.HrinsurancePaymentLocal;
import et.gov.eep.hrms.businessLogic.medical.CashRefundApproveBeanLocal;
import et.gov.eep.hrms.businessLogic.medical.LocalMedSettlementBeanLocal;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollMonthlyTransactionLocal;
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
import java.util.Map.Entry;
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
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import securityBean.WorkFlow;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.fcms.businessLogic.Bond.BondForeignScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalScheduleBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPeriodsBeanLocal;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.Constants;
import webService.AAA;
import webService.IAdministration;
import securityBean.SessionBean;
import webService.EventEntry;
//</editor-fold>

@Named(value = "postingChequePaymentVoucherController")
@ViewScoped
public class postingChequePaymentVoucherController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="posting Cheque Payment Voucher Controller">
    public postingChequePaymentVoucherController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="create New ChequePaymentVoucher">
    public void createNewJv() {
        clearPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateJv = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateJv = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="populate">
    public void populate(SelectEvent event) {
        fmsVoucher = (FmsVoucher) event.getObject();
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        String splitedVoucherNO[] = fmsVoucher.getVoucherId().split("-");
        clearPopup();
        setVoucherNO(splitedVoucherNO[1]);
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
            if (!"SL".equals(splitedaccoutid[2])) {
                fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(splitedaccoutid[3]));
                fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
            }
            display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
            fmsVoucher.getFmsAccountUseList().get(i).setDisplay_conn(display_conn);
        }
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
        account.put(accountcredit, accountDebit);
        for (int i = 0; i < accountUseResults.size(); i++) {
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
        printe = true;
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

    //<editor-fold defaultstate="collapsed" desc="EJBs ">
    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;
    @EJB
    private ChequePaymentVoucherbeanLocal chequeVoucherBeanLocal;
    @EJB
    private FmsAccountUseBeanLocal fmsAccountUseBeanLocal;
    @EJB
    private FmsVoucherBeanLocal fmsVoucherBeanLocal;
    @EJB
    FmsCostCenterBeanLocal fmsCostCenterBeanLocal;
    @EJB
    FmsGeneralLedgerBeanLocal fmsGeneralLedgerBeanLocal;
    @EJB
    FmsLuSystemBeanLocal fmsLuSystemBeanLocal;
    @EJB
    subsidiaryBeanLocal subLedgerBeanLocal;
    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    @EJB
    FmsLuPaymentTypeFacade paymentTypeFacade;
    @EJB
    BondForeignScheduleBeanLocal bondForeignScheduleBeanLocal;
    @EJB
    BondLocalScheduleBeanLocal bondLocalScheduleBeanLocal;
    @EJB
    LocalMedSettlementBeanLocal localMedSettlementBeanLocal;
    @EJB
    CashRefundApproveBeanLocal cashRefundApproveBeanLocal;
    @EJB
    HrPayrollMonthlyTransactionLocal hrPayrollMonthlyTransactionLocal;
    @EJB
    HrPayrollPeriodsBeanLocal hrPayrollPeriodsBeanLocal;
    @EJB
    PmsProjectPaymentReqBeanLocal pmsProjectPaymentReqBeanLocal;
    @EJB
    FmsFieldAllowansBeanLocal fieldAllowansBeanLocal;
    @EJB
    HrinsurancePaymentLocal hrinsurancePaymentLocal;
    @EJB
    RequestforPaymentBeanLocal prmsRequestforPaymentBeanLocal;
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inject">
    @Inject
    localbean Languagelocalbean;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;
    @Inject
    FmsLuSystem fmsLuSystem;
    @Inject
    FmsCostCenter fmsCostCenter;
    @Inject
    FmsDocumentFollowup fmsDocumentFollowup;
    @Inject
    FmsChequePaymentVoucher chequePaymentVoucher;
    @Inject
    FmsAccountUse accountUseEnty;
    @Inject
    FmsBondLocalSchedule bondLocalSchedule;
    @Inject
    FmsVoucher fmsVoucher;
    @Inject
    WorkFlow workFlow;
    @Inject
    WfFcmsProcessed wfFcmsProcessed;
    @Inject
    private FmsGeneralLedger generalLedger;
    @Inject
    private FmsSubsidiaryLedger subsidiaryLedger;
    @Inject
    private FmsLuPaymentType luPaymentType;
    @Inject
    FmsBondForeignSchedule bondForeignSchedule;
    @Inject
    HrLocalMedSettlementDetail hrLocalMedSettlementDetail;
    @Inject
    HrLocalMedSettlements hrLocalMedSettlements;
    @Inject
    HrPayrollMonTransactions hrPayrollMonTransactions;
    @Inject
    PmsProjectPaymentRequest pmsProjectPaymentRequest;
    @Inject
    FmsFieldAllowance fieldAllowance;
    @Inject
    PrmsPaymentRequisition prmsPaymentRequisition;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsAccountUse> accountUseDetailDataModelposting;
    DataModel<FmsVoucher> voucherDataModel;
    DataModel<FmsChequePaymentVoucher> chequePaymentDataModel;
    private FmsAccountUse selectedAccountUses;
    private List<FmsVoucher> selectedJv;
    private List<FmsLuPaymentType> fmsLuPaymentType;
    private List<FmsBondLocalSchedule> bondLocalSchedules;
    private List<FmsBondForeignSchedule> bondForeignSchedules;
    List<HrLocalMedSettlementDetail> hrmedSettlmentDetailList;
    List<HrLocalMedSettlements> hrmedSettlmentList;
    List<HrPayrollMonTransactions> hrPayrollMonTransactionsList;
    List<PrmsPaymentRequisition> prmsPaymentRequisitionsList;
    List<PmsProjectPaymentRequest> pmsProjectPaymentReqsList;
    List<FmsFieldAllowance> fieldAllowances;
    List<HrInsurancePaymentDetail> hrInsurancePaymentList;
    List<String> referenceNumbers;
    List<FmsVoucher> fmsVouchersLists;
    int updteStatus = 0;
    private String selectedDecision = "";
    private String saveorUpdateBundle = "Create";
    private String ActionDebitCredit = "";
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);
    //-------------------------------------
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    boolean addButtonStatus = false;
    boolean taxrend = false;
    String add_colaps = "add";
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    private boolean printe = false;
    private String icone = "ui-icon-plus";
    Boolean selectOptionStatus = false;
    Boolean editRemoveStatus = false;
    Boolean void_dis = true;
    String VatTax = "0.00";
    String WITHHOLD = "0.00";
    String voucherNO = "";

    private List<Object> accountCredit;
    private HashMap<String, BigDecimal> accountDebit = new HashMap<>();
    private HashMap<String, BigDecimal> accountcredit = new HashMap<>();
    private HashMap<HashMap, HashMap> account = new HashMap<>();
    private DataModel<HashMap> accounthashmapModel;

    private String accountNo;
    private BigDecimal value;
    FmsAccountUseResult accountUseResult;
    List<FmsAccountUseResult> accountUseResults = new ArrayList<>();
    private List<Entry<String, BigDecimal>> entries;
    private List<Entry<String, BigDecimal>> entries_;

    List<FmsVoucher> listVoucher = null;
    int MajorCatagorylistId = 0;
    List<FmsVoucher> jvList;
    String voucherId = null;
    int dataTableUpdateStatus = 0;
    private String sysName;
    boolean cc = true;
    private String costName;
    private String generalLName;
    boolean sl = true;
    private String subLName;
    double Credit = 0.0;
    double Debit = 0.0;
    List<FmsGeneralLedger> listGeneralLedger = null;
    int GLListSize = 0;
    private String Conc = "";
    private String display_conn;
    FmsVoucher voucher;
    private List<FmsVoucher> selectedVouches = new ArrayList<>();
    List<FmsVoucher> vouchersList = new ArrayList<>();
    String rederedCheckBox = "true";
    private String PRStatus;
    private DataModel<FmsVoucher> vouchersDetailDataModel;
    boolean refbondlocal = false;
    boolean refbondforeign = false;
    boolean others = false;
    boolean refdisable = true;
    String reftype = "nun";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    /**
     *
     * @return
     */
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

    public boolean isOthers() {
        return others;
    }

    public void setOthers(boolean others) {
        this.others = others;
    }

    public List<FmsVoucher> getSelectedVouches() {
        if (selectedVouches == null) {
            selectedVouches = new ArrayList<>();
        }
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

    public FmsVoucher getVoucher() {
        if (voucher == null) {
            voucher = new FmsVoucher();
        }
        return voucher;
    }

    public void setVoucher(FmsVoucher voucher) {
        this.voucher = voucher;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
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

    public boolean isTaxrend() {
        return taxrend;
    }

    public void setTaxrend(boolean taxrend) {
        this.taxrend = taxrend;
    }

    public String getWITHHOLD() {
        return WITHHOLD;
    }

    public void setWITHHOLD(String WITHHOLD) {
        this.WITHHOLD = WITHHOLD;
    }

    public String getVatTax() {
        return VatTax;
    }

    public void setVatTax(String VatTax) {
        this.VatTax = VatTax;
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

    public void handleDecision(ValueChangeEvent decision) {
//        post_chequePaymentVoucher_data();
        if (decision.getNewValue() != null) {
            selectedDecision = decision.getNewValue().toString();
        }

    }

    String types = "";

    public void changeSignature(ValueChangeEvent e) {
        if (e.getNewValue().toString().contains("1")) {
            types = "1";
            System.err.println("-eeeeeeee--" + e.getNewValue().toString());
        } else {
            types = "2";
        }
    }

    public HrPayrollMonTransactions getHrPayrollMonTransactions() {
        if (hrPayrollMonTransactions == null) {
            hrPayrollMonTransactions = new HrPayrollMonTransactions();
        }
        return hrPayrollMonTransactions;
    }

    public void setHrPayrollMonTransactions(HrPayrollMonTransactions hrPayrollMonTransactions) {
        this.hrPayrollMonTransactions = hrPayrollMonTransactions;
    }

    public HrLocalMedSettlementDetail getHrLocalMedSettlementDetail() {
        if (hrLocalMedSettlementDetail == null) {
            hrLocalMedSettlementDetail = new HrLocalMedSettlementDetail();
        }
        return hrLocalMedSettlementDetail;
    }

    public void setHrLocalMedSettlementDetail(HrLocalMedSettlementDetail hrLocalMedSettlementDetail) {
        this.hrLocalMedSettlementDetail = hrLocalMedSettlementDetail;
    }

    public HrLocalMedSettlements getHrLocalMedSettlements() {
        if (hrLocalMedSettlements == null) {
            hrLocalMedSettlements = new HrLocalMedSettlements();
        }
        return hrLocalMedSettlements;
    }

    public void setHrLocalMedSettlements(HrLocalMedSettlements hrLocalMedSettlements) {
        this.hrLocalMedSettlements = hrLocalMedSettlements;
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

    public List<HrLocalMedSettlements> getHrmedSettlmentList() {
        if (hrmedSettlmentList == null) {
            hrmedSettlmentList = new ArrayList<>();
        }
        return hrmedSettlmentList;
    }

    public void setHrmedSettlmentList(List<HrLocalMedSettlements> hrmedSettlmentList) {
        this.hrmedSettlmentList = hrmedSettlmentList;
    }

    public List<FmsFieldAllowance> getFieldAllowances() {
        if (fieldAllowances == null) {
            fieldAllowances = new ArrayList<>();
        }
        return fieldAllowances;
    }

    public FmsFieldAllowance getFieldAllowance() {
        if (fieldAllowance == null) {
            fieldAllowance = new FmsFieldAllowance();
        }
        return fieldAllowance;
    }

    public void setFieldAllowance(FmsFieldAllowance fieldAllowance) {
        this.fieldAllowance = fieldAllowance;
    }

    public void setFieldAllowances(List<FmsFieldAllowance> fieldAllowances) {
        this.fieldAllowances = fieldAllowances;
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

    public int getInterCatListSizes() {
        return interCatListSizes;
    }

    public void setInterCatListSizes(int interCatListSizes) {
        this.interCatListSizes = interCatListSizes;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isPrinte() {
        return printe;
    }

    public void setPrinte(boolean printe) {
        this.printe = printe;
    }

    public Set<String> getCheckSubsidiaryCode() {
        return checkSubsidiaryCode;
    }

    public void setCheckSubsidiaryCode(Set<String> checkSubsidiaryCode) {
        this.checkSubsidiaryCode = checkSubsidiaryCode;
    }

    public Set<BigInteger> getCheckDebitCredit() {
        return checkDebitCredit;
    }

    public void setCheckDebitCredit(Set<BigInteger> checkDebitCredit) {
        this.checkDebitCredit = checkDebitCredit;
    }

    public int getGLListSize() {
        return GLListSize;
    }

    public void setGLListSize(int GLListSize) {
        this.GLListSize = GLListSize;
    }

    public FmsSubsidiaryLedger getSubsidiaryLedger() {
        return subsidiaryLedger;
    }

    public void setSubsidiaryLedger(FmsSubsidiaryLedger subsidiaryLedger) {
        this.subsidiaryLedger = subsidiaryLedger;
    }

    public FmsAccountUse getSelectedAccountUses() {
        return selectedAccountUses;
    }

    public void setSelectedAccountUses(FmsAccountUse selectedAccountUses) {
        this.selectedAccountUses = selectedAccountUses;
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

    public List<FmsVoucher> getSelectedJv() {
        return selectedJv;
    }

    public void setSelectedJv(List<FmsVoucher> selectedJv) {
        this.selectedJv = selectedJv;
    }

    public int getIntermidiateCatStatus() {
        return IntermidiateCatStatus;
    }

    public void setIntermidiateCatStatus(int IntermidiateCatStatus) {
        this.IntermidiateCatStatus = IntermidiateCatStatus;
    }

    public int getCatagorynameStatus() {
        return catagorynameStatus;
    }

    public void setCatagorynameStatus(int catagorynameStatus) {
        this.catagorynameStatus = catagorynameStatus;
    }

    public boolean isAddButtonStatus() {
        return addButtonStatus;
    }

    public void setAddButtonStatus(boolean addButtonStatus) {
        this.addButtonStatus = addButtonStatus;
    }

    public String getAdd_colaps() {
        return add_colaps;
    }

    public void setAdd_colaps(String add_colaps) {
        this.add_colaps = add_colaps;
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

    public Boolean getSelectOptionStatus() {
        return selectOptionStatus;
    }

    public void setSelectOptionStatus(Boolean selectOptionStatus) {
        this.selectOptionStatus = selectOptionStatus;
    }

    public Boolean getEditRemoveStatus() {
        return editRemoveStatus;
    }

    public void setEditRemoveStatus(Boolean editRemoveStatus) {
        this.editRemoveStatus = editRemoveStatus;
    }

    public Boolean getVoid_dis() {
        return void_dis;
    }

    public void setVoid_dis(Boolean void_dis) {
        this.void_dis = void_dis;
    }

    public List<FmsVoucher> getListVoucher() {
        return listVoucher;
    }

    public void setListVoucher(List<FmsVoucher> listVoucher) {
        this.listVoucher = listVoucher;
    }

    public int getMajorCatagorylistId() {
        return MajorCatagorylistId;
    }

    public void setMajorCatagorylistId(int MajorCatagorylistId) {
        this.MajorCatagorylistId = MajorCatagorylistId;
    }

    public FmsVouchersNoRange getVouchersNoRange() {
        return vouchersNoRange;
    }

    public void setVouchersNoRange(FmsVouchersNoRange vouchersNoRange) {
        this.vouchersNoRange = vouchersNoRange;
    }

    public List<FmsGeneralLedger> getListGeneralLedger() {
        return listGeneralLedger;
    }

    public void setListGeneralLedger(List<FmsGeneralLedger> listGeneralLedger) {
        this.listGeneralLedger = listGeneralLedger;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    /**
     *
     * @param fmsGeneralLedger
     */
    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    /**
     *
     * @return
     */
    public FmsSubsidiaryLedger getFmsSubsid1aryLedger1() {
        if (fmsSubsid1aryLedger1 == null) {
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();

        }
        return fmsSubsid1aryLedger1;
    }

    /**
     *
     * @param fmsSubsid1aryLedger1
     */
    public void setFmsSubsid1aryLedger1(FmsSubsidiaryLedger fmsSubsid1aryLedger1) {
        this.fmsSubsid1aryLedger1 = fmsSubsid1aryLedger1;
    }

    /**
     *
     * @return
     */
    public FmsLuSystem getFmsLuSystem() {
        if (fmsLuSystem == null) {
            fmsLuSystem = new FmsLuSystem();
        }
        return fmsLuSystem;
    }

    /**
     *
     * @param fmsLuSystem
     */
    public void setFmsLuSystem(FmsLuSystem fmsLuSystem) {
        this.fmsLuSystem = fmsLuSystem;
    }

    /**
     *
     * @return
     */
    public FmsCostCenter getFmsCostCenter() {
        if (fmsCostCenter == null) {
            fmsCostCenter = new FmsCostCenter();
        }
        return fmsCostCenter;
    }

    /**
     *
     * @param fmsCostCenter
     */
    public void setFmsCostCenter(FmsCostCenter fmsCostCenter) {
        this.fmsCostCenter = fmsCostCenter;
    }

    /**
     *
     * @return
     */
    public FmsDocumentFollowup getFmsDocumentFollowup() {
        if (fmsDocumentFollowup == null) {
            fmsDocumentFollowup = new FmsDocumentFollowup();

        }
        return fmsDocumentFollowup;
    }

    /**
     *
     * @param fmsDocumentFollowup
     */
    public void setFmsDocumentFollowup(FmsDocumentFollowup fmsDocumentFollowup) {
        this.fmsDocumentFollowup = fmsDocumentFollowup;
    }

    /**
     *
     * @return
     */
    public FmsChequePaymentVoucher getChequePaymentVoucher() {
        if (chequePaymentVoucher == null) {
            chequePaymentVoucher = new FmsChequePaymentVoucher();
        }
        return chequePaymentVoucher;
    }

    /**
     *
     * @param chequePaymentVoucher
     */
    public void setChequePaymentVoucher(FmsChequePaymentVoucher chequePaymentVoucher) {
        this.chequePaymentVoucher = chequePaymentVoucher;
    }

    /**
     *
     * @return
     */
    public BigDecimal getValueDebitCredit() {
        return ValueDebitCredit;
    }

    /**
     *
     * @param ValueDebitCredit
     */
    public void setValueDebitCredit(BigDecimal ValueDebitCredit) {
        this.ValueDebitCredit = ValueDebitCredit;
    }

    /**
     *
     * @return
     */
    public String getActionDebitCredit() {

        return ActionDebitCredit;
    }

    /**
     *
     * @param ActionDebitCredit
     */
    public void setActionDebitCredit(String ActionDebitCredit) {
        this.ActionDebitCredit = ActionDebitCredit;
    }

    /**
     *
     * @return
     */
    public FmsGeneralLedger getGeneralLedger() {
        if (generalLedger == null) {
            generalLedger = new FmsGeneralLedger();
        }
        return generalLedger;
    }

    /**
     *
     * @param generalLedger
     */
    public void setGeneralLedger(FmsGeneralLedger generalLedger) {
        this.generalLedger = generalLedger;
    }

    /**
     *
     * @return
     */
    public DataModel<FmsAccountUse> getAccountUseDetailDataModel() {
        if (accountUseDetailDataModel == null) {
            accountUseDetailDataModel = new ListDataModel<>();
        }
        return accountUseDetailDataModel;
    }

    /**
     *
     * @param accountUseDetailDataModel
     */
    public void setAccountUseDetailDataModel(DataModel<FmsAccountUse> accountUseDetailDataModel) {
        this.accountUseDetailDataModel = accountUseDetailDataModel;
    }

    /**
     *
     * @return
     */
    public DataModel<FmsChequePaymentVoucher> getChequePaymentDataModel() {
        if (chequePaymentDataModel == null) {
            chequePaymentDataModel = new ArrayDataModel<>();
        }
        return chequePaymentDataModel;
    }

    /**
     *
     * @param chequePaymentDataModel
     */
    public void setChequePaymentDataModel(DataModel<FmsChequePaymentVoucher> chequePaymentDataModel) {
        this.chequePaymentDataModel = chequePaymentDataModel;
    }

    public FmsAccountUse getAccountUseEnty() {
        if (accountUseEnty == null) {
            accountUseEnty = new FmsAccountUse();
        }
        return accountUseEnty;
    }

    /**
     *
     * @param accountUseEnty
     */
    public void setAccountUseEnty(FmsAccountUse accountUseEnty) {
        this.accountUseEnty = accountUseEnty;
    }

    /**
     *
     * @return
     */
    public FmsVoucher getFmsVoucher() {
        if (fmsVoucher == null) {
            fmsVoucher = new FmsVoucher();
        }
        return fmsVoucher;
    }

    /**
     *
     * @param fmsVoucher
     */
    public void setFmsVoucher(FmsVoucher fmsVoucher) {
        this.fmsVoucher = fmsVoucher;
    }

    //--------------------------------------------
    /**
     *
     * @return
     */
    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    /**
     *
     * @param saveorUpdateBundle
     */
    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    /**
     *
     * @return
     */
    public String getConc() {
        return Conc;
    }

    /**
     *
     * @param Conc
     */
    public void setConc(String Conc) {
        this.Conc = Conc;
    }

    /**
     *
     * @return
     */
    public int getUpdteStatus() {
        return updteStatus;
    }

    /**
     *
     * @param updteStatus
     */
    public void setUpdteStatus(int updteStatus) {
        this.updteStatus = updteStatus;
    }

    public FmsBondLocalSchedule getBondLocalSchedule() {
        if (bondLocalSchedule == null) {
            bondLocalSchedule = new FmsBondLocalSchedule();
        }
        return bondLocalSchedule;
    }

    public void setBondLocalSchedule(FmsBondLocalSchedule bondLocalSchedule) {
        this.bondLocalSchedule = bondLocalSchedule;
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
        flpt.setPayment("PV");
        return paymentTypeFacade.findByPaymentType(flpt);
    }

    public void setFmsLuPaymentType(List<FmsLuPaymentType> fmsLuPaymentType) {
        this.fmsLuPaymentType = fmsLuPaymentType;
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

    public FmsBondForeignSchedule getBondForeignSchedule() {
        if (bondForeignSchedule == null) {
            bondForeignSchedule = new FmsBondForeignSchedule();
        }
        return bondForeignSchedule;
    }

    public void setBondForeignSchedule(FmsBondForeignSchedule bondForeignSchedule) {
        this.bondForeignSchedule = bondForeignSchedule;
    }

    public List<FmsBondForeignSchedule> getBondForeignSchedules() {
        bondForeignSchedules = bondForeignScheduleBeanLocal.searchForingns_Bondpayed();
        return bondForeignSchedules;
    }

    public void setBondForeignSchedules(List<FmsBondForeignSchedule> bondForeignSchedules) {
        this.bondForeignSchedules = bondForeignSchedules;
    }

    public DataModel<FmsAccountUse> getAccountUseDetailDataModelposting() {
        accountUseDetailDataModelposting = new ListDataModel(vouchersDetailDataModel.getRowData().getFmsAccountUseList());
        return accountUseDetailDataModelposting;
    }

    public void setAccountUseDetailDataModelposting(DataModel<FmsAccountUse> accountUseDetailDataModelposting) {
        this.accountUseDetailDataModelposting = accountUseDetailDataModelposting;
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

    //</editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="save - Update">
    /**
     *
     * @return
     */
    String amountInword;
//    String vInword;
//    String wInword;

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
            System.out.println("amountInword " + amountInword);
            //amountInFigure = Double.toString(num);
            //System.err.println("AmountInword=" + amountInword + "num=" + num);

        } catch (Exception ex) {
        }
    }

//    public void convertVInWored(double Vatamount) {
//        System.err.println("inside the event");
//        try {
//
//            double num = Vatamount;
//            int birr = (int) Math.floor(num);
//            int cents = (int) Math.floor((num - birr) * 100);
//            int cent1;
//            cent1 = (int) ((num - birr) * 10000);
//            if (!(cent1 % 10 == 0)) {
//                double cent_1 = cent1 / 100.0;
//                cents = (int) Math.ceil(cent_1);
//            }
//            String vInwordConverted = EnglishNumberToWords.convert(birr) + " Birr" + " and "
//                    + EnglishNumberToWords.convert(cents) + " Cents";
//            vInword = vInwordConverted;
//            System.out.println("amountInword " + vInword);
//            //amountInFigure = Double.toString(num);
//            //System.err.println("vInword=" + vInword + "num=" + num);
//
//        } catch (Exception ex) {
//        }
//    }
//    public void convertWInWored(double wAmount) {
//        System.err.println("inside the event");
//        try {
//
//            double num = wAmount;
//            int birr = (int) Math.floor(num);
//            int cents = (int) Math.floor((num - birr) * 100);
//            int cent1;
//            cent1 = (int) ((num - birr) * 10000);
//            if (!(cent1 % 10 == 0)) {
//                double cent_1 = cent1 / 100.0;
//                cents = (int) Math.ceil(cent_1);
//            }
//            String wInwordConverted = EnglishNumberToWords.convert(birr) + " Birr" + " and "
//                    + EnglishNumberToWords.convert(cents) + " Cents";
//            wInword = wInwordConverted;
//            System.out.println("wInword " + wInword);
//            //amountInFigure = Double.toString(num);
//            //System.err.println("wInword=" + wInword + "num=" + num);
//
//        } catch (Exception ex) {
//        }
//    }
    public String saveChequePaymentVoucher() {
        System.out.println("ammt intero: ");
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "saveChequePaymentVoucher", dataset)) {
//                 put ur code here...! 
            double amt = chequePaymentVoucher.getAmountInFigure().doubleValue();

//        String v = getVatTax();
//       String w = getWITHHOLD();
            convertAmountInWored(amt);
//        convertVInWored(v);
//        convertWInWored(w);
            chequePaymentVoucher.setAmountInWord(amountInword);
//        chequePaymentVoucher.setVatInWord(vInword);
//        chequePaymentVoucher.setWitholdInWord(wInword);
            System.out.println("ammt: " + amt);
//        System.out.println("v: " + v);
//        System.out.println("withold: " + w);

            if (((amt == Debit) && (Debit == Credit))) {

                if (updteStatus == 0) {
                    String voucherID, preparedate;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    preparedate = dateFormat.format(chequePaymentVoucher.getPreparedDate());
                    voucherID = preparedate + "-" + getVoucherNO() + "-" + "ChPV";
                    fmsVoucher.setVoucherId(voucherID);
                    if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher)) {
                        System.err.println("Value is Duplicated");
                        JsfUtil.addSuccessMessage("Value is Duplicated");
                    } else {

                        try {

                            fmsVoucher.setVoucherId(voucherID);
                            chequePaymentVoucher.setSourceJeId("0");
                            chequePaymentVoucher.setStatus("1");
                            fmsVoucher.setStatus(2);
                            fmsDocumentFollowup.setDocumentReference("23526");
                            fmsDocumentFollowup.setType("ChPV");
                            fmsVoucher.setType("CHPV");
                            fmsVoucher.addToFmsCheckPaymentDetail(chequePaymentVoucher);
                            fmsVoucherBeanLocal.create(fmsVoucher);
                            JsfUtil.addSuccessMessage("Cheque Payment Voucher Data Created");
                            int currentNo = Integer.parseInt(getVoucherNO()) + 1;
                            vouchersNoRange.setCurrentNo(String.valueOf(currentNo));
                            noRangeBeanLocal.edit(vouchersNoRange);
                            return clearPage();
                        } catch (Exception e) {
                            JsfUtil.addFatalMessage("Something Occured on Data Created");
                            return null;
                        }
                    }
//               }
                } else if (updteStatus == 1) {
                    fmsVoucherBeanLocal.edit(fmsVoucher);
                    JsfUtil.addSuccessMessage("Cheque Payment Voucher Data is Updated");
                    return clearPage();
                } else if (updteStatus == 3) {
                    if (listVoucher.size() > 0) {
                        try {
                            for (int i = 0; i < listVoucher.size(); i++) {
                                fmsVoucher.setStatus(3);
                                FmsVoucher fmsVoucher = new FmsVoucher();
                                fmsVoucher = listVoucher.get(i);
                                fmsVoucherBeanLocal.edit(listVoucher.get(i));
                                fmsVoucherBeanLocal.edit(fmsVoucher);

                                JsfUtil.addSuccessMessage("Cheque Payment Voucher Data is posting Done");

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
                }
                return null;
            } else {
                JsfUtil.addFatalMessage("Pls check for balance");
                System.out.println("ammt: " + amt);
                System.out.println("credit: " + Credit);
                System.out.println("debit: " + Debit);
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
//
        }
        return null;

    }

    /**
     *
     * @return
     *///         QName qualifiedName = new QName("", "project");
    public String clearPage() {
        fmsVoucher = null;
        chequePaymentVoucher = null;
        accountUseEnty = null;
        accountUseDetailDataModel = null;
        selectedVouches = null;
        updteStatus = 0;

        return null;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    /**
     *
     * @param voucheid
     * @return
     */
    public List<FmsVoucher> JVSearchlist(String voucheid) {
        listVoucher = null;
        fmsVoucher.setVoucherId(voucheid);
        listVoucher = fmsVoucherBeanLocal.searchVoucheidTypeCHPV(fmsVoucher);
        return listVoucher;
    }

    /**
     *
     * @param event
     */
    public void recreateJvDataModel() {
        voucherDataModel = null;
        voucherDataModel = new ListDataModel(new ArrayList(jvList));
    }

    public void searchchequePayment() {
        try {
            //find all
            if (voucherId.isEmpty()) {
                fmsVoucher.setType("CHPV");
                jvList = fmsVoucherBeanLocal.findAllbytype(fmsVoucher);
                System.out.println("xx");
                recreateJvDataModel();
            } else if (!(voucherId.isEmpty())) {
                //find by begins with
                System.out.println("ss");
                fmsVoucher.setVoucherId(voucherId);
                jvList = fmsVoucherBeanLocal.searchVoucheidTypeCHPV(fmsVoucher);
                recreateJvDataModel();
            } else {
                JsfUtil.addFatalMessage("Sorry!.there is no voucher begins with " + fmsVoucher.getVoucherId() + ". Try again.");
            }
        } catch (Exception e) {
            JsfUtil.addFatalMessage("Sorry!. No voucher begins with " + fmsVoucher.getVoucherId() + ". Try again.");
            throw e;
        }
    }

    public void getJVInformation(SelectEvent event) {

        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        String splitedVoucherNO[] = fmsVoucher.getVoucherId().split("-");
        clearPopup();
        setVoucherNO(splitedVoucherNO[1]);
        //Concatenate();
        recreateModelDetail();
        recreateJVModelDetail();
        addButtonStatus = true;
        selectOptionStatus = true;
        editRemoveStatus = true;
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        printe = true;

    }

    public void getJVInformation1(SelectEvent event) {

        String selectedVoucherId = event.getObject().toString();
        fmsVoucher.setVoucherId(selectedVoucherId);
        fmsVoucher = fmsVoucherBeanLocal.getVoucherIDInfo(fmsVoucher);
        String splitedVoucherNO[] = fmsVoucher.getVoucherId().split("-");
        setVoucherNO(splitedVoucherNO[1]);
        recreateModelDetail();
        recreateJVModelDetail();
        updteStatus = 1;
        saveorUpdateBundle = "Update";
        printe = true;

    }

    public void populateAccDetail(SelectEvent event) {
//                        accountUseEnty.setFmsAccountUseId(Integer.parseInt((String) event.getObject()));
        accountUseEnty = (FmsAccountUse) event.getObject();
        dataTableUpdateStatus = 1;
        String subsidari;
//                        accountUseEnty = getAccountUseDetailDataModel().getRowData();
//                        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
//                        fmsGeneralLedger = accountUseEnty.getSubsidaryId().getGeneralLedgerId();
        subsidari = accountUseEnty.getSubsidaryId();
//                                getAccountUseDetailDataModel().getRowData().getSubsidaryId();
        int x = 0;
        String[] col = subsidari.split("-");

        int sys = Integer.parseInt(col[0]);
        fmsLuSystem.setSystemId(sys);
        int gl = Integer.parseInt(col[2]);
        fmsGeneralLedger.setGeneralLedgerId(gl);
        if (!"CC".equals(col[1])) {
            int cc = Integer.parseInt(col[1]);
            fmsCostCenter.setCostCenterId(cc);
        }
        if (!"SL".equals(col[3])) {
            int sl = Integer.parseInt(col[3]);
            fmsSubsid1aryLedger1.setSubsidiaryId(sl);
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

    public SelectItem[] getpaymentTypeList() {
        return JsfUtil.getSelectItems(paymentTypeFacade.findAll(), true);

    }

    public SelectItem[] getBondpaymentRfList() {
        return JsfUtil.getSelectItems(bondLocalScheduleBeanLocal.searchpayedSchedule(), true);

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" PostConstruct getCurrentNumber - Implimentation"> 
    FmsVouchersNoRange vouchersNoRange = new FmsVouchersNoRange();

    @PostConstruct
    public void init() {
        String refNo = null;
        Integer seqNo = 0;
        vouchersNoRange.setStatus(1);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(2);
        vouchersNoRange.setTypeId(luVouchersType);
        vouchersNoRange = noRangeBeanLocal.getCurrentVoucherNumber(vouchersNoRange);
        setVoucherNO(vouchersNoRange.getCurrentNo());
//        fmsVoucher = null;
        System.out.println("is app" + workFlow.isApproveStatus() + " is check" + workFlow.isCheckStatus() + " is prep" + workFlow.isPrepareStatus());
        System.out.println("U Name " + workFlow.getUserName());
        fmsVoucher.setProcessedBy(workFlow.getUserName());
        if (Languagelocalbean.getLangsession().getAttribute("lang") != null) {
            if (Languagelocalbean.getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
                Languagelocalbean.changeLanguage("ET");
            } else {
                Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
            }
        }
    }
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="- Number To Word convertor-Implimentation">
    private NumberConverter numberConverter = new NumberConverter();

    /**
     *
     * @return
     */
    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    /**
     *
     * @param numberConverter
     */
    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AccountUse,GeneralLedger,Subsidiary - Implimentation">

    public void optionHandiler() {
        if (!addButtonStatus) {
            addButtonStatus = true;
            add_colaps = "Colaps";
        } else {
            addButtonStatus = false;
            add_colaps = "add";
        }

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
            fmsCostCenter = new FmsCostCenter();
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
            fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
            fmsSubsid1aryLedger1.setGeneralLedgerId(fmsGeneralLedger);

        }
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
            subLName = fmsSubsid1aryLedger1.getAccountTitle();
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

    public String addSubsidiaryLedgerDetail() {
//                        if (fmsGeneralLedger != null&&fmsLuSystem!=null) {
        accountUseEnty = new FmsAccountUse();
        Concatenate();

        accountUseEnty.setSubsidaryId(Conc);
        accountUseEnty.setDisplay_conn(display_conn);

//                        }
        if (ActionDebitCredit.equals("Debit")) {
            accountUseEnty.setDebit(ValueDebitCredit);
            accountUseEnty.setCredit(new BigDecimal(0.00));
        }
        if (ActionDebitCredit.equals("Credit")) {
            accountUseEnty.setDebit(new BigDecimal(0.00));
            accountUseEnty.setCredit(ValueDebitCredit);

        }
        //check duplicate Debit Credit with same SL code

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

            recreateModelDetail();
            return clearPopup();
        }
        return null;
    }
    Set<String> checkSubsidiaryCode = new HashSet<>();
    Set<BigInteger> checkDebitCredit = new HashSet<>();

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
        chequePaymentVoucher = fmsVoucher.getFmsChequePaymentVoucher();
    }

    public void recreateModelDetail() {
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(fmsVoucher.getFmsAccountUseList());
    }

    public void remove() {
        accountUseEnty = null;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        fmsVoucher.getFmsAccountUseList().remove(accountUseEnty);
        recreateModelDetail();
    }
//</editor-fold>  
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
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(getVouchersDetailDataModel().getRowData().getFmsAccountUseList());
    }

    public String post_chequePaymentVoucher_data() {
        AAA securitySrvice = new AAA();
        IAdministration security = securitySrvice.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "post_chequePaymentVoucher_data", dataset)) {
            //put ur code here...!
            if (selectedVouches.size() > 0) {
                try {
                    selectedVouches.stream().map((selectedVouche) -> {
                        FmsVoucher fmsVoucher = new FmsVoucher();
                        fmsVoucher = selectedVouche;
                        return fmsVoucher;
                    }).map((fmsVoucher) -> {
                        fmsVoucher.setStatus(Constants.VOUCHER_POST);
                        //fmsVoucher.setStatus(Constants.VOUCHER_POST);
//                        wfFcmsProcessed.setDecision(fmsVoucher.getStatus());
                        return fmsVoucher;
                    }).forEach((fmsVoucher) -> {
                        //                    fmsVoucherBeanLocal.edit(selectedVouches.get(i));
                        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(sessionBeanLocal.getUserId()));
                        wfFcmsProcessed.setDecision(Constants.VOUCHER_POST);
                        wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                        wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getProcessedDate()));
                        fmsVoucher.setProcessedBy(wfFcmsProcessed.getProcessedOn());
                        wfFcmsProcessed.setFmsVoucherId(fmsVoucher);
                        fmsVoucherBeanLocal.edit(fmsVoucher);
                        wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);
//                        wfFcmsProcessedBeanLocal.edit(wfFcmsProcessed);
                    });
                    JsfUtil.addSuccessMessage("Cheque Payment   Voucher Data is Post Done");
                    return clearPage();

                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Something Occured on Data post");
                    return null;
                }

            } else {
                JsfUtil.addFatalMessage("Select one to Post");
                return null;
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

    public String void_chequePaymentVoucher_data() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "void_chequePaymentVoucher_data", dataset)) {
//                 put ur code here...!   
            if (selectedVouches.size() > 0) {
                try {
                    selectedVouches.stream().map((selectedVouche) -> {
                        FmsVoucher fmsVoucherNEW = new FmsVoucher();
                        fmsVoucherNEW = selectedVouche;
                        return fmsVoucherNEW;
                    }).map((fmsVoucherNEW) -> {
                        System.out.println("2222");
                        fmsVoucherNEW.setStatus(38);
                        fmsVoucherNEW.setReason(fmsVoucher.getReason());
                        return fmsVoucherNEW;
                    }).forEach((fmsVoucherNEW) -> {
                        System.out.println("33333");
                        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(sessionBeanLocal.getUserId()));
                        wfFcmsProcessed.setDecision(securityBean.Constants.VOUCHER_VOID);
                        wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                        wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getProcessedDate()));
                        fmsVoucher.setProcessedBy(wfFcmsProcessed.getProcessedOn());
                        // wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);
                        fmsVoucher.setStatus(securityBean.Constants.VOUCHER_VOID);
                        //                    fmsVoucherBeanLocal.edit(selectedVouches.get(i));
                        fmsVoucherBeanLocal.edit(fmsVoucherNEW);
                    });
                    JsfUtil.addSuccessMessage("Cheque Payment   Voucher Data is Void Done");
                    return clearPage();

                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Something Occured on Data Void");
                    return null;
                }
            } else {
                JsfUtil.addFatalMessage("Select one to Void");
                return null;
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

    public List<FmsVoucher> getFmsVouchersLists() {
        if (fmsVouchersLists == null) {
            fmsVouchersLists = new ArrayList<>();
        }
        return fmsVouchersLists;
    }

    public void setFmsVouchersLists(List<FmsVoucher> fmsVouchersLists) {
        this.fmsVouchersLists = fmsVouchersLists;
    }

    public String generatePostingChpv() {
        vouchersList = null;
        vouchersList = new ArrayList<>();
        vouchersList = fmsVoucherBeanLocal.handleSearchPostChpv();
        for (int j = 0; j < vouchersList.size(); j++) {
        }
        recreateVoucherModelDetail();
        updteStatus = 3;
        listVoucher = null;
        listVoucher = new ArrayList<>();
        return null;

    }
    //</editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="Ref - No">
    public List<FmsBondLocalSchedule> fetchBondLocalSchedules() {
        referenceNumbers = new ArrayList<>();
        bondLocalSchedules = bondLocalScheduleBeanLocal.searchpayedSchedule();
        for (int i = 0; i < bondLocalSchedules.size(); i++) {
            String ref = null;
            ref = bondLocalSchedules.get(i).getLocalBondId().getSerialNo() + "-" + bondLocalSchedules.get(i).getNoOfInstallmen();
            getReferenceNumbers().add(ref);
        }
        return bondLocalSchedules;
    }

    public List<FmsBondForeignSchedule> fetchBondForeignSchedules() {
        referenceNumbers = new ArrayList<>();
        bondForeignSchedules = bondForeignScheduleBeanLocal.searchForingns_Bondpayed();
        for (int i = 0; i < bondForeignSchedules.size(); i++) {
            String ref = null;
            ref = bondForeignSchedules.get(i).getBondForeignId().getSerialNo() + "-" + bondForeignSchedules.get(i).getNoOfInstallmen();
            getReferenceNumbers().add(ref);
        }
        return bondForeignSchedules;
    }

    public void ref(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            reftype = event.getNewValue().toString();
            luPaymentType.setPaymentType(event.getNewValue().toString());
            if (luPaymentType.getPaymentType().matches("Local Bond")) {
                fetchBondLocalSchedules();
                refbondforeign = true;
            } else if (reftype.matches("Foreign Bond")) {
                fetchBondForeignSchedules();
                refbondforeign = true;
            } else if (reftype.matches("Medical")) {
                System.out.println("inside rer method___");
                referenceNumbers = new ArrayList<>();
                hrmedSettlmentDetailList = localMedSettlementBeanLocal.fetchApprovedPayments();
                if (!hrmedSettlmentDetailList.isEmpty()) {
                    for (HrLocalMedSettlementDetail hrmedSettlmentDetailList1 : hrmedSettlmentDetailList) {
                        HrLocalMedSettlements localMedObject;
                        localMedObject = cashRefundApproveBeanLocal.getSelectedRequest(hrmedSettlmentDetailList1.getId());
                        //                        if (localMedObject.getStatus().equalsIgnoreCase("1")) {
                        getReferenceNumbers().add(localMedObject.getReferenceNo());
//                        }
                    }

                    refNoList();
                    refbondforeign = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;
            } else if (reftype.matches("Court Order")) {
                referenceNumbers = new ArrayList<>();
                hrPayrollMonTransactionsList = hrPayrollMonthlyTransactionLocal.fetchCourtOrderPayments();
                if (!hrPayrollMonTransactionsList.isEmpty()) {
                    for (HrPayrollMonTransactions hrPayrollMonTransactionsList1 : hrPayrollMonTransactionsList) {
                        HrPayrollMonTransactions monthlyPayrollObject;
                        HrPayrollPeriods hrPayrollPeriodsObj;
                        monthlyPayrollObject = hrPayrollMonthlyTransactionLocal.getSelectedPayment(hrPayrollMonTransactionsList1.getId());
                        hrPayrollPeriodsObj = hrPayrollPeriodsBeanLocal.findPayrollPeriodById(monthlyPayrollObject.getPayrollPeriodId().getId().intValue());
                        getReferenceNumbers().add(monthlyPayrollObject.getId() + " - " + monthlyPayrollObject.getEmpId().toString() + " - " + hrPayrollPeriodsObj.getPaymentMadeForTheMonthOf());
                    }
                    refNoList();
                    refbondforeign = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;

            } else if (reftype.matches("Projects")) {
                referenceNumbers = new ArrayList<>();
                pmsProjectPaymentReqsList = pmsProjectPaymentReqBeanLocal.fetchNewProjectPayments();
                System.out.println("size of payment type list" + pmsProjectPaymentReqsList.size());
                if (!pmsProjectPaymentReqsList.isEmpty()) {
                    pmsProjectPaymentReqsList.stream().forEach((pmsProjectPaymentReqsList1) -> {
                        getReferenceNumbers().add(pmsProjectPaymentReqsList1.getPaymentNo());
                    });
                    refNoList();
                    refbondforeign = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = true;

            } else if (reftype.matches("Field Allowance")) {
                System.out.println("inside fild allowance method");
                referenceNumbers = new ArrayList<>();
                fieldAllowances = fieldAllowansBeanLocal.searchnotexitvocher();
                System.out.println("size " + fieldAllowances.size() + "_____ " + fieldAllowances.get(0).getRequestNo());
                //System.out.println("=====fild allowance ===" + fieldAllowance.getTotalExpense());
                fieldAllowances.get(0).getEmpId();

                if (!fieldAllowances.isEmpty()) {
                    fieldAllowances.stream().forEach((fieldAllowanceobj) -> {
                        getReferenceNumbers().add(fieldAllowanceobj.getRequestNo());
                    });
                    refNoList();
                    refbondforeign = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
                others = false;
            } else if (reftype.matches("Payment Requisition")) {
                referenceNumbers = new ArrayList<>();
                prmsPaymentRequisitionsList = prmsRequestforPaymentBeanLocal.getPrmsPaymentRequisitions();
                if (!prmsPaymentRequisitionsList.isEmpty()) {
                    for (PrmsPaymentRequisition prmsPaymentRequisitions_ : prmsPaymentRequisitionsList) {
                        getReferenceNumbers().add(prmsPaymentRequisitions_.getReqPaymentNo());
                    }
                    refNoList();
                    refbondforeign = true;
                    others = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
            } else if (reftype.matches("Insurance")) {
                referenceNumbers = new ArrayList<>();
                hrInsurancePaymentList = hrinsurancePaymentLocal.fetchNewInsurancePayments();
                if (!hrInsurancePaymentList.isEmpty()) {
                    for (HrInsurancePaymentDetail hrInsurancePaymentList1 : hrInsurancePaymentList) {

                        getReferenceNumbers().add(hrInsurancePaymentList1.getReferenceNo());
                    }
                    refNoList();
                    refbondforeign = true;
                    others = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
            } else if (reftype.matches("Other")) {
                refbondforeign = false;
                others = true;

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
    //</editor-fold> 
}
