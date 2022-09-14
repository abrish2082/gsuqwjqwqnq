/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.BeginningBalanceBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.CashReceiptVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.FmsAccountUseBeanLocal;
import et.gov.eep.fcms.businessLogic.Voucher.FmsVoucherBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsAccountingPeriodBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsCostCenterBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsGeneralLedgerBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.FmsLuSystemBeanLocal;
import et.gov.eep.fcms.businessLogic.admin.subsidiaryBeanLocal;
import et.gov.eep.fcms.businessLogic.budget.FmsOperatingBudgetDetailBeanLocal;
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsBeginningBalance;
import et.gov.eep.fcms.entity.FmsBudgetcomparison;
import et.gov.eep.fcms.entity.FmsTrialBalanceDetail;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author mora1
 */
@Named(value = "budgetcomparisonController")
@ViewScoped
public class BudgetcomparisonController implements Serializable {

    /**
     * Creates a new instance of BudgetcomparisonController
     */
    public BudgetcomparisonController() {
    }
    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    @EJB
    CashReceiptVoucherBeanLocal fmsJournalBeanLocal;

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
    BeginningBalanceBeanLocal beginningBalanceBeanLocal;
    @EJB
    FmsOperatingBudgetDetailBeanLocal budgetDetailBeanLocal;

    @Inject
    FmsOperatingBudgetDetail budgetDetail;

    @Inject
    FmsBeginningBalance beginningBalance;

    @Inject
    FmsTrialBalanceDetail balanceDetail;

    @Inject
    FmsGeneralLedger fmsGeneralLedger;

    @Inject
    FmsSubsidiaryLedger fmsSubsid1aryLedger1;

    @Inject
    FmsLuSystem fmsLuSystem;

    @Inject
    FmsCostCenter fmsCostCenter;

    @EJB
    private subsidiaryBeanLocal subsidiaryLedgerBeanLocal;
    @EJB
    private FmsAccountingPeriodBeanLocal fmsAccountingPeriodBeanLocal;
    @Inject
    FmsAccountingPeriod fmsAccountingPeriodEnty;

    int updteStatus = 0;

    private String saveorUpdateBundle = Constants.getComponentBundle("Create");
    private String ActionDebitCredit = "";
    private boolean disableBtnCreate;
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateJv = false;
    private boolean renderPnlManPage = true;
    private String icone = "ui-icon-document";
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
    private String start_dates;
    private String end_dates;
    private Boolean projOption = false;
    private Boolean projRender = false;
    private Boolean printe = false;
    private Boolean void_dis = true;
    private Date start_date;
    private Date end_date;
    private List<FmsAccountingPeriod> fmsAccountingPeriodsl;
    private List<FmsTrialBalanceDetail> trialBalanceDetails;
    DataModel<FmsTrialBalanceDetail> TrialBalanceDataModel = new ArrayDataModel<>();

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    @Inject
    FmsAccountUse accountUse;

    @EJB
    FmsAccountUseBeanLocal accountUseBeanLocal;
    private Double DebitCredit = 0.0;
    private Double Credit = 0.0;
    private Double Debit = 0.0;
    List<FmsBudgetcomparison> ledgerCardResults = new ArrayList<>();
    List<FmsBudgetcomparison> trialbalans = new ArrayList<>();
    DataModel<FmsBudgetcomparison> ledgerCardDataModel = new ArrayDataModel<>();
    FmsBudgetcomparison ledgerCardResult;
    private FmsBudgetcomparison selectfmsLedgerCardResult;

    public FmsBudgetcomparison getSelectfmsLedgerCardResult() {
        return selectfmsLedgerCardResult;
    }

    public void setSelectfmsLedgerCardResult(FmsBudgetcomparison selectfmsLedgerCardResult) {
        this.selectfmsLedgerCardResult = selectfmsLedgerCardResult;
    }

    public FmsAccountUse getAccountUse() {
        if (accountUse == null) {
            accountUse = new FmsAccountUse();
        }
        return accountUse;
    }

    public void setAccountUse(FmsAccountUse accountUse) {
        this.accountUse = accountUse;
    }

