package et.gov.eep.fcms.controller.Voucher;
//<editor-fold defaultstate="collapsed" desc="import">

import et.gov.eep.commonApplications.businessLogic.WfFcmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfFcmsProcessed;
import et.gov.eep.commonApplications.localbean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Voucher.CashReceiptVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.perDiem.FmsFieldAllowansBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsVouchersNoRangeBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.controller.Constants;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsAccountUseResult;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
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
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

@Named(value = "postingCashPaymentOrderController")
@ViewScoped
public class postingCashPaymentOrderController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="posting Cash Payment Order Controller">
    public postingCashPaymentOrderController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(3);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    CashReceiptVoucherBeanLocal cashPaymentOrderBeanLocal;
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
    @Inject
    LocalMedSettlementBeanLocal localMedSettlementBeanLocal;
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="recreate posting Cash Payment Order Data Model">
    public void recreateJvDataModel() {
        voucherDataModel = null;
        voucherDataModel = new ListDataModel(new ArrayList(VoucherList));
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
        //Concatenate();
        for (int i = 0; i < fmsVoucher.getFmsAccountUseList().size(); i++) {
            String splitedaccoutid[] = fmsVoucher.getFmsAccountUseList().get(i).getSubsidaryId().split("-");
            fmsLuSystem.setSystemId(Integer.parseInt(splitedaccoutid[0]));
            fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
            System.out.println("system id " + i + " ->" + fmsLuSystem.getSystemCode());
            String CC_display = "CC";
            String SL_display = "SL";
            if (!"CC".equals(splitedaccoutid[0])) {
                fmsCostCenter.setCostCenterId(Integer.parseInt(splitedaccoutid[1]));
                fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                CC_display = fmsCostCenter.getSystemName();
            }

            fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(splitedaccoutid[2]));
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
            if (!"SL".equals(splitedaccoutid[0])) {
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
    //<editor-fold defaultstate="collapsed" desc="populate AccDetail">
    public void populateAccDetail(SelectEvent event) {
        System.out.println("-------------populateAccDetail event----------" + event.getObject().toString());

        int accotId = Integer.parseInt(event.getObject().toString());
        accountUseEnty.setFmsAccountUseId(accotId);
        dataTableUpdateStatus = 1;
        String subsidari;
        accountUseEnty.setSubsidaryId(getAccountUseDetailDataModel().getRowData().getSubsidaryId());
//                        fmsGeneralLedger = accountUseEnty.getSubsidaryId().getGeneralLedgerId();
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

        System.out.println("-------------Debit----------  " + accountUseEnty.getDebit());
        System.out.println("-------------Credit---------  " + accountUseEnty.getCredit());
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
        System.out.println("morat------------- " + Conc);

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    DataModel<FmsAccountUse> accountUseDetailDataModel;
    DataModel<FmsAccountUse> accountUseDetailDataModelposting;
    DataModel<FmsVoucher> voucherDataModel;
    DataModel<FmsCashPaymentOrder> journalVoucherDataModel;
    List<HrLocalMedSettlementDetail> hrmedSettlmentDetailList;
    List<HrPayrollMonTransactions> hrPayrollMonTransactionsList;
    List<FmsFieldAllowance> fieldAllowances;
    List<HrInsurancePaymentDetail> hrInsurancePaymentList;
    List<PmsProjectPaymentRequest> pmsProjectPaymentReqsList;
    List<PrmsPaymentRequisition> prmsPaymentRequisitionsList;
    int dataTableUpdateStatus = 0;
    List<FmsVoucher> VoucherList;
    private HashMap<String, BigDecimal> accountDebit = new HashMap<>();
    private HashMap<String, BigDecimal> accountcredit = new HashMap<>();
    private String accountNo;
    private BigDecimal value;
    FmsAccountUseResult accountUseResult;
    List<FmsAccountUseResult> accountUseResults = new ArrayList<>();
    private List<Map.Entry<String, BigDecimal>> entries;
    private List<Map.Entry<String, BigDecimal>> entries_;
    private List<FmsVoucher> selectedJv;
    private FmsAccountUse selectedAccountUses;
    List<String> referenceNumbers;
    boolean refRender = false;
    int updteStatus = 0;
    private String saveorUpdateBundle = Constants.getComponentBundle("Create");
    private String ActionDebitCredit = "";
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-plus";
    private BigDecimal ValueDebitCredit = new BigDecimal(0.0);
    //-------------------------------------
    int interCatListSizes = 0;
    int IntermidiateCatStatus = 0;
    int catagorynameStatus = 0;
    Boolean addButtonStatus = false;
    Boolean selectOptionStatus = false;
    Boolean editRemoveStatus = false;
    private String selCodedTransactionVal = "codedTransaction";
    private String selPayrollSummaryVal = "payrollSummary";
    private String selOtherVal = "other";
    private Boolean projOption = false;
    private Boolean projRender = false;
    private Boolean printe = false;
    private Boolean void_dis = true;
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Ref - No">
    boolean refbondlocal = false;
    boolean refbondforeign = false;
    boolean refdisable = true;
    String reftype = "nun";
    private boolean other = true;

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

    public void ref(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            reftype = event.getNewValue().toString();
            luPaymentType.setPaymentType(event.getNewValue().toString());
            if (reftype.matches("Medical")) {
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
                    refRender = true;
                    refNoList();
                    refRender = true;
                }
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

            } else if (reftype.matches("Field Allowance")) {
                referenceNumbers = new ArrayList<>();
                fieldAllowances = fieldAllowansBeanLocal.searchnotexitvocher();
                System.out.println("size " + fieldAllowances.size() + " " + fieldAllowances.get(0).getRequestNo());
                fieldAllowances.get(0).getEmpId();
                if (!fieldAllowances.isEmpty()) {
                    fieldAllowances.stream().forEach((fieldAllowanceobj) -> {
                        getReferenceNumbers().add(fieldAllowanceobj.getRequestNo());
                    });
                    refNoList();
                    refRender = true;
                } else {
                    JsfUtil.addFatalMessage("No data found");
                }
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
            } else if (reftype.matches("Insurance")) {
                referenceNumbers = new ArrayList<>();
                hrInsurancePaymentList = hrinsurancePaymentLocal.fetchNewInsurancePayments();
                if (!hrInsurancePaymentList.isEmpty()) {
                    for (HrInsurancePaymentDetail hrInsurancePaymentList1 : hrInsurancePaymentList) {

                        getReferenceNumbers().add(hrInsurancePaymentList1.getReferenceNo());
                    }
                    refNoList();
                    refRender = true;
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
                System.out.println("setRequestNo---------" + fieldAllowanceObj.getRequestNo());
                fieldAllowance = fieldAllowansBeanLocal.getByrequestno(fieldAllowanceObj);
                System.out.println("ressal----" + fieldAllowance.getTotalExpense());
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
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
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

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
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
            if ((amt == Debit) && (Debit == Credit)) {
                if (updteStatus == 0) {
                    String voucherID, preparedate;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    preparedate = dateFormat.format(fmsCashPaymentOrder.getPreparedDate());
                    voucherID = preparedate + "-" + getVoucherNO() + "-" + "CPO";
                    fmsVoucher.setVoucherId(voucherID);
                    if (fmsVoucherBeanLocal.getfmsVoucherDup(fmsVoucher)) {
                        System.err.println("Voucher No. value is duplicated. Try again reloading the page!");
                        JsfUtil.addFatalMessage("Voucher No. value is duplicated. Try again reloading the page!");
                    } else {

                        if ((!(getAccountUseDetailDataModel().getRowCount() > 0))) {
                            JsfUtil.addFatalMessage("Data table shoud be filled");
                        } else {
                            try {

                                fmsVoucher.setStatus(2);
                                fmsVoucher.setType("CPO");
                                fmsCashPaymentOrder.setStatus("1");
                                fmsCashPaymentOrder.setDailCashStatus(1);
                                fmsVoucher.addToFmsCashPaymentOrderDetail(fmsCashPaymentOrder);
                                fmsVoucherBeanLocal.create(fmsVoucher);
                                int currentNo = Integer.parseInt(getVoucherNO()) + 1;
                                vouchersNoRange.setCurrentNo(String.valueOf(currentNo));
                                noRangeBeanLocal.edit(vouchersNoRange);

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
                    System.out.println("ammt: " + amt);
                    System.out.println("credit: " + Credit);
                    System.out.println("debit: " + Debit);

                    return null;
                }
            }
            //} 
//                                    else {
//                                    JsfUtil.addFatalMessage("Error!. Debit & Credit value must be balanced!");
//                        }

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
    List<FmsVoucher> listVoucher = null;
    int MajorCatagorylistId = 0;

    public List<FmsLuPaymentType> fmsPayments() {
        FmsLuPaymentType flpt = new FmsLuPaymentType();
        flpt.setPayment("PV");
        return paymentTypeFacade.findByPaymentType(flpt);
    }

    public void OptionChecker() {
        if (projOption == true) {
            accountUseDetailDataModel = null;
            addButtonStatus = true;
            projRender = true;
        } else {
            projRender = false;
//            fmsGeneralLedger.setProjectId(null);
        }

    }

    public List<FmsVoucher> JVSearchlist(String voucheid) {
        listVoucher = null;
        fmsVoucher.setVoucherId(voucheid);

        listVoucher = fmsVoucherBeanLocal.searchVoucheidTypeJV(fmsVoucher);

        return listVoucher;
    }

    String voucherId = null;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public void searchCashPaymentByVoucherId() {
        System.out.println("vv " + voucherId);

        //find all
        if (!(voucherId.isEmpty())) {
            //find by begins with
            fmsVoucher.setVoucherId(voucherId);
            VoucherList = fmsVoucherBeanLocal.searchVoucheidTypeCPO(fmsVoucher);
            recreateJvDataModel();
        } else if (voucherId.isEmpty()) {
            System.out.println("uuuu");
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
        //Concatenate();
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
    @EJB
    FmsVouchersNoRangeBeanLocal noRangeBeanLocal;
    String voucherNO = "";

    public String getVoucherNO() {
        return voucherNO;
    }

    public void setVoucherNO(String voucherNO) {
        this.voucherNO = voucherNO;
    }

    FmsVouchersNoRange vouchersNoRange = new FmsVouchersNoRange();

    @PostConstruct
    public void getCurrentVoucherNumber() {
        String refNo = null;
        Integer seqNo = 0;
        vouchersNoRange.setStatus(1);
        FmsLuVouchersType luVouchersType = new FmsLuVouchersType();
        luVouchersType.setId(3);
        vouchersNoRange.setTypeId(luVouchersType);
        vouchersNoRange = noRangeBeanLocal.getCurrentVoucherNumber(vouchersNoRange);
        setVoucherNO(vouchersNoRange.getCurrentNo());
        voucherId = null;
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

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AccountUse,GeneralLedger,Subsidiary - Implimentation">

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
//                                    items[0] = new SelectItem("", "--Select Sytem Code--");
            return items;
        }
    }
    private String sysName;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public void SystemChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsLuSystem.setSystemCode(event.getNewValue().toString());
            fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
            sysName = fmsLuSystem.getSystemName();
            //System.out.print("System Change method" + fmsLuSystem.getSystemCode());
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }

    private String costName;

    public String getCostName() {
        return costName;
    }

    public void setCostName(String costName) {
        this.costName = costName;
    }

    public void CostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
            costName = fmsCostCenter.getDescription();
            //System.out.print("Search CostCenter Change" + fmsLuSystem.getSystemCode());
//            fmsSubsid1aryLedger1.setCostCenterId(fmsCostCenter);
        }
    }

    private String generalLName;

    public String getGeneralLName() {
        return generalLName;
    }

    public void setGeneralLName(String generalLName) {
        this.generalLName = generalLName;
    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            //System.out.print("Search General Ledger" + fmsGeneralLedger.getGeneralLedgerCode());
            generalLName = fmsGeneralLedger.getAccountTitle();
            fmsSubsid1aryLedger1.setGeneralLedgerId(fmsGeneralLedger);
            //System.out.print("Search General Ldger Change" + fmsGeneralLedger.getGeneralLedgerCode());

        }
    }

    private String subLName;

    public String getSubLName() {
        return subLName;
    }

    public void setSubLName(String subLName) {
        this.subLName = subLName;
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            //System.out.print("Search getSubsidiaryLChange 1 " + fmsSubsid1aryLedger1.getSubsidiaryCode());
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
            subLName = fmsSubsid1aryLedger1.getAccountTitle();
//            Concatenate();
            //System.out.print("Search sub ID 2 " + fmsSubsid1aryLedger1.getSubsidiaryId());
        }
    }
    boolean cc = true;
    boolean sl = true;

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
                    //System.out.print(" id... " + subsidaryList.get(i).getSubsidiaryId() + " sCode.. " + subsidaryList.get(i).getSubsidiaryCode());
                    listSl[i + 1] = new SelectItem(subsidaryList.get(i).getSubsidiaryId(), subsidaryList.get(i).getSubsidiaryCode());
                }
            } else {
                sl = true;

            }

            //return JsfUtil.getSelectItems(subLedgerBeanLocal.findSubLedger(fmsCostCenter, fmsGeneralLedger), true);
            return listSl;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--Select CC & GL--");
            return items;
        }
    }

    Set<BigInteger> checkDebitCredit = new HashSet<>();
    Set<String> checkSubsidiaryCode = new HashSet<>();
    List<HashMap> list = new ArrayList<HashMap>() {
    };
    double Credit = 0.0;
    double Debit = 0.0;

    public String addCashPaymentOrderSubsidiaryLedgerDetail() {
//                        if (fmsGeneralLedger != null&&fmsLuSystem!=null) {
        accountUseEnty = new FmsAccountUse();
        Concatenate();

        accountUseEnty.setSubsidaryId(Conc);
        accountUseEnty.setDisplay_conn(display_conn);
        System.out.println("ssssssssssssssssssssssss-----------------" + accountUseEnty.getSubsidaryId());

//                        }
        if (ActionDebitCredit.equals("Debit")) {
            accountUseEnty.setDebit(ValueDebitCredit);
            System.out.println("debitttttttttttttttttttttttt" + ValueDebitCredit);
            accountUseEnty.setCredit(new BigDecimal(0.00));
        }
        if (ActionDebitCredit.equals("Credit")) {
            accountUseEnty.setDebit(new BigDecimal(0.00));
            accountUseEnty.setCredit(ValueDebitCredit);
            System.out.println("Creditttttttttttttttttttttttt" + ValueDebitCredit);

        }
        //check duplicate Debit Credit with same SL code

        if (checkSubsidiaryCode.contains(accountUseEnty.getSubsidaryId())) {
            System.out.println("---check 1st if---");
            System.out.println("---check 2nd if 1---" + getActionDebitCredit() + "---Value---" + accountUseEnty.getDebit());
            if (checkDebitCredit.contains(accountUseEnty.getDebit()) && ActionDebitCredit.equals("Debit")) {
//                                                System.out.println("---check 2nd if---" + getActionDebitCredit() + accountUseEnty.getSubsidaryId().getDebit());
                JsfUtil.addErrorMessage("Duplicate Debit is not allowed with duplicate Subsidiary Code!");
//                                                System.out.println("=====getDebit(value)====" + accountUseEnty.getSubsidaryId().getDebit());
                return null;
            } else if (checkDebitCredit.contains(accountUseEnty.getCredit()) && ActionDebitCredit.equals("Credit")) {
                System.out.println("---check 2nd 2nd if---" + getActionDebitCredit());
                JsfUtil.addErrorMessage("Duplicate Credit is not allowed with duplicate Subsidiary Code!");
//                                                System.out.println("getCredit(value)====" + accountUseEntygetCredit());
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
//        fmsLuSystem = accountUseEnty.getSubsidaryId().getCostCenterId().getSystemId();
//        fmsCostCenter = accountUseEnty.getSubsidaryId().getCostCenterId();
//                        fmsGeneralLedger = accountUseEnty.getSubsidaryId().getGeneralLedgerId();
//                        fmsSubsid1aryLedger1 = accountUseEnty.getSubsidaryId();
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

    List<FmsGeneralLedger> listGeneralLedger = null;
    int GLListSize = 0;

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
    //conc
    private String Conc = "";
    private String display_conn;

    public void remove() {
        accountUseEnty = null;
        accountUseEnty = getAccountUseDetailDataModel().getRowData();
        fmsVoucher.getFmsAccountUseList().remove(accountUseEnty);
        recreateModelDetail();
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="posting - Implimentation"> 
    private List<FmsVoucher> selectedVouches;

    /**
     *
     * @return
     */
    public List<FmsVoucher> getSelectedVouches() {

        return selectedVouches;
    }

    /**
     *
     * @param selectedVouches
     */
    public void setSelectedVouches(List<FmsVoucher> selectedVouches) {
        this.selectedVouches = selectedVouches;
    }

    String rederedCheckBox = "true";

    /**
     *
     * @return
     */
    public String getRederedCheckBox() {
        return rederedCheckBox;
    }

    /**
     *
     * @param rederedCheckBox
     */
    public void setRederedCheckBox(String rederedCheckBox) {
        this.rederedCheckBox = rederedCheckBox;
    }

    public Boolean getVoid_dis() {
        return void_dis;
    }

    public void setVoid_dis(Boolean void_dis) {
        this.void_dis = void_dis;
    }

    List<FmsVoucher> vouchersList = new ArrayList<>();

    /**
     *
     * @param events
     */
    public void check(SelectEvent events) {
        System.out.println("check");
        fmsVoucher = new FmsVoucher();
        fmsVoucher = (FmsVoucher) events.getObject();
        listVoucher.add(fmsVoucher);
        System.out.println("checked" + listVoucher.size());
        void_dis = false;
    }

    public void allcheck(SelectEvent events) {
        System.out.println("check");
        fmsVoucher = new FmsVoucher();
        listVoucher = (List<FmsVoucher>) events.getObject();
        System.out.println("checked" + listVoucher.size());
        void_dis = false;
        updteStatus = 3;

    }

    /**
     *
     * @param uncheckevent
     */
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
        System.out.println("uncheck");
        listVoucher.clear();
        void_dis = true;
    }
    private String PRStatus;
    private DataModel<FmsVoucher> vouchersDetailDataModel;

    /**
     *
     * @return
     */
    public DataModel<FmsVoucher> getVouchersDetailDataModel() {
        if (vouchersDetailDataModel == null) {
            vouchersDetailDataModel = new ListDataModel<>();
        }
        return vouchersDetailDataModel;
    }

    /**
     *
     * @param vouchersDetailDataModel
     */
    public void setVouchersDetailDataModel(DataModel<FmsVoucher> vouchersDetailDataModel) {
        this.vouchersDetailDataModel = vouchersDetailDataModel;
    }
//                        DataModel<FmsAccountUse> accountUseDetailDataModelrow;

    /**
     *
     * @return
     */
    public List<FmsVoucher> getVouchersList() {

        return vouchersList;
    }

    /**
     *
     * @param vouchersList
     */
    public void setVouchersList(List<FmsVoucher> vouchersList) {
        this.vouchersList = vouchersList;
    }

    /**
     *
     * @return
     */
    public int getDataTableUpdateStatus() {
        return dataTableUpdateStatus;
    }

    /**
     *
     * @param dataTableUpdateStatus
     */
    public void setDataTableUpdateStatus(int dataTableUpdateStatus) {
        this.dataTableUpdateStatus = dataTableUpdateStatus;
    }

    /**
     *
     * @return
     */
    public String getPRStatus() {
        return PRStatus;
    }

    /**
     *
     * @param PRStatus
     */
    public void setPRStatus(String PRStatus) {
        this.PRStatus = PRStatus;
    }

    /**
     *
     */
    public void recreateVoucherModelDetail() {
        vouchersDetailDataModel = null;
        vouchersDetailDataModel = new ListDataModel(vouchersList);
    }

    /**
     *
     */
    public void recreateAccountModelDetail() {
        System.err.println("fgggggggggggggggggggggggggggggggggggggggggggggggg");
        accountUseDetailDataModel = null;
        accountUseDetailDataModel = new ListDataModel(getVouchersDetailDataModel().getRowData().getFmsAccountUseList());
        System.out.println(getVouchersDetailDataModel().getRowData().getStatus());

    }

    /**
     *
     * @return
     */
    public String GeneratePostingCpo() {
        vouchersList = fmsVoucherBeanLocal.handleSearchPost();
//        for (int j = 0; j < vouchersList.size(); j++) {
//
//            for (int i = 0; i < vouchersList.get(j).getFmsAccountUseList().size(); i++) {
//                String splitedaccoutid[] = vouchersList.get(j).getFmsAccountUseList().get(i).getSubsidaryId().split("-");
//                fmsLuSystem.setSystemId(Integer.parseInt(splitedaccoutid[0]));
//                fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
//                System.out.println("system id " + i + " ->" + fmsLuSystem.getSystemCode());
//                String CC_display = "CC";
//                String SL_display = "SL";
//                if (!"CC".equals(splitedaccoutid[1])) {
//                    fmsCostCenter.setCostCenterId(Integer.parseInt(splitedaccoutid[1]));
//                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
//                    CC_display = fmsCostCenter.getSystemName();
//                }
//
//                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(splitedaccoutid[2]));
//                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
//                if (!"SL".equals(splitedaccoutid[2])) {
//                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(splitedaccoutid[3]));
//                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
//                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
//                }
//                display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
//                vouchersList.get(j).getFmsAccountUseList().get(i).setDisplay_conn(display_conn);
//
//            }
//        }
        recreateVoucherModelDetail();
        updteStatus = 3;
        listVoucher = null;
        listVoucher = new ArrayList<>();
        return null;
    }
    @Inject
    WfFcmsProcessed wfFcmsProcessed;

    @EJB
    WfFcmsProcessedBeanLocal wfFcmsProcessedBeanLocal;

    public WfFcmsProcessed getWfFcmsProcessed() {
        if (wfFcmsProcessed == null) {
            wfFcmsProcessed = new WfFcmsProcessed();
        }
        return wfFcmsProcessed;
    }

    public void setWfFcmsProcessed(WfFcmsProcessed wfFcmsProcessed) {
        this.wfFcmsProcessed = wfFcmsProcessed;
    }

    public void post_cashPaymentOrderVoucher_d() {
        System.out.println("hello");
        AAA securitySrvice = new AAA();
        IAdministration security = securitySrvice.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        System.out.println("hello1");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "post_cashPaymentOrderVoucher_d", dataset)) {

//                 put ur code here...!
            System.out.println("size______" + selectedVouches.size());
            if (selectedVouches.size() > 0) {
                try {
                    selectedVouches.stream().map((selectedVouche) -> {
                        FmsVoucher fmsVoucher = new FmsVoucher();
                        fmsVoucher = selectedVouche;
                        return fmsVoucher;
                    }).map((fmsVoucher) -> {
                        fmsVoucher.setStatus(3);
                        return fmsVoucher;
                    }).forEach((fmsVoucher) -> {
                        //                    fmsVoucherBeanLocal.edit(selectedVouches.get(i));
                        wfFcmsProcessed.setProcessedBy(BigInteger.valueOf(sessionBeanLocal.getUserId()));
                        wfFcmsProcessed.setDecision(securityBean.Constants.VOUCHER_POST);
                        System.out.println("decision==" + wfFcmsProcessed.getDecision());
                        wfFcmsProcessed.setCommentGiven(fmsVoucher.getPreparedRemark());
                        wfFcmsProcessed.setProcessedOn(String.valueOf(fmsVoucher.getProcessedDate()));
                        fmsVoucher.setProcessedBy(wfFcmsProcessed.getProcessedOn());
                        wfFcmsProcessed.setFmsVoucherId(fmsVoucher);
                        fmsVoucherBeanLocal.edit(fmsVoucher);
                        wfFcmsProcessedBeanLocal.saveUpdate(wfFcmsProcessed);

                    });
                    JsfUtil.addSuccessMessage("Cash Payment   Voucher Data is Post Done");
                    clearPage();

                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Something Occured on Data post");
                    // return null;
                }

            } else {
                JsfUtil.addFatalMessage("Select one to Post");
                //return null;
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
    }

    public void void_cashPayOrderVoucher_data() {
        AAA securityService = new AAA();
        IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
        if (security.checkAccess(sessionBeanLocal.getUserName(), "void_cashPayOrderVoucher_data", dataset)) {
//                 put ur code here...!
            if (selectedVouches.size() > 0) {
                try {

                    selectedVouches.stream().map((selectedVouche) -> {
                        FmsVoucher fmsVoucherNEW = new FmsVoucher();
                        fmsVoucherNEW = selectedVouche;
                        return fmsVoucherNEW;
                    }).map((fmsVoucherNEW) -> {
                        fmsVoucherNEW.setStatus(38);
                        fmsVoucherNEW.setReason(fmsVoucher.getReason());
                        return fmsVoucherNEW;
                    }).forEach((fmsVoucherNEW) -> {
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
                    JsfUtil.addSuccessMessage("Cash Payment   Voucher Data is Void Done");
                    clearPage();

                } catch (Exception e) {
                    JsfUtil.addFatalMessage("Something Occured on Data Void");
                    // return null;
                }

            } else {
                JsfUtil.addFatalMessage("Select one to Void");
                //return null;
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
    }
    //</editor-fold> 
}
