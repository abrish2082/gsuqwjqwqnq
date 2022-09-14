/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller;

import et.gov.eep.commonApplications.localbean;
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
import et.gov.eep.fcms.entity.FmsAccountUse;
import et.gov.eep.fcms.entity.FmsBeginningBalance;
import et.gov.eep.fcms.entity.FmsLedgerCardResult;
import et.gov.eep.fcms.entity.FmsTrialBalanceDetail;
import et.gov.eep.fcms.entity.admin.FmsAccountingPeriod;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.mapper.FmsTrialBalanceDetailFacade;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.PropertyNotFoundException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author mora
 */
@Named(value = "ledgerCardViewController")
@ViewScoped
public class LedgerCardViewController implements Serializable {

    /**
     * Creates a new instance of LedgerCardViewController
     */
    public LedgerCardViewController() {

       

    }
    boolean baya = true;

    public boolean isBaya() {
        return baya;
    }

    public void setBaya(boolean baya) {
        this.baya = baya;
    }
    
    @PostConstruct
    
    public void init() {
         if (Languagelocalbean.getLangsession().getAttribute("lang") != null) {
            if (Languagelocalbean.getLangsession().getAttribute("lang").toString().equalsIgnoreCase("am")) {
                Languagelocalbean.changeLanguage("ET");
            } else {
                Languagelocalbean.changeLanguage(Languagelocalbean.getLangsession().getAttribute("lang").toString());
            }
        }
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

    @Inject
    localbean Languagelocalbean;

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
    boolean printerRender = false;

    public boolean isPrinterRenderTrialBalance() {
        return printerRender;
    }

    public void setPrinterRenderTrialBalance(boolean printerRender) {
        this.printerRender = printerRender;
    }

    //</editor-fold>
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
//    // <editor-fold defaultstate="collapsed" desc="Search">
//    List<FmsVoucher> listVoucher = null;
//    int MajorCatagorylistId = 0;
//
//    public void OptionChecker() {
//        if (projOption == true) {
//            addButtonStatus = true;
//            projRender = true;
//        } else {
//            projRender = false;
//            fmsGeneralLedger.setProjectId(null);
//        }
//
//    }
//    // </editor-fold>         
    int dataTableUpdateStatus = 0;

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
//    //<editor-fold defaultstate="collapsed" desc="posting - Implimentation"> 
//    private FmsVoucher selectedVouches[];
//
//    public FmsVoucher[] getSelectedVouches() {
//
//        return selectedVouches;
//    }
//
//    public void setSelectedVouches(FmsVoucher[] selectedVouches) {
//        this.selectedVouches = selectedVouches;
//    }
//
//    String rederedCheckBox = "true";
//
//    public String getRederedCheckBox() {
//        return rederedCheckBox;
//    }
//
//    public void setRederedCheckBox(String rederedCheckBox) {
//        this.rederedCheckBox = rederedCheckBox;
//    }
//
//    private String PRStatus;
//    private DataModel<FmsVoucher> vouchersDetailDataModel;
//
//    public DataModel<FmsVoucher> getVouchersDetailDataModel() {
//        if (vouchersDetailDataModel == null) {
//            vouchersDetailDataModel = new ListDataModel<>();
//        }
//        return vouchersDetailDataModel;
//    }
//
//    public void setVouchersDetailDataModel(DataModel<FmsVoucher> vouchersDetailDataModel) {
//        this.vouchersDetailDataModel = vouchersDetailDataModel;
//    }
//
//    public int getDataTableUpdateStatus() {
//        return dataTableUpdateStatus;
//    }
//
//    public void setDataTableUpdateStatus(int dataTableUpdateStatus) {
//        this.dataTableUpdateStatus = dataTableUpdateStatus;
//    }
//
//    public String getPRStatus() {
//        return PRStatus;
//    }
//
//    public void setPRStatus(String PRStatus) {
//        this.PRStatus = PRStatus;
//    }
//
//    //</editor-fold>                     

    public List<FmsAccountingPeriod> getActiveYear() {

        System.out.println("gebahoooo--------------");

        try {
            List<FmsAccountingPeriod> accountingPeriods = fmsAccountingPeriodBeanLocal.findAll();
            return accountingPeriods;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public void bigningbalance() {
        FmsBeginningBalance beginningBalance_result = new FmsBeginningBalance();
        beginningBalance_result = beginningBalanceBeanLocal.Acount_Period(beginningBalance);
        System.out.println("beginningBalance_result =  " + beginningBalance_result.getAmount());
    }

    //<editor-fold defaultstate="collapsed" desc="Object and variable declaration">
    @Inject
    FmsAccountUse accountUse;

    @EJB
    FmsAccountUseBeanLocal accountUseBeanLocal;
    private Double DebitCredit = 0.0;
    private Double Credit = 0.0;
    private Double Debit = 0.0;
    List<FmsLedgerCardResult> ledgerCardResults = new ArrayList<>();
    List<FmsLedgerCardResult> trialbalans = new ArrayList<>();
    DataModel<FmsLedgerCardResult> ledgerCardDataModel = new ArrayDataModel<>();
    FmsLedgerCardResult ledgerCardResult;
    private FmsLedgerCardResult selectfmsLedgerCardResult;

    public FmsLedgerCardResult getSelectfmsLedgerCardResult() {
        return selectfmsLedgerCardResult;
    }

    public void setSelectfmsLedgerCardResult(FmsLedgerCardResult selectfmsLedgerCardResult) {
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

    public FmsLedgerCardResult getLedgerCardResult() {
        if (ledgerCardResult == null) {
            ledgerCardResult = new FmsLedgerCardResult();
        }
        return ledgerCardResult;
    }

    public void setLedgerCardResult(FmsLedgerCardResult ledgerCardResult) {
        this.ledgerCardResult = ledgerCardResult;
    }

    public List<FmsLedgerCardResult> getLedgerCardResults() {
        if (ledgerCardResults == null) {
            ledgerCardResults = new ArrayList<>();
        }
        return ledgerCardResults;
    }

    public void setLedgerCardResults(List<FmsLedgerCardResult> ledgerCardResults) {
        this.ledgerCardResults = ledgerCardResults;
    }

    public DataModel<FmsLedgerCardResult> getLedgerCardDataModel() {
        if (ledgerCardDataModel == null) {
            ledgerCardDataModel = new ListDataModel<>();
        }
        return ledgerCardDataModel;
    }

    public void setLedgerCardDataModel(DataModel<FmsLedgerCardResult> ledgerCardDataModel) {
        this.ledgerCardDataModel = ledgerCardDataModel;
    }

    public void report() {

    }

    public void recreateModelDetail() {
        ledgerCardDataModel = null;
        ledgerCardDataModel = new ListDataModel(new ArrayList(ledgerCardResults));
    }

    // </editor-fold>
    public void getreport() {
        //           AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");          
//            if (security.checkAccess(SessionBean.getUserName(), "saveAppealRequest", dataset)) {
//                 put ur code here...! 
        restet();
        String SL, Sys = null;
        ledgerCardResults = null;
        ledgerCardDataModel = null;
        start_dates = start_date.toString();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/mm/dd");
        start_dates = dt.format(start_date).toString();
        System.out.println("start_dates " + start_dates);
        if (fmsLuSystem.getSystemCode() == null && fmsSubsid1aryLedger1.getSubsidiaryCode() != null) {
            SL = fmsSubsid1aryLedger1.getSubsidiaryId().toString();
            Conc = fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL;
            ledgerCardDataModel = new ArrayDataModel<>();
            HashMap hashmap = new HashMap();

            hashmap.put("MONTH_YEAR", "2020");
            hashmap.put("SUBSID", Conc);
            ledgerCardResults = new ArrayList<>();
            List<FmsAccountUse> accountUses = new ArrayList<>();

            accountUses = accountUseBeanLocal.getlagerd_Sys(hashmap);
            int size = accountUses.size();
            for (int i = 0; i < size; i++) {
                FmsLedgerCardResult lagerd = new FmsLedgerCardResult();
                accountUse = new FmsAccountUse();
                accountUse = accountUses.get(i);
                DebitCredit += accountUses.get(i).getCredit().doubleValue();
                DebitCredit -= accountUses.get(i).getDebit().doubleValue();
                Credit += accountUses.get(i).getCredit().doubleValue();
                Debit += accountUses.get(i).getDebit().doubleValue();
                lagerd.setCREDIT(accountUses.get(i).getCredit().doubleValue());
                lagerd.setDEBIT(accountUses.get(i).getDebit().doubleValue());
                lagerd.setBalance(DebitCredit);
                lagerd.setTotalCredit(Credit);
                lagerd.setTotalDebit(Debit);
                lagerd.setReference(accountUses.get(i).getSubsidaryId());
                ledgerCardResults.add(lagerd);
                lagerd = null;
                accountUse = null;
            }

        } else if (fmsLuSystem.getSystemCode() != null && fmsSubsid1aryLedger1.getSubsidiaryCode() != null) {
            SL = fmsSubsid1aryLedger1.getSubsidiaryId().toString();
            Conc = fmsSubsid1aryLedger1.getGeneralLedgerId().getGeneralLedgerId() + "-" + SL;
            Sys = fmsLuSystem.getSystemId().toString();
            ledgerCardDataModel = new ArrayDataModel<>();
            HashMap hashmap = new HashMap();
            hashmap.put("MONTH_YEAR", "2020");
            hashmap.put("SUBSID", Conc);
            hashmap.put("SYS", Sys);
            ledgerCardResults = new ArrayList<>();
            List<FmsAccountUse> accountUses = new ArrayList<>();

            accountUses = accountUseBeanLocal.getlagerd(hashmap);
            int size = accountUses.size();
            for (int i = 0; i < size; i++) {
                FmsLedgerCardResult lagerd = new FmsLedgerCardResult();
                accountUse = new FmsAccountUse();
                accountUse = accountUses.get(i);
                DebitCredit += accountUses.get(i).getCredit().doubleValue();
                DebitCredit -= accountUses.get(i).getDebit().doubleValue();
                Credit += accountUses.get(i).getCredit().doubleValue();
                Debit += accountUses.get(i).getDebit().doubleValue();
                lagerd.setCREDIT(accountUses.get(i).getCredit().doubleValue());
                lagerd.setDEBIT(accountUses.get(i).getDebit().doubleValue());
                lagerd.setBalance(DebitCredit);
                lagerd.setTotalCredit(Credit);
                lagerd.setTotalDebit(Debit);
                lagerd.setReference(accountUses.get(i).getVOUCHEID().getVoucherId());
                ledgerCardResults.add(lagerd);
                lagerd = null;
                accountUse = null;
            }
        } else if (fmsLuSystem.getSystemCode() != null && fmsSubsid1aryLedger1.getSubsidiaryCode() == null) {
            Sys = fmsLuSystem.getSystemId().toString();
            ledgerCardDataModel = new ArrayDataModel<>();
            HashMap hashmap = new HashMap();
            hashmap.put("MONTH_YEAR", "2020");
            hashmap.put("SUBSID", Conc);
            hashmap.put("SYS", Sys);
            System.out.println("size =+++");
            ledgerCardResults = new ArrayList<>();
            List<FmsAccountUse> accountUses = new ArrayList<>();

            accountUses = accountUseBeanLocal.getlagerd_Sys(hashmap);
            int size = accountUses.size();
            for (int i = 0; i < size; i++) {
                FmsLedgerCardResult lagerd = new FmsLedgerCardResult();
                accountUse = new FmsAccountUse();
                accountUse = accountUses.get(i);
                DebitCredit += accountUses.get(i).getCredit().doubleValue();
                DebitCredit -= accountUses.get(i).getDebit().doubleValue();
                Credit += accountUses.get(i).getCredit().doubleValue();
                Debit += accountUses.get(i).getDebit().doubleValue();
                lagerd.setCREDIT(accountUses.get(i).getCredit().doubleValue());
                lagerd.setDEBIT(accountUses.get(i).getDebit().doubleValue());
                lagerd.setBalance(DebitCredit);
                lagerd.setTotalCredit(Credit);
                lagerd.setTotalDebit(Debit);
                lagerd.setReference(accountUses.get(i).getVOUCHEID().getVoucherId());
                ledgerCardResults.add(lagerd);
                lagerd = null;
                accountUse = null;
            }
        } else if (fmsLuSystem.getSystemCode() == null && fmsSubsid1aryLedger1.getSubsidiaryCode() == null && fmsGeneralLedger.getGeneralLedgerCode() != null) {
            Conc = fmsGeneralLedger.getGeneralLedgerId().toString();
            ledgerCardDataModel = new ArrayDataModel<>();
            HashMap hashmap = new HashMap();
            hashmap.put("MONTH_YEAR", "2020");
            hashmap.put("SUBSID", Conc);
            System.out.println("size =+++");
            ledgerCardResults = new ArrayList<>();
            List<FmsAccountUse> accountUses = new ArrayList<>();

            accountUses = accountUseBeanLocal.getlagerd_GL(hashmap);
            int size = accountUses.size();
            for (int i = 0; i < size; i++) {
                FmsLedgerCardResult lagerd = new FmsLedgerCardResult();
                accountUse = new FmsAccountUse();
                accountUse = accountUses.get(i);
                DebitCredit += accountUses.get(i).getCredit().doubleValue();
                DebitCredit -= accountUses.get(i).getDebit().doubleValue();
                Credit += accountUses.get(i).getCredit().doubleValue();
                Debit += accountUses.get(i).getDebit().doubleValue();
                lagerd.setCREDIT(accountUses.get(i).getCredit().doubleValue());
                lagerd.setDEBIT(accountUses.get(i).getDebit().doubleValue());
                lagerd.setBalance(DebitCredit);
                lagerd.setTotalCredit(Credit);
                lagerd.setTotalDebit(Debit);
                lagerd.setReference(accountUses.get(i).getVOUCHEID().getVoucherId());
                ledgerCardResults.add(lagerd);
                lagerd = null;
                accountUse = null;
            }
        }
        recreateModelDetail();
//        } else {
//         EventEntry eventEntry = new EventEntry();
//         eventEntry.setSessionId(sessionBean.getSessionID());
//         eventEntry.setUserId(sessionBean.getUserId());
//         QName qualifiedName = new QName("", "project");
//         JAXBElement<String> test = new JAXBElement<String> (qualifiedName,String.class,null,sessionBean.getUserName());
//         eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
//            security.addEventLog(eventEntry, dataset);

//            }
    }

    void restet() {
        DebitCredit = 0.0;
        Credit = 0.0;
        Debit = 0.0;
    }

    public void gettrialBalance() {
        String Sys = null;
        getListOfSystems();
        restet();
        trialbalans = new ArrayList<>();
        for (FmsLuSystem listOfSystem : listOfSystems) {
            listOfSystem.getSystemId();
            Sys = listOfSystem.getSystemId().toString();
            ledgerCardDataModel = new ArrayDataModel<>();
            HashMap hashmap = new HashMap();
            hashmap.put("MONTH_YEAR", "2020");
            hashmap.put("SUBSID", Conc);
            hashmap.put("SYS", Sys);
            System.out.println("size =+++");
            ledgerCardResults = new ArrayList<>();
            List<FmsAccountUse> accountUses = new ArrayList<>();
            accountUses = accountUseBeanLocal.getlagerd_Sys(hashmap);

            if (accountUses != null) {
                for (FmsAccountUse accountUse : accountUses) {
                    FmsLedgerCardResult lagerd = new FmsLedgerCardResult();
                    DebitCredit += accountUse.getCredit().doubleValue();
                    DebitCredit -= accountUse.getDebit().doubleValue();
                    Credit += accountUse.getCredit().doubleValue();
                    Debit += accountUse.getDebit().doubleValue();
                    lagerd.setCREDIT(accountUse.getCredit().doubleValue());
                    lagerd.setDEBIT(accountUse.getDebit().doubleValue());
                    lagerd.setBalance(DebitCredit);
                    lagerd.setTotalCredit(Credit);
                    lagerd.setTotalDebit(Debit);
                    lagerd.setReference(accountUse.getSubsidaryId());
                    ledgerCardResults.add(lagerd);
                    lagerd = null;
                    accountUse = null;
                }
            }
            FmsLedgerCardResult lagerd = new FmsLedgerCardResult();
            if (DebitCredit > 0) {
                lagerd.setCREDIT(DebitCredit);
                lagerd.setDEBIT(0);
            } else if (DebitCredit < 0) {
                lagerd.setCREDIT(0);
                lagerd.setDEBIT(DebitCredit);
            } else if (DebitCredit == 0) {

                lagerd.setCREDIT(0);
                lagerd.setDEBIT(0);
            }
            lagerd.setBalance(DebitCredit);
            lagerd.setReference(listOfSystem.getSystemId().toString());

            trialbalans.add(lagerd);
            restet();
        }
        System.out.println("trialbalans" + trialbalans.size());
        ledgerCardDataModel = null;
        ledgerCardDataModel = new ListDataModel(new ArrayList(trialbalans));
    }

    public void gettrialBalance_sys() {
        restet();
        String Sys = null, G_L = null, S_L = null;
        getListOfSystems();
        int x = 0, y = 0;
        trialbalans = new ArrayList<>();
        for (FmsLuSystem listOfSystem : listOfSystems) {
            listOfSystem.getSystemId();
            Sys = listOfSystem.getSystemId().toString();
            getListofgeneralLedgers();
            System.out.println("size ==-----------" + Sys + "-----------------=== " + y++);
            for (FmsGeneralLedger generalLedger : listofgeneralLedgers) {
                G_L = generalLedger.getGeneralLedgerId().toString();
                ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
                if (generalLedger != null) {
                    subsidaryList = subLedgerBeanLocal.findSubLedger(generalLedger);
                    if (subsidaryList != null) {
                        for (FmsSubsidiaryLedger subsidiaryLedger : subsidaryList) {
                            S_L = subsidiaryLedger.getSubsidiaryId().toString();
                            Conc = G_L + "-" + S_L;
                            ledgerCardDataModel = new ArrayDataModel<>();
                            HashMap hashmap = new HashMap();
                            hashmap.put("MONTH_YEAR", "2020");
                            hashmap.put("SUBSID", Conc);
                            hashmap.put("SYS", Sys);
                            List<FmsAccountUse> accountUses = new ArrayList<>();
                            accountUses = accountUseBeanLocal.getlagerd_SL(hashmap);
                            String subsder = "";

                            if (accountUses != null) {
                                for (FmsAccountUse accountUse : accountUses) {
                                    DebitCredit += accountUse.getCredit().doubleValue();
                                    DebitCredit -= accountUse.getDebit().doubleValue();
                                    Credit += accountUse.getCredit().doubleValue();
                                    Debit += accountUse.getDebit().doubleValue();

                                    subsder = accountUse.getSubsidaryId();
                                    accountUse = null;
                                }
                            }
                            FmsLedgerCardResult lagerdcard = new FmsLedgerCardResult();
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
                            if (!subsder.matches("")) {
                                trialbalans.add(lagerdcard);
                                restet();
                                System.out.println("size --- " + x++);
                            }
                            DebitCredit = 0.0;
                            lagerdcard = null;
                        }
                    }
                }
            }
        }
        System.out.println("trialbalans " + trialbalans.size());
        ledgerCardDataModel = null;
        ledgerCardDataModel = new ListDataModel(new ArrayList(trialbalans));
    }

    public void gettrialBalance_preclose() {
        String Sys = null, G_L = null, S_L = null;
        getListOfSystems();
        int x = 0, y = 0;
        trialbalans = new ArrayList<>();

        Sys = fmsLuSystem.getSystemId().toString();
        getListofgeneralLedgers();
        System.out.println("size ==-----------" + Sys + "-----------------=== " + y++);
        for (FmsGeneralLedger generalLedger : listofgeneralLedgers) {
            G_L = generalLedger.getGeneralLedgerId().toString();
            ArrayList<FmsSubsidiaryLedger> subsidaryList = new ArrayList<>();
            if (generalLedger != null) {
                subsidaryList = subLedgerBeanLocal.findSubLedger(generalLedger);
                for (FmsSubsidiaryLedger subsidiaryLedger : subsidaryList) {
                    S_L = subsidiaryLedger.getSubsidiaryId().toString();
                    Conc = G_L + "-" + S_L;

                    ledgerCardDataModel = new ArrayDataModel<>();
                    HashMap hashmap = new HashMap();
                    hashmap.put("MONTH_YEAR", "2020");
                    hashmap.put("SUBSID", Conc);
                    hashmap.put("SYS", Sys);
                    List<FmsAccountUse> accountUses = new ArrayList<>();
                    accountUses = accountUseBeanLocal.getlagerd_SL(hashmap);
                    String subsder = "";

                    if (accountUses != null) {
                        for (FmsAccountUse accountUse : accountUses) {
                            DebitCredit += accountUse.getCredit().doubleValue();
                            DebitCredit -= accountUse.getDebit().doubleValue();
                            Credit += accountUse.getCredit().doubleValue();
                            Debit += accountUse.getDebit().doubleValue();

                            subsder = accountUse.getSubsidaryId();
                            accountUse = null;
                        }
                    }
                    FmsLedgerCardResult lagerdcard = new FmsLedgerCardResult();
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
                    if (!subsder.matches("")) {
                        trialbalans.add(lagerdcard);
                        restet();
                        System.out.println("size --- " + x++);
                    }
                    DebitCredit = 0.0;
                    lagerdcard = null;
                }
            }
        }
        System.out.println("trialbalans " + trialbalans.size());
        ledgerCardDataModel = null;
        ledgerCardDataModel = new ListDataModel(new ArrayList(trialbalans));
    }

    public void gettrialBalance_GL() {
        //           AAA securityService = new AAA();
//            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
//            String systemBundle = "et/gov/eep/commonApplications/securityServer";
//            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");          
//            if (security.checkAccess(SessionBean.getUserName(), "saveAppealRequest", dataset)) {
//                 put ur code here...!  
        String Sys = null, G_L = null, S_L = null;
        getListOfSystems();
        int x = 0, y = 0;
        trialbalans = new ArrayList<>();

        Sys = fmsLuSystem.getSystemCode().toString();
        System.out.println("Sys___" + Sys);
        getListofgeneralLedgers();
        System.out.println("getListofgeneralLedgers___" + getListofgeneralLedgers());
        for (FmsGeneralLedger generalLedger : listofgeneralLedgers) {
            G_L = generalLedger.getGeneralLedgerId().toString();
            Conc = G_L;
            ledgerCardDataModel = new ArrayDataModel<>();
            HashMap hashmap = new HashMap();
            hashmap.put("MONTH_YEAR", "2020");
            hashmap.put("SUBSID", Conc);
            hashmap.put("SYS", Sys);
            List<FmsAccountUse> accountUses = new ArrayList<>();
            accountUses = accountUseBeanLocal.gettrilSL(hashmap);
            System.out.println("accountUses" + accountUses);
            String subsder = "";

            if (accountUses != null) {
                System.out.println("size ==-----------" + Sys + "-----------------=== " + accountUses.size());

                for (FmsAccountUse accountUse : accountUses) {
                    System.out.println("for");
                    DebitCredit += accountUse.getCredit().doubleValue();
                    DebitCredit -= accountUse.getDebit().doubleValue();
                    Credit += accountUse.getCredit().doubleValue();
                    Debit += accountUse.getDebit().doubleValue();

                    subsder = accountUse.getSubsidaryId();
                    accountUse = null;
                }
            }
            FmsLedgerCardResult lagerdcard = new FmsLedgerCardResult();
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
            if (!"".equals(lagerdcard.getReference())) {
                System.out.println("lagerdcard.getReference() ___" + lagerdcard.getReference());
                String splitedaccoutid[] = lagerdcard.getReference().split("-");
                fmsLuSystem = new FmsLuSystem();
                System.out.println("split ___" + splitedaccoutid[0]);
                System.out.println("split ___conv" + Integer.parseInt(splitedaccoutid[0]));
                Integer sys11 = Integer.parseInt(splitedaccoutid[0]);
//                System.out.println("sys11" + sys11);
//                fmsLuSystem.setSystemId(sys11);
                fmsLuSystem.setSystemCode(splitedaccoutid[0]);
                System.out.println("system in __" + fmsLuSystem);
                System.out.println("THE SYSTEM ID __" + fmsLuSystem);
                fmsLuSystem = fmsLuSystemBeanLocal.getSysDetail(fmsLuSystem);
                System.out.println("THE SYSTEM ID __11" + fmsLuSystem);
                String CC_display = "CC";
                String SL_display = "SL";
                if (!"CC".equals(splitedaccoutid[1])) {
                    System.out.println("splitedaccoutid[1]----" + splitedaccoutid[1]);
                    fmsCostCenter = new FmsCostCenter();
                    fmsCostCenter.setSystemName(splitedaccoutid[1]);
                    System.out.println("fmsCostCenter--- " + fmsCostCenter);
                    fmsCostCenter = fmsCostCenterBeanLocal.getCostDetail(fmsCostCenter);
                    System.out.println("fmsCostCenter after --- " + fmsCostCenter);

                    CC_display = fmsCostCenter.getSystemName();
                    System.out.println("CC_display===" + CC_display);
                }
                fmsGeneralLedger = new FmsGeneralLedger();
                fmsGeneralLedger.setGeneralLedgerCode(splitedaccoutid[2]);
                System.out.println("fmsGeneralLedger===" + fmsGeneralLedger);
                fmsGeneralLedger = fmsGeneralLedgerBeanLocal.getGLDetail(fmsGeneralLedger);
                System.out.println("fmsGeneralLedger == " + fmsGeneralLedger);
//                if (!"SL".equals(splitedaccoutid[2])) {
//                    fmsSubsid1aryLedger1 = new FmsSubsidiaryLedger();
//                    fmsSubsid1aryLedger1.setSubsidiaryId(Integer.parseInt(splitedaccoutid[3]));
//                    fmsSubsid1aryLedger1 = subLedgerBeanLocal.getSubsidiaryInfo(fmsSubsid1aryLedger1);
//                    SL_display = fmsSubsid1aryLedger1.getSubsidiaryCode();
//                }
                System.out.println("CC_display___" + CC_display);
//                System.out.println("fmsGeneralLedger.getGeneralLedgerCode()___" + fmsGeneralLedger.getGeneralLedgerCode());
//                System.out.println("SL_display___" + SL_display);
//                display_conn = fmsLuSystem.getSystemCode() + "-" + CC_display + "-" + fmsGeneralLedger.getGeneralLedgerCode() + "-" + SL_display;
                display_conn = fmsLuSystem + "-" + CC_display + "-" + fmsGeneralLedger;
                lagerdcard.setDisplay_conn(display_conn);
            }
            if (!subsder.matches("")) {
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
        if (trialbalans.isEmpty()) {
            printerRender = false;
        } else {
            printerRender = true;
        }
        //        } else {
//         EventEntry eventEntry = new EventEntry();
//         eventEntry.setSessionId(sessionBean.getSessionID());
//         eventEntry.setUserId(sessionBean.getUserId());
//         QName qualifiedName = new QName("", "project");
//         JAXBElement<String> test = new JAXBElement<String> (qualifiedName,String.class,null,sessionBean.getUserName());
//         eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
//            security.addEventLog(eventEntry, dataset);

//            }
    }

//         //<editor-fold defaultstate="collapsed" desc="file and trilbalnce fms doc">
    @EJB
    FmsTrialBalanceDetailFacade trialBalanceDetailFacade;
    private StreamedContent file;

    public void FileDownloadView() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/optimus.jpg");
        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
    }

    public StreamedContent setFile(StreamedContent file) {
        return file = file;
    }

    public StreamedContent getFile() throws Throwable {
        lOBExport2Excel();
        return file;
    }
//
//

    public String generateFinancialStmt() throws SQLException, IOException, BiffException, WriteException {
        String AccountingPre = "2015_2016";
        byte[] templateByteFile = null;
        templateByteFile = trialBalanceDetailFacade.getTemplate(AccountingPre);
        ByteArrayInputStream bis = new ByteArrayInputStream(templateByteFile);
        Workbook workbook = Workbook.getWorkbook(bis);
        WritableWorkbook copy = Workbook.createWorkbook(new File("copy_template.xls"), workbook);
        WritableSheet writableSheetSL_TR = copy.getSheet("GL_TR");
        Sheet preservResSheet = workbook.getSheet(0);
        Sheet sheet2 = workbook.getSheet(1);
        Sheet sheet3 = workbook.getSheet(2);
//        Sheet sheet4 = workbook.getSheet(3);
        int totlaRows = preservResSheet.getRows();
        int totlaRowssheet1 = sheet2.getRows();
        int totlaRowssheet2 = sheet3.getRows();
//        int totlaRowssheet3 = sheet4.getRows();
//                   int totlaRowssheet4 = sheet2.getRows();
        System.out.println("---------------------- total row 1is " + totlaRows);
        System.out.println("---------------------- total row 2 is " + totlaRowssheet1);
        System.out.println("---------------------- total row 3 is " + totlaRowssheet2);
//        System.out.println("---------------------- total row  4 is " + totlaRowssheet3);
        InputStream stream = bis;
//        setFile(stream);
        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
        //FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/optimus.jpg");
        return null;
    }

    protected void lOBExport2Excel() throws Throwable {
        try {

            HSSFWorkbook wb = new HSSFWorkbook();
            String AccountingPre = "2015_2016";
            byte[] templateByteFile = null;
            templateByteFile = trialBalanceDetailFacade.getTemplate(AccountingPre);
            Workbook workbook = Workbook.getWorkbook(convertFichier(templateByteFile).getStream());
            File inp = new File("copy_template.xls");
            WritableWorkbook copy = Workbook.createWorkbook(inp, workbook);
            String excelFileName = "Financial Stmt.xls";
            FileOutputStream fos = new FileOutputStream(excelFileName);
            WritableSheet writableSheetTr = copy.getSheet("GL_TR");
            int j = 3, size = trialbalans.size();
            for (int i = 0; i < size; i++) {
                WritableCell writableCellContentDr = writableSheetTr.getWritableCell("C" + j);
                WritableCell writableCellContentCr = writableSheetTr.getWritableCell("D" + j);
                //  WritableCell writableCellContentBal = writableSheet.getWritableCell("F" + j);
//                WritableCell wcAccountId = writableSheetTr.getWritableCell("A" + j);
//                if(wcAccountId.getContents()!=null ||!(wcAccountId.getContents().equals("")) )
//                {                   

//                if ((writableCellContentDr.getType() == CellType.NUMBER) && (writableCellContentCr.getType() == CellType.NUMBER)) {
                System.out.println(trialbalans.get(i).getCREDIT() + "aaaaaa" + trialbalans.get(i).getDEBIT());
                jxl.write.Number contentDr = (jxl.write.Number) writableCellContentDr;
                jxl.write.Number contentCr = (jxl.write.Number) writableCellContentCr;
                System.out.println(trialbalans.get(i).getCREDIT() + "bbbbbbbbb" + trialbalans.get(i).getDEBIT());
                contentDr.setValue(trialbalans.get(i).getDEBIT());
                contentCr.setValue(trialbalans.get(i).getCREDIT());
                System.out.println(contentCr.getContents() + "--------------" + contentDr.getContents());
//                }
                j++;
            }
            System.out.println("excel shet end");
            wb.write(fos);
            fos.flush();
            fos.close();

            copy.write();
            copy.close();
            FileInputStream toBeSaved = new FileInputStream(new File("copy_template.xls"));
            InputStream stream = convertFichier(templateByteFile).getStream();
            file = new DefaultStreamedContent(toBeSaved, "application/xls", excelFileName);

        } catch (Exception e) {
            ;//catchError(e);
        }

    }

    public StreamedContent convertFichier(byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        // System.out.println("size file : " + bytes.length);
        StreamedContent image = new DefaultStreamedContent(is);
        // System.out.println("dans le convertisseur : " + image.getContentType());
        return image;
    }

    public void reportData() throws IOException, PropertyNotFoundException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(0.0);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"my.xls\"");
        workbook.write(externalContext.getResponseOutputStream());
        facesContext.responseComplete();

        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/optimus.jpg");
        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
    }
//        
//      // </editor-fold>      

}