    public FmsAccountUseBeanLocal getAccountUseBeanLocal() {
        return accountUseBeanLocal;
    }

    public void setAccountUseBeanLocal(FmsAccountUseBeanLocal accountUseBeanLocal) {
        this.accountUseBeanLocal = accountUseBeanLocal;
    }

    public FmsBudgetcomparison getLedgerCardResult() {
        if (ledgerCardResult == null) {
            ledgerCardResult = new FmsBudgetcomparison();
        }
        return ledgerCardResult;
    }

    public void setLedgerCardResult(FmsBudgetcomparison ledgerCardResult) {
        this.ledgerCardResult = ledgerCardResult;
    }

    public List<FmsBudgetcomparison> getLedgerCardResults() {
        if (ledgerCardResults == null) {
            ledgerCardResults = new ArrayList<>();
        }
        return ledgerCardResults;
    }

    public void setLedgerCardResults(List<FmsBudgetcomparison> ledgerCardResults) {
        this.ledgerCardResults = ledgerCardResults;
    }

    public DataModel<FmsBudgetcomparison> getLedgerCardDataModel() {
        if (ledgerCardDataModel == null) {
            ledgerCardDataModel = new ListDataModel<>();
        }
        return ledgerCardDataModel;
    }

    public void setLedgerCardDataModel(DataModel<FmsBudgetcomparison> ledgerCardDataModel) {
        this.ledgerCardDataModel = ledgerCardDataModel;
    }

    public void report() {

    }

    public void recreateModelDetail() {
        ledgerCardDataModel = null;
        ledgerCardDataModel = new ListDataModel(new ArrayList(ledgerCardResults));
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsTrialBalanceDetail getBalanceDetail() {
        if (balanceDetail == null) {
            balanceDetail = new FmsTrialBalanceDetail();
        }
        return balanceDetail;
    }

    public void setBalanceDetail(FmsTrialBalanceDetail balanceDetail) {
        this.balanceDetail = balanceDetail;
    }

    public List<FmsTrialBalanceDetail> getTrialBalanceDetails() {
        return trialBalanceDetails;
    }

    public void setTrialBalanceDetails(List<FmsTrialBalanceDetail> trialBalanceDetails) {
        this.trialBalanceDetails = trialBalanceDetails;
    }

    public DataModel<FmsTrialBalanceDetail> getTrialBalanceDataModel() {
        if (TrialBalanceDataModel == null) {
            TrialBalanceDataModel = new ArrayDataModel<>();
        }
        return TrialBalanceDataModel;
    }

    public void setTrialBalanceDataModel(DataModel<FmsTrialBalanceDetail> TrialBalanceDataModel) {
        this.TrialBalanceDataModel = TrialBalanceDataModel;
    }

    public FmsAccountingPeriod getFmsAccountingPeriodEnty() {
        if (fmsAccountingPeriodEnty == null) {
            fmsAccountingPeriodEnty = new FmsAccountingPeriod();
        }
        return fmsAccountingPeriodEnty;
    }

    public void setFmsAccountingPeriodEnty(FmsAccountingPeriod fmsAccountingPeriodEnty) {
        this.fmsAccountingPeriodEnty = fmsAccountingPeriodEnty;
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

    public Boolean getPrinte() {
        return printe;
    }

    public void setPrinte(Boolean printe) {
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

    public FmsVoucherBeanLocal getFmsVoucherBeanLocal() {
        return fmsVoucherBeanLocal;
    }

    public void setFmsVoucherBeanLocal(FmsVoucherBeanLocal fmsVoucherBeanLocal) {
        this.fmsVoucherBeanLocal = fmsVoucherBeanLocal;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    //</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="AccountUse,GeneralLedger,Subsidiary - Implimentation">
    public SelectItem[] getGLdata() {
        return JsfUtil.getSelectItems(fmsGeneralLedgerBeanLocal.findAll(), true);
    }

    private List<FmsLuSystem> listOfSystems;
    private List<FmsGeneralLedger> listofgeneralLedgers;

    public List<FmsLuSystem> getListOfSystems() {
        listOfSystems = fmsLuSystemBeanLocal.findAll();
        return listOfSystems;
    }

    public void setListOfSystems(List<FmsLuSystem> listOfSystems) {
        this.listOfSystems = listOfSystems;
    }

    public List<FmsGeneralLedger> getListofgeneralLedgers() {
        listofgeneralLedgers = fmsGeneralLedgerBeanLocal.findAll();
        return listofgeneralLedgers;
    }

    public void setListofgeneralLedgers(List<FmsGeneralLedger> listofgeneralLedgers) {
        this.listofgeneralLedgers = listofgeneralLedgers;
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
            fmsCostCenter.setSystemId(fmsLuSystem);
        }
    }

    public void CostCenterChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsCostCenter.setSystemName(event.getNewValue().toString());
            fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);

        }
    }

    public void getGeneralLedgerChange(ValueChangeEvent event) {

        if (!event.getNewValue().toString().isEmpty()) {
            fmsGeneralLedger.setGeneralLedgerCode(event.getNewValue().toString());
            fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
            fmsSubsid1aryLedger1.setGeneralLedgerId(fmsGeneralLedger);

        }
    }

    public void getSubsidiaryLChange(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(event.getNewValue().toString()));
            fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);

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

    List<FmsGeneralLedger> listGeneralLedger = null;
    int GLListSize = 0;

    //<editor-fold defaultstate="collapsed" desc=" calc and check Debit Credit balance - Implimentation"> 
    public BigDecimal toBigDecimal(double val) {
        BigDecimal newVal = new BigDecimal(val);
        return newVal;
    }

    // </editor-fold> 
    private String Conc = "";
    private String display_conn;

    public SelectItem[] getListPeriods() {
        return JsfUtil.getSelectItems(fmsAccountingPeriodBeanLocal.findAll(), true);
    }

//</editor-fold>
    public void Concatenate() {
        String CC = "CC";
        String SL = "SL";
        String CC_display = "CC";
        String SL_display = "SL";

        if (fmsCostCenter.getCostCenterId() != null) {
            CC = fmsCostCenter.getCostCenterId().toString();
            CC_display = fmsCostCenter.getCostCenterId().toString();

        }
        if (fmsSubsid1aryLedger1.getSubsidiaryCode() != null) {
            SL = fmsSubsid1aryLedger1.getSubsidiaryId().toString();
            SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();

        }

        display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerCode() + "-" + SL_display;
        Conc = fmsLuSystem.getSystemId() + "-" + CC + "-" + fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL;
        System.out.println("morat------------- " + Conc);

    }

    public void gettrialBalance_GL() {
        String Sys = null, G_L = null, S_L = null;
        getListOfSystems();
        int x = 0, y = 0;
        trialbalans = new ArrayList<>();

        Sys = fmsLuSystem.getSystemId().toString();
        getListofgeneralLedgers();
        for (FmsGeneralLedger generalLedger : listofgeneralLedgers) {
            G_L = generalLedger.getGeneralLedgerId().toString();
            Conc = G_L;
            ledgerCardDataModel = new ArrayDataModel<>();
            HashMap hashmap = new HashMap();
            hashmap.put("MONTH_YEAR", "2017");
            hashmap.put("SUBSID", Conc);
            hashmap.put("SYS", Sys);
            List<FmsAccountUse> accountUses = new ArrayList<>();
            accountUses = accountUseBeanLocal.gettrilSL(hashmap);
            String subsder = "";

            if (accountUses != null) {
                System.out.println("size ==-----------" + Sys + "-----------------=== " + accountUses.size());

                for (FmsAccountUse accountUse : accountUses) {
                    DebitCredit += accountUse.getCredit().doubleValue();
                    DebitCredit -= accountUse.getDebit().doubleValue();
                    Credit += accountUse.getCredit().doubleValue();
                    Debit += accountUse.getDebit().doubleValue();

                    subsder = accountUse.getSubsidaryId();
                    accountUse = null;
                }
            }
            FmsBudgetcomparison lagerdcard = new FmsBudgetcomparison();
            if (DebitCredit > 0) {
                lagerdcard.setCREDIT(DebitCredit);
                lagerdcard.setDEBIT(0);
            } else if (DebitCredit < 0) {
                lagerdcard.setCREDIT(0);
                lagerdcard.setDEBIT(DebitCredit);
            } else if (DebitCredit == 0) {

                lagerdcard.setCREDIT(0);
                lagerdcard.setDEBIT(0);
            }
            lagerdcard.setBalance(DebitCredit);
            lagerdcard.setReference(subsder);
            int Sys_Id = 0;
            int Gl_Id = 0;
            if (!"".equals(lagerdcard.getReference())) {
                String splitedaccoutid[] = lagerdcard.getReference().split("-");
                fmsLuSystem = new FmsLuSystem();
                fmsLuSystem.setSystemId(Integer.parseInt(splitedaccoutid[0]));
                fmsLuSystem = fmsLuSystemBeanLocal.getSystembyId(fmsLuSystem);
                Sys_Id = fmsLuSystem.getSystemId();
                String CC_display = "CC";
                String SL_display = "SL";
                if (!"CC".equals(splitedaccoutid[1])) {
                    fmsCostCenter = new FmsCostCenter();
                    fmsCostCenter.setCostCenterId(Integer.parseInt(splitedaccoutid[1]));
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostCenterId(fmsCostCenter);
                    CC_display = fmsCostCenter.getSystemName();
                }
                fmsGeneralLedger = new FmsGeneralLedger();
                fmsGeneralLedger.setGeneralLedgerId(Integer.parseInt(splitedaccoutid[2]));
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getByGlId(fmsGeneralLedger);
                Gl_Id = fmsGeneralLedger.getGeneralLedgerId();
                if (!"SL".equals(splitedaccoutid[2])) {
                    fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(splitedaccoutid[3]));
                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
                }
                display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                lagerdcard.setDisplay_conn(display_conn);
            }
            if (!subsder.matches("")) {
                HashMap hashcompt = new HashMap();
                hashcompt.put("GL", Gl_Id);
                hashcompt.put("BGYEAR", 3);
                hashcompt.put("SYS", Sys_Id);
                budgetDetail = new FmsOperatingBudgetDetail();
                budgetDetail = budgetDetailBeanLocal.budgetcomparison(hashcompt);
                double balancetr=lagerdcard.getCREDIT()+lagerdcard.getDEBIT();
                lagerdcard.setBalance(balancetr);
                if (budgetDetail != null) {
                    double ApprovedAmount=budgetDetail.getApprovedAmount().doubleValue();
                    System.out.println("ApprovedAmount"+ApprovedAmount);
                    double RemainingBalance=budgetDetail.getRemainingBalance().doubleValue();
                                        System.out.println("RemainingBalance"+RemainingBalance);
                    double Utilization=ApprovedAmount-RemainingBalance;
                                                            System.out.println("Utilization"+Utilization);

                    double Performance=(balancetr/ApprovedAmount)*100;
                    lagerdcard.setUtilization(Utilization);
                    
                    lagerdcard.setApprovedAmount(budgetDetail.getApprovedAmount().doubleValue());
                    lagerdcard.setRemainingBalance(budgetDetail.getRemainingBalance().doubleValue());
                    lagerdcard.setPerformance(Performance);

                } else {
                    lagerdcard.setRemainingBalance(0.0);
                    lagerdcard.setUtilization(0.0);
                    lagerdcard.setPerformance(0.0);
                    lagerdcard.setApprovedAmount(0.0);

                }
                trialbalans.add(lagerdcard);
                restet();
                System.out.println("size --- " + x++);
            }
            DebitCredit = 0.0;
            lagerdcard = null;

        }
        System.out.println("trialbalans " + trialbalans.size());
        ledgerCardDataModel = null;
        ledgerCardDataModel = new ListDataModel(new ArrayList(trialbalans));
    }

    void restet() {
        DebitCredit = 0.0;
        Credit = 0.0;
        Debit = 0.0;
    }
}
